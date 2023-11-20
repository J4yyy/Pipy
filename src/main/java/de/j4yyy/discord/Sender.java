package de.j4yyy.discord;

import de.j4yyy.Environment;
import de.j4yyy.twitch.Event;
import de.j4yyy.twitch.events.MessageEvent;
import okhttp3.*;

import java.io.IOException;

public class Sender {

    private final Environment environment;
    private final OkHttpClient httpClient;

    public Sender(Environment environment) {
        this.environment = environment;
        this.httpClient = new OkHttpClient();
    }

    public void triggerHook(Event event) {
        if(!(event instanceof MessageEvent messageEvent)) return;
        String bodyString = String.format("{\"content\":null,\"embeds\":[{\"title\":\"Message\",\"description\":\"%s\",\"url\":\"https://twitch.tv/%s\",\"color\":5814783,\"author\":{\"name\":\"%s\",\"url\":\"https://twitch.tv/%s\"}}],\"username\":\"Pipy\",\"avatar_url\":\"https://pbs.twimg.com/profile_images/1596084569341677572/5wtqshRu_400x400.jpg\",\"attachments\":[]}",
                messageEvent.getMessage(),
                messageEvent.getChannel().substring(1),
                messageEvent.getNickName(),
                messageEvent.getNickName());
        MediaType JSON = MediaType.get("application/json");
        RequestBody body = RequestBody.create(bodyString, JSON);

        Request request = new Request.Builder()
                .url(this.environment.get("DC_HOOK"))
                .post(body)
                .build();

        try(Response response = this.httpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}