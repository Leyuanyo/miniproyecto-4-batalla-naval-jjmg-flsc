package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;

import java.util.Random;

public class RandomShotStrategy implements ShotStrategy {
    private final Random random = new Random();

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

    private boolean isShootable(Cell cell) {
        CellState state = cell.getState();
        return state == CellState.EMPTY || state == CellState.SHIP;
    }
}
