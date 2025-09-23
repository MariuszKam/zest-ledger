package io.zestledger.domain.user.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailTest {

	@ParameterizedTest
	@ValueSource(strings = {"alice@example.com", "a.b+c-d_e%f@example.co.uk", "john@xn--d1acpjx3f.xn--p1ai", "exampleoflocalwithsixtyfourcharacterslongshouldpassinourprogramm@ex.com", "user@a.b.c.d.example"})
	void shouldAccept_SomeEmail(String input) {
		Email email = Email.from(input);
		assertEquals(input, email.value(), "Emails should be equals");
	}

}
