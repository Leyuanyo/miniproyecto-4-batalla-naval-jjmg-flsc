package com.example.miniproyecto_batalla_naval.exceptions;

/**
 * Exception thrown when an attempt is made to place
 * a ship in an invalid position on the board.
 *
 * This exception is generated whenever the proposed
 * placement violates the game rules, such as placing
 * a ship outside the board boundaries or overlapping
 * another previously placed ship.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class InvalidShipPlacementException extends Exception {

    /**
     * Creates a new exception with the specified detail message.
     *
     * @param message the detail message describing the reason
     *                why the ship placement is invalid
     */
    public InvalidShipPlacementException(String message) {
        super(message);
    }
}