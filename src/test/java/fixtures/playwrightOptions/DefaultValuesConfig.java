package fixtures.playwrightOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import static Utils.ConsoleLogUtil.secureLog;

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

    /// Returns all keys
    public static Set<String> getPropertyNames() {
        return props.stringPropertyNames();
    }

    public static String get(String key) {
        return System.getProperty(key,
                        System.getenv().getOrDefault(key.toUpperCase(),
                                props.getProperty(key)
                        )

        );
    }

    public static String getWithLog(String key) {
        return secureLog(key,get(key));
    }
}
