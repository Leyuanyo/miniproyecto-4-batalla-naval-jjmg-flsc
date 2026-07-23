package com.example.miniproyecto_batalla_naval.model.ships;

import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipTest {

    @Test
    void frigateSinksAfterOneHit() {
        Ship frigate = ShipFactory.create(ShipType.FRIGATE);

        frigate.registerHit();

        assertTrue(frigate.isSunk());
    }

    @Test
    void destroyerNeedsBothCellsHitToSink() {
        Ship destroyer = ShipFactory.create(ShipType.DESTROYER);

        destroyer.registerHit();
        assertFalse(destroyer.isSunk());

        destroyer.registerHit();
        assertTrue(destroyer.isSunk());
    }

    @Test
    void aircraftCarrierSizeMatchesFourCells() {
        Ship carrier = ShipFactory.create(ShipType.AIRCRAFT_CARRIER);

        assertEquals(4, carrier.getSize());
    }

    @Test
    void defaultOrientationIsHorizontal() {
        Ship submarine = ShipFactory.create(ShipType.SUBMARINE);

        assertEquals(Orientation.HORIZONTAL, submarine.getOrientation());
    }
}
