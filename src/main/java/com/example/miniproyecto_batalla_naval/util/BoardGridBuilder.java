package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionAdapter;
import com.example.miniproyecto_batalla_naval.controller.adapter.CellInteractionListener;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Utility class responsible for constructing the graphical
 * representation of a Battleship board.
 *
 * This class dynamically creates the JavaFX nodes that compose
 * a board, associates each cell with its corresponding click
 * listener, and returns a matrix that provides direct access
 * to every visual cell.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public final class BoardGridBuilder {

    /**
     * Private constructor to prevent instantiation.
     *
     * This class only provides static utility methods.
     */
    private BoardGridBuilder() {
    }

    /**
     * Builds a complete graphical board inside the given
     * {@link GridPane}.
     *
     * Each board position is represented by a {@link StackPane}
     * containing a colored background rectangle. A
     * {@link CellInteractionAdapter} is attached to every cell
     * so user clicks are forwarded to the supplied listener.
     *
     * Before creating the new board, any existing content inside
     * the {@code GridPane} is removed.
     *
     * @param gridPane the JavaFX grid where the board will be created
     * @param listener the listener that receives mouse interactions
     *                 performed on each cell
     *
     * @return a two-dimensional array containing references to every
     *         created {@link StackPane}, indexed by row and column
     */
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
