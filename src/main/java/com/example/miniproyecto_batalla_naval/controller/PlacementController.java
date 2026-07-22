package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionListener;
import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
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

import java.util.LinkedList;
import java.util.Queue;

public class PlacementController implements CellInteractionListener {

    @FXML
    private GridPane positionBoardGrid;

    @FXML
    private Label currentShipLabel;

    @FXML
    private Label instructionsLabel;

    @FXML
    private Button startGameButton;

    private String nickname;
    private Board board;
    private StackPane[][] cellPanes;
    private final Queue<ShipType> pendingShips = new LinkedList<>();
    private Orientation currentOrientation = Orientation.HORIZONTAL;

    public void startNewGame(String nickname) {
        this.nickname = nickname;
        this.board = new Board();
        loadPendingShips();
        cellPanes = BoardGridBuilder.build(positionBoardGrid, this);
        updateCurrentShipLabel();
        startGameButton.setDisable(true);
        instructionsLabel.setText("Click izquierdo: coloca. Click derecho: rota. Empieza por el portaaviones.");
    }

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

    @FXML
    private void handleRotate() {
        currentOrientation = currentOrientation == Orientation.HORIZONTAL
                ? Orientation.VERTICAL
                : Orientation.HORIZONTAL;
    }

    @FXML
    private void handleStartGame() {
        Machine machine = new Machine(new SmartShotStrategy());
        RandomFleetPlacer.placeFullFleet(machine.getBoard());

        Player human = new Player(nickname, board);
        GameModel model = new GameModel(human, machine);

        GameStage.goToGame(model);
    }

    @Override
    public void onCellLeftClick(int row, int column) {
        placeCurrentShip(row, column);
    }

    @Override
    public void onCellRightClick(int row, int column) {
        handleRotate();
    }

    private void placeCurrentShip(int row, int column) {
        ShipType nextType = pendingShips.peek();
        if (nextType == null) {
            return;
        }
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

    private void updateCurrentShipLabel() {
        ShipType next = pendingShips.peek();
        currentShipLabel.setText(next == null ? "-" : next.name());
    }
}
