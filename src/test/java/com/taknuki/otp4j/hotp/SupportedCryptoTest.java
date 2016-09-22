package com.taknuki.otp4j.hotp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.taknuki.otp4j.hotp.SupportedCrypto;

public class SupportedCryptoTest {

	@Test
	public void testSupportedCryptoTest001() throws Exception {
		SupportedCrypto crypto = SupportedCrypto.HmacSHA1;
		assertThat(crypto.toAlgorithmName(), is("HmacSHA1"));
	}

	@Test
	public void testSupportedCryptoTest002() throws Exception {
		SupportedCrypto crypto = SupportedCrypto.HmacSHA256;
		assertThat(crypto.toAlgorithmName(), is("HmacSHA256"));
	}

	@Test
	public void testSupportedCryptoTest003() throws Exception {
		SupportedCrypto crypto = SupportedCrypto.HmacSHA512;
		assertThat(crypto.toAlgorithmName(), is("HmacSHA512"));
	}

}
