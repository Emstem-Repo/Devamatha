package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.forms.reports.StudentDetailsReportForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.StudentDetailsReportHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

/**
 * @author dIlIp
 *
 */
public class StudentDetailsReportAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentDetailsReportAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentDetailsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentDetailsReportForm stForm = (StudentDetailsReportForm) form;
		try {
			setUserId(request, stForm);
			stForm.clear();
			stForm.setClassMap(null);
			stForm.setProgramId(null);
			HttpSession session = request.getSession(false);	
			session.removeAttribute("StudentSearch");
			session.removeAttribute(CMSConstants.EXCEL_BYTES);
			setRequiredDataToForm(stForm);
			stForm.setDownloadExcel(null);
		} catch (Exception e) {
			log.error("error in initStudentDetailsReport...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.INIT_STUDENT_DETAILS_REPORT);
	}

	private void setRequiredDataToForm(StudentDetailsReportForm stForm) {
		
		try {
			
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			stForm.setProgramTypeList(programTypeList);
			
			List<String> deaneryList = StudentDetailsReportHandler.getInstance().getDeanery();
			stForm.setDeaneryList(deaneryList);
			
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
		}
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSearchedStudents(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("StudentSearch")== null){
			StudentDetailsReportForm stForm = (StudentDetailsReportForm) form;
			stForm.setDownloadExcel(null);
			ActionErrors errors = new ActionErrors();
		
			if(errors.isEmpty()){
				try {
					if(stForm.getIsCurrentYear().equalsIgnoreCase("previous") && stForm.getPreviousYears().equalsIgnoreCase("")){
						errors.add("error", new ActionError("knowledgepro.reports.student.details.report.previous.required"));
						addErrors(request, errors);
						stForm.clear();
						setRequiredDataToForm(stForm);
						return mapping.findForward(CMSConstants.INIT_STUDENT_DETAILS_REPORT);
					}
					else{
					List<StudentTO> studentSearch = StudentDetailsReportHandler.getSearchedStudents(stForm);
					if(studentSearch!=null){
						//stForm.setStudentToList(studentSearch);
						session.setAttribute("StudentSearch", studentSearch);
					 }
					}
			
				}catch (ApplicationException ae) {
					String msg = super.handleApplicationException(ae);
					stForm.setErrorMessage(msg);
					stForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				catch (Exception e) {
					throw e;
				}
			}else{
				saveErrors(request, errors);
				setRequiredDataToForm(stForm);
				return mapping.findForward(CMSConstants.INIT_STUDENT_DETAILS_REPORT);
			}
		}
		return mapping.findForward(CMSConstants.STUDENT_DETAILS_REPORT);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("entered initexportToExcel..");
		StudentDetailsReportForm stForm = (StudentDetailsReportForm) form;
		stForm.setSelectedColumnsArray(null);
		stForm.setUnselectedColumnsArray(null);
		stForm.setDownloadExcel(null);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_STUDENT_DETAILS_REPORT_EXCEL);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("entered exportToExcel..");
		StudentDetailsReportForm stForm = (StudentDetailsReportForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = stForm.validate(mapping, request);
		stForm.setDownloadExcel(null);
		HttpSession session = request.getSession(false);
		if (errors.isEmpty()) {	
			try {
		
					setUserId(request, stForm);
					
					boolean isUpdated =	StudentDetailsReportHandler.getInstance().exportTOExcel(stForm, request,session);
	
					if(isUpdated){
						stForm.setDownloadExcel("download");
						ActionMessage message = new ActionMessage("knowledgepro.student.excelreport.successful");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
			}catch (ApplicationException ae) {
				log.error("error occured in exportToExcel in StudentDetailsReport",ae);
				String msg = super.handleApplicationException(ae);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
				log.error("error occured in exportToExcel in EmployeeReportAction",e);
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else {
			addErrors(request, errors);			
			return mapping.findForward(CMSConstants.INIT_STUDENT_DETAILS_REPORT_EXCEL);
		}
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.STUDENT_DETAILS_REPORT);
	}

}
