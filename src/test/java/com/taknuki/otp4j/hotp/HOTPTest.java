package com.taknuki.otp4j.hotp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HOTPTest {

	@Test
	public void testHOTPConstructor001() {
		HOTP generator = new HOTP();
		assertThat(generator.getDigit(), is(Digit.SIX));
		assertThat(generator.getCrypto(), is(SupportedCrypto.HmacSHA1));
	}

	@Test
	public void testHOTPGenerator001() {
		HOTP generator = new HOTP(Digit.EIGHT, SupportedCrypto.HmacSHA1);
		assertThat(generator.generate("3132333435363738393031323334353637383930", "0000000000000001"), is("94287082"));
	}
}
