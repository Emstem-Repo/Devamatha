package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.BulkMailForm;
import com.kp.cms.helpers.admin.BulkMailHelper;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.transactions.admission.IStudentSearchTransaction;
import com.kp.cms.transactionsimpl.admission.StudentSearchTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class BulkMailHandler {
	
	private static final Log log = LogFactory.getLog(BulkMailHandler.class);
	
	private static volatile BulkMailHandler bulkMailHandler = null;

	private BulkMailHandler() {

	}

	/**
	 * @return single instance of bulkMailHandler object.
	 */
	public static BulkMailHandler getInstance() {
		if (bulkMailHandler == null) {
			bulkMailHandler = new BulkMailHandler();
		}
		return bulkMailHandler;
	}
	
	/**
	 * Get the list of selected students for the mail.
	 * @param bulkMailForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSearchTO> getSelectedStudents(
			BulkMailForm bulkMailForm) throws Exception {
		log.info("entering into getSelectedStudents in BulkMailHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		List<StudentSearchTO> studentSearchTo = BulkMailHelper
				.convertBoToTo(studentSearchTransactionImpl
						.getSelectedStudents(BulkMailHelper
								.getSelectedStudentSearch(bulkMailForm)));
		log.info("exit of getSelectedStudents in BulkMailHandler class.");
		return studentSearchTo;

	}

	/**
	 * Converts from BO to TO.
	 * @param bulkMailForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSearchTO> getStudentSearchResults(
			BulkMailForm bulkMailForm) throws Exception {
		log.info("entering into getStudentSearchResults in BulkMailHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		List<StudentSearchTO> studentSearchTo = BulkMailHelper
				.convertBoToTo(

				studentSearchTransactionImpl
						.getStudentSearch(BulkMailHelper
								.getSelectionSearchCriteria(bulkMailForm)));
		log.info("exit of getStudentSearchResults in BulkMailHandler class.");
		return studentSearchTo;
	}

	

	/**
	 * Send the mail for selected students.
	 * @param form
	 * @throws ApplicationException
	 */
	public void sendMails(BulkMailForm bulkMailForm) throws ApplicationException {
		log.info("entering into sendMails in BulkMailHandler class.");
		boolean mailsend = true;
		Properties prop = new Properties();
		try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
//		String fromaddress = prop.getProperty("knowledgepro.admin.mail");
		String fromaddress = CMSConstants.MAIL_USERID;
		String fromname = prop.getProperty("knowledgepro.admin.mailfrom");
		String[] emails = bulkMailForm.getEmailAddresses();
		
		for (int count = 0; count < emails.length; count++) {
			MailTO mailto = new MailTO();
			mailto.setFromAddress(fromaddress);
			mailto.setFromName(fromname);
			mailto.setToAddress(emails[count]);
			mailto.setSubject(bulkMailForm.getSubject());
			mailto.setMessage(bulkMailForm.getDesc());
					
			mailsend = CommonUtil.sendMail(mailto);
			if (!mailsend) {
				String subject = "Problem occured while sending mail to "
						+ emails[count];
				String message = "There was an error while sending mail to "
						+ emails[count]
						+ ", So i am afraid i couldn't send this mail.";
				MailTO errorMail = new MailTO();
				errorMail.setFromAddress(fromaddress);
				errorMail.setFromName(fromname);
				errorMail.setToAddress(fromaddress);
				errorMail.setSubject(subject);
				errorMail.setMessage(message);
							
				mailsend = CommonUtil.sendMail(mailto);

				mailsend = true;
			}
		}
		log.info("exit of sendMails in BulkMailHandler class.");
	}
}