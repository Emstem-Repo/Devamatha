package com.kp.cms.helpers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;

public class AttendanceRemainderHelper {
	/**
	 * Singleton object of AttendanceRemainderHelper
	 */
	private static volatile AttendanceRemainderHelper attendanceRemainderHelper = null;
	private static final Log log = LogFactory.getLog(AttendanceRemainderHelper.class);
	private AttendanceRemainderHelper() {
		
	}
	/**
	 * return singleton object of AttendanceRemainderHelper.
	 * @return
	 */
	public static AttendanceRemainderHelper getInstance() {
		if (attendanceRemainderHelper == null) {
			attendanceRemainderHelper = new AttendanceRemainderHelper();
		}
		return attendanceRemainderHelper;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<MobileMessaging> convertEmpListToSMSList(List<Object[]> list) throws Exception {
		log.info("Entered into convertEmpListToSMSList");
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		
		Properties prop = new Properties();
		try {
			InputStream in = AttendanceEntryHelper.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
		}
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		
		String desc="";
		List<SMSTemplate> template= SMSTemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.TEMPLATE_EMPLOYEE_ATTENDANCE_REMAINDER);
		if(template != null && !template.isEmpty()) {
			desc = template.get(0).getTemplateDescription();
		}
		if(list!=null && !list.isEmpty() && !desc.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[0]!=null){
					if(template != null && !template.isEmpty()) {
						desc = template.get(0).getTemplateDescription();
					}
					MobileMessaging mob=new MobileMessaging();
					if(obj[0].toString().trim().length()==10)
						mob.setDestinationNumber("91"+obj[0].toString());
					else
						mob.setDestinationNumber(obj[0].toString());
					
					String name=obj[1]!=null?obj[1].toString():""+" "+obj[2]!=null?obj[2].toString():""+" "+obj[3]!=null?obj[3].toString():"";
					String fingerPrintId=obj[4]!=null?obj[4].toString():"";
					desc=desc.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME, name);
					desc=desc.replace(CMSConstants.TEMPLATE_FINGER_PRINTID, fingerPrintId);
					mob.setMessageBody(desc);
					mob.setSenderNumber(senderNumber);
					mob.setSenderName(senderName);
					mob.setMessagePriority(3);
					mob.setIsMessageSent(false);
					mob.setMessageEnqueueDate(new Date());
					smsList.add(mob);
				}
			}
		}
		log.info("Exit from convertEmpListToSMSList");
		return smsList;
	}
}
