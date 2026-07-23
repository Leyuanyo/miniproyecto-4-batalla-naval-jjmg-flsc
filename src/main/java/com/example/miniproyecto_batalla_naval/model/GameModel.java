package com.example.miniproyecto_batalla_naval.model;

import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.player.Player;

import java.io.Serializable;

/**
 * Represents the complete state of a Battleship match.
 *
 * This class stores the human player, the computer-controlled
 * player, and the current turn. It acts as the central model
 * of the game and provides utility methods to determine
 * whether the match has ended.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameModel implements Serializable {

    /** Serialization identifier for the game model class. */
    private static final long serialVersionUID = 1L;

    /** Human player participating in the game. */
    private final Player human;

    /** Computer-controlled opponent. */
    private final Machine machine;

    /** Indicates whether it is currently the human player's turn. */
    private boolean humanTurn;

    /**
     * Creates a new game model with the specified players.
     *
     * The human player always starts the game.
     *
     * @param human the human player
     * @param machine the computer-controlled player
     */
    public GameModel(Player human, Machine machine) {
        this.human = human;
        this.machine = machine;
        this.humanTurn = true;
    }

    /**
     * Returns the human player.
     *
     * @return the human player
     */
    public Player getHuman() {
        return human;
    }

    /**
     * Returns the computer-controlled player.
     *
     * @return the machine player
     */
    public Machine getMachine() {
        return machine;
    }

    /**
     * Indicates whether it is currently the human player's turn.
     *
     * @return {@code true} if it is the human player's turn;
     *         {@code false} otherwise
     */
    public boolean isHumanTurn() {
        return humanTurn;
    }

    /**
     * Updates the current turn.
     *
     * @param humanTurn {@code true} if the next turn belongs
     *                  to the human player; {@code false}
     *                  if it belongs to the machine
     */
    public void setHumanTurn(boolean humanTurn) {
        this.humanTurn = humanTurn;
    }

    /**
     * Determines whether the game has finished.
     *
     * The game ends when either the human player's fleet
     * or the machine's fleet has been completely sunk.
     *
     * @return {@code true} if the game is over;
     *         {@code false} otherwise
     */
    public boolean isGameOver() {
        boolean humanLost = !human.getBoard().getFleet().isEmpty() && human.getBoard().isFleetSunk();
        boolean machineLost = !machine.getBoard().getFleet().isEmpty() && machine.getBoard().isFleetSunk();

        return humanLost || machineLost;
    }
}