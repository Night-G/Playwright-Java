package fixtures.playwrightOptions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.nio.file.Paths;

/// Sets options, such as 'headless', 'baseUrl'
public class SystemPropertiesPlaywrightOptions implements OptionsFactory {
    private final String DEF_URL;
    private final String DEF_VIDEODIR;
    private final Boolean DEF_HEADLESS;

    public SystemPropertiesPlaywrightOptions(){
        DEF_URL = DefaultValuesConfig.BASE_URL.toLowerCase();
        DEF_HEADLESS = DefaultValuesConfig.HEADLESS;
        DEF_VIDEODIR = DefaultValuesConfig.VIDEODIR;
    }

    @Override
    public Options getOptions() {
        boolean headless = Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", String.valueOf(DEF_HEADLESS)));
        String baseUrl = System.getenv().getOrDefault("BASEURL", DEF_URL);

        Browser.NewContextOptions ctx = new Browser.NewContextOptions()
                .setBaseURL(baseUrl)
                .setRecordVideoDir(Paths.get(System.getenv().getOrDefault("VIDEODIR", DEF_VIDEODIR)));

        return new Options().setHeadless(headless).setContextOptions(ctx);
    }
}
