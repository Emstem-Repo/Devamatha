package com.kp.cms.handlers.usermanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.StudentForgotPasswordForm;
import com.kp.cms.helpers.usermanagement.StudentForgotPasswordHelper;
import com.kp.cms.transactions.usermanagement.IStudentForgotPasswordTransaction;
import com.kp.cms.transactionsimpl.usermanagement.StudentForgotPasswordTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.jms.MailTO;

public class StudentForgotPasswordHandler {
	private static final Log log = LogFactory.getLog(StudentForgotPasswordHandler.class);
	/**
	 * Singleton object of StudentForgotPasswordHelper
	 */
	private static volatile StudentForgotPasswordHandler studentForgotPasswordHandler = null;
	private StudentForgotPasswordHandler() {
		
	}
	/**
	 * return singleton object of StudentForgotPasswordHelper.
	 * @return
	 */
	public static StudentForgotPasswordHandler getInstance() {
		if (studentForgotPasswordHandler == null) {
			studentForgotPasswordHandler = new StudentForgotPasswordHandler();
		}
		return studentForgotPasswordHandler;
	}
	/**
	 * @param studentForgotPasswordForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidData(StudentForgotPasswordForm studentForgotPasswordForm) throws Exception {
		boolean isValidData=false;
		String query=StudentForgotPasswordHelper.getInstance().checkValidDataQuery(studentForgotPasswordForm);
		IStudentForgotPasswordTransaction transaction=new StudentForgotPasswordTransactionImpl();
		StudentLogin studentLogin=transaction.checkValidData(query);
		if(studentLogin!=null && studentLogin.getId()>0){
			studentForgotPasswordForm.setStudentLogin(studentLogin);
			isValidData=true;
		}
		return isValidData;
	}
	/**
	 * @param studentForgotPasswordForm
	 * @return
	 */
	public boolean sendMailToStudent(
			StudentForgotPasswordForm studentForgotPasswordForm) {
		
		boolean sent=false;
		StudentLogin studentLogin=studentForgotPasswordForm.getStudentLogin();
		if(studentLogin.getStudent().getAdmAppln().getPersonalData()!=null && studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail()!=null){
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
			StringBuffer name=new StringBuffer();
			PersonalData personalData=studentLogin.getStudent().getAdmAppln().getPersonalData();
			if(personalData.getFirstName()!=null){
				name.append(personalData.getFirstName());
			}
			if(personalData.getMiddleName()!=null){
				name.append(" "+personalData.getMiddleName());
			}
			if(personalData.getLastName()!=null){
				name.append(" "+personalData.getLastName());
			}
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
			final String adminmail=CMSConstants.MAIL_USERID;
			String toAddress=studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail();
			// MAIL TO BE IMPLEMENTED AS THE FORMAT SAVED IN DB
			String subject=prop.getProperty("knowledgepro.usermanagement.forgotpass.subject");
			String msg= "Dear " + name.toString() + "," +"<br/>"+" Your "+
			prop.getProperty("knowledgepro.username") + " : " +
			studentLogin.getUserName() + " and "+prop.getProperty("knowledgepro.admin.password")+" is : "+EncryptUtil.getInstance().decryptDES(studentLogin.getPassword());
			
			/*MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admin.mailfrom"));
			*/
			//uses JMS 
			//sent=CommonUtil.sendMail(mailto);

			//raghu copied this on from school admissions
			
			//final String adminmail=CMSConstants.MAIL_USERID;
			final String password = CMSConstants.MAIL_PASSWORD;
			final String port = CMSConstants.MAIL_PORT;
			final String host = CMSConstants.MAIL_HOST;
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", port);
	 
			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
	 
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
			
			try
			{
				 MimeMessage message1 = new MimeMessage(session);
					
					// Set from & to addresses
					InternetAddress from = new InternetAddress(adminmail,"Forget PassWord SH College");
					
					InternetAddress toAssociate = new InternetAddress(toAddress);
					message1.setSubject(subject);
					message1.setFrom(from);
					message1.addRecipient(Message.RecipientType.TO, toAssociate);
				    MimeBodyPart mailBody = new MimeBodyPart();
				    mailBody.setText(msg, "US-ASCII", "html");
				    MimeMultipart mimeMultipart = new MimeMultipart();
				    
				    mimeMultipart.addBodyPart(mailBody);
				    message1.setContent(mimeMultipart);
				
				    Properties config = new Properties() {
						{
							put("mail.smtps.auth", "true");
							put("mail.smtp.host", host);
							put("mail.smtp.port", port);
							put("mail.smtp.starttls.enable", "true");
							put("mail.transport.protocol", "smtps");
						}
					}; 
					
					
					
				Session carrierSession = Session.getInstance(config, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
				
				Transport transport = carrierSession.getTransport("smtps");
				transport.connect(host,adminmail,password);
				transport.sendMessage(message1,message1.getRecipients(Message.RecipientType.TO));  //set
		        transport.close();
		        
		    	System.out.println("==========Done========");
			}
			
			catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
			//uses JMS 
			sent=CommonUtil.sendMail(mailto);
		}
		return sent;
	}
	/**
	 * @param studentForgotPasswordForm
	 * @throws Exception
	 */
	public void resetPassword(StudentForgotPasswordForm studentForgotPasswordForm) throws Exception {
		String randPass=PasswordGenerator.getPassword();
		EncryptUtil encUtil=EncryptUtil.getInstance();
		String encpass=null;
		if(randPass!=null && !StringUtils.isEmpty(randPass.trim())){
			encpass=encUtil.encryptDES(randPass);
		}
		IStudentForgotPasswordTransaction transaction=new StudentForgotPasswordTransactionImpl();
		StudentLogin studentLogin=transaction.changePassword(studentForgotPasswordForm,encpass,randPass.substring(0, 3)+randPass);
		if(studentLogin!=null){
			studentForgotPasswordForm.setStudentLogin(studentLogin);
		}
	}
	/**
	 * @param studentForgotPasswordForm
	 * @return
	 */
	public boolean checkValidUser(StudentForgotPasswordForm studentForgotPasswordForm) throws Exception{
		boolean isValidData=false;
		String query=StudentForgotPasswordHelper.getInstance().checkValidUserQuery(studentForgotPasswordForm);
		IStudentForgotPasswordTransaction transaction=new StudentForgotPasswordTransactionImpl();
		Users user=transaction.checkValidUser(query);
		if(user!=null && user.getId()>0){
			studentForgotPasswordForm.setUser(user);
			isValidData=true;
		}
		return isValidData;
	}
	/**
	 * @param studentForgotPasswordForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailToUser(StudentForgotPasswordForm studentForgotPasswordForm) throws Exception{
		boolean sent=false;
		Users user=studentForgotPasswordForm.getUser();
		if(user.getEmployee() !=null && (user.getEmployee().getWorkEmail()!=null || user.getEmployee().getOtherEmail() != null)){
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
			String name = "";
			name = user.getEmployee().getFirstName();
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
			final String adminmail=CMSConstants.MAIL_USERID;
			String toAddress="";
			if(user.getEmployee().getWorkEmail() != null && !user.getEmployee().getWorkEmail().isEmpty()){
				if(CommonUtil.validateEmailID(user.getEmployee().getWorkEmail())){
					toAddress = toAddress +user.getEmployee().getWorkEmail();
				}
			}
			if(user.getEmployee().getOtherEmail() != null && !user.getEmployee().getOtherEmail() .isEmpty() && !user.getEmployee().getWorkEmail().equalsIgnoreCase(user.getEmployee().getOtherEmail())){
				if(CommonUtil.validateEmailID(user.getEmployee().getOtherEmail())){
					toAddress = toAddress + ","+user.getEmployee().getOtherEmail() ;
				}
			}
			// MAIL TO BE IMPLEMENTED AS THE FORMAT SAVED IN DB
			String subject="Password";
			String msg= "Dear " + name + "," +"<br/>"+" Your "+
			prop.getProperty("knowledgepro.username") + " : " +
			user.getUserName() + " and "+prop.getProperty("knowledgepro.admin.password")+" is : "+EncryptUtil.getInstance().decryptDES(user.getPwd());
			
			/*MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admin.mailfrom"));
			*/
			
			
			
			//raghu copied this on from school admissions
			
			//final String adminmail=CMSConstants.MAIL_USERID;
			final String password = CMSConstants.MAIL_PASSWORD;
			final String port = CMSConstants.MAIL_PORT;
			final String host = CMSConstants.MAIL_HOST;
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", port);
	 
			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
	 
			//String toAddress=mailID;
			// MAIL TO CONSTRUCTION
			//String subject=sub;
			//String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
			//uses JMS 
			//sent=CommonUtil.postMail(mailto);
			
			// sending email
			
			try
			{
				 MimeMessage message1 = new MimeMessage(session);
					
					// Set from & to addresses
					InternetAddress from = new InternetAddress(adminmail,"Forget PassWord SH College");
					
					InternetAddress toAssociate = new InternetAddress(toAddress);
					message1.setSubject(subject);
					message1.setFrom(from);
					message1.addRecipient(Message.RecipientType.TO, toAssociate);
				    MimeBodyPart mailBody = new MimeBodyPart();
				    mailBody.setText(msg, "US-ASCII", "html");
				    MimeMultipart mimeMultipart = new MimeMultipart();
				    
				    mimeMultipart.addBodyPart(mailBody);
				    message1.setContent(mimeMultipart);
				
				    Properties config = new Properties() {
						{
							put("mail.smtps.auth", "true");
							put("mail.smtp.host", host);
							put("mail.smtp.port", port);
							put("mail.smtp.starttls.enable", "true");
							put("mail.transport.protocol", "smtps");
						}
					}; 
					
					
					
				Session carrierSession = Session.getInstance(config, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
				
				
				
				Transport transport = carrierSession.getTransport("smtps");
				transport.connect(host,adminmail,password);
				transport.sendMessage(message1,message1.getRecipients(Message.RecipientType.TO));  //set
		        transport.close();
	 
				System.out.println("==========Done========");
			}
			
			catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
			
			
			//uses JMS 
			sent=CommonUtil.sendMail(mailto);
			
			
		}
		return sent;
	}
}
