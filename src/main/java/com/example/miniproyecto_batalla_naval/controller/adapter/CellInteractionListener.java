package com.example.miniproyecto_batalla_naval.controller.adapter;

/**
 * Defines the contract for handling mouse interactions on a board cell.
 * Implementations of this interface are responsible for responding to
 * left-click and right-click events performed by the player on the game board.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface CellInteractionListener {

    /**
     * Invoked when the user performs a left-click on a board cell.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    void onCellLeftClick(int row, int column);

    /**
     * Invoked when the user performs a right-click on a board cell.
     *
     * @param row the row index of the selected cell
     * @param column the column index of the selected cell
     */
    void onCellRightClick(int row, int column);
}