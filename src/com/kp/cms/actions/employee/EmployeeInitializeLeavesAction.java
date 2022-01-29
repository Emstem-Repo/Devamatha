package com.kp.cms.actions.employee;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmployeeInitializeLeavesForm;
import com.kp.cms.handlers.employee.ApplyLeaveHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandler;
import com.kp.cms.handlers.employee.EmployeeInitializeLeavesHandler;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmpInitializeTo;
import com.kp.cms.to.employee.EmployeeKeyValueTO;

public class EmployeeInitializeLeavesAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(EmployeeInitializeLeavesAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInitalizeLeaves(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initInitalizeLeaves input");
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		employeeInitializeLeavesForm.resetFields();
		setRequiredDatatoForm(employeeInitializeLeavesForm, request);
		log.info("Exit initInitalizeLeaves input");
		
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}

	/**
	 * @param employeeInitializeLeavesForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(EmployeeInitializeLeavesForm employeeInitializeLeavesForm,HttpServletRequest request) throws Exception{
		List<EmployeeKeyValueTO> listEmployeeType = EmployeeInfoHandler.getInstance().getEmployeeType();
		if (listEmployeeType != null && listEmployeeType.size() > 0) {
			employeeInitializeLeavesForm.setListEmployeeType(listEmployeeType);
		}
		List<EmpInitializeTo> list=EmployeeInitializeLeavesHandler.getInstance().getInitializeData();
		employeeInitializeLeavesForm.setList(list);
		List<EmpLeaveTypeTO> leaveList=ApplyLeaveHandler.getInstance().getLeaveTypeList();
		employeeInitializeLeavesForm.setLeaveList(leaveList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addEmployeeInitialize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = employeeInitializeLeavesForm.validate(mapping, request);
		boolean isAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, employeeInitializeLeavesForm);
			EmployeeInitializeLeavesHandler.getInstance().checkForDupliate(employeeInitializeLeavesForm);
			isAdded = EmployeeInitializeLeavesHandler.getInstance().addEmployeeInitialize(employeeInitializeLeavesForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.employee.leaves.initialize.duplicate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(employeeInitializeLeavesForm, request);
					return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.employee.leaves.initialize.reactivate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(employeeInitializeLeavesForm, request);
					return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);	
				}
				log.error("Error occured in addEmployeeInitialize", e);
				String msg = super.handleApplicationException(e);
				employeeInitializeLeavesForm.setErrorMessage(msg);
				employeeInitializeLeavesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.employee.leaves.initialize.added.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequiredDatatoForm(employeeInitializeLeavesForm, request);
				employeeInitializeLeavesForm.resetFields();
				
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.employee.leaves.initialize.added.failure"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
		}
		setRequiredDatatoForm(employeeInitializeLeavesForm, request);
		
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteEmployeeInitialize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isDeleted = false;
		int id = employeeInitializeLeavesForm.getId();
		try
		{
			if(id >0)
			{
				isDeleted = EmployeeInitializeLeavesHandler.getInstance().deleteEmployeeInitialize(id);
			}
		}
		catch (Exception e) {
			log.error("error occured in delete Employee leave Initialize in Action"+e);
			String msg = super.handleApplicationException(e);
			employeeInitializeLeavesForm.setErrorMessage(msg);
			employeeInitializeLeavesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isDeleted)
		{
			ActionMessage message = new ActionMessage("knowledgepro.employee.leaves.initialize.deleted.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			employeeInitializeLeavesForm.resetFields();
			setRequiredDatatoForm(employeeInitializeLeavesForm, request);
		}else
		{
			errors.add("error",  new ActionError("knowledgepro.employee.leaves.initialize.deleted.failure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editEmployeeInitialize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		try{
			EmployeeInitializeLeavesHandler.getInstance().editEmployeeInitialize(employeeInitializeLeavesForm);
			employeeInitializeLeavesForm.setDupEmpTypeId(employeeInitializeLeavesForm.getEmpTypeId());
			employeeInitializeLeavesForm.setDupLeaveTypeId(employeeInitializeLeavesForm.getLeaveTypeId());
			employeeInitializeLeavesForm.setDupId(employeeInitializeLeavesForm.getId());
			employeeInitializeLeavesForm.setDupDate(employeeInitializeLeavesForm.getAllotedDate());
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		}
		catch (Exception e) {
			log.error("Error occured  editEmployeeInitialize in Action"+e);
			String msg = super.handleApplicationException(e);
			employeeInitializeLeavesForm.setErrorMessage(msg);
			employeeInitializeLeavesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateEmployeeInitialize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		String dupEmpTypeId = employeeInitializeLeavesForm.getDupEmpTypeId();
		String dupLeaveTypeId = employeeInitializeLeavesForm.getDupLeaveTypeId();
		String dupAllotedDate = employeeInitializeLeavesForm.getDupDate();
		boolean isUpdated = false;
		try
		{if(isCancelled(request))
		{
			employeeInitializeLeavesForm.setDupEmpTypeId(employeeInitializeLeavesForm.getEmpTypeId());
			employeeInitializeLeavesForm.setDupLeaveTypeId(employeeInitializeLeavesForm.getLeaveTypeId());
			employeeInitializeLeavesForm.setDupDate(employeeInitializeLeavesForm.getAllotedDate());
			EmployeeInitializeLeavesHandler.getInstance().editEmployeeInitialize(employeeInitializeLeavesForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		}else
			if(errors.isEmpty())
			{
				if(!dupEmpTypeId.equals(employeeInitializeLeavesForm.getEmpTypeId()) || !dupLeaveTypeId .equals(employeeInitializeLeavesForm.getLeaveTypeId()) || !dupAllotedDate.equals(employeeInitializeLeavesForm.getAllotedDate()))
				{
					EmployeeInitializeLeavesHandler.getInstance().checkForDupliate(employeeInitializeLeavesForm);
				}
				isUpdated = EmployeeInitializeLeavesHandler.getInstance().updateEmployeeInitialize(employeeInitializeLeavesForm);
				if(isUpdated)
				{
					ActionMessage message = new ActionMessage("knowledgepro.employee.leaves.initialize.updated.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequiredDatatoForm(employeeInitializeLeavesForm, request);
					employeeInitializeLeavesForm.resetFields();
				}else{
					errors.add("errors", new ActionError("knowledgepro.employee.leaves.initialize.updated.failure"));
					saveErrors(request, errors);
				}
			}
			else
			{
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.leaves.initialize.duplicate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
		}catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.leaves.initialize.reactivate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
		}
		catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeInitializeLeavesForm.setErrorMessage(msg);
			employeeInitializeLeavesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(employeeInitializeLeavesForm, request);
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateEmployeeInitialize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInitializeLeavesForm employeeInitializeLeavesForm = (EmployeeInitializeLeavesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isReActivate = false;
		int id = employeeInitializeLeavesForm.getDupId();
		try
		{
			if(id >0)
			{
				isReActivate = EmployeeInitializeLeavesHandler.getInstance().reActivateEmployeeInitialize(id);
			}
		}
		catch (Exception e) {
			log.error("error occured in delete Employee leave Initialize in Action"+e);
			String msg = super.handleApplicationException(e);
			employeeInitializeLeavesForm.setErrorMessage(msg);
			employeeInitializeLeavesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isReActivate)
		{
			ActionMessage message = new ActionMessage("knowledgepro.employee.leaves.initialize.reActivate.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			employeeInitializeLeavesForm.resetFields();
			setRequiredDatatoForm(employeeInitializeLeavesForm, request);
		}else
		{
			errors.add("error",  new ActionError("knowledgepro.employee.leaves.initialize.reActivate.failure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INITALIZE_LEAVE);
	}
}
