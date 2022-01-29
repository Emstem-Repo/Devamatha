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
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.employee.EmpAllowanceHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.employee.PayScaleTO;

public class EmpAllowanceAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EmpAllowanceAction.class);
	public ActionForward initEmpAllowance(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		empAllowanceForm.reset();
		setRequestedDataToForm(empAllowanceForm);
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
	}
	public void setRequestedDataToForm(EmpAllowanceForm empAllowanceForm) throws Exception {
		// 1. Set the Allowance list
		List<EmpAllowanceTO> allowanceToList=EmpAllowanceHandler.getInstance().getEmpAllowance();
		empAllowanceForm.setAllowanceToList(allowanceToList);
	}
	public ActionForward addAllowanceType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		setUserId(request, empAllowanceForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = empAllowanceForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=EmpAllowanceHandler.getInstance().duplicateCheck(empAllowanceForm,errors,session);
				if(!isDuplicate){
					String mode="add";
				isAdded = EmpAllowanceHandler.getInstance().addAllowance(empAllowanceForm,mode);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.allowance.success"));
					saveMessages(request, messages);
					empAllowanceForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.allowance.fail"));
					addErrors(request, errors);
					empAllowanceForm.reset();
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
				empAllowanceForm.setErrorMessage(msg);
				empAllowanceForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(empAllowanceForm);
			return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
		}
		saveErrors(request, errors);
		setRequestedDataToForm(empAllowanceForm);
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
	}
	public ActionForward editAllowance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		log.debug("Entering EditAllowance ");
		try {
			// employeeResumeForm.reset(mapping, request);
			// String formName = mapping.getName();
			// request.getSession().setAttribute(CMSConstants.FORMNAME,
			// formName);
			EmpAllowanceHandler.getInstance().editAllowance(
					empAllowanceForm);
			request.setAttribute("operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving EditAllowance ");
		} catch (Exception e) {
			log.error("error in editing allowance...", e);
			String msg = super.handleApplicationException(e);
			empAllowanceForm.setErrorMessage(msg);
			empAllowanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
	}
	public ActionForward updateAllowance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePayScale Action");
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = empAllowanceForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				empAllowanceForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				EmpAllowanceHandler.getInstance().editAllowance(
						empAllowanceForm);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
			}
			setUserId(request, empAllowanceForm); // setting user id to update
			// boolean
			// isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);

			// if(isDuplicate)
			// errors.add(CMSConstants.ERROR, new
			// ActionError(CMSConstants.DUPLICATE_RECORDS));
			// else
			boolean isDuplicate=EmpAllowanceHandler.getInstance().duplicateCheck(empAllowanceForm,errors,session);
			if(!isDuplicate){
				String mode="update";
			isUpdated = EmpAllowanceHandler.getInstance().updateAllowance(empAllowanceForm,mode);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.allowance.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				empAllowanceForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.allowance.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				empAllowanceForm.reset();
			}}
		} catch (Exception e) {
			log.error("Error occured in edit allowance", e);
			String msg = super.handleApplicationException(e);
			empAllowanceForm.setErrorMessage(msg);
			empAllowanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(empAllowanceForm);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
		}
		setRequestedDataToForm(empAllowanceForm);
		log.debug("Exit: action class updateAllowance");
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);

	}
	public ActionForward deleteAllowance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = EmpAllowanceHandler.getInstance().deleteAllowance(empAllowanceForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.allowance.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.allowance.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			empAllowanceForm.reset();
			setRequestedDataToForm(empAllowanceForm);
		} catch (Exception e) {
			log.error("error submit allowance...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				empAllowanceForm.setErrorMessage(msg);
				empAllowanceForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				empAllowanceForm.setErrorMessage(msg);
				empAllowanceForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete Allowance ");
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE);
	}
	public ActionForward reactivateAllowance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivatePayscale Action");
		EmpAllowanceForm empAllowanceForm=(EmpAllowanceForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, empAllowanceForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = empAllowanceForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			empAllowanceForm.setId(Integer.parseInt(duplicateId));
			isReactivate = EmpAllowanceHandler.getInstance().reactivateAllowance(empAllowanceForm, userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.allowance.details.activate"));
				setRequestedDataToForm(empAllowanceForm);
				empAllowanceForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(empAllowanceForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.allowance.details.activate.fail"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateAllowance of EmpAllowanceAction", e);
			String msg = super.handleApplicationException(e);
			empAllowanceForm.setErrorMessage(msg);
			empAllowanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateAllowance of EmpAllowanceAction");
		return mapping.findForward(CMSConstants.INIT_EMP_ALLOWANCE); 
	}
}
