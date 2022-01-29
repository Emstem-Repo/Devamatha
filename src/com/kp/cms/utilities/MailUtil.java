package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import com.kp.cms.constants.CMSConstants;

public class MailUtil {
	private static String SMTP_HOST_NAME = "";
    private static int SMTP_HOST_PORT = 0;
    private static String SMTP_AUTH_USER = "";
    private static String SMTP_AUTH_PWD  = "";
    
	/**
	 * Default constructor.
	 */
	private static final Transport transport;

	static {
		try {
			Properties prop = new Properties();
			InputStream in = CommonUtil.class.getClassLoader() .getResourceAsStream(CMSConstants.MAIL_FILE_CFG);
			prop.load(in);
			SMTP_HOST_NAME = prop.getProperty("mailServer");
			SMTP_HOST_PORT = Integer.parseInt(prop.getProperty("port"));
			SMTP_AUTH_USER = prop.getProperty("loginName");
			SMTP_AUTH_PWD = prop.getProperty("password");
			
			Properties props = new Properties();
	    	props.put("mail.transport.protocol", "smtps");
	    	props.put("mail.smtps.host", SMTP_HOST_NAME);
	    	props.put("mail.smtps.auth", "true");
	    	
	    	Session mailSession = Session.getDefaultInstance(props);
	    	mailSession.setDebug(true);
	    	transport = mailSession.getTransport();
	    	transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		} catch (Throwable ex) {
			ex.printStackTrace();
			// Make sure you log the exception, as it might be swallowed
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Transport getInstance() {
		return transport;
	}


}
