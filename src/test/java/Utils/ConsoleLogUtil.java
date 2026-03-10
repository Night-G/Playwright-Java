package Utils;

import fixtures.playwrightOptions.DefaultValuesConfig;

public class ConsoleLogUtil {
    private static boolean isDebug;

    static {
        try {
            isDebug = Boolean.parseBoolean(DefaultValuesConfig.get("debug"));
        } catch (Exception e) {
            isDebug = false;
            throw new RuntimeException("Issue with the default value of 'debug'", e);
        }
    }

    /// prints a value
    public static <T> T consoleLog(T value) {
        System.out.println(value);
        return value;
    }

    /// prints a value and a message in the format:<p>`message : value`</p>
    /// <b><i>!note the " : " between the message and the value</i></b>
    public static <T> T consoleLog(String message, T value) {
        System.out.println(message + " : " + value.toString());
        return value;
    }

    /// outputs a message
    public static <T> T sendConsoleLog(String message, T value) {
        System.out.println(message);
        return value;
    }

    /// Prints `value` to the console while `debug` env var is set `true`
    public static <T> T secureLog(T value) {
        if (isDebug) {
            return consoleLog(value);
        } else {
            return value;
        }
    }

    /// while `debug` env var is set `true` prints `value` and `message` in the format:<p>`message : value`</p>
    /// <b><i>!note the " : " between the message and the value</i></b>
    public static <T> T secureLog(String message, T value) {
        if (isDebug) {
            return consoleLog(message, value);
        } else {
            return value;
        }
    }

}
