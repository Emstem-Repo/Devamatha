package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentFeedBack;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentFeedBackForm;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.helpers.admin.StudentFeedBackHelper;
import com.kp.cms.transactionsimpl.admin.StudentFeedBackTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class StudentFeedBackHandler {
	
	public static volatile StudentFeedBackHandler studentFeedBackHandler = null;
	private static final Log log = LogFactory.getLog(StudentFeedBackHandler.class);

	public static StudentFeedBackHandler getInstance() {
		if (studentFeedBackHandler == null) {
			studentFeedBackHandler = new StudentFeedBackHandler();
			return studentFeedBackHandler;
		}
		return studentFeedBackHandler;
	}
	
	public boolean addStudentFeedBack(StudentFeedBackForm studentFeedBackForm,String studentId) throws Exception
	{
		boolean isAdded= false; 
		StudentFeedBack studentFeedBack = StudentFeedBackHelper.getInstance().convertFormToBo(studentFeedBackForm,studentId);
		isAdded=StudentFeedBackTransactionImpl.getInstance().addStudentFeedBack(studentFeedBack);
		return isAdded;
		
	}

	/**
	 * @param studentFeedBackForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentEmailId(String userName) throws Exception{
		
		return StudentFeedBackTransactionImpl.getInstance().getStudentEmailId(userName);
	}
	/**
	 * send mail to admin to reconfigure application number
	 * @param admForm
	 * @param appliedyear
	 * @return
	 */
	public boolean sendMailToAdmin(StudentFeedBackForm studentFeedBackForm) {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			String adminmail=prop.getProperty(CMSConstants.STUDENT_FEEDBACK_MAIL_ID);
			String toAddress=adminmail;
			String subject="Student Feedback";
			String username=studentFeedBackForm.getUsername();
			@SuppressWarnings("unused")
			String fromAddress=studentFeedBackForm.getEmail();
			String msg=studentFeedBackForm.getFeedBack();
			sent=sendMail(toAddress, subject, msg, username,fromAddress,studentFeedBackForm);
		return sent;
}
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID,String sub,String message, String user,String fromAddress,StudentFeedBackForm studentFeedBackForm) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
				String fromMail=fromAddress;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg="";
				msg = msg + "<html> <body> <table> <tr>";
				msg = msg + message;
				msg = msg + "</tr><br/><br/><tr> <td>Mobile No:</td> <td>"+studentFeedBackForm.getMobileNo() +"</td></tr></body></table>";
				String username=user;
				MailTO mailto=new MailTO();
				mailto.setFromAddress(fromMail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(username);
				//uses JMS 
//				sent=CommonUtil.postMail(mailto);
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}
}
