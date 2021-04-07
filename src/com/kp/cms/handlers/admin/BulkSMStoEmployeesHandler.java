package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.BulkSMStoEmployeeMobileMessaging;
import com.kp.cms.bo.admin.BulkSMStoStudentMobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.BulkSMStoEmployeesForm;
import com.kp.cms.helpers.admin.BulkSMStoEmployeesHelper;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.admin.IBulkSMStoEmployeesTransaction;
import com.kp.cms.transactionsimpl.admin.BulkSMStoEmployeesTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class BulkSMStoEmployeesHandler {
	private static volatile BulkSMStoEmployeesHandler bulkSMStoEmployeesHandler = null;
	private static final Log log = LogFactory.getLog(BulkSMStoEmployeesHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public BulkSMStoEmployeesHandler()
	{
		
	}
	public static BulkSMStoEmployeesHandler getInstance()
	{
		if(bulkSMStoEmployeesHandler==null)
		{
			bulkSMStoEmployeesHandler = new BulkSMStoEmployeesHandler();
		}
		return bulkSMStoEmployeesHandler;
		
	}
	public Map<Integer, String> getRollMap() throws Exception{
        log.debug("call of getRollMap method in BulkSMStoEmployeesHandler.class");
        Map<Integer, String> rollMap = new HashMap<Integer, String>();
        IBulkSMStoEmployeesTransaction  transaction = new BulkSMStoEmployeesTransactionImpl().getInstance();
        rollMap = transaction.getRollMap();
        log.debug("end of getRollMap method in BulkSMStoEmployeesHandler.class");
		return rollMap;
	}
	public List<UserInfoTO> getEmployeeList(BulkSMStoEmployeesForm bulkSMStoEmployeesForm) throws Exception{
		log.debug("call of getEmployeeList method in BulkSMStoEmployeesHandler.class");
		IBulkSMStoEmployeesTransaction  transaction = new BulkSMStoEmployeesTransactionImpl().getInstance();
		List<UserInfoTO> userList= new ArrayList<UserInfoTO>();
		List<Users> userBolist = new ArrayList<Users>();
		String[] rolls = bulkSMStoEmployeesForm.getRolls();
		String selectedRolls = "";
		for(int i=0;i<rolls.length;i++)
		{
			if(i==rolls.length-1)
			{
				selectedRolls = selectedRolls+rolls[i];
			}
			else
			{
				selectedRolls = selectedRolls+rolls[i]+",";
			}
		}
		userBolist = transaction.getEmployeeList(selectedRolls);
		userList = BulkSMStoEmployeesHelper.getInstance().convertBOtoTOList(userBolist);
		log.debug("end of getEmployeeList method in BulkSMStoEmployeesHandler.class");
		return userList;
	}
	public boolean sentSMStoEmployee(BulkSMStoEmployeesForm bulkSMStoEmployeesForm,List<UserInfoTO> usersLiseNew)  throws Exception{
	    log.debug("call of sentSMStoEmployee method in BulkSMStoEmployeesHandler.class");
	    boolean isSmsSend = false;
	    IBulkSMStoEmployeesTransaction  transaction = new BulkSMStoEmployeesTransactionImpl().getInstance();
	    List<BulkSMStoEmployeeMobileMessaging> mobileMessagings = new ArrayList<BulkSMStoEmployeeMobileMessaging>();
	    Date date1 = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date1);
		java.sql.Date smsDate=CommonUtil.ConvertStringToSQLDate(today);
		if(usersLiseNew!= null && usersLiseNew.size()>0 )
		{
			
			Iterator<UserInfoTO> iterator =  usersLiseNew.iterator();
			while(iterator.hasNext())
			{
				UserInfoTO infoTO = iterator.next();
				if(infoTO.isChecked())
				{
					
					String messageBody = "";
					messageBody = bulkSMStoEmployeesForm.getMessageBody();
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
					temp=temp+URLEncoder.encode("Staff, ","UTF-8");
					
					//subject wise
					
					temp = temp+ URLEncoder.encode(messageBody, "UTF-8");
					temp=temp+URLEncoder.encode(" from Principal" + " ", "UTF-8");
					String college="VET PUC";
					temp=temp+URLEncoder.encode(""+college+"", "UTF-8");
					
					BulkSMStoEmployeeMobileMessaging messaging = new  BulkSMStoEmployeeMobileMessaging();
					
					messaging.setMessagePriority(3);
					messaging.setMessageBody(temp);
					messaging.setSenderName(senderName);
					messaging.setSenderNumber(senderNumber);
					messaging.setMessageEnqueueDate(new Date());
					messaging.setUserId(infoTO.getId());
					messaging.setRollid(infoTO.getRollid());
					String destinationNumber =  infoTO.getPhNo();
					messaging.setIsMessageSent(false);
					messaging.setDestinationNumber(destinationNumber);
					messaging.setSmsDate(smsDate);
					List<Integer> userIds=transaction.getUserIds(smsDate);
					if(!userIds.contains(infoTO.getId()))
					{
						mobileMessagings.add(messaging);	
					}
				
					
					
					
				}
				
			}
			PropertyUtil.getInstance().saveBulkSMSEmployeeList(mobileMessagings);
			SMSUtil s =new SMSUtil();
	    	SMSUtils smsUtils=new SMSUtils();

			ConverationUtil converationUtil=new ConverationUtil();
	    	List<SMS_Message> listSms=converationUtil.convertBotoTOBulSMSEmployee(s.getListOfBulkSMSEmployee());
	    	List<SMS_Message> mobList=smsUtils.sendSMS(listSms);
	    	isSmsSend = s.updateBulkSMStoEmployee(converationUtil.convertTotoBOBulkSMSEmployee(mobList));
		
		}
		else
		{
			isSmsSend = false;
		}
		
	    log.debug("end of sentSMStoEmployee method in BulkSMStoEmployeesHandler.class");
		return isSmsSend;
	}
	
	
	public boolean saveBulkSMSEmployeeList(List<BulkSMStoEmployeeMobileMessaging> mobileMessagings) throws BusinessException, ApplicationException {
		log.debug("call of saveBulkSMSList method in PropertyUtil.class");
		Session session = null;
		Transaction transaction = null;
		BulkSMStoEmployeeMobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<BulkSMStoEmployeeMobileMessaging> tcIterator = mobileMessagings.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}

			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
		
	}
	
	
}
