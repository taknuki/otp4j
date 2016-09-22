package com.taknuki.otp4j.totp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taknuki.otp4j.hotp.Digit;
import com.taknuki.otp4j.hotp.SupportedCrypto;
import com.taknuki.otp4j.mock.CurrentTimeMock;

public class TOTPTest {

	@Test
	public void testTOTPTimeStepCount001() throws Exception {
		long timeStepUnit = 2L;
		long startTime = 100000L;
		long expectedTimeStepCount = 497108018117L;
		String expectedTimeStepCountHexString = "00000073BDF263C5";
		testTOTPTimeStepCount(timeStepUnit, startTime, expectedTimeStepCount, expectedTimeStepCountHexString);
	}

	@Test
	public void testTOTPTimeStepCount002() throws Exception {
		long timeStepUnit = 2L;
		long startTime = 0L;
		long expectedTimeStepCount = 497108068117L;
		String expectedTimeStepCountHexString = "00000073BDF32715";
		testTOTPTimeStepCount(timeStepUnit, startTime, expectedTimeStepCount, expectedTimeStepCountHexString);
	}

	@Test
	public void testTOTPTimeStepCount003() throws Exception {
		long timeStepUnit = 30000L;
		long startTime = 0L;
		long expectedTimeStepCount = 33140537L;
		String expectedTimeStepCountHexString = "0000000001F9AF39";
		testTOTPTimeStepCount(timeStepUnit, startTime, expectedTimeStepCount, expectedTimeStepCountHexString);
	}

	@Test
	public void testTOTPGenerator001() throws Exception {
		// Seed for HMAC-SHA1 - 20 bytes
		String seed = "3132333435363738393031323334353637383930";
		long testTimes[] = { 59L, 1111111109L, 1111111111L, 1234567890L, 2000000000L, 20000000000L };
		String expectedTOTPs[] = { "94287082", "07081804", "14050471", "89005924", "69279037", "65353130" };
		for (int i = 0; i < testTimes.length; i++) {
			testHMACSHA1(seed, testTimes[i], expectedTOTPs[i]);
		}
	}

	@Test
	public void testTOTPGenerator002() throws Exception {
		// Seed for HMAC-SHA256 - 32 bytes
		String seed = "3132333435363738393031323334353637383930" + "313233343536373839303132";
		long testTimes[] = { 59L, 1111111109L, 1111111111L, 1234567890L, 2000000000L, 20000000000L };
		String expectedTOTPs[] = { "46119246", "68084774", "67062674", "91819424", "90698825", "77737706" };
		for (int i = 0; i < testTimes.length; i++) {
			testHMACSHA256(seed, testTimes[i], expectedTOTPs[i]);
		}
	}

	@Test
	public void testTOTPGenerator003() throws Exception {
		// Seed for HMAC-SHA512 - 64 bytes
		String seed = "3132333435363738393031323334353637383930" + "3132333435363738393031323334353637383930"
				+ "3132333435363738393031323334353637383930" + "31323334";
		long testTimes[] = { 59L, 1111111109L, 1111111111L, 1234567890L, 2000000000L, 20000000000L };
		String expectedTOTPs[] = { "90693936", "25091201", "99943326", "93441116", "38618901", "47863826" };
		for (int i = 0; i < testTimes.length; i++) {
			testHMACSHA512(seed, testTimes[i], expectedTOTPs[i]);
		}
	}

	@Test
	public void testTOTPConstructor001() throws Exception {
		TOTP generator = new TOTP();
		TOTPParameter totpParameter = generator.getParameter();
		assertThat(totpParameter.getStartTime(), is(0L));
		assertThat(totpParameter.getTimeStep(), is(30000L));
	}

	@Test
	public void testTOTPGenerator004() throws Exception {
		TOTP generator = new TOTP();
		assertThat(generator.generate("3132333435363738393031323334353637383930"), is("540238"));
	}

	@BeforeClass
	public static void before() throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		new CurrentTimeMock(df.parse("2001-07-04T12:08:56.235"));
	}

	private void testTOTPTimeStepCount(final long timeStepUnit, final long startTime, final long expectedTimeStepCount,
			final String expectedTimeStepCountHexString) throws Exception {
		TOTP generator = new TOTP(new TOTPParameter(timeStepUnit, startTime));
		Method getTimeStepCount = TOTP.class.getDeclaredMethod("getTimeStepCount", long.class);
		getTimeStepCount.setAccessible(true);
		Method getTimeStepCountHexString = TOTP.class.getDeclaredMethod("getTimeStepCountHexString");
		getTimeStepCountHexString.setAccessible(true);
		assertThat(getTimeStepCount.invoke(generator, System.currentTimeMillis()), is(expectedTimeStepCount));
		assertThat(getTimeStepCountHexString.invoke(generator), is(expectedTimeStepCountHexString));
	}

	private void testHMACSHA1(final String seed, final long testTime, final String expectedTOTP) {
		testHMACSHA(seed, testTime, expectedTOTP, SupportedCrypto.HmacSHA1);
	}

	private void testHMACSHA256(final String seed, final long testTime, final String expectedTOTP) {
		testHMACSHA(seed, testTime, expectedTOTP, SupportedCrypto.HmacSHA256);
	}

	private void testHMACSHA512(final String seed, final long testTime, final String expectedTOTP) {
		testHMACSHA(seed, testTime, expectedTOTP, SupportedCrypto.HmacSHA512);
	}

	private void testHMACSHA(final String seed, final long testTime, final String expectedTOTP,
			final SupportedCrypto crypto) {
		TOTP generator = new TOTP(new TOTPParameter(30000));
		assertThat(generator.generate(seed, testTime * 1000, Digit.EIGHT, crypto), is(expectedTOTP));
	}

}
