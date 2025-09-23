package io.zestledger.domain.user.vo;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        Objects.requireNonNull(value, MessageUtils.messageForCannotBeNull("User ID"));
    }

    public static UserId from(UUID uuid) {
        return new UserId(uuid);
    }

    public static UserId from(String name) {
        name = name.trim();
        return new UserId(UUID.fromString(name));
    }
}
