package com.example.miniproyecto_batalla_naval.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Note: {@link PlayerFileManager} always writes to a fixed "saves/player.txt"
 * path relative to the working directory, so this test creates a real file
 * on disk and removes it afterward.
 */
class PlayerFileManagerTest {

    private static final File SAVE_FILE = new File("saves/player.txt");

    @AfterEach
    void cleanUp() {
        SAVE_FILE.delete();
    }

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
