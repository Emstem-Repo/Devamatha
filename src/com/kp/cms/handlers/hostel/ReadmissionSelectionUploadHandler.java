package com.kp.cms.handlers.hostel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ReadmissionSelectionUploadForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.SendSmsToClassHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.helpers.hostel.ReadmissionSelectionUploadHelper;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.UploadTheOfflineApplicationDetailsToSystemTo;
import com.kp.cms.transactions.hostel.IReadmissionSelectionUploadTransaction;
import com.kp.cms.transactionsimpl.hostel.ReadmissionSelectionUploadTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.print.HtmlPrinter;

public class ReadmissionSelectionUploadHandler {
	private static final Log log = LogFactory.getLog(ReadmissionSelectionUploadHandler.class);
	private static volatile ReadmissionSelectionUploadHandler readmissionUploadHandler = null;
	
	private ReadmissionSelectionUploadHandler() {
	}
	
	/**
	 * @return
	 */
	public static ReadmissionSelectionUploadHandler getInstance() {
		if (readmissionUploadHandler == null) {
			readmissionUploadHandler = new ReadmissionSelectionUploadHandler();
		}
		return readmissionUploadHandler;
	}
	IReadmissionSelectionUploadTransaction transaction=ReadmissionSelectionUploadTransactionImpl.getInstance();
	
	
	/**
	 * @param regNumList
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer>  getStudentIdByStudentRegNum(List<String> regNumList)throws Exception {
		log.info("enterd in  getStudentIdByStudentRegNum method");
		return transaction.getStudentIdByStudentRegNum(regNumList);
	}
	
	/**
	 * @param results
	 * @param objform
	 * @param registerNumMap
	 * @return
	 * @throws Exception
	 */
	public boolean uploadOfflineApplication(List<UploadTheOfflineApplicationDetailsToSystemTo> results,ReadmissionSelectionUploadForm objform,Map<String,Integer> registerNumMap)throws Exception {
		Map<Integer,HostelOnlineApplication> boList=ReadmissionSelectionUploadHelper.getInstance().covertToToBo(results,objform);
		boolean issaved=transaction.saveUploadOfflineApplication(boList,objform,registerNumMap);
		return issaved;
	}
	
	public boolean sendingSMSToStudents(List<HostelOnlineApplicationTo> selectedStudentList)throws Exception {
		Properties prop = new Properties();
		List<SMSTemplate> list=null;
		String desc="";
		
		try {
			InputStream in = SendSmsToClassHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
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
		
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
	
		list= temphandle.getDuplicateCheckList(CMSConstants.HOSTEL_READMISSION_SELECTION_SMS);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		Iterator<HostelOnlineApplicationTo> itr=selectedStudentList.iterator();
		while(itr.hasNext()) {
			HostelOnlineApplicationTo to=itr.next();
			String registerNo=to.getStudentRegNo();
			String studentName=to.getStudentname();
			String hostel=to.getHlHostelName();
			String roomType=to.getSelectedRoomType();
			String message =desc;
			
			if(registerNo!=null && !registerNo.isEmpty())
			message = message.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,registerNo);
			if(studentName!=null && !studentName.isEmpty())
			message = message.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentName);
			if(hostel!=null && !hostel.isEmpty())
			message = message.replace(CMSConstants.TEMPLATE_HOSTEL_NAME,hostel);
			if(roomType!=null && !roomType.isEmpty())
				message = message.replace(CMSConstants.TEMPLATE_HOSTEL_ROOMTYPE,roomType);
			
			String mobileNo="";
			String mobileNo1=to.getMobileNo1();
			String mobileNo2=to.getMobileNo2();
			if(mobileNo1!=null && !mobileNo1.isEmpty()){
				if(mobileNo1.trim().equals("0091") || mobileNo1.trim().equals("+91")
						|| mobileNo1.trim().equals("091") || mobileNo1.trim().equals("0"))
					mobileNo = "91";
				else
					mobileNo=mobileNo1;
			}else{
				mobileNo="91";
			}
			mobileNo=mobileNo+mobileNo2;
			if(mobileNo !=null &&  !mobileNo.isEmpty() && StringUtils.isNumeric(mobileNo) && mobileNo.length()==12 && 
					message!=null && !message.isEmpty() && message.length()<=160 ){
				
					MobileMessaging mob=new MobileMessaging();
					mob.setDestinationNumber(mobileNo);
					mob.setMessageBody(message);
					mob.setMessagePriority(3);
					mob.setSenderName(senderName);
					mob.setSenderNumber(senderNumber);
					mob.setMessageEnqueueDate(new Date());
					mob.setIsMessageSent(false);
					smsList.add(mob);
			}
		}
		return PropertyUtil.getInstance().saveSMSList(smsList);
	}
	
	
	public boolean sendingMailToStudents(List<HostelOnlineApplicationTo> selectedStudentList)throws Exception {
		List<GroupTemplate> list=null;
		boolean mailSent=false;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		String desc=" ";
			 list= temphandle.getDuplicateCheckList(CMSConstants.HOSTEL_READMISSION_SELECTION_SMS);
			 if(list != null && !list.isEmpty()) {
				 desc = list.get(0).getTemplateDescription();
			 }
			 
				Iterator<HostelOnlineApplicationTo> itr=selectedStudentList.iterator();
				while(itr.hasNext()) {
					HostelOnlineApplicationTo to=itr.next();
					String registerNo=to.getStudentRegNo();
					String studentName=to.getStudentname();
					String hostel=to.getHlHostelName();
					String roomType=to.getSelectedRoomType();
					
					String message =desc;
					if(registerNo!=null && !registerNo.isEmpty())
					message = message.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,registerNo);
					if(studentName!=null && !studentName.isEmpty())
					message = message.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentName);
					if(hostel!=null && !hostel.isEmpty())
					message = message.replace(CMSConstants.TEMPLATE_HOSTEL_NAME,hostel);
					if(roomType!=null && !roomType.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_HOSTEL_ROOMTYPE,roomType);
					
		
		
					String subject= CMSConstants.HOSTEL_READMISSION_SELECTION_SMS;
					String emailAddress=to.getEmail();
					//send mail
					boolean isMailSent=false;
					if((emailAddress!=null && !emailAddress.isEmpty()) && (message!=null && !message.isEmpty()))
						isMailSent=AdmissionFormHandler.getInstance().sendMail(emailAddress,subject,message);
					if(isMailSent){
						mailSent=true;
					}
					//print letter
					HtmlPrinter.printHtml(message);
				}
		
	return mailSent;	
	}

}
