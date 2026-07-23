package com.example.miniproyecto_batalla_naval.model.player;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;
import com.example.miniproyecto_batalla_naval.model.interfaces.ShotStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Machine} class.
 *
 * This test suite verifies that the machine player correctly
 * delegates shot selection to its configured strategy and
 * properly reports shot results back to the strategy.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class MachineTest {

    /**
     * Verifies that the machine delegates the selection of the
     * next target cell to the injected {@link ShotStrategy}.
     */
    @Test
    void chooseNextShotDelegatesToTheInjectedStrategy() {
        Board enemyBoard = new Board();
        Cell expected = enemyBoard.getCell(4, 4);
        Machine machine = new Machine(board -> expected);

        Cell actual = machine.chooseNextShot(enemyBoard);

        assertSame(expected, actual);
    }

    /**
     * Verifies that the strategy is notified only when the
     * machine hits or sinks an enemy ship, and not when
     * the shot lands in water.
     */
    @Test
    void notifyShotResultOnlyCallsRegisterHitForHitOrSunk() {
        Board enemyBoard = new Board();
        Cell shotCell = enemyBoard.getCell(2, 2);
        boolean[] wasCalled = {false};

        ShotStrategy strategy = new ShotStrategy() {
            @Override
            public Cell nextShot(Board board) {
                return shotCell;
            }

            @Override
            public void registerHit(Board board, Cell hitCell) {
                wasCalled[0] = true;
            }
        };
        Machine machine = new Machine(strategy);

        machine.notifyShotResult(enemyBoard, shotCell, ShotResult.WATER);
        assertFalse(wasCalled[0]);

        machine.notifyShotResult(enemyBoard, shotCell, ShotResult.HIT);
        assertTrue(wasCalled[0]);
    }
}