package com.kp.cms.utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.kp.cms.constants.CMSConstants;

/**
 * This class is used to encrypt the given string using MD5 one way hash
 * algorithm.
 * 
 * @author kalyan
 * 
 */
public class EncryptUtil {
	
	private static final Logger log = Logger.getLogger(EncryptUtil.class);
	private static EncryptUtil instance;
	private SecretKey key=null;
	private Cipher desCipher=null;
	/**
	 * Constructor
	 * 
	 */
	private EncryptUtil() {
		loadCipher();
	}
	/**
	 * loading cipher
	 * @return
	 */
	private Cipher loadCipher()
	{
		try {	
			key=getKey("resources/key.dat");
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			log.error("error occured in loadCipher in EncryptUtil class.",e);
		} catch (NoSuchPaddingException e) {
			log.error("error occured in loadCipher in EncryptUtil class.",e);
		} 
		return desCipher;
	}
	/**
	 * reading the key file and returning the key
	 * @param keyFile
	 * @return
	 */
	private SecretKey getKey(String keyFile) {
		ObjectInputStream inStream;
		SecretKey key=null;
		try {
			inStream = new ObjectInputStream(
				this.getClass().getClassLoader().getResourceAsStream(
						keyFile));
			key=((SecretKey) inStream.readObject());
			inStream.close();
		} catch (IOException e) {
			log.error("error occured in getKey in EncryptUtil class.",e);
		}
		catch (ClassNotFoundException e) {
			log.error("error occured in getKey in EncryptUtil class.",e);
		}
		
		
		return key;
	}
	
	
	/**
	 * used to encrypt plain text.
	 * @param plaintext
	 * @return
	 */
	public synchronized String encryptDES(String plaintext)
	{
		String strCipherText=null;
		byte[] byteDataToEncrypt = plaintext.getBytes();
		byte[] byteCipherText;
		try {
			desCipher.init(Cipher.ENCRYPT_MODE,key);
			byteCipherText = desCipher.doFinal(byteDataToEncrypt);
			strCipherText = new BASE64Encoder().encode(byteCipherText);
		} catch (IllegalBlockSizeException e) {
			log.error("error occured in encryptDES in EncryptUtil class.",e);
		} catch (BadPaddingException e) {
			log.error("error occured in encryptDES in EncryptUtil class.",e);
		} catch (InvalidKeyException e) {
			log.error("error occured in encryptDES in EncryptUtil class.",e);
		} 
		
		return strCipherText;
	}
	
	/**
	 * Used to decrypt the encrypted text.
	 * @param encryptedText
	 * @return
	 */
	public synchronized String decryptDES(String encryptedText)
	{
		
		byte[] byteCipherText=null;
		try {
			byteCipherText = new BASE64Decoder().decodeBuffer(encryptedText);
		} catch (IOException e1) {
			log.error("error occured in decryptDES in EncryptUtil class.",e1);
		}
		
		
		String strDecryptedText=null;
		byte[] byteDecryptedText=null;
		try {
			desCipher.init(Cipher.DECRYPT_MODE,key,desCipher.getParameters());
			byteDecryptedText = desCipher.doFinal(byteCipherText);
		} catch (InvalidKeyException e) {
			log.error("error occured in decryptDES in EncryptUtil class.",e);
		} catch (InvalidAlgorithmParameterException e) {
			log.error("error occured in decryptDES in EncryptUtil class.",e);
		}

		catch (IllegalBlockSizeException e) {

			log.error("error occured in decryptDES in EncryptUtil class.",e);
		} catch (BadPaddingException e) {
			log.error("error occured in decryptDES in EncryptUtil class.",e);
		}
		
		strDecryptedText = new String(byteDecryptedText);

		return strDecryptedText;
	}
	/**
	 * This method encrypt plain text using MD5 one way hash algorithm.
	 * 
	 * @param plaintext
	 * @return
	 */
	public synchronized String encrypt(String plaintext) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(CMSConstants.ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {

		}
		try {
			md.reset();
			md.update(plaintext.getBytes(CMSConstants.ENCODING_TYPE));
		} catch (UnsupportedEncodingException ex) {

		}
		byte digest[] = md.digest();
		String hash = (new BASE64Encoder()).encode(digest);

		return hash;
	}

	public static synchronized EncryptUtil getInstance() {
		if (instance == null) {
			instance = new EncryptUtil();
		}
		return instance;
	}

}