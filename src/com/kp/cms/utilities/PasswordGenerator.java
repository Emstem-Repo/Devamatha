package com.kp.cms.utilities;

/**
 * Class for generating temporary password 
 *
 */
public class PasswordGenerator {
	 /** Minimum length for a decent password */
	  public static final int MIN_LENGTH = 8;
	  public static final int MIN_LENGTH_ALPHA = 1;
	  public static final int MIN_LENGTH_NUM = 5;
	  public static final int OTP_LENGTH_NUM = 6;
	  
	  /** The random number generator. */
	  protected static java.util.Random rand = new java.util.Random();

	  /*
	   * Set of characters that is valid. Must be printable, memorable, and "won't
	   * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands
	   * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out,
	   * as are numeric zero and one.
	   */
	  protected static char[] goodChar = {'0', '1',
	      '2', '3', '4', '5', '6', '7', '8', '9' };

	  protected static char[] goodCharAlpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	      'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	  
	  /* Generate a Password object with a random password. */
	  public static String getPassword() {
	    StringBuffer sbuf = new StringBuffer();
	    for (int cnt = 0; cnt < MIN_LENGTH; cnt++) {
	    	sbuf.append(goodChar[rand.nextInt(goodChar.length)]);
	    }
	    return sbuf.toString();
	  }
	
	  /* Generate a Password object with a random password for Challan. */
	  public static String getPasswordNum() {
	    StringBuffer sbuf = new StringBuffer();
	    for (int cnt = 0; cnt < MIN_LENGTH_NUM; cnt++) {
	    	sbuf.append(goodChar[rand.nextInt(goodChar.length)]);
	    }
	    return sbuf.toString();
	  }
	  
	  /* Generate a Password object with a random password for Challan. */
	  public static String getPasswordAlpha() {
	    StringBuffer sbuf = new StringBuffer();
	    for (int cnt = 0; cnt < MIN_LENGTH_ALPHA; cnt++) {
	    	sbuf.append(goodCharAlpha[rand.nextInt(goodCharAlpha.length)]);
	    }
	    return sbuf.toString();
	  }
	  //For OTP generation
	  public static String getPasswordforOTP() {
		    StringBuffer sbuf = new StringBuffer();
		    for (int cnt = 0; cnt < OTP_LENGTH_NUM; cnt++) {
		    	sbuf.append(goodChar[rand.nextInt(goodChar.length)]);
		    }
		    return sbuf.toString();
		  }
	  
	  
}
