package com.kp.cms.handlers.hostel;

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
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelLeaveApprovalForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.hostel.HostelLeaveApprovalHelper;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.transactions.hostel.IHostelLeaveApprovalTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelLeaveApprovalTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.JobScheduler;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;


public class HostelLeaveApprovalHandler {
	private static final Log log = LogFactory.getLog(HostelLeaveApprovalHandler.class);
	private static volatile HostelLeaveApprovalHandler handler = null;
	public static HostelLeaveApprovalHandler getInstance(){
		if(handler == null){
			handler  =new HostelLeaveApprovalHandler();
			return handler;
		}
		return handler;
	}
	IHostelLeaveApprovalTransaction transaction = HostelLeaveApprovalTxnImpl.getInstance();
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelLeaveApprovalTo> getLeaveApprovalDetails( HostelLeaveApprovalForm objForm) throws Exception{
		List<Object[]> hlLeavesList = transaction.getLeaveApprovalDetails(objForm);
		Map<String,List<HostelLeaveApprovalTo>> mapList = HostelLeaveApprovalHelper.getInstance().convertBOToMap(hlLeavesList,objForm);
		Map<String,List<HostelLeaveApprovalTo>> previousLeaveDetailsMap = transaction.getPreviousLeaveDetailsByRegisterNo(objForm);
		List<HostelLeaveApprovalTo> leaveApprovalTolist = HostelLeaveApprovalHelper.getInstance().convertMapListToTOList(mapList,previousLeaveDetailsMap);
		if(previousLeaveDetailsMap!=null && !previousLeaveDetailsMap.isEmpty()){
			objForm.setLeaveApprovalMap(previousLeaveDetailsMap);
		}
		return leaveApprovalTolist;
	}
	/**
	 * @param leaveApprovalMapDetails
	 * @param objForm
	 * @param name 
	 * @return
	 * @throws Exception
	 */
	public List<HostelLeaveApprovalTo> getDetailsList( Map<String, List<HostelLeaveApprovalTo>> leaveApprovalMapDetails,
			HostelLeaveApprovalForm objForm, String name)throws Exception {
		List<HostelLeaveApprovalTo> toList= new ArrayList<HostelLeaveApprovalTo>();
		if(leaveApprovalMapDetails.containsKey(objForm.getRegNo())){
			List<HostelLeaveApprovalTo> leaveApprovalList = leaveApprovalMapDetails.get(objForm.getRegNo());
			if(leaveApprovalList!=null && !leaveApprovalList.isEmpty()){
				Iterator<HostelLeaveApprovalTo> iterator = leaveApprovalList.iterator();
				while (iterator.hasNext()) {
					HostelLeaveApprovalTo to = (HostelLeaveApprovalTo) iterator .next();
					if(name.equalsIgnoreCase("NoofApplication")){
							addDetailsToTheList(objForm,to,toList);
					}else if(name.equalsIgnoreCase("Approved")){
						if(to.getNoOfLeaveApproval()!=0){
							addDetailsToTheList(objForm,to,toList);
						}
					}else if(name.equalsIgnoreCase("Rejected")){
						if(to.getNoOfLeaveRejected()!=0){
							addDetailsToTheList(objForm,to,toList);
						}
					}else if(name.equalsIgnoreCase("Cancelled")){
						if(to.getNoOfLeaveCancelled()!=0){
							addDetailsToTheList(objForm,to,toList);
						}
					}
				}
			}
		}
		return toList;
	}
	/**
	 * @param toList 
	 * @param objForm 
	 * @param to 
	 * @throws Exception
	 */
	private void addDetailsToTheList(HostelLeaveApprovalForm objForm, HostelLeaveApprovalTo to, List<HostelLeaveApprovalTo> toList)throws Exception {
		if(to.getRegisterNo()!=null && !to.getRegisterNo().isEmpty()){
			objForm.setRegNo(to.getRegisterNo());
		}
		if(to.getName()!=null && !to.getName().isEmpty()){
			objForm.setStudentName(to.getName());
		}
		if(to.getClassName()!=null && !to.getClassName().isEmpty()){
			objForm.setClassName(to.getClassName());
		}
		toList.add(to);
	}
	/**
	 * @param objForm
	 * @param mode 
	 * @return
	 */
	public boolean sendSMSAndEmailToStudents(HostelLeaveApprovalForm objForm, String mode)throws Exception {
		boolean sentSms=false;
		boolean sentMail=false;
		boolean sentSMSAndMail=false;
		List<HostelLeaveApprovalTo> approvalTosList = objForm.getHostelLeaveApprovalTo();
		if(approvalTosList!=null && !approvalTosList.isEmpty()){
			String fromName = CMSConstants.HOSTEL_LEAVE_APPROVAL_MAIL_FROM_NAME;
			String subject = "Leave Application Status";
			String fromAddress=CMSConstants.MAIL_USERID;
			String senderNumber=CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER;
			String senderName=CMSConstants.KNOWLEDGEPRO_SENDER_NAME;
			String templateName = "";
			Properties prop = new Properties();
			InputStream in = JobScheduler.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			try {
				prop.load(in);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			String sendSms= prop.getProperty("knowledgepro.sms.send");
			if(mode.equalsIgnoreCase("Approve")){
				 templateName = CMSConstants.HOSTEL_LEAVE_APPROVAL;
			}else if(mode.equalsIgnoreCase("Reject")){
				 templateName = CMSConstants.HOSTEL_LEAVE_REJECT;
			}
			Iterator<HostelLeaveApprovalTo> iterator = approvalTosList.iterator();
			int count = 0;
			while (iterator.hasNext()) {
				HostelLeaveApprovalTo to = (HostelLeaveApprovalTo) iterator .next();
				if(to.getChecked()!=null && !to.getChecked().isEmpty()){
					if(to.getChecked().equalsIgnoreCase("on")){
						if(to.getEmailId()!=null && !to.getEmailId().isEmpty()){
							sentMail=sendMailToStudent(to,fromName,fromAddress,subject,templateName);
						}
						if(to.getMobileNo()!=null &&!to.getMobileNo().isEmpty()){
							sentSms = sendSmsToStudent(to,senderNumber,senderName,templateName,sendSms);
						}
						transaction.setStatus(to.getId(),objForm,mode);
						count = 1;
					}else{
						if(count == 1){
							count = 1;
						}else{
							count = 0;
						}
					}
				}else{
					if(count == 1){
						count = 1;
					}else{
						count = 0;
					}
				}
			}
			if(sentMail==true && sentSms==true)
			{
				sentSMSAndMail=true;
			}
			if(count!=1){
				objForm.setNotSelectAtLeastOne(true);
				sentSMSAndMail = false;
			}
		}
		return sentSMSAndMail;
	}
	/**
	 * @param to
	 * @param templateName 
	 * @param subject 
	 * @param fromAddress 
	 * @param fromName 
	 * @return
	 * @throws Exception
	 */
	private boolean sendMailToStudent (HostelLeaveApprovalTo to, String fromName, String fromAddress, String subject, String templateName) throws Exception{
		String desc="";
		List<GroupTemplate> list=null;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		 	list= temphandle.getDuplicateCheckList(templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO,to.getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,to.getName());
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,to.getDateAndTimeFrom());
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,to.getDateAndTimeTo());
		return sendMail(to.getEmailId(),fromName,fromAddress,subject,desc);
	}
	/**
	 * @param emailId
	 * @param fromName
	 * @param fromAddress
	 * @param subject
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	private boolean sendMail(String mailID, String fromName, String fromAddress, String sub, String desc) throws Exception{
		boolean sent=false;
		//String toAddress="sudheerkrishnam@gmail.com";
		String toAddress=mailID;
		// MAIL TO CONSTRUCTION
		String subject=sub;
		String msg=desc;
		MailTO mailto=new MailTO();
		mailto.setFromAddress(fromAddress);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(fromName);
		sent=CommonUtil.sendMail(mailto);
	return sent;
}
	/**
	 * @param to
	 * @param templateName 
	 * @param senderName 
	 * @param senderNumber 
	 * @param sendSms 
	 * @return
	 * @throws Exception
	 */
	private boolean sendSmsToStudent(HostelLeaveApprovalTo to, String senderNumber, String senderName, String templateName, String sendSms) throws Exception{
		boolean isSmsSent = false;
		String desc="";
		String mobileNo = "";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO,to.getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,to.getName());
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,to.getDateAndTimeFrom());
		desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,to.getDateAndTimeTo());
		if(to.getMobileNo()!=null && !to.getMobileNo().isEmpty()){
			mobileNo = "91"+to.getMobileNo();
		}
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				MobileMessaging mob=new MobileMessaging();
				mob.setDestinationNumber(mobileNo);
				mob.setMessageBody(desc);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setIsMessageSent(false);
				isSmsSent=PropertyUtil.getInstance().save(mob);
			}
		}
		return isSmsSent;
	}
}
