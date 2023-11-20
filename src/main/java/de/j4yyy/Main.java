package de.j4yyy;

import de.j4yyy.twitch.Bot;
import de.j4yyy.twitch.Capability;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        Path path = Paths.get(".env");
        Environment environment = new Environment(dir, path);

        if(!environment.isProperlyConfigured()) {
            System.err.println(
                    """
                            Environment is not properly configured
                            Please add the following entries:
                            BOT_TOKEN, BOT_NAME, BOT_CHANNEL, DC_HOOK
                            """
            );
            System.exit(1);
        }

        try {
            Bot bot = Bot.connect(environment, Capability.values());
            String[] channels = environment.get("BOT_CHANNEL").split(",");
            if(channels.length > 1) {
                System.out.println(channels.length);
                bot.join(channels);
            } else {
                bot.join(channels[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}