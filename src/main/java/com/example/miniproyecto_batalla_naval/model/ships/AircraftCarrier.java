package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Represents an aircraft carrier in the Battleship game.
 *
 * The aircraft carrier is the largest ship in the fleet and
 * inherits all common ship behavior from {@link Ship}. Its
 * characteristics, such as size and type, are defined by
 * {@link ShipType#AIRCRAFT_CARRIER}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class AircraftCarrier extends Ship {

    /** Serialization identifier for the aircraft carrier class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new aircraft carrier.
     *
     * The ship is initialized with the
     * {@link ShipType#AIRCRAFT_CARRIER} type.
     */
    public AircraftCarrier() {
        super(ShipType.AIRCRAFT_CARRIER);
    }
}