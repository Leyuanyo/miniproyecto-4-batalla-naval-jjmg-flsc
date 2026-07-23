package com.example.miniproyecto_batalla_naval.model.board;

import com.example.miniproyecto_batalla_naval.exceptions.CellAlreadyShotException;
import com.example.miniproyecto_batalla_naval.exceptions.InvalidShipPlacementException;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.Ship;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void placingShipInsideBoundsSucceeds() throws InvalidShipPlacementException {
        Board board = new Board();
        Ship frigate = ShipFactory.create(ShipType.FRIGATE);

        board.placeShip(frigate, 2, 2, Orientation.HORIZONTAL);

        assertEquals(CellState.SHIP, board.getCell(2, 2).getState());
    }

    @Test
    void placingShipOutOfBoundsThrows() {
        Board board = new Board();
        Ship destroyer = ShipFactory.create(ShipType.DESTROYER);

        assertThrows(InvalidShipPlacementException.class, () ->
                board.placeShip(destroyer, 0, 9, Orientation.HORIZONTAL));
    }

    @Test
    void placingOverlappingShipsThrows() throws InvalidShipPlacementException {
        Board board = new Board();
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 5, 5, Orientation.HORIZONTAL);

        assertThrows(InvalidShipPlacementException.class, () ->
                board.placeShip(ShipFactory.create(ShipType.FRIGATE), 5, 5, Orientation.HORIZONTAL));
    }

    @Test
    void shootingWaterReturnsWaterAndMarksCell() {
        Board board = new Board();

        ShotResult result = board.receiveShot(0, 0);

        assertEquals(ShotResult.WATER, result);
        assertEquals(CellState.WATER, board.getCell(0, 0).getState());
    }

    @Test
    void shootingSameCellTwiceThrows() {
        Board board = new Board();
        board.receiveShot(3, 3);

        assertThrows(CellAlreadyShotException.class, () -> board.receiveShot(3, 3));
    }

    @Test
    void sinkingLastShipReturnsGameOver() throws InvalidShipPlacementException {
        Board board = new Board();
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 0, 0, Orientation.HORIZONTAL);

        ShotResult result = board.receiveShot(0, 0);

        assertEquals(ShotResult.GAME_OVER, result);
        assertTrue(board.isFleetSunk());
    }

    @Test
    void sinkingOneOfSeveralShipsReturnsSunkNotGameOver() throws InvalidShipPlacementException {
        Board board = new Board();
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 0, 0, Orientation.HORIZONTAL);
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 5, 5, Orientation.HORIZONTAL);

        ShotResult result = board.receiveShot(0, 0);

        assertEquals(ShotResult.SUNK, result);
        assertFalse(board.isFleetSunk());
    }
}
