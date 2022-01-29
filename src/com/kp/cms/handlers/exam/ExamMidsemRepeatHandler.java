package com.kp.cms.handlers.exam;

import java.io.InputStream;
import java.util.ArrayList;
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
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidSemRepeatExemption;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatDetails;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admin.RepeatMidSemAppForm;
import com.kp.cms.forms.exam.ExamMidsemRepeatForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.exam.ExamMidsemRepeatHelper;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamMidsemRepeatTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SendingSmsJob;
import com.kp.cms.utilities.jms.MailTO;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemRepeatHandler {
	
	private static volatile ExamMidsemRepeatHandler examMidsemExemptionHandler = null;
	private static final Log log = LogFactory.getLog(ExamMidsemRepeatHandler.class);
	private ExamMidsemRepeatHandler() {
	}
	/**
	 * return singleton object of examMidsemExemptionHandler.
	 * @return
	 */
	public static ExamMidsemRepeatHandler getInstance() {
		if (examMidsemExemptionHandler == null) {
			examMidsemExemptionHandler = new ExamMidsemRepeatHandler();
		}
		return examMidsemExemptionHandler;
	}
	IExamMidsemRepeatTransaction transaction = ExamMidsemRepeatTransactionImpl.getInstance();
	/**
	 * @param year
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, String> getExamMidsemNameList(int year) throws Exception {
		
		ArrayList<KeyValueTO> examByyear = transaction.getExamMidsemByYear(year);
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByyear) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	/**
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamNameList(int academicYear) throws Exception {
		
		ArrayList<KeyValueTO> examByyear = transaction.getExamByYear(academicYear);
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByyear) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception 
	 */
	public boolean getRunSetDataToTable(ExamMidsemRepeatForm exemptionForm) throws Exception {
		boolean flag=false;
		boolean checkExist=false;
		List<ExamMidsemRepeat> previousData=transaction.getpreviousData(exemptionForm);
		if(previousData!=null && !previousData.isEmpty()){
		 checkExist=ExamMidsemRepeatHelper.getInstance().checkFoDelete(previousData);
		 if(!checkExist){
			transaction.deleteAllData(previousData,exemptionForm);
		 }else{
			 throw new SubjectGroupNotDefinedException();
		  }
    	 }
		List<Object[]> repeatBo= transaction.getRunSetDataToTable(exemptionForm);
		if(repeatBo==null || repeatBo.isEmpty()){
		  throw new DataNotFoundException();
		}
		List<ExamMidsemRepeat> allData= ExamMidsemRepeatHelper.getInstance().convertBOsToTOs(repeatBo, exemptionForm);
		flag=transaction.saveData(allData);
	    return flag;
	}
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public ExamDefinitionBO getExamName(ExamMidsemRepeatForm exemptionForm) throws Exception 
	{
		 ExamDefinitionBO examName = transaction.getExamName(exemptionForm);
		return examName;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamMidsemRepeatTO> setDataToForm(LoginForm loginForm) throws Exception{
		ExamMidsemRepeat previousData=transaction.getDataForForm(loginForm);
		List<ExamMidsemRepeatTO> dataToForm= ExamMidsemRepeatHelper.getInstance().convertDataToForm(previousData,loginForm);
		return dataToForm;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamMidsemRepeatTO> setDataToFormPrint(LoginForm loginForm) throws Exception{
		ExamMidsemRepeat previousData=transaction.getDataForForm(loginForm);
		List<ExamMidsemRepeatTO> dataToForm= ExamMidsemRepeatHelper.getInstance().convertDataToFormPrints(previousData,loginForm);
		return dataToForm;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public boolean setDataToBos(LoginForm loginForm) throws Exception{
		ExamMidsemRepeat oneData= ExamMidsemRepeatHelper.getInstance().convertTOsToBo(loginForm);
		boolean flag=transaction.updateOneData(oneData);
		ExamMidsemRepeat previousData=transaction.getDataForForm(loginForm);
		
		Properties prop = new Properties();
		InputStream in = SendingSmsJob.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		 try{
	        	prop.load(in);
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
			}
		String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_SMS;
		String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_MAIL;
		String subject= prop.getProperty("knowledgepro.midSem.repeat.exam.submission.Subject");
		if(flag){
			SendMailsToStudent(loginForm.getFeeEndDate() ,previousData,templetNameMails,prop,subject);
			SendSmsToStudent(previousData,templetNameSms,prop);
		}
		
		return flag;
	}
	
	/**
	 * @param loginForm
	 * @param previousData 
	 * @param templetName 
	 * @param prop 
	 * @throws Exception
	 */
	private void SendSmsToStudent(ExamMidsemRepeat previousData, String templetName, Properties prop) throws Exception{
		
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String desc="";
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templetName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
	
			String mobileNo="";
			if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null && !previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
				if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
						|| previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
					mobileNo = "91";
				else
					mobileNo=previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
			}else{
				mobileNo="91";
			}
			if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
				mobileNo=mobileNo+previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
			}
			
		
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, previousData.getStudentId().getRegisterNo());
		
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			smsListBos.add(mob);
		   }
	    }
		 PropertyUtil.getInstance().saveSMSList(smsListBos);
	   }
	}
	/**
	 * @param loginForm
	 * @param previousData 
	 * @param templetNameMails 
	 * @param prop 
	 * @param subject2 
	 * @throws Exception
	 */
	private void SendMailsToStudentAdmin(String ApprovedOrRejected, String feeEndDate,ExamMidsemRepeat previousData, String templetNameMails, Properties prop, String subject) throws Exception{
	
		String adminmail=CMSConstants.MAIL_USERID;
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
		if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
		MailTO mailto = new MailTO();
		String desc ="";
		String subjectName="";
		Iterator<ExamMidsemRepeatDetails>  itrr=previousData.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
			if(ApprovedOrRejected!=null && ApprovedOrRejected.equalsIgnoreCase("Rejected")){
				if(examMidDetails.getIsRejected()!=null && examMidDetails.getIsRejected()){
				  if(subjectName==null || subjectName.isEmpty()){
					subjectName=examMidDetails.getSubject().getName();
				  }else{
				    subjectName=subjectName +","+examMidDetails.getSubject().getName();
				 }
			  }
			}else if(ApprovedOrRejected!=null && ApprovedOrRejected.equalsIgnoreCase("Approved"))
			{
				if(examMidDetails.getIsApproved()!=null && examMidDetails.getIsApproved()){
					  if(subjectName==null || subjectName.isEmpty()){
						subjectName=examMidDetails.getSubject().getName();
					  }else{
					    subjectName=subjectName +","+examMidDetails.getSubject().getName();
					 }
				  }
			}
			
		}
		String mailsId=previousData.getStudentId().getAdmAppln().getPersonalData().getEmail();
		TemplateHandler temphandle=TemplateHandler.getInstance();	
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,templetNameMails);
		
		if(list != null && !list.isEmpty()) {
		desc = list.get(0).getTemplateDescription();
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, previousData.getStudentId().getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_SUBJECT_NAME, subjectName);
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME, previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		desc=desc.replace(CMSConstants.TEMPLATE_CLASS, previousData.getClassId().getName());
		desc=desc.replace(CMSConstants.TEMPLATE_FEE_END_DATE, feeEndDate);
		mailto.setFromAddress(adminmail);
		mailto.setFromName(fromName);
		mailto.setMessage(desc);
		mailto.setSubject(subject);
		mailto.setToAddress(mailsId);
		CommonUtil.sendMail(mailto);
		 }
	  }
   } 
	
	private void SendMailsToStudent(String feeEndDate,ExamMidsemRepeat previousData, String templetNameMails, Properties prop, String subject) throws Exception{
		
		String adminmail=CMSConstants.MAIL_USERID;
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
		if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
		MailTO mailto = new MailTO();
		String desc ="";
		String subjectName="";
		Iterator<ExamMidsemRepeatDetails>  itrr=previousData.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidDetails.getIsApplied()!=null && examMidDetails.getIsApplied()){
			  if(subjectName==null || subjectName.isEmpty()){
				subjectName=examMidDetails.getSubject().getName();
			  }else{
			    subjectName=subjectName +","+examMidDetails.getSubject().getName();
			 }
		  }
		}
		String mailsId=previousData.getStudentId().getAdmAppln().getPersonalData().getEmail();
		TemplateHandler temphandle=TemplateHandler.getInstance();	
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,templetNameMails);
		
		if(list != null && !list.isEmpty()) {
		desc = list.get(0).getTemplateDescription();
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, previousData.getStudentId().getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_SUBJECT_NAME, subjectName);
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME, previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		desc=desc.replace(CMSConstants.TEMPLATE_CLASS, previousData.getClassId().getName());
		desc=desc.replace(CMSConstants.TEMPLATE_FEE_END_DATE, feeEndDate);
		mailto.setFromAddress(adminmail);
		mailto.setFromName(fromName);
		mailto.setMessage(desc);
		mailto.setSubject(subject);
		mailto.setToAddress(mailsId);
		CommonUtil.sendMail(mailto);
		 }
	  }
   } 
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamMidsemRepeatTO> setCoeApprovedData(ExamMidsemRepeatForm loginForm) throws Exception{
		List<ExamMidsemRepeat> previousData=transaction.getCoeDataForForm(loginForm);
		List<ExamMidsemRepeatTO> dataToForm= ExamMidsemRepeatHelper.getInstance().convertCoeDataToForm(previousData,loginForm);
		return dataToForm;
	}
	
	public List<ExamMidsemRepeatTO> setOfflineFeeData(ExamMidsemRepeatForm loginForm) throws Exception{
		List<ExamMidsemRepeat> previousData=transaction.getCoeDataForForm(loginForm);
		List<ExamMidsemRepeatTO> dataToForm= ExamMidsemRepeatHelper.getInstance().convertOfflineDataToForm(previousData,loginForm);
		return dataToForm;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public boolean setApproveDataToBos(ExamMidsemRepeatForm loginForm) throws Exception{
		boolean flag=false;
		ExamMidsemRepeat oneData= ExamMidsemRepeatHelper.getInstance().convertApprovedTOsToBo(loginForm);
		if(loginForm.getIsFeesPaid()!=null && loginForm.getIsFeesPaid().equalsIgnoreCase("false")){
			 flag=transaction.updateOneData(oneData);
		}
		ExamMidsemRepeatSetting settings=transaction.GetExamRepeatSettings(loginForm);
		ExamMidsemRepeat previousData=transaction.getApprovedDataForForm(loginForm);
		Properties prop = new Properties();
		InputStream in = SendingSmsJob.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		 try{
	        	prop.load(in);
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
			}
	    if(loginForm.getIsFeeExempted()!=null && loginForm.getIsFeeExempted().equalsIgnoreCase("true")){
	    	String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_SMS;
			String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_MAIL;
			String subject= prop.getProperty("knowledgepro.midSem.repeat.exam.Fee.Exempted");
			if(flag){
				SendMailsToStudentExemption(CommonUtil.ConvertStringToDateFormat(settings.getFeesEndDate().toString(), "yyyy-mm-dd",
				"dd/mm/yyyy"),previousData,templetNameMails,prop,subject);
				SendSmsToStudentExemption(previousData,templetNameSms,prop);
			}
	    }
	    else
	    {
			if(loginForm.getDataRejected()!=null && loginForm.getDataRejected().equalsIgnoreCase("true")){
				String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_REJECTED_SMS;
				String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_REJECTED_MAIL;
				String subject= prop.getProperty("knowledgepro.midSem.repeat.exam.reject.Subject");
				if(flag){
					SendMailsToStudentAdmin("Rejected",CommonUtil.ConvertStringToDateFormat(settings.getFeesEndDate().toString(), "yyyy-mm-dd",
					"dd/mm/yyyy"),previousData,templetNameMails,prop,subject);
					SendSmsToStudent(previousData,templetNameSms,prop);
				}
			}
			if(loginForm.getDataApproved()!=null && loginForm.getDataApproved().equalsIgnoreCase("true")){
		    	String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_APPROVE_SMS;
				String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_APPROVE_MAIL;
				String subject= prop.getProperty("knowledgepro.midSem.repeat.exam.approve.Subject");
				if(flag){
					SendMailsToStudentAdmin("Approved",CommonUtil.ConvertStringToDateFormat(settings.getFeesEndDate().toString(), "yyyy-mm-dd",
					"dd/mm/yyyy"),previousData,templetNameMails,prop,subject);
					SendSmsToStudent(previousData,templetNameSms,prop);
				}
			}
	    }
		return flag;
	}
	
	public boolean offlinePaymentSave(ExamMidsemRepeatForm loginForm) throws Exception{
		ExamMidsemRepeat oneData= ExamMidsemRepeatHelper.getInstance().convertOfflineTOsToBo(loginForm);
		boolean flag=transaction.updateOneData(oneData);
		//ExamMidsemRepeatSetting settings=transaction.GetExamRepeatSettings(loginForm);
	//	ExamMidsemRepeat previousData=transaction.getApprovedDataForForm(loginForm);
		return flag;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public ExamMidsemRepeatSetting checkForApplicationDate(LoginForm loginForm) throws Exception{
		ExamMidsemRepeatSetting isValid=transaction.getValidExamId(loginForm);
		return isValid;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public ExamMidsemRepeatSetting checkForFeePaymentDate (LoginForm loginForm) throws Exception{
		ExamMidsemRepeatSetting isValid=transaction.getValidExamIdForFeesPayment(loginForm);
		return isValid;
	}
	/**
	 * @param examId
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public String checkForRepeatApplicationExam (int id, Integer examId, LoginForm loginForm) throws Exception {
		String isPresent=transaction.checkForRepeatApplicationExam (id,examId,loginForm);
		return isPresent;
	}
	/**
	 * @param id
	 * @param examId
	 * @param loginForm
	 * @return
	 */
	public String checkForRepeatFeesPaymentExam(int id, Integer examId, LoginForm loginForm) throws Exception{
		String isPresent=transaction.checkForRepeatFeesPaymentExam(id,examId,loginForm);
		return isPresent;
	}
	
	
	public String checkForRepeatHallticket(int id, LoginForm loginForm) throws Exception{
		String isPresent=transaction.checkForRepeatHallticket(id,loginForm);
		return isPresent;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public ExamMidsemRepeatSetting checkForFeesDate(LoginForm loginForm) throws Exception {
		ExamMidsemRepeatSetting isValid=transaction.getValidExamIdForFees(loginForm);
		return isValid;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public boolean convertTOsToBoForFees(LoginForm loginForm) throws Exception{
		boolean flag=false;
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl .getInstance();
		int finId = cashCollectionTransaction.getCurrentFinancialYear();
		loginForm.setFinId(finId);
		OnlinePaymentReciepts onlinePaymentReciepts = new OnlinePaymentReciepts();
		setOnlinePaymentRecieptsBo(onlinePaymentReciepts,loginForm);
		int onlinePaymentId = transaction.saveOnlinePaymentBo(onlinePaymentReciepts);
		ExamMidsemRepeat previousData=transaction.getDataForForm(loginForm);
		if(previousData.getId()>0 && onlinePaymentId>0){
			ISingleFieldMasterTransaction iTransaction=SingleFieldMasterTransactionImpl.getInstance();
			OnlinePaymentReciepts paymentReciepts =(OnlinePaymentReciepts)iTransaction.getMasterEntryDataById(OnlinePaymentReciepts.class,onlinePaymentId);
			ExamMidsemRepeatHelper.getInstance().dedactAmountFromAccount(paymentReciepts,loginForm);
			if(!paymentReciepts.getIsPaymentFailed()){ 
				previousData.setIsFeePaid(true);
				previousData.setTotalAmount(loginForm.getMidSemTotalAmount());
				previousData.setIsPaymentFail(false);
				previousData.setIsApplyOnline(true);
				previousData.setOnlinePaymentReceipt(paymentReciepts);
				transaction.updateOneData(previousData);
				transaction.updateAndGenerateRecieptNoOnlinePaymentReciept(paymentReciepts);
				return true;
			}else{
				loginForm.setErrorMessage(paymentReciepts.getStatus());
				previousData.setIsFeePaid(false);
				previousData.setIsPaymentFail(true);
				previousData.setIsApplyOnline(true);
				previousData.setOnlinePaymentReceipt(paymentReciepts);
				transaction.updateOneData(previousData);
				return false;
			}
		}else{
			flag = false;
		}

		return flag;
	}
	/**
	 * @param onlinePaymentReciepts
	 * @param loginForm
	 * @throws Exception
	 */
	private void setOnlinePaymentRecieptsBo( OnlinePaymentReciepts onlinePaymentReciepts, LoginForm loginForm) throws Exception{
		Student student =new Student();
		student.setId(loginForm.getStudentId());
		onlinePaymentReciepts.setStudent(student);
		onlinePaymentReciepts.setTotalAmount(loginForm.getMidSemTotalAmount());
		onlinePaymentReciepts.setApplicationFor("Mid Semester Repeat Exam Application");
		PcFinancialYear pcFinancialYear=new PcFinancialYear();
		pcFinancialYear.setId(loginForm.getFinId());
		onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
		onlinePaymentReciepts.setIsPaymentFailed(true);
		onlinePaymentReciepts.setCreatedBy(loginForm.getUserId());
		onlinePaymentReciepts.setCreatedDate(new Date());
		onlinePaymentReciepts.setModifiedBy(loginForm.getUserId());
		onlinePaymentReciepts.setLastModifiedDate(new Date());
		onlinePaymentReciepts.setIsActive(true);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public ExamMidsemRepeatSetting ReminderMailForFeePayment() throws Exception {
	   ExamMidsemRepeatSetting isValid=transaction.ReminderMailForFeePayment();
	   return isValid;
	}
	/**
	 * @param boData
	 * @param templetNameSms
	 * @param prop
	 */
	public void SendSmsToStudent(ExamMidsemRepeatSetting checkForFeesDate, List<ExamMidsemRepeat> boData,String templetName, Properties prop) throws Exception{
		
		Iterator<ExamMidsemRepeat> itrBo=boData.iterator();
		
		 while (itrBo.hasNext()) {
			ExamMidsemRepeat examMidsemBo = (ExamMidsemRepeat) itrBo.next();
		
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String desc="";
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templetName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
	
			String mobileNo="";
			if(examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null && !examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
				if(examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
						|| examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
					mobileNo = "91";
				else
					mobileNo=examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
			}else{
				mobileNo="91";
			}
			if(examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
				mobileNo=mobileNo+examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
			}
			
		
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, examMidsemBo.getStudentId().getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_FEE_END_DATE, CommonUtil.ConvertStringToDateFormat(checkForFeesDate.getFeesEndDate().toString(), "yyyy-mm-dd",
		"dd/mm/yyyy"));
		
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			smsListBos.add(mob);
		   }
	     }
		 PropertyUtil.getInstance().saveSMSList(smsListBos);
	   }
	  }
	}
	/**
	 * @param boData
	 * @param templetNameMails
	 * @param prop
	 * @param subject
	 * @throws Exception
	 */
	public void SendMailsToStudent(ExamMidsemRepeatSetting checkForFeesDate,List<ExamMidsemRepeat> boData,String templetNameMails, Properties prop,String subject)  throws Exception {
	
		Iterator<ExamMidsemRepeat> itrBo=boData.iterator();
		
	 while (itrBo.hasNext()) {
		ExamMidsemRepeat examMidsemBo = (ExamMidsemRepeat) itrBo.next();
		
		String adminmail=CMSConstants.MAIL_USERID;
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
		if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
		MailTO mailto = new MailTO();
		String desc ="";
		String subjectName="";
		Iterator<ExamMidsemRepeatDetails>  itrr=examMidsemBo.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidDetails.getIsApplied()!=null && examMidDetails.getIsApplied()){
			  if(subjectName==null || subjectName.isEmpty()){
				subjectName=examMidDetails.getSubject().getName();
			  }else{
			    subjectName=subjectName +","+examMidDetails.getSubject().getName();
			 }
		  }
		}
		String mailsId=examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getEmail();
		TemplateHandler temphandle=TemplateHandler.getInstance();	
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,templetNameMails);
		
		if(list != null && !list.isEmpty()) {
		desc = list.get(0).getTemplateDescription();
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, examMidsemBo.getStudentId().getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_SUBJECT_NAME, subjectName);
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME, examMidsemBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		desc=desc.replace(CMSConstants.TEMPLATE_CLASS, examMidsemBo.getClassId().getName());
		desc=desc.replace(CMSConstants.TEMPLATE_FEE_END_DATE, CommonUtil.ConvertStringToDateFormat(checkForFeesDate.getFeesEndDate().toString(), "yyyy-mm-dd",
		"dd/mm/yyyy"));
		mailto.setFromAddress(adminmail);
		mailto.setFromName(fromName);
		mailto.setMessage(desc);
		mailto.setSubject(subject);
		mailto.setToAddress(mailsId);
		CommonUtil.sendMail(mailto);
		  }
	    }
     }
  }
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamMidsemRepeatTO> setDataToFormHallTicket(LoginForm loginForm) throws Exception{
		ExamMidsemRepeat previousData=transaction.getDataForForm(loginForm);
		List<ExamMidsemRepeatTO> dataToForm= ExamMidsemRepeatHelper.getInstance().setDataToFormHallTicket(previousData,loginForm);
		return dataToForm;
	}
	
	public boolean getStudentDataForExemption(RepeatMidSemAppForm repeatMidSemAppForm)throws Exception{
		boolean dataset=transaction.getStudentDataForExempt(repeatMidSemAppForm);
		return dataset;
	}
	
	public boolean getStudentAlreadyExempted(RepeatMidSemAppForm repeatMidSemAppForm)throws Exception{
		boolean flag=transaction.getStudentAlreadyExempted(repeatMidSemAppForm);
		return flag;
	}
	
	public boolean getStudentAlreadyExempted(LoginForm loginForm)throws Exception{
		boolean flag=transaction.getStudentAlreadyExempted(loginForm);
		return flag;
	}
	
	public boolean setDataToBosExemption(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception{
		ExamMidSemRepeatExemption oneData= ExamMidsemRepeatHelper.getInstance().convertTOsToBoExemption(repeatMidSemAppForm);
		boolean flag=transaction.updateRepeatMidExemption(oneData);
		return flag;
	}
	
	/*public boolean setDataToBosFeeExemption(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception{
		ExamMidSemRepeatExemption oneData= ExamMidsemRepeatHelper.getInstance().convertTOsToBoExemption(repeatMidSemAppForm);
		boolean flag=transaction.updateRepeatMidExemption(oneData);
		Properties prop = new Properties();
		InputStream in = SendingSmsJob.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		 try{
	        	prop.load(in);
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
			}
		String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_SMS;
		String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_MAIL;
		String subject= prop.getProperty("knowledgepro.midSem.repeat.exam.Fee.Exempted");
		if(flag){
			SendMailsToStudentExemption(oneData,templetNameMails,prop,subject);
			SendSmsToStudentExemption(oneData,templetNameSms,prop);
		}
		return flag;
	}*/
	
	private void SendMailsToStudentExemption(String feeEndDate, ExamMidsemRepeat previousData, String templetNameMails, Properties prop, String subject) throws Exception{
		
		String adminmail=CMSConstants.MAIL_USERID;
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
		if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
		MailTO mailto = new MailTO();
		String desc ="";
		String subjectName="";
		Iterator<ExamMidsemRepeatDetails>  itrr=previousData.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidDetails.getIsApplied()!=null && examMidDetails.getIsApplied()){
			  if(subjectName==null || subjectName.isEmpty()){
				subjectName=examMidDetails.getSubject().getName();
			  }else{
			    subjectName=subjectName +","+examMidDetails.getSubject().getName();
			 }
		  }
		}
		String mailsId=previousData.getStudentId().getAdmAppln().getPersonalData().getEmail();
		TemplateHandler temphandle=TemplateHandler.getInstance();	
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,templetNameMails);
		
		if(list != null && !list.isEmpty()) {
		desc = list.get(0).getTemplateDescription();
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, previousData.getStudentId().getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME, previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		desc=desc.replace(CMSConstants.TEMPLATE_SUBJECT_NAME, subjectName);
		desc=desc.replace(CMSConstants.TEMPLATE_CLASS, previousData.getClassId().getName());
		desc=desc.replace(CMSConstants.TEMPLATE_FEE_END_DATE, feeEndDate);
		mailto.setFromAddress(adminmail);
		mailto.setFromName(fromName);
		mailto.setMessage(desc);
		mailto.setSubject(subject);
		mailto.setToAddress(mailsId);
		CommonUtil.sendMail(mailto);
		 }
	  }
   }
private void SendSmsToStudentExemption(ExamMidsemRepeat previousData, String templetName, Properties prop) throws Exception{
		
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String desc="";
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templetName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
	
			String mobileNo="";
			if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null && !previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
				if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
						|| previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
					mobileNo = "91";
				else
					mobileNo=previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
			}else{
				mobileNo="91";
			}
			if(previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
				mobileNo=mobileNo+previousData.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
			}
			
		
		desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, previousData.getStudentId().getRegisterNo());
		
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			smsListBos.add(mob);
		   }
	    }
		 PropertyUtil.getInstance().saveSMSList(smsListBos);
	   }
	}
	
}
