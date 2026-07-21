package com.example.miniproyecto_batalla_naval.model.ships;

public enum ShipType {
    AIRCRAFT_CARRIER(4),
    SUBMARINE(3),
    DESTROYER(2),
    FRIGATE(1);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}