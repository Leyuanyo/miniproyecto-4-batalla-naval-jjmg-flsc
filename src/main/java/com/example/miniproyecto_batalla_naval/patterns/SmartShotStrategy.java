package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Shot strategy that combines random targeting with
 * adjacent-cell exploration after a successful hit.
 *
 * This implementation improves over a purely random strategy
 * by storing neighboring cells of successful hits and giving
 * them priority in future shots. If no candidate cells remain,
 * the strategy falls back to selecting a random valid target.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class SmartShotStrategy implements ShotStrategy {

    /** Queue containing candidate cells to prioritize. */
    private final Queue<Cell> candidates = new LinkedList<>();

    /** Random number generator used when no candidates exist. */
    private final Random random = new Random();

    /**
     * Selects the next cell to shoot.
     *
     * The strategy first attempts to use a previously stored
     * candidate cell. If none are available, a random valid
     * target is selected.
     *
     * @param enemyBoard the opponent's board
     * @return the selected target cell
     */
    @Override
    public Cell nextShot(Board enemyBoard) {
        Cell target = pollValidCandidate();
        return target != null ? target : randomShot(enemyBoard);
    }

    /**
     * Registers a successful hit and stores adjacent cells
     * as future shooting candidates.
     *
     * @param enemyBoard the opponent's board
     * @param hitCell the cell where the successful hit occurred
     */
    @Override
    public void registerHit(Board enemyBoard, Cell hitCell) {
        int row = hitCell.getRow();
        int column = hitCell.getColumn();
        addIfValid(enemyBoard, row - 1, column);
        addIfValid(enemyBoard, row + 1, column);
        addIfValid(enemyBoard, row, column - 1);
        addIfValid(enemyBoard, row, column + 1);
    }

    /**
     * Adds a neighboring cell to the candidate queue if it
     * belongs to the board and has not been shot before.
     *
     * @param board the opponent's board
     * @param row the row of the candidate cell
     * @param column the column of the candidate cell
     */
    private void addIfValid(Board board, int row, int column) {
        if (row < 0 || row >= Board.SIZE || column < 0 || column >= Board.SIZE) {
            return;
        }
        Cell cell = board.getCell(row, column);
        if (isShootable(cell)) {
            candidates.add(cell);
        }
    }

    /**
     * Retrieves the next valid candidate cell from the queue.
     *
     * Cells that have already been shot are discarded until
     * a valid candidate is found or the queue becomes empty.
     *
     * @return the next valid candidate, or {@code null} if none exist
     */
    private Cell pollValidCandidate() {
        while (!candidates.isEmpty()) {
            Cell candidate = candidates.poll();
            if (isShootable(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Selects a random valid target from the board.
     *
     * Random positions are generated until an unshot cell
     * is found.
     *
     * @param board the opponent's board
     * @return a valid random target
     */
    private Cell randomShot(Board board) {
        Cell candidate;
        do {
            int row = random.nextInt(Board.SIZE);
            int column = random.nextInt(Board.SIZE);
            candidate = board.getCell(row, column);
        } while (!isShootable(candidate));
        return candidate;
    }

    /**
     * Determines whether a cell can be targeted.
     *
     * A cell is considered shootable if it has not been
     * previously marked as water, hit, or sunk.
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