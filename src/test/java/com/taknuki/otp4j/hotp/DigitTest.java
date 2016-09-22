package com.taknuki.otp4j.hotp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.taknuki.otp4j.hotp.Digit;

public class DigitTest {

	@Test
	public void testDigit001() throws Exception {
		Digit digit = Digit.SIX;
		assertThat(digit.getDigit(), is(6));
		assertThat(digit.toPowerOfTen(), is(power(10, 6)));
	}

	@Test
	public void testDigit002() throws Exception {
		Digit digit = Digit.SEVEN;
		assertThat(digit.getDigit(), is(7));
		assertThat(digit.toPowerOfTen(), is(power(10, 7)));
	}

	@Test
	public void testDigit003() throws Exception {
		Digit digit = Digit.EIGHT;
		assertThat(digit.getDigit(), is(8));
		assertThat(digit.toPowerOfTen(), is(power(10, 8)));
	}

	private int power(int base, int exp) {
		if (exp > 0) {
			return base * power(base, exp - 1);
		} else {
			return 1;
		}
	}
}
