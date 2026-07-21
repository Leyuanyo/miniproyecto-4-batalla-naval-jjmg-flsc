package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.Cell;

public interface BoardListener {
    void onCellChanged(Cell cell);
}