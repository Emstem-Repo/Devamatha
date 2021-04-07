package com.kp.cms.handlers.usermanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.usermanagement.CreateUserAction;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.CreateUserForm;
import com.kp.cms.forms.usermanagement.EditUserInfoForm;
import com.kp.cms.helpers.usermanagement.CreateUserHelper;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.usermanagement.ICreateUserTransaction;
import com.kp.cms.transactions.usermanagement.IUserInfoTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.CreateUserTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.UserInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.jms.SMS_Message;

public class CreateUserHandler {
	/**
	 * Singleton object of CreateUserHandler
	 */
	private static volatile CreateUserHandler createUserHandler = null;
	private static final Log log = LogFactory.getLog(CreateUserHandler.class);
	private CreateUserHandler() {
		
	}
	/**
	 * return singleton object of CreateUserHandler.
	 * @return
	 */
	public static CreateUserHandler getInstance() {
		if (createUserHandler == null) {
			createUserHandler = new CreateUserHandler();
		}
		return createUserHandler;
	}
	/**
	 * @param createUserForm
	 * @return
	 * @throws Exception
	 */
	public String checkUserInfo(String userName,int id,String employeeId,String guestId) throws Exception {
		String msg="";
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		if (userName!= null
				&& !userName.isEmpty()) {
			if (transaction.isUserNameDuplcated(userName, id)) {
				msg="UserName is Already Exists";
			}
		}
		ICreateUserTransaction transaction2=CreateUserTransactionImpl.getInstance();
		if(	employeeId!=null && !employeeId.isEmpty()){
			if(transaction2.isEmployeeDuplicated(employeeId,id)){
				msg="User Name is Already Existed for Selected Employee";
			}
		}
		if(	guestId!=null && !guestId.isEmpty()){
			if(transaction2.isGuestDuplicated(guestId,id)){
				msg="User Name is Already Existed for Selected Guest";
			}
		}
		
		return msg;
	}
	/**
	 * @param createUserForm
	 * @return
	 * @throws Exception
	 */
	public boolean addUserInfo(CreateUserForm createUserForm) throws Exception{
		Users bo=CreateUserHelper.getInstance().convertFormToBo(createUserForm);
		return PropertyUtil.getInstance().save(bo);
	}
	/**
	 * @param searchEmployeeId
	 * @param searchRoleId
	 * @param searchUserName
	 * @return
	 */
	public List<UserInfoTO> getUserDetails(String searchEmployeeId, String searchRoleId, String searchUserName, String searchGuestId) throws Exception {
		String query="select u from Users u left join u.employee e with (e.active=1 and e.isActive=1) left join u.guest g with (g.active=1 and g.isActive=1) where u.isActive=1" ;
		if(searchUserName!=null && !searchUserName.isEmpty())
				query=query+" and u.userName like '"+searchUserName+"%' " ;
		if(searchEmployeeId!=null && !searchEmployeeId.isEmpty())
				query=query+ " and u.employee.id="+searchEmployeeId;
		if(searchGuestId!=null && !searchGuestId.isEmpty())
			query=query+ " and u.guest.id="+searchGuestId;
		if(searchRoleId!=null && !searchRoleId.isEmpty())
				query=query+"  and u.roles.id= "+searchRoleId;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Users> list=transaction.getDataForQuery(query);
		return CreateUserHelper.getInstance().convertBotoTO(list);
	}
	
