package de.j4yyy.twitch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Connection implements Runnable {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Socket socket;
    private final Bot bot;
    private Future<?> future;
    private final String token;

    public Connection(Bot bot) {
        this.bot = bot;
        this.socket = bot.getSocketInstance();
        this.token = bot.getToken();
    }

    public static Connection connect(Bot bot) {
        Connection connection = new Connection(bot);
        if(connection.future != null && !connection.future.isDone()) {
            System.err.println("Already connected");
        } else {
            connection.future = connection.executorService.submit(connection);
        }
        return connection;
    }

    public void sendRAW(Object o) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write((o + "\r\n").getBytes(StandardCharsets.UTF_8));
        String message = o.toString().replace(
                token,
                "${REDACTED}"
        );
        System.out.println("> " + message);
        outputStream.flush();
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream()) {
            Traffic traffic = new Traffic();
            while (socket.isConnected()) {
                byte b = (byte) inputStream.read();
                traffic.add(b);

                if(b == '\n') {
                    String in = new String(
                            traffic.get(),
                            StandardCharsets.UTF_8
                    );
                    bot.onInput(in.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}