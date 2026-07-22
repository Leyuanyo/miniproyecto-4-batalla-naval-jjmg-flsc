package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipFactoryTest {

    @Test
    void createReturnsShipOfRequestedType() {
        Ship ship = ShipFactory.create(ShipType.SUBMARINE);

        assertEquals(ShipType.SUBMARINE, ship.getType());
        assertEquals(3, ship.getSize());
    }

    @Test
    void fullFleetHasTenShipsWithCorrectComposition() {
        List<Ship> fleet = ShipFactory.createFullFleet();

        assertEquals(10, fleet.size());

        Map<ShipType, Long> countByType = fleet.stream()
                .collect(Collectors.groupingBy(Ship::getType, Collectors.counting()));

        assertEquals(1L, countByType.get(ShipType.AIRCRAFT_CARRIER));
        assertEquals(2L, countByType.get(ShipType.SUBMARINE));
        assertEquals(3L, countByType.get(ShipType.DESTROYER));
        assertEquals(4L, countByType.get(ShipType.FRIGATE));
    }
}
