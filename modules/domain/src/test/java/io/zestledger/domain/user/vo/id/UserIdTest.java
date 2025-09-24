package io.zestledger.domain.user.vo.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserIdTest {

    private static final UUID expected = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Test
    void shouldCreatedFromUUID() {
        UserId userId = UserId.from(expected);

        assertEquals(expected, userId.value(), "UUID are not equals");
    }

    @Test
    void shouldParseFromTrimmedString() {
        UserId userId = UserId.from(" 123e4567-e89b-12d3-a456-426614174000 ");

        assertEquals(expected, userId.value(), "UUID are not equals when parse from not trimmed");
    }

    @Test
    void shouldRejectNull() {
        assertThrows(
                NullPointerException.class,
                () -> new UserId(null),
                "Should throw NPE, when Null passed instead of UUID");
    }

    @Test
    void shouldRejectNonUuidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserId.from("abc"),
                "Should throw IllegalArgumentException, when string is incorrect");
    }
}
