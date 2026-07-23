package com.example.miniproyecto_batalla_naval.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link PlayerFileManager} class.
 *
 * This test suite verifies that player information is correctly
 * stored and retrieved from the persistence file, ensuring that
 * saved statistics remain consistent between save and load operations.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class PlayerFileManagerTest {

    /**
     * File used by the player file manager
     * during the execution of the tests.
     */
    private static final File SAVE_FILE = new File("saves/player.txt");

    /**
     * Deletes the generated player data file
     * after each test to ensure that every test
     * starts with a clean environment.
     */
    @AfterEach
    void cleanUp() {
        SAVE_FILE.delete();
    }

    /**
     * Verifies that player information saved to disk
     * can be successfully loaded back while preserving
     * the nickname and the number of ships sunk by
     * both the human player and the machine.
     */
    @Test
    void savedDataCanBeReadBack() {
        PlayerFileManager manager = new PlayerFileManager();

        manager.save("Juan", 3, 5);
        Map<String, String> data = manager.load();

        assertEquals("Juan", data.get("nickname"));
        assertEquals("3", data.get("shipsSunkByHuman"));
        assertEquals("5", data.get("shipsSunkByMachine"));
    }
}