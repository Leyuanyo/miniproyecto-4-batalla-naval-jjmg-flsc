package com.example.miniproyecto_batalla_naval.model.board;

/**
 * Represents the possible outcomes of a shot fired
 * during a Battleship match.
 *
 * These values are returned after processing a shot
 * and are used by the controllers to update the game
 * state, user interface, and turn flow.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public enum ShotResult {

    /**
     * Indicates that the shot landed on an empty cell.
     */
    WATER,

    /**
     * Indicates that the shot successfully hit
     * a ship without sinking it.
     */
    HIT,

    /**
     * Indicates that the shot sank an entire ship.
     */
    SUNK,

    /**
     * Indicates that the shot sank the last remaining
     * ship of the opponent, ending the game.
     */
    GAME_OVER
}