package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.jms.MailTO;

public class SmsMailForPmJob implements Job{
	private static final Log log = LogFactory.getLog(DocumentPendingMailJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Properties prop = new Properties();
			InputStream in = SmsMailForAmJob.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			InputStream sin = AttendanceEntryHelper.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
	        try
	        {
	        	prop.load(in);
	        	prop.load(sin);
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
	        String adminmail=CMSConstants.MAIL_USERID;
			String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
			String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
			String sendSms= prop.getProperty("knowledgepro.sms.send");
			String sendMail= prop.getProperty("knowledgepro.mail.send");
			
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			List<Object[]> mailIds = AdmissionFormTransactionImpl.getInstance().SendMailForPm(year);
			
			if(mailIds != null){
				Iterator<Object[]> iterator = mailIds.iterator();
				Iterator<Object[]> iter = mailIds.iterator();
				TemplateHandler temphandle=TemplateHandler.getInstance();
				List<GroupTemplate> mailList= temphandle.getDuplicateCheckList("Exam-Question Paper Checking Alert Mail To Teachers");
				List<SMSTemplate> smsList= SMSTemplateHandler.getInstance().getDuplicateCheckList("Exam-Question Paper Checking Alert Sms To Teachers");
				
				if(smsList != null && !smsList.isEmpty() && sendSms!=null && sendSms.equalsIgnoreCase("true")) {
					List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						MobileMessaging mob=new MobileMessaging();
						if((obj[3]!= null && !obj[3].toString().isEmpty())|| (obj[7]!= null && !obj[7].toString().isEmpty())){
						String desc ="";
						String name="";
						String phonNo="";
						if(obj[1]!=null)name=obj[1].toString();
						else if(obj[5]!=null)name=obj[5].toString();
						if(obj[3]!=null)phonNo=obj[3].toString();
						else if(obj[7]!=null) phonNo=obj[7].toString();
							
							
						 if(smsList.get(0)!=null && smsList.get(0).getTemplateDescription()!=null){
							desc = smsList.get(0).getTemplateDescription();
						   }  
						    desc = desc.replace("[TEACHER_NAME]",name);
							desc = desc.replace("[SUBJECT_CODE]",obj[9].toString());
							desc = desc.replace("[SUBJECT_NAME]",obj[10].toString());
							desc = desc.replace("[EXAM_DATE]", obj[11].toString());
							desc = desc.replace("[EXAM_SESSIONS]",obj[12].toString());
						
							if(phonNo.trim().length()==10)
								mob.setDestinationNumber("91"+phonNo);
							else
								mob.setDestinationNumber(phonNo);
							
							mob.setMessageBody(desc);
							mob.setSenderNumber(senderNumber);
							mob.setSenderName(senderName);
							mob.setMessagePriority(3);
							mob.setIsMessageSent(false);
							mob.setMessageEnqueueDate(new Date());
							smsListBos.add(mob);
						}
					}
					PropertyUtil.getInstance().saveSMSList(smsListBos);
				}
				
				if(mailList != null && !mailList.isEmpty() && sendMail!=null && sendMail.equalsIgnoreCase("true")) {
					while (iter.hasNext()) {
						Object[] obj = (Object[]) iter.next();
						MailTO mailto = new MailTO();
						if((obj[2]!= null && !obj[2].toString().isEmpty()) || (obj[6]!= null && !obj[6].toString().isEmpty())){
						String desc ="";
						String name="";
						String mailsId="";
						if(obj[1]!=null)name=obj[1].toString();
						else if(obj[5]!=null)name=obj[5].toString();
						if(obj[2]!=null)mailsId=obj[2].toString();
						else if(obj[6]!=null) mailsId=obj[6].toString();
							
							
						if(mailList.get(0)!=null && mailList.get(0).getTemplateDescription()!=null){
							desc = mailList.get(0).getTemplateDescription();
						}
						desc = desc.replace("[TEACHER_NAME]",name);
						desc = desc.replace("[SUBJECT_CODE]",obj[9].toString());
						desc = desc.replace("[SUBJECT_NAME]",obj[10].toString());
						desc = desc.replace("[EXAM_DATE]", obj[11].toString());
						desc = desc.replace("[EXAM_SESSIONS]",obj[12].toString());
						mailto.setFromAddress(adminmail);
						mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
						mailto.setMessage(desc);
						mailto.setSubject("Reminder mails For Todays Exam - Christ University");
						mailto.setToAddress(mailsId);
						CommonUtil.sendMail(mailto);
					}
				  }
				}
			}
		} catch (Exception e) {
			log.error("Error in SmsMailForPmJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from SmsMailForPmJob"+Calendar.getInstance().getTimeInMillis());
	}
}
