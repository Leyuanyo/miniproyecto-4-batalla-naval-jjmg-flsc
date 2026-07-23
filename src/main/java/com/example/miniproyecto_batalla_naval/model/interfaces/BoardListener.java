package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.board.Cell;

/**
 * Listener interface for receiving notifications when
 * the state of a board cell changes.
 *
 * Classes implementing this interface can register with a
 * board to be informed whenever a cell is updated, allowing
 * the user interface or other components to react to changes
 * in the game state.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface BoardListener {

    /**
     * Invoked whenever the state of a board cell changes.
     *
     * @param cell the cell whose state has been modified
     */
    void onCellChanged(Cell cell);
}