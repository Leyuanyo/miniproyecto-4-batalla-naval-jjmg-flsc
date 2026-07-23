package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;

import java.util.List;
import java.util.Random;

/**
 * Utility class responsible for randomly placing a complete fleet
 * on a game board.
 *
 * This class generates random positions and orientations for every
 * ship until all of them have been successfully placed without
 * violating the placement rules of the board.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public final class RandomFleetPlacer {

    /**
     * Random number generator used to select ship positions
     * and orientations.
     */
    private static final Random RANDOM = new Random();

    /**
     * Private constructor to prevent instantiation.
     *
     * This class only contains static utility methods.
     */
    private RandomFleetPlacer() {
    }

    /**
     * Places an entire fleet on the specified board.
     *
     * The fleet is created through the {@link ShipFactory} and each
     * ship is placed in a valid random location.
     *
     * @param board the board where the fleet will be placed
     */
    public static void placeFullFleet(Board board) {
        List<Ship> fleet = ShipFactory.createFullFleet();
        for (Ship ship : fleet) {
            placeSomewhereValid(board, ship);
        }
    }

    /**
     * Attempts to place a ship in a random valid position.
     *
     * Random coordinates and orientations are generated repeatedly
     * until the ship is successfully placed on the board.
     *
     * @param board the board where the ship will be placed
     * @param ship the ship to place
     */
    private static void placeSomewhereValid(Board board, Ship ship) {
        boolean placed = false;
        while (!placed) {
            int row = RANDOM.nextInt(Board.SIZE);
            int column = RANDOM.nextInt(Board.SIZE);
            Orientation orientation = RANDOM.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
            try {
                board.placeShip(ship, row, column, orientation);
                placed = true;
            } catch (InvalidShipPlacementException e) {
                // Invalid spot: retry with another random position.
            }
        }
    }
}