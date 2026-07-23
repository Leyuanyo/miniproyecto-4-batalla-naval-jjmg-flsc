package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link SmartShotStrategy} class.
 *
 * This test suite verifies that the smart shooting strategy
 * correctly prioritizes adjacent cells after a successful hit,
 * improving the probability of finding and sinking enemy ships.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class SmartShotStrategyTest {

    /**
     * Verifies that after registering a successful hit,
     * the next selected shot corresponds to one of the
     * adjacent cells surrounding the hit position.
     */
    @Test
    void afterAHitTheNextShotTargetsAnAdjacentCell() {
        Board board = new Board();
        SmartShotStrategy strategy = new SmartShotStrategy();
        Cell hitCell = board.getCell(5, 5);

        strategy.registerHit(board, hitCell);
        Cell next = strategy.nextShot(board);

        Set<Cell> neighbors = Set.of(
                board.getCell(4, 5),
                board.getCell(6, 5),
                board.getCell(5, 4),
                board.getCell(5, 6)
        );

        assertTrue(neighbors.contains(next));
    }
}