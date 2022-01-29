/**
 * 
 */
package com.kp.cms.utilities;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.SecretKey;

import com.sun.crypto.provider.SunJCE;
/**
 * @author debasis
 *
 */
public class KeyGenerator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

				KeyGenerator.generateKey("f:/key.dat");
		
	}
	/**
	 * Method to generate the key. The key is written to the file given in the parm
	 * Errors are output to the console, since this is run as a tool and not deployed.
	 * NOTE: This method uses DES algorithm. 
	 *       To use other algorithm, like triple DES see overloaded method.
	 * 
	 * @see generateKey(String filename, String cryptoType) 
	 * @param filename
	 * 
	 * @deprecated Single DES encryption is no longer appropriate. Use generateKey(String filename, String cryptoType)
	 *  
	 */
	public static void generateKey(String filename) {

		// provider registration
		Security.addProvider(new SunJCE());

		SecretKey key;
		ObjectOutputStream outStream = null;

		File file = new File(filename);
		if (!file.exists()) {
			try {
				outStream =
					new ObjectOutputStream(
						new java.io.FileOutputStream(filename));
				javax.crypto.KeyGenerator generator =
					javax.crypto.KeyGenerator.getInstance("DES");
				generator.init(new SecureRandom());
				key = generator.generateKey();
				outStream.writeObject(key);
				outStream.flush();

			} catch (IOException ioe) {
			} catch (NoSuchAlgorithmException nsa) {
			} finally {
				if (null == outStream)
					return;
				try {
					outStream.close();
				} catch (IOException e) {
				}
			}

		} else {
		}

	}
}
