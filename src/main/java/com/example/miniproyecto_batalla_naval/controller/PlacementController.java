package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionListener;
import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.controller.adapter.RotationListener;
import com.example.miniproyecto_batalla_naval.controller.adapter.KeyRotationAdapter;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.player.Player;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;
import com.example.miniproyecto_batalla_naval.patterns.SmartShotStrategy;
import com.example.miniproyecto_batalla_naval.util.BoardGridBuilder;
import com.example.miniproyecto_batalla_naval.util.RandomFleetPlacer;
import com.example.miniproyecto_batalla_naval.util.ShipShapeFactory;
import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import com.example.miniproyecto_batalla_naval.model.board.CellState;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Controller responsible for the ship placement stage of the Battleship game.
 * Manages fleet placement, ship rotation, placement preview, and the transition
 * from the placement phase to the main gameplay screen.
 * Implements both {@link CellInteractionListener} and {@link RotationListener}
 * to respond to board interactions and keyboard rotation events.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class PlacementController implements CellInteractionListener, RotationListener {

    /** Grid displaying the player's placement board. */
    @FXML
    private GridPane positionBoardGrid;

    /** Label displaying the next ship that must be placed. */
    @FXML
    private Label currentShipLabel;

    /** Label displaying placement instructions and validation messages. */
    @FXML
    private Label instructionsLabel;

    /** Button used to start the match once the fleet is complete. */
    @FXML
    private Button startGameButton;

    /** Nickname of the current player. */
    private String nickname;

    /** Board where the player places the fleet. */
    private Board board;

    /** Matrix containing the graphical representation of every board cell. */
    private StackPane[][] cellPanes;

    /** Queue storing the ships that remain to be placed. */
    private final Queue<ShipType> pendingShips = new LinkedList<>();

    /** Current orientation used for placing the next ship. */
    private Orientation currentOrientation = Orientation.HORIZONTAL;

    /** List containing the panes used by the current ghost ship preview. */
    private final List<StackPane> ghostPanes = new ArrayList<>();

    /** Last row over which the mouse cursor was positioned. */
    private int lastHoverRow = -1;

    /** Last column over which the mouse cursor was positioned. */
    private int lastHoverColumn = -1;

    /**
     * Initializes a new placement session for the specified player.
     * Creates the board, prepares the pending fleet, builds the visual grid,
     * enables the hover preview, and configures the initial interface state.
     *
     * @param nickname the nickname entered by the player
     */
    public void startNewGame(String nickname) {
        this.nickname = nickname;
        this.board = new Board();
        loadPendingShips();
        cellPanes = BoardGridBuilder.build(positionBoardGrid, this);
        attachHoverPreview();
        updateCurrentShipLabel();
        startGameButton.setDisable(true);
        instructionsLabel.setText("Click izquierdo: coloca. Click derecho o Espacio: rota.");
    }

    /**
     * Loads the fleet that must be placed by the player.
     * Ships are inserted into the queue following the official
     * order established for the game.
     */
    private void loadPendingShips() {
        pendingShips.clear();
        pendingShips.add(ShipType.AIRCRAFT_CARRIER);
        pendingShips.add(ShipType.SUBMARINE);
        pendingShips.add(ShipType.SUBMARINE);
        pendingShips.add(ShipType.DESTROYER);
        pendingShips.add(ShipType.DESTROYER);
        pendingShips.add(ShipType.DESTROYER);
        pendingShips.add(ShipType.FRIGATE);
        pendingShips.add(ShipType.FRIGATE);
        pendingShips.add(ShipType.FRIGATE);
        pendingShips.add(ShipType.FRIGATE);
    }

    /**
     * Rotates the orientation of the ship currently waiting to be placed.
     * If the mouse is positioned over the board, the ghost preview is
     * immediately updated to reflect the new orientation.
     */
    @FXML
    private void handleRotate() {
        currentOrientation = currentOrientation == Orientation.HORIZONTAL
                ? Orientation.VERTICAL
                : Orientation.HORIZONTAL;
        if (lastHoverRow >= 0) {
            showGhostShip(lastHoverRow, lastHoverColumn);
        }
    }

    /**
     * Creates the game model once the fleet has been placed.
     * Generates the machine player, places its fleet automatically,
     * creates the complete game model, and opens the gameplay screen.
     */
    @FXML
    private void handleStartGame() {
        Machine machine = new Machine(new SmartShotStrategy());
        RandomFleetPlacer.placeFullFleet(machine.getBoard());

        Player human = new Player(nickname, board);
        GameModel model = new GameModel(human, machine);

        GameStage.goToGame(model);
    }

    /**
     * {@inheritDoc}
     * Handles a rotation request generated from the keyboard
     * by delegating the action to the controller's rotation logic.
     */
    @Override
    public void onRotateRequested() {
        handleRotate();
    }

    /**
     * {@inheritDoc}
     * Attempts to place the current ship at the selected board position.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    @Override
    public void onCellLeftClick(int row, int column) {
        placeCurrentShip(row, column);
    }

    /**
     * {@inheritDoc}
     * Rotates the current ship when the user performs
     * a right-click on the placement board.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    @Override
    public void onCellRightClick(int row, int column) {
        handleRotate();
    }

    /**
     * Attempts to place the next pending ship on the board.
     * If the placement is valid, the ship is drawn, removed from the
     * pending queue, and the interface is updated accordingly.
     * Otherwise, the validation message is displayed to the player.
     *
     * @param row the row where the ship placement begins
     * @param column the column where the ship placement begins
     */
    private void placeCurrentShip(int row, int column) {
        ShipType nextType = pendingShips.peek();
        if (nextType == null) {
            return;
        }
        clearGhostShip();
        Ship ship = ShipFactory.create(nextType);
        try {
            board.placeShip(ship, row, column, currentOrientation);
            pendingShips.poll();
            paintShip(ship);
            updateCurrentShipLabel();
            if (pendingShips.isEmpty()) {
                startGameButton.setDisable(false);
                instructionsLabel.setText("Flota lista. Pulsa Comenzar partida.");
            }
        } catch (InvalidShipPlacementException e) {
            instructionsLabel.setText(e.getMessage());
        }
    }

    /**
     * Draws the specified ship on the placement board.
     * Each occupied cell is represented using the corresponding
     * graphical segment according to the ship's size, orientation,
     * and position within the vessel.
     *
     * @param ship the ship to be rendered on the board
     */
    private void paintShip(Ship ship) {

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

            cellPanes[cell.getRow()][cell.getColumn()]
                    .getChildren()
                    .add(
                            ShipShapeFactory.createShipSegment(
                                    segment,
                                    ship.getOrientation()
                            )
                    );
        }
    }

    /**
     * Registers the mouse listeners required to display the ghost ship preview.
     * The preview follows the cursor while it remains inside the board and is
     * automatically removed when the cursor leaves the placement area.
     */
    private void attachHoverPreview() {
        positionBoardGrid.setOnMouseMoved(event -> {
            int column = (int) (event.getX() / ShipShapeFactory.CELL_SIZE);
            int row = (int) (event.getY() / ShipShapeFactory.CELL_SIZE);
            if (row >= 0 && row < Board.SIZE && column >= 0 && column < Board.SIZE) {
                showGhostShip(row, column);
            }
        });
        positionBoardGrid.setOnMouseExited(event -> clearGhostShip());
    }

    /**
     * Displays a preview of the next ship to be placed.
     * The preview is rendered using green or red ghost cells depending on
     * whether the current placement is valid according to the board state
     * and the selected orientation.
     *
     * @param anchorRow the row where the preview begins
     * @param anchorColumn the column where the preview begins
     */
    private void showGhostShip(int anchorRow, int anchorColumn) {
        lastHoverRow = anchorRow;
        lastHoverColumn = anchorColumn;
        clearGhostShip();

        ShipType nextType = pendingShips.peek();
        if (nextType == null) {
            return;
        }

        boolean valid = true;
        List<int[]> cellsInBounds = new ArrayList<>();

        for (int i = 0; i < nextType.getSize(); i++) {
            int r = anchorRow + (currentOrientation == Orientation.VERTICAL ? i : 0);
            int c = anchorColumn + (currentOrientation == Orientation.HORIZONTAL ? i : 0);

            if (r < 0 || r >= Board.SIZE || c < 0 || c >= Board.SIZE) {
                valid = false;
                continue;
            }
            if (board.getCell(r, c).getState() != CellState.EMPTY) {
                valid = false;
            }
            cellsInBounds.add(new int[]{r, c});
        }

        for (int[] rc : cellsInBounds) {
            StackPane pane = cellPanes[rc[0]][rc[1]];
            pane.getChildren().add(ShipShapeFactory.createGhostCell(valid));
            ghostPanes.add(pane);
        }
    }

    /**
     * Removes the current ghost ship preview from the board.
     * All temporary graphical nodes associated with the preview
     * are deleted from their corresponding cells.
     */
    private void clearGhostShip() {
        for (StackPane pane : ghostPanes) {
            if (!pane.getChildren().isEmpty()) {
                pane.getChildren().remove(pane.getChildren().size() - 1);
            }
        }
        ghostPanes.clear();
    }

    /**
     * Updates the label that indicates which ship must be placed next.
     * If all ships have already been placed, a placeholder is displayed.
     */
    private void updateCurrentShipLabel() {
        ShipType next = pendingShips.peek();
        currentShipLabel.setText(next == null ? "-" : translateShipType(next));
    }

    /**
     * Returns the Spanish name corresponding to the specified ship type.
     *
     * @param type the ship type to translate
     * @return the localized name of the ship type
     */
    private String translateShipType(ShipType type) {
        switch (type) {
            case AIRCRAFT_CARRIER:
                return "Portaaviones";
            case SUBMARINE:
                return "Submarino";
            case DESTROYER:
                return "Destructor";
            case FRIGATE:
                return "Fragata";
            default:
                return type.name();
        }
    }
}
