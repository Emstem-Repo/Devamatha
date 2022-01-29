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
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.exam.ValuatorChargesHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ValuatorChargeTo;

public class ValuatorChargesAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ValuatorChargesAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuatorCharge (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of initValuatorCharge method in ValuatorChargesAction class.");
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		setProgramTypeListToRequest(request);  //setting programType List to request to populate in combo
		valuatorChargesForm.reset();
		setRequestedDataToForm(valuatorChargesForm);
		log.debug("Leaving initValuatorCharge ");
	    return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
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
	public ActionForward AddValuatorCharges(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of AddValuatorCharges method in ValuatorChargesAction class.");
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		setUserId(request,valuatorChargesForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = valuatorChargesForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=ValuatorChargesHandler.getInstance().duplicateCheck(valuatorChargesForm,errors,session);
				if(!isDuplicate){
				isAdded = ValuatorChargesHandler.getInstance().addValuatorCharges(valuatorChargesForm,"Add");
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.exam.valuatorcharges.addsuccess"));
					saveMessages(request, messages);
					setRequestedDataToForm(valuatorChargesForm);
					valuatorChargesForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.exam.valuatorcharges.addfailure"));
					addErrors(request, errors);
					valuatorChargesForm.reset();
				}}
				else{
					addErrors(request, errors);
				}			
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				valuatorChargesForm.setErrorMessage(msg);
				valuatorChargesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(valuatorChargesForm);
			return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editValuatorCharges(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		valuatorChargesForm.reset();
		log.debug("Entering ValuatorCharges ");
		try {
			ValuatorChargesHandler.getInstance().editValuatorCharges(valuatorChargesForm);
			request.setAttribute("valuatorCharges", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			valuatorChargesForm.setErrorMessage(msg);
			valuatorChargesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatevaluatorCharges(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatevaluatorCharges Action");
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = valuatorChargesForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				valuatorChargesForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				ValuatorChargesHandler.getInstance().editValuatorCharges(valuatorChargesForm);
				request.setAttribute("valuatorCharges", "edit");
				return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
			}
			setUserId(request, valuatorChargesForm); // setting user id to update
			// boolean
			// isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);

			// if(isDuplicate)
			// errors.add(CMSConstants.ERROR, new
			// ActionError(CMSConstants.DUPLICATE_RECORDS));
			// else
			boolean isDuplicate=ValuatorChargesHandler.getInstance().duplicateCheck(valuatorChargesForm,errors,session);
			if(!isDuplicate){
			isUpdated = ValuatorChargesHandler.getInstance().updateValuatorCharges(valuatorChargesForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.exam.valuatorcharges.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				valuatorChargesForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.exam.valuatorcharges.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				valuatorChargesForm.reset();
			}}
			else{
				request.setAttribute("valuatorCharges", "edit");
				addErrors(request, errors);
				//payScaleForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			valuatorChargesForm.setErrorMessage(msg);
			valuatorChargesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(valuatorChargesForm);
			request.setAttribute("valuatorCharges", "edit");
			return mapping.findForward(CMSConstants.EMP_PAYSCALE);
		}
		setRequestedDataToForm(valuatorChargesForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteValuatorCharges(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = ValuatorChargesHandler.getInstance().deleteValuatorCharges(valuatorChargesForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.exam.ValuatorCharges.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.exam.ValuatorCharges.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			valuatorChargesForm.reset();
			setRequestedDataToForm(valuatorChargesForm);
		} catch (Exception e) {
			log.error("error submit valuatorCharges...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				valuatorChargesForm.setErrorMessage(msg);
				valuatorChargesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				valuatorChargesForm.setErrorMessage(msg);
				valuatorChargesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete valuatorCharges ");
		return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateValuatorCharges(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		ValuatorChargesForm valuatorChargesForm = (ValuatorChargesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, valuatorChargesForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = valuatorChargesForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			valuatorChargesForm.setId(Integer.parseInt(duplicateId));
			isReactivate =  ValuatorChargesHandler.getInstance().reactivateValuatorCharges(valuatorChargesForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuatorCharges.activate"));
				setRequestedDataToForm(valuatorChargesForm);
				valuatorChargesForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(valuatorChargesForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.valuatorCharges.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
			String msg = super.handleApplicationException(e);
			valuatorChargesForm.setErrorMessage(msg);
			valuatorChargesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
		return mapping.findForward(CMSConstants.EXAM_VALUATOR_CHARGE); 
	}

	/**
	 * @param valuatorChargesForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm(ValuatorChargesForm valuatorChargesForm) throws Exception{
		List<ValuatorChargeTo> valuatorList=ValuatorChargesHandler.getInstance().getValuatorChargeList();
		valuatorChargesForm.setValuatorChargeList(valuatorList);
		}

}
