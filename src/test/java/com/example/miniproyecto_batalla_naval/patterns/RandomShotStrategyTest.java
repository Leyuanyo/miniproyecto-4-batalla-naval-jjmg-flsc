package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class RandomShotStrategyTest {

    @Test
    void nextShotReturnsAValidCell() {
        Board board = new Board();
        RandomShotStrategy strategy = new RandomShotStrategy();

        Cell cell = strategy.nextShot(board);

        assertNotNull(cell);
    }

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
