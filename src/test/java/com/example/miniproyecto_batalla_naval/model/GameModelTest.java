package com.example.miniproyecto_batalla_naval.model;

import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.player.Player;
import com.example.miniproyecto_batalla_naval.model.ships.Orientation;
import com.example.miniproyecto_batalla_naval.model.ships.ShipType;
import com.example.miniproyecto_batalla_naval.patterns.RandomShotStrategy;
import com.example.miniproyecto_batalla_naval.patterns.ShipFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link GameModel} class.
 *
 * This test suite verifies the core game state behavior,
 * including turn management, game initialization, and
 * game-over detection when a player's fleet has been sunk.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class GameModelTest {

    /**
     * Verifies that a newly created game starts
     * with the human player's turn.
     */
    @Test
    void gameStartsWithHumanTurn() {
        Player human = new Player("Juan", new Board());
        Machine machine = new Machine(new RandomShotStrategy());
        GameModel model = new GameModel(human, machine);

        assertTrue(model.isHumanTurn());
    }

    /**
     * Verifies that a newly created game is not
     * considered finished while both fleets remain intact.
     */
    @Test
    void gameIsNotOverWithFreshBoards() {
        Player human = new Player("Juan", new Board());
        Machine machine = new Machine(new RandomShotStrategy());
        GameModel model = new GameModel(human, machine);

        assertFalse(model.isGameOver());
    }

    /**
     * Verifies that the active turn can be changed
     * from the human player to the machine player.
     */
    @Test
    void turnCanBeToggled() {
        GameModel model = new GameModel(new Player("Juan"), new Machine(new RandomShotStrategy()));

        model.setHumanTurn(false);

        assertFalse(model.isHumanTurn());
    }

    /**
     * Verifies that the game is considered over
     * when the human player's entire fleet has been sunk.
     *
     * @throws Exception if an error occurs while placing
     *                   or shooting the test ship
     */
    @Test
    void gameIsOverWhenHumanFleetIsFullySunk() throws Exception {
        Player human = new Player("Juan");
        human.getBoard().placeShip(ShipFactory.create(ShipType.FRIGATE), 0, 0, Orientation.HORIZONTAL);
        human.getBoard().receiveShot(0, 0);

        GameModel model = new GameModel(human, new Machine(new RandomShotStrategy()));

        assertTrue(model.isGameOver());
    }
}