package com.kp.cms.actions.admin;

import java.util.List;

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
import com.kp.cms.forms.admin.ConvocationRegistrationStatusForm;
import com.kp.cms.handlers.admin.ConvocationRegistrationStatusHandler;

public class ConvocationRegistrationStatusAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ConvocationRegistrationStatusAction.class);
	public ActionForward initConvocationRegistrationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initConvocationRegistrationStatus input");
		ConvocationRegistrationStatusForm Form = (ConvocationRegistrationStatusForm) form;// Type casting the Action form to Required Form
		Form.resetFields();
		log.info("Exit initConvocationRegistrationStatus input");
		return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
	}
	
	
	public ActionForward getResults(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		ConvocationRegistrationStatusForm Form = (ConvocationRegistrationStatusForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = Form.validate(mapping, request);
		validateStudentRegOrRollNo(Form,errors);
		if (errors.isEmpty()) {
			try {
				boolean isValidStudent=ConvocationRegistrationStatusHandler.getInstance().checkValidStudentRegNo(Form);
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
				}
				boolean isStudentAppliedForConvocation=ConvocationRegistrationStatusHandler.getInstance().checkStudentAppliedForConvocation(Form);
				if(!isStudentAppliedForConvocation){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgePro.admin.notapplied.convocation"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
				}
				ConvocationRegistrationStatusHandler.getInstance().checkStudentDetails(Form);
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationOrRetotallingMarksEntryAction - getCandidateRecord errors not empty ");
			return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
		}
		Form.setFlag(true);
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
	}
	private void validateStudentRegOrRollNo(ConvocationRegistrationStatusForm form ,ActionErrors errors) throws Exception{
		if(form.getRegisterNo()==null || form.getRegisterNo().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.registerNo.requred"));
		}
	}
	
	public ActionForward saveDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		ConvocationRegistrationStatusForm Form = (ConvocationRegistrationStatusForm) form;// Type casting the Action form to Required Form
		setUserId(request,Form);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		validatePassNumbers(Form,errors);
		if (errors.isEmpty()) {
			try {
				
				boolean isAdded = ConvocationRegistrationStatusHandler.getInstance().updateData(Form);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgePro.admission.record.edited"));
					saveMessages(request, messages);
					} else{
						return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
					}
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
		}
		Form.resetFields();
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.CONVOCATION_REGISTRATION_STATUS_ACTION);
	}
	private void validatePassNumbers(ConvocationRegistrationStatusForm form ,ActionErrors errors) throws Exception{
		if(form.getGuestPassCount()!=null && form.getGuestPassCount().equalsIgnoreCase("1")){
			if(form.getPassNo1()==null || form.getPassNo1().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.passNo1"));
			}
		} else if(form.getGuestPassCount()!=null && form.getGuestPassCount().equalsIgnoreCase("2")){
			if((form.getPassNo1()==null || form.getPassNo1().isEmpty()) && (form.getPassNo2()==null || form.getPassNo2().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.passNo1"));
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.passNo2"));
			}else if(form.getPassNo1()==null || form.getPassNo1().isEmpty() ){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.passNo1"));
			}else if(form.getPassNo2()==null || form.getPassNo2().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.passNo2"));
			}
		}
	}

}
