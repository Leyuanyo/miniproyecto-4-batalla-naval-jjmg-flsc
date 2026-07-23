package com.example.miniproyecto_batalla_naval.controller.adapter;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyRotationAdapter implements EventHandler<KeyEvent> {

    private final RotationListener listener;

    public KeyRotationAdapter(RotationListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            listener.onRotateRequested();
            event.consume();
        }
    }
}