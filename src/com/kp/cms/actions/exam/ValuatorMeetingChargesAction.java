package com.kp.cms.actions.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuatorMeetingChargesForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.exam.ValuatorMeetingChargesHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ValuatorMeetingChargeTo;

public class ValuatorMeetingChargesAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ValuatorMeetingChargesAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuatorMeetingCharge (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of initValuatorCharge method in ValuatorChargesAction class.");
		ValuatorMeetingChargesForm valuatorMeetingChargesForm = (ValuatorMeetingChargesForm) form;
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		setProgramTypeListToRequest(request);  //setting programType List to request to populate in combo
		valuatorMeetingChargesForm.reset();
		setRequestedDataToForm(valuatorMeetingChargesForm);
		log.debug("Leaving initValuatorCharge ");
	    return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
	}
	private void setProgramTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.getSession().setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward AddValuatorMeetingCharge(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of AddValuatorCharges method in ValuatorChargesAction class.");
		ValuatorMeetingChargesForm valuatorMeetingChargesForm = (ValuatorMeetingChargesForm) form;
		setUserId(request,valuatorMeetingChargesForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = valuatorMeetingChargesForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=ValuatorMeetingChargesHandler.getInstance().duplicateCheck(valuatorMeetingChargesForm,errors,session);
				if(!isDuplicate){
				isAdded = ValuatorMeetingChargesHandler.getInstance().addValuatorCharges(valuatorMeetingChargesForm,"Add");
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.exam.valuatorcharges.addsuccess"));
					saveMessages(request, messages);
					setRequestedDataToForm(valuatorMeetingChargesForm);
					valuatorMeetingChargesForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.exam.valuatorcharges.addfailure"));
					addErrors(request, errors);
					valuatorMeetingChargesForm.reset();
				}}
				else{
					addErrors(request, errors);
				}			
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				valuatorMeetingChargesForm.setErrorMessage(msg);
				valuatorMeetingChargesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(valuatorMeetingChargesForm);
			return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editValuatorMeetingCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValuatorMeetingChargesForm valuatorMeetingChargesForm = (ValuatorMeetingChargesForm) form;
		log.debug("Entering ValuatorCharges ");
		try {
			ValuatorMeetingChargesHandler.getInstance().editValuatorCharges(valuatorMeetingChargesForm);
			request.setAttribute("valuatorMeeting", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			valuatorMeetingChargesForm.setErrorMessage(msg);
			valuatorMeetingChargesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateValuatorMeetingCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatevaluatorCharges Action");
		ValuatorMeetingChargesForm valuatorMeetingChargesForm = (ValuatorMeetingChargesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = valuatorMeetingChargesForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				valuatorMeetingChargesForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				ValuatorMeetingChargesHandler.getInstance().editValuatorCharges(valuatorMeetingChargesForm);
				request.setAttribute("valuatorMeeting", "edit");
				return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
			}
			setUserId(request, valuatorMeetingChargesForm); // setting user id to update
			// boolean
			// isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);

			// if(isDuplicate)
			// errors.add(CMSConstants.ERROR, new
			// ActionError(CMSConstants.DUPLICATE_RECORDS));
			// else

			isUpdated =ValuatorMeetingChargesHandler.getInstance().updateValuatorCharges(valuatorMeetingChargesForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.exam.valuatorcharges.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				valuatorMeetingChargesForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.exam.valuatorcharges.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				valuatorMeetingChargesForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			valuatorMeetingChargesForm.setErrorMessage(msg);
			valuatorMeetingChargesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(valuatorMeetingChargesForm);
			request.setAttribute("valuatorMeeting", "edit");
			return mapping.findForward(CMSConstants.EMP_PAYSCALE);
		}
		setRequestedDataToForm(valuatorMeetingChargesForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteValuatorMeetingCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		ValuatorMeetingChargesForm valuatorMeetingChargesForm = (ValuatorMeetingChargesForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted =ValuatorMeetingChargesHandler.getInstance().deleteValuatorCharges(valuatorMeetingChargesForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.exam.ValuatorCharges.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.exam.ValuatorCharges.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			valuatorMeetingChargesForm.reset();
			setRequestedDataToForm(valuatorMeetingChargesForm);
		} catch (Exception e) {
			log.error("error submit valuator Meeting Charges...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				valuatorMeetingChargesForm.setErrorMessage(msg);
				valuatorMeetingChargesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				valuatorMeetingChargesForm.setErrorMessage(msg);
				valuatorMeetingChargesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete valuatorMeeting ");
		return mapping.findForward(CMSConstants.EXAM_VALUATORMEETING_CHARGE);
	}
	/**
	 * @param valuatorChargesForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm(ValuatorMeetingChargesForm valuatorMeetingChargesForm) throws Exception{
		List<ValuatorMeetingChargeTo> valuatorList=ValuatorMeetingChargesHandler.getInstance().getValuatorChargeList();
		valuatorMeetingChargesForm.setValuatorMeetingChargeList(valuatorList);
		}

}
