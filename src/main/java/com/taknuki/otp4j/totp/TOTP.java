package com.taknuki.otp4j.totp;

import com.taknuki.otp4j.hotp.Digit;
import com.taknuki.otp4j.hotp.HOTP;
import com.taknuki.otp4j.hotp.SupportedCrypto;

/**
 * Implementation of Time-based One-time Password Algorithm
 * 
 * @author taknuki
 * @see <a href="https://tools.ietf.org/html/rfc6238">RFC6238</a>
 */
public class TOTP {
	private final TOTPParameter param;

	/**
	 * create TOTP generator instance with default TOTP parameter
	 */
	public TOTP() {
		this(new TOTPParameter());
	}

	/**
	 * create TOTP generator instance with given TOTP parameter
	 * 
	 * @param param
	 *            TOTP parameter
	 */
	public TOTP(TOTPParameter param) {
		this.param = param;
	}

	public TOTPParameter getParameter() {
		return this.param;
	}

	/**
	 * generate TOTP value of six digit with HMAC-SHA1 algorithm
	 * 
	 * @param key
	 *            shared secret key
	 * @return TOTP value
	 */
	public String generate(final String key) {
		return generate(key, Digit.SIX, SupportedCrypto.HmacSHA1);
	}

	/**
	 * generate TOTP value of given digit with given HMAC algorithm
	 * 
	 * @param key
	 *            shared secret key
	 * @param digit
	 *            number of digits in an TOTP value
	 * @param crypto
	 *            HMAC algorithm
	 * @return TOTP value
	 */
	public String generate(final String key, final Digit digit, final SupportedCrypto crypto) {
		return HOTP.generate(key, getTimeStepCountHexString(), digit, crypto);
	}

	/**
	 * generate TOTP value of given digit with given HMAC algorithm TOTP value
	 * is calculated by using given current time instead of current system time
	 * 
	 * @param key
	 *            shared secret key
	 * @param currentTime
	 *            current time used to calculate TOTP value
	 * @param digit
	 *            number of digits in an TOTP value
	 * @param crypto
	 *            HMAC algorithm
	 * @return TOTP value
	 */
	public String generate(final String key, final long currentTime, final Digit digit, final SupportedCrypto crypto) {
		return HOTP.generate(key, getTimeStepCountHexString(currentTime), digit, crypto);
	}

	/**
	 * output = (Current Unix time - T0) / X, where the default floor function
	 * is used in the computation.
	 * 
	 * @param currentTime
	 *            current unix time
	 * @return time step count
	 */
	private long getTimeStepCount(long currentTime) {
		return (currentTime - this.param.getStartTime()) / this.param.getTimeStep();
	}

	/**
	 * output = (Current Unix time - T0) / X, where the default floor function
	 * is used in the computation.
	 * 
	 * output string is zero padded for compliant with base RFC 4226 (HOTP)
	 * 
	 * @return hex string
	 */
	private String getTimeStepCountHexString() {
		return getTimeStepCountHexString(System.currentTimeMillis());
	}

	/**
	 * output = (current_time - start_time) / time_step_unit, where the default
	 * floor function is used in the computation.
	 * 
	 * output string is zero padded for compliant with base RFC 4226 (HOTP)
	 * 
	 * @param currentTime
	 *            current unix time
	 * @return hex string
	 */
	private String getTimeStepCountHexString(long currentTime) {
		String hex = Long.toHexString(getTimeStepCount(currentTime)).toUpperCase();
		return hex.length() > 16 ? hex : String.format("%16s", hex).replace(" ", "0");
	}

}
