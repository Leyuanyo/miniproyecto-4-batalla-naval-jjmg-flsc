package com.example.miniproyecto_batalla_naval.model.board;

import com.example.miniproyecto_batalla_naval.model.ships.Ship;

import java.io.Serializable;

/**
 * Represents a single cell of the Battleship game board.
 * Stores its position, current state, and the ship occupying
 * the cell, if any.
 *
 * Instances of this class are used by the board to manage
 * ship placement, shot resolution, and game state updates.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Cell implements Serializable {

    /** Serialization identifier for the cell class. */
    private static final long serialVersionUID = 1L;

    /** Row index of the cell within the board. */
    private final int row;

    /** Column index of the cell within the board. */
    private final int column;

    /** Current state of the cell. */
    private CellState state;

    /** Ship occupying this cell, or {@code null} if the cell is empty. */
    private Ship ship;

    /**
     * Creates a new board cell with the specified coordinates.
     * Newly created cells are initialized as empty and without
     * an associated ship.
     *
     * @param row the row index of the cell
     * @param column the column index of the cell
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = CellState.EMPTY;
        this.ship = null;
    }

    /**
     * Returns the row where this cell is located.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column where this cell is located.
     *
     * @return the column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the current state of the cell.
     *
     * @return the cell state
     */
    public CellState getState() {
        return state;
    }

    /**
     * Updates the current state of the cell.
     *
     * @param state the new state assigned to the cell
     */
    public void setState(CellState state) {
        this.state = state;
    }

    /**
     * Returns the ship currently occupying this cell.
     *
     * @return the occupying ship, or {@code null} if none exists
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Assigns a ship to this cell.
     *
     * @param ship the ship that will occupy the cell
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
}