package com.example.miniproyecto_batalla_naval.exceptions;

/**
 * Exception thrown when a player attempts to shoot
 * a board cell that has already been targeted during
 * the current match.
 *
 * This exception is used to prevent repeated shots
 * on the same position and to notify the controller
 * that the selected cell is no longer available.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class CellAlreadyShotException extends RuntimeException {

    /**
     * Creates a new exception with the specified detail message.
     *
     * @param message the detail message describing the reason
     *                why the exception was thrown
     */
    public CellAlreadyShotException(String message) {
        super(message);
    }
}