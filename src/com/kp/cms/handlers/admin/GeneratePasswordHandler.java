package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.GeneratePasswordForm;
import com.kp.cms.helpers.admin.GeneratePasswordHelper;
import com.kp.cms.transactions.admin.IGeneratePasswordTransaction;
import com.kp.cms.transactions.admin.StudentLoginTO;
import com.kp.cms.transactionsimpl.admin.GeneratePasswordTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.jms.MailTO;

/**
 * Handler for generate password handler
 * 
 */
public class GeneratePasswordHandler {
	private static final Log log = LogFactory
			.getLog(GeneratePasswordHandler.class);

	public static volatile GeneratePasswordHandler self = null;

	public static GeneratePasswordHandler getInstance() {
		if (self == null) {
			self = new GeneratePasswordHandler();
		}
		return self;
	}

	private GeneratePasswordHandler() {

	}

	/**
	 * updates generated password for all students
	 * 
	 * @param gnForm
	 * @param servletContext
	 */
	public boolean updatePassword(GeneratePasswordForm gnForm) throws Exception {
		GeneratePasswordHelper helper = GeneratePasswordHelper.getInstance();
		boolean result = false;
		IGeneratePasswordTransaction txn = new GeneratePasswordTransactionImpl();
		int year = 0;
		int progid = 0;
		if (gnForm.getYear() != null
				&& !StringUtils.isEmpty(gnForm.getYear().trim())
				&& StringUtils.isNumeric(gnForm.getYear())) {
			year = Integer.parseInt(gnForm.getYear());
		}
		if (gnForm.getProgramId() != null
				&& !StringUtils.isEmpty(gnForm.getProgramId().trim())
				&& StringUtils.isNumeric(gnForm.getProgramId())) {
			progid = Integer.parseInt(gnForm.getProgramId());
		}
		// search student list where password not generated till now
		List<Student> studentList = txn.getSearchedStudents(progid, year);
		List<String> usernames = txn.getAllUserNamesPresent();
		// get list and generate user name and password for each
		List<StudentLogin> studentlogins = helper.prepareStudentLogins(
				studentList, gnForm, usernames);
		// update to student login table
		result = txn.saveCredentials(studentlogins);

		TemplateHandler temphandle = TemplateHandler.getInstance();
		List<GroupTemplate> list = temphandle.getDuplicateCheckList(0,
				CMSConstants.PASSWORD_TEMPLATE);
		if (list != null && !list.isEmpty() && result) {
			String desc = "";
			if (list.get(0) != null
					&& list.get(0).getTemplateDescription() != null)
				desc = list.get(0).getTemplateDescription();
			// send mail to students and parents
			List<StudentLoginTO> loginTos = gnForm.getSuccessList();
			if (loginTos != null) {
				Iterator<StudentLoginTO> logItr = loginTos.iterator();
				while (logItr.hasNext()) {
					StudentLoginTO loginTO = (StudentLoginTO) logItr.next();
					String fatherName = "";
					String studentName = "";
					String parentAddressLine1 = "";
					String parentAddressLine2 = "";
					String parentAddressLine3 = "";
					String studAddressLine1 = "";
					String studAddressLine2 = "";
					String studAddressLine3 = "";
					String date = CommonUtil.getStringDate(new Date());
					String studUserName = "";
					String studPassword = "";
					String parentUserName = "";
					String parentPassword = "";
					String parPin = "";
					String studPin = "";

					if (loginTO.getOriginalStudent() != null
							&& loginTO.getOriginalStudent().getAdmAppln() != null
							&& loginTO.getOriginalStudent().getAdmAppln()
									.getPersonalData() != null) {
						studentName = loginTO.getStudentName();
						fatherName = loginTO.getOriginalStudent().getAdmAppln()
								.getPersonalData().getFatherName();
						parentAddressLine1 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine1();
						parentAddressLine2 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine2();
						parentAddressLine3 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine3();
						studAddressLine1 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getCurrentAddressLine1();
						studAddressLine2 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getCurrentAddressLine2();
						studAddressLine3 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getCurrentAddressZipCode();
						parPin = loginTO.getOriginalStudent().getAdmAppln()
								.getPersonalData().getCurrentAddressZipCode();
						studPin = loginTO.getOriginalStudent().getAdmAppln()
								.getPersonalData().getParentAddressZipCode();
					}

					if (gnForm.getSendMail() != null
							&& (gnForm.getSendMail()
									.equalsIgnoreCase("student") || gnForm
									.getSendMail().equalsIgnoreCase("both"))) {
						if (loginTO.getStudentMail() != null
								&& !StringUtils.isEmpty(loginTO
										.getStudentMail().trim())
								&& loginTO.getStudentUsername() != null
								&& !StringUtils.isEmpty(loginTO
										.getStudentUsername())) {
							studUserName = loginTO.getStudentUsername();
							studPassword = loginTO.getOriginalPassword();
							String subject = CMSConstants.MAIL_SUBJECT;
							String message = desc;
							message = message.replace(
									CMSConstants.TEMPLATE_FATHER_NAME,
									studentName);
							message = message.replace(
									CMSConstants.TEMPLATE_ADDRESS,
									studAddressLine1 + "<br/>"
											+ studAddressLine2 + "<br/>"
											+ studAddressLine3);
							message = message.replace(
									CMSConstants.TEMPLATE_PINCODE, studPin);
							message = message.replace(
									CMSConstants.TEMPLATE_APPLICANT_NAME,
									studentName);
							message = message.replace(
									CMSConstants.TEMPLATE_USERNAME,
									studUserName);
							message = message.replace(
									CMSConstants.TEMPLATE_PASSWORD,
									studPassword);
							message = message.replace(
									CMSConstants.TEMPLATE_DATE, date);

							sendMailToStudent(loginTO.getStudentMail(),
									subject, message);
							if(gnForm.getSendMail().equalsIgnoreCase("both")){
								if (loginTO.getParentMail() != null
										&& !StringUtils.isEmpty(loginTO.getParentMail()
												.trim())) {
									sendMailToStudent(loginTO.getParentMail(),subject, message);
								}
							}
						}
					}
					// ------------------------send mail to
					// parent-----------------//
					if (gnForm.getSendMail() != null
							&& (gnForm.getSendMail().equalsIgnoreCase("parent"))) {
						if (loginTO.getParentMail() != null
								&& !StringUtils.isEmpty(loginTO.getParentMail()
										.trim())
								&& loginTO.getParentUsername() != null
								&& !StringUtils.isEmpty(loginTO
										.getParentUsername())) {
							parentUserName = loginTO.getParentUsername();
							parentPassword = loginTO.getParentPassword();
							String subject = CMSConstants.MAIL_SUBJECT;
							String message = desc;

							message = message.replace(
									CMSConstants.TEMPLATE_FATHER_NAME,
									fatherName);
							message = message.replace(
									CMSConstants.TEMPLATE_ADDRESS,
									parentAddressLine1 + "<br/>"
											+ parentAddressLine2 + "<br/>"
											+ parentAddressLine3);
							message = message.replace(
									CMSConstants.TEMPLATE_PINCODE, parPin);
							message = message.replace(
									CMSConstants.TEMPLATE_APPLICANT_NAME,
									studentName);
							message = message.replace(
									CMSConstants.TEMPLATE_USERNAME,
									parentUserName);
							message = message.replace(
									CMSConstants.TEMPLATE_PASSWORD,
									parentPassword);
							message = message.replace(
									CMSConstants.TEMPLATE_DATE, date);

							sendMailToStudent(loginTO.getParentMail(), subject,
									message);

						}
					}
				}
			}

		} else if (result) {
			// send mail to students and parents
			List<StudentLoginTO> loginTos = gnForm.getSuccessList();
			if (loginTos != null) {
				Iterator<StudentLoginTO> logItr = loginTos.iterator();
				while (logItr.hasNext()) {
					StudentLoginTO loginTO = (StudentLoginTO) logItr.next();
					String fatherName = "";
					String permanentAddressLine1 = "";
					String permanentAddressLine2 = "";
					String permanentAddressLine3 = "";
					if (loginTO.getOriginalStudent() != null
							&& loginTO.getOriginalStudent().getAdmAppln() != null
							&& loginTO.getOriginalStudent().getAdmAppln()
									.getPersonalData() != null) {
						fatherName = loginTO.getOriginalStudent().getAdmAppln()
								.getPersonalData().getFatherName();
						permanentAddressLine1 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine1();
						permanentAddressLine2 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine2();
						permanentAddressLine3 = loginTO.getOriginalStudent()
								.getAdmAppln().getPersonalData()
								.getParentAddressLine3();
					}
					if (gnForm.getSendMail() != null
							&& (gnForm.getSendMail()
									.equalsIgnoreCase("student") || gnForm
									.getSendMail().equalsIgnoreCase("both"))) {
						if (loginTO.getStudentMail() != null
								&& !StringUtils.isEmpty(loginTO
										.getStudentMail().trim())) {
							String subject = CMSConstants.MAIL_SUBJECT;
							String message = "To,<br>&nbsp;&nbsp;"
									+ loginTO.getStudentName() + "<br>"
									+ permanentAddressLine1 + "<br>"
									+ permanentAddressLine2 + "<br>"
									+ permanentAddressLine3 + "<br>"
									+ CMSConstants.MAIL_STUDENT_MATTER1 + "<B>"
									+ loginTO.getStudentName() + "</B>"
									+ CMSConstants.MAIL_MATTER2
									+ CMSConstants.MAIL_LOGINNAME + "<B>"
									+ loginTO.getStudentUsername() + "</B>"
									+ "<br>" + CMSConstants.MAIL_LOGINPASSWORD
									+ "<B>" + loginTO.getOriginalPassword()
									+ "</B>" + "<br><br>"
									+ CMSConstants.MAIL_DATE + "<br>"
									+ CMSConstants.MAIL_PLACE
									+ CMSConstants.MAIL_MATTER3;
							//seding mail to Student mail id
							sendMailToStudent(loginTO.getStudentMail(),subject, message);
							
							//sending mail to parent mail id if click on both radio button
							if(gnForm.getSendMail().equalsIgnoreCase("both")){
								if (loginTO.getParentMail() != null
										&& !StringUtils.isEmpty(loginTO.getParentMail()
												.trim())) {
									sendMailToStudent(loginTO.getParentMail(),subject, message);
								}
							}

						}
					}
					if (gnForm.getSendMail() != null
							&& (gnForm.getSendMail().equalsIgnoreCase("parent"))) {
						if (loginTO.getParentMail() != null
								&& !StringUtils.isEmpty(loginTO.getParentMail()
										.trim())) {
							String subject = CMSConstants.MAIL_SUBJECT;
							String message = "To,<br>&nbsp;&nbsp;" + fatherName
									+ "<br>" + permanentAddressLine1 + "<br>"
									+ permanentAddressLine2 + "<br>"
									+ permanentAddressLine3 + "<br>"
									+ CMSConstants.MAIL_MATTER1 + "<B>"
									+ loginTO.getStudentName() + "</B>"
									+ CMSConstants.MAIL_MATTER2
									+ CMSConstants.MAIL_LOGINNAME + "<B>"
									+ loginTO.getParentUsername() + "</B>"
									+ "<br>" + CMSConstants.MAIL_LOGINPASSWORD
									+ "<B>" + loginTO.getParentPassword()
									+ "</B>" + "<br><br>"
									+ CMSConstants.MAIL_DATE + "<br>"
									+ CMSConstants.MAIL_PLACE
									+ CMSConstants.MAIL_MATTER3;
							sendMailToStudent(loginTO.getParentMail(), subject,
									message);

						}
					}
				}
			}
		}
		log.error("ending of updatePassword method in GeneratePasswordHandler");
		return result;
	}

