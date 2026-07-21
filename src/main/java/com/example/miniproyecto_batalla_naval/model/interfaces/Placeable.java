package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;

public interface Placeable {
    void placeShip(Ship ship, int row, int column, Orientation orientation) throws InvalidShipPlacementException;
}