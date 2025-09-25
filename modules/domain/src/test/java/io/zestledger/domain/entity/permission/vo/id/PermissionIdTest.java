package io.zestledger.domain.entity.permission.vo.id;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PermissionIdTest {

	private static final UUID expected = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

	@Test
	void shouldCreatedFromUUID() {
		PermissionId permissionId = PermissionId.from(expected);

		assertEquals(expected, permissionId.value(), "UUID are not equals");
	}

	@Test
	void shouldParseFromTrimmedString() {
		PermissionId permissionId = PermissionId.from(" 123e4567-e89b-12d3-a456-426614174000 ");

		assertEquals(expected, permissionId.value(), "UUID are not equals when parse from not trimmed");
	}

	@Test
	void shouldRejectNull() {
		assertThrows(
			NullPointerException.class,
			() -> new PermissionId(null),
			"Should throw NPE, when Null passed instead of UUID");
	}

	@Test
	void shouldRejectNonUuidString() {
		assertThrows(
			IllegalArgumentException.class,
			() -> PermissionId.from("abc"),
			"Should throw IllegalArgumentException, when string is incorrect");
	}
}
