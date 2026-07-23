package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.model.board.Cell;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;

public class MachineTurnRunner implements Runnable {

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
