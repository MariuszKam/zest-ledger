package io.zestledger.domain.entity.role.vo.rolename;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class RoleNameTest {

	@Nested
	@DisplayName("Positive cases")
	class Positive {

		@Test
		@DisplayName("Accepts single uppercase word")
		void acceptsSingleUppercaseWord() {
			RoleName rn = RoleName.from("ADMIN");
			assertEquals("ADMIN", rn.name());
		}

		@Test
		@DisplayName("Accepts UPPER_SNAKE_CASE with single underscores")
		void acceptsUpperSnakeCase() {
			RoleName rn = RoleName.from("WAREHOUSE_MANAGER");
			assertEquals("WAREHOUSE_MANAGER", rn.name());
		}

		@Test
		@DisplayName("Trims leading/trailing spaces")
		void trimsSpaces() {
			RoleName rn = RoleName.from("  ADMIN  ");
			assertEquals("ADMIN", rn.name());
		}

		@Test
		@DisplayName("Normalizes hyphen/dot/space to underscore (when already uppercase)")
		void normalizesSeparatorsToUnderscore() {
			assertEquals("WAREHOUSE_MANAGER", RoleName.from("WAREHOUSE-MANAGER").name());
			assertEquals("WAREHOUSE_MANAGER", RoleName.from("WAREHOUSE.MANAGER").name());
			assertEquals("WAREHOUSE_MANAGER", RoleName.from("WAREHOUSE MANAGER").name());
		}
	}

	@Nested
	@DisplayName("Negative cases")
	class Negative {

		@Test
		@DisplayName("Rejects null")
		void rejectsNull() {
			assertThrowsExactly(NullPointerException.class, () -> RoleName.from(null));
		}

		@Test
		@DisplayName("Rejects too short (<2) and too long (>32)")
		void rejectsLengthOutOfBounds() {
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("A"));

			String thirtyThree = "A".repeat(33);
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from(thirtyThree));
		}

		@Test
		@DisplayName("Rejects lowercase letters (no auto-uppercasing)")
		void rejectsLowercase() {
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("admin"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("Admin"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("admin_user"));
		}

		@Test
		@DisplayName("Rejects digits and other illegal characters")
		void rejectsDigitsAndIllegalChars() {
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("ADMIN1"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("ADMIN!"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("SALES@MANAGER"));
		}

		@Test
		@DisplayName("Rejects leading/trailing underscores")
		void rejectsLeadingOrTrailingUnderscore() {
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("_ADMIN"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("ADMIN_"));
		}

		@Test
		@DisplayName("Rejects double or multiple underscores")
		void rejectsDoubleUnderscore() {
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("ADMIN__SUPER"));
		}

		@Test
		@DisplayName("Rejects multiple separators that normalize to consecutive underscores")
		void rejectsMultipleSeparatorsCollapsingToMultipleUnderscores() {
			// "three spaces" -> "___" after replace; regex nie dopuszcza "__"
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("WAREHOUSE   MANAGER"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("WAREHOUSE--MANAGER"));
			assertThrowsExactly(IllegalRoleNameException.class, () -> RoleName.from("WAREHOUSE..MANAGER"));
		}
	}

	@Nested
	@DisplayName("Equality semantics (record)")
	class Equality {

		@Test
		@DisplayName("Record equality is by field value")
		void equalsByValue() {
			RoleName a = RoleName.from("ADMIN");
			RoleName b = RoleName.from("ADMIN");
			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());
		}
	}
}
