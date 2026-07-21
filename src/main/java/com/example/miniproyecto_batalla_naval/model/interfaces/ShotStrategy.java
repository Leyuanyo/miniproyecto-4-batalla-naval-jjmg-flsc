package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.Board;
import com.example.miniproyecto_batalla_naval.model.Cell;

public interface ShotStrategy {
    Cell nextShot(Board enemyBoard);

    default void registerHit(Board enemyBoard, Cell hitCell) {
    }
}