package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.BulkSMStoStudentMobileMessaging;
import com.kp.cms.bo.admin.SMSExamMarksMobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.BulkSmsToStudentForm;
import com.kp.cms.helpers.admin.BulkSMStoStudentHelper;
import com.kp.cms.helpers.admin.SmsExamMarksToStudentHelper;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admin.IBulkSMStoStudentTransaction;
import com.kp.cms.transactionsimpl.admin.BulkSMStoStudentTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class BulkSMStoStudentHandler {
	private static volatile BulkSMStoStudentHandler bulkSMStoStudentHandler = null;
	private static final Log log = LogFactory.getLog(BulkSMStoStudentHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public BulkSMStoStudentHandler()
	{
		
	}
	public static BulkSMStoStudentHandler getInstance()
	{
		if(bulkSMStoStudentHandler==null)
		{
			bulkSMStoStudentHandler = new BulkSMStoStudentHandler();
		}
		return bulkSMStoStudentHandler;
		
	}
	public List<StudentTO> getStudentListForClassIds(BulkSmsToStudentForm bulkSmsToStudentForm) throws Exception{
		log.debug("call of getStudentListForClassIds method in BulkSMStoStudentHandler.class");
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		IBulkSMStoStudentTransaction transaction = new BulkSMStoStudentTransactionImpl().getInstance();
		List<Student> studentsBO = new ArrayList<Student>();
		String[] classIds = bulkSmsToStudentForm.getClassIds();
		String classID = "";
		for(int i=0;i<classIds.length;i++)
		{
			if(i==classIds.length-1)
			{
				classID = classID+classIds[i];
			}
			else
			{
				classID = classID+classIds[i]+",";
			}
		}
		studentsBO = transaction.getStudnetForClassIds(classID);
		studentList =  BulkSMStoStudentHelper.getInstance().convertBOtoTOList(studentsBO);
		log.debug("call of getStudentListForClassIds method in BulkSMStoStudentHandler.class");
		return studentList;
	}
	public boolean sentSMStoStudents(BulkSmsToStudentForm bulkSmsToStudentForm,List<StudentTO> smsStudentList) throws Exception {
	    log.debug("call of sentSMStoStudents method in BulkSMStoStudentHandler.class");
	    boolean isSmsSend= false;
	    IBulkSMStoStudentTransaction transaction = new BulkSMStoStudentTransactionImpl().getInstance();
	    List<BulkSMStoStudentMobileMessaging> mobileMessagings = new ArrayList<BulkSMStoStudentMobileMessaging>();
	    Date date1 = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date1);
		java.sql.Date smsDate=CommonUtil.ConvertStringToSQLDate(today);
		if(smsStudentList!= null && smsStudentList.size()>0 )
		{
			
			Iterator<StudentTO> iterator =  smsStudentList.iterator();
			while(iterator.hasNext())
			{
				StudentTO studentTO = iterator.next();
				if(studentTO.isChecked())
				{
					
					String messageBody = "";
					messageBody = bulkSmsToStudentForm.getSmsMessage();
					String desc="";
					String date =new Date().toString();
					SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
					List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_ENTRY);
					if(list != null && !list.isEmpty()) {
						desc = list.get(0).getTemplateDescription();
						desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
					}
					Properties prop = new Properties();
					try {
						InputStream in = AttendanceEntryHelper.class.getClassLoader()
						.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
						prop.load(in);
					} catch (FileNotFoundException e) {	
					log.error("Unable to read properties file...", e);
						return false;
					} catch (IOException e) {
						log.error("Unable to read properties file...", e);
						return false;
					}
					String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
					String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
					String temp = "";
					temp=temp+URLEncoder.encode("Dear" + " ", "UTF-8");
					temp=temp+URLEncoder.encode("Parent, ","UTF-8");
					
					//subject wise
					
					temp = temp+ URLEncoder.encode(messageBody, "UTF-8");
					temp=temp+URLEncoder.encode(" from Principal" + " ", "UTF-8");
					String college="VET PUC";
					temp=temp+URLEncoder.encode(""+college+"", "UTF-8");
					
					BulkSMStoStudentMobileMessaging messaging = new  BulkSMStoStudentMobileMessaging();
					
					messaging.setMessagePriority(3);
					messaging.setMessageBody(temp);
					messaging.setSenderName(senderName);
					messaging.setSenderNumber(senderNumber);
					messaging.setMessageEnqueueDate(new Date());
					messaging.setStudentId(studentTO.getId());
					messaging.setClassId(studentTO.getClassId());
					String destinationNumber =  studentTO.getMobileNo1()+ studentTO.getMobileNo2();
					messaging.setIsMessageSent(false);
					messaging.setDestinationNumber(destinationNumber);
					messaging.setSmsDate(smsDate);
					List<Integer> studId=transaction.getStudentIds(smsDate);
					if(!studId.contains(studentTO.getId()))
					{
						mobileMessagings.add(messaging);	
					}
				
					
					
					
				}
				
			}
			PropertyUtil.getInstance().saveBulkSMSList(mobileMessagings);
			SMSUtil s =new SMSUtil();
	    	SMSUtils smsUtils=new SMSUtils();

			ConverationUtil converationUtil=new ConverationUtil();
	    	List<SMS_Message> listSms=converationUtil.convertBotoTOBulSMS(s.getListOfBulkSMS());
	    	List<SMS_Message> mobList=smsUtils.sendSMS(listSms);
	    	isSmsSend = s.updateBulkSMStoStudnet(converationUtil.convertTotoBOBulkSMS(mobList));
		
		}
		else
		{
			isSmsSend = false;
		}
		
	    log.debug("end of sentSMStoStudents method in BulkSMStoStudentHandler.class");
		return isSmsSend;
	}
	
	

}
