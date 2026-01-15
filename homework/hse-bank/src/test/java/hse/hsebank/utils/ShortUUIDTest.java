package hse.hsebank.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShortUUIDTest {

    @Test
    void testGenerate() {
        String shortId = ShortUUID.generate();

        assertNotNull(shortId);
        assertEquals(8, shortId.length());
        assertTrue(shortId.matches("[a-f0-9]{8}"));
    }

    @Test
    void testIsValidShortId() {
        assertTrue(ShortUUID.isValidShortId("abc12345"));
        assertTrue(ShortUUID.isValidShortId("ABCDEF12"));
        assertFalse(ShortUUID.isValidShortId("abc1234")); // too short
        assertFalse(ShortUUID.isValidShortId("abc123456")); // too long
        assertFalse(ShortUUID.isValidShortId("xyz12345")); // invalid chars
        assertFalse(ShortUUID.isValidShortId(null));
        assertFalse(ShortUUID.isValidShortId(""));
    }
}