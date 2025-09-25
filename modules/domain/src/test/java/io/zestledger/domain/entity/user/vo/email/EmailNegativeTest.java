package io.zestledger.domain.entity.user.vo.email;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class EmailNegativeTest {

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("\"john.doe\"@example.com", IllegalLocalEmailException.class),
                Arguments.of("john(d)@example.com", IllegalLocalEmailException.class),
                Arguments.of("john @example.com", IllegalLocalEmailException.class),
                Arguments.of("user@[192.168.0.1]", IllegalDomainEmailException.class),
                Arguments.of("user@[IPv6:2001:db8::1]", IllegalDomainEmailException.class),
                Arguments.of("alice.example.com", AtEmailException.class),
                Arguments.of("@example.com", AtEmailException.class),
                Arguments.of("alice@", AtEmailException.class),
                Arguments.of("user@localhost", IllegalDomainEmailException.class),
                Arguments.of("user@-abc.example", IllegalDomainEmailException.class),
                Arguments.of("user@abc-.example", IllegalDomainEmailException.class),
                Arguments.of("user@example.com.", IllegalDomainEmailException.class),
                Arguments.of("user..name@example.com", IllegalLocalEmailException.class),
                // local > 64
                Arguments.of(
                        "exampleoflocalwithoversixtyfourcharacterswhichisnotallowedinouremailsoshouldfail@ex.com",
                        LocalEmailLengthException.class),
                // domain label > 63
                Arguments.of(
                        "localpart@one.secondlablehasoversixtyfourcharacterswhichisnotallowedinouremailsoshouldfail.com",
                        IllegalDomainEmailException.class),
                // total > 254
                Arguments.of(
                        "thisemailwillhaveover254characterssoitshouldfailipsumopsum12345@thislabelitsfirst-label.thislabelitssecond-label.thislabelisanother-label.andyetanotherlabel-label.thislabelisnotlastlabel-label.thislabelisalmostlastlabel-label.wearealmosthere-label.thislabelislast.com",
                        EmailLengthException.class));
    }

    @ParameterizedTest(name = "Should reject \"{0}\" with {1}")
    @MethodSource("cases")
    void shouldRejectWithSpecificException(String input, Class<? extends RuntimeException> ex) {
        assertThrowsExactly(ex, () -> Email.from(input));
    }
}
