package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.bo.exam.ExamMidsemExemptionDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.CertificateRequestOnlineHandler;
import com.kp.cms.handlers.exam.ExamMidsemRepeatHandler;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;
import com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateRequestOnlineImpl;
import com.kp.cms.transactionsimpl.exam.ExamMidsemRepeatTransactionImpl;

public class MidSemRepeatExamApplicationAction extends BaseDispatchAction{
	
	private static final String MESSAGE_KEY = "messages";
	
	private static final Logger log = Logger.getLogger(MidSemRepeatExamApplicationAction.class);
	CertificateRequestOnlineHandler handler=CertificateRequestOnlineHandler.getInstance();
	 ICertificateRequestOnlineTransaction txn=new CertificateRequestOnlineImpl();
	 IExamMidsemRepeatTransaction transaction = ExamMidsemRepeatTransactionImpl.getInstance();
	 	 /**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward initRepeatExamApplication (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		{
			LoginForm loginForm=(LoginForm) form;
			setUserId(request, loginForm);
			loginForm.setMidSemPrint(false);
			try {
				List<ExamMidsemExemption> exempt=transaction.getStudentExemptedOrNot(loginForm);
				if(exempt!=null && !exempt.isEmpty())
				{
					loginForm.setFeesExemption("true");
				}
				else
				{
					loginForm.setFeesExemption("false");
				}
				Object[] obj=transaction.getNoOfAttempts(loginForm.getCourseId(), loginForm.getStudentId());
				if(obj!=null){
					if(obj[0].toString()!=null && obj[1].toString()!=null){
						if(Integer.parseInt(obj[0].toString())<=Integer.parseInt(obj[1].toString())){
							loginForm.setAttemtsCompleted("true");
							loginForm.setAttemptsCount(obj[1].toString());
							boolean flag =transaction.getStudentAlreadyExempted(loginForm);
							if(flag){
								List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
								loginForm.setMidSemRepeatList(absentSubject);
								loginForm.setAttemtsCompleted("false");
							}
						}else
						{
							loginForm.setAttemtsCompleted("false");
							List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
							loginForm.setMidSemRepeatList(absentSubject);
							loginForm.setAttemptsCount(obj[1].toString());
							if(Integer.parseInt(obj[1].toString())==1){
								loginForm.setMidSemCountWords("Second");	
							}
							if(Integer.parseInt(obj[1].toString())==2){
								 loginForm.setMidSemCountWords("Third");	
							}
							
						}
						if((Integer.parseInt(obj[0].toString())-Integer.parseInt(obj[1].toString()))>0)
							loginForm.setMidSemAttemptsLeft(String.valueOf(Integer.parseInt(obj[0].toString())-Integer.parseInt(obj[1].toString())));
						else
							loginForm.setMidSemAttemptsLeft("No");
					}
				}else
				{
					loginForm.setAttemtsCompleted("false");
					List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
					loginForm.setMidSemRepeatList(absentSubject);
					int count=transaction.getAttemptsByCourseId(loginForm.getCourseId());
					loginForm.setMidSemCountWords("First");
					if((count-1)>0)
					loginForm.setMidSemAttemptsLeft(String.valueOf(count-1));
					else
						loginForm.setMidSemAttemptsLeft("No");
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
		}

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public ActionForward SavePrintApplication (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		{
			LoginForm loginForm=(LoginForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			setUserId(request, loginForm);
			boolean isApplied=false;
			loginForm.setMidSemPrint(true);
			if(loginForm.getMidSemRepeatReason()==null || loginForm.getMidSemRepeatReason().isEmpty()){
				
				if (errors.get(CMSConstants.MID_SEM_REASON_REQUIRED) != null&& !errors.get(CMSConstants.MID_SEM_REASON_REQUIRED).hasNext()) {
					errors.add(CMSConstants.MID_SEM_REASON_REQUIRED,new ActionError(CMSConstants.MID_SEM_REASON_REQUIRED));
				}
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				loginForm.setMidSemPrint(false);
				return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
				
			}
			else{
				try {
					isApplied=ExamMidsemRepeatHandler.getInstance().setDataToBos(loginForm);
					List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
					loginForm.setMidSemRepeatList(absentSubject);
					if(isApplied && absentSubject!=null && !absentSubject.isEmpty()){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.mid.sem.success"));
					saveMessages(request, messages);	
					return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
					}
				}catch(DataNotFoundException e) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.noSubjects.selected"));
					loginForm.setMidSemPrint(false);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
		}

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward RepeatExamApplicationDownload (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LoginForm loginForm=(LoginForm) form;
			loginForm.setMidSemPrint(true);
			List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
			loginForm.setMidSemRepeatList(absentSubject);
			return mapping.findForward(CMSConstants.INIT_REPER_EXAM_APPLICATION);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward RepeatExamApplicationPrint (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LoginForm loginForm=(LoginForm) form;
			loginForm.setMidSemRepeatList(null);
			List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToFormPrint(loginForm);
			loginForm.setMidSemRepeatList(absentSubject);
			return mapping.findForward(CMSConstants.REPER_EXAM_APPLICATION_PRINT);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward initRepeatExamFeePayment (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		{
			LoginForm loginForm=(LoginForm) form;
			setUserId(request, loginForm);
			loginForm.setMidSemPrint(false);
			try {
				List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToForm(loginForm);
				loginForm.setMidSemRepeatList(absentSubject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapping.findForward(CMSConstants.INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward repeatExamFeePaymentProcess (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		{
			LoginForm loginForm=(LoginForm) form;
			ActionErrors errors = loginForm.validate(mapping, request);
			setUserId(request,loginForm);
			if (errors.isEmpty()) {
				try {
					loginForm.setDob(null);
					loginForm.setSmartCardNo(null);
					loginForm.setValidThruMonth(null);
					loginForm.setValidThruYear(null);
				}catch(Exception ex){
					String msg = super.handleApplicationException(ex);
					loginForm.setErrorMessage(msg);
					loginForm.setErrorStack(ex.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			return mapping.findForward(CMSConstants.MIDSEM_REPEAT_EXAM_FEEPAYMENT_PROCESS);
		  }
     /**
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
 		LoginForm loginForm=(LoginForm) form;
 		ActionErrors errors = new ActionErrors();
 		setUserId(request,loginForm);//setting the userId to Form
 		try {
 			loginForm.setMidSemPrint(false);
 			boolean isValidSmartCard=handler.verifySmartCard(loginForm.getSmartCardNo(),loginForm.getStudentId());
 			if(!isValidSmartCard){
 				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
 			}
 			if(loginForm.getDob()!=null){
 				if(!loginForm.getDob().equalsIgnoreCase(loginForm.getOriginalDob()))
 					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
 			}
 			
 			if(!errors.isEmpty()){
 				saveErrors(request, errors);
 				return mapping.findForward(CMSConstants.MIDSEM_REPEAT_EXAM_FEEPAYMENT_PROCESS);
 			}
 			
 		}catch (ReActivateException e) {
 			errors.add("error", new ActionError("knowledgepro.exam.midSem.repeat.name"));
 			saveErrors(request, errors);
 			return mapping.findForward(CMSConstants.INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT);
 		} catch (Exception e) {
 			log.error("Error in getCertificateCourses"+e.getMessage());
 			String msg = super.handleApplicationException(e);
 			loginForm.setErrorMessage(msg);
 			loginForm.setErrorStack(e.getMessage());
 			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
 		}
 		
 		return mapping.findForward(CMSConstants.MIDSEM_REPEAT_EXAM_FEEPAYMENT);
 	}
 	/**
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	public ActionForward addRepeatExamFeesPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 		LoginForm loginForm=(LoginForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = loginForm.validate(mapping, request);
		setUserId(request,loginForm);
		if (errors.isEmpty()) {
			try {
				loginForm.setMidSemPrint(false);
				String msg="";
				boolean isSaved=ExamMidsemRepeatHandler.getInstance().convertTOsToBoForFees(loginForm);
				msg=loginForm.getMsg();
				if(isSaved){
					loginForm.setMidSemPrint(true);
					loginForm.setIsFeesPaid("true");
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.midsem.repeat.success.online"));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT);
				}else{
					if(msg==null || msg.isEmpty()){
						msg="Payment Failed";
						}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Mid Semester Repeat Exam Request was not successfull, Reason:"+msg));
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
					saveErrors(request, errors);
					
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit CertificateRequestOnlineAction - addCertificateApplicationForStudentLogin errors not empty ");
			return mapping.findForward(CMSConstants.INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT);
		}
		return mapping.findForward(CMSConstants.INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward RepeatExamHallTicketPrint (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm=(LoginForm) form;
		loginForm.setMidSemRepeatList(null);
		List<ExamMidsemRepeatTO> absentSubject=ExamMidsemRepeatHandler.getInstance().setDataToFormHallTicket(loginForm);
		loginForm.setMidSemRepeatList(absentSubject);
		return mapping.findForward(CMSConstants.REPER_EXAM_HALL_TICKET_PRINT);
	}
}
	
	
