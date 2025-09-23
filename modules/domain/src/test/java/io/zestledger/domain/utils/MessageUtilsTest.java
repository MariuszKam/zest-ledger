package io.zestledger.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageUtilsTest {

    @Test
    void shouldPrintCorrectMessageForMessageForCannotBeNull() {
        String actual = MessageUtils.messageForCannotBeNull("Foo");
        assertEquals("Foo cannot be null", actual, "Message for cannot be null should align");
    }
}
