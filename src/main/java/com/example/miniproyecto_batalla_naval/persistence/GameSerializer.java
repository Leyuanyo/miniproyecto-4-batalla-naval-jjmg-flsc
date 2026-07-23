package com.example.miniproyecto_batalla_naval.persistence;

import com.example.miniproyecto_batalla_naval.model.GameModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles the serialization and deserialization of game data.
 *
 * This class is responsible for saving the current state of a game
 * into a binary file and restoring it when requested. It also
 * provides a simple method to determine whether a saved game
 * already exists.
 *
 * The save file is stored in the project's {@code saves}
 * directory using Java object serialization.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */

public class GameSerializer {

    /**
     * Relative path where the serialized game is stored.
     */
    private static final String SAVE_PATH = "saves/game.dat";

    /**
     * Saves the current game model to disk.
     *
     * If the save directory does not exist, it is created automatically.
     * The entire {@link GameModel} object is serialized into a binary file.
     *
     * @param model the game model to serialize and save
     *
     * @throws RuntimeException if an error occurs while writing
     *                          the save file
     */
    public void save(GameModel model) {
        File file = new File(SAVE_PATH);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(model);
        } catch (IOException e) {
            throw new RuntimeException("Could not save the game state.", e);
        }
    }

    /**
     * Loads the previously saved game from disk.
     *
     * If no save file exists, this method returns {@code null}.
     * Otherwise, the serialized {@link GameModel} is reconstructed
     * and returned.
     *
     * @return the loaded game model, or {@code null} if no save
     *         file exists
     *
     * @throws RuntimeException if the save file cannot be read
     *                          or does not contain a valid game
     *                          object
     */
    public GameModel load() {
        File file = new File(SAVE_PATH);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (GameModel) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not load the saved game.", e);
        }
    }

    /**
     * Determines whether a saved game is available.
     *
     * This method simply checks whether the save file exists
     * at the configured save location.
     *
     * @return {@code true} if a saved game exists;
     *         {@code false} otherwise
     */
    public boolean hasSavedGame() {
        return new File(SAVE_PATH).exists();
    }
}
