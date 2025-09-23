package io.zestledger.domain.utils;

public class MessageUtils {

    private MessageUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static String messageForCannotBeNull(String item) {
        return item + " cannot be null";
    }
}
