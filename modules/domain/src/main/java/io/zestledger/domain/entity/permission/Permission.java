package io.zestledger.domain.entity.permission;

import io.zestledger.domain.entity.permission.vo.id.PermissionId;
import io.zestledger.domain.entity.permission.vo.permissionname.PermissionName;
import io.zestledger.domain.utils.MessageUtils;

import java.util.Objects;

public final class Permission {

	private final PermissionId id;
	private PermissionName name;

	private Permission(PermissionId id, PermissionName name) {
		this.id = Objects.requireNonNull(id, MessageUtils.messageForCannotBeNull("Permission Id"));
		this.name = Objects.requireNonNull(name, MessageUtils.messageForCannotBeNull("Permission Name"));
	}

	public static Permission create(PermissionId id, PermissionName name) {
		return new Permission(id, name);
	}

	public PermissionId id() {
		return id;
	}

	public PermissionName name() {
		return name;
	}

	public boolean rename(PermissionName name) {
		Objects.requireNonNull(name, MessageUtils.messageForCannotBeNull("Permission Name"));
		if (this.name.equals(name)) {
			return false;
		}
		this.name = name;
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Permission that)) return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Permission{" +
			"id=" + id +
			", name=" + name.name() +
			'}';
	}
}
