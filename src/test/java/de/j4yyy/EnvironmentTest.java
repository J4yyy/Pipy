package de.j4yyy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EnvironmentTest {
    @Test
    public void shouldLoadEnv() {
        String dir = System.getProperty("user.dir")+"/src/test/java/de/j4yyy/assets";
        Path path = Paths.get(dir+"/valid.env");
        Environment environment = new Environment(dir, path);
        assertEquals("z87vy0s2qcmqom", environment.get("BOT_TOKEN"));
    }

    @Test
    public void shouldSayInvalidFormat() {
        String dir = System.getProperty("user.dir")+"/src/test/java/de/j4yyy/assets";
        Path path = Paths.get(dir+"/invalid.env");

        Environment environment = new Environment(dir, path);
        assertFalse(environment.isProperlyConfigured());
    }
}
