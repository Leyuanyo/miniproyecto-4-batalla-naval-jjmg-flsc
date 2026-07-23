package com.example.miniproyecto_batalla_naval.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFileManager {
    private static final String FILE_PATH = "saves/player.txt";

    public void save(String nickname, int shipsSunkByHuman, int shipsSunkByMachine) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("nickname=" + nickname);
            writer.println("shipsSunkByHuman=" + shipsSunkByHuman);
            writer.println("shipsSunkByMachine=" + shipsSunkByMachine);
        } catch (IOException e) {
            throw new RuntimeException("Could not save the player file.", e);
        }
    }

    public Map<String, String> load() {
        Map<String, String> data = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return data;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read the player file.", e);
        }
        return data;
    }
}
