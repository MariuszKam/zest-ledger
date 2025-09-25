package io.zestledger.domain.entity.permission.vo.permissionname;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class PermissionNameTest {

	@Nested
	@DisplayName("Positive cases")
	class Positive {

		@Test
		@DisplayName("Accepts single uppercase word (length within 2..48)")
		void acceptsSingleUppercaseWord() {
			PermissionName n = PermissionName.from("ORDER");
			assertEquals("ORDER", n.name());
		}

		@Test
		@DisplayName("Accepts UPPER_SNAKE_CASE with single underscores")
		void acceptsUpperSnakeCase() {
			PermissionName n = PermissionName.from("ORDER_REFUND");
			assertEquals("ORDER_REFUND", n.name());
		}

		@Test
		@DisplayName("Trims leading/trailing spaces")
		void trimsSpaces() {
			PermissionName n = PermissionName.from("  ORDER_REFUND  ");
			assertEquals("ORDER_REFUND", n.name());
		}

		@Test
		@DisplayName("Normalizes space/hyphen/dot to underscore")
		void normalizesSeparatorsToUnderscore() {
			assertEquals("ORDER_REFUND", PermissionName.from("ORDER REFUND").name());
			assertEquals("ORDER_REFUND", PermissionName.from("ORDER-REFUND").name());
			assertEquals("ORDER_REFUND", PermissionName.from("ORDER.REFUND").name());
		}

		@Test
		@DisplayName("Allows minimal length 2")
		void allowsMinLengthTwo() {
			PermissionName n = PermissionName.from("AB");
			assertEquals("AB", n.name());
		}

		@Test
		@DisplayName("Allows maximal length 48")
		void allowsMaxLengthFortyEight() {
			String max48 = "A".repeat(48);
			PermissionName n = PermissionName.from(max48);
			assertEquals(48, n.name().length());
		}
	}

	@Nested
	@DisplayName("Negative cases")
	class Negative {

		@Test
		@DisplayName("Rejects null (NPE from requireNonNull)")
		void rejectsNull() {
			assertThrowsExactly(NullPointerException.class, () -> PermissionName.from(null));
		}

		@Test
		@DisplayName("Rejects too short (<2) and too long (>48)")
		void rejectsLengthOutOfBounds() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("A"));

			String tooLong49 = "A".repeat(49);
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from(tooLong49));
		}

		@Test
		@DisplayName("Rejects lowercase or mixed case (no auto-uppercasing)")
		void rejectsLowercaseOrMixedCase() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("order"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("Order_Refund"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("order_refund"));
		}

		@Test
		@DisplayName("Rejects digits and special characters")
		void rejectsDigitsAndSpecials() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER1"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER!REFUND"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER@REFUND"));
		}

		@Test
		@DisplayName("Rejects leading or trailing underscores")
		void rejectsLeadingOrTrailingUnderscore() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("_ORDER"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER_"));
		}

		@Test
		@DisplayName("Rejects multiple separators that create consecutive underscores after normalization")
		void rejectsConsecutiveUnderscoresAfterNormalization() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER  REFUND"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER--REFUND"));
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER..REFUND"));
		}

		@Test
		@DisplayName("Rejects double underscores directly")
		void rejectsDoubleUnderscore() {
			assertThrowsExactly(IllegalPermissionNameException.class, () -> PermissionName.from("ORDER__REFUND"));
		}
	}

	@Nested
	@DisplayName("Equality semantics (record)")
	class Equality {

		@Test
		@DisplayName("Record equality is by field value")
		void equalsByValue() {
			PermissionName a = PermissionName.from("ORDER_REFUND");
			PermissionName b = PermissionName.from("ORDER_REFUND");
			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());
		}
	}
}
