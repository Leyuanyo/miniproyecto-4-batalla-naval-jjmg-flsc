package com.example.miniproyecto_batalla_naval.model.player;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;
import com.example.miniproyecto_batalla_naval.patterns.SmartShotStrategy;

import java.io.Serializable;

/**
 * Represents the computer-controlled player in the Battleship game.
 *
 * The machine uses a configurable {@link ShotStrategy} to determine
 * which cell to target on each turn. Different strategies can be
 * assigned without modifying the machine's behavior, following the
 * Strategy design pattern.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Machine extends Player implements Serializable {

    /** Serialization identifier for the machine player. */
    private static final long serialVersionUID = 1L;

    /**
     * Strategy used by the machine to select its next shot.
     * This field is transient because strategies are recreated
     * after deserialization if necessary.
     */
    private transient ShotStrategy shotStrategy;

    /**
     * Creates a machine player using the specified shot strategy.
     *
     * @param shotStrategy the strategy that will determine the
     *                     machine's shot selection
     */
    public Machine(ShotStrategy shotStrategy) {
        super("Maquina");
        this.shotStrategy = shotStrategy;
    }

    /**
     * Changes the strategy used by the machine to choose
     * future shots.
     *
     * @param shotStrategy the new shooting strategy
     */
    public void setShotStrategy(ShotStrategy shotStrategy) {
        this.shotStrategy = shotStrategy;
    }

    /**
     * Selects the next target cell on the opponent's board.
     *
     * If no strategy is currently assigned, a
     * {@link SmartShotStrategy} is created automatically.
     *
     * @param enemyBoard the opponent's board
     * @return the cell selected as the next target
     */
    public Cell chooseNextShot(Board enemyBoard) {
        if (this.shotStrategy == null) {
            this.shotStrategy = new SmartShotStrategy();
        }
        return shotStrategy.nextShot(enemyBoard);
    }

    /**
     * Notifies the current strategy about the result of the
     * machine's most recent shot.
     *
     * The strategy is informed only when the shot produces
     * a hit or sinks a ship, allowing it to adapt future
     * decisions if necessary.
     *
     * @param enemyBoard the opponent's board
     * @param shotCell the cell that was targeted
     * @param result the outcome of the shot
     */
    public void notifyShotResult(Board enemyBoard, Cell shotCell, ShotResult result) {
        if (shotStrategy != null && (result == ShotResult.HIT || result == ShotResult.SUNK)) {
            shotStrategy.registerHit(enemyBoard, shotCell);
        }
    }
}