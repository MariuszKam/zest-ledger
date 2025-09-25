package io.zestledger.domain.entity.role.vo.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class RoleIdTest {

    private static final UUID expected = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Test
    void shouldCreatedFromUUID() {
        RoleId roleId = RoleId.from(expected);

        assertEquals(expected, roleId.value(), "UUID are not equals");
    }

    @Test
    void shouldParseFromTrimmedString() {
        RoleId roleId = RoleId.from(" 123e4567-e89b-12d3-a456-426614174000 ");

        assertEquals(expected, roleId.value(), "UUID are not equals when parse from not trimmed");
    }

    @Test
    void shouldRejectNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RoleId(null),
                "Should throw NPE, when Null passed instead of UUID");
    }

    @Test
    void shouldRejectNonUuidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> RoleId.from("abc"),
                "Should throw IllegalArgumentException, when string is incorrect");
    }
}
