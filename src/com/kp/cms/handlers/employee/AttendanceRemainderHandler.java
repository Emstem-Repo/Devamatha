package com.kp.cms.handlers.employee;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
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

import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.helpers.employee.AttendanceRemainderHelper;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.MailMessage;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class AttendanceRemainderHandler {
	/**
	 * Singleton object of AttendanceRemainderHandler
	 */
	private static volatile AttendanceRemainderHandler attendanceRemainderHandler = null;
	private static final Log log = LogFactory.getLog(AttendanceRemainderHandler.class);
	private AttendanceRemainderHandler() {
		
	}
	/**
	 * return singleton object of AttendanceRemainderHandler.
	 * @return
	 */
	public static AttendanceRemainderHandler getInstance() {

		if (attendanceRemainderHandler == null) {
			attendanceRemainderHandler = new AttendanceRemainderHandler();
		}
		return attendanceRemainderHandler;
	}
	/**
	 * @throws Exception
	 */
	public void checkAttendanceForTheCurrentDayAndTime() throws Exception {
		log.info("Entered into checkAttendanceForTheCurrentDayAndTime");
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		String holidayQuery="select * from emp_holidays where is_active=1 and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between start_date and end_date";
		List<Object[]> holidayList=transaction.getStudentHallTicket(holidayQuery);
		if(holidayList.isEmpty()){
			String query=" select employee.current_address_mobile1, employee.first_name, employee.middle_name, employee.last_name,employee.fingerPrintId " +
						 " from employee left join emp_type ON employee.emp_type_id = emp_type.id where employee.is_punching_exemption=0 and employee.active=1 and employee.is_active=1 and employee.fingerPrintId is not null " +
						 " and employee.fingerPrintId!='' and employee.current_address_mobile1 is not null " +
						 " and ('"+CommonUtil.getCurrentTime()+"' >= ifnull(employee.time_in, emp_type.time_in)) " +
						 " and employee.id not " +
						 " in ( select employee_id from emp_attendance where date ='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' ) " +
						 " and employee.id not in (select employee_id from emp_apply_leave e  where '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between date(from_date) and date(to_date)) "+
						 " and employee.department_id not in " + /*New sub-query added by Cimi on 28/12/12 as message should not be sent for staffs those who have vacation*/ 
						 "( "+
						 " select department_id from emp_event_vacation_department " +
									" inner join emp_event_vacation ON emp_event_vacation_department.emp_event_vacation_id = emp_event_vacation.id " +
									" where emp_event_vacation.is_active=1 and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between from_date and to_date and " +
									" type = 'Vacation' and emp_event_vacation_department.department_id = employee.department_id " +
									" and emp_event_vacation.is_teaching_staff=employee.emp_teaching_staff " +
						 ") " + /*Till here added by Cimi on 28/12/12 as message should not be sent for staffs those who have vacation*/
						 " group by employee.id order by employee.id ";
			
			List<Object[]> list=transaction.getStudentHallTicket(query);
			List<MobileMessaging> smsList=AttendanceRemainderHelper.getInstance().convertEmpListToSMSList(list);
			log.info("Exit from checkAttendanceForTheCurrentDayAndTime");
			PropertyUtil.getInstance().saveSMSList(smsList);
		}
	}
	
	public void sendingSMS() throws Exception{
    	SMSUtil s =new SMSUtil();
    	SMSUtils smsUtils=new SMSUtils();
    	ConverationUtil converationUtil=new ConverationUtil();
    	List<SMS_Message> list=converationUtil.convertBotoTO(s.getListOfSMS());
    	List<SMS_Message> mobList=smsUtils.sendSMS(list);
    	s.updateSMS(converationUtil.convertTotoBO(mobList));
    	List<SMS_Message> shedulList = converationUtil.convertSheduleBOtoTO(s.getMessageList());
    	List<SMS_Message> moList=smsUtils.sendSMS(shedulList);
    	s.updateSMSForSchedule(converationUtil.convertTotoBOForSchedule(moList));
	}
	
	/**
	 * @throws Exception
	 */
	public void sendingMail() throws Exception {
		boolean isSend=false;
			List<GenerateMail> mailList=AdmissionFormHandler.getInstance().getMails();
			if(mailList!=null && !mailList.isEmpty()){
				Iterator<GenerateMail> itr=mailList.iterator();
				while (itr.hasNext()) {
					GenerateMail generateMail = (GenerateMail) itr.next();
					try{
					isSend=false;
					if(generateMail.getFilePath()!=null && !generateMail.getFilePath().isEmpty()){
						if(generateMail.getFromAddress() != null && generateMail.getToAddress() != null && !generateMail.getFromAddress().trim().isEmpty() && !generateMail.getToAddress().trim().isEmpty()){
									File file=new File(generateMail.getFilePath());
								    File[] files=file.listFiles();
								    MimeMessage message = new MimeMessage(MailMessage.session);
									
									// Set from & to addresses
									InternetAddress from = new InternetAddress(generateMail.getFromAddress(),
											generateMail.getFromName());
									
									InternetAddress toAssociate = new InternetAddress(generateMail.getToAddress());
									message.setSubject(generateMail.getSubject());
									message.setFrom(from);
									message.addRecipient(Message.RecipientType.TO, toAssociate);
								    MimeBodyPart mailBody = new MimeBodyPart();
								    mailBody.setText(generateMail.getMessage());
								    MimeMultipart mimeMultipart = new MimeMultipart();
								    for(int filelist=0; filelist < files.length; filelist++){
								       MimeBodyPart pdfBodyPart = new MimeBodyPart();
								       FileDataSource fileDataSource = new FileDataSource(generateMail.getFilePath()+"/"+files[filelist].getName()) {
								                @Override
								                public String getContentType() {
								                    return "application/pdf";
								                }
								       };
								       pdfBodyPart.setDataHandler(new DataHandler(fileDataSource));
									   pdfBodyPart.setFileName(files[filelist].getName());
									   mimeMultipart.addBodyPart(pdfBodyPart);
								    }
								    mimeMultipart.addBodyPart(mailBody);
								    message.setContent(mimeMultipart);
								    Properties config = new Properties() {
										{
											/*put("mail.smtps.auth", "true");
											put("mail.smtp.host", "smtp.gmail.com");
											put("mail.smtp.port", "465");
											put("mail.smtp.starttls.enable", "true");
											put("mail.transport.protocol", "smtps");*/
										
											put("mail.smtp.host", "smtp.live.com");
											put("mail.smtp.auth", "true");
											put("mail.transport.protocol", "smtp");
											put("mail.smtp.starttls.enable", "true");
											put("mail.smtp.port", "587");

										}
									}; 
									Session carrierSession = Session.getInstance(config, new Authenticator() {
										@Override
										protected PasswordAuthentication getPasswordAuthentication() {
											return new PasswordAuthentication(CMSConstants.MAIL_USERID,
													CMSConstants.MAIL_PASSWORD);
										}
									});
									Transport transport = carrierSession.getTransport("smtp");
									transport.connect("smtp.live.com",CMSConstants.MAIL_USERID,
											CMSConstants.MAIL_PASSWORD);
									transport.sendMessage(message, 
							        		message.getRecipients(Message.RecipientType.TO));  //set
							        transport.close();
							        //File directory = new File(generateMail.getFilePath());
							        if(file.isDirectory()){
							        	//File[] files1 = directory.listFiles();
								        if(files!=null){
								        	for(File f: files) {
								        		if(f.exists()){
								        			boolean isfile=f.delete();
									        		System.out.println(isfile);
								        		}
								        	}
								        }
								        boolean isdelete = file.delete();
								        System.out.println(isdelete);
							        }
							        
							        PropertyUtil.getInstance().delete(generateMail);
								}  
					}else if(generateMail.getFromAddress() != null && generateMail.getToAddress() != null && !generateMail.getFromAddress().trim().isEmpty() && !generateMail.getToAddress().trim().isEmpty()){
						String[] recipients=generateMail.getToAddress().split(",");
						for (int i = 0; i < recipients.length; i++)
						{
							if(CommonUtil.validateEmailID(generateMail.getFromAddress()) && CommonUtil.validateEmailID(recipients[i])){
								MimeMessage message = new MimeMessage(MailMessage.session);
								
								// Set from & to addresses
								InternetAddress from = new InternetAddress(generateMail.getFromAddress(),
										generateMail.getFromName());
								
								InternetAddress toAssociate = new InternetAddress(recipients[i]);
								
								message.setFrom(from);
								message.addRecipient(Message.RecipientType.TO, toAssociate);
								
								// Set subject & message content
								message.setSubject(generateMail.getSubject());
								// message.setContent(mailTO.getMessage(), "text/html");
								
								// Create the Multipart to be added the parts to
								Multipart mp = new MimeMultipart();
								
								// Create and fill the first message part
								MimeBodyPart mbp = new MimeBodyPart();
								mbp.setText(generateMail.getMessage(), "UTF-8", "html");
								
								String attachment = generateMail.getAttachment();
								// Attach the files to the message
								if (attachment != null || !StringUtils.isEmpty(attachment)) {
									FileDataSource fds = new FileDataSource(attachment);
									mbp.setDataHandler(new DataHandler(fds));
									mbp.setFileName(fds.getName());
									// mp.addBodyPart(mbp);
								}
								mp.addBodyPart(mbp);
								// Add the Multipart to the message
								message.setContent(mp);
								Transport transport=MailMessage.initTransport();
								// Send message
								//					Transport.send(message);
								//					transport.connect();
								transport.sendMessage(message,
										message.getRecipients(Message.RecipientType.TO));
								//			        transport.close();
								PropertyUtil.getInstance().delete(generateMail);
								// Set to true if no errors encountered
							}
						}
					}else{
						
						PropertyUtil.getInstance().delete(generateMail);
					}
					// This delete code was in finally block. written here because 
					// if any network exception occurs the record should not get deleted from database. 
					 
					} catch (IOException e) {
						isSend=true;
						log.error("Error in sending mail...::" + e);
					} catch (MessagingException m) {
						isSend=true;
						log.error("Error in sending mail...::" + m);
					} catch (Exception e) {
						isSend=true;
						// No need to process exception because
						// if one is thrown, isSent will be false
						log.error("Error in sending mail...::" + e);
					}finally{
						if(isSend)
							MailMessage.removeSession();
					}
				}
			}
	}
}
