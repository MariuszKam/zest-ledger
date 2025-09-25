package io.zestledger.domain.entity.role.vo.id;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Objects;
import java.util.UUID;

public record RoleId(UUID value) {

    public RoleId {
        Objects.requireNonNull(value, MessageUtils.messageForCannotBeNull("Role Id"));
    }

    public static RoleId from(UUID uuid) {
        return new RoleId(uuid);
    }

    public static RoleId from(String name) {
        name = name.trim();
        return new RoleId(UUID.fromString(name));
    }
}
