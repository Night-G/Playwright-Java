package samples;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class ShouldFailTest {
    @Disabled
    @Test
    void shouldFail() {
        assertFalse(true);
    }
}
