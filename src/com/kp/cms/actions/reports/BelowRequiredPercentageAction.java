package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.BelowRequiredPercentageReportForm;
import com.kp.cms.forms.reports.StudentAbsenceDetailsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.reports.BelowRequiredPercentageHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


@SuppressWarnings("deprecation")
public class BelowRequiredPercentageAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(BelowRequiredPercentageAction.class);
	private static final String BELOW_REPORT = "belowReport";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes Below Required Percentage Report
	 * @throws Exception
	 */
	public ActionForward initBelowRequiredPercentage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initBelowRequiredPercentage of BelowRequiredPercentageAction");
		BelowRequiredPercentageReportForm requiredPercentageReportForm = (BelowRequiredPercentageReportForm)form;
		try {
			//Setting all programType and AttendanceType to formbean
			requiredPercentageReportForm.resetFields();
			setRequiredDataToForm(requiredPercentageReportForm, request);
			HttpSession session = request.getSession(false);
			session.removeAttribute(BELOW_REPORT);
		} catch (Exception e) {
			log.error("Error occured at initBelowRequiredPercentage of BelowRequiredPercentageAction",e);
				String msg = super.handleApplicationException(e);
				requiredPercentageReportForm.setErrorMessage(msg);
				requiredPercentageReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Leaving into initBelowRequiredPercentage of BelowRequiredPercentageAction");
		return mapping.findForward(CMSConstants.INIT_BELOWREQUIRED_PERCENTAGE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * Submits the data
	 * @returns the students below the input percentage
	 * Takes all the inputs and searchs all the students and checks their percentage in each subject
	 * @throws Exception
	 */

	public ActionForward submitBelowRequiredPercentage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitBelowRequiredPercentage of BelowRequiredPercentageAction");
		BelowRequiredPercentageReportForm requiredPercentageReportForm = (BelowRequiredPercentageReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(BELOW_REPORT)==null){
			 ActionMessages errors = requiredPercentageReportForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					//Check for valid date format(Only accepts DD/MM/YYYY)
					boolean isValidStartDate;
					boolean isValidEndDate;
					isValidStartDate = CommonUtil.isValidDate(requiredPercentageReportForm.getStartDate().trim());
					isValidEndDate = CommonUtil.isValidDate(requiredPercentageReportForm.getEndDate().trim());
					if(!isValidStartDate || !isValidEndDate){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
						addErrors(request, errors);
						//Set the required data in form and also in request
						setRequiredDataToForm(requiredPercentageReportForm, request);
						return mapping.findForward(CMSConstants.INIT_BELOWREQUIRED_PERCENTAGE);		
					}
					else{
						List<BelowRequiredPercentageTO> studentDetails = BelowRequiredPercentageHandler.getInstance().getStudentAttendanceResults(requiredPercentageReportForm);			
						session.setAttribute(BELOW_REPORT,studentDetails );
					}
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(requiredPercentageReportForm, request);
					return mapping.findForward(CMSConstants.INIT_BELOWREQUIRED_PERCENTAGE);
				}
			} catch (Exception e) {
				log.error("Error occured at submitBelowRequiredPercentage of BelowRequiredPercentageAction",e);
					String msg = super.handleApplicationException(e);
					requiredPercentageReportForm.setErrorMessage(msg);
					requiredPercentageReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.info("Leaving into submitBelowRequiredPercentage of BelowRequiredPercentageAction");
		return mapping.findForward(CMSConstants.SUBMIT_BELOWREQUIRED_PERCENTAGE);
	}
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(BelowRequiredPercentageReportForm requiredPercentageReportForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of BelowRequiredPercentageAction");	
			List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
			request.setAttribute("attendanceTypeList", attendanceTypeList);
			if(requiredPercentageReportForm.getClassesName()!=null && requiredPercentageReportForm.getClassesName().length>0){
				Map<Integer, String> subjectMap = setSubjectMapForForm(requiredPercentageReportForm);
				request.setAttribute("subjectMaps", subjectMap);
			}
			// Getting the class map current academic year.
			Map<Integer, String> classMap = setClassMapToRequest(requiredPercentageReportForm.getAcademicYear());
			requiredPercentageReportForm.setClassMap(classMap);
			if(requiredPercentageReportForm.getAcademicYear()==null){
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				if(year>0)
					requiredPercentageReportForm.setAcademicYear(Integer.toString(year));
			}
			log.info("Exit setRequiredDataToForm..");	
	}
	private Map<Integer, String> setSubjectMapForForm(BelowRequiredPercentageReportForm requiredPercentageReportForm) {
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = requiredPercentageReportForm.getClassesName();

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}

			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while (itr.hasNext()) {
				classSchemewise = itr.next();
				if (classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year = classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse()
							.getId();
					int term = classSchemewise.getClasses().getTermNumber();
					Map<Integer, String> tempMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
					subjectsMap.putAll(tempMap);
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return subjectsMap;
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setClassMapToRequest(String academicYear) throws ApplicationException {
		log.info("entering into setpClassMapToRequest of BelowRequiredPercentageAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			if(academicYear==null){
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				if(year!=0){
					currentYear=year;
				}// end
				}else{
					currentYear=Integer.parseInt(academicYear);
				}
			Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
					.getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of BelowRequiredPercentageAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of BelowRequiredPercentageAction class.",e);
			throw new ApplicationException(e);
		}
	}
	/**
	 *printing option for printMonthlyAbsenceInformationSummary  Report
	 */
	public ActionForward printBelowRequiredPercentage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printBelowRequiredPercentage");
		log.info("Exit printBelowRequiredPercentage");
		return mapping.findForward(CMSConstants.PRINT_BELOW_REQUIRED_PERCENTAGE);
	}
}