package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SmartShotStrategyTest {

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
