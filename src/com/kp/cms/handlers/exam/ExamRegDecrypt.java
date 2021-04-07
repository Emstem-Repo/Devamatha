package com.kp.cms.handlers.exam;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ExamRegDecrypt {

	public static String encrypt(String stringToEncrypt) throws Exception {
		String key = "Microhard";
		// encrypt
		byte[] raw = key.getBytes("UTF8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] pText = stringToEncrypt.getBytes("UTF8");
		byte[] encrypted = cipher.doFinal(pText);
		String encryptedHexString = toHexString(encrypted);
		return encryptedHexString;
	}

	public static String decrypt(String stringToDecrypt)  {

//		String key = "Microhard";
//		// create a key
//		byte[] raw = key.getBytes("UTF8");
//		byte[] cText = toBinArray(stringToDecrypt);
//		SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
//		Cipher cipher = Cipher.getInstance("Blowfish");
//		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//		byte[] decrypted = cipher.doFinal(cText);
//		String decryptedString = new String(decrypted, "UTF8");
		//return decryptedString;
		return stringToDecrypt;

	}

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static String toHexString(byte[] b) {

		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			// look up high nibble char
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]); // fill left with zero
			// bits

			// look up low nibble char
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	private static byte[] toBinArray(String hexStr) {
		byte bArray[] = new byte[hexStr.length() / 2];
		for (int i = 0; i < (hexStr.length() / 2); i++) {
			byte firstNibble = Byte.parseByte(hexStr
					.substring(2 * i, 2 * i + 1), 16); // [x,y)
			byte secondNibble = Byte.parseByte(hexStr.substring(2 * i + 1,
					2 * i + 2), 16);
			int finalByte = (secondNibble) | (firstNibble << 4); //bit-operations
			// only with
			// numbers,
			// not
			// bytes.
			bArray[i] = (byte) finalByte;
		}
		return bArray;
	}
}
