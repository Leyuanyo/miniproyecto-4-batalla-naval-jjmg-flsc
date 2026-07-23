package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.ships.AircraftCarrier;
import com.example.miniproyecto_batalla_naval.model.ships.Destroyer;
import com.example.miniproyecto_batalla_naval.model.ships.Frigate;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import com.example.miniproyecto_batalla_naval.model.ships.Submarine;

import java.util.ArrayList;
import java.util.List;

public final class ShipFactory {

    private ShipFactory() {
    }

    public static Ship create(ShipType type) {
        switch (type) {
            case AIRCRAFT_CARRIER:
                return new AircraftCarrier();
            case SUBMARINE:
                return new Submarine();
            case DESTROYER:
                return new Destroyer();
            case FRIGATE:
                return new Frigate();
            default:
                throw new IllegalArgumentException("Unknown ship type: " + type);
        }
    }

    public static List<Ship> createFullFleet() {
        List<Ship> fleet = new ArrayList<>();
        fleet.add(create(ShipType.AIRCRAFT_CARRIER));
        for (int i = 0; i < 2; i++) {
            fleet.add(create(ShipType.SUBMARINE));
        }
        for (int i = 0; i < 3; i++) {
            fleet.add(create(ShipType.DESTROYER));
        }
        for (int i = 0; i < 4; i++) {
            fleet.add(create(ShipType.FRIGATE));
        }
        return fleet;
    }
}
