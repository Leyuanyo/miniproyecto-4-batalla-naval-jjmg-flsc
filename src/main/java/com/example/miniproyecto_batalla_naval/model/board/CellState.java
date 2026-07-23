package com.example.miniproyecto_batalla_naval.model.board;

/**
 * Represents the possible states of a cell on the Battleship board.
 *
 * Each constant describes the current condition of a board cell
 * throughout the game, including whether it is empty, occupied,
 * or has already been targeted by a shot.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public enum CellState {

    /**
     * Indicates that the cell is empty and has not been occupied
     * or targeted by any shot.
     */
    EMPTY,

    /**
     * Indicates that the cell is occupied by a ship
     * that has not yet been hit.
     */
    SHIP,

    /**
     * Indicates that a shot was fired at this cell
     * but no ship was present.
     */
    WATER,

    /**
     * Indicates that the cell contains a ship segment
     * that has been successfully hit.
     */
    HIT,

    /**
     * Indicates that the ship occupying this cell
     * has been completely sunk.
     */
    SUNK
}