package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;

/**
 * Runs the machine's turn on a background thread so the UI stays responsive
 * and the short "thinking" delay is visible. Results are handed back via
 * {@link MachineShotCallback}; the caller is responsible for hopping back
 * onto the JavaFX Application Thread (with {@code Platform.runLater}) before
 * touching the scene graph.
 */
public class MachineTurnRunner implements Runnable {

    /**
     * Functional callback invoked once the machine's shot has been
     * resolved.
     */
    public interface MachineShotCallback {
        void onShotResolved(int row, int column, ShotResult result);
    }

    private static final long THINKING_DELAY_MS = 700;

    private final GameModel model;
    private final MachineShotCallback callback;

    public MachineTurnRunner(GameModel model, MachineShotCallback callback) {
        this.model = model;
        this.callback = callback;
    }

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
