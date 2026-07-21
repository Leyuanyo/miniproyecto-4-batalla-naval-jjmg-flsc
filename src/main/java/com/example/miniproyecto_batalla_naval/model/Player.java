package com.example.miniproyecto_batalla_naval.model;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nickname;
    private final Board board;

    public Player(String nickname) {
        this(nickname, new Board());
    }

    public Player(String nickname, Board board) {
        this.nickname = nickname;
        this.board = board;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Board getBoard() {
        return board;
    }
}