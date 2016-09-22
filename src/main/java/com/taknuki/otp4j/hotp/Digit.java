package com.taknuki.otp4j.hotp;

/**
 * Number of digits in an HOTP value RFC4226 specifies Implementations MUST
 * extract a 6-digit code at a minimum and possibly 7 and 8-digit code.
 * 
 * @author taknuki
 * @see <a href="https://tools.ietf.org/html/rfc4226#section-5.3">RFC4226</a>
 *
 */
public enum Digit {
	SIX(6, 1000000), SEVEN(7, 10000000), EIGHT(8, 100000000),;

	private final int digit;
	private final int powerOfTen;

	private Digit(final int digit, final int powerOfTen) {
		this.digit = digit;
		this.powerOfTen = powerOfTen;
	}

	public int getDigit() {
		return this.digit;
	}

	public int toPowerOfTen() {
		return this.powerOfTen;
	}

}