	/**
	 * @param userInfoTOList
	 * @param editeditUserInfoForm
	 */
	public void setRequiredDateToForm(List<UserInfoTO> userInfoTOList, EditUserInfoForm editUserInfoForm) throws Exception {

		Iterator<UserInfoTO> itr = userInfoTOList.iterator();
		UserInfoTO userInfoTO;
		while (itr.hasNext()) {
			userInfoTO = itr.next();
			editUserInfoForm.setId(userInfoTO.getId());
			if(userInfoTO.getEmployeeTO()!=null)
			{
				editUserInfoForm.setEmployeeId(String.valueOf(userInfoTO.getEmployeeTO().getId()));
			editUserInfoForm.setUserName(userInfoTO.getUserName());
			if (userInfoTO.getRolesTO() != null && userInfoTO.getRolesTO().getId() != 0) {
				editUserInfoForm.setRoleId(Integer.toString(userInfoTO.getRolesTO() .getId()));
			}
			if (userInfoTO.getDepartment() != null && userInfoTO.getDepartment().getId() != 0) {
				editUserInfoForm.setDepartmentId(Integer.toString(userInfoTO.getDepartment().getId()));
			}
			if(userInfoTO.getIsTeachingStaff()!=null){
				editUserInfoForm.setTeachingStaff(userInfoTO.getIsTeachingStaff());
			}else{
				editUserInfoForm.setTeachingStaff(false);	
			}
			if(userInfoTO.getPwd()!=null)
			editUserInfoForm.setPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
			editUserInfoForm.setIsAddRemarks(userInfoTO.getRemarksEntry());
			editUserInfoForm.setIsViewRemarks(userInfoTO.getViewRemarks());
			editUserInfoForm.setRestrictedUser(userInfoTO.getIsRestrictedUser());
			editUserInfoForm.setEnableAttendance(userInfoTO.getEnableAtendanceEntry());
			editUserInfoForm.setActive(userInfoTO.getActive());
			editUserInfoForm.setStaffType(userInfoTO.getStaffType());
			editUserInfoForm.setAlreadyLoggedIn(userInfoTO.getIsLoggedIn());
			editUserInfoForm.setMultipleLoginAllow(userInfoTO.getMultipleLoginAllow());
			if(userInfoTO.getEmployeeTO()!=null){
				editUserInfoForm.setEmployeeId(String.valueOf(userInfoTO.getEmployeeTO().getId()));
				}
			if(userInfoTO.getTillDate()!=null){
				editUserInfoForm.setTillDate(userInfoTO.getTillDate());
			}
			}
			else if(userInfoTO.getGuestFacultyTO()!=null)
			{
			editUserInfoForm.setGuestId(String.valueOf(userInfoTO.getGuestFacultyTO().getId()));
			editUserInfoForm.setUserName(userInfoTO.getUserName());
			if (userInfoTO.getRolesTO() != null && userInfoTO.getRolesTO().getId() != 0) {
				editUserInfoForm.setRoleId(Integer.toString(userInfoTO.getRolesTO() .getId()));
			}
			if (userInfoTO.getDepartment() != null && userInfoTO.getDepartment().getId() != 0) {
				editUserInfoForm.setDepartmentId(Integer.toString(userInfoTO.getDepartment().getId()));
			}
			if(userInfoTO.getIsTeachingStaff()!=null){
				editUserInfoForm.setTeachingStaff(userInfoTO.getIsTeachingStaff());
			}else{
				editUserInfoForm.setTeachingStaff(false);	
			}
			if(userInfoTO.getPwd()!=null)
			editUserInfoForm.setPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
			editUserInfoForm.setIsAddRemarks(userInfoTO.getRemarksEntry());
			editUserInfoForm.setIsViewRemarks(userInfoTO.getViewRemarks());
			editUserInfoForm.setRestrictedUser(userInfoTO.getIsRestrictedUser());
			editUserInfoForm.setEnableAttendance(userInfoTO.getEnableAtendanceEntry());
			editUserInfoForm.setActive(userInfoTO.getActive());
			editUserInfoForm.setStaffType(userInfoTO.getStaffType());
			editUserInfoForm.setAlreadyLoggedIn(userInfoTO.getIsLoggedIn());
			editUserInfoForm.setMultipleLoginAllow(userInfoTO.getMultipleLoginAllow());
			if(userInfoTO.getGuestFacultyTO()!=null){
				editUserInfoForm.setGuestId(String.valueOf(userInfoTO.getGuestFacultyTO().getId()));
				}
			if(userInfoTO.getTillDate()!=null){
				editUserInfoForm.setTillDate(userInfoTO.getTillDate());
			}
			
			}else 
			{
				//editUserInfoForm.setGuestId(String.valueOf(userInfoTO.getGuestFacultyTO().getId()));
				editUserInfoForm.setUserName(userInfoTO.getUserName());
				if (userInfoTO.getRolesTO() != null && userInfoTO.getRolesTO().getId() != 0) {
					editUserInfoForm.setRoleId(Integer.toString(userInfoTO.getRolesTO() .getId()));
				}
				if (userInfoTO.getDepartment() != null && userInfoTO.getDepartment().getId() != 0) {
					editUserInfoForm.setDepartmentId(Integer.toString(userInfoTO.getDepartment().getId()));
				}
				if(userInfoTO.getIsTeachingStaff()!=null){
					editUserInfoForm.setTeachingStaff(userInfoTO.getIsTeachingStaff());
				}else{
					editUserInfoForm.setTeachingStaff(false);	
				}
				if(userInfoTO.getPwd()!=null)
				editUserInfoForm.setPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
				editUserInfoForm.setIsAddRemarks(userInfoTO.getRemarksEntry());
				editUserInfoForm.setIsViewRemarks(userInfoTO.getViewRemarks());
				editUserInfoForm.setRestrictedUser(userInfoTO.getIsRestrictedUser());
				editUserInfoForm.setEnableAttendance(userInfoTO.getEnableAtendanceEntry());
				editUserInfoForm.setActive(userInfoTO.getActive());
				editUserInfoForm.setStaffType(userInfoTO.getStaffType());
				editUserInfoForm.setAlreadyLoggedIn(userInfoTO.getIsLoggedIn());
				editUserInfoForm.setMultipleLoginAllow(userInfoTO.getMultipleLoginAllow());
				if(userInfoTO.getTillDate()!=null){
					editUserInfoForm.setTillDate(userInfoTO.getTillDate());
				}
				}
			
		}
	}
	/**
	 * @param editUserInfoForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserInfo(EditUserInfoForm editUserInfoForm) throws Exception {
		Users bo=CreateUserHelper.getInstance().convertFormToBoForUpdate(editUserInfoForm);
		return PropertyUtil.getInstance().update(bo);
	}
	
	
	public boolean initOtpGeneration(HttpSession session,String screenName) throws Exception {
		// TODO Auto-generated method stub
		
		boolean sendMsg=false;
		boolean sentMail=false;
		boolean returnMsg=false;
		Properties prop=new Properties();
		String collegeName = CMSConstants.COLLEGE_NAME;
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String otp="";
		
		if(session.getAttribute("usermobile")!=null){
			
		
		try{
			InputStream in =CreateUserAction.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		}
		catch(FileNotFoundException e){
			log.error("Unable to read properties File........", e);
		}
		catch(IOException e){
			log.error("Unable to read properties File........", e);
		}
		
        
		otp=PasswordGenerator.getPasswordforOTP();
		session.setAttribute("OTP", otp);
		
		String temp="";
		temp=temp+URLEncoder.encode("One Time Password for Accessing "+screenName+" is "+otp,"UTF-8");
		temp=temp+URLEncoder.encode("Please use the password to access it.Please do not share this with anyone.","UTF-8");
		
		
		SMS_Message sms=new SMS_Message();
		sms.setDestination_number(session.getAttribute("usermobile").toString());
		//Check with a specific mobile no
		//String no="";
		//sms.setDestination_number(no);
		sms.setMessage_body(temp);
	    sms.setMessage_priority(String.valueOf(3));
		sms.setSender_name(senderName);
		sms.setSender_number(senderNumber);
	    List<SMS_Message> smsList=new ArrayList<SMS_Message>();
	    smsList.add(sms);
		SMSUtils smsUtils=new SMSUtils();
        List<SMS_Message> mobList=smsUtils.sendSMS(smsList);
        
        Calendar cal1 = Calendar.getInstance();
        session.setAttribute("generatedTime",cal1);
        
        if(mobList.get(0).getMessage_status()==0){
        	sendMsg=true;
        	System.out.println(session.getAttribute("usermobile").toString()+"++++++++++++++++++++++++++++++++++"+mobList.get(0).getSms_gateway_response().substring(0,7)+"+++++++++++++++++++++++++++++++++"+screenName);
        }
        else{
        	sendMsg=false;	
        }
       
		}//sms check over
       
       
		if(session.getAttribute("usermail")!=null){
			
	 	sentMail=true;
		String toAddress="";
		if(session.getAttribute("usermail").toString()!=null){
			toAddress = session.getAttribute("usermail").toString();
			
 		}
		
		String subject="One Time Password for Accessing UserInfo from "+collegeName;
		String msg= "One Time Password for Accessing "+screenName+" is "+otp+
			     " Dear "+session.getAttribute("empname").toString()+",<br/>"+" Please use the password to access it." +
				 "<br/>"+"Please do not share this with anyone.";
		
	 	
		final String adminmail=CMSConstants.MAIL_USERID;
		final String password = CMSConstants.MAIL_PASSWORD;
		final String port = CMSConstants.MAIL_PORT;
		final String host = CMSConstants.MAIL_HOST;
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
 
		Session session1 = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(adminmail,password);
				}
			});
 
		MailTO mailto=new MailTO();
		mailto.setFromName(CMSConstants.COLLEGE_NAME);
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg); 
		mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
			
		try
		{
			 MimeMessage message1 = new MimeMessage(session1);
				
				// Set from & to addresses
				InternetAddress from = new InternetAddress(adminmail,"Online Application PassWord for Employee from "+collegeName);
				
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
 
			System.out.println("Done");
		}
		
		catch (Exception e) {
			
			sentMail=false;
			System.out.println("mail="+session.getAttribute("usermail").toString()+"++++++++++++++++++++++++++++++++++"+e+"+++++++++++++++++++++++++++++++++"+screenName);
	        
		}
		
		
		//uses JMS 
		//sentMail=CommonUtil.sendMail(mailto);
		
		}//mail check over
		
		
		if(sentMail || sendMsg){
			returnMsg=true;
		}
		
		
		return returnMsg;
	 	
	}
	
}
