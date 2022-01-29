package com.kp.cms.actions.fee;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm;
import com.kp.cms.handlers.fee.CancelledAdmissionRepaymentHandler;
import com.kp.cms.to.admission.AdmApplnTO;

public class CancelledAdmissionRepaymentAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(CancelledAdmissionRepaymentAction.class);
	
	/**
	 * Method to redirect to the init jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCancelledAdmissionRepayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCancelledAdmissionRepayment-CancelledAdmissionRepaymentAction Batch input");
		CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm = (CancelledAdmissionRepaymentForm) form;
		cancelledAdmissionRepaymentForm.resetFields();
		log.info("Exit initCancelledAdmissionRepayment-CancelledAdmissionRepaymentAction Batch input");
		
		return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);
	}
	
	
	/**
	 * method gets the student canceled details if any and sets to the form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentCanceledDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered getStudentCancelledDetails-CancelledAdmissionRepaymentAction Batch input");
		CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm = (CancelledAdmissionRepaymentForm) form;
		ActionErrors errors= new ActionErrors();
		validateAppRegNo(errors,cancelledAdmissionRepaymentForm);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			cancelledAdmissionRepaymentForm.resetFields();
			log.info("Exit getStudentCancelledDetails - CancelledAdmissionRepaymentAction errors not empty ");
			return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);
		}
		else {
			try{
				AdmApplnTO admApplnTo=CancelledAdmissionRepaymentHandler.getInstance().getStudentCanceledDetails(cancelledAdmissionRepaymentForm);
				if(admApplnTo!=null){
					cancelledAdmissionRepaymentForm.setAdmApplnTo(admApplnTo);
				}
				else if(admApplnTo==null){
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					cancelledAdmissionRepaymentForm.resetFields();
				}
			}
			catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				cancelledAdmissionRepaymentForm.setErrorMessage(msg);
				cancelledAdmissionRepaymentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Exit getStudentCanceledDetails-CancelledAdmissionRepaymentAction Batch input");
		return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);
	}


	/**
	 * validation of RegisterNo and Appln No
	 * @param errors
	 * @throws Exception
	 */
	private void validateAppRegNo(ActionErrors errors,CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception{
		if(cancelledAdmissionRepaymentForm.getApplnNo()!=null && !cancelledAdmissionRepaymentForm.getApplnNo().isEmpty() 
				&& cancelledAdmissionRepaymentForm.getRegisterNo()!=null && !cancelledAdmissionRepaymentForm.getRegisterNo().isEmpty()){
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.studentClassSubjectDetails.bothRegNoAndApplnNoNotRequired"));
		}
		if((cancelledAdmissionRepaymentForm.getApplnNo()==null || cancelledAdmissionRepaymentForm.getApplnNo().trim().isEmpty()) 
				&& (cancelledAdmissionRepaymentForm.getRegisterNo()==null || cancelledAdmissionRepaymentForm.getRegisterNo().trim().isEmpty())){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_APPORREGI_REQUIRED));
		}
	}
	/**
	 * method saves the student canceled details if any 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveRepaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");
		CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm = (CancelledAdmissionRepaymentForm) form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, cancelledAdmissionRepaymentForm);
		ActionErrors errors = cancelledAdmissionRepaymentForm.validate(mapping, request);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			log.info(" errors not empty - Exit saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");
			return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);
		}
		else{
			try{
				boolean isSaved=false;
				isSaved=CancelledAdmissionRepaymentHandler.getInstance().saveRepaymentDetails(cancelledAdmissionRepaymentForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.fee.cancelledAdmissionRepayment.success"));
					saveMessages(request, messages);
					log.info("Exit saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");				}
				else if(!isSaved){
					errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.fee.cancelledAdmissionRepayment.failure"));
					saveErrors(request, errors);
					log.info("Exit saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");
					return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);	
				}
			}
			catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				cancelledAdmissionRepaymentForm.setErrorMessage(msg);
				cancelledAdmissionRepaymentForm.setErrorStack(exception.getMessage());
				log.info("Exit saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		cancelledAdmissionRepaymentForm.resetFields();
		log.info("Exit saveRepaymentDetails-CancelledAdmissionRepaymentAction Batch input");
		return mapping.findForward(CMSConstants.CANCELLED_ADMISSION_REPAYMENT_INIT);
	}

}
