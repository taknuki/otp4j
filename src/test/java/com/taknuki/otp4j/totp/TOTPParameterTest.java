package com.taknuki.otp4j.totp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.taknuki.otp4j.totp.TOTPParameter;

public class TOTPParameterTest {

	@Test
	public void testTOTPParameter001() throws Exception {
		TOTPParameter totpParameter = new TOTPParameter(2L, 100000L);
		assertThat(totpParameter.getStartTime(), is(100000L));
		assertThat(totpParameter.getTimeStep(), is(2L));
	}

	@Test
	public void testTOTPParameter002() throws Exception {
		TOTPParameter totpParameter = new TOTPParameter(2L);
		assertThat(totpParameter.getStartTime(), is(0L));
		assertThat(totpParameter.getTimeStep(), is(2L));
	}

	@Test
	public void testTOTPParameter003() throws Exception {
		TOTPParameter totpParameter = new TOTPParameter();
		assertThat(totpParameter.getStartTime(), is(0L));
		assertThat(totpParameter.getTimeStep(), is(30000L));
	}

}
