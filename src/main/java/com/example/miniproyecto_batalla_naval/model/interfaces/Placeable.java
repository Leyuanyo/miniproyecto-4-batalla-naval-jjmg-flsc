package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.Orientation;
import com.example.miniproyecto_batalla_naval.model.Ship;

public interface Placeable {
    void placeShip(Ship ship, int row, int column, Orientation orientation) throws InvalidShipPlacementException;
}