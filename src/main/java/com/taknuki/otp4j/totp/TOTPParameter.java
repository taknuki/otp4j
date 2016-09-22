package com.taknuki.otp4j.totp;

/**
 * TOTP system parameter
 * 
 * @author taknuki
 *
 */
public class TOTPParameter {
	/**
	 * the time step in seconds (default value = 30 seconds)
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc6238#section-4.1">RFC6238
	 *      </a>
	 */
	private final long timeStepUnitInMilliSeconds;

	/**
	 * the Unix time to start counting time steps (default value is 0, i.e., the
	 * Unix epoch)
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc6238#section-4.1">RFC6238
	 *      </a>
	 */
	private final long startTimeInUnixTime;

	/**
	 * the time step in seconds (default value = 30 seconds)
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc6238#section-4.1">RFC6238
	 *      </a>
	 */
	private static final long DEFAULT_TIME_STEP_UNIT_IN_MILLI_SECONDS = 30000;

	/**
	 * the Unix time to start counting time steps (default value is 0, i.e., the
	 * Unix epoch)
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc6238#section-4.1">RFC6238
	 *      </a>
	 */
	private static final long DEFAULT_START_TIME_IN_UNIX_TIME = 0;

	public TOTPParameter() {
		this(DEFAULT_TIME_STEP_UNIT_IN_MILLI_SECONDS, DEFAULT_START_TIME_IN_UNIX_TIME);
	}

	public TOTPParameter(final long timeStepUnitInMilliSeconds) {
		this(timeStepUnitInMilliSeconds, DEFAULT_START_TIME_IN_UNIX_TIME);
	}

	public TOTPParameter(final long timeStepUnitInMilliSeconds, final long startTimeInUnixTime) {
		this.timeStepUnitInMilliSeconds = timeStepUnitInMilliSeconds;
		this.startTimeInUnixTime = startTimeInUnixTime;
	}

	public long getTimeStep() {
		return this.timeStepUnitInMilliSeconds;
	}

	public long getStartTime() {
		return this.startTimeInUnixTime;
	}

}
