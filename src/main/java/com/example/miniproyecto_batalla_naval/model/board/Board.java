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

public class Board implements Serializable, Placeable, Shootable {
    private static final long serialVersionUID = 1L;
    public static final int SIZE = 10;
    private final Cell[][] grid;
    private final List<Ship> fleet;
    private final Map<String, Ship> shipsById;
    private transient List<BoardListener> listeners;

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

    public void addListener(BoardListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    private void notifyListeners(Cell cell) {
        if (listeners == null) return;
        for (BoardListener listener : listeners) {
            listener.onCellChanged(cell);
        }
    }

    public Cell getCell(int row, int column) {
        return grid[row][column];
    }

    public Ship getShipById(String id) {
        return shipsById.get(id);
    }

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

    public boolean isFleetSunk() {
        for (Ship ship : fleet) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public List<Ship> getFleet() {
        return fleet;
    }
}