package com.example.miniproyecto_batalla_naval.controller.adapter;

public interface CellInteractionListener {
    void onCellLeftClick(int row, int column);

    void onCellRightClick(int row, int column);
}
