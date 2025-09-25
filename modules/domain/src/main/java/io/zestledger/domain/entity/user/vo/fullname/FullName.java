package io.zestledger.domain.entity.user.vo.fullname;

import io.zestledger.domain.utils.MessageUtils;
import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

public record FullName(String value) {

    private static final Pattern PATTERN = Pattern.compile("^\\p{L}++(?:['’-]\\p{L}++)*+$");

    public FullName {
        Objects.requireNonNull(value, MessageUtils.messageForCannotBeNull("Full Name"));
        value = value.trim();
        if (value.length() > 255) {
            throw new IllegalFullNameException("Full name cannot have more than 255 characters");
        }

        String normalize = Normalizer.normalize(value, Normalizer.Form.NFC);

        String canonical = normalize.replaceAll("\\h+", " ");

        String[] parts = canonical.split(" ");

        if (parts.length < 2) {
            throw new IllegalFullNameException("Full name must contain at least two words");
        }

        for (String part : parts) {
            if (!PATTERN.matcher(part).matches()) {
                throw new IllegalFullNameException("Illegal character in a part of a full name");
            }
        }

        value = canonical;
    }
}
