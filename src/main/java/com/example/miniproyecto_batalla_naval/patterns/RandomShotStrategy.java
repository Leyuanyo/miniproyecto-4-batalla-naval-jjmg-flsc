package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;

import java.util.Random;

/**
 * Shot strategy that selects valid targets randomly.
 *
 * This strategy chooses random cells from the opponent's board
 * until it finds one that has not been shot previously. It is
 * intended as a simple implementation of the
 * {@link ShotStrategy} interface.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class RandomShotStrategy implements ShotStrategy {

    /** Random number generator used to select board positions. */
    private final Random random = new Random();

    /**
     * Selects the next cell to shoot.
     *
     * Random board positions are generated until a valid,
     * shootable cell is found.
     *
     * @param enemyBoard the opponent's board
     * @return the selected target cell
     */
    @Override
    public Cell nextShot(Board enemyBoard) {
        Cell candidate;
        do {
            int row = random.nextInt(Board.SIZE);
            int column = random.nextInt(Board.SIZE);
            candidate = enemyBoard.getCell(row, column);
        } while (!isShootable(candidate));
        return candidate;
    }

    /**
     * Determines whether a cell can be targeted.
     *
     * A cell is considered shootable only if it has not
     * been previously hit or marked as water.
     *
     * @param cell the cell to evaluate
     * @return {@code true} if the cell can be shot;
     *         {@code false} otherwise
     */
    private boolean isShootable(Cell cell) {
        CellState state = cell.getState();
        return state == CellState.EMPTY || state == CellState.SHIP;
    }
}