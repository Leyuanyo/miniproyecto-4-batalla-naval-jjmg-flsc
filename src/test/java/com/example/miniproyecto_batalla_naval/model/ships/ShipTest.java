package com.example.miniproyecto_batalla_naval.model.ships;

import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Ship} class.
 *
 * This test suite verifies the behavior of ships regarding
 * hit registration, sinking conditions, size configuration,
 * and default orientation after creation.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class ShipTest {

    /**
     * Verifies that a frigate is sunk immediately
     * after receiving its only required hit.
     */
    @Test
    void frigateSinksAfterOneHit() {
        Ship frigate = ShipFactory.create(ShipType.FRIGATE);

        frigate.registerHit();

        assertTrue(frigate.isSunk());
    }

    /**
     * Verifies that a destroyer requires two successful
     * hits before being considered sunk.
     */
    @Test
    void destroyerNeedsBothCellsHitToSink() {
        Ship destroyer = ShipFactory.create(ShipType.DESTROYER);

        destroyer.registerHit();
        assertFalse(destroyer.isSunk());

        destroyer.registerHit();
        assertTrue(destroyer.isSunk());
    }

    /**
     * Verifies that an aircraft carrier is created
     * with the expected size of four cells.
     */
    @Test
    void aircraftCarrierSizeMatchesFourCells() {
        Ship carrier = ShipFactory.create(ShipType.AIRCRAFT_CARRIER);

        assertEquals(4, carrier.getSize());
    }

    /**
     * Verifies that newly created ships are initialized
     * with a horizontal orientation by default.
     */
    @Test
    void defaultOrientationIsHorizontal() {
        Ship submarine = ShipFactory.create(ShipType.SUBMARINE);

        assertEquals(Orientation.HORIZONTAL, submarine.getOrientation());
    }
}