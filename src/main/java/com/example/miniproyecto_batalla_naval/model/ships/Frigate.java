package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Represents a frigate in the Battleship game.
 *
 * The frigate is the smallest ship in the fleet and
 * inherits all common ship behavior from {@link Ship}.
 * Its characteristics, such as size and type, are defined
 * by {@link ShipType#FRIGATE}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Frigate extends Ship {

    /** Serialization identifier for the frigate class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new frigate.
     *
     * The ship is initialized with the
     * {@link ShipType#FRIGATE} type.
     */
    public Frigate() {
        super(ShipType.FRIGATE);
    }
}