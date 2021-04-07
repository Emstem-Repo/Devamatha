package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceSummaryReportHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.attendance.AttendanceSummaryReportTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.MonthlyAttendanceSummaryTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


/**
 * @author kalyan.c
 * This class is implemente for Attendance Summary Report
 *
 */
@SuppressWarnings("deprecation")
public class AttendanceSummaryReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttendanceSummaryReportAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to display search page of Attendance Summary Report
	 */
	public ActionForward initAttendSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initAttendSummaryReport..");
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm)form;
		attendanceSummaryReportForm.resetFields();
		setRequiredDataToForm(attendanceSummaryReportForm,request);
		if(request.getParameter("KJC")!=null){
			attendanceSummaryReportForm.setKjcReport(true);
		}else{
			attendanceSummaryReportForm.setKjcReport(false);
		}
		HttpSession session = request.getSession();
		Iterator<Integer> attendanceSummarySizeIterator = attendanceSummaryReportForm.getAttendanceSummarySizeList().iterator();
		while (attendanceSummarySizeIterator.hasNext()) {
			Integer integer = (Integer) attendanceSummarySizeIterator.next();
			if(session.getAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+integer) != null)
				session.removeAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+integer);
		}
		log.info("Exit initAttendSummaryReport..");
		return mapping.findForward(CMSConstants.INIT_ATTENDENCE_SUMMARY_REPORT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to get candidates based on the search criteria
	 */
	public ActionForward submitAttendSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAttendSummaryReport..");	
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		 ActionMessages errors = attendanceSummaryReportForm.validate(mapping, request);
		validateAttendanceDate(attendanceSummaryReportForm, errors);
		if (errors.isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			if(session.getAttribute("attendanceSummaryReport1")==null){
			Map<Integer,List<AttendanceSummaryReportTO>> candidateSearch;
			setSubjectMapNew(attendanceSummaryReportForm);
			candidateSearch = AttendanceSummaryReportHandler.getInstance().getStudentAttendanceResults(attendanceSummaryReportForm);
			session.setAttribute("isActivity", attendanceSummaryReportForm.getIsActivity());
			if(candidateSearch.isEmpty()){	
				message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequiredDataToForm(attendanceSummaryReportForm, request);
				return mapping
						.findForward(CMSConstants.INIT_ATTENDENCE_SUMMARY_REPORT);
			} else {
				attendanceSummaryReportForm.setAttnType("adminView");
				session.setAttribute("AttenType", attendanceSummaryReportForm.getAttnType());
				List<OrganizationTO> organizationDetails = OrganizationHandler
							.getInstance().getOrganizationDetails();
					if (organizationDetails != null
							&& !organizationDetails.isEmpty()) {
						OrganizationTO organizationTO = organizationDetails
								.get(0);
						if (organizationTO != null
								&& organizationTO.getOrganizationName() != null) {
							attendanceSummaryReportForm
									.setOrganisationName(organizationTO
											.getOrganizationName());
						}
					}
				Iterator<List<AttendanceSummaryReportTO>> classCandidateListIterator = candidateSearch.values().iterator();
				int size =0;
				attendanceSummaryReportForm.getAttendanceSummarySizeList().clear();
				while (classCandidateListIterator.hasNext()) {
					List<AttendanceSummaryReportTO> monthlyAttendenceTOList = (List<AttendanceSummaryReportTO>) classCandidateListIterator
							.next();
					if(!monthlyAttendenceTOList.isEmpty()) {
						size = size+1;
						attendanceSummaryReportForm.getAttendanceSummarySizeList().add(size);
						Collections.sort(monthlyAttendenceTOList);

						session.setAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+size,monthlyAttendenceTOList );
					}
				}
				
				
			}
			}
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				attendanceSummaryReportForm.setErrorMessage(msg);
				attendanceSummaryReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			setRequiredDataToForm(attendanceSummaryReportForm, request);
			return mapping.findForward(CMSConstants.INIT_ATTENDENCE_SUMMARY_REPORT);
		}
		log.info("Exit submitAttendSummaryReport..");
		if(attendanceSummaryReportForm.isKjcReport()){
			return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_SUMMARY_REPORT_KJC);
		}else{
			return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_SUMMARY_REPORT);
		}
	}

	
	/*
	 * This method sets the required data to form and request.	 * 
	 */
	public void setRequiredDataToForm(AttendanceSummaryReportForm attendanceSummaryReportForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm..");	
		    //setting programList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
		    //setting AttendanceTypeList to Request
			List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
			request.setAttribute("attendanceTypeList", attendanceTypeList);
			//setting the subject maps when errors are occured
			if(attendanceSummaryReportForm.getClassesName()!=null && attendanceSummaryReportForm.getClassesName().length>0){
				Map<Integer, String> subjectMap = setSubjectMapForForm(attendanceSummaryReportForm);
				request.setAttribute("subjectMaps", subjectMap);
			}
			// Getting the class map current academic year.
			Map<Integer, String> classMap = setpClassMapToRequest(attendanceSummaryReportForm.getAcademicYear());
			attendanceSummaryReportForm.setClassMap(classMap);
			//setting the current Academic year to the form
			if(attendanceSummaryReportForm.getAcademicYear()==null){
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				int year1=year+1;
				if(year>0){
				attendanceSummaryReportForm.setAcademicYear(Integer.toString(year));
				String academicYear=year+"-"+year1;
				attendanceSummaryReportForm.setAcademicYear1(academicYear);
				}
			}
			log.info("Exit setRequiredDataToForm..");	
	}
	private Map<Integer, String> setSubjectMapForForm(
			AttendanceSummaryReportForm attendanceSummaryReportForm) {
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = attendanceSummaryReportForm.getClassesName();

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			
			Map<Integer, String> tempMap = AttendanceSummaryReportHandler.getInstance().getSubjectByClassIDs(classesIdsSet);
			subjectsMap.putAll(tempMap);
			
			/* code commented by chandra
			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseIdNew(classesIdsSet);
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
					tempMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
					Map<Integer, String> tempMap = AttendanceSummaryReportHandler.getInstance()
					.getSubjectByCourseTermYear(courseId, year, term, attendanceSummaryReportForm);
					subjectsMap.putAll(tempMap);
				}
			}    code commented by chandra*/

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return subjectsMap;
	}

	/**
	 * 
	 * @param studentSearchForm
	 * @param errors
	 * This method is used to validate DATE 
	 */
	private void validateAttendanceDate(AttendanceSummaryReportForm attendanceSummaryReportForm,
			ActionMessages errors) {
			if(attendanceSummaryReportForm.getStartDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getStartDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
			}
			if(attendanceSummaryReportForm.getEndDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getEndDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
			}
		
			//if start date greater than end date then showing error message
			if(CommonUtil.checkForEmpty(attendanceSummaryReportForm.getStartDate()) && CommonUtil.checkForEmpty(attendanceSummaryReportForm.getEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(attendanceSummaryReportForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(attendanceSummaryReportForm.getEndDate());
			if (startDate != null && endDate != null) {
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if (daysBetween <= 0) {
					errors.add("error", new ActionError(
							CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}

		}

	}	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to display search page of Attendance Summary Report
	 */
	public ActionForward initMonthlyAttendSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initMonthlyAttendSummaryReport..");
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm)form;
		setRequiredDataToForm(attendanceSummaryReportForm,request);
		attendanceSummaryReportForm.resetFields();
		HttpSession session = request.getSession();
	Iterator<Integer> monthlyAttendanceSummarySizeIterator = attendanceSummaryReportForm
				.getAttendanceSummarySizeList().iterator();
		while (monthlyAttendanceSummarySizeIterator.hasNext()) {
			Integer integer = (Integer) monthlyAttendanceSummarySizeIterator
					.next();
			session
					.removeAttribute(CMSConstants.MONTHLY_ATTENDENCE_SUMMARY_REPORT
							+ integer);
		}
	
		log.info("Exit initMonthlyAttendSummaryReport..");
		return mapping.findForward(CMSConstants.INIT_MONTH_ATTENDENCE_SUMMARY_REPORT);
	}
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to get candidates based on the search criteria
	 */
	public ActionForward submitMonthlyAttendSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitMonthlyAttendSummaryReport..");
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm) form;

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		 ActionMessages errors = attendanceSummaryReportForm.validate(mapping, request);
		validateAttendanceDate(attendanceSummaryReportForm, errors);
		if (errors.isEmpty()) {
			try {

				HttpSession session = request.getSession();
				if(session.getAttribute("monthlyattendanceSummaryReport1")==null){
				Map<Integer, List<MonthlyAttendanceSummaryTO>> candidateSearch;
				setSubjectMapMonthly(attendanceSummaryReportForm);
				candidateSearch = AttendanceSummaryReportHandler.getInstance()
						.getMonthlyStudentAttendanceResults(
								attendanceSummaryReportForm);
				if (candidateSearch.isEmpty()) {
					message = new ActionMessage("knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequiredDataToForm(attendanceSummaryReportForm, request);
					log
							.info("exit of getMonthlyAbsenceInformationSummary of AbsenceInformationSummaryAction class.");
					return mapping
							.findForward(CMSConstants.INIT_MONTH_ATTENDENCE_SUMMARY_REPORT);

				} else {
					List<OrganizationTO> organizationDetails = OrganizationHandler
							.getInstance().getOrganizationDetails();
					if (organizationDetails != null
							&& !organizationDetails.isEmpty()) {
						OrganizationTO organizationTO = organizationDetails
								.get(0);
						if (organizationTO != null
								&& organizationTO.getOrganizationName() != null) {
							attendanceSummaryReportForm
									.setOrganisationName(organizationTO
											.getOrganizationName());
						}
					}
					// monthlyattendanceSummaryReport
					Iterator<List<MonthlyAttendanceSummaryTO>> classCandidateListIterator = candidateSearch
							.values().iterator();
					int size = 0;
					attendanceSummaryReportForm.getAttendanceSummarySizeList()
							.clear();
					while (classCandidateListIterator.hasNext()) {
						List<MonthlyAttendanceSummaryTO> monthlyAttendenceTOList = (List<MonthlyAttendanceSummaryTO>) classCandidateListIterator
								.next();
						if (!monthlyAttendenceTOList.isEmpty()) {
							size = size + 1;
							attendanceSummaryReportForm
									.getAttendanceSummarySizeList().add(size);
							Collections.sort(monthlyAttendenceTOList);
							session
									.setAttribute(
											CMSConstants.MONTHLY_ATTENDENCE_SUMMARY_REPORT
													+ size,
											monthlyAttendenceTOList);
						}
					}

				}
				}
			} catch (ApplicationException ae) {
				String msg = super.handleApplicationException(ae);
				attendanceSummaryReportForm.setErrorMessage(msg);
				attendanceSummaryReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception e) {
				throw e;
			}
		} else {
			addErrors(request, errors);
			setRequiredDataToForm(attendanceSummaryReportForm, request);
			return mapping
					.findForward(CMSConstants.INIT_MONTH_ATTENDENCE_SUMMARY_REPORT);
		}

		log.info("Exit submitMonthlyAttendSummaryReport..");
		return mapping
				.findForward(CMSConstants.SUBMIT_MONTH_ATTENDENCE_SUMMARY_REPORT);
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setpClassMapToRequest(String academicYear) throws ApplicationException {
		log.info("entering into setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
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
					.getClassesByYearNew(currentYear);
			log.info("exit of setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of MonthlyAttendanceEntryAction class.",e);
			throw new ApplicationException(e);
		}
	}
	
	private Map<Integer, String> setClassMapTeacherView(AttendanceSummaryReportForm attendanceSummaryReportForm) throws ApplicationException {
		log.info("entering into setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			if(attendanceSummaryReportForm.getAcademicYear()==null){
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end
			}else{
				currentYear=Integer.parseInt(attendanceSummaryReportForm.getAcademicYear());
			}
			Map<Integer, String> classMap = AttendanceSummaryReportHandler.getInstance().getClassByYearUserId(currentYear,attendanceSummaryReportForm);
			log.info("exit of setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of MonthlyAttendanceEntryAction class.",e);
			throw new ApplicationException(e);
		}
	}
	
	private void setSubjectMap(
			AttendanceSummaryReportForm attendanceSummaryEntryForm) throws ApplicationException {

		Map<Integer, List<Subject>> subjectMapList = new HashMap<Integer, List<Subject>>();
		Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();

		if (attendanceSummaryEntryForm.getClassesName() != null
				&& attendanceSummaryEntryForm.getClassesName().length != 0) {

			if (attendanceSummaryEntryForm.getSubjects() != null
					&& attendanceSummaryEntryForm.getSubjects().length != 0) {

				Set<Integer> classesIdsSet = new HashSet<Integer>();
				Set<Integer> subjectsIdsSet = new HashSet<Integer>();
				for (String str : attendanceSummaryEntryForm.getClassesName()) {
					classesIdsSet.add(Integer.parseInt(str));
				}

				for(String subject : attendanceSummaryEntryForm.getSubjects()){
					subjectsIdsSet.add(Integer.parseInt(subject));
				}
				List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
				.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
				Iterator<ClassSchemewise> classSchemeIterator = classSchemewiseList
				.iterator();
				ClassSchemewise classSchemewise;
				while (classSchemeIterator.hasNext()) {
					classSchemewise = classSchemeIterator.next();
					List<Subject> subjectList = new ArrayList<Subject>();
					if (classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear() != null
							&& classSchemewise.getClasses().getCourse().getId() != 0
							&& classSchemewise.getClasses().getTermNumber() != 0) {
						int year = classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear();
						int courseId = classSchemewise.getClasses().getCourse()
						.getId();
						int term = classSchemewise.getClasses().getTermNumber();
						Map<Integer, Subject> tempMap = AttendanceSummaryReportHandler.getInstance()
						.getSubjectByCourseIdTermYear(courseId, year, term);
						subjectMap.putAll(tempMap);
						subjectList.addAll(tempMap.values());
						subjectMapList.put(classSchemewise.getClasses().getId(),
								subjectList);
					}
				}
			
			
//			Iterator <List<Subject>> tempSubjectIterator = subjectMapList.values().iterator();
			Iterator<Integer> classesIdItr =  subjectMapList.keySet().iterator();
			
			Map<Integer,List<Subject>> newSubjectMapList = new HashMap<Integer, List<Subject>>();
			
			while(classesIdItr.hasNext()){
			Integer classid = (Integer) classesIdItr.next(); 	
			List<Subject> tempList = subjectMapList.get(classid);
			   Iterator<Subject> subIterator = tempList.iterator();
			   List<Subject> newSubjectList =  new ArrayList<Subject>();
			   	while(subIterator.hasNext()){
			   		Subject sub = (Subject)subIterator.next();
			   		if(subjectsIdsSet.contains(sub.getId())){
			   			newSubjectList.add(sub);
			   		}
			   	}
			   	newSubjectMapList.put(classid, newSubjectList);
			}			
			
//			while(tempSubjectIterator.hasNext()){
//			List<Subject> tempSubjectList = (List<Subject>)tempSubjectIterator.next();  	
//			if(tempSubjectList!=null && !tempSubjectList.isEmpty()){
//				Iterator<Subject> itrSub = tempSubjectList.iterator();
//					while(itrSub.hasNext()){
//						Subject sub = (Subject)itrSub.next();
//						
//						
//						
//					}			
//			}			
//			}			
			
			attendanceSummaryEntryForm.setSubjectMap(subjectMap);
			attendanceSummaryEntryForm.setSubjectMapList(newSubjectMapList);
			}
		}
	}
	
	
	
	private void setSubjectMapNew(AttendanceSummaryReportForm attendanceSummaryEntryForm) throws ApplicationException {

		Map<Integer, List<Subject>> subjectMapList = new HashMap<Integer, List<Subject>>();
		Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();
		try {
		if (attendanceSummaryEntryForm.getClassesName() != null
				&& attendanceSummaryEntryForm.getClassesName().length != 0) {

			if (attendanceSummaryEntryForm.getSubjects() != null
					&& attendanceSummaryEntryForm.getSubjects().length != 0) {

				Set<Integer> classesIdsSet = new HashSet<Integer>();
				Set<Integer> subjectsIdsSet = new HashSet<Integer>();
				for (String str : attendanceSummaryEntryForm.getClassesName()) {
					classesIdsSet.add(Integer.parseInt(str));
				}

				for(String subject : attendanceSummaryEntryForm.getSubjects()){
					subjectsIdsSet.add(Integer.parseInt(subject));
				}
				List<ClassSchemewise> classSchemewiseList;
				
					classSchemewiseList = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseIdNew(classesIdsSet);
				
				Iterator<ClassSchemewise> classSchemeIterator = classSchemewiseList
				.iterator();
				ClassSchemewise classSchemewise;
				while (classSchemeIterator.hasNext()) {
					classSchemewise = classSchemeIterator.next();
					List<Subject> subjectList = new ArrayList<Subject>();
					if (classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear() != null
							&& classSchemewise.getClasses().getCourse().getId() != 0
							&& classSchemewise.getClasses().getTermNumber() != 0) {
						int year = classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear();
						int courseId = classSchemewise.getClasses().getCourse()
						.getId();
						int term = classSchemewise.getClasses().getTermNumber();
						Map<Integer, Subject> tempMap = AttendanceSummaryReportHandler.getInstance()
						.getSubjectByCourseIdTermYear(courseId, year, term);
						subjectMap.putAll(tempMap);
						subjectList.addAll(tempMap.values());
						subjectMapList.put(classSchemewise.getClasses().getId(),
								subjectList);
					}
				}
			
			
//			Iterator <List<Subject>> tempSubjectIterator = subjectMapList.values().iterator();
			Iterator<Integer> classesIdItr =  subjectMapList.keySet().iterator();
			
			Map<Integer,List<Subject>> newSubjectMapList = new HashMap<Integer, List<Subject>>();
			
			while(classesIdItr.hasNext()){
			Integer classid = (Integer) classesIdItr.next(); 	
			List<Subject> tempList = subjectMapList.get(classid);
			   Iterator<Subject> subIterator = tempList.iterator();
			   List<Subject> newSubjectList =  new ArrayList<Subject>();
			   	while(subIterator.hasNext()){
			   		Subject sub = (Subject)subIterator.next();
			   		if(subjectsIdsSet.contains(sub.getId())){
			   			newSubjectList.add(sub);
			   		}
			   	}
			   	newSubjectMapList.put(classid, newSubjectList);
			}			
			
//			while(tempSubjectIterator.hasNext()){
//			List<Subject> tempSubjectList = (List<Subject>)tempSubjectIterator.next();  	
//			if(tempSubjectList!=null && !tempSubjectList.isEmpty()){
//				Iterator<Subject> itrSub = tempSubjectList.iterator();
//					while(itrSub.hasNext()){
//						Subject sub = (Subject)itrSub.next();
//						
//						
//						
//					}			
//			}			
//			}			
			
			attendanceSummaryEntryForm.setSubjectMap(subjectMap);
			attendanceSummaryEntryForm.setSubjectMapList(newSubjectMapList);
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param attendanceSummaryEntryForm
	 * @throws ApplicationException
	 */
	private void setSubjectMapMonthly(
			AttendanceSummaryReportForm attendanceSummaryEntryForm) throws ApplicationException {
		
		Map<Integer, List<Subject>> subjectMapList = new HashMap<Integer, List<Subject>>();
		Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();
		
		if (attendanceSummaryEntryForm.getClassesName() != null
				&& attendanceSummaryEntryForm.getClassesName().length != 0) {
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (String str : attendanceSummaryEntryForm.getClassesName()) {
				classesIdsSet.add(Integer.parseInt(str));
			}

			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> classSchemeIterator = classSchemewiseList
					.iterator();
			ClassSchemewise classSchemewise;
			while (classSchemeIterator.hasNext()) {
				classSchemewise = classSchemeIterator.next();
				List<Subject> subjectList = new ArrayList<Subject>();
				if (classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year = classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse()
							.getId();
					int term = classSchemewise.getClasses().getTermNumber();
					Map<Integer, Subject> tempMap = AttendanceSummaryReportHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
					subjectMap.putAll(tempMap);
					subjectList.addAll(tempMap.values());
					subjectMapList.put(classSchemewise.getClasses().getId(),
							subjectList);
				}
			}
		}
		attendanceSummaryEntryForm.setSubjectMap(subjectMap);
		attendanceSummaryEntryForm.setSubjectMapList(subjectMapList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTeacherViewClassAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm)form;
		attendanceSummaryReportForm.resetFields();
		try {
			setUserId(request,attendanceSummaryReportForm);
			setDataToForm(attendanceSummaryReportForm,request);
			HttpSession session = request.getSession();
			if(session.getAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+1) != null){
				session.removeAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+1);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			attendanceSummaryReportForm.setErrorMessage(msg);
			attendanceSummaryReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		/*Iterator<Integer> attendanceSummarySizeIterator = attendanceSummaryReportForm.getAttendanceSummarySizeList().iterator();
		while (attendanceSummarySizeIterator.hasNext()) {
			//Integer integer = (Integer) attendanceSummarySizeIterator.next();
			session.removeAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+1);
		}*/
		return mapping.findForward(CMSConstants.INIT_TEACHER_VIEW_ATTENDANCE_SUMMARY);
	}
	public ActionForward submitTeacherViewClassAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAttendSummaryReport..");	
		AttendanceSummaryReportForm attendanceSummaryReportForm = (AttendanceSummaryReportForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		int i=0;
		 ActionMessages errors = attendanceSummaryReportForm.validate(mapping, request);
		if (errors.isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			if(session.getAttribute("attendanceSummaryReport1")==null){
			Map<Integer,List<AttendanceSummaryReportTO>> candidateSearch;
			setSubjectMapNew(attendanceSummaryReportForm);
			candidateSearch = AttendanceSummaryReportHandler.getInstance().getTeacherViewAttendance(attendanceSummaryReportForm);
			session.setAttribute("isActivity", attendanceSummaryReportForm.getIsActivity());
			if(candidateSearch.isEmpty()){	
				message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				setDataToForm(attendanceSummaryReportForm, request);
				return mapping
						.findForward(CMSConstants.INIT_TEACHER_VIEW_ATTENDANCE_SUMMARY);
			} else {
				attendanceSummaryReportForm.setAttnType("teacherView");
				session.setAttribute("AttenType", attendanceSummaryReportForm.getAttnType());
				List<OrganizationTO> organizationDetails = OrganizationHandler
							.getInstance().getOrganizationDetails();
					if (organizationDetails != null
							&& !organizationDetails.isEmpty()) {
						OrganizationTO organizationTO = organizationDetails
								.get(0);
						if (organizationTO != null
								&& organizationTO.getOrganizationName() != null) {
							attendanceSummaryReportForm
									.setOrganisationName(organizationTO
											.getOrganizationName());
						}
					}
				Iterator<List<AttendanceSummaryReportTO>> classCandidateListIterator = candidateSearch.values().iterator();
				int size =0;
				attendanceSummaryReportForm.getAttendanceSummarySizeList().clear();
				while (classCandidateListIterator.hasNext()) {
					List<AttendanceSummaryReportTO> monthlyAttendenceTOList = (List<AttendanceSummaryReportTO>) classCandidateListIterator
							.next();
					if(!monthlyAttendenceTOList.isEmpty()) {
						size = size+1;
						attendanceSummaryReportForm.getAttendanceSummarySizeList().add(size);
						Collections.sort(monthlyAttendenceTOList);

						session.setAttribute(CMSConstants.ATTENDENCE_SUMMARY_REPORT+size,monthlyAttendenceTOList );
					}
				}
			}
			
			}
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				attendanceSummaryReportForm.setErrorMessage(msg);
				attendanceSummaryReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			attendanceSummaryReportForm.setAcademicYear(null);
			setDataToForm(attendanceSummaryReportForm, request);
			return mapping.findForward(CMSConstants.INIT_TEACHER_VIEW_ATTENDANCE_SUMMARY);
		}
		log.info("Exit submitAttendSummaryReport..");
		if(attendanceSummaryReportForm.isKjcReport()){
			return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_SUMMARY_REPORT_KJC);
		}else{
			return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_SUMMARY_REPORT);
		}
	}
 public void getSubjectsByClassId(AttendanceSummaryReportForm attendanceSummaryReportForm){
	 Set<Integer> classesIdsSet = new HashSet<Integer>();
		for (String str : attendanceSummaryReportForm.getClassesName()) {
			classesIdsSet.add(Integer.parseInt(str));
		}
 }
 
 
 public void setDataToForm(AttendanceSummaryReportForm attendanceSummaryReportForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm..");	
		    //setting programList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
		    //setting AttendanceTypeList to Request
			List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
			request.setAttribute("attendanceTypeList", attendanceTypeList);
			//setting the subject maps when errors are occured
			
			// Getting the class map current academic year.
			Map<Integer, String> classMap = setClassMapTeacherView(attendanceSummaryReportForm);
			attendanceSummaryReportForm.setClassMap(classMap);
			attendanceSummaryReportForm.setClassesName(attendanceSummaryReportForm.getClassesName());
			
			if(attendanceSummaryReportForm.getClassesName()!=null && attendanceSummaryReportForm.getClassesName().length>0){
				Map<Integer, String> subjectMap = setSubjectMapForm(attendanceSummaryReportForm);
				request.setAttribute("subjectMaps", subjectMap);
			}
			//setting the current Academic year to the form
			if(attendanceSummaryReportForm.getAcademicYear()==null){
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				int year1=year+1;
				if(year>0){
				attendanceSummaryReportForm.setAcademicYear(Integer.toString(year));
				String academicYear=year+"-"+year1;
				attendanceSummaryReportForm.setAcademicYear1(academicYear);
				}
			}
			log.info("Exit setRequiredDataToForm..");	
	}
	private Map<Integer, String> setSubjectMapForm(AttendanceSummaryReportForm attendanceSummaryReportForm) {
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = attendanceSummaryReportForm.getClassesName();
			int count = 0;
			String sub[] = new String[100];
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			
			Map<Integer, String> tempMap = AttendanceSummaryReportHandler.getInstance().getSubjectByClassIDs(classesIdsSet);
			
			subjectsMap.putAll(tempMap);
			if(subjectsMap!=null && !subjectsMap.isEmpty())
			{
				Iterator entries = subjectsMap.entrySet().iterator();
				while (entries.hasNext()) {
				    Map.Entry entry = (Map.Entry) entries.next();
				    Integer key = (Integer)entry.getKey();
				
					sub[count]=String.valueOf(key);
					count=count+1;
			}
				attendanceSummaryReportForm.setSubjects(sub);
			}
				//sub[count]=String.valueOf(subjectGroupSubjects.getSubject().getId());
			//	count=count+1;
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return subjectsMap;
	}
}
