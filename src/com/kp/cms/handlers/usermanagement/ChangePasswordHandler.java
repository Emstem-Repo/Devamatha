package com.kp.cms.handlers.usermanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.usermanagement.ChangePasswordForm;
import com.kp.cms.transactions.usermanagement.IChangePassword;
import com.kp.cms.transactionsimpl.usermanagement.ChangePasswordImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

/**
 * 
 * @author 
 *
 */
public class ChangePasswordHandler {
	public static volatile ChangePasswordHandler changePasswordHandler = null;
	private static Log log = LogFactory.getLog(ChangePasswordHandler.class);
	
	/**
	 * 
	 * @return
	 */
	public static ChangePasswordHandler getInstance() {
		if (changePasswordHandler == null) {
			changePasswordHandler = new ChangePasswordHandler();
			return changePasswordHandler;
		}
		return changePasswordHandler;
	}
	/**
	 * 
	 * @param changePasswordForm
	 * @return
	 * @throws Exception
	 */
	public boolean changePass(ChangePasswordForm changePasswordForm) throws Exception {
		IChangePassword iChangePassword = new ChangePasswordImpl();
		Boolean isChanged = false;
		isChanged = iChangePassword.changePassword(changePasswordForm);
		return isChanged;
	}
	/**
	 * 
	 * @param changePasswordForm
	 * @return
	 * @throws ApplicationException
	 */
	
	
	public StudentLogin isValidStudent(ChangePasswordForm changePasswordForm) throws ApplicationException {
		IChangePassword iChangePassword = new ChangePasswordImpl();
		return iChangePassword.verifyStudent(changePasswordForm);		
	}
	
	/**
	 * 
	 * @param changePasswordForm
	 * @return
	 * @throws Exception
	 */
	public boolean changeStudentPass(ChangePasswordForm changePasswordForm) throws Exception {
		IChangePassword iChangePassword = new ChangePasswordImpl();
		Boolean isChanged = false;
		isChanged = iChangePassword.changeStudentPassword(changePasswordForm);
		return isChanged;
	}
	/**
	 * 
	 * @param changePasswordForm
	 * @return
	 * @throws ApplicationException
	 */
	
	
	public Users isValiedUser(ChangePasswordForm changePasswordForm) throws ApplicationException {
		IChangePassword iChangePassword = new ChangePasswordImpl();
		return iChangePassword.verifyUser(changePasswordForm);		
	}
	
	/**
	 * Send mail to Users after successful submit of change password
	 * @param changePasswordForm
	 * @return
	 */
	public boolean sendMailToUser(ChangePasswordForm changePasswordForm) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream in = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(in);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
				String adminmail=CMSConstants.MAIL_USERID;
				String toAddress=changePasswordForm.getEmail();
				// MAIL TO BE IMPLEMENTED AS THE FORMAT SAVED IN DB
				String subject=prop.getProperty("knowledgepro.usermanagement.changepass.subject");
				String msg= "Dear " + changePasswordForm.getTempUname() + "," +
					prop.getProperty("knowledgepro.usermanagement.changepass.msg1") + " " +
					changePasswordForm.getNewPwd() + prop.getProperty("knowledgepro.usermanagement.changepass.msg2");
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(adminmail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(prop.getProperty("knowledgepro.admin.mailfrom"));
				//uses JMS 
				sent=CommonUtil.postMail(mailto);
			return sent;
	}
	
}
