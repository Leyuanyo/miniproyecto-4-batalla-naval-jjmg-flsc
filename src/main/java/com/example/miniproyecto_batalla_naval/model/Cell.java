package com.example.miniproyecto_batalla_naval.model;

import java.io.Serializable;

public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int row;
    private final int column;
    private CellState state;
    private Ship ship;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = CellState.EMPTY;
        this.ship = null;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}