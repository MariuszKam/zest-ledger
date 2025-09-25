package io.zestledger.domain.entity.user.vo.email;

import io.zestledger.domain.utils.MessageUtils;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public record Email(String value) {

    private static final Pattern LOCAL_PART_PATTERN =
            Pattern.compile("^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]++$");

    private static final Pattern DOMAIN_PATTERN =
            Pattern.compile("^[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?$");

    public Email {
        Objects.requireNonNull(value, MessageUtils.messageForCannotBeNull("Email"));
        value = value.trim();

        validateEmail(value);

        String[] splitEmail = StringUtils.split(value, "@");

        String localPart = splitEmail[0];
        validateLocalPart(localPart);

        String domainPart = splitEmail[1];

        validateDomainPart(domainPart);

        value = localPart + "@" + domainPart.toLowerCase(Locale.ROOT);
    }

    public static Email from(String value) {
        return new Email(value);
    }

    private void validateEmail(String value) {
        if (value.length() > 254) {
            throw new EmailLengthException();
        }

        int count = StringUtils.countMatches(value, "@");
        if (count != 1) {
            throw new AtEmailException("At [@] has to exactly once in email");
        }

        int atIndex = value.indexOf("@");
        if (atIndex == 0 || atIndex == value.length() - 1) {
            throw new AtEmailException("At [@] cannot be at beginning or end of email");
        }
    }

    private void validateLocalPart(String localPart) {
        if (localPart.length() > 64) {
            throw new LocalEmailLengthException();
        }

        if (localPart.contains("..")) {
            throw new IllegalLocalEmailException("Local part of email cannot contain [..]");
        }

        if (localPart.startsWith(".") || localPart.endsWith(".")) {
            throw new IllegalLocalEmailException("Local part cannot start or end with [.]");
        }

        String[] localParts = StringUtils.split(localPart, ".");
        for (String part : localParts) {
            if (!LOCAL_PART_PATTERN.matcher(part).matches()) {
                throw new IllegalLocalEmailException(
                        "Use of illegal character in local part of email");
            }
        }
    }

    private void validateDomainPart(String domainPart) {
        if (domainPart.length() > 253) {
            throw new DomainEmailLengthException();
        }

        if (!domainPart.contains(".")) {
            throw new IllegalDomainEmailException("Domain part must have at least one [.]");
        }

        if (domainPart.contains("..")) {
            throw new IllegalDomainEmailException("Domain part cannot have two dots [..]");
        }

        if (domainPart.startsWith(".") || domainPart.endsWith(".")) {
            throw new IllegalDomainEmailException("Domain part cannot start or end with [.]");
        }

        String[] labels = StringUtils.splitPreserveAllTokens(domainPart, ".");
        for (String label : labels) {
            if (!DOMAIN_PATTERN.matcher(label).matches()) {
                throw new IllegalDomainEmailException(
                        "Use of illegal character in domain label of email");
            }
        }
    }
}
