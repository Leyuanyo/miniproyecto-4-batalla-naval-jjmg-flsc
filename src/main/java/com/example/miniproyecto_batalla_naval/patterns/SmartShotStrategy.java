package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SmartShotStrategy implements ShotStrategy {
    private final Queue<Cell> candidates = new LinkedList<>();
    private final Random random = new Random();

    @Override
    public Cell nextShot(Board enemyBoard) {
        Cell target = pollValidCandidate();
        return target != null ? target : randomShot(enemyBoard);
    }

    @Override
    public void registerHit(Board enemyBoard, Cell hitCell) {
        int row = hitCell.getRow();
        int column = hitCell.getColumn();
        addIfValid(enemyBoard, row - 1, column);
        addIfValid(enemyBoard, row + 1, column);
        addIfValid(enemyBoard, row, column - 1);
        addIfValid(enemyBoard, row, column + 1);
    }

    private void addIfValid(Board board, int row, int column) {
        if (row < 0 || row >= Board.SIZE || column < 0 || column >= Board.SIZE) {
            return;
        }
        Cell cell = board.getCell(row, column);
        if (isShootable(cell)) {
            candidates.add(cell);
        }
    }

    private Cell pollValidCandidate() {
        while (!candidates.isEmpty()) {
            Cell candidate = candidates.poll();
            if (isShootable(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private Cell randomShot(Board board) {
        Cell candidate;
        do {
            int row = random.nextInt(Board.SIZE);
            int column = random.nextInt(Board.SIZE);
            candidate = board.getCell(row, column);
        } while (!isShootable(candidate));
        return candidate;
    }

    private boolean isShootable(Cell cell) {
        CellState state = cell.getState();
        return state == CellState.EMPTY || state == CellState.SHIP;
    }
}
