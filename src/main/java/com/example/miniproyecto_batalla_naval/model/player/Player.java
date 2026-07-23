package com.example.miniproyecto_batalla_naval.model.player;

import com.example.miniproyecto_batalla_naval.model.board.Board;

import java.io.Serializable;

/**
 * Represents a player participating in a Battleship match.
 *
 * Each player has a nickname and owns a game board where
 * ships are placed and shots from the opponent are received.
 * This class serves as the base implementation for both
 * human and computer-controlled players.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Player implements Serializable {

    /** Serialization identifier for the player class. */
    private static final long serialVersionUID = 1L;

    /** Nickname that identifies the player. */
    private String nickname;

    /** Board that contains the player's fleet and game state. */
    private final Board board;

    /**
     * Creates a player with the specified nickname and
     * an empty game board.
     *
     * @param nickname the player's nickname
     */
    public Player(String nickname) {
        this(nickname, new Board());
    }

    /**
     * Creates a player with the specified nickname and board.
     *
     * @param nickname the player's nickname
     * @param board the board assigned to the player
     */
    public Player(String nickname, Board board) {
        this.nickname = nickname;
        this.board = board;
    }

    /**
     * Returns the player's nickname.
     *
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Updates the player's nickname.
     *
     * @param nickname the new nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the board assigned to the player.
     *
     * @return the player's game board
     */
    public Board getBoard() {
        return board;
    }
}