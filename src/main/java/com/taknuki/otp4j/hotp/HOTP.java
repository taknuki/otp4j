package com.taknuki.otp4j.hotp;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * Implementation o HMAC-Based One-Time Password Algorithm
 * 
 * @author taknuki
 * @see <a href="https://tools.ietf.org/html/rfc4226">RFC4226</a>
 */
public class HOTP {
	/**
	 * number of digits in an HOTP value
	 */
	private final Digit digit;

	/**
	 * HMAC algorithm
	 */
	private final SupportedCrypto crypto;

	/**
	 * create HOTP generator instance with six digit and HMAC-SHA1 algorithm
	 */
	public HOTP() {
		this(Digit.SIX, SupportedCrypto.HmacSHA1);
	}

	/**
	 * create HOTP generator instance
	 * 
	 * @param digit
	 *            number of digits in an HOTP value
	 * @param crypto
	 *            HMAC algorithm
	 */
	public HOTP(final Digit digit, final SupportedCrypto crypto) {
		this.digit = digit;
		this.crypto = crypto;
	}

	public Digit getDigit() {
		return this.digit;
	}

	public SupportedCrypto getCrypto() {
		return this.crypto;
	}

	/**
	 * instance method version of HOTP value generator
	 * 
	 * @param key
	 *            shared secret between client and server
	 * @param movingFactor
	 *            8-byte counter value, the moving factor. This counter MUST be
	 *            synchronized between the HOTP generator (client) and the HOTP
	 *            validator (server).
	 * @return HOTP value
	 */
	public String generate(final String key, final String movingFactor) {
		return generate(key, movingFactor, this.digit, this.crypto);
	}

	/**
	 * @see <a href="https://tools.ietf.org/html/rfc4226#section-5.3">RFC4226
	 *      </a>
	 * @param key
	 *            shared secret between client and server
	 * @param movingFactor
	 *            8-byte counter value, the moving factor. This counter MUST be
	 *            synchronized between the HOTP generator (client) and the HOTP
	 *            validator (server).
	 * @param digit
	 *            number of digits in an HOTP value
	 * @param crypto
	 *            HMAC algorithm
	 * @return HOTP value
	 */
	public static String generate(final String key, final String movingFactor, final Digit digit,
			final SupportedCrypto crypto) {
		// Step 1: Generate an HMAC-SHA-1 value Let HS = HMAC-SHA-1(K,C)
		// HS is a 20-byte string
		byte[] hash = hmac_sha(crypto, key, movingFactor);

		// Step 2: Generate a 4-byte string (Dynamic Truncation)
		int snum = truncateHash(hash);

		// Step 3: Compute an HOTP value
		int otp = snum % digit.toPowerOfTen();
		return String.format("%0" + digit.getDigit() + "d", otp);
	}

	/**
	 * @see <a href="https://tools.ietf.org/html/rfc2104">RFC2104</a>
	 * @param crypto
	 *            HMAC algorithm
	 * @param key
	 *            secret key
	 * @param text
	 *            The data over which HMAC is computed.
	 * @return HMAC
	 */
	private static byte[] hmac_sha(SupportedCrypto crypto, String key, String text) {
		return hmac_sha(crypto, hexStr2Bytes(key), hexStr2Bytes(text));
	}

	/**
	 * @see <a href="https://tools.ietf.org/html/rfc2104">RFC2104</a>
	 * @param crypto
	 *            HMAC algorithm
	 * @param keyBytes
	 *            secret key
	 * @param textBytes
	 *            The data over which HMAC is computed.
	 * @return HMAC
	 */
	private static byte[] hmac_sha(SupportedCrypto crypto, byte[] keyBytes, byte[] textBytes) {
		try {
			Mac hmac = Mac.getInstance(crypto.toAlgorithmName());
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(textBytes);
		} catch (NoSuchAlgorithmException nae) {
			throw new IllegalArgumentException(nae);
		} catch (InvalidKeyException ike) {
			throw new IllegalArgumentException(ike);
		}
	}

	/**
	 * convert hex string to byte array
	 * 
	 * @param hex
	 * @return byte array representing hex string
	 */
	private static byte[] hexStr2Bytes(String hex) {
		// Adding one byte to get the right conversion
		// Values starting with multiple "0"s can be converted precisely
		// "1" -> 1, "001" -> 01
		byte[] byteArray = new BigInteger("10" + hex, 16).toByteArray();
		return Arrays.copyOfRange(byteArray, 1, byteArray.length);
	}

	/**
	 * DT(String) // String = String[0]...String[19]</br>
	 * Let OffsetBits be the low-order 4 bits of String[19]</br>
	 * Offset = StToNum(OffsetBits) // 0 <= OffSet <= 15</br>
	 * Let P = String[OffSet]...String[OffSet+3]</br>
	 * Return the Last 31 bits of P</br>
	 * 
	 * @see <a href="https://tools.ietf.org/html/rfc4226#section-5.3">RFC4226
	 *      </a>
	 * @param hash
	 * @return
	 */
	private static int truncateHash(final byte[] hash) {
		int offset = hash[hash.length - 1] & 0xf;
		return ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8)
				| (hash[offset + 3] & 0xff);
	}

}
