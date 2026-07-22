package com.example.miniproyecto_batalla_naval.model.player;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;
import com.example.miniproyecto_batalla_naval.patterns.SmartShotStrategy;

import java.io.Serializable;

public class Machine extends Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient ShotStrategy shotStrategy;

    public Machine(ShotStrategy shotStrategy) {
        super("Maquina");
        this.shotStrategy = shotStrategy;
    }

    public void setShotStrategy(ShotStrategy shotStrategy) {
        this.shotStrategy = shotStrategy;
    }

    public Cell chooseNextShot(Board enemyBoard) {
        if (this.shotStrategy == null) {
            this.shotStrategy = new SmartShotStrategy();
        }
        return shotStrategy.nextShot(enemyBoard);
    }

    public void notifyShotResult(Board enemyBoard, Cell shotCell, ShotResult result) {
        if (shotStrategy != null && (result == ShotResult.HIT || result == ShotResult.SUNK)) {
            shotStrategy.registerHit(enemyBoard, shotCell);
        }
    }
}