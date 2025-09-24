package io.zestledger.domain.user.vo.fullname;

import static org.junit.jupiter.api.Assertions.*;

import java.text.Normalizer;
import org.junit.jupiter.api.Test;

final class FullNameLengthAndNormalizationTest {

    @Test
    void shouldAcceptExactly255CharsAfterTrim() {
        String t1 = "A".repeat(127);
        String t2 = "A".repeat(127);
        String input = t1 + " " + t2;

        FullName fullName = new FullName(input);
        assertEquals(input, fullName.value());
    }

    @Test
    void shouldReject256CharsAfterTrim() {
        String t1 = "A".repeat(128);
        String t2 = "A".repeat(127);
        String input = t1 + " " + t2;
        assertEquals(256, input.length());
        assertThrowsExactly(IllegalFullNameException.class, () -> new FullName(input));
    }

    @Test
    void shouldNormalizeToNFC() {
        // "José Garcia" with accent
        String decomposed = "Jose\u0301 Garcia"; // e + combining acute accent
        FullName fullName = new FullName(decomposed);
        assertTrue(
                Normalizer.isNormalized(fullName.value(), Normalizer.Form.NFC),
                "Value should be NFC-normalized");
        assertEquals("José Garcia", fullName.value());
    }
}
