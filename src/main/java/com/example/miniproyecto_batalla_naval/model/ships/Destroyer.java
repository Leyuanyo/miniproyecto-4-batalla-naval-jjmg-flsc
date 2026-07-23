package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Represents a destroyer in the Battleship game.
 *
 * The destroyer is a medium-sized ship that inherits all
 * common ship behavior from {@link Ship}. Its characteristics,
 * such as size and type, are defined by
 * {@link ShipType#DESTROYER}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Destroyer extends Ship {

    /** Serialization identifier for the destroyer class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new destroyer.
     *
     * The ship is initialized with the
     * {@link ShipType#DESTROYER} type.
     */
    public Destroyer() {
        super(ShipType.DESTROYER);
    }
}