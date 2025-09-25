package io.zestledger.domain.entity.permission.vo.permissionname;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Objects;
import java.util.regex.Pattern;

public record PermissionName(String name) {

    private static final Pattern PATTERN = Pattern.compile("^[A-Z]++(?:_?[A-Z])*+$");

    public PermissionName {
        Objects.requireNonNull(name, MessageUtils.messageForCannotBeNull("Permission Name"));
        name = name.trim();
        name = name.replaceAll("[\\s.-]", "_");
        if (name.length() < 2 || name.length() > 48) {
            throw new IllegalPermissionNameException(
                    "Permission cannot be less than 2 characters, and longer than 48 characters");
        }
        if (!PATTERN.matcher(name).matches()) {
            throw new IllegalPermissionNameException("Name has to be upper snake case");
        }
    }

    public static PermissionName from(String name) {
        return new PermissionName(name);
    }
}
