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
 * Note: {@link GameSerializer} always writes to a fixed "saves/game.dat"
 * path relative to the working directory, so this test creates a real file
 * on disk and removes it afterward.
 */
class GameSerializerTest {

    private static final File SAVE_FILE = new File("saves/game.dat");

    @AfterEach
    void cleanUp() {
        SAVE_FILE.delete();
    }

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

    @Test
    void hasSavedGameIsFalseWhenNoFileExists() {
        SAVE_FILE.delete();
        GameSerializer serializer = new GameSerializer();

        assertFalse(serializer.hasSavedGame());
    }
}
