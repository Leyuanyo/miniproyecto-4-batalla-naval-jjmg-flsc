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

/**
 * Manages the storage and retrieval of player information.
 *
 * This class saves basic player statistics in a plain text file
 * using a simple key-value format. It is mainly used to persist
 * information that can be easily read or modified without relying
 * on object serialization.
 *
 * Each line of the file follows the format:
 * <pre>
 * key=value
 * </pre>
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class PlayerFileManager {

    /**
     * Relative path where the player information is stored.
     */
    private static final String FILE_PATH = "saves/player.txt";

    /**
     * Saves the player's information into the text file.
     *
     * If the destination directory does not exist, it is created
     * automatically. Existing data is overwritten with the new values.
     *
     * @param nickname the player's nickname
     * @param shipsSunkByHuman the number of ships sunk by the human player
     * @param shipsSunkByMachine the number of ships sunk by the machine
     *
     * @throws RuntimeException if an error occurs while writing
     *                          the player file
     */
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

    /**
     * Loads the player information stored in the text file.
     *
     * Each line is interpreted as a key-value pair separated by
     * the '=' character. If the file does not exist, an empty map
     * is returned.
     *
     * @return a map containing all loaded player information,
     *         or an empty map if no file exists
     *
     * @throws RuntimeException if an error occurs while reading
     *                          the player file
     */
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
