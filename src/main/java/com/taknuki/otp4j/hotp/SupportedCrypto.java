package com.taknuki.otp4j.hotp;

/**
 * Supported HMAC Algorithm
 * 
 * @author taknuki
 *
 */
public enum SupportedCrypto {
	HmacSHA1("HmacSHA1"), HmacSHA256("HmacSHA256"), HmacSHA512("HmacSHA512"),;

	private final String algorithmName;

	private SupportedCrypto(final String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public String toAlgorithmName() {
		return this.algorithmName;
	}

}
