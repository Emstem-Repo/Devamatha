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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeApproveLeaveForm;
import com.kp.cms.handlers.employee.EmployeeApproveLeaveHandler;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;

@SuppressWarnings("deprecation")
public class EmployeeApproveLeaveAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmployeeApproveLeaveAction.class);
	String employeeId = null;
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to fee concession search.
	 * @throws Exception
	 *        
	 */
	public ActionForward employeeApproveLeave(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
	 	
		log.debug("Entering employeeApproveLeave ");
		EmployeeApproveLeaveForm employeeApproveLeaveForm  = (EmployeeApproveLeaveForm)form;
		HttpSession session = request.getSession();
		employeeId = session.getAttribute("employeeId").toString();
	 	try 
	 	{
	 		List<EmployeeApproveLeaveTO> leaveApproveList = EmployeeApproveLeaveHandler.getInstance().getApplyLeaveDetails(employeeApproveLeaveForm,employeeId);
	 		employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			employeeApproveLeaveForm.setErrorMessage(msg);
			employeeApproveLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	 	log.debug("Leaving employeeApproveLeave");
	 	
	 	return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_LEAVE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			   						HttpServletResponse response) throws Exception {
		log.debug("Entering updateStatus ");
		EmployeeApproveLeaveForm employeeApproveLeaveForm  = (EmployeeApproveLeaveForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isUpdated;
	 	try {
	 		if(isCancelled(request)){
	 			List<EmployeeApproveLeaveTO> leaveApproveList1 = EmployeeApproveLeaveHandler.getInstance().getApplyLeaveDetails(employeeApproveLeaveForm,employeeId);
		 		employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList1);
		 		return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_LEAVE);
	 		}
	 		
	 		List<EmployeeApproveLeaveTO> leaveApproveList = employeeApproveLeaveForm.getLeaveApprovalList();
	 		isUpdated = EmployeeApproveLeaveHandler.getInstance().updateStatus(leaveApproveList);
	 		List<EmployeeApproveLeaveTO> leaveApproveList1 = EmployeeApproveLeaveHandler.getInstance().getApplyLeaveDetails(employeeApproveLeaveForm,employeeId);
	 		employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList1);
	 	}catch (Exception e) {
	 		if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				employeeApproveLeaveForm.setErrorMessage(msg);
				employeeApproveLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}	 		
	 	}

		if (isUpdated) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.employee.leave.approve.success");
			messages.add("messages", message);
			saveMessages(request, messages);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.employee.leave.approve.failure"));
			saveErrors(request, errors);
		}
	 	log.debug("Leaving updateStatus");
	 	
	 	return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_LEAVE);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to fee concession search.
	 * @throws Exception
	 *        
	 */
	public ActionForward showAvailableLeaves(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		log.debug("Entering showAvailableLeaves ");
		EmployeeApproveLeaveForm employeeApproveLeaveForm  = (EmployeeApproveLeaveForm)form;
	 	try {
	 		List<EmpLeaveTO> availableLeaveList = EmployeeApproveLeaveHandler.getInstance().getAvailableLeaves(employeeApproveLeaveForm.getEmployeeId());
	 		employeeApproveLeaveForm.setAvailableLeaveList(availableLeaveList);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			employeeApproveLeaveForm.setErrorMessage(msg);
			employeeApproveLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	 	log.debug("Leaving showAvailableLeaves");
	 	
	 	return mapping.findForward(CMSConstants.EMPLOYEE_DISP_AVAILABLE_LEAVE);
	}
	
	public ActionForward employeeApproveCancellation(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		log.debug("Entering employeeApproveLeave ");
		EmployeeApproveLeaveForm employeeApproveLeaveForm  = (EmployeeApproveLeaveForm)form;
		HttpSession session = request.getSession();
		employeeId = session.getAttribute("employeeId").toString();
		try 
		{
			List<EmployeeApproveLeaveTO> leaveApproveList = EmployeeApproveLeaveHandler.getInstance().getCancelLeaveDetails(employeeApproveLeaveForm,employeeId);
			employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList);
		}
		catch (Exception exception) 
		{
			String msg = super.handleApplicationException(exception);
			employeeApproveLeaveForm.setErrorMessage(msg);
			employeeApproveLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving employeeApproveLeave");
		return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_CANCEL_LEAVE);
	}

	/**
	 * 
* 	@param mapping
* 	@param form
* 	@param request
* 	@param response
* 	@return
* 	@throws Exception
	*/

	public ActionForward updateCancelStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		log.debug("Entering updateStatus ");
		EmployeeApproveLeaveForm employeeApproveLeaveForm  = (EmployeeApproveLeaveForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isUpdated;
		try 
		{
			if(isCancelled(request))
			{
				List<EmployeeApproveLeaveTO> leaveApproveList1 = EmployeeApproveLeaveHandler.getInstance().getCancelLeaveDetails(employeeApproveLeaveForm,employeeId);
				employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList1);
				return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_CANCEL_LEAVE);
			}
			List<EmployeeApproveLeaveTO> leaveApproveList = employeeApproveLeaveForm.getLeaveApprovalList();
			isUpdated = EmployeeApproveLeaveHandler.getInstance().updateCancelStatus(leaveApproveList);
			List<EmployeeApproveLeaveTO> leaveApproveList1 = EmployeeApproveLeaveHandler.getInstance().getCancelLeaveDetails(employeeApproveLeaveForm,employeeId);
			employeeApproveLeaveForm.setLeaveApprovalList(leaveApproveList1);
		}
		catch (Exception e) 
		{
			if (e instanceof BusinessException) 
			{
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				employeeApproveLeaveForm.setErrorMessage(msg);
				employeeApproveLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}	 		
		}
		if (isUpdated) 
		{
			// 	success deleted
			ActionMessage message = new ActionMessage("knowledgepro.employee.cancel.leave.approve.success");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		else 
		{
			// 	failure error message.
			errors.add("error", new ActionError("knowledgepro.employee.cancel.leave.approve.failure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving updateStatus");
		return mapping.findForward(CMSConstants.EMPLOYEE_APPROVE_CANCEL_LEAVE);
	}
}
