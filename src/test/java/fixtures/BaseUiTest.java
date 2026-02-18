package fixtures;


import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.junit.UsePlaywright;
import fixtures.extensions.FailureScreenshotExtension;
import fixtures.playwrightOptions.SystemPropertiesPlaywrightOptions;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@UsePlaywright(SystemPropertiesPlaywrightOptions.class)
public abstract class BaseUiTest{

    private static final Logger log = LoggerFactory.getLogger(BaseUiTest.class);
    @RegisterExtension
    protected FailureScreenshotExtension onFail = new FailureScreenshotExtension();

    @BeforeEach
    void wireFailureCapture(Page page){
        onFail.captureFrom(page);

        page.context().tracing().start(
                new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterEach
    void exportTrace(Page page, TestInfo info){
        String safeName = info.getDisplayName().replaceAll("[^a-zA-Z0-9-_.]","_");
        String testName = safeName + "_" + UUID.randomUUID();
        Path out = Paths.get("target/traces", testName+".zip");

        page.context().tracing().stop(new Tracing.StopOptions().setPath(out));

        try{
            Allure.addAttachment("Trace","application/zip", Files.newInputStream(out),"zip");

        } catch(IOException e){
            log.warn("Traces could not be attached {}",e.getMessage());
        }
    }
}
