package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SendSmsToClassForm;
import com.kp.cms.helpers.admin.SendSmsToClassHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admin.ISendSmsToClassTransaction;
import com.kp.cms.transactionsimpl.admin.SendSmsToClassTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

public class SendSmsToClassHandler {
	/**
	 * Singleton object of SendSmsToClassHandler
	 */
	private static volatile SendSmsToClassHandler sendSmsToClassHandler = null;
	private static final Log log = LogFactory.getLog(SendSmsToClassHandler.class);
	private SendSmsToClassHandler() {
		
	}
	/**
	 * return singleton object of SendSmsToClassHandler.
	 * @return
	 */
	public static SendSmsToClassHandler getInstance() {
		if (sendSmsToClassHandler == null) {
			sendSmsToClassHandler = new SendSmsToClassHandler();
		}
		return sendSmsToClassHandler;
	}
	/**
	 * @param sendSmsToClassForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> getStudentsForClass(SendSmsToClassForm sendSmsToClassForm) throws Exception {
		// TODO Auto-generated method stub
		log.info("Entered SendSmsToClassHandler-getStudentsForClass");
		ISendSmsToClassTransaction transaction=new SendSmsToClassTransactionImpl();
		List<Student> studentList=transaction.getStudentForClass(SendSmsToClassHelper.getInstance().getStudentForClassQuery(sendSmsToClassForm)); 
		log.info("Exists SendSmsToClassHandler-getStudentsForClass");
		return SendSmsToClassHelper.getInstance().convertBoListToToList(studentList);
	}
	/**
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public boolean sendSMSToStudent(List<StudentTO> studentList,SendSmsToClassForm sendSmsToClassForm) throws Exception{
		// TODO Auto-generated method stub
		log.info("Entered SendSmsToClassHandler-sendSMSToStudent");
		Properties prop = new Properties();
		try {
			InputStream in = SendSmsToClassHandler.class.getClassLoader()
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
		
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<StudentTO> itr=studentList.iterator();
			while (itr.hasNext()) {
				StudentTO studentTO = (StudentTO) itr.next();
				if(studentTO.getMobileNo1()!=null && studentTO.getMobileNo2()!=null && !studentTO.getMobileNo1().isEmpty() && !studentTO.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2())){
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
						mob.setMessageBody(sendSmsToClassForm.getMessage());
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						smsList.add(mob);
					}
				}
			}
		}
		log.info("Exists SendSmsToClassHandler-sendSMSToStudent");
		return PropertyUtil.getInstance().saveSMSList(smsList);
	}
	public StudentTO getDetails(SendSmsToClassForm sendSmsToClassForm)throws Exception {
		// TODO Auto-generated method stub
		ISendSmsToClassTransaction transaction=new SendSmsToClassTransactionImpl();
		Student studentBO = transaction.getStudentDetails(sendSmsToClassForm);
		return SendSmsToClassHelper.getInstance().convertStudentBOToStudentTO(studentBO);
	}
	public boolean getListForSMS(SendSmsToClassForm sendSmsToClassForm)throws Exception {
		// TODO Auto-generated method stub
		List<StudentTO> studentList = sendSmsToClassForm.getStudentList();
		Properties prop = new Properties();
		try {
			InputStream in = SendSmsToClassHandler.class.getClassLoader()
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
		
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<StudentTO> itr=studentList.iterator();
			while (itr.hasNext()) {
				StudentTO studentTO = (StudentTO) itr.next();
				if(studentTO.isChecked()){
				if(studentTO.getMobileNo1()!=null && studentTO.getMobileNo2()!=null && !studentTO.getMobileNo1().isEmpty() && !studentTO.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2())){
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
						mob.setMessageBody(sendSmsToClassForm.getMessage());
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						smsList.add(mob);
					}
				  }
				}
			}
		}
		log.info("Exists SendSmsToClassHandler-sendSMSToStudent");
		return PropertyUtil.getInstance().saveSMSList(smsList);
	}
	
	
	public boolean sendSmsToSingleStudent(SendSmsToClassForm sendSmsToClassForm)throws Exception {
		// TODO Auto-generated method stub
		
		Properties prop = new Properties();
		try {
			InputStream in = SendSmsToClassHandler.class.getClassLoader()
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
		MobileMessaging mob=new MobileMessaging();
				StudentTO studentTO = sendSmsToClassForm.getStudentDetail();
				if(studentTO.getMobileNo1()!=null && studentTO.getMobileNo2()!=null && !studentTO.getMobileNo1().isEmpty() && !studentTO.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2())){
						mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
						mob.setMessageBody(sendSmsToClassForm.getMessage());
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
					}
			}
		log.info("Exists SendSmsToClassHandler-sendSMSToStudent");
		return PropertyUtil.getInstance().saveSMSForSingleStudent(mob);
	}
}
