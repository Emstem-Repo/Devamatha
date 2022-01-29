package com.kp.cms.handlers.smartcard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.smartcard.ScLostCorrectionProcessedHelper;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO;
import com.kp.cms.transactions.smartcard.IScLostCorrectionProcessedTransaction;
import com.kp.cms.transactionsimpl.smartcard.ScLostCorrectionProcessedTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.JobScheduler;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessedHandler {
	
	private static final Log log = LogFactory.getLog(ScLostCorrectionProcessedHandler.class);
	public static volatile ScLostCorrectionProcessedHandler scHandler=null;
	
	public static ScLostCorrectionProcessedHandler getInstance(){
		if(scHandler==null)
		{
			scHandler=new ScLostCorrectionProcessedHandler();
			return scHandler;
		}
		return scHandler;
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionProcessedTO> getDetailsList(ScLostCorrectionProcessedForm scForm)throws Exception
	{
		IScLostCorrectionProcessedTransaction iScTransaction = ScLostCorrectionProcessedTransactionImpl.getInstance();
		List<ScLostCorrection> scList =iScTransaction.getDetailsList(scForm);
		if(scList!=null && !scList.isEmpty()){
			return ScLostCorrectionProcessedHelper.getInstance().pupulateScProcessedBOtoTO(scList, scForm);
		}
		return new ArrayList<ScLostCorrectionProcessedTO>();
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionProcessedTO> getAnySelected(ScLostCorrectionProcessedForm scForm) throws Exception{
		List<ScLostCorrectionProcessedTO> list= new ArrayList<ScLostCorrectionProcessedTO>();
		if(scForm.getScList()!=null && !scForm.getScList().isEmpty()){
			Iterator<ScLostCorrectionProcessedTO> scItr=scForm.getScList().iterator();
			while(scItr.hasNext()){
				ScLostCorrectionProcessedTO scTO = scItr.next();
				if(scTO.getChecked()!=null && scTO.getChecked().equalsIgnoreCase("on")){
					list.add(scTO);
				}
			}
			return list;
			}
		return list;
	}
	
	/**
	 * @param scForm
	 * @param checkedList
	 * @return
	 * @throws Exception
	 */
	public boolean addScLostCorrectionProcessed(ScLostCorrectionProcessedForm scForm, List<ScLostCorrectionProcessedTO> checkedList) 
	throws Exception{
		
		boolean flagSet=false;
		IScLostCorrectionProcessedTransaction iScTransaction = ScLostCorrectionProcessedTransactionImpl.getInstance();
		flagSet=iScTransaction.addScLostCorrectionProcessed(scForm);
		return flagSet;
		
	}
	
	/**
	 * @param scTO
	 * @param scForm 
	 * @return
	 * @throws Exception
	 */
	public boolean sendSMSToSelected(ScLostCorrectionProcessedTO scTO, ScLostCorrectionProcessedForm scForm) 
	throws Exception{
		
		Properties prop = new Properties();
		InputStream in = JobScheduler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(in);
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMARTCARD_MAIL_FROM_NAME;
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SC_LOST_CORRECTION_SMS);
		
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
			desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,scTO.getStudentName());
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_NUMBER,scTO.getRegNo());
			desc=desc.replace(CMSConstants.TEMPLATE_DATE,scTO.getAppliedDate());
			desc=desc.replace(CMSConstants.TEMPLATE_SMARTCARD_TYPE,scTO.getCardType());
			
			if(StringUtils.isNumeric(scTO.getStudentTO().getMobileNo2()) && (scTO.getStudentTO().getMobileNo2().length()==12 && desc.length()<=160)){
				if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
					MobileMessaging mob=new MobileMessaging();
					mob.setDestinationNumber(scTO.getStudentTO().getMobileNo2());
					mob.setMessageBody(desc);
					mob.setMessagePriority(3);
					mob.setSenderName(senderName);
					mob.setSenderNumber(senderNumber);
					mob.setMessageEnqueueDate(new Date());
					mob.setIsMessageSent(false);
					PropertyUtil.getInstance().save(mob);
				}
			}
		}
		else{
			desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,scTO.getEmployeeName());
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_NUMBER,scTO.getEmpId());
			desc=desc.replace(CMSConstants.TEMPLATE_DATE,scTO.getAppliedDate());
			desc=desc.replace(CMSConstants.TEMPLATE_SMARTCARD_TYPE,scTO.getCardType());
			
			if(StringUtils.isNumeric(scTO.getEmployeeTO().getMobile()) && (scTO.getEmployeeTO().getMobile().length()==12 && desc.length()<=160)){
				if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
					MobileMessaging mob=new MobileMessaging();
					mob.setDestinationNumber(scTO.getEmployeeTO().getMobile());
					mob.setMessageBody(desc);
					mob.setMessagePriority(3);
					mob.setSenderName(senderName);
					mob.setSenderNumber(senderNumber);
					mob.setMessageEnqueueDate(new Date());
					mob.setIsMessageSent(false);
					PropertyUtil.getInstance().save(mob);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @param scTO
	 * @return
	 * @throws Exception
	 */
	public boolean sendEmailToSelected(ScLostCorrectionProcessedTO scTO, ScLostCorrectionProcessedForm scForm) 
	throws Exception{
		
		Properties prop = new Properties();
		InputStream in = JobScheduler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(in);
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String fromName = CMSConstants.SMARTCARD_MAIL_FROM_NAME;
		String fromAddress=CMSConstants.MAIL_USERID;
		String subject = "Smart Card Status";
		String templateName = "";
		templateName = CMSConstants.SMARTCARD_STATUS;
		String desc="";
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(templateName);
		
	 	if(list != null && !list.isEmpty()) {
	 		desc = list.get(0).getTemplateDescription();
	 	}
	 	
	 	if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
	 		desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,scTO.getStudentName());
	 		desc=desc.replace(CMSConstants.TEMPLATE_SMS_NUMBER,scTO.getRegNo());
	 		desc=desc.replace(CMSConstants.TEMPLATE_DATE,scTO.getAppliedDate());
	 		desc=desc.replace(CMSConstants.TEMPLATE_SMARTCARD_TYPE,scTO.getCardType());
		
	 		if(scTO.getStudentTO().getEmail()!=null && !scTO.getStudentTO().getEmail().isEmpty()){
	 			if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
	 				String toAddress=scTO.getStudentTO().getEmail();
	 				MailTO mailto=new MailTO();
	 				mailto.setFromAddress(fromAddress);
	 				mailto.setToAddress(toAddress);
	 				mailto.setSubject(subject);
	 				mailto.setMessage(desc);
	 				mailto.setFromName(fromName);
	 				CommonUtil.sendMail(mailto);
	 			}
	 		}
	 	}
	 	else{
	 		desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,scTO.getEmployeeName());
	 		desc=desc.replace(CMSConstants.TEMPLATE_SMS_NUMBER,scTO.getEmpId());
	 		desc=desc.replace(CMSConstants.TEMPLATE_DATE,scTO.getAppliedDate());
	 		desc=desc.replace(CMSConstants.TEMPLATE_SMARTCARD_TYPE,scTO.getCardType());
		
	 		if(scTO.getEmployeeTO().getEmail()!=null && !scTO.getEmployeeTO().getEmail().isEmpty()){
	 			if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
	 				String toAddress=scTO.getEmployeeTO().getEmail();
	 				MailTO mailto=new MailTO();
	 				mailto.setFromAddress(fromAddress);
	 				mailto.setToAddress(toAddress);
	 				mailto.setSubject(subject);
	 				mailto.setMessage(desc);
	 				mailto.setFromName(fromName);
	 				CommonUtil.sendMail(mailto);
	 			}
	 		}
	 	
	 	}
	 	
		return false;
		
	}

	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public boolean editRemarks(ScLostCorrectionProcessedForm scForm) throws Exception{
		
		boolean flagSet=false;
		IScLostCorrectionProcessedTransaction iScTransaction = ScLostCorrectionProcessedTransactionImpl.getInstance();
		flagSet=iScTransaction.editRemarks(scForm);
		return flagSet;
		
	}
		
	

}