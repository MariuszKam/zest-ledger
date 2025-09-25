package io.zestledger.domain.entity.role.vo.rolename;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Objects;
import java.util.regex.Pattern;

public record RoleName(String name) {

    private static final Pattern PATTERN = Pattern.compile("^[A-Z]++(?:_?[A-Z])*+$");

    public RoleName {
        Objects.requireNonNull(name, MessageUtils.messageForCannotBeNull("Role Name"));
        name = name.trim();
        name = name.replaceAll("[\\s.-]", "_");
        if (name.length() < 2 || name.length() > 32) {
            throw new IllegalRoleNameException(
                    "Role name cannot be less than 2 characters or longer than 32 characters");
        }
        if (!PATTERN.matcher(name).matches()) {
            throw new IllegalRoleNameException("Name has to be upper snake case");
        }
    }

    public static RoleName from(String name) {
        return new RoleName(name);
    }
}