	// -------------------only--generate
	// password---------------------------------

	public boolean updateOnlyPassword(GeneratePasswordForm gnForm)
			throws Exception {
		GeneratePasswordHelper helper = GeneratePasswordHelper.getInstance();
		boolean result = false;
		IGeneratePasswordTransaction txn = new GeneratePasswordTransactionImpl();
		int year = 0;
		int progid = 0;
		if (gnForm.getYear() != null
				&& !StringUtils.isEmpty(gnForm.getYear().trim())
				&& StringUtils.isNumeric(gnForm.getYear())) {
			year = Integer.parseInt(gnForm.getYear());
		}
		if (gnForm.getProgramId() != null
				&& !StringUtils.isEmpty(gnForm.getProgramId().trim())
				&& StringUtils.isNumeric(gnForm.getProgramId())) {
			progid = Integer.parseInt(gnForm.getProgramId());
		}
		// search student list where password not generated till now
		List<Student> studentList = txn.getSearchedStudents(progid, year);
		List<String> usernames = txn.getAllUserNamesPresent();
		// get list and generate user name and password for each
		List<StudentLogin> studentlogins = helper.prepareStudentLogins(
				studentList, gnForm, usernames);
		// update to student login table
		result = txn.saveCredentials(studentlogins);
		log.error("ending of updateOnlyPassword method in GeneratePasswordHandler");
		return result;
	}

