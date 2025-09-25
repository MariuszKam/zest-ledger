package io.zestledger.domain.entity.permission.vo.id;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Objects;
import java.util.UUID;

public record PermissionId(UUID value) {

    public PermissionId {
        Objects.requireNonNull(value, MessageUtils.messageForCannotBeNull("Permission Id"));
    }

    public static PermissionId from(UUID uuid) {
        return new PermissionId(uuid);
    }

    public static PermissionId from(String name) {
        name = name.trim();
        return new PermissionId(UUID.fromString(name));
    }
}
