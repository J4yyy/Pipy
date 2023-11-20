package de.j4yyy.twitch;

import de.j4yyy.Environment;
import de.j4yyy.discord.Sender;
import de.j4yyy.twitch.events.MessageEvent;
import de.j4yyy.twitch.events.UnknownEvent;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bot implements Handler {

    private static Map<String, Function<String[], Event>> map = new HashMap<>(){{
        put("PRIVMSG", MessageEvent::new);
    }};

    private final Environment environment;
    private final Connection connection;
    private final Socket socket;
    private final Sender sender;

    public Bot(Environment environment) {
        this.environment = environment;
        this.socket = getSocket();
        this.connection = Connection.connect(this);
        this.sender = new Sender(environment);
    }

    private Socket getSocket() {
        try {
            return new Socket(
                    "irc.chat.twitch.tv",
                    6667
            );
        } catch (IOException e) {
            System.err.println("Failed to create Socket");
            System.exit(1);
        }
        return null;
    }

    @Override
    public void onInput(String line) {
        System.out.println("< " +line);

        if(line.startsWith("PING")) {
            String message = line.split(" ", 2)[1];
            try {
                connection.sendRAW(String.join(
                        " ",
                        "PONG",
                        message
                ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            boolean comesWithTags = line.startsWith("@");
            String[] data = line.split(" ", comesWithTags ? 5 : 4);
            String type = data[comesWithTags ? 2 : 1];
            Event event = map.getOrDefault(type, UnknownEvent::new).apply(data);

            this.sender.triggerHook(event);

            for (int i = 0; i < data.length; i++) {
                System.out.println(i+": " + data[i]);
            }
        }

    }

    private void requestCapabilities(Capability... capabilities) throws IOException {
        if(capabilities.length == 0) return;
        String capability = Arrays.stream(capabilities).map(Capability::toString).collect(Collectors.joining(" "));
        connection.sendRAW("CAP REQ :" + capability);
    }

    public static Bot connect(Environment environment, Capability... capabilities) throws IOException {
        Bot bot = new Bot(environment);
        bot.requestCapabilities(capabilities);
        bot.connection.sendRAW(bot.getPassLine());
        bot.connection.sendRAW(bot.getNickLine());
        return bot;
    }

    public Socket getSocketInstance() {
        return this.socket;
    }

    public String getPassLine() {
        String token = environment.get("BOT_TOKEN");
        String oauth = String.join(
                ":",
                "oauth",
                token
        );
        return String.join(
                " ",
                "PASS",
                oauth
        );
    }

    public String getNickLine() {
        String bot_name = environment.get("BOT_NAME");
        return String.join(
                " ",
                "NICK",
                bot_name
        );
    }

    public String getToken() {
        return environment.get("BOT_TOKEN");
    }

    public void join(String... channels) throws IOException {
        connection.sendRAW(String.format(
                "JOIN #%S",
                String.join(",#", channels)
        ));
    }

    public void join(String channel) throws IOException {
        connection.sendRAW(String.format("JOIN #%s", channel));
    }
}
