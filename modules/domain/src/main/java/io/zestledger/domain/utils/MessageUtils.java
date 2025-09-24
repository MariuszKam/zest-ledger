package io.zestledger.domain.utils;

public class MessageUtils {

    private MessageUtils() {
        throw new UtilityClassException();
    }

    public static String messageForCannotBeNull(String item) {
        return item + " cannot be null";
    }
}
