package com.kp.cms.actions.reports;

import java.util.Calendar;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.MonthlyBelowRequiredPercentageReportForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.reports.MonthlyBelowRequiredPercentageHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class MonthlyBelowRequiredPercentageAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(MonthlyBelowRequiredPercentageAction.class);
	private static final String BELOW_REPORT = "belowReport";

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes MOnthly Below Required Percentage Report
	 * @throws Exception
	 */
	public ActionForward initMonthlyBelowRequiredPercentage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initMonthlyBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction");
		MonthlyBelowRequiredPercentageReportForm requiredPercentageReportForm = (MonthlyBelowRequiredPercentageReportForm)form;
		try {
			//Setting all programType and AttendanceType to formbean
			setRequiredDataToForm(requiredPercentageReportForm, request);
			requiredPercentageReportForm.resetFields();
			HttpSession session = request.getSession(false);
			session.removeAttribute(BELOW_REPORT);
		} catch (Exception e) {
			log.error("Error occured at initBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction",e);
				String msg = super.handleApplicationException(e);
				requiredPercentageReportForm.setErrorMessage(msg);
				requiredPercentageReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Leaving into initMonthlyBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction");
		return mapping.findForward(CMSConstants.INIT_MONTHLY_BELOW_PERCENTAGE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * Submits the data
	 * @returns the students MOnthly below the input percentage
	 * Takes all the inputs and searchs all the students and checks their percentage in each subject
	 * @throws Exception
	 */

	public ActionForward submitMonthlyBelowRequiredPercentage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction");
		MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm = (MonthlyBelowRequiredPercentageReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(BELOW_REPORT) == null){
			 ActionMessages errors = monthlyPercentageForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					//Check for valid date format(Only accepts DD/MM/YYYY)
					boolean isValidStartDate;
					boolean isValidEndDate;
					isValidStartDate = CommonUtil.isValidDate(monthlyPercentageForm.getStartDate().trim());
					isValidEndDate = CommonUtil.isValidDate(monthlyPercentageForm.getEndDate().trim());
					if(!isValidStartDate || !isValidEndDate){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
						addErrors(request, errors);
						//Set the required data in form and also in request
						setRequiredDataToForm(monthlyPercentageForm, request);
						return mapping.findForward(CMSConstants.INIT_MONTHLY_BELOW_PERCENTAGE);		
					}
					else{
						HttpSession belowSession = request.getSession(false);
						//Gets the list of students having below the entered percentage
						List<BelowRequiredPercentageTO> studentDetails = MonthlyBelowRequiredPercentageHandler.getInstance().
						getMonthlyAttendanceBelowPercentage(monthlyPercentageForm);
						belowSession.setAttribute(BELOW_REPORT,studentDetails );
					}
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(monthlyPercentageForm, request);
					return mapping.findForward(CMSConstants.INIT_MONTHLY_BELOW_PERCENTAGE);
				}
			} catch (Exception e) {
				log.error("Error occured at submitMonthlyBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction",e);
					String msg = super.handleApplicationException(e);
					monthlyPercentageForm.setErrorMessage(msg);
					monthlyPercentageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.info("Leaving into submitMonthlyBelowRequiredPercentage of MonthlyBelowRequiredPercentageAction");
		return mapping.findForward(CMSConstants.SUBMIT_MONTHLY_BELOW_PERCENTAGE);
	}
	
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of BelowRequiredPercentageAction");	
			List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
			request.setAttribute("attendanceTypeList", attendanceTypeList);
			// Getting the class map current academic year.
			Map<Integer, String> classMap = setClassMapToRequest();
			monthlyPercentageForm.setClassMap(classMap);
			log.info("Exit setRequiredDataToForm..");	
	}
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setClassMapToRequest() throws ApplicationException {
		log.info("entering into setpClassMapToRequest of BelowRequiredPercentageAction class.");
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
			log.info("exit of setpClassMapToRequest of BelowRequiredPercentageAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of BelowRequiredPercentageAction class.",e);
			throw new ApplicationException(e);
		}
	}
}
