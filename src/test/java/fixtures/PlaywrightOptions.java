package fixtures;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.nio.file.Paths;

public class PlaywrightOptions implements OptionsFactory {

    @Override
    public Options getOptions() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        String baseUrl = System.getProperty("baseUrl", "https://google.com");
        String apiBaseUrl = System.getProperty("apiUrl", String.valueOf(false));

        Browser.NewContextOptions ctx = new Browser.NewContextOptions()
                .setBaseURL(baseUrl)
                .setRecordVideoDir(Paths.get("target/videos"));

        return new Options().setHeadless(headless).setContextOptions(ctx);
    }
}
