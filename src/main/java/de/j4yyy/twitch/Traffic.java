package de.j4yyy.twitch;

import java.util.Arrays;

public class Traffic {
    private byte[] bytes = new byte[128];
    private int position = 0;

    private void reset() {
        this.bytes = new byte[128];
        this.position = 0;
    }

    public void add(byte b) {
        this.bytes[position++] = b;
        if(position >= bytes.length) {
            byte[] replacement = new byte[bytes.length << 1];
            System.arraycopy(
                    bytes,
                    0,
                    replacement,
                    0,
                    bytes.length
            );
            this.bytes = replacement;
        }
    }

    public byte[] get() {
        byte[] b = Arrays.copyOfRange(bytes, 0, position);
        this.reset();
        return b;
    }
}
