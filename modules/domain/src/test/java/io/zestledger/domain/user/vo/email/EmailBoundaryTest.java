package io.zestledger.domain.user.vo.email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class EmailBoundaryTest {

    @Test
    void shouldAcceptLocalLengthExactly64() {
        String local64 = "exampleoflocalwithsixtyfourcharacterslongshouldpassinourprogramm";
        Email email = Email.from(local64 + "@ex.com");
        assertEquals(local64 + "@ex.com", email.value());
    }

    @Test
    void shouldAcceptDomainLabelExactly63() {
        String label63 = "a".repeat(63);
        Email email = Email.from("x@" + label63 + ".com");
        assertEquals("x@" + label63 + ".com", email.value());
    }

    @Test
    void shouldAcceptTotalLengthExactly254() {
        String email254 =
                "thisemailwillhave254characterssoitshouldpass12345a@thislabelitsfirst-label.thislabelitssecond-label.thislabelisanother-label.andyetanotherlabel-label.thislabelisnotlastlabel-label.thislabelisalmostlastlabel-label.wearealmosthere-label.thislabelislast.com";
        Email email = Email.from(email254);
        assertEquals(email254, email.value());
    }
}
