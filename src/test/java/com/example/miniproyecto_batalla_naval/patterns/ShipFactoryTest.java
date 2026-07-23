package com.example.miniproyecto_batalla_naval.patterns;

import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link ShipFactory} class.
 *
 * This test suite verifies that the factory correctly creates
 * ships of the requested type and generates a complete fleet
 * with the expected number and distribution of ships.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class ShipFactoryTest {

    /**
     * Verifies that the factory creates a ship with
     * the requested type and the correct size.
     */
    @Test
    void createReturnsShipOfRequestedType() {
        Ship ship = ShipFactory.create(ShipType.SUBMARINE);

        assertEquals(ShipType.SUBMARINE, ship.getType());
        assertEquals(3, ship.getSize());
    }

    /**
     * Verifies that the complete fleet contains
     * the expected number of ships and the correct
     * composition for each ship type.
     */
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