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

/**
 * Unit tests for the {@link Board} class.
 *
 * This test suite verifies the main behaviors of the game board,
 * including ship placement, shot processing, collision detection,
 * board limits, and game ending conditions.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class BoardTest {

    /**
     * Verifies that a ship can be successfully placed
     * inside the board boundaries.
     *
     * @throws InvalidShipPlacementException if the placement is invalid
     */
    @Test
    void placingShipInsideBoundsSucceeds() throws InvalidShipPlacementException {
        Board board = new Board();
        Ship frigate = ShipFactory.create(ShipType.FRIGATE);

        board.placeShip(frigate, 2, 2, Orientation.HORIZONTAL);

        assertEquals(CellState.SHIP, board.getCell(2, 2).getState());
    }

    /**
     * Verifies that placing a ship outside the board
     * throws an {@link InvalidShipPlacementException}.
     */
    @Test
    void placingShipOutOfBoundsThrows() {
        Board board = new Board();
        Ship destroyer = ShipFactory.create(ShipType.DESTROYER);

        assertThrows(InvalidShipPlacementException.class, () ->
                board.placeShip(destroyer, 0, 9, Orientation.HORIZONTAL));
    }

    /**
     * Verifies that overlapping two ships is not allowed
     * and produces an exception.
     *
     * @throws InvalidShipPlacementException if the first placement fails
     */
    @Test
    void placingOverlappingShipsThrows() throws InvalidShipPlacementException {
        Board board = new Board();
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 5, 5, Orientation.HORIZONTAL);

        assertThrows(InvalidShipPlacementException.class, () ->
                board.placeShip(ShipFactory.create(ShipType.FRIGATE), 5, 5, Orientation.HORIZONTAL));
    }

    /**
     * Verifies that shooting an empty cell returns
     * {@link ShotResult#WATER} and updates the cell state.
     */
    @Test
    void shootingWaterReturnsWaterAndMarksCell() {
        Board board = new Board();

        ShotResult result = board.receiveShot(0, 0);

        assertEquals(ShotResult.WATER, result);
        assertEquals(CellState.WATER, board.getCell(0, 0).getState());
    }

    /**
     * Verifies that shooting the same cell twice
     * throws a {@link CellAlreadyShotException}.
     */
    @Test
    void shootingSameCellTwiceThrows() {
        Board board = new Board();
        board.receiveShot(3, 3);

        assertThrows(CellAlreadyShotException.class, () -> board.receiveShot(3, 3));
    }

    /**
     * Verifies that sinking the last remaining ship
     * ends the game and returns {@link ShotResult#GAME_OVER}.
     *
     * @throws InvalidShipPlacementException if the ship placement fails
     */
    @Test
    void sinkingLastShipReturnsGameOver() throws InvalidShipPlacementException {
        Board board = new Board();
        board.placeShip(ShipFactory.create(ShipType.FRIGATE), 0, 0, Orientation.HORIZONTAL);

        ShotResult result = board.receiveShot(0, 0);

        assertEquals(ShotResult.GAME_OVER, result);
        assertTrue(board.isFleetSunk());
    }

    /**
     * Verifies that sinking one ship while other ships remain
     * returns {@link ShotResult#SUNK} instead of ending the game.
     *
     * @throws InvalidShipPlacementException if a ship placement fails
     */
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