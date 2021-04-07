package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeReportForm;
import com.kp.cms.forms.employee.GuestFacultyExcelReportForm;
import com.kp.cms.handlers.employee.EmployeeReportHandler;
import com.kp.cms.handlers.employee.GuestFacultyExcelReportHandler;
import com.kp.cms.to.employee.GuestFacultyTO;

public class GuestFacultyExcelReportAction extends BaseDispatchAction{
	GuestFacultyExcelReportHandler handler=GuestFacultyExcelReportHandler.getInstance();
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGuestFacultyExcelReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GuestFacultyExcelReportForm guestFacultyExcelReportForm = (GuestFacultyExcelReportForm)form;
		guestFacultyExcelReportForm.setStreamDetails(null);
		guestFacultyExcelReportForm.setDepartment(null);
		guestFacultyExcelReportForm.setDesignation(null);
		guestFacultyExcelReportForm.setActive("1");
		guestFacultyExcelReportForm.setWorkLocation(null);
		HttpSession session = request.getSession(false);	
		session.removeAttribute("EmployeeSearch");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm (guestFacultyExcelReportForm);
		return mapping.findForward(CMSConstants.INIT_GUEST_FACULTY_EXCEL_REPORT);
	}

	private void setRequiredDataTOForm (
			GuestFacultyExcelReportForm guestFacultyExcelReportForm)throws Exception {
		handler.getInitialData(guestFacultyExcelReportForm);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchGuestFaculty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("EmployeeSearch")== null){
			GuestFacultyExcelReportForm guestFacultyExcelReportForm = (GuestFacultyExcelReportForm)form;
			guestFacultyExcelReportForm.setMode(null);
			guestFacultyExcelReportForm.setDownloadExcel(null);
			 ActionErrors errors = guestFacultyExcelReportForm.validate(mapping, request);
			if(errors.isEmpty()){
				try{
					 List<GuestFacultyTO> employeeSearch=handler.getGuestSearchedResults(guestFacultyExcelReportForm);
					 if(employeeSearch!=null){
						 guestFacultyExcelReportForm.setGuestFacultyToList(employeeSearch);
						 session.setAttribute("EmployeeSearch", employeeSearch);
					 }
				}catch (ApplicationException ae) {
					String msg = super.handleApplicationException(ae);
					guestFacultyExcelReportForm.setErrorMessage(msg);
					guestFacultyExcelReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				catch (Exception e) {
					throw e;
				}
			}else {
				addErrors(request, errors);
				setRequiredDataTOForm(guestFacultyExcelReportForm);
				return mapping.findForward(CMSConstants.INIT_GUEST_FACULTY_EXCEL_REPORT);
			}
		}
		return mapping.findForward(CMSConstants.SEARCH_GUEST_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initexportToExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		GuestFacultyExcelReportForm guestFacultyExcelReportForm = (GuestFacultyExcelReportForm)form;
		guestFacultyExcelReportForm.setSelectedColumnsArray(null);
		guestFacultyExcelReportForm.setUnselectedColumnsArray(null);
		return mapping.findForward(CMSConstants.INIT_EXCEL_GUEST_REPORT);
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
		GuestFacultyExcelReportForm guestFacultyExcelReportForm = (GuestFacultyExcelReportForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = guestFacultyExcelReportForm.validate(mapping, request);
		guestFacultyExcelReportForm.setMode(null);
		guestFacultyExcelReportForm.setDownloadExcel(null);
		guestFacultyExcelReportForm.setMaxListSize(0);
		if (errors.isEmpty()) {	
			try {
		
		setUserId(request, guestFacultyExcelReportForm);
		boolean isUpdated =	GuestFacultyExcelReportHandler.getInstance().exportTOExcel(guestFacultyExcelReportForm,request);
	
 		if(isUpdated){
 			guestFacultyExcelReportForm.setMode("excel");
 			guestFacultyExcelReportForm.setDownloadExcel("download");
			ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		}catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			guestFacultyExcelReportForm.setErrorMessage(msg);
			guestFacultyExcelReportForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			String msg = super.handleApplicationException(e);
			guestFacultyExcelReportForm.setErrorMessage(msg);
			guestFacultyExcelReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);			
			return mapping.findForward(CMSConstants.INIT_EXCEL_GUEST_REPORT);
		}
		return mapping.findForward(CMSConstants.SEARCH_GUEST_REPORT);
}
}
