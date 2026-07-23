package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.exceptions.CellAlreadyShotException;
import com.example.miniproyecto_batalla_naval.model.board.ShotResult;

/**
 * Defines the operations required for processing
 * shots on a Battleship game board.
 *
 * Classes implementing this interface are responsible
 * for determining the result of each shot, updating
 * the board state, and reporting the corresponding
 * outcome to the caller.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface Shootable {

    /**
     * Processes a shot fired at the specified board position.
     *
     * @param row the row of the targeted cell
     * @param column the column of the targeted cell
     * @return the result produced by the shot
     * @throws CellAlreadyShotException if the selected cell
     *         has already been targeted previously
     */
    ShotResult receiveShot(int row, int column) throws CellAlreadyShotException;
}