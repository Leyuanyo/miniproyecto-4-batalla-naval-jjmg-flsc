package com.example.miniproyecto_batalla_naval.controller;

import java.util.concurrent.atomic.AtomicBoolean;
import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionListener;
import com.example.miniproyecto_batalla_naval.exceptions.CellAlreadyShotException;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;
import com.example.miniproyecto_batalla_naval.model.interfaces.BoardListener;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.persistence.GameSerializer;
import com.example.miniproyecto_batalla_naval.persistence.PlayerFileManager;
import com.example.miniproyecto_batalla_naval.util.BoardGridBuilder;
import com.example.miniproyecto_batalla_naval.util.ShipShapeFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main controller for the Battleship game screen.
 * Implements {@link CellInteractionListener} and {@link BoardListener}
 * to receive user interactions, synchronize the graphical interface with
 * the {@link GameModel}, manage the turn flow, update both game boards,
 * execute machine turns, and coordinate persistence operations.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameController implements CellInteractionListener, BoardListener {

    /** Label displaying the elapsed game time. */
    @FXML
    private Label timeLabel;

    /** Grid representing the human player's board. */
    @FXML
    private GridPane positionBoardGrid;

    /** Grid representing the machine player's board. */
    @FXML
    private GridPane mainBoardGrid;

    /** Label displaying whose turn is currently active. */
    @FXML
    private Label turnLabel;

    /** Label displaying gameplay messages and notifications. */
    @FXML
    private Label messageLabel;

    /** Game model containing the complete match state. */
    private GameModel model;

    /** Matrix containing the graphical cells of the player's board. */
    private StackPane[][] positionCells;

    /** Matrix containing the graphical cells of the machine's board. */
    private StackPane[][] mainCells;

    /** Serializer responsible for saving and restoring game progress. */
    private final GameSerializer gameSerializer = new GameSerializer();

    /** Manager responsible for storing player statistics. */
    private final PlayerFileManager playerFileManager = new PlayerFileManager();

    /** Controls the execution of the game timer thread. */
    private final AtomicBoolean gameTimerRunning = new AtomicBoolean(true);

    /** Number of elapsed seconds since the match started. */
    private int elapsedSeconds = 0;

    /**
     * Starts a new Battleship match.
     * Initializes the game model, builds both boards,
     * renders the player's fleet, updates the turn indicator,
     * performs an automatic save, and starts the game timer.
     *
     * @param model the game model containing the current match state
     */
    public void startGame(GameModel model) {
        this.model = model;
        setupBoards();
        paintOwnFleet();
        updateTurnLabel();
        autosave();
        startGameTimer();
    }

    /**
     * Restores a previously saved match.
     * Rebuilds the graphical boards, restores their visual state,
     * updates the turn indicator, and resumes the game timer.
     * If it is currently the machine's turn, the corresponding
     * action is executed automatically.
     *
     * @param savedModel the previously saved game model
     */
    public void resumeGame(GameModel savedModel) {
        this.model = savedModel;
        setupBoards();
        repaintFromModel();
        updateTurnLabel();

        if (!model.isHumanTurn()) {
            messageLabel.setText("Reanudando turno de la maquina...");
            runMachineTurn();
        }
        startGameTimer();
    }

    /**
     * Builds both game boards and registers the listeners required
     * to synchronize the graphical interface with the underlying model.
     */
    private void setupBoards() {
        positionCells = BoardGridBuilder.build(positionBoardGrid, new ReadOnlyListener());
        mainCells = BoardGridBuilder.build(mainBoardGrid, this);

        model.getHuman().getBoard().addListener(this);
    }

    /**
     * {@inheritDoc}
     * Updates the graphical representation of a board cell whenever
     * its state changes in the game model.
     *
     * @param cell the cell whose state has changed
     */
    @Override
    public void onCellChanged(Cell cell) {
        Platform.runLater(() ->
                repaintCell(
                        positionCells[cell.getRow()][cell.getColumn()],
                        cell.getState()
                )
        );
    }

    /**
     * {@inheritDoc}
     * Processes a left-click on the machine's board.
     * Validates that the match is in a playable state, executes the shot,
     * updates the graphical interface according to the obtained result,
     * persists the game state, and transfers the turn when necessary.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    @Override
    public void onCellLeftClick(int row, int column) {

        if (model == null
                || !model.isHumanTurn()
                || model.getMachine().getBoard().isFleetSunk()
                || model.getHuman().getBoard().isFleetSunk()) {
            return;
        }
        try {
            Board enemyBoard = model.getMachine().getBoard();
            Cell targetCell = enemyBoard.getCell(row, column);
            ShotResult result = enemyBoard.receiveShot(row, column);

            if (result == ShotResult.SUNK || result == ShotResult.GAME_OVER) {
                revealSunkShip(targetCell.getShip(), mainCells);
            } else {
                paintShotResult(mainCells[row][column], result);
            }

            applyMessage(result, true);

            if (result == ShotResult.WATER) {
                model.setHumanTurn(false);
                updateTurnLabel();
                autosave();
                runMachineTurn();
            } else {
                updateTurnLabel();
                autosave();
            }
        } catch (CellAlreadyShotException e) {
            messageLabel.setText("Ya disparaste ahi. Elige otra celda.");
        }
    }

    /**
     * {@inheritDoc}
     * Right-click interactions are ignored during gameplay,
     * since only left-click actions are used to fire shots.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    @Override
    public void onCellRightClick(int row, int column) {
        // The main board does not use right-click during gameplay.
    }

    /**
     * Starts the game timer in a background thread.
     * The elapsed time is updated every second until
     * the match finishes.
     */
    private void startGameTimer() {
        Thread timerThread = new Thread(() -> {
            while (gameTimerRunning.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                elapsedSeconds++;
                Platform.runLater(() -> timeLabel.setText("Tiempo: " + elapsedSeconds + "s"));
            }
        }, "game-timer");
        timerThread.setDaemon(true);
        timerThread.start();
    }

    /**
     * Creates and starts a background thread responsible for
     * executing the machine player's turn without blocking
     * the JavaFX application thread.
     */
    private void runMachineTurn() {
        MachineTurnRunner runner = new MachineTurnRunner(model, this::onMachineShotResolved);
        Thread thread = new Thread(runner, "machine-turn");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Resolves the result of the machine's shot.
     * Updates the interface, switches turns when appropriate,
     * stores the current game state, and continues the machine's
     * turn if another shot is granted.
     *
     * @param row the targeted row
     * @param column the targeted column
     * @param result the result produced by the machine's shot
     */
    private void onMachineShotResolved(int row, int column, ShotResult result) {
        Platform.runLater(() -> {
            applyMessage(result, false);

            if (result == ShotResult.WATER) {
                model.setHumanTurn(true);
                updateTurnLabel();
                autosave();
            } else if (result == ShotResult.GAME_OVER) {
                updateTurnLabel();
                autosave();
            } else {
                updateTurnLabel();
                autosave();
                runMachineTurn();
            }
        });
    }

    /**
     * Displays the appropriate gameplay message according to
     * the result of the most recent shot.
     *
     * @param result the outcome of the executed shot
     * @param shotByHuman {@code true} if the shot was fired by the human player;
     *                    {@code false} if it was fired by the machine
     */
    private void applyMessage(ShotResult result, boolean shotByHuman) {
        switch (result) {
            case WATER:
                messageLabel.setText(shotByHuman ? "Agua. Turno de la maquina." : "La maquina fallo. Tu turno.");
                break;
            case HIT:
                messageLabel.setText(shotByHuman ? "Tocado! Dispara otra vez." : "La maquina te toco un barco.");
                break;
            case SUNK:
                messageLabel.setText(shotByHuman ? "Hundiste un barco! Dispara otra vez." : "La maquina te hundio un barco.");
                break;
            case GAME_OVER:

                messageLabel.setText(
                        shotByHuman
                                ? "Ganaste! Hundiste toda la flota."
                                : "La maquina hundio tu flota. Perdiste."
                );

                mainBoardGrid.setDisable(true);
                positionBoardGrid.setDisable(true);
                gameTimerRunning.set(false);

                break;
        }
    }

    /**
     * Paints the graphical mark corresponding to the specified
     * shot result on the given board cell.
     *
     * @param cellPane the graphical cell where the mark will be displayed
     * @param result the result to be represented
     */
    private void paintShotResult(StackPane cellPane, ShotResult result) {
        if (result == ShotResult.WATER) {
            cellPane.getChildren().add(ShipShapeFactory.createWaterMark());
        } else if (result == ShotResult.HIT) {
            cellPane.getChildren().add(ShipShapeFactory.createHitMark());
        }
    }

    /**
     * Reveals every segment of a ship that has been completely sunk.
     *
     * @param ship the ship to reveal
     * @param cells the graphical board containing the ship
     */
    private void revealSunkShip(Ship ship, StackPane[][] cells) {
        for (Cell cell : ship.getOccupiedCells()) {
            cells[cell.getRow()][cell.getColumn()].getChildren().add(ShipShapeFactory.createSunkMark());
        }
    }

    /**
     * Updates the label indicating which player's turn
     * is currently active.
     */
    private void updateTurnLabel() {
        turnLabel.setText(model.isHumanTurn() ? "Tu turno" : "Turno de la maquina");
    }

    /**
     * Draws the complete fleet of the human player on the
     * position board using the corresponding graphical segment
     * for each occupied cell.
     */
    private void paintOwnFleet() {

        for (Ship ship : model.getHuman().getBoard().getFleet()) {

            int size = ship.getOccupiedCells().size();

            for (int i = 0; i < size; i++) {

                Cell cell = ship.getOccupiedCells().get(i);

                ShipShapeFactory.SegmentType segment;

                if (size == 1) {
                    segment = ShipShapeFactory.SegmentType.SINGLE;
                } else if (i == 0) {
                    segment = ShipShapeFactory.SegmentType.HEAD;
                } else if (i == size - 1) {
                    segment = ShipShapeFactory.SegmentType.TAIL;
                } else {
                    segment = ShipShapeFactory.SegmentType.BODY;
                }

                positionCells[cell.getRow()][cell.getColumn()]
                        .getChildren()
                        .add(ShipShapeFactory.createShipSegment(segment, ship.getOrientation()));
            }
        }
    }

    /**
     * Restores the graphical state of both boards from
     * the information stored in the current game model.
     * Repaints ships, previous shots, and every sunk ship.
     */
    private void repaintFromModel() {
        paintOwnFleet();
        Board humanBoard = model.getHuman().getBoard();
        Board machineBoard = model.getMachine().getBoard();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                repaintCell(positionCells[row][column], humanBoard.getCell(row, column).getState());

                CellState machineState = machineBoard.getCell(row, column).getState();
                if (machineState == CellState.HIT) {
                    paintShotResult(mainCells[row][column], ShotResult.HIT);
                } else if (machineState == CellState.WATER) {
                    paintShotResult(mainCells[row][column], ShotResult.WATER);
                }
            }
        }

        for (Ship ship : machineBoard.getFleet()) {
            if (ship.isSunk()) {
                revealSunkShip(ship, mainCells);
            }
        }
    }

    /**
     * Updates the graphical representation of a board cell according
     * to its current state.
     *
     * @param cellPane the graphical container representing the board cell
     * @param state the current state of the cell
     */
    private void repaintCell(StackPane cellPane, CellState state) {
        switch (state) {
            case WATER:
                cellPane.getChildren().add(ShipShapeFactory.createWaterMark());
                break;
            case HIT:
                cellPane.getChildren().add(ShipShapeFactory.createHitMark());
                break;
            case SUNK:
                cellPane.getChildren().add(ShipShapeFactory.createSunkMark());
                break;
            default:
                break;
        }
    }

    /**
     * Saves the current game state and updates the player's
     * statistics stored on disk.
     */
    private void autosave() {
        gameSerializer.save(model);
        int shipsHumanSunk = countSunk(model.getHuman().getBoard());
        int shipsMachineSunk = countSunk(model.getMachine().getBoard());
        playerFileManager.save(model.getHuman().getNickname(), shipsHumanSunk, shipsMachineSunk);
    }

    /**
     * Counts the number of ships that have been completely sunk
     * on the specified board.
     *
     * @param board the board whose fleet will be evaluated
     * @return the number of sunk ships on the board
     */
    private int countSunk(Board board) {
        int count = 0;
        for (Ship ship : board.getFleet()) {
            if (ship.isSunk()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Opens a verification window displaying the machine's board.
     * This view is intended exclusively for debugging and validation
     * purposes, allowing the complete enemy fleet to be visualized.
     */
    @FXML
    private void handleViewMachineBoard() {

        Stage popup = new Stage();

        GridPane verificationGrid = new GridPane();

        StackPane[][] verificationCells =
                BoardGridBuilder.build(verificationGrid, new ReadOnlyListener());

        for (Ship ship : model.getMachine().getBoard().getFleet()) {

            int size = ship.getOccupiedCells().size();

            for (int i = 0; i < size; i++) {

                Cell cell = ship.getOccupiedCells().get(i);

                ShipShapeFactory.SegmentType segment;

                if (size == 1) {
                    segment = ShipShapeFactory.SegmentType.SINGLE;
                } else if (i == 0) {
                    segment = ShipShapeFactory.SegmentType.HEAD;
                } else if (i == size - 1) {
                    segment = ShipShapeFactory.SegmentType.TAIL;
                } else {
                    segment = ShipShapeFactory.SegmentType.BODY;
                }

                verificationCells[cell.getRow()][cell.getColumn()]
                        .getChildren()
                        .add(
                                ShipShapeFactory.createShipSegment(
                                        segment,
                                        ship.getOrientation()
                                )
                        );
            }
        }

        popup.setTitle("Tablero de la maquina (solo verificacion)");
        popup.setScene(new Scene(verificationGrid));

        var iconStream = getClass().getResourceAsStream(
                "/com/example/miniproyecto_batalla_naval/images/cheatImage.png");
        if (iconStream != null) {
            popup.getIcons().add(new Image(iconStream));
        }

        popup.show();
    }

    /**
     * Read-only implementation of {@link CellInteractionListener}.
     * Ignores all user interactions on observation-only boards.
     */
    private static class ReadOnlyListener implements CellInteractionListener {

        /**
         * {@inheritDoc}
         * Ignores left-click events because the associated board
         * is intended only for visualization.
         *
         * @param row the row index of the selected cell
         * @param column the column index of the selected cell
         */
        @Override
        public void onCellLeftClick(int row, int column) {
            // Observation-only board: ignores clicks.
        }

        /**
         * {@inheritDoc}
         * Ignores right-click events because the associated board
         * is intended only for visualization.
         *
         * @param row the row index of the selected cell
         * @param column the column index of the selected cell
         */
        @Override
        public void onCellRightClick(int row, int column) {
            // Observation-only board: ignores clicks.
        }
    }
}