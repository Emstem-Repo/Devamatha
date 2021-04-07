package com.kp.cms.handlers.hostel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.helpers.hostel.HostelWaitingListHelper;
import com.kp.cms.to.hostel.HostelWaitingListViewTo;
import com.kp.cms.transactions.hostel.IHostelWaitingListViewTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelWaitingListViewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.jms.SMS_Message;


public class HostelWaitingListHandler {
	
	private static final Log log=LogFactory.getLog(HostelWaitingListHandler.class);
	public static volatile HostelWaitingListHandler hostelWaitingListHandler=null;
	public static HostelWaitingListHandler getInstance()
	{
		if(hostelWaitingListHandler==null)
		{
			hostelWaitingListHandler=new HostelWaitingListHandler();
			return hostelWaitingListHandler;
		}
		return hostelWaitingListHandler;
	}
	
	IHostelWaitingListViewTransaction transaction = new HostelWaitingListViewTransactionImpl();
	
	
	public List<HostelWaitingListViewTo> searchStudentsInHostel(HlAdmissionForm hlAdmissionForm) throws Exception
	{
		
		List<HlAdmissionBookingWaitingBo> hlAdmissionBookingWaitingBoList=transaction.SearchStudentsInHostel(hlAdmissionForm);
		return HostelWaitingListHelper.getInstance().convertBoTOTos(hlAdmissionBookingWaitingBoList,hlAdmissionForm);
	}
	
	public String getHostelNameById(int hostelId) throws Exception
	{
		return transaction.getHostelNameById(hostelId);
	}
	public String getRoomTypeName(int roomTypeId) throws Exception
	{
		return transaction.getRoomTypeName(roomTypeId);
	}
	
	public boolean sendMailAndSmsForSelectedStudents(HlAdmissionForm hlAdmissionForm) throws Exception
	{
		boolean sentSms=false;
		boolean sentMail=false;
		boolean sentSMSAndMail=false;
		List<HostelWaitingListViewTo> list=hlAdmissionForm.getHlWaitingListViewList();
		if(list!=null && !list.isEmpty())
		{
		Iterator iterator=list.iterator();
		while (iterator.hasNext()) {
			String mobileNo="";
			String emailID="";
			HostelWaitingListViewTo hostelWaitingListViewTo = (HostelWaitingListViewTo) iterator.next();
			if(hostelWaitingListViewTo.getIntimateDate()==null)
			{
			if(hostelWaitingListViewTo.getChecked()!=null && hostelWaitingListViewTo.getChecked().equalsIgnoreCase("on"))
			{
				if(hostelWaitingListViewTo.getMobileNo1()!=null && !hostelWaitingListViewTo.getMobileNo1().isEmpty()){
					if(hostelWaitingListViewTo.getMobileNo1().trim().equals("0091") || hostelWaitingListViewTo.getMobileNo1().trim().equals("+91")
							|| hostelWaitingListViewTo.getMobileNo1().trim().equals("091") || hostelWaitingListViewTo.getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=hostelWaitingListViewTo.getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(hostelWaitingListViewTo.getMobileNo2()!=null && !hostelWaitingListViewTo.getMobileNo2().isEmpty()){
					mobileNo=mobileNo+hostelWaitingListViewTo.getMobileNo2();
					//mobileNo="918095960252";
				}
				if(hostelWaitingListViewTo.getEmailId()!=null && !hostelWaitingListViewTo.getEmailId().isEmpty())
				{
					emailID=hostelWaitingListViewTo.getEmailId();	
				}
				
				 sentSms=sendSMSToStudent(mobileNo,CMSConstants.HOSTEL_WAITING_LIST_INTIMATION_TEMPLATE,hostelWaitingListViewTo.getApplicationNo(),hostelWaitingListViewTo.getHostelApplicationNo());
				 sentMail=sendMailToStudent(emailID,CMSConstants.HOSTEL_WAITING_LIST_INTIMATION_TEMPLATE,hostelWaitingListViewTo.getApplicationNo(),hostelWaitingListViewTo.getHostelApplicationNo(),hostelWaitingListViewTo.getHostelName(),hostelWaitingListViewTo.getRoomTypeName(),String.valueOf(hostelWaitingListViewTo.getWaitingListPriorityNo()),hostelWaitingListViewTo.getStudentName());
				 if(sentMail==true && sentSms==true)
					{
                       int id=hostelWaitingListViewTo.getId();
                       transaction.intimatedToStudent(id);
					}
			}
		}
		}	
		if(sentMail==true && sentSms==true)
		{
			sentSMSAndMail=true;
		}
		}
		return sentSMSAndMail;
	}
	
	
	public boolean sendSMSToStudent(String mobileNo,String templateName,String applicationNo,String hostelApplicationNo) throws Exception
	{
		boolean sentSms=false;
		Properties prop = new Properties();
        InputStream in1 = HostelWaitingListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applicationNo);
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_APPLICATIONNO,hostelApplicationNo);
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			sentSms=PropertyUtil.getInstance().save(mob);
		}
		return sentSms;
		
	}
	public boolean sendMailToStudent(String emailID,String templateName,String applicationNo,String hostelApplicationNo,String hostelName,String roomTypeName,String priorityNo,String studentName) throws Exception
	{
	Properties prop = new Properties();
	String desc="";
	try {
		InputStream inStr = CommonUtil.class.getClassLoader()
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(inStr);
	} catch (FileNotFoundException e) {
	log.error("Unable to read properties file...", e);
	} catch (IOException e) {
		log.error("Unable to read properties file...", e);
	}
		List<GroupTemplate> list=null;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		 list= temphandle.getDuplicateCheckList(templateName);
		
		if(list != null && !list.isEmpty()) {

			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applicationNo);
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_APPLICATIONNO,hostelApplicationNo);
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_NAME,hostelName);
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_ROOMTYPE,roomTypeName);
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_PRIORITYNO,priorityNo);
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentName);
		
		String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
		String fromAddress=CMSConstants.MAIL_USERID;
		String subject="Hostel waiting list intimation";
		return sendMail(emailID, subject, desc, fromName, fromAddress);
	}
	
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
		boolean sent=false;
			String toAddress=mailID;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(fromAddress);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(fromName);
			
			sent=CommonUtil.sendMail(mailto);
		return sent;
}
	
}
