package com.kp.cms.actions.employee;

import java.util.List;
import java.util.Map;
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
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.employee.EmpLeaveAllotmentHandler;
import com.kp.cms.handlers.employee.EmployeeSettingsHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.to.employee.EmpLeaveAllotTO;

public class EmpLeaveAllotmentAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EmpLeaveAllotmentAction.class);
	public ActionForward initEmpLeaveAllotment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		empLeaveAllotForm.reset();
		setRequestedDataToForm(empLeaveAllotForm);
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
	}
	public void setRequestedDataToForm(EmpLeaveAllotmentForm empLeaveForm) throws Exception {
		// 1. Set the LeaveAllotment list
		int settingsLeave=EmpLeaveAllotmentHandler.getInstance().getEmpSettingsLeaveType();
		Map<Integer,String> empLeaveMap=EmpLeaveAllotmentHandler.getInstance().getEmpLeveType(settingsLeave);
		empLeaveForm.setEmpLeaveMap(empLeaveMap);
		List<EmpLeaveAllotTO> leaveList = EmpLeaveAllotmentHandler.getInstance().getLeaveAllotList();
		empLeaveForm.setLeaveAllotTOList(leaveList);
		Map<Integer,String> empTypeMap=EmpLeaveAllotmentHandler.getInstance().getEmpType();
		empLeaveForm.setEmpTypeMap(empTypeMap);
	}
	public ActionForward addLeaveAllotment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		setUserId(request, empLeaveAllotForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = empLeaveAllotForm.validate(mapping, request);
		String initRequired=empLeaveAllotForm.getInitializeRequired();
		HttpSession session=request.getSession();
		if(initRequired!=null){
			if(initRequired.equalsIgnoreCase("No")){
				if(empLeaveAllotForm.getNoOfAccumulatedLeave()!=null && !empLeaveAllotForm.getNoOfAccumulatedLeave().isEmpty()){
						errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.notSelected"));
						addErrors(request, errors);
				}
			}
		}
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=EmpLeaveAllotmentHandler.getInstance().duplicateCheck(empLeaveAllotForm,session,errors);
				if(!isDuplicate){
				isAdded = EmpLeaveAllotmentHandler.getInstance()
						.addLeaveAllotment(empLeaveAllotForm);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.leaveAllotment.addsuccess"));
					saveMessages(request, messages);
					empLeaveAllotForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.leaveAllotment.failed"));
					addErrors(request, errors);
					empLeaveAllotForm.reset();
				}
				}else{
					addErrors(request, errors);
				}
			} catch (ReActivateException e2) {
				errors.add("error", new ActionError(CMSConstants.EMPLOYEE_LEAVEALLOTMENT_REACTIVATE));
				saveErrors(request, errors);
				session.setAttribute("ReactivateId", empLeaveAllotForm.getId());
				return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
			} 
			
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				empLeaveAllotForm.setErrorMessage(msg);
				empLeaveAllotForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(empLeaveAllotForm);
			return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
		}
		setRequestedDataToForm(empLeaveAllotForm);
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
	}
	public ActionForward editLeaveAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		log.debug("Entering EditLeaveAllotment ");
		try {
			EmpLeaveAllotmentHandler.getInstance().editLeaveAllotment(
					empLeaveAllotForm);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving EditLeaveAllotment ");
		} catch (Exception e) {
			log.error("error in editing LeaveAllotment...", e);
			String msg = super.handleApplicationException(e);
			empLeaveAllotForm.setErrorMessage(msg);
			empLeaveAllotForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateLeaveAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePayScale Action");
		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = empLeaveAllotForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isUpdated = false;
		String initRequired=empLeaveAllotForm.getInitializeRequired();
		if(initRequired!=null){
			if(initRequired.equalsIgnoreCase("No")){
				if(empLeaveAllotForm.getNoOfAccumulatedLeave()!=null && !empLeaveAllotForm.getNoOfAccumulatedLeave().isEmpty()){
						errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.notSelected"));
						addErrors(request, errors);
				}
			}
		}
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				empLeaveAllotForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				EmpLeaveAllotmentHandler.getInstance().editLeaveAllotment(
						empLeaveAllotForm);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
			}
			setUserId(request, empLeaveAllotForm); // setting user id to update
			boolean isDuplicate=EmpLeaveAllotmentHandler.getInstance().duplicateCheck(empLeaveAllotForm,session,errors);
			if(!isDuplicate){
			isUpdated = EmpLeaveAllotmentHandler.getInstance()
					.updateLeaveAllotment(empLeaveAllotForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.leave.allotment.update");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				empLeaveAllotForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.leave.allotment.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				empLeaveAllotForm.reset();
			}}
		else{
				addErrors(request, errors);
				request.setAttribute("Operation", "edit");
			}
		} catch (Exception e) {
			log.error("Error occured in edit LeaveAllotment", e);
			String msg = super.handleApplicationException(e);
			empLeaveAllotForm.setErrorMessage(msg);
			empLeaveAllotForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(empLeaveAllotForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
		}
		setRequestedDataToForm(empLeaveAllotForm);
		log.debug("Exit: action class updateLeaveAllotment");
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);

	}
	public ActionForward deleteLeaveAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = EmpLeaveAllotmentHandler.getInstance()
					.deleteLeaveAllotment(empLeaveAllotForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.leave.allotment.delete");
				messages.add("messages", message);
				saveMessages(request, messages);
				empLeaveAllotForm.reset();
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.leave.allotment.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			setRequestedDataToForm(empLeaveAllotForm);
		} catch (Exception e) {
			log.error("error submit leaveAllotment...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				empLeaveAllotForm.setErrorMessage(msg);
				empLeaveAllotForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				empLeaveAllotForm.setErrorMessage(msg);
				empLeaveAllotForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete LeaveAllotment ");
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT);
	}
	public ActionForward reactivateLeaveAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivatePayscale Action");
		EmpLeaveAllotmentForm empLeaveAllotForm = (EmpLeaveAllotmentForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, empLeaveAllotForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = empLeaveAllotForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			empLeaveAllotForm.setUserId(userId);
			empLeaveAllotForm.setId(Integer.parseInt(duplicateId));
			isReactivate = EmpLeaveAllotmentHandler.getInstance().reactivateLeaveAllotment(empLeaveAllotForm);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.leaveAllotment.activate"));
				setRequestedDataToForm(empLeaveAllotForm);
				empLeaveAllotForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(empLeaveAllotForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.leaveAllotment.activate.fail"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatePayScale of EmployeeResumeSubmissionAction", e);
			String msg = super.handleApplicationException(e);
			empLeaveAllotForm.setErrorMessage(msg);
			empLeaveAllotForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivatePayScale of EmployeeResumeSubmissionAction");
		return mapping.findForward(CMSConstants.EMP_LEAVE_ALLOTMENT); 
	}
}
