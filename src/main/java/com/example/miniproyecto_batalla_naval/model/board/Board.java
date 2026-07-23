package com.example.miniproyecto_batalla_naval.model.board;

import com.example.miniproyecto_batalla_naval.exceptions.CellAlreadyShotException;
import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.interfaces.BoardListener;
import com.example.miniproyecto_batalla_naval.model.interfaces.Placeable;
import com.example.miniproyecto_batalla_naval.model.interfaces.Shootable;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Battleship game board.
 * Stores the board cells, the fleet placed on the board,
 * and provides the operations required to place ships,
 * process shots, notify listeners, and determine whether
 * the entire fleet has been sunk.
 *
 * Implements {@link Placeable} and {@link Shootable} to
 * provide the core board mechanics used during the game.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Board implements Serializable, Placeable, Shootable {

    /** Serialization identifier for the board class. */
    private static final long serialVersionUID = 1L;

    /** Size of the square game board. */
    public static final int SIZE = 10;

    /** Matrix containing every cell of the board. */
    private final Cell[][] grid;

    /** Fleet currently placed on the board. */
    private final List<Ship> fleet;

    /** Map used to retrieve ships by their generated identifier. */
    private final Map<String, Ship> shipsById;

    /** List of listeners notified whenever a cell changes its state. */
    private transient List<BoardListener> listeners;

    /**
     * Creates an empty board.
     * Initializes every board cell, the fleet collection,
     * the ship lookup table, and the listener list.
     */
    public Board() {
        grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                grid[row][column] = new Cell(row, column);
            }
        }
        fleet = new ArrayList<>();
        shipsById = new HashMap<>();
        listeners = new ArrayList<>();
    }

    /**
     * Registers a listener that will be notified whenever
     * a board cell changes its state.
     *
     * @param listener the listener to register
     */
    public void addListener(BoardListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners that the specified
     * cell has changed its state.
     *
     * @param cell the cell whose state has changed
     */
    private void notifyListeners(Cell cell) {
        if (listeners == null) return;
        for (BoardListener listener : listeners) {
            listener.onCellChanged(cell);
        }
    }

    /**
     * Returns the cell located at the specified position.
     *
     * @param row the row index
     * @param column the column index
     * @return the requested board cell
     */
    public Cell getCell(int row, int column) {
        return grid[row][column];
    }

    /**
     * Returns the ship associated with the specified identifier.
     *
     * @param id the unique ship identifier
     * @return the corresponding ship, or {@code null} if it does not exist
     */
    public Ship getShipById(String id) {
        return shipsById.get(id);
    }

    /**
     * {@inheritDoc}
     * Places a ship on the board after validating that the
     * requested position is completely inside the board and
     * does not overlap another ship.
     *
     * @param ship the ship to place
     * @param row the starting row
     * @param column the starting column
     * @param orientation the desired ship orientation
     * @throws InvalidShipPlacementException if the placement is invalid
     */
    @Override
    public void placeShip(Ship ship, int row, int column, Orientation orientation) throws InvalidShipPlacementException {
        List<Cell> target = new ArrayList<>();
        int size = ship.getSize();
        for (int i = 0; i < size; i++) {
            int r = row + (orientation == Orientation.VERTICAL ? i : 0);
            int c = column + (orientation == Orientation.HORIZONTAL ? i : 0);
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
                throw new InvalidShipPlacementException("El barco se sale del tablero.");
            }
            if (grid[r][c].getState() != CellState.EMPTY) {
                throw new InvalidShipPlacementException("El barco se superpone con otro.");
            }
            target.add(grid[r][c]);
        }
        ship.setOrientation(orientation);
        for (Cell cell : target) {
            ship.occupy(cell);
            notifyListeners(cell);
        }
        fleet.add(ship);
        shipsById.put(ship.getType().name() + "-" + fleet.size(), ship);
    }

    /**
     * {@inheritDoc}
     * Processes a shot on the specified board position,
     * updates the affected cell and ship states, notifies
     * listeners, and returns the resulting shot outcome.
     *
     * @param row the targeted row
     * @param column the targeted column
     * @return the result produced by the shot
     * @throws CellAlreadyShotException if the selected cell has already been shot
     */
    @Override
    public ShotResult receiveShot(int row, int column) throws CellAlreadyShotException {
        Cell cell = grid[row][column];
        CellState state = cell.getState();
        if (state == CellState.WATER || state == CellState.HIT || state == CellState.SUNK) {
            throw new CellAlreadyShotException("Cell (" + row + ", " + column + ") was already shot.");
        }
        if (state == CellState.EMPTY) {
            cell.setState(CellState.WATER);
            notifyListeners(cell);
            return ShotResult.WATER;
        }
        Ship ship = cell.getShip();
        ship.registerHit();
        cell.setState(CellState.HIT);
        notifyListeners(cell);

        if (ship.isSunk()) {
            for (Cell shipCell : ship.getOccupiedCells()) {
                shipCell.setState(CellState.SUNK);
                notifyListeners(shipCell);
            }
            return isFleetSunk() ? ShotResult.GAME_OVER : ShotResult.SUNK;
        }
        return ShotResult.HIT;
    }

    /**
     * Determines whether every ship on the board
     * has been completely sunk.
     *
     * @return {@code true} if all ships are sunk;
     *         {@code false} otherwise
     */
    public boolean isFleetSunk() {
        if (fleet.isEmpty()) {
            return false;
        }

        for (Ship ship : fleet) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the fleet currently placed on the board.
     *
     * @return the list of ships belonging to the board
     */
    public List<Ship> getFleet() {
        return fleet;
    }
}