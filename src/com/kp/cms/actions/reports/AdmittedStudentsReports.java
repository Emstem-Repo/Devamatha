package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.Date;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AdmittedStudentReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.AdmissionReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.AdmittedStudentsReportsTO;

public class AdmittedStudentsReports extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(AdmittedStudentsReports.class);

	/**
	 * 
	 * Performs the initialize Admitted Students Report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initAdmittedStudentsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering initAdmittedStudentsReport of AdmittedStudentsReports class");
		AdmittedStudentReportForm admittedStudentReportForm = (AdmittedStudentReportForm) form;
		HttpSession session = request.getSession(false);
		admittedStudentReportForm.resetFields();
		setRequiredDataToForm(admittedStudentReportForm, request);
		session.removeAttribute("admittedStudentReport");
		session.removeAttribute("classWithStudents");
		log.info("exit of initAdmittedStudentsReport of AdmittedStudentsReports class");
		return mapping.findForward(CMSConstants.INIT_ADMITTED_STUDENTS_REPORT);
	}

	/**
	 * 
	 * Performs the get Admitted Students Report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getAdmittedStudentsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering initAdmittedStudentsReport of AdmittedStudentsReports class");
		AdmittedStudentReportForm admittedStudentReportForm = (AdmittedStudentReportForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("admittedStudentReport")==null){
			try {
	
				 ActionErrors errors = admittedStudentReportForm.validate(mapping, request);
				if (errors.isEmpty()) {
					
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						admittedStudentReportForm.setOrganizationName(orgTO.getOrganizationName());
					}
					Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					admittedStudentReportForm.setTempYear(admittedStudentReportForm.getAcademicYear() + "-" + String.valueOf(Integer.parseInt(admittedStudentReportForm.getAcademicYear()) + 1));					
					
					List<AdmittedStudentsReportsTO> admissionReportToList = AdmissionReportHandler
							.getInstance().getAdmittedStudentReportToList(
									admittedStudentReportForm);
					session.setAttribute("classWithStudents", admittedStudentReportForm.getClassWithStudents());
					
					session.setAttribute("admittedStudentReport",
							admissionReportToList);
				} else {
					addErrors(request, errors);
	
					setRequiredDataToForm(admittedStudentReportForm, request);
					return mapping
							.findForward(CMSConstants.INIT_ADMITTED_STUDENTS_REPORT);
				}
			} catch (ApplicationException applicationException) {
	
				log.error("Error while initializing activity attendance",
						applicationException);
				String msg = super.handleApplicationException(applicationException);
				admittedStudentReportForm.setErrorMessage(msg);
				admittedStudentReportForm.setErrorStack(applicationException
						.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("exit of initAdmittedStudentsReport of AdmittedStudentsReports class");
		return mapping.findForward(CMSConstants.GET_ADMITTED_STUDENTS_REPORT);

	}

	/*
	 * This method sets the required data to form and request.
	 */
	/**
	 * @param attendanceSummaryReportForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(
			AdmittedStudentReportForm admittedStudentReportForm,
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDataToForm of AbsenceInformationSummaryAction class.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		admittedStudentReportForm.setAcademicYear(String.valueOf(currentYear));
		log.info("exit of setRequiredDataToForm of AbsenceInformationSummaryAction class.");
	}
}