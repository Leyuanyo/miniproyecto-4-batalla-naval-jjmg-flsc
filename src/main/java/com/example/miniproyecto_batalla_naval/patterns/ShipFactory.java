package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.ships.AircraftCarrier;
import com.example.miniproyecto_batalla_naval.model.ships.Destroyer;
import com.example.miniproyecto_batalla_naval.model.ships.Frigate;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import com.example.miniproyecto_batalla_naval.model.ships.Submarine;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class responsible for creating ships used in the
 * Battleship game.
 *
 * This utility class centralizes the creation of individual
 * ship instances and complete fleets, following the Factory
 * design pattern. It cannot be instantiated.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public final class ShipFactory {

    /**
     * Prevents instantiation of this utility class.
     */
    private ShipFactory() {
    }

    /**
     * Creates a ship corresponding to the specified type.
     *
     * @param type the type of ship to create
     * @return a new ship instance of the requested type
     * @throws IllegalArgumentException if the ship type is unknown
     */
    public static Ship create(ShipType type) {
        switch (type) {
            case AIRCRAFT_CARRIER:
                return new AircraftCarrier();
            case SUBMARINE:
                return new Submarine();
            case DESTROYER:
                return new Destroyer();
            case FRIGATE:
                return new Frigate();
            default:
                throw new IllegalArgumentException("Unknown ship type: " + type);
        }
    }

    /**
     * Creates the complete fleet used in a Battleship match.
     *
     * The generated fleet contains one aircraft carrier,
     * two submarines, three destroyers, and four frigates.
     *
     * @return a list containing the complete fleet
     */
    public static List<Ship> createFullFleet() {
        List<Ship> fleet = new ArrayList<>();
        fleet.add(create(ShipType.AIRCRAFT_CARRIER));
        for (int i = 0; i < 2; i++) {
            fleet.add(create(ShipType.SUBMARINE));
        }
        for (int i = 0; i < 3; i++) {
            fleet.add(create(ShipType.DESTROYER));
        }
        for (int i = 0; i < 4; i++) {
            fleet.add(create(ShipType.FRIGATE));
        }
        return fleet;
    }
}