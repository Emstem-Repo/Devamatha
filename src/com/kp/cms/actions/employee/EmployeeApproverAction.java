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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.employee.EmployeeApproverForm;
import com.kp.cms.handlers.employee.EmployeeApproverHandler;
import com.kp.cms.handlers.employee.EmployeeOnlineLeaveHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeApproverAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(EmployeeApproverAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initPage");
		EmployeeApproverForm employeeApproverForm = (EmployeeApproverForm) form;
		employeeApproverForm.resetFields();
		try{
			setDataToForm(employeeApproverForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeApproverForm.setErrorMessage(msg);
			employeeApproverForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initOnlineLeave");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE_APPROVER);
	}

	/**
	 * @param employeeApproverForm
	 * @throws Exception 
	 */
	private void setDataToForm(EmployeeApproverForm employeeApproverForm) throws Exception {
		Map<Integer, String> departmentMap=UserInfoHandler.getInstance().getDepartment();
		employeeApproverForm.setDepartmentMap(departmentMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initPage");
		EmployeeApproverForm employeeApproverForm = (EmployeeApproverForm) form;
		ActionErrors errors = new ActionErrors();
		try{
			if(employeeApproverForm.getDepartmentId() == null || employeeApproverForm.getDepartmentId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Department "));
			}
			if(errors.isEmpty()){
				List<EmployeeTO> employeeToList = EmployeeApproverHandler.getInstance().getEmployeeDetailsDeptWise(employeeApproverForm);
				employeeApproverForm.setEmployeeToList(employeeToList);
				Map<Integer, String> employeeMap = EmployeeOnlineLeaveHandler.getInstance().getEmployeeMap();
				employeeMap = CommonUtil.sortMapByValue(employeeMap);
				request.getSession().setAttribute("approversMap", employeeMap);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE_APPROVER);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeApproverForm.setErrorMessage(msg);
			employeeApproverForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initOnlineLeave");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE_APPROVER);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initPage");
		EmployeeApproverForm employeeApproverForm = (EmployeeApproverForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, employeeApproverForm);
		try{
			if(employeeApproverForm.getApproverId() == null || employeeApproverForm.getApproverId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Approver "));
			}
			if(errors.isEmpty()){
				List<EmployeeTO> employeeToList = employeeApproverForm.getEmployeeToList();
				boolean save = EmployeeApproverHandler.getInstance().saveDetails(employeeToList,employeeApproverForm);
				if(save){
					employeeApproverForm.setEmployeeToList(null);
					List<EmployeeTO> employeeToList1 = EmployeeApproverHandler.getInstance().getEmployeeDetailsDeptWise(employeeApproverForm);
					employeeApproverForm.setEmployeeToList(employeeToList1);
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Approver "));
					saveMessages(request, messages);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE_APPROVER);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeApproverForm.setErrorMessage(msg);
			employeeApproverForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initOnlineLeave");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE_APPROVER);
	}
	/**
	 * This method will set the user in to the form.
	 * @param request
	 * @param form
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}		
}
