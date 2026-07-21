package com.example.miniproyecto_batalla_naval.util;

import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;

import java.util.List;
import java.util.Random;

public final class RandomFleetPlacer {

    private static final Random RANDOM = new Random();

    private RandomFleetPlacer() {
    }

    public static void placeFullFleet(Board board) {
        List<Ship> fleet = ShipFactory.createFullFleet();
        for (Ship ship : fleet) {
            placeSomewhereValid(board, ship);
        }
    }

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
