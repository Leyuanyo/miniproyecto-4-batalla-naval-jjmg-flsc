package com.example.miniproyecto_batalla_naval.controller.adapter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CellInteractionAdapter implements EventHandler<MouseEvent> {
    private final int row;
    private final int column;
    private final CellInteractionListener listener;

    public CellInteractionAdapter(int row, int column, CellInteractionListener listener) {
        this.row = row;
        this.column = column;
        this.listener = listener;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            listener.onCellLeftClick(row, column);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            listener.onCellRightClick(row, column);
        }
    }
}
