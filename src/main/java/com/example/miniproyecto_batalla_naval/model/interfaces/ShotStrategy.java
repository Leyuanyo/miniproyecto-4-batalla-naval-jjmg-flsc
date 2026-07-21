package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;

public interface ShotStrategy {
    Cell nextShot(Board enemyBoard);

    default void registerHit(Board enemyBoard, Cell hitCell) {
    }
}