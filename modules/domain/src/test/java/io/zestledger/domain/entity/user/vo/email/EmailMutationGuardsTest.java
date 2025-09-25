package io.zestledger.domain.entity.user.vo.email;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

final class EmailMutationGuardsTest {

    @Test
    void domainCannotContainDoubleDot() {
        assertThrowsExactly(IllegalDomainEmailException.class, () -> Email.from("u@a..b.com"));
    }

    @Test
    void domainCannotStartOrEndWithDot() {
        assertThrowsExactly(IllegalDomainEmailException.class, () -> Email.from("u@.example.com"));
        assertThrowsExactly(IllegalDomainEmailException.class, () -> Email.from("u@example.com."));
    }

    @Test
    void localCannotStartOrEndWithDot() {
        assertThrowsExactly(IllegalLocalEmailException.class, () -> Email.from(".u@example.com"));
        assertThrowsExactly(IllegalLocalEmailException.class, () -> Email.from("u.@example.com"));
    }

    @Test
    void illegalCharInLocalSegment() {
        assertThrowsExactly(IllegalLocalEmailException.class, () -> Email.from("a,b@example.com"));
    }

    @Test
    void illegalCharInDomainLabel() {
        assertThrowsExactly(IllegalDomainEmailException.class, () -> Email.from("u@exa_mple.com"));
    }
}
