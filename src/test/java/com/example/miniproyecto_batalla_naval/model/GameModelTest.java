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

class GameModelTest {

    @Test
    void gameStartsWithHumanTurn() {
        Player human = new Player("Juan", new Board());
        Machine machine = new Machine(new RandomShotStrategy());
        GameModel model = new GameModel(human, machine);

        assertTrue(model.isHumanTurn());
    }

    @Test
    void turnCanBeToggled() {
        GameModel model = new GameModel(new Player("Juan"), new Machine(new RandomShotStrategy()));

        model.setHumanTurn(false);

        assertFalse(model.isHumanTurn());
    }

    @Test
    void gameIsOverWhenHumanFleetIsFullySunk() throws Exception {
        Player human = new Player("Juan");
        human.getBoard().placeShip(ShipFactory.create(ShipType.FRIGATE), 0, 0, Orientation.HORIZONTAL);
        human.getBoard().receiveShot(0, 0);

        GameModel model = new GameModel(human, new Machine(new RandomShotStrategy()));

        assertTrue(model.isGameOver());
    }
}
