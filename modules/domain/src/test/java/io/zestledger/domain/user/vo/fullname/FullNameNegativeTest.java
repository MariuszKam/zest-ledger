package io.zestledger.domain.user.vo.fullname;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class FullNameNegativeTest {

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("Madonna", IllegalFullNameException.class), // single token
                Arguments.of("Jan3 Kowalski", IllegalFullNameException.class), // digit inside
                Arguments.of("Anna@Kowalska", IllegalFullNameException.class), // forbidden punct
                Arguments.of("-Anna Kowalska", IllegalFullNameException.class), // leading hyphen
                Arguments.of(
                        "Anna -Kowalska", IllegalFullNameException.class), // token starts with '-'
                Arguments.of(
                        "Jean--Pierre Kowalski",
                        IllegalFullNameException.class), // double hyphen without letters
                Arguments.of("O''Neill Anna", IllegalFullNameException.class), // double apostrophe
                Arguments.of(
                        "'Anna Kowalska", IllegalFullNameException.class), // leading apostrophe
                Arguments.of(
                        "Jean–Pierre Kowalski",
                        IllegalFullNameException.class), // EN DASH (U+2013), not allowed
                Arguments.of("   ", IllegalFullNameException.class) // empty after trim
                );
    }

    @ParameterizedTest(name = "Should reject \"{0}\" with {1}")
    @MethodSource("cases")
    void shouldRejectWithSpecificException(String input, Class<? extends RuntimeException> ex) {
        assertThrowsExactly(ex, () -> new FullName(input));
    }
}
