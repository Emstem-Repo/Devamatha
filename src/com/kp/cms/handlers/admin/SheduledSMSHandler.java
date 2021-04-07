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
import org.apache.poi.util.StringUtil;
import org.apache.taglibs.standard.tag.common.core.SetSupport;
import org.quartz.plugins.management.ShutdownHookPlugin;

import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SheduledSMSForm;
import com.kp.cms.helpers.admin.SheduledSMSHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admin.ISheduledSMSTransaction;
import com.kp.cms.transactionsimpl.admin.SheduledSMSTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class SheduledSMSHandler {

	/**
	 * Singleton object of SendSmsToClassHandler
	 */
	private static volatile SheduledSMSHandler sheduledSMSHandler = null;
	private static final Log log = LogFactory.getLog(SheduledSMSHandler.class);
	private SheduledSMSHandler() {
		
	}
	/**
	 * return singleton object of SendSmsToClassHandler.
	 * @return
	 */
	public static SheduledSMSHandler getInstance() {
		if (sheduledSMSHandler == null) {
			sheduledSMSHandler = new SheduledSMSHandler();
		}
		return sheduledSMSHandler;
	}
	public List<StudentTO> getStudentForClass(String claIds)throws Exception {
		// TODO Auto-generated method stub
		ISheduledSMSTransaction transaction = new SheduledSMSTransactionImpl();
		List<Student> students = transaction.getStudentList(claIds);
		return SheduledSMSHelper.getInstance().convertBOToTO(students);
	}
	public boolean sent(List<StudentTO> studentList,
			SheduledSMSForm sheduledSMSForm)throws Exception {
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
		List<MobileMessagingSchedule> mobList = new ArrayList<MobileMessagingSchedule>();
		if(studentList!=null && !studentList.isEmpty()){
			//Iterator<StudentTO> itr = studentList.iterator();
			MobileMessagingSchedule bo = new MobileMessagingSchedule();
			for(StudentTO to:studentList){
				if(to.getMobileNo1()!=null && !to.getMobileNo1().isEmpty() && to.getMobileNo2()!=null && !to.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(to.getMobileNo1())&& StringUtils.isNumeric(to.getMobileNo2())){
						bo.setDestinationNumber(to.getMobileNo1()+to.getMobileNo2());
					}
				}
				bo.setMessageBody(sheduledSMSForm.getMessage());
				bo.setSenderName(senderName);
				bo.setSenderNumber(senderNumber);
				bo.setSendingDate(CommonUtil.ConvertStringToDate(sheduledSMSForm.getDate()));
				bo.setSendTime(sheduledSMSForm.getHours()+":"+ sheduledSMSForm.getMin());
				bo.setMessageEnqueueDate(new Date());
				bo.setIsMessageSent(false);
				bo.setMessagePriority(3);
				mobList.add(bo);
			}
		}
		return PropertyUtil.getInstance().saveSMSSheduledList(mobList);
	}
}
