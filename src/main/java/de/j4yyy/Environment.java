package de.j4yyy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Environment extends HashMap<String, String> {

    public Environment(String dir, Path path) {
        try {
            List<String> lines = Files.readAllLines(path);

            for(String line : lines) {
                String[] pair = line.split("=", 2);
                if(pair.length != 2) continue;
                put(pair[0], pair[1]);
            }
        } catch (IOException e) {
            System.err.println("Failed read file '" + path.getFileName()+ "' in '" + dir + "'.");
        }
    }

    public boolean isProperlyConfigured() {
        return containsKey("BOT_TOKEN") && containsKey("BOT_NAME") && containsKey("DC_HOOK") && containsKey("BOT_CHANNEL");
    }
}