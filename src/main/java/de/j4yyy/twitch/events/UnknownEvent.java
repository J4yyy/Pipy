package de.j4yyy.twitch.events;

import de.j4yyy.twitch.Event;

public class UnknownEvent extends Event {
    public UnknownEvent(String[] data) {
        super(data);
    }

    @Override
    public String getType() {
        return "UNKNOWN";
    }
}