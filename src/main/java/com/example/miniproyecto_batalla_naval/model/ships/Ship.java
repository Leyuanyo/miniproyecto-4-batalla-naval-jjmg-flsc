package com.example.miniproyecto_batalla_naval.model.ships;

import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic ship in the Battleship game.
 *
 * This abstract class defines the common properties and
 * behavior shared by every ship type, including its size,
 * orientation, occupied cells, and hit registration.
 * Specific ship classes extend this class by providing
 * their corresponding {@link ShipType}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public abstract class Ship implements Serializable {

    /** Serialization identifier for the ship class. */
    private static final long serialVersionUID = 1L;

    /** Type that defines the characteristics of the ship. */
    private final ShipType type;

    /** Current orientation of the ship on the board. */
    private Orientation orientation;

    /** Cells currently occupied by the ship. */
    private final List<Cell> occupiedCells;

    /** Number of successful hits received by the ship. */
    private int hits;

    /**
     * Creates a ship of the specified type.
     *
     * The ship is initialized with a horizontal orientation,
     * no occupied cells, and zero registered hits.
     *
     * @param type the type of ship to create
     */
    protected Ship(ShipType type) {
        this.type = type;
        this.orientation = Orientation.HORIZONTAL;
        this.occupiedCells = new ArrayList<>();
        this.hits = 0;
    }

    /**
     * Returns the type of this ship.
     *
     * @return the ship type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * Returns the size of the ship.
     *
     * The size is obtained from the associated
     * {@link ShipType}.
     *
     * @return the number of cells occupied by the ship
     */
    public int getSize() {
        return type.getSize();
    }

    /**
     * Returns the current orientation of the ship.
     *
     * @return the ship orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Updates the orientation of the ship.
     *
     * @param orientation the new orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the list of cells occupied by the ship.
     *
     * @return the occupied cells
     */
    public List<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    /**
     * Assigns a board cell to this ship.
     *
     * The specified cell becomes part of the ship,
     * stores a reference to it, and changes its
     * state to {@link CellState#SHIP}.
     *
     * @param cell the cell to occupy
     */
    public void occupy(Cell cell) {
        occupiedCells.add(cell);
        cell.setShip(this);
        cell.setState(CellState.SHIP);
    }

    /**
     * Registers a successful hit on the ship.
     *
     * Each invocation increases the number of
     * hits received by one.
     */
    public void registerHit() {
        hits++;
    }

    /**
     * Determines whether the ship has been sunk.
     *
     * A ship is considered sunk when the number
     * of registered hits is equal to or greater
     * than its size.
     *
     * @return {@code true} if the ship has been sunk;
     *         {@code false} otherwise
     */
    public boolean isSunk() {
        return hits >= getSize();
    }
}