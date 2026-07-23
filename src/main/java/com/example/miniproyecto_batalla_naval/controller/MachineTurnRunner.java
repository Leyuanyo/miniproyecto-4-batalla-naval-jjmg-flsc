package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;

/**
 * Executes the machine player's turn in a separate thread.
 * Simulates the machine's thinking time, selects the next target,
 * performs the shot on the human player's board, updates the machine's
 * shooting strategy, and reports the result through a callback.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class MachineTurnRunner implements Runnable {

    /**
     * Callback interface used to notify the controller when the
     * machine's shot has been completely processed.
     */
    public interface MachineShotCallback {

        /**
         * Invoked after the machine's shot has been resolved.
         *
         * @param row the row index of the targeted cell
         * @param column the column index of the targeted cell
         * @param result the result obtained from the shot
         */
        void onShotResolved(int row, int column, ShotResult result);
    }

    /** Time, in milliseconds, that simulates the machine's thinking process. */
    private static final long THINKING_DELAY_MS = 700;

    /** Game model containing the current match state. */
    private final GameModel model;

    /** Callback notified when the machine finishes its turn. */
    private final MachineShotCallback callback;

    /**
     * Creates a new runner responsible for executing the machine's turn.
     *
     * @param model the game model containing the current match
     * @param callback the callback that will receive the shot result
     */
    public MachineTurnRunner(GameModel model, MachineShotCallback callback) {
        this.model = model;
        this.callback = callback;
    }

    /**
     * {@inheritDoc}
     * Simulates the machine's thinking time, selects the next target,
     * executes the shot on the human player's board, updates the
     * machine's internal strategy, and notifies the controller of
     * the resulting shot outcome.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(THINKING_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        Machine machine = model.getMachine();
        Board humanBoard = model.getHuman().getBoard();
        Cell target = machine.chooseNextShot(humanBoard);

        ShotResult result = humanBoard.receiveShot(target.getRow(), target.getColumn());
        machine.notifyShotResult(humanBoard, target, result);

        callback.onShotResolved(target.getRow(), target.getColumn(), result);
    }
}