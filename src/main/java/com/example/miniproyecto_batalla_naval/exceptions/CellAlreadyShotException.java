package com.example.miniproyecto_batalla_naval.exceptions;

public class CellAlreadyShotException extends RuntimeException {
    public CellAlreadyShotException(String message) {
        super(message);
    }
}