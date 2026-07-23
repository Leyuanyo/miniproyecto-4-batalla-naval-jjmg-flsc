package com.example.miniproyecto_batalla_naval.controller.adapter;

/**
 * Defines the contract for handling ship rotation requests.
 * Implementations of this interface are responsible for performing
 * the necessary actions to change the orientation of the ship being
 * placed or manipulated in the Battleship game.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface RotationListener {

    /**
     * Invoked when a ship rotation is requested.
     * This method is typically triggered by a keyboard event,
     * such as pressing the Space key during the ship placement phase.
     */
    void onRotateRequested();

}