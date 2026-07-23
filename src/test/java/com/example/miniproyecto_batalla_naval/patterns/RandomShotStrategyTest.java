package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Unit tests for the {@link RandomShotStrategy} class.
 *
 * This test suite verifies that the random shot strategy
 * always returns valid target cells and avoids selecting
 * cells that have already been shot.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class RandomShotStrategyTest {

    /**
     * Verifies that the strategy always returns
     * a valid board cell when selecting the next shot.
     */
    @Test
    void nextShotReturnsAValidCell() {
        Board board = new Board();
        RandomShotStrategy strategy = new RandomShotStrategy();

        Cell cell = strategy.nextShot(board);

        assertNotNull(cell);
    }

    /**
     * Verifies that the strategy never selects a cell
     * that has already been targeted by a previous shot.
     */
    @Test
    void nextShotNeverReturnsAnAlreadyShotCell() {
        Board board = new Board();
        RandomShotStrategy strategy = new RandomShotStrategy();

        Cell first = strategy.nextShot(board);
        board.receiveShot(first.getRow(), first.getColumn());

        for (int i = 0; i < 20; i++) {
            Cell next = strategy.nextShot(board);
            assertNotSame(first, next);
        }
    }
}