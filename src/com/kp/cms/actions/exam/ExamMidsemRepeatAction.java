package com.kp.cms.actions.exam;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.exam.ExamMidsemRepeatForm;
import com.kp.cms.handlers.exam.ExamMidsemRepeatHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;
import com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMidsemRepeatTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.RepeatMidSemReminderSmsMail;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemRepeatAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ExamMidsemRepeatAction.class);
	IExamMidsemRepeatTransaction transaction = ExamMidsemRepeatTransactionImpl.getInstance();
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMidsemRepeat(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initExamMidsemExemption method in ExamMidsemExemptionAction class.");
		ExamMidsemRepeatForm examMidsemRepratForm=(ExamMidsemRepeatForm) form;
		examMidsemRepratForm.clearAll();
		setRequiredDatatoForm(examMidsemRepratForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		log.debug("Leaving initExamMidsemExemption");
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_REPEAT);
	}
	/**
	 * @param examMidsemRepratForm
	 * @param year
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamMidsemRepeatForm examMidsemRepratForm, int year,HttpServletRequest request) throws Exception{
		
		Map<Integer, String> midexamNameMap = ExamMidsemRepeatHandler.getInstance().getExamMidsemNameList(year);
		examMidsemRepratForm.setMidsemExamList(midexamNameMap);
		
		Map<Integer, String> examNameMap = ExamMidsemRepeatHandler.getInstance().getExamNameList(year);
		examMidsemRepratForm.setExamList(examNameMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchRunProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExamMidsemRepeatForm exemptionForm = (ExamMidsemRepeatForm) form;
		 ActionErrors errors = exemptionForm.validate(mapping, request);
		 ActionMessages messages = new ActionMessages();
		 boolean flag=false;
		setUserId(request,exemptionForm);
		setExamName(exemptionForm);
		try {
			if(errors.isEmpty()){
				flag= ExamMidsemRepeatHandler.getInstance().getRunSetDataToTable(exemptionForm);
				if(flag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.midsem.repeat.success"));
					saveMessages(request, messages);
				}
				exemptionForm.clearAll();
				setRequiredDatatoForm(exemptionForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
				return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_REPEAT);
			}
		}catch(DataNotFoundException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_REPEAT);
		}catch(SubjectGroupNotDefinedException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.exam.midsem.Repeat.process.over",exemptionForm.getExamName()));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_REPEAT);
	 	}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				exemptionForm.setErrorMessage(msg);
				exemptionForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		setRequiredDatatoForm(exemptionForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_REPEAT);
	}
	/**
	 * @param exemptionForm
	 * @param request
	 * @throws Exception
	 */
	private void setExamName(ExamMidsemRepeatForm exemptionForm) throws Exception 
	{
		ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) ExamMidsemRepeatHandler.getInstance().getExamName(exemptionForm);
		exemptionForm.setExamName(examDefinitionBO.getName());
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCoeApproveApplication (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		loginForm.clearAll();
		setUserId(request, loginForm);
		setRequiredDatatoForm(loginForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
    	return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward coeApproveApplication (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		ActionErrors errors = loginForm.validate(mapping, request);
		setUserId(request, loginForm);
		try {
			if(errors.isEmpty()){
			Object[] obj=transaction.getNoOfAttemptsCOE(loginForm.getMidSemRepeatRegNo());
				if(obj!=null){
					if(obj[0].toString()!=null && obj[1].toString()!=null){
							loginForm.setAttemptsCompletedCount(obj[1].toString());
							loginForm.setAttemptsAllowed(obj[0].toString());
					}
					else
					{
						int count=transaction.getAttemptsByRegisterNo(loginForm.getMidSemRepeatRegNo());
						loginForm.setAttemptsCompletedCount("0");
						loginForm.setAttemptsAllowed(String.valueOf(count));
					}
				}else
				{
					int count=transaction.getAttemptsByRegisterNo(loginForm.getMidSemRepeatRegNo());
					loginForm.setAttemptsCompletedCount("0");
					loginForm.setAttemptsAllowed(String.valueOf(count));
				}
			List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setCoeApprovedData(loginForm);
			if(absentSubject==null || absentSubject.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
			}
			loginForm.setMidSemRepeatList(absentSubject);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward SavePrintApplication (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		setUserId(request, loginForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isApplied=false;
		try {
			if(loginForm.getErrorsFeePaid()!=null && loginForm.getErrorsFeePaid().equalsIgnoreCase("false")){
				isApplied=ExamMidsemRepeatHandler.getInstance().setApproveDataToBos(loginForm);
			}else
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.mid.sem.repeat.fee.paid.error"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
			}
			if(isApplied){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.update.approved.success"));
				saveMessages(request, messages);
				loginForm.clearAll();
			}				
			else
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.update.approved.fail"));
				saveMessages(request, messages);
			}
		}catch(DataNotFoundException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.midsem.Repeat.not.Approved"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setRequiredDatatoForm(loginForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.COE_APPROVE_APPLICATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward initRepeatOfflinePayment (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		loginForm.clearAll();
		setUserId(request, loginForm);
		setRequiredDatatoForm(loginForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
    	return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
	}
	
	public ActionForward RepeatOfflinePayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request, loginForm);
		validateData(loginForm,errors);
		try {
			if(errors.isEmpty()){
			List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setOfflineFeeData(loginForm);
			if(absentSubject==null || absentSubject.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
			}
			loginForm.setMidSemRepeatList(absentSubject);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
	}
	
	public ActionForward SaveOfflinePayment (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
		setUserId(request, loginForm);
		
		ActionErrors errors = loginForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isApplied=false;
		try {
			isApplied=ExamMidsemRepeatHandler.getInstance().offlinePaymentSave(loginForm);
			if(isApplied){
				loginForm.clearAll();
				loginForm.setMidSemRepeatList(null);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.midsem.success"));
				saveMessages(request, messages);
				
			}else{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.midsem.failure"));
				saveMessages(request, messages);
			}
		}catch(DataNotFoundException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.midsem.failure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setRequiredDatatoForm(loginForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.REPEAT_OFFLINE_PAYMENT);
	}
	
	 public void validateData(ExamMidsemRepeatForm midSemForm, ActionErrors errors)
	 {
		 
				if(midSemForm.getMidSemRepeatRegNo()==null || midSemForm.getMidSemRepeatRegNo().isEmpty())
				{
							
					if(errors.get(CMSConstants.REGISTER_NO_REQUIRED)!=null && !errors.get(CMSConstants.REGISTER_NO_REQUIRED).hasNext()){									
						errors.add(CMSConstants.REGISTER_NO_REQUIRED,new ActionError(CMSConstants.REGISTER_NO_REQUIRED));
					}	
				}
				if(midSemForm.getExamId().isEmpty()){
					
					if(errors.get(CMSConstants.MIDSEMESTER_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.MIDSEMESTER_EXAM_REQUIRED).hasNext()){									
						errors.add(CMSConstants.MIDSEMESTER_EXAM_REQUIRED,new ActionError(CMSConstants.MIDSEMESTER_EXAM_REQUIRED));
					}	
				}
	
	 	}
	 
	/* public ActionForward initSMSReminder (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
			setUserId(request, loginForm);
			return mapping.findForward(CMSConstants.SEND_SMS_REMINDER_FEE_PAYMENT);
		}*/
		
		public ActionForward initSMSReminder (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			ExamMidsemRepeatForm loginForm = (ExamMidsemRepeatForm) form;
			setUserId(request, loginForm);
			ActionMessages messages = new ActionMessages();
			try {
				Properties prop = new Properties();
				InputStream in = RepeatMidSemReminderSmsMail.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				InputStream sin = AttendanceEntryHelper.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
		        
		        	prop.load(in);
		        	prop.load(sin);
		       
				ExamMidsemRepeatSetting checkForFeesDate=ExamMidsemRepeatHandler.getInstance().ReminderMailForFeePayment();
				if(checkForFeesDate!=null) {
				List<ExamMidsemRepeat> BoData = ExamMidsemRepeatTransactionImpl.getInstance().repeatMidSemExamReminder(checkForFeesDate.getMidSemExamId().getId());
				
				if(BoData != null){
					String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_SMS;
					String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_MAIL;
					ExamMidsemRepeatHandler.getInstance().SendSmsToStudent(checkForFeesDate,BoData,templetNameSms,prop);
					ExamMidsemRepeatHandler.getInstance().SendMailsToStudent(checkForFeesDate,BoData,templetNameMails,prop,templetNameMails);
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.reminder.smsmail.success"));
					saveMessages(request, messages);
				}
			}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapping.findForward(CMSConstants.SEND_SMS_REMINDER_FEE_PAYMENT);
		}
}