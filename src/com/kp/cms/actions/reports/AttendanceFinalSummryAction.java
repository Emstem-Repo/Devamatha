package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.attendance.AttendanceSummaryReportAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.AttendenceFinalSummaryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.reports.AttendanceFinalSummaryHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.AttendanceFinalSummaryReportTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class AttendanceFinalSummryAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(AttendanceSummaryReportAction.class);

	/**
	 * 
	 * Performs the initialize Attendance final summary report.
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
	public ActionForward initAttendanceFinalSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		AttendenceFinalSummaryForm attendanceSummaryReportForm = (AttendenceFinalSummaryForm) form;
		attendanceSummaryReportForm.resetFields();
		setRequiredDataToForm(attendanceSummaryReportForm, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("monthlyAttendanceFinalSummaryReport");
		session.removeAttribute("attendanceFinalSummaryReport");
		log.info("exit of initAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_FINAL_SUMMARY);
	}
	
	/**
	 * 
	 * Performs the initialize Monthly Attendance final summary report.
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
	public ActionForward initMonthlyAttendanceFinalSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initMonthlyAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		AttendenceFinalSummaryForm attendanceSummaryReportForm = (AttendenceFinalSummaryForm) form;
		setRequiredDataToForm(attendanceSummaryReportForm, request);
		attendanceSummaryReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute("attendanceFinalSummaryReport");
		session.removeAttribute("monthlyAttendanceFinalSummaryReport");
		log.info("exit of initMonthlyAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		return mapping.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE_FINAL_SUMMARY);
	}
	
	
	/**
	 * 
	 * Get Monthly Attendance Final summary report result.
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
	public ActionForward getMonthlyAttendanceFinalSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getMonthlyAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		AttendenceFinalSummaryForm attendanceSummaryReportForm = (AttendenceFinalSummaryForm) form;
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("monthlyAttendanceFinalSummaryReport")==null){
			 ActionMessages errors = attendanceSummaryReportForm.validate(mapping, request);
			if (errors.isEmpty()) {
				try {
					List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryList = AttendanceFinalSummaryHandler
							.getInstance().getMonthlyAttendanceFinalSummaryReport(
									attendanceSummaryReportForm);
					session.setAttribute("monthlyAttendanceFinalSummaryReport",
							attendanceFinalSummaryList);
				} catch (Exception e) {
					log
							.error("Error while Getting attendance final summary report "
									+ e.getMessage());
					
					String msg = super.handleApplicationException(e);
					attendanceSummaryReportForm.setErrorMessage(msg);
					attendanceSummaryReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
	
				setRequiredDataToForm(attendanceSummaryReportForm, request);
				return mapping
						.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE_FINAL_SUMMARY);
			}
		}	
		log.info("exit of getMonthlyAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		return mapping.findForward(CMSConstants.GET_MONTHLY_ATTENDANCE_FINAL_SUMMARY);
	}

	/**
	 * 
	 * Get Attendance Final summary report result.
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
	public ActionForward getAttendanceFinalSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		AttendenceFinalSummaryForm attendanceSummaryReportForm = (AttendenceFinalSummaryForm) form;

		HttpSession session = request.getSession(false);
		if(session.getAttribute("attendanceFinalSummaryReport")==null){
			 ActionMessages errors = attendanceSummaryReportForm.validate(mapping, request);
			validateAttendanceDate(attendanceSummaryReportForm, errors);
			if (errors.isEmpty()) {
				try {
					List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryList = AttendanceFinalSummaryHandler
							.getInstance().getAttendanceFinalSummaryReport(
									attendanceSummaryReportForm);
					session.setAttribute("attendanceFinalSummaryReport",
							attendanceFinalSummaryList);
				} catch (Exception e) {
					log.error("Error while Getting attendance final summary report " + e.getMessage());
					
					String msg = super.handleApplicationException(e);
					attendanceSummaryReportForm.setErrorMessage(msg);
					attendanceSummaryReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
	
				setRequiredDataToForm(attendanceSummaryReportForm, request);
				return mapping
						.findForward(CMSConstants.INIT_ATTENDANCE_FINAL_SUMMARY);
			}
		}	
		log.info("exit of getAttendanceFinalSummary of AttendanceFinalSummryAction class.");
		return mapping.findForward(CMSConstants.GET_ATTENDANCE_FINAL_SUMMARY);
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
			AttendenceFinalSummaryForm attendanceSummaryReportForm,
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDataToForm of AttendanceFinalSummryAction class.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
			.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler
				.getInstance().getAttendanceType();
		request.setAttribute("attendanceTypeList", attendanceTypeList);
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// code by hari
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}// end

		Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
		attendanceSummaryReportForm.setClassMap(classMap);
		
		log.info("exit of setRequiredDataToForm of AttendanceFinalSummryAction class.");
	}
	
	private void validateAttendanceDate(AttendenceFinalSummaryForm attendanceSummaryReportForm,
			ActionMessages errors) {
			if(attendanceSummaryReportForm.getStartDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getStartDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
			}
			if(attendanceSummaryReportForm.getEndDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getEndDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
			}
	}

}