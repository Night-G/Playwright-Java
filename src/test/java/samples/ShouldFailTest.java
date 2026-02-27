package samples;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShouldFailTest {
    @Test
    void shouldFail() {
        assertFalse(true);
    }
}
