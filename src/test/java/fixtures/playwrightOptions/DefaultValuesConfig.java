package fixtures.playwrightOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/// retrieves values from the 'default-values.properties' file
public class DefaultValuesConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = DefaultValuesConfig.class
                .getClassLoader()
                .getResourceAsStream("default-values.properties")) {

            if (is == null) {
                throw new RuntimeException("config.properties not found");
            }

            props.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    private static String get(String key) {
        return System.getProperty(key,
                System.getenv().getOrDefault(key.toUpperCase(),
                        props.getProperty(key)));
    }

    public static final boolean HEADLESS = Boolean.parseBoolean(get("headless"));
    public static final String VIDEODIR = get("videoDir");
    public static final String BASE_URL = get("baseUrl");
}
