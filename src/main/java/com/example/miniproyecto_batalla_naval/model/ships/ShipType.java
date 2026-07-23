package com.example.miniproyecto_batalla_naval.model.ships;

/**
 * Enumerates the different types of ships available
 * in the Battleship game.
 *
 * Each ship type defines the number of board cells
 * it occupies, allowing the game to determine ship
 * placement, hit detection, and sinking conditions.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public enum ShipType {

    /**
     * Aircraft carrier occupying four consecutive cells.
     */
    AIRCRAFT_CARRIER(4),

    /**
     * Submarine occupying three consecutive cells.
     */
    SUBMARINE(3),

    /**
     * Destroyer occupying two consecutive cells.
     */
    DESTROYER(2),

    /**
     * Frigate occupying a single cell.
     */
    FRIGATE(1);

    /** Number of cells occupied by the ship type. */
    private final int size;

    /**
     * Creates a ship type with the specified size.
     *
     * @param size the number of cells occupied by the ship
     */
    ShipType(int size) {
        this.size = size;
    }

    /**
     * Returns the size of this ship type.
     *
     * @return the number of cells occupied by the ship
     */
    public int getSize() {
        return size;
    }
}