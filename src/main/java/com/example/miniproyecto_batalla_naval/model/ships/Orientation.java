package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Represents the possible orientations of a ship
 * when it is placed on the Battleship board.
 *
 * The orientation determines whether a ship occupies
 * consecutive cells horizontally or vertically.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public enum Orientation {

    /**
     * Indicates that the ship is placed horizontally,
     * occupying consecutive columns in the same row.
     */
    HORIZONTAL,

    /**
     * Indicates that the ship is placed vertically,
     * occupying consecutive rows in the same column.
     */
    VERTICAL
}