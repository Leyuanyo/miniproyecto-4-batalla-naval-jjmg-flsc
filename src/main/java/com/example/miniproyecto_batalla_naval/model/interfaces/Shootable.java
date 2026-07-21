package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.exceptions.CellAlreadyShotException;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;

public interface Shootable {
    ShotResult receiveShot(int row, int column) throws CellAlreadyShotException;
}