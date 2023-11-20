package de.j4yyy.twitch.events;

import de.j4yyy.twitch.Event;

import java.util.HashMap;
import java.util.Map;

public class MessageEvent extends Event {

    private final String message, channel, type, serverName, nickName, host;
    private final Map<String, String> tags = new HashMap<>();

    public MessageEvent(String[] data) {
        super(data);
        this.message = data[data.length - 1].substring(1);
        this.channel = data[data.length - 2];
        this.type = data[data.length -3];
        String[] prefix = data[data.length - 4].split("@");
        this.host = prefix[1];
        String[] names = prefix[0].split("!");
        this.serverName = names[0].substring(1);
        this.nickName = names[1];
        if(!data[0].startsWith("@")) return;
        String[] metadata = data[0].split(";");
        for(String pair : metadata) {
            String[] values = pair.split("=");
            this.tags.put(values[0], values.length == 2 ? values[1] : "");
        }
    }

    @Override
    public String getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getHost() {
        return this.host;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "message='" + message + '\'' +
                ", channel='" + channel + '\'' +
                ", type='" + type + '\'' +
                ", serverName='" + serverName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", host='" + host + '\'' +
                ", tags=" + tags +
                ", timeStamp=" + timeStamp +
                '}';
    }
}