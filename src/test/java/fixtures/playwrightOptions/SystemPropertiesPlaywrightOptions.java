package fixtures.playwrightOptions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/// Sets options, such as 'headless', 'baseUrl' etc.
public class SystemPropertiesPlaywrightOptions implements OptionsFactory {

    private final Map<String, String> defaults = new HashMap<>();

    public SystemPropertiesPlaywrightOptions() {
        for (String key : DefaultValuesConfig.getPropertyNames()) {
            defaults.put(key, DefaultValuesConfig.get(key));
        }
    }

    @Override
    public Options getOptions() {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        Options playwrightOptions = new Options();

        for (String key : DefaultValuesConfig.getPropertyNames()) {
            String value = DefaultValuesConfig.getWithLog(key);

            applyPropertyIfExists(playwrightOptions, key, value);

            applyPropertyIfExists(contextOptions, key, value);
        }
        return playwrightOptions.setContextOptions(contextOptions);
    }

    /// using the Java Reflection to set all found params by finding a method that matches a property name (e.g., setBaseURL), and invoking it.
    private void applyPropertyIfExists(Object target, String key, String value) {
        String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);

        for (Method method : target.getClass().getDeclaredMethods()) {
            //only 1 parameter
            if (method.getName().equalsIgnoreCase(methodName) && method.getParameterCount() == 1) {
                try {
                    //getting the only parameter's type
                    Class<?> parameterType = method.getParameterTypes()[0];
                    Object convertedValue = convertType(parameterType, value);
                    method.invoke(target, convertedValue);
                } catch (Exception e) {
                    e.printStackTrace(); //TODO change to something more robust
                }
            }
        }
    }

    private Object convertType(Class<?> type, String value) {
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == Path.class) return Paths.get(value);
        return value; // Default to String
    }
}
