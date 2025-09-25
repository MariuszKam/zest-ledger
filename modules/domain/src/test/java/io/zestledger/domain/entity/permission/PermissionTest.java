package io.zestledger.domain.entity.permission;

import io.zestledger.domain.entity.permission.vo.id.PermissionId;
import io.zestledger.domain.entity.permission.vo.permissionname.PermissionName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class PermissionTest {

	@Nested
	@DisplayName("Creation")
	class Creation {

		@Test
		void shouldCreateWithValidIdAndName() {
			PermissionId id = new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
			PermissionName name = PermissionName.from("ORDER_REFUND");

			Permission p = Permission.create(id, name);

			assertEquals(id, p.id());
			assertEquals("ORDER_REFUND", p.name().name());
		}

		@Test
		void shouldRejectNullIdOnCreate() {
			assertThrowsExactly(NullPointerException.class,
				() -> Permission.create(null, PermissionName.from("ORDER_READ")));
		}

		@Test
		void shouldRejectNullNameOnCreate() {
			PermissionId id = new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000002"));
			assertThrowsExactly(NullPointerException.class,
				() -> Permission.create(id, null));
		}
	}

	@Nested
	@DisplayName("Rename")
	class Rename {

		@Test
		void shouldRenameAndReturnTrue() {
			Permission p = Permission.create(
				new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000003")),
				PermissionName.from("ORDER_READ")
			);

			boolean changed = p.rename(PermissionName.from("ORDER_UPDATE"));

			assertTrue(changed);
			assertEquals("ORDER_UPDATE", p.name().name());
		}

		@Test
		void shouldBeIdempotentWhenRenamingToSameName() {
			Permission p = Permission.create(
				new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000004")),
				PermissionName.from("USER_READ")
			);

			boolean first = p.rename(PermissionName.from("USER_READ"));
			boolean second = p.rename(PermissionName.from("USER_READ"));

			assertFalse(first);
			assertFalse(second);
			assertEquals("USER_READ", p.name().name());
		}

		@Test
		void shouldRejectNullOnRename() {
			Permission p = Permission.create(
				new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000005")),
				PermissionName.from("INVOICE_CREATE")
			);

			assertThrowsExactly(NullPointerException.class, () -> p.rename(null));
		}
	}

	@Nested
	@DisplayName("Equality")
	class Equality {

		@Test
		void shouldHaveEqualityByIdOnlyEvenAfterRename() {
			PermissionId id = new PermissionId(UUID.fromString("00000000-0000-0000-0000-00000000ABCD"));

			Permission a = Permission.create(id, PermissionName.from("AB"));
			Permission b = Permission.create(id, PermissionName.from("BA"));

			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());

			b.rename(PermissionName.from("CD"));

			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());
		}
	}

	@Nested
	@DisplayName("Collections behavior")
	class CollectionsBehavior {

		@Test
		void shouldRemainFindableInHashSetAfterRename() {
			Permission p = Permission.create(
				new PermissionId(UUID.fromString("00000000-0000-0000-0000-000000000006")),
				PermissionName.from("CATALOG_READ")
			);

			Set<Permission> set = new HashSet<>();
			set.add(p);

			p.rename(PermissionName.from("CATALOG_LIST"));

			assertTrue(set.contains(p));
		}
	}
}
