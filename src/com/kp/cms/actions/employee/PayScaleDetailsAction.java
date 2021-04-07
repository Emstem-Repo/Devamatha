package com.kp.cms.actions.employee;

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
import com.kp.cms.exceptions.ReActivateException;

import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;

import com.kp.cms.to.employee.PayScaleTO;

public class PayScaleDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PayScaleDetailsAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPayScaleDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		payScaleForm.reset();
		setRequestedDataToForm(payScaleForm);
		return mapping.findForward(CMSConstants.EMP_PAYSCALE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPayScale(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		setUserId(request, payScaleForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = payScaleForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=PayScaleDetailsHandler.getInstance().duplicateCheck(payScaleForm,errors,session);
				if(!isDuplicate){
				isAdded = PayScaleDetailsHandler.getInstance()
						.addPayScale(payScaleForm);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.payScale.addsuccess"));
					saveMessages(request, messages);
					payScaleForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.payScale.addfailure"));
					addErrors(request, errors);
					payScaleForm.reset();
				}}
				else{
					addErrors(request, errors);
				}
				}/*else{
					errors.add("error", new ActionError("knowledgepro.employee.PayScale.duplicate"));
					addErrors(request, errors);
					//payScaleForm.reset();
				}*/
			
			/*catch (ReActivateException e2) {
				errors.add("error", new ActionError(CMSConstants.EMPLOYEE_LEAVEALLOTMENT_REACTIVATE));
				saveErrors(request, errors);
				session.setAttribute("ReactivateId", payScaleForm.getId());
				return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
			} */
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				payScaleForm.setErrorMessage(msg);
				payScaleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(payScaleForm);
			return mapping.findForward(CMSConstants.EMP_PAYSCALE);
		}
		saveErrors(request, errors);
		setRequestedDataToForm(payScaleForm);
		return mapping.findForward(CMSConstants.EMP_PAYSCALE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPayScale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		log.debug("Entering EditPayScale ");
		try {
			// employeeResumeForm.reset(mapping, request);
			// String formName = mapping.getName();
			// request.getSession().setAttribute(CMSConstants.FORMNAME,
			// formName);
			PayScaleDetailsHandler.getInstance().editPayScale(
					payScaleForm);
			request.setAttribute("payScaleOperation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving EditPayScale ");
		} catch (Exception e) {
			log.error("error in editing payscale...", e);
			String msg = super.handleApplicationException(e);
			payScaleForm.setErrorMessage(msg);
			payScaleForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMP_PAYSCALE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePayScale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePayScale Action");
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = payScaleForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				payScaleForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				PayScaleDetailsHandler.getInstance().editPayScale(
						payScaleForm);
				request.setAttribute("payScaleOperation", "edit");
				return mapping.findForward(CMSConstants.EMP_PAYSCALE);
			}
			setUserId(request, payScaleForm); // setting user id to update
			// boolean
			// isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);

			// if(isDuplicate)
			// errors.add(CMSConstants.ERROR, new
			// ActionError(CMSConstants.DUPLICATE_RECORDS));
			// else
			boolean isDuplicate=PayScaleDetailsHandler.getInstance().duplicateCheck(payScaleForm,errors,session);
			if(!isDuplicate){
			isUpdated = PayScaleDetailsHandler.getInstance()
					.updatePayScale(payScaleForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.payScale.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				payScaleForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.payScale.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				payScaleForm.reset();
			}}else{
				request.setAttribute("payScaleOperation", "edit");
				errors.add("error", new ActionError("knowledgepro.employee.PayScale.duplicate"));
				addErrors(request, errors);
				//payScaleForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit paysacle", e);
			String msg = super.handleApplicationException(e);
			payScaleForm.setErrorMessage(msg);
			payScaleForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(payScaleForm);
			request.setAttribute("payScaleOperation", "edit");
			return mapping.findForward(CMSConstants.EMP_PAYSCALE);
		}
		setRequestedDataToForm(payScaleForm);
		log.debug("Exit: action class updatePayScale");
		return mapping.findForward(CMSConstants.EMP_PAYSCALE);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePayScale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = PayScaleDetailsHandler.getInstance()
					.deletePayScale(payScaleForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.payScale.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.payScale.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			payScaleForm.reset();
			setRequestedDataToForm(payScaleForm);
		} catch (Exception e) {
			log.error("error submit payscale...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				payScaleForm.setErrorMessage(msg);
				payScaleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				payScaleForm.setErrorMessage(msg);
				payScaleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete PayScale ");
		return mapping.findForward(CMSConstants.EMP_PAYSCALE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivatePayScale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivatePayscale Action");
		PayScaleDetailsForm payScaleForm = (PayScaleDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, payScaleForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = payScaleForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			payScaleForm.setId(Integer.parseInt(duplicateId));
			isReactivate = PayScaleDetailsHandler.getInstance().reactivatePayScale(payScaleForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.Payscale.details.activate"));
				setRequestedDataToForm(payScaleForm);
				payScaleForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(payScaleForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.Payscale.details.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatePayScale of EmployeeResumeSubmissionAction", e);
			String msg = super.handleApplicationException(e);
			payScaleForm.setErrorMessage(msg);
			payScaleForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivatePayScale of EmployeeResumeSubmissionAction");
		return mapping.findForward(CMSConstants.EMP_PAYSCALE); 
	}
	public void setRequestedDataToForm(PayScaleDetailsForm payScaleForm) throws Exception {
		// 1. Set the PayScale list
		List<PayScaleTO> payScaleToList = PayScaleDetailsHandler.getInstance().getPayscaleList();
		payScaleForm.setPayScaleToList(payScaleToList);
	}
}