	// -------------------------------------------------------------------------------------------
	/**
	 * Send mail to student after successful submit of application
	 * 
	 * @param admForm
	 * @return
	 */
	private boolean sendMailToStudent(String emailId, String sub, String message) {
		boolean sent = false;
		Properties prop = new Properties();
		try {
			InputStream inst = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inst);
		} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail =CMSConstants.MAIL_USERID;
		String toAddress = emailId;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop
				.getProperty("knowledgepro.admission.studentmail.fromName"));
		// uses JMS
		sent = CommonUtil.postMail(mailto);
		return sent;
	}

	/**
	 * reset password implementation
	 * 
	 * @param gnForm
	 * @return
	 */
	public boolean resetPassword(GeneratePasswordForm gnForm) throws Exception {
		boolean result = false;
		String usernm = gnForm.getUserName();
		IGeneratePasswordTransaction txn = new GeneratePasswordTransactionImpl();
		String encryptedpwd = EncryptUtil.getInstance().encryptDES(
				gnForm.getResetPwd());
		StudentLogin studentlogin = txn.resetPassword(usernm, encryptedpwd,
				gnForm.getUserId(),PasswordGenerator.getPassword().substring(0,3)+gnForm.getResetPwd());
		if (studentlogin != null) {

			String emailId = "";
			String message = "";
			String subject = CMSConstants.MAIL_SUBJECT;
			// get user details to send mail
			TemplateHandler temphandle = TemplateHandler.getInstance();
			List<GroupTemplate> list = temphandle.getDuplicateCheckList(0,
					CMSConstants.PASSWORD_TEMPLATE);
			if (list != null && !list.isEmpty()) {
				String desc = "";
				if (list.get(0) != null
						&& list.get(0).getTemplateDescription() != null)
					desc = list.get(0).getTemplateDescription();
				String fatherName = "";
				String studentName = "";
				String parentAddressLine1 = "";
				String parentAddressLine2 = "";
				String parentAddressLine3 = "";
				String studAddressLine1 = "";
				String studAddressLine2 = "";
				String studAddressLine3 = "";
				String date = CommonUtil.getStringDate(new Date());
				String studUserName = "";
				String studPassword = "";
				String parentUserName = "";
				String parentPassword = "";
				String parPin = "";
				String studPin = "";

				message = desc;

				if (studentlogin.getStudent() != null
						&& studentlogin.getStudent().getAdmAppln() != null
						&& studentlogin.getStudent().getAdmAppln()
								.getPersonalData() != null) {
					StringBuffer namebuff = new StringBuffer();
					namebuff.append(studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getFirstName());
					namebuff.append(" ");
					namebuff.append(studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getMiddleName());
					namebuff.append(" ");
					namebuff.append(studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getLastName());
					namebuff.append(" ");
					studentName = namebuff.toString();
					fatherName = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getFatherName();
					parentAddressLine1 = studentlogin.getStudent()
							.getAdmAppln().getPersonalData()
							.getParentAddressLine1();
					parentAddressLine2 = studentlogin.getStudent()
							.getAdmAppln().getPersonalData()
							.getParentAddressLine2();
					parentAddressLine3 = studentlogin.getStudent()
							.getAdmAppln().getPersonalData()
							.getParentAddressLine3();
					studAddressLine1 = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getCurrentAddressLine1();
					studAddressLine2 = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getCurrentAddressLine2();
					studAddressLine3 = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getCurrentAddressZipCode();
					parPin = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getCurrentAddressZipCode();
					studPin = studentlogin.getStudent().getAdmAppln()
							.getPersonalData().getParentAddressZipCode();

					studUserName = usernm;
					studPassword = gnForm.getResetPwd();

					if (studentlogin.getIsStudent()) {
						message = message.replace(
								CMSConstants.TEMPLATE_FATHER_NAME, studentName);
						message = message.replace(
								CMSConstants.TEMPLATE_ADDRESS, studAddressLine1
										+ "<br/>" + studAddressLine2 + "<br/>"
										+ studAddressLine3);
						message = message.replace(
								CMSConstants.TEMPLATE_PINCODE, studPin);
						message = message.replace(
								CMSConstants.TEMPLATE_APPLICANT_NAME,
								studentName);
						emailId = studentlogin.getStudent().getAdmAppln()
								.getPersonalData().getEmail();
					} else {
						message = message.replace(
								CMSConstants.TEMPLATE_FATHER_NAME, fatherName);
						message = message.replace(
								CMSConstants.TEMPLATE_ADDRESS,
								parentAddressLine1 + "<br/>"
										+ parentAddressLine2 + "<br/>"
										+ parentAddressLine3);
						message = message.replace(
								CMSConstants.TEMPLATE_PINCODE, parPin);
						message = message.replace(
								CMSConstants.TEMPLATE_APPLICANT_NAME,
								fatherName);
						emailId = studentlogin.getStudent().getAdmAppln()
								.getPersonalData().getFatherEmail();
					}

					message = message.replace(CMSConstants.TEMPLATE_USERNAME,
							studUserName);
					message = message.replace(CMSConstants.TEMPLATE_PASSWORD,
							studPassword);
					message = message.replace(CMSConstants.TEMPLATE_DATE, date);

				}
			}

			// send mail to mail id

			if (emailId != null && !StringUtils.isEmpty(emailId)) {
				sendMailToStudent(emailId, subject, message);
				result = true;
			}
		} else {
			result = false;
		}
		log.error("ending of resetPassword method in GeneratePasswordHandler");
		return result;
	}

	/**
	 * checks username exists or not
	 * 
	 * @param userName
	 * @return
	 */
	public boolean checkUserExists(String userName) throws Exception {
		boolean result = false;
		IGeneratePasswordTransaction txn = new GeneratePasswordTransactionImpl();
		result = txn.checkUserExists(userName);
		return result;
	}

}
