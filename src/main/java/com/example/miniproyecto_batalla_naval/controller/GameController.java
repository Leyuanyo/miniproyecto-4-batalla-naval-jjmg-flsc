package com.example.miniproyecto_batalla_naval.controller;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameController implements CellInteractionListener, BoardListener {

    @FXML
    private GridPane positionBoardGrid;

    @FXML
    private GridPane mainBoardGrid;

    @FXML
    private Label turnLabel;

    @FXML
    private Label messageLabel;

    private GameModel model;
    private StackPane[][] positionCells;
    private StackPane[][] mainCells;
    private final GameSerializer gameSerializer = new GameSerializer();
    private final PlayerFileManager playerFileManager = new PlayerFileManager();

    public void startGame(GameModel model) {
        this.model = model;
        setupBoards();
        paintOwnFleet();
        updateTurnLabel();
        autosave();
    }

    public void resumeGame(GameModel savedModel) {
        this.model = savedModel;
        setupBoards();
        repaintFromModel();
        updateTurnLabel();

        if (!model.isHumanTurn()) {
            messageLabel.setText("Reanudando turno de la maquina...");
            runMachineTurn();
        }
    }

    private void setupBoards() {
        positionCells = BoardGridBuilder.build(positionBoardGrid, new ReadOnlyListener());
        mainCells = BoardGridBuilder.build(mainBoardGrid, this);

        model.getHuman().getBoard().addListener(this);
    }

    @Override
    public void onCellChanged(Cell cell) {
        Platform.runLater(() ->
                repaintCell(
                        positionCells[cell.getRow()][cell.getColumn()],
                        cell.getState()
                )
        );
    }

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

    @Override
    public void onCellRightClick(int row, int column) {
        // The main board does not use right-click during gameplay.
    }

    private void runMachineTurn() {
        MachineTurnRunner runner = new MachineTurnRunner(model, this::onMachineShotResolved);
        Thread thread = new Thread(runner, "machine-turn");
        thread.setDaemon(true);
        thread.start();
    }

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

                break;
        }
    }

    private void paintShotResult(StackPane cellPane, ShotResult result) {
        if (result == ShotResult.WATER) {
            cellPane.getChildren().add(ShipShapeFactory.createWaterMark());
        } else if (result == ShotResult.HIT) {
            cellPane.getChildren().add(ShipShapeFactory.createHitMark());
        }
    }

    private void revealSunkShip(Ship ship, StackPane[][] cells) {
        for (Cell cell : ship.getOccupiedCells()) {
            cells[cell.getRow()][cell.getColumn()].getChildren().add(ShipShapeFactory.createSunkMark());
        }
    }

    private void updateTurnLabel() {
        turnLabel.setText(model.isHumanTurn() ? "Tu turno" : "Turno de la maquina");
    }

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

    private void autosave() {
        gameSerializer.save(model);
        int shipsHumanSunk = countSunk(model.getHuman().getBoard());
        int shipsMachineSunk = countSunk(model.getMachine().getBoard());
        playerFileManager.save(model.getHuman().getNickname(), shipsHumanSunk, shipsMachineSunk);
    }

    private int countSunk(Board board) {
        int count = 0;
        for (Ship ship : board.getFleet()) {
            if (ship.isSunk()) {
                count++;
            }
        }
        return count;
    }

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
        popup.show();
    }

    private static class ReadOnlyListener implements CellInteractionListener {
        @Override
        public void onCellLeftClick(int row, int column) {
            // Observation-only board: ignores clicks.
        }

        @Override
        public void onCellRightClick(int row, int column) {
            // Observation-only board: ignores clicks.
        }
    }
}