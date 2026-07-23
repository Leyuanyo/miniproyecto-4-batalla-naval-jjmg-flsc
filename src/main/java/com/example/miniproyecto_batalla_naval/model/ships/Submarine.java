package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Represents a submarine in the Battleship game.
 *
 * The submarine is a medium-sized ship that inherits all
 * common ship behavior from {@link Ship}. Its characteristics,
 * such as size and type, are defined by
 * {@link ShipType#SUBMARINE}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Submarine extends Ship {

    /** Serialization identifier for the submarine class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new submarine.
     *
     * The ship is initialized with the
     * {@link ShipType#SUBMARINE} type.
     */
    public Submarine() {
        super(ShipType.SUBMARINE);
    }
}