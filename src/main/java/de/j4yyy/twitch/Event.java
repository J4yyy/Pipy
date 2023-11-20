package de.j4yyy.twitch;

public abstract class Event {
    protected final long timeStamp = System.currentTimeMillis();
    protected final String[] data;

    public Event(String[] data) {
        this.data = data;
    }

    public abstract String getType();

    public long getTimeStamp() {
        return this.timeStamp;
    }
}