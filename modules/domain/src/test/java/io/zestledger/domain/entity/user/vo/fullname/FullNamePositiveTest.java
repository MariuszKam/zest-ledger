package io.zestledger.domain.entity.user.vo.fullname;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class FullNamePositiveTest {

    @Test
    void shouldAcceptFirstAndLast() {
        FullName fullName = new FullName("Anna Kowalska");
        assertEquals("Anna Kowalska", fullName.value());
    }

    @Test
    void shouldAcceptThreeTokens() {
        FullName fullName = new FullName("Jan Maria Rokita");
        assertEquals("Jan Maria Rokita", fullName.value());
    }

    @Test
    void shouldAllowMultipleHyphensInToken() {
        FullName fullName = new FullName("Anne-Marie-Louise Kowalska");
        assertEquals("Anne-Marie-Louise Kowalska", fullName.value());
    }

    @Test
    void shouldAllowApostrophesInToken() {
        FullName fn1 = new FullName("O'Connor Smith");
        assertEquals("O'Connor Smith", fn1.value());

        FullName fn2 = new FullName("Jean-Baptiste d’Arc"); // right single quotation mark
        assertEquals("Jean-Baptiste d’Arc", fn2.value());
    }

    @Test
    void shouldAcceptUnicodeLetters() {
        FullName fullName = new FullName("Łukasz Żółć");
        assertEquals("Łukasz Żółć", fullName.value());
    }

    @Test
    void shouldAcceptHorizontalTabAsSeparator_andPreserveIt() {
        FullName fullName = new FullName("Anna\tKowalska");
        assertEquals("Anna Kowalska", fullName.value());
    }

    @Test
    void shouldTrimOuterWhitespaceOnly() {
        FullName fullName = new FullName("  Jean Baptiste  ");
        assertEquals("Jean Baptiste", fullName.value());
    }
}
