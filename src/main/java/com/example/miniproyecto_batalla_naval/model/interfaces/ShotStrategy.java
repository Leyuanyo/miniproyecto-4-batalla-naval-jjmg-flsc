package com.example.miniproyecto_batalla_naval.model.interfaces;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.board.Cell;

/**
 * Defines the strategy used by the computer player
 * to select its next shot during a Battleship match.
 *
 * Different implementations may provide simple,
 * random, or intelligent shooting behaviors without
 * modifying the game logic that uses them.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface ShotStrategy {

    /**
     * Determines the next cell that should be targeted
     * on the opponent's board.
     *
     * @param enemyBoard the opponent's board
     * @return the selected target cell
     */
    Cell nextShot(Board enemyBoard);

    /**
     * Receives notification when a successful hit has
     * been registered on the opponent's board.
     *
     * The default implementation performs no action.
     * Strategy implementations may override this method
     * to adapt future shot selections after a hit.
     *
     * @param enemyBoard the opponent's board
     * @param hitCell the cell where the successful hit occurred
     */
    default void registerHit(Board enemyBoard, Cell hitCell) {
    }
}