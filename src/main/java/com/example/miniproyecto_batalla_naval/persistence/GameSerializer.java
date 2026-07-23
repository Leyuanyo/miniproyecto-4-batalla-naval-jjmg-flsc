package com.example.miniproyecto_batalla_naval.persistence;

import com.example.miniproyecto_batalla_naval.model.GameModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameSerializer {
    private static final String SAVE_PATH = "saves/game.dat";

    public void save(GameModel model) {
        File file = new File(SAVE_PATH);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(model);
        } catch (IOException e) {
            throw new RuntimeException("Could not save the game state.", e);
        }
    }

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

    public boolean hasSavedGame() {
        return new File(SAVE_PATH).exists();
    }
}
