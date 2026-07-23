package com.example.miniproyecto_batalla_naval.persistence;

import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.model.board.Board;
import com.example.miniproyecto_batalla_naval.model.player.Machine;
import com.example.miniproyecto_batalla_naval.model.player.Player;
import com.example.miniproyecto_batalla_naval.patterns.RandomShotStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link GameSerializer} class.
 *
 * This test suite verifies the serialization and deserialization
 * of game data, ensuring that saved games can be recovered correctly
 * and that the serializer properly detects the presence or absence
 * of saved game files.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class GameSerializerTest {

    /**
     * File used by the serializer to store game data
     * during the execution of the tests.
     */
    private static final File SAVE_FILE = new File("saves/game.dat");

    /**
     * Deletes the generated save file after each test
     * to ensure that every test starts with a clean environment.
     */
    @AfterEach
    void cleanUp() {
        SAVE_FILE.delete();
    }

    /**
     * Verifies that a saved game can be successfully loaded
     * while preserving the player's nickname and the current
     * game turn information.
     */
    @Test
    void savedGameCanBeLoadedBackWithSameNickname() {
        GameSerializer serializer = new GameSerializer();
        Player human = new Player("Juan", new Board());
        Machine machine = new Machine(new RandomShotStrategy());
        GameModel original = new GameModel(human, machine);

        serializer.save(original);
        GameModel loaded = serializer.load();

        assertEquals("Juan", loaded.getHuman().getNickname());
        assertTrue(loaded.isHumanTurn());
    }

    /**
     * Verifies that the serializer correctly reports
     * that no saved game exists when the save file
     * has been removed.
     */
    @Test
    void hasSavedGameIsFalseWhenNoFileExists() {
        SAVE_FILE.delete();
        GameSerializer serializer = new GameSerializer();

        assertFalse(serializer.hasSavedGame());
    }
}