package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.board.Cell;

public interface BoardListener {
    void onCellChanged(Cell cell);
}