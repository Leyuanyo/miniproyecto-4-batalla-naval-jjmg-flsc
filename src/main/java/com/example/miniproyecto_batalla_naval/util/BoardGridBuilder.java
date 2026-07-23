package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionAdapter;
import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionListener;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class BoardGridBuilder {

    private BoardGridBuilder() {
    }

    public static StackPane[][] build(GridPane gridPane, CellInteractionListener listener) {
        StackPane[][] cells = new StackPane[Board.SIZE][Board.SIZE];
        gridPane.getChildren().clear();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Rectangle background = new Rectangle(ShipShapeFactory.CELL_SIZE, ShipShapeFactory.CELL_SIZE);
                background.setFill(Color.AQUA);
                background.setStroke(Color.STEELBLUE);

                StackPane cellPane = new StackPane(background);
                cellPane.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new CellInteractionAdapter(row, column, listener));

                gridPane.add(cellPane, column, row);
                cells[row][column] = cellPane;
            }
        }
        return cells;
    }
}
