package fixtures.extensions;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.ByteArrayInputStream;

public class FailureScreenshotExtension implements AfterTestExecutionCallback {
    private Page page;

    public FailureScreenshotExtension captureFrom(Page page) {
        this.page = page;
        return this;
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext){
        boolean failed = extensionContext.getExecutionException().isPresent();
        if (failed && page != null) {
            Allure.addAttachment("Current URL", page.url());

            byte[] png = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String name = "Failed-"+extensionContext.getDisplayName();
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png),".png");
        }
    }
}
