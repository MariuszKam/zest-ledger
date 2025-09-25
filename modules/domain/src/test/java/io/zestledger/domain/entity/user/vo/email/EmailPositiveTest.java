package io.zestledger.domain.entity.user.vo.email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class EmailPositiveTest {

    @Test
    void shouldKeepCanonical_simple() {
        Email email = Email.from("alice@example.com");
        assertEquals("alice@example.com", email.value());
    }

    @Test
    void shouldPreserveLocalAndLowerDomain() {
        Email email = Email.from("BoB.huJe@DodA-ar.io.CoM");
        assertEquals("BoB.huJe@doda-ar.io.com", email.value());
    }

    @Test
    void shouldTrimOuterWhitespace() {
        Email email = Email.from("  alice@example.com  ");
        assertEquals("alice@example.com", email.value());
    }

    @Test
    void shouldAcceptDotAtomWithSymbols() {
        Email email = Email.from("a.b+c-d_e%f@example.co.uk");
        assertEquals("a.b+c-d_e%f@example.co.uk", email.value());
    }

    @Test
    void shouldAcceptPunycodeDomain() {
        Email email = Email.from("john@xn--d1acpjx3f.xn--p1ai");
        assertEquals("john@xn--d1acpjx3f.xn--p1ai", email.value());
    }
}
