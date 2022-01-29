	package com.kp.cms.actions.employee;
	
	import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeReportForm;
import com.kp.cms.handlers.employee.EmployeeReportHandler;
	
	public class EmployeeReportAction extends BaseDispatchAction {
		private static Log log = LogFactory.getLog(EmployeeReportAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmployeeReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeReportForm employeeReportForm = (EmployeeReportForm)form;
		employeeReportForm.setStreamDetails(null);
		employeeReportForm.setDepartment(null);
		employeeReportForm.setDesignation(null);
		employeeReportForm.setTeachingStaff("2");
		employeeReportForm.setActive("1");
		employeeReportForm.setWorkLocation(null);
		HttpSession session = request.getSession(false);	
		session.removeAttribute("EmployeeSearch");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm(employeeReportForm);
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_REPORT);
	}
	/**
	 * @param employeeReportForm
	 * @throws Exception 
	 */
	private void setRequiredDataTOForm(EmployeeReportForm employeeReportForm) throws Exception {
		EmployeeReportHandler.getInstance().getInitialPageData(employeeReportForm);
		employeeReportForm.setActive("1");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchEmployeeReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("EmployeeSearch")== null){
			EmployeeReportForm employeeReportForm = (EmployeeReportForm)form;
			employeeReportForm.setMode(null);
			employeeReportForm.setDownloadExcel(null);
			 ActionErrors errors = employeeReportForm.validate(mapping, request);
			if(errors.isEmpty()){
				try{
					 List employeeSearch=EmployeeReportHandler.getInstance().getEmployeeSearchedResults(employeeReportForm);
					 if(employeeSearch!=null){
						 employeeReportForm.setEmployeeToList(employeeSearch);
						 session.setAttribute("EmployeeSearch", employeeSearch);
					 }
				}catch (ApplicationException ae) {
					String msg = super.handleApplicationException(ae);
					employeeReportForm.setErrorMessage(msg);
					employeeReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				catch (Exception e) {
					throw e;
				}
			}else {
				addErrors(request, errors);
				setRequiredDataTOForm(employeeReportForm);
				return mapping.findForward(CMSConstants.INIT_EMPLOYEE_REPORT);
			}
		}
		return mapping.findForward(CMSConstants.SEARCH_EMPLOYEE_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initexportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initexportToExcel..");
		EmployeeReportForm employeeReportForm = (EmployeeReportForm)form;
		employeeReportForm.setSelectedColumnsArray(null);
		employeeReportForm.setUnselectedColumnsArray(null);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_EXCEL_EMP_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered exportToExcel..");
		EmployeeReportForm employeeReportForm = (EmployeeReportForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = employeeReportForm.validate(mapping, request);
		employeeReportForm.setMode(null);
		employeeReportForm.setDownloadExcel(null);
		if (errors.isEmpty()) {	
			try {
		
		setUserId(request, employeeReportForm);
		boolean isUpdated =	EmployeeReportHandler.getInstance().exportTOExcel(employeeReportForm,request);
	
 		if(isUpdated){
 			employeeReportForm.setMode("excel");
 			employeeReportForm.setDownloadExcel("download");
			ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in EmployeeReportAction",ae);
			String msg = super.handleApplicationException(ae);
			employeeReportForm.setErrorMessage(msg);
			employeeReportForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in EmployeeReportAction",e);
			String msg = super.handleApplicationException(e);
			employeeReportForm.setErrorMessage(msg);
			employeeReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);			
			return mapping.findForward(CMSConstants.INIT_EXCEL_EMP_REPORT);
		}
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.SEARCH_EMPLOYEE_REPORT);
}
}
