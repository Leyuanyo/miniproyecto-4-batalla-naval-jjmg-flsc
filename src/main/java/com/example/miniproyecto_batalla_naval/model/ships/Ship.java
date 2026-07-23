package com.example.miniproyecto_batalla_naval.model.ships;

import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.CellState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Ship implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ShipType type;
    private Orientation orientation;
    private final List<Cell> occupiedCells;
    private int hits;

    protected Ship(ShipType type) {
        this.type = type;
        this.orientation = Orientation.HORIZONTAL;
        this.occupiedCells = new ArrayList<>();
        this.hits = 0;
    }

    public ShipType getType() {
        return type;
    }

    public int getSize() {
        return type.getSize();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public List<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    public void occupy(Cell cell) {
        occupiedCells.add(cell);
        cell.setShip(this);
        cell.setState(CellState.SHIP);
    }

    public void registerHit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= getSize();
    }
}