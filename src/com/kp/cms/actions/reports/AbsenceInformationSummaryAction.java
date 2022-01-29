package com.kp.cms.actions.reports;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.actions.attendance.AttendanceSummaryReportAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AbsenceInformationSummaryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.AbsenceInformationSummaryHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.AbsenceInformationSummaryTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AbsenceInformationSummaryAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(AttendanceSummaryReportAction.class);

	/**
	 * 
	 * Performs the initialize Absence Information Summary report.
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
	public ActionForward initAbsenceInformationSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		AbsenceInformationSummaryForm attendanceSummaryReportForm = (AbsenceInformationSummaryForm) form;
		setRequiredDataToForm(attendanceSummaryReportForm, request);
		attendanceSummaryReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute("absenceSummaryReport");
		log.info("exit of initAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		return mapping.findForward(CMSConstants.INIT_ABSENCE_INFORMATION_SUMMARY);
	}
	
	
	/**
	 * 
	 * Performs the initialize Absence Information Summary report for monthly attendance.
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
	public ActionForward initMonthlyAbsenceInformationSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		AbsenceInformationSummaryForm absenceSummaryReportForm = (AbsenceInformationSummaryForm) form;
		setRequiredDataToForm(absenceSummaryReportForm, request);
		absenceSummaryReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute("monthlyabsenceSummaryReport");
		log.info("exit of initMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		return mapping.findForward(CMSConstants.INIT_MONTHLY_ABSENCE_INFORMATION_SUMMARY);
	}
	
	/**
	 * 
	 * Fetch and display Absence Information Summary report data based on search condition.
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
	public ActionForward getMonthlyAbsenceInformationSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		AbsenceInformationSummaryForm attendanceSummaryReportForm = (AbsenceInformationSummaryForm) form;
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("monthlyabsenceSummaryReport")==null){
			try {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				ActionErrors errors = attendanceSummaryReportForm.validate(mapping, request);
				if (errors.isEmpty()) {
					List<AbsenceInformationSummaryTO> attendanceSummaryReport = AbsenceInformationSummaryHandler
							.getInstance().getMonthlyAbsenceInformationSummaryTOList(
									attendanceSummaryReportForm);
					
					if(attendanceSummaryReport.isEmpty() ) {
						message = new ActionMessage("knowledgepro.norecords");
						messages.add("messages", message);
						saveMessages(request, messages);
						setRequiredDataToForm(attendanceSummaryReportForm, request);
						log.info("exit of getMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
						return mapping.findForward(CMSConstants.INIT_MONTHLY_ABSENCE_INFORMATION_SUMMARY);
					} else {
						session.setAttribute("monthlyabsenceSummaryReport", attendanceSummaryReport);
						log.info("exit of getMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
					}
					
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(attendanceSummaryReportForm, request);
					return mapping.findForward(CMSConstants.INIT_MONTHLY_ABSENCE_INFORMATION_SUMMARY);
				}
			
			} catch (Exception e) {
				
				log.error("Error while initializing activity attendance"+e.getMessage());
				String msg = super.handleApplicationException(e);
				attendanceSummaryReportForm.setErrorMessage(msg);
				attendanceSummaryReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.GET_MONTHLY_ABSENCE_INFORMATION_SUMMARY);
	}
	

	/**
	 * 
	 * Fetch and display Absence Information Summary report data based on search condition.
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
	public ActionForward getAbsenceInformationSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
		AbsenceInformationSummaryForm attendanceSummaryReportForm = (AbsenceInformationSummaryForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("absenceSummaryReport")==null){
			try {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				ActionErrors errors = attendanceSummaryReportForm.validate(mapping, request);
				if (errors.isEmpty()) {
					List<AbsenceInformationSummaryTO> attendanceSummaryReport = AbsenceInformationSummaryHandler
							.getInstance().getAbsenceInformationSummaryTOList(
									attendanceSummaryReportForm);
					if(attendanceSummaryReport.isEmpty() ) {
						message = new ActionMessage("knowledgepro.norecords");
						messages.add("messages", message);
						saveMessages(request, messages);
						setRequiredDataToForm(attendanceSummaryReportForm, request);
						log.info("exit of getAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
						return mapping.findForward(CMSConstants.INIT_ABSENCE_INFORMATION_SUMMARY);
					} else {
						session.setAttribute("absenceSummaryReport",attendanceSummaryReport);
						log.info("exit of getAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
					}
					
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(attendanceSummaryReportForm, request);
					return mapping.findForward(CMSConstants.INIT_ABSENCE_INFORMATION_SUMMARY);
				}
			
			} catch (Exception e) {
				
				log.error("Error while initializing activity attendance"+e.getMessage());
				String msg = super.handleApplicationException(e);
				attendanceSummaryReportForm.setErrorMessage(msg);
				attendanceSummaryReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.GET_ABSENCE_INFORMATION_SUMMARY);
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
			AbsenceInformationSummaryForm attendanceSummaryReportForm,
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDataToForm of AbsenceInformationSummaryAction class.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		// Getting the class map current academic year.
		Map<Integer, String> classMap = setpClassMapToRequest();
		attendanceSummaryReportForm.setClassMap(classMap);

		log.info("exit of setRequiredDataToForm of AbsenceInformationSummaryAction class.");
	}
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setpClassMapToRequest() throws ApplicationException {
		log.info("entering into setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end


			Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
					.getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of MonthlyAttendanceEntryAction class.",e);
			throw new ApplicationException(e);
		}
	}
	/**
	 *printing option for printAbsenceInformationSummary  Report
	 */
	public ActionForward printAbsenceInformationSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printAbsenceInformationSummary");
		log.info("Exit printAbsenceInformationSummary");
		return mapping.findForward(CMSConstants.ABSENCE_REPORT_PRINT);
	}
	/**
	 *printing option for printMonthlyAbsenceInformationSummary  Report
	 */
	public ActionForward printMonthlyAbsenceInformationSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printMonthlyAbsenceInformationSummary");
		log.info("Exit printMonthlyAbsenceInformationSummary");
		return mapping.findForward(CMSConstants.MONTHLY_ABSENCE_REPORT_PRINT);
	}
}