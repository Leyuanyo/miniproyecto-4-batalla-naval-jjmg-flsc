package com.example.miniproyecto_batalla_naval.model;

import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.player.Player;

import java.io.Serializable;

public class GameModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Player human;
    private final Machine machine;
    private boolean humanTurn;

    public GameModel(Player human, Machine machine) {
        this.human = human;
        this.machine = machine;
        this.humanTurn = true;
    }

    public Player getHuman() {
        return human;
    }

    public Machine getMachine() {
        return machine;
    }

    public boolean isHumanTurn() {
        return humanTurn;
    }

    public void setHumanTurn(boolean humanTurn) {
        this.humanTurn = humanTurn;
    }

    public boolean isGameOver() {
        boolean humanLost = !human.getBoard().getFleet().isEmpty() && human.getBoard().isFleetSunk();
        boolean machineLost = !machine.getBoard().getFleet().isEmpty() && machine.getBoard().isFleetSunk();

        return humanLost || machineLost;
    }
}