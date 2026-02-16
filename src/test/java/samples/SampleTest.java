package samples;

import com.microsoft.playwright.Page;
import fixtures.BaseUiTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SampleTest extends BaseUiTest {
    @Test
    public void searchSomewhere(Page page){
        page.navigate("/");
        assertThat(page).hasURL(Pattern.compile("https://.*google\\.com/.*"));
    }
}
