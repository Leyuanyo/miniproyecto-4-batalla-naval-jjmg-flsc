package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;

/**
 * Defines the operations required for placing ships
 * on a Battleship game board.
 *
 * Classes implementing this interface are responsible
 * for validating ship placement according to the game
 * rules and updating the board state accordingly.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */

public interface Placeable {

    /**
     * Places a ship on the board using the specified
     * starting position and orientation.
     *
     * @param ship the ship to be placed
     * @param row the starting row of the ship
     * @param column the starting column of the ship
     * @param orientation the orientation in which the ship
     *                    will be placed
     * @throws InvalidShipPlacementException if the placement
     *         is outside the board or overlaps another ship
     */

    void placeShip(Ship ship, int row, int column, Orientation orientation) throws InvalidShipPlacementException;
}