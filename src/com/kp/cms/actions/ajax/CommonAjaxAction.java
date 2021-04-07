package com.kp.cms.actions.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.employee.EmployeeApplyLeaveAction;
import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import com.kp.cms.handlers.admission.StudentSpecialAchivementsHandlers;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceSummaryReportHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.employee.EmpEventVacationHandler;
import com.kp.cms.handlers.employee.EmployeeApplyLeaveHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandlerNew;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamMidsemExemptionHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.ValuationScheduleHandler;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HostelDisciplinaryDetailsHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelLeaveHandler;
import com.kp.cms.handlers.reports.ViewInternalMarkHandler;
import com.kp.cms.handlers.timetable.TimeTableForClassHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.coursesToPrint;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ValuationScheduleTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.transactions.timetable.ITimeTableForClassTransaction;
import com.kp.cms.transactionsimpl.admin.StudentSupportRequestTransImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HlAdmissionImpl;
import com.kp.cms.transactionsimpl.hostel.HlStudentCheckInImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.transactionsimpl.timetable.TimeTableForClassTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class CommonAjaxAction extends CommonAjaxExamAction {
	private static final Log log = LogFactory.getLog(CommonAjaxAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 * @throws Exception
	 */
	public ActionForward getStatesByCountry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
		if (baseActionForm.getCountryId() != null) {
			try {
				cid = Integer.parseInt(baseActionForm.getCountryId());
				// The below map contains key as id and value as name of state.
				stateMap = CommonAjaxHandler.getInstance()
						.getStatesByCountryId(cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), stateMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, stateMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return sets cityMap to request <key,value> Ex:<1,bangalore> <2,tumkur>
	 * @throws Exception
	 */
	public ActionForward getCitiesByState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> cityMap = new HashMap<Integer, String>();
		if (baseActionForm.getCountryId() != null) {
			try {
				cid = Integer.parseInt(baseActionForm.getCountryId());
				// The below map contains key as id and value as name of state.
				cityMap = CommonAjaxHandler.getInstance().getCitiesByStateId(
						cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, cityMap);
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), cityMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getCitiesByCountry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> cityMap = new HashMap<Integer, String>();
		if (baseActionForm.getCountryId() != null) {
			try {
				cid = Integer.parseInt(baseActionForm.getCountryId());
				// The below map contains key as id and value as name of state.
				cityMap = CommonAjaxHandler.getInstance().getCitiesByCountry(
						cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, cityMap);
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), cityMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the programMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getProgramsByProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int ptid;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				// ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
				ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
				// The below map contains key as id and value as name of state.
				programMap = CommonAjaxHandler.getInstance()
						.getProgramsByProgramType(ptid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * retrieves only open programs for application
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the programMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getApplnProgramsByProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int ptid;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				// ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
				ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
				// The below map contains key as id and value as name of state.
				programMap = CommonAjaxHandler.getInstance()
						.getApplnProgramsByProgramType(ptid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getActivityByAttendenceType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		if (baseActionForm.getAttendanceTypeids() != null
				&& baseActionForm.getAttendanceTypeids().length() != 0) {
			try {
				String attendanceType[] = baseActionForm.getAttendanceTypeids()
						.split(",");

				Set appNoSet = new HashSet();
				for (int i = 0; i < attendanceType.length; i++) {
					appNoSet.add(Integer.parseInt(attendanceType[i]));
				}
				// The below map contains key as id and value as name of state.
				activityMap = CommonAjaxHandler.getInstance()
						.getActivityByAttendenceType(appNoSet);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), activityMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, activityMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getCourseByProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of state.
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
						pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward interviewTypeByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map collegeMap = new HashMap();
		if (baseActionForm.getCourseId() != null) {
			try {
				if (baseActionForm.getCourseId() != null
						&& !StringUtils.isEmpty(baseActionForm.getCourseId())
						&& StringUtils.isNumeric(baseActionForm.getCourseId())

						&& baseActionForm.getYear() != null
						&& !StringUtils.isEmpty(baseActionForm.getYear())
						&& StringUtils.isNumeric(baseActionForm.getYear()))
					uid = Integer.parseInt(baseActionForm.getCourseId());
				int year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of state.
				collegeMap = CommonAjaxHandler.getInstance()
						.getInterviewTypeByCourse(uid, year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward interviewTypeByProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map collegeMap = new HashMap();
		if (baseActionForm.getProgramId() != null) {
			try {
				if (baseActionForm.getProgramId() != null
						&& !StringUtils.isEmpty(baseActionForm.getProgramId())
						&& StringUtils.isNumeric(baseActionForm.getProgramId())

						&& baseActionForm.getYear() != null
						&& !StringUtils.isEmpty(baseActionForm.getYear())
						&& StringUtils.isNumeric(baseActionForm.getYear()))
					uid = Integer.parseInt(baseActionForm.getProgramId());
				int year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of state.
				collegeMap = CommonAjaxHandler.getInstance()
						.getInterviewTypeByProgram(uid, year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward interviewSubroundsByInterviewType(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map<Integer, String> interviewSubroundsMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getInterviewTypeId() != null
					&& !StringUtils
							.isEmpty(baseActionForm.getInterviewTypeId())
					&& StringUtils.isNumeric(baseActionForm
							.getInterviewTypeId()))
				uid = Integer.parseInt(baseActionForm.getInterviewTypeId());
			// The below map contains key as id and value as name of state.
			interviewSubroundsMap = CommonAjaxHandler.getInstance()
					.getInterviewSubroundsByInterviewType(uid);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, interviewSubroundsMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), interviewSubroundsMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward interviewSubroundsByApplicationId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		int applicationId = 0;
		Map<Integer, String> interviewSubroundsMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getInterviewTypeId() != null
					&& !StringUtils
							.isEmpty(baseActionForm.getInterviewTypeId())
					&& StringUtils.isNumeric(baseActionForm
							.getInterviewTypeId()))
				uid = Integer.parseInt(baseActionForm.getInterviewTypeId());
			if (request.getParameter("applicationId") != null) {
				applicationId = Integer.parseInt(request.getParameter(
						"applicationId"));
			}
			// The below map contains key as id and value as name of state.
			interviewSubroundsMap = CommonAjaxHandler.getInstance()
					.getInterviewSubroundsByApplicationId(uid, applicationId);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, interviewSubroundsMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), interviewSubroundsMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getCollegeByUniversity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map<Integer, String> collegeMap = new HashMap<Integer, String>();
		if (baseActionForm.getUniversityId() != null) {
			try {
				if (baseActionForm.getUniversityId() != null
						&& !StringUtils.isEmpty(baseActionForm
								.getUniversityId())
						&& StringUtils.isNumeric(baseActionForm
								.getUniversityId()))
					uid = Integer.parseInt(baseActionForm.getUniversityId());
				// The below map contains key as id and value as name of state.
				collegeMap = CommonAjaxHandler.getInstance()
						.getCollegeByUniversity(uid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getSubReligionByReligion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid = 0;
		Map<Integer, String> subreligionMap = new HashMap<Integer, String>();
		if (baseActionForm.getReligionId() != null) {
			try {
				if (StringUtils.isNumeric(baseActionForm.getReligionId())) {
					pid = Integer.parseInt(baseActionForm.getReligionId());
					// The below map contains key as id and value as name of
					// state.
					subreligionMap = CommonAjaxHandler.getInstance()
							.getSubReligionByReligion(pid);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subreligionMap);
		if (baseActionForm.getPropertyName() != null) {
			if ((baseActionForm.getCollectionMap().get(
					baseActionForm.getPropertyName()) != null)) {
				baseActionForm.getCollectionMap().remove(
						baseActionForm.getPropertyName());
			}
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subreligionMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getSubjectGroupsByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int courseId;
		Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null) {
			try {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				subjectGroupMap = CommonAjaxHandler.getInstance()
						.getSubjectGroupsByCourse(courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectGroupMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getCandidateCount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		int cid = 0;
		int year;
		int examCenterId = 0;
		ArrayList<Integer> interviewTypeList = new ArrayList<Integer>();
		int noofcandidates = 0;
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getYear() != null
				&& baseActionForm.getInterviewTypeId() != null) {
			try {
				String[] str = baseActionForm.getInterviewTypeId().split(",");
				if (str.length >= 1) {
					for (int i = 0; i < str.length; i++) {
						interviewTypeList.add(Integer.parseInt(str[i]));

					}
				}
				// else {
				// interviewTypeList.add(Integer.parseInt(str[0]));
				// }
				year = Integer.parseInt(baseActionForm.getYear());
				if (baseActionForm.getCourseId().length() > 0) {
					cid = Integer.parseInt(baseActionForm.getCourseId());
				}
				pid = Integer.parseInt(baseActionForm.getProgramId());
				if (baseActionForm.getExamCenterId() != null
						&& !baseActionForm.getExamCenterId().trim().isEmpty()) {
					examCenterId = Integer.parseInt(baseActionForm
							.getExamCenterId());
				}
				// The below map contains key as id and value as name of state.
				noofcandidates = CommonAjaxHandler.getInstance()
						.getCandidateCount(cid, pid, year, interviewTypeList,
								examCenterId);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
			}
		}

		request.setAttribute("noofcandidates", noofcandidates);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getMandatoryFieldsByAttendanceType(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int attendanceTypeId;
		List<AttendanceTypeMandatoryTO> mandatoryList = new ArrayList<AttendanceTypeMandatoryTO>();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		if (baseActionForm.getAttendanceTypeId() != null) {
			try {
				attendanceTypeId = Integer.parseInt(baseActionForm
						.getAttendanceTypeId());
				// The below map contains key as id and value as name of state.
				mandatoryList = CommonAjaxHandler.getInstance()
						.getMandatoryByAttenadnce(attendanceTypeId);

				Set<Integer> set = new HashSet<Integer>();
				set.add(attendanceTypeId);
				activityMap = CommonAjaxHandler.getInstance()
						.getActivityByAttendenceType(set);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), activityMap);
		}
		request.setAttribute("lists", mandatoryList);
		request.setAttribute(CMSConstants.OPTION_MAP, activityMap);
		return mapping.findForward("ajaxResponseForMandatory");
	}

	public ActionForward getSemistersByYearAndCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		int courseId;
		Map<Integer, Integer> semisterMap = new HashMap<Integer, Integer>();
		if (baseActionForm.getYear() != null) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				semisterMap = CommonAjaxHandler.getInstance()
						.getSemistersByYearAndCourse(year, courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, semisterMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), semisterMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSemistersByYearAndCourseScheme(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		int courseId;
		Map<Integer, Integer> semisterMap = new HashMap<Integer, Integer>();
		if (baseActionForm.getYear() != null) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				semisterMap = CommonAjaxHandler.getInstance()
						.getSemistersByYearAndCourseScheme(year, courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, semisterMap);
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), semisterMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectGroupByYearAndCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		int courseId;
		int semister;
		Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
		if (baseActionForm.getSemister() != null) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				semister = Integer.parseInt(baseActionForm.getSemister());
				// The below map contains key as id and value as name of state.
				subjectGroupMap = CommonAjaxHandler.getInstance()
						.getSubjectGroupsByYearAndCourse(year, courseId,
								semister);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectGroupMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectGroupMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the classMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getClassesByYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByYear(
						year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClassesByYearFromGeneratedTimeTable(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByYearFromGeneratedTimeTable(
						year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsPeriodsBatchForClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		// Map<Integer,String> batchMap = new HashMap<Integer,String>();
		Map<Integer, String> periodMap = new HashMap<Integer, String>();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");

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
					subjectMap.putAll(tempMap);
				}
			}

			periodMap = CommonAjaxHandler.getInstance()
					.getPeriodByClassSchemewiseId(classesIdsSet);

			// batchMap =
			// CommonAjaxHandler.getInstance().getBatchesByClasses(classesIdsSet);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("periodOptionMap", periodMap);
		request.setAttribute("subjectOptionMap", subjectMap);
		// request.setAttribute("batchOptionMap", batchMap);
		request.setAttribute("timeTable",false);
		return mapping.findForward("ajaxResponseForPeriodSubjectBatch");
	}

	public ActionForward getPeriodByClassSchemewiseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> periodMap = CommonAjaxHandler
				.getInstance()
				.getPeriodByClassSchemewiseId(
						Integer.parseInt(baseActionForm.getClassSchemewiseId()));
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), periodMap);

		request.setAttribute(CMSConstants.OPTION_MAP, periodMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getPeriodByClassSchemewiseIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> periodMap = new HashMap<Integer, String>();
		if (baseActionForm.getClassSchemewiseId() != null) {
			String[] arrayList = baseActionForm.getClassSchemewiseId().split(
					",");
			periodMap = CommonAjaxHandler.getInstance()
					.getPeriodByClassSchemewiseIds(arrayList);
			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), periodMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, periodMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsByMultipleClasses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");

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

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectsMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectsMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getClassSchemewiseId() != null
					&& baseActionForm.getClassSchemewiseId().length() != 0) {
				ClassSchemewise classSchemewise = CommonAjaxHandler
						.getInstance().getDetailsonClassSchemewiseId(
								Integer.parseInt(baseActionForm
										.getClassSchemewiseId()));
				if (classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year = classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse()
							.getId();
					int term = classSchemewise.getClasses().getTermNumber();
					subjectMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	

	public ActionForward getBatchesBySubjects(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			int subjectId = 0;
			if (baseActionForm.getSubjectId() != null
					&& baseActionForm.getSubjectId().length() != 0)
				subjectId = Integer.parseInt(baseActionForm.getSubjectId());

			subjectMap = CommonAjaxHandler.getInstance()
					.getBatchesBySubjectAndClassIds(subjectId, classesIdsSet);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = CommonAjaxHandler.getInstance()
				.getSubjectDetailsByCourse(
						Integer.parseInt(baseActionForm.getCourseId()));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}


	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUpdatedPreferences(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HttpSession session = request.getSession();
		List<CourseTO> prefcourses = new ArrayList<CourseTO>();
		List<CourseTO> templist = null;
		if (session != null) {
			templist = (List<CourseTO>) session
					.getAttribute(CMSConstants.COURSE_PREFERENCES);
			prefcourses.addAll(templist);
		}
		Map<Integer, String> prefMap = CommonAjaxHandler.getInstance()
				.getUpdatedPreferenceList(prefcourses,
						Integer.parseInt(baseActionForm.getSecPrefId()));

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), prefMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, prefMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getHlGroupsByHostel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> groupMap = CommonAjaxHandler.getInstance()
				.getHlGroupNameByHostel(
						Integer.parseInt(baseActionForm.getHostelId()));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), groupMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, groupMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getRoomTypeByHostel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance()
				.getRoomTypeByHostel(
						Integer.parseInt(baseActionForm.getHostelId()));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomTypeMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomTypeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getRoomsByHostel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance()
				.getRoomsByHostel(
						Integer.parseInt(baseActionForm.getHostelId()));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomTypeMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomTypeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the EmployeeMap <key,value> based on the department
	 * @throws Exception
	 */
	public ActionForward getEmployeesByDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int departmentId;
		Map<Integer, String> employeeMap = new HashMap<Integer, String>();
		if (baseActionForm.getDepartmentId() != null
				&& baseActionForm.getDepartmentId().length() != 0) {
			try {
				departmentId = Integer.parseInt(baseActionForm
						.getDepartmentId());
				// The below map contains key as id and value as name of class.
				employeeMap = CommonAjaxHandler.getInstance()
						.getEmployeesByDepartment(departmentId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), employeeMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, employeeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the classMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getClassesByProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int programId;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				programId = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByProgram(
						programId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward interviewTypeByCourseWithoutYear(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map collegeMap = new HashMap();
		if (baseActionForm.getCourseId() != null) {
			try {
				if (baseActionForm.getCourseId() != null
						&& !StringUtils.isEmpty(baseActionForm.getCourseId())
						&& StringUtils.isNumeric(baseActionForm.getCourseId()))

					uid = Integer.parseInt(baseActionForm.getCourseId());
				// int year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of state.
				collegeMap = CommonAjaxHandler.getInstance()
						.getInterviewTypeByCourseWithoutYear(uid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward interviewTypeByProgramWithoutYear(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int uid = 0;
		Map collegeMap = new HashMap();
		if (baseActionForm.getProgramId() != null) {
			try {
				if (baseActionForm.getProgramId() != null
						&& !StringUtils.isEmpty(baseActionForm.getProgramId())
						&& StringUtils.isNumeric(baseActionForm.getProgramId()))
					uid = Integer.parseInt(baseActionForm.getProgramId());

				// The below map contains key as id and value as name of state.
				collegeMap = CommonAjaxHandler.getInstance()
						.getInterviewTypeByProgramWithoutYear(uid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the classMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getClassesByProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int programTypeId;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				programTypeId = Integer.parseInt(baseActionForm
						.getProgramTypeId());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance()
						.getClassesByProgramTypeId(programTypeId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getStudentName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int optionNo;
		String appRegRollno = "";
		String academicYear;
		String name = "";

		if (baseActionForm.getOptionNo() != null
				&& baseActionForm.getAppRegRollno() != null
				&& baseActionForm.getAcademicYear() != null) {
			try {
				optionNo = Integer.parseInt(baseActionForm.getOptionNo());
				// nameValidate(baseActionForm.getAppRegRollno().trim());
				if (baseActionForm.getAppRegRollno().trim() != null
						&& !baseActionForm.getAppRegRollno().isEmpty()) {
					appRegRollno = baseActionForm.getAppRegRollno().trim();
					if (optionNo == 1 && !StringUtils.isNumeric(appRegRollno)) {
						return mapping
								.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
					}
				}

				academicYear = baseActionForm.getAcademicYear();
				if (optionNo != 0 && !StringUtils.isEmpty(appRegRollno)) {
					name = CommonAjaxHandler.getInstance().getStudentName(
							optionNo, appRegRollno, academicYear,request);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (name != null && !name.equals(""))
		{
			name=name.replaceAll("'","");
			request.setAttribute("name", name.trim());
		}
		else
			request.setAttribute("name", name);

		return mapping.findForward("ajaxResponseForGetStudent");

	}

	public ActionForward getAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// CashCollectionForm collectionForm = (CashCollectionForm)form;
		BaseActionForm baseActionForm = (BaseActionForm) form;
		int accoId;
		PcAccountHead pcAccountHead = null;

		if (baseActionForm.getAccoId() != 0) {
			try {
				accoId = baseActionForm.getAccoId();
				pcAccountHead = CommonAjaxHandler.getInstance().getAmount(
						accoId);
				if (pcAccountHead != null) {
					if (pcAccountHead.getAmount() != null) {
						baseActionForm.setAmount(String.valueOf(pcAccountHead
								.getAmount().doubleValue()));
						request.setAttribute("amount", String
								.valueOf(pcAccountHead.getAmount()
										.doubleValue()));
						request.getSession().setAttribute("baseActionForm",
								baseActionForm);
					} else {
						request.setAttribute("amount", "0");
						return mapping.findForward("ajaxResponseForGetAmount");
					}

					if (pcAccountHead.getIsFixedAmount() != null
							&& pcAccountHead.getIsFixedAmount()) {
						request.setAttribute("isFixed", "true");
					} else {
						request.setAttribute("isFixed", "false");
					}

				} else {
					request.setAttribute("isFixed", "false");
					request.setAttribute("amount", "0");
					return mapping.findForward("ajaxResponseForGetAmount");
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		// collectionForm.setAccId(Integer.valueOf(accoId).toString());
		// collectionForm.setAmount(baseActionForm.getAmount());
		return mapping.findForward("ajaxResponseForGetAmount");
	}

	// for Hostel Admin Message
	public ActionForward getRoomsMapByHostelId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomsMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getHostelId() != null
					&& !baseActionForm.getHostelId().equals("")) {
				roomsMap = CommonAjaxHandler.getInstance()
						.getHostelRoomsMapByHostelId(
								baseActionForm.getHostelId());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomsMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomsMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getRoomsByFloors(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomMap = CommonAjaxHandler.getInstance()
				.getRoomsByFloors(
						Integer.parseInt(baseActionForm.getHostelId()),
						Integer.parseInt(baseActionForm.getFloorNo()),
						Integer.parseInt(baseActionForm.getRoomId()));

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getBedByRoomId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> bedMap = CommonAjaxHandler.getInstance()
				.getBedByRoomId(Integer.parseInt(baseActionForm.getRoomId()));

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), bedMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, bedMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getRoomTypesByHostel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		// HostelStatusInfoForm hostelStatusInfoForm=(HostelStatusInfoForm)form;
		Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance()
				.getRoomTypesByHostel(
						Integer.parseInt(baseActionForm.getHostelId()));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getProgramName(), roomTypeMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomTypeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward getExamCenterByProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> examCenterMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of state.
				examCenterMap = CommonAjaxHandler.getInstance()
						.getExamCenterForProgram(pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examCenterMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examCenterMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * @author jboss
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the classMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getClassesByTeacher(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int teacherId;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		try {
			teacherId = Integer.parseInt(request.getParameter("teacherId"));
			String year = request.getParameter("year");
			// The below map contains key as id and value as name of class.
			classMap = CommonAjaxHandler.getInstance().getClassesByTeacher(
					teacherId, year);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * @author jboss
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsPeriodsBatchForTeacherAndClass(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		//commented by mehaboob subjectMap
		//Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		Map<String, String> subjectMap = new LinkedHashMap<String, String>();
        HttpSession session=request.getSession();
		Map<Integer, String> periodMap = new HashMap<Integer, String>();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");

			int teacherId = Integer.parseInt(request.getParameter("teacherId"));
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
         //commented by mehaboob there is no use of this code START
			
			
			/*List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
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
					int term = classSchemewise.getClasses().getTermNumber();*/
					Map<String, String> tempMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYearTeacher(teacherId, selectedClasses,session,false);
			
			  //code added by mehaboob start	
				/*	Map<String, String> tempMap = CommonAjaxHandler.getInstance()
					.getSubjectByCourseIdTermYearTeacherAndCheckIsCommonSubject(teacherId, selectedClasses,session);*/
			//end
					subjectMap.putAll(tempMap);
		/*		}
			}*/
		//end			
			Set<Integer> temClassIdsSet=new HashSet<Integer>();
			temClassIdsSet.add(Integer.parseInt(selectedClasses[0]));
			periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(temClassIdsSet);

			// batchMap =
			// CommonAjaxHandler.getInstance().getBatchesByClasses(classesIdsSet);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("periodOptionMap", periodMap);
		request.setAttribute("subjectOptionMap", subjectMap);
		request.setAttribute("timeTable",false);
		return mapping.findForward("ajaxResponseForPeriodSubjectBatch");
	}

	public ActionForward getProgramsByAdmitedYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				programMap = CommonAjaxHandler.getInstance()
						.getProgramsByAdmitedYear(year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClassesByYearForMuliSelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance()
						.getClassesByYearForMuliSelect(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getRoomsByClassSchemewiseIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomMap = new HashMap<Integer, String>();
		if (baseActionForm.getClassId() != null
				&& baseActionForm.getClassId().length() != 0) {
			try {
				String[] classes = baseActionForm.getClassId().split(",");
				ArrayList<Integer> classeIds = new ArrayList<Integer>();
				for (String string : classes) {
					if (string != null && string.trim().length() > 0) {
						classeIds.add(Integer.parseInt(string));
					}
				}
				roomMap = CommonAjaxHandler.getInstance()
						.getRoomsByClassSchemewiseIds(classeIds);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getEducationByQualificationId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int qualificationId;
		Map<Integer, String> educationMap = new HashMap<Integer, String>();
		if (baseActionForm.getQualificationId() != null
				&& baseActionForm.getQualificationId().length() != 0) {
			try {
				qualificationId = Integer.parseInt(baseActionForm
						.getQualificationId());
				// The below map contains key as id and value as name of class.
				educationMap = CommonAjaxHandler.getInstance()
						.getEducationByQualificationId(qualificationId);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), educationMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, educationMap);
		request.setAttribute("educationMap", educationMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSubjectsByCourseTermYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getAcademicYear()!= null && 
				baseActionForm.getAcademicYear().length() != 0 &&
				baseActionForm.getCourseId()!=null &&
				baseActionForm.getCourseId().length()!=0 &&
				baseActionForm.getSchemeId()!=null &&
				baseActionForm.getSchemeId().length()!=0){
				if(baseActionForm.getSchemeId().contains("_")){
					
					String[] schemeId_schemeNo=baseActionForm.getSchemeId().split("_");
					baseActionForm.setSchemeId(schemeId_schemeNo[1]);
					
				}
					int year = Integer.parseInt(baseActionForm.getAcademicYear());
					int courseId = Integer.parseInt(baseActionForm.getCourseId());
					int term = Integer.parseInt(baseActionForm.getSchemeId());
					subjectMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId, year, term);
				}
			}
			catch (Exception e) {
				log.debug(e.getMessage());
			}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getSpecializationByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int courseId;
		Map<Integer, String> specializationMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null) {
			try {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				specializationMap = CommonAjaxHandler.getInstance()
						.getSpecializationByCourse(courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, specializationMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchesByActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			int activityId = 0;
			if (baseActionForm.getActivityId() != null
					&& baseActionForm.getActivityId().length() != 0)
				activityId = Integer.parseInt(baseActionForm.getActivityId());

			subjectMap = CommonAjaxHandler.getInstance()
					.getBatchesByActivityAndClassIds(activityId, classesIdsSet);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getClassesByCourseAndYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> rejoinClassMap = new HashMap<Integer, String>();
		try {
			int courseId = 0;
			int year = 0;
			if(baseActionForm.getCourseId()!= null && baseActionForm.getYear()!= null){
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				year = Integer.parseInt(baseActionForm.getYear());
			}
			rejoinClassMap = CommonAjaxHandler.getInstance().getClassesBySelectedCourse(courseId, year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), rejoinClassMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, rejoinClassMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSectionsByCourseAndSemester(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> sectionMap = new HashMap<String, String>();
		try {
			int courseId = 0;
			int year=0;
			if(baseActionForm.getCourseId()!= null && baseActionForm.getYear()!= null)
			{
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				year = Integer.parseInt(baseActionForm.getYear());
			}
			sectionMap = CommonAjaxHandler.getInstance().getSectionsByCourseAndSemester(courseId, year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), sectionMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, sectionMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getCourseByPrograminOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of state.
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgramInOrder(
						pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	public ActionForward getCountForSelectedInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		int cid = 0;
		int year;
		int examCenterId = 0;
		String stime="";
		String etime="";
		ArrayList<Integer> interviewTypeList = new ArrayList<Integer>();
		List<Date> dateList=new ArrayList<Date>();
		
		int count = 0;
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getYear() != null
				&& baseActionForm.getInterviewTypeId() != null) {
			try {
				String[] str = baseActionForm.getInterviewTypeId().split(",");
				if (str.length >= 1) {
					for (int i = 0; i < str.length; i++) {
						interviewTypeList.add(Integer.parseInt(str[i]));
					}
				}
				year = Integer.parseInt(baseActionForm.getYear());
				if (baseActionForm.getCourseId().length() > 0) {
					cid = Integer.parseInt(baseActionForm.getCourseId());
				}
				pid = Integer.parseInt(baseActionForm.getProgramId());
				if (baseActionForm.getExamCenterId() != null
						&& !baseActionForm.getExamCenterId().trim().isEmpty()) {
					examCenterId = Integer.parseInt(baseActionForm
							.getExamCenterId());
				}
				
				stime=baseActionForm.getStime();
				etime=baseActionForm.getEtime();
				String[] dtr = baseActionForm.getDate().split(",");
				if (dtr.length >= 1) {
					for (int i = 0; i < dtr.length; i++) {
						dateList.add(CommonUtil.ConvertStringToSQLDate(dtr[i]));
					}
				}
				// The below map contains key as id and value as name of state.
				count = CommonAjaxHandler.getInstance().getCount(cid, pid, year, interviewTypeList,examCenterId, stime, etime, dateList);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
			}
		}

		request.setAttribute("noofcandidates", count);
		return mapping.findForward("ajaxResponseForInterview");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoomsByFloor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomMap = CommonAjaxHandler.getInstance().getRoomsByFloor(Integer.parseInt(baseActionForm.getHostelId()),
						Integer.parseInt(baseActionForm.getFloorNo()));

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), roomMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward interviewSubroundsByInterviewTypeForMultiselect(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> interviewSubroundsMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getInterviewTypeId() != null){
				String[] arrayList = baseActionForm.getInterviewTypeId().split(",");	
			// The below map contains key as id and value as name of state.
			interviewSubroundsMap = CommonAjaxHandler.getInstance()
					.getInterviewSubroundsByInterviewTypeForMultiSelect(arrayList);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, interviewSubroundsMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), interviewSubroundsMap);

		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getRegisterNumber(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String registerNo = " ";
		try 
		{
			registerNo = CommonAjaxHandler.getInstance().getRegisterNo(baseActionForm.getIpAddress());
			if(registerNo.startsWith("9"))
				registerNo="0"+registerNo;
		}
		catch (Exception e) 
		{
				e.printStackTrace();
				log.debug(e.getMessage());
		}
		

		request.setAttribute("registerNo", registerNo);
		return mapping.findForward("ajaxResponseForRegisterNo");
	}
	
	public ActionForward getEmployeeCodeName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String eCodeName;
		Map<Integer, String> employeeMap = new HashMap<Integer, String>();
		if (baseActionForm.getSchemeNo() != null
				&& baseActionForm.getSchemeNo().length() != 0) {
			try {
				eCodeName = baseActionForm.getSchemeNo();
				// The below map contains key as id and value as name of state.
				employeeMap = CommonAjaxHandler.getInstance().getEmployeeCodeName(eCodeName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), employeeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, employeeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getSubjectGroupByFeeAdditional(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
		if (baseActionForm.getFeeGroupId() != null) {
			try {
				//feeGroupId = Integer.parseInt(baseActionForm.getFeeGroupId());
				String[] arrayList = baseActionForm.getFeeGroupId().split(",");
				// The below map contains key as id and value as name of groupName.
				subjectGroupMap = CommonAjaxHandler.getInstance()
						.getSubjectGroupByFeeAdditionalForMultiSelect(arrayList);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectGroupMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 * @throws Exception
	 */
	public ActionForward getAttendanceSubjectsByRegisterNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String regNo;
		Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		if (baseActionForm.getRegNo() != null) {
			try {
				regNo =baseActionForm.getRegNo();
				// The below map contains key as id and value as name of Subject.
				subjectMap = CommonAjaxHandler.getInstance().getAttendanceSubjectsByRegisterNo(regNo);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getCoursesByYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				courseMap = CommonAjaxHandler.getInstance().getCoursesByYear(year);
						

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchesByActivity1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			int activityId = 0;
			if (baseActionForm.getActivityId() != null
					&& baseActionForm.getActivityId().length() != 0)
				activityId = Integer.parseInt(baseActionForm.getActivityId());

			subjectMap = CommonAjaxHandler.getInstance()
					.getBatchesByActivityAndClassIds1(activityId, classesIdsSet);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchesBySubjects1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		HttpSession session=request.getSession();
		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			//int subjectId = 0;
			//code changed by mehaboob start
			if (baseActionForm.getSubjectId() != null && !baseActionForm.getSubjectId().isEmpty())
				//subjectId = Integer.parseInt(baseActionForm.getSubjectId());
            //commented by mehaboob
/*			subjectMap = CommonAjaxHandler.getInstance()
					.getBatchesBySubjectAndClassIds1(subjectId, classesIdsSet);*/
				//added by mehaboob
			subjectMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds1(baseActionForm.getSubjectId(), classesIdsSet,session);
			
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmpDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			Employee employee = EmployeeApplyLeaveHandler.getInstance().getEmployeeDetails(baseActionForm.getEmpCode(),baseActionForm.getFingerPrintId());
			if(employee != null){
				if(employee.getFirstName() != null){
					request.setAttribute("EmpName",employee.getFirstName());
				}
				if(employee.getDepartment() != null && employee.getDepartment().getName() != null){
					request.setAttribute("DepartmentName",employee.getDepartment().getName());
				}else
					request.setAttribute("DepartmentName","-");
					
				if(employee.getDesignation() !=null && employee.getDesignation().getName() != null){
					request.setAttribute("DesignationName",employee.getDesignation().getName());
				}else
					request.setAttribute("DesignationName","-");
				if(employee.getId() !=0){
					request.setAttribute("empId",employee.getId());
				}else
					request.setAttribute("empId","0");
				if(employee.getEmptype() !=null && employee.getEmptype().getId() != 0){
					request.setAttribute("empTypeId",employee.getEmptype().getId());
				}else
					request.setAttribute("empTypeId","0");
			}else{
				request.setAttribute("msg","Not Valid Employee");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForEmpDetails");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the programMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getLeaveTypesForEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		try {
			int year=EmployeeApplyLeaveAction.getCurrentYearForGivenEmployee(baseActionForm.getDate(), baseActionForm.getType());
			List<LeaveTypeTo> leaveTypes=EmployeeApplyLeaveHandler.getInstance().getLeaveTypesForEmployee(baseActionForm.getEmployeeId(),baseActionForm.getOptionNo(),baseActionForm.getDate(),year);
			if(leaveTypes!=null && !leaveTypes.isEmpty()){
				Iterator<LeaveTypeTo> itr=leaveTypes.iterator();
				while (itr.hasNext()) {
					LeaveTypeTo to = (LeaveTypeTo) itr.next();
					programMap.put(to.getId(),to.getName());
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLeavesTakenInaMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getDate() != null && !baseActionForm.getDate().isEmpty() && baseActionForm.getType() != null && !baseActionForm.getType().isEmpty()){
				int year=EmployeeApplyLeaveAction.getCurrentYearForGivenEmployee(baseActionForm.getDate(), baseActionForm.getType());
				double leavesTaken=EmployeeApplyLeaveHandler.getInstance().getLeavesTaken(baseActionForm.getEmployeeId(),baseActionForm.getDate(),year);
				request.setAttribute("noofcandidates", leavesTaken);
			}else{
				request.setAttribute("noofcandidates", "employee type has to define");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForInterview");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkTeachingStaff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getEmployeeId()!=null && !baseActionForm.getEmployeeId().isEmpty()){
				boolean isTeachingStaff=CommonAjaxHandler.getInstance().checkTeachingStaff(baseActionForm.getEmployeeId());
				request.setAttribute("noofcandidates", isTeachingStaff);
			}else{
				request.setAttribute("noofcandidates", false);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForInterview");
	}
	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkGuestTeachingStaff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getGuestId()!=null && !baseActionForm.getGuestId().isEmpty()){
				boolean isTeachingStaff=CommonAjaxHandler.getInstance().checkGuestTeachingStaff(baseActionForm.getGuestId());
				request.setAttribute("noofcandidates", isTeachingStaff);
			}else{
				request.setAttribute("noofcandidates", false);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForInterview");
	}
	public ActionForward getDynamicFingerPrintId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		try {
			List<String> fingerPrintIds=CommonAjaxHandler.getInstance().getDynamicFingerPrintIds(baseActionForm.getFingerPrintId(), baseActionForm.getUserId());
			request.setAttribute("fingerPrints", fingerPrintIds);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForFingerPrints");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDeptWise(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getDepartmentId()!=null && !baseActionForm.getDepartmentId().isEmpty()){
				String deptNmae = baseActionForm.getDepartmentId();
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getFilteredTeacherDepartmentsName(deptNmae);
				request.setAttribute(CMSConstants.OPTION_MAP, teachersMap);
			}else{
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
				request.setAttribute(CMSConstants.OPTION_MAP, teachersMap);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPayScaleDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getPayscaleId()!=null && !baseActionForm.getPayscaleId().isEmpty()){
				String payScale=baseActionForm.getPayscaleId();
				String Scale =EmployeeInfoHandlerNew.getInstance().getPayscale(payScale);
				request.setAttribute(CMSConstants.OPTION_MAP, Scale);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForStringOptions");
	}

	public ActionForward getEmpLeaveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getEmpTypeId()!=null && !baseActionForm.getEmpTypeId().isEmpty()){
				String emptypeId=baseActionForm.getEmpTypeId();
				
				List<EmpLeaveAllotTO> empLeaveToList=EmployeeInfoHandlerNew.getInstance().getEmpLeaveList(emptypeId);
				request.setAttribute(CMSConstants.OPTION_MAP, empLeaveToList);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForEmpLeave");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectGroupsByProgramWise(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getProgramTypeId()!=null && !baseActionForm.getProgramTypeId().isEmpty() && baseActionForm.getProgramId() != null && !baseActionForm.getProgramId().isEmpty()){
				HttpSession session=request.getSession();
				session.setAttribute("tempProgramId", baseActionForm.getProgramId());
				session.setAttribute("tempProgramTypeId", baseActionForm.getProgramTypeId());
				List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(baseActionForm.getProgramTypeId(),baseActionForm.getProgramId());
				request.setAttribute(CMSConstants.OPTION_MAP, list);
				
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForList");
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getCourseByProgramForOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of state.
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgramForOnline( pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStreamWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			if(baseActionForm.getStreamId()!= null && !baseActionForm.getStreamId().isEmpty()){
				String StreamId=baseActionForm.getStreamId();
				String teachingStaff = baseActionForm.getTeachingStaff1();
				Map<Integer,String> deptMap = EmpEventVacationHandler.getInstance().getFilteredDepartmentsStreamNames(StreamId,teachingStaff);
				request.setAttribute(CMSConstants.OPTION_MAP, deptMap);
			}else{
				String teachingStaff = baseActionForm.getTeachingStaff1();
				Map<Integer,String> deptMap = EmpEventVacationHandler.getInstance().getDepartmentNames(teachingStaff);
				request.setAttribute(CMSConstants.OPTION_MAP, deptMap);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getPayscaleTeachingStaffDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getTeachingStaff1()!=null && !baseActionForm.getTeachingStaff1().isEmpty()){
				String teachingStaff=baseActionForm.getTeachingStaff1();
				Map<Integer,String> payscale =EmployeeInfoHandlerNew.getInstance().getPayscaleTeachingStaff(teachingStaff);
				request.setAttribute(CMSConstants.OPTION_MAP, payscale);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
/*	public ActionForward getProgramsListAcademicYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getAcademicYear()!=null && !baseActionForm.getAcademicYear().isEmpty()){
				
				String AcademicYear= baseActionForm.getAcademicYear();
				List<ExamDefinitionTO> list=ExamDefinitionHandler.getInstance().getListExamDefinitionBO_main(AcademicYear);
				request.setAttribute(CMSConstants.OPTION_MAP, list);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForExamList");
	}*/
	
	public ActionForward getCurrentTermNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			if(baseActionForm.getRegNo()!= null && !baseActionForm.getRegNo().isEmpty()){
				String termNumberMap = StudentSpecialAchivementsHandlers.getInstance().getCurrentTermNumbers(baseActionForm.getRegNo());
				request.setAttribute("noofcandidates", termNumberMap);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForInterview");
	}
	public ActionForward SearchYearByMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			if(baseActionForm.getMonth()!= null && !baseActionForm.getMonth().isEmpty()){
				String MonthId=baseActionForm.getMonth();
				Map<String, String> yearMap = AdmissionFormHandler.getInstance().getYearByMonth(MonthId);
				request.setAttribute(CMSConstants.OPTION_MAP, yearMap);
			}else{
				Map<String, String> yearMap = AdmissionFormHandler.getInstance().getYear();
				request.setAttribute(CMSConstants.OPTION_MAP, yearMap);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.ajax.CommonAjaxExamAction#getExamNameByExamType(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */	
	public ActionForward getExamNameByExamTypeAndYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String examType = baseActionForm.getExamType();
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		String currentExam = null;
		try {
			examMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(examType,Integer.parseInt(baseActionForm.getYear()));
			currentExam = examhandler.getCurrentExamName(examType);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (currentExam != null) {
			baseActionForm.setExamTypeId(Integer.parseInt(currentExam));
			request.setAttribute("subjectType", currentExam);
		} else {
			baseActionForm.setExamTypeId(0);
			request.setAttribute("subjectType", 0);
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}
	
	public ActionForward getClassByYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year=0;
		int userId=0;
		if (baseActionForm.getYear() != null && baseActionForm.getYear().length() != 0 && baseActionForm.getUserId()!=null && baseActionForm.getUserId().length()!=0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				userId = Integer.parseInt(baseActionForm.getUserId());
				Map<Integer, String> classMap = ViewInternalMarkHandler.getInstance().getClassByYear(year,userId);
				request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSubjectByClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year=0;
		int classId=0;
		int teacherId=0;
		if (baseActionForm.getYear() != null && baseActionForm.getYear().length() != 0 && baseActionForm.getClassId()!=null && baseActionForm.getClassId().length()!=0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				classId = Integer.parseInt(baseActionForm.getClassId());
				teacherId= Integer.parseInt(baseActionForm.getUserId());
				// The below map contains key as id and value as name of class.
				Map<Integer, String> subjectMap = ViewInternalMarkHandler.getInstance().getSubjectByClassYear(year, classId, teacherId);
				request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
		
		
		
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getCourseByYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				courseMap = CommonAjaxHandler.getInstance().getCourseByYear(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		/*if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		}*/
		baseActionForm.setCourseMap(courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward getClassesByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
			try {
				classMap = CommonAjaxHandler.getInstance().getClassesByCourse(Integer.parseInt(baseActionForm.getCourseId()),Integer.parseInt(baseActionForm.getYear()));

			} catch (Exception e) {
				log.debug(e.getMessage());
			}

		/*if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), classMap);
		}*/
			baseActionForm.setClassMap(classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * Method to get the group template list according to program id or template name
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGroupTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if( (baseActionForm.getProgramTypeId() != null && !baseActionForm.getProgramTypeId().isEmpty()) || 
					(baseActionForm.getProgramId() != null && !baseActionForm.getProgramId().isEmpty()) || 
					(baseActionForm.getTemplateName()!=null && !baseActionForm.getTemplateName().isEmpty())){
				List<GroupTemplateTO> list=TemplateHandler.getInstance().getGroupTemplateList(0, baseActionForm.getProgramTypeId(),baseActionForm.getProgramId(), baseActionForm.getTemplateName());
				request.setAttribute(CMSConstants.OPTION_MAP, list);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForGroupTemplateList");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPeriodsByTeacher(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		AttendanceTO to=null;
		try {
			String teacherId = request.getParameter("teacherId");
			String year = request.getParameter("year");
			String day=CommonUtil.getDayForADate(request.getParameter("date"));
			
			String attEntryDay = AttendanceEntryTransactionImpl.getInstance().getAttendanceEntryDay(baseActionForm);
			if(attEntryDay!=null  && !attEntryDay.isEmpty())//{
				day=attEntryDay;
			// The below map contains key as id and value as name of class.
			 to= CommonAjaxHandler.getInstance().getPeriodsByTeacher( teacherId, year ,day,request.getParameter("date"));

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), to.getPeriodMap());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, to.getPeriodMap());
		request.setAttribute("periodId",to.getPeriodId());
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsAndClassForPeriods(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		Map<Integer, String> batchMap = new LinkedHashMap<Integer, String>();
		Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
		AttendanceTO to=null;
		//session is added by mehabooob
		HttpSession session=request.getSession();
		try {
			String teacherId = request.getParameter("teacherId");
			String year = request.getParameter("year");
			String periods = request.getParameter("periodId");
			String day=CommonUtil.getDayForADate(request.getParameter("date"));
			String date=request.getParameter("date");
			String attEntryDay = AttendanceEntryTransactionImpl.getInstance().getAttendanceEntryDay(baseActionForm);
			if(attEntryDay!=null && !attEntryDay.isEmpty())
				day=attEntryDay;

			classMap=CommonAjaxHandler.getInstance().getClassesByPeriods(teacherId,year,periods,day,date);
			to=CommonAjaxHandler.getInstance().getSubjectsByPeriods(teacherId,year,periods,day,date,"",session);
			if(!to.getError()){
			
			if(to.getBatchId()!=0){
				batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacherId,year,periods,day,date);
				request.setAttribute("batchId",to.getBatchId());
			}//added by mehaboob start multiple Batches
			else if(to.getBatchList()!=null && !to.getBatchList().isEmpty()){
				batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacherId,year,periods,day,date);
				String batchId="";
				for (Integer integer : to.getBatchList()) {
					batchId=batchId+","+integer;
				}
				batchId=batchId.substring(1);
				request.setAttribute("batchId",batchId);
			}else{
				request.setAttribute("batchId","");
				
			}
			//end
			if(to.getAttendanceTypeId()!=0){
				Set<Integer> id=new HashSet<Integer>();
				id.add(to.getAttendanceTypeId());
				activityMap = CommonAjaxHandler.getInstance() .getActivityByAttendenceType(id);
			}
			request.setAttribute("periodOptionMap", classMap);
			request.setAttribute("subjectOptionMap", to.getSubjectMap());
			request.setAttribute("batchMap", batchMap);
			request.setAttribute("classId",to.getClassId());
			request.setAttribute("subjectId",to.getSubjectId());
			request.setAttribute("attendanceTypeId",to.getAttendanceTypeId());
			request.setAttribute("timeTable",true);
			request.setAttribute("activityId",to.getActivityId());
			request.setAttribute("activityMap",activityMap);
			
			// request.setAttribute("batchOptionMap", batchMap);
			}else{
				request.setAttribute("periodOptionMap", "");
				request.setAttribute("subjectOptionMap", "");
				request.setAttribute("batchMap", "");
				request.setAttribute("classId","");
				request.setAttribute("subjectId","");
				request.setAttribute("attendanceTypeId","");
				request.setAttribute("timeTable",true);
				request.setAttribute("activityId","");
				request.setAttribute("activityMap","");
				request.setAttribute("ErrorOccured","Time Table Setting Error.");
				
				// request.setAttribute("batchOptionMap", batchMap);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		

		return mapping.findForward("ajaxResponseForPeriodSubjectBatch");
	}
     
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubCategoryListByCategoryId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subCategoryMap =new HashMap<Integer, String>();
		try {
			String categoryId = baseActionForm.getItemCategoryId();
			// The below map contains key as id and value as name of invItemSubCategory.
			if(categoryId!=null && !categoryId.isEmpty())
			subCategoryMap = CommonAjaxHandler.getInstance().getItemSubCategory(categoryId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subCategoryMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subCategoryMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSpecializationBySubjectGroupWithoutCommSubjectAndSecondLang(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> specializationMap =new HashMap<Integer, String>();
		try {
			String subjectGroupId = baseActionForm.getSubjectGroupId();
			if(subjectGroupId!=null && !subjectGroupId.isEmpty())
				specializationMap = CommonAjaxHandler.getInstance().getSpecializationBySubGrpWithoutCommSubjAndSecndLang(subjectGroupId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), specializationMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, specializationMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInvLocationListByCampusId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> invLocationMap =new HashMap<Integer, String>();
		try {
			String campusId = baseActionForm.getCampusId();
			// The below map contains key as id and value as name of invItemSubCategory.
			if(campusId!=null && !campusId.isEmpty())
				invLocationMap = CommonAjaxHandler.getInstance().getInvLocation(campusId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), invLocationMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, invLocationMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNameByAppNo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getAppNo()!=null && !baseActionForm.getAppNo().isEmpty()){
				String name=CommonAjaxHandler.getInstance().getName(baseActionForm.getAppNo());
				request.setAttribute("name", name);
			}else{
				request.setAttribute("name", "");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseNameDisplay");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkReceivedThroughAvailability(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try {
			if(baseActionForm.getReceivedThrough() != null && !baseActionForm.getReceivedThrough().isEmpty()){
				boolean isAvailable=CommonAjaxHandler.getInstance().checkReceivedThrough(baseActionForm.getReceivedThrough(),request);
				request.setAttribute("isAvailable", isAvailable);
			}else{
				request.setAttribute("isAvailable", false);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForReceivedThrough");
	}
	
	public ActionForward getCoursesByMultiplePrograms(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();

		try {
			String selectedPrograms[] = baseActionForm.getSelectedClassesArray()
					.split(",");

			Set<Integer> programIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedPrograms.length; i++) {
				programIdsSet.add(Integer.parseInt(selectedPrograms[i]));
			}

			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(programIdsSet);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	
	if (baseActionForm.getPropertyName() != null)
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), courseMap);
	request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
	return mapping.findForward("ajaxResponseForOptions");
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectByYear (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String year;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = String.valueOf(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				subjectMap = CommonAjaxHandler.getInstance().getSubjectByYear(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExamByYear (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String year;
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = String.valueOf(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				examMap = CommonAjaxHandler.getInstance().getExamByYear(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCourseByApplnNo (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String year;
		Map<Integer, String> coursesMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null && baseActionForm.getYear().length() != 0 && baseActionForm.getAppNo()!=null && !baseActionForm.getAppNo().isEmpty()){
			try {
				year = String.valueOf(baseActionForm.getYear());
				
				// The below map contains key as id and value as name of class.
				coursesMap = CommonAjaxHandler.getInstance().getCourseByApplnNoYear(baseActionForm.getAppNo(),year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), coursesMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, coursesMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClassByYearUserId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int teacherId;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		try {
			teacherId = Integer.parseInt(request.getParameter("teacherId"));
			String year = request.getParameter("year");
			// The below map contains key as id and value as name of class.
			classMap = CommonAjaxHandler.getInstance().getClassesByTeacher(
					teacherId, year);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward interviewTypeByCourseNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map collegeMap = new HashMap();
		if (baseActionForm.getCourseId() != null) {
			try {
				if (baseActionForm.getCourseId() != null
						&& !StringUtils.isEmpty(baseActionForm.getCourseId())
						&& baseActionForm.getYear() != null
						&& !StringUtils.isEmpty(baseActionForm.getYear())
						&& StringUtils.isNumeric(baseActionForm.getYear())){
					
					int year = Integer.parseInt(baseActionForm.getYear());
					// The below map contains key as id and value as name of state.
					collegeMap = CommonAjaxHandler.getInstance()
					.getInterviewTypeByCourseNew(baseActionForm.getCourseId(), year);
					
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, collegeMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), collegeMap);

		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward interviewSubroundsByInterviewTypeNew(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> interviewSubroundsMap = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getInterviewTypeId() != null	&& !StringUtils.isEmpty(baseActionForm.getInterviewTypeId())){
				// The below map contains key as id and value as name of state.
				interviewSubroundsMap = CommonAjaxHandler.getInstance().getInterviewSubroundsByInterviewTypeNew(baseActionForm.getInterviewTypeId(),baseActionForm.getCourseId(),baseActionForm.getYear());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, interviewSubroundsMap);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), interviewSubroundsMap);

		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFacultyByDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			int departmentId = Integer.parseInt(baseActionForm.getDepartmentId());
			// The below map contains key as userid and value as name of Employee.
			Map<Integer,String> teachersMap = CommonAjaxHandler.getInstance().getFacultyByDepartment(departmentId);
			request.setAttribute(CMSConstants.OPTION_MAP, teachersMap);
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getYearWiseDocuments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getYear() != null	&& !StringUtils.isEmpty(baseActionForm.getYear())){
				// The below map contains key as id and value as name of state.
				map = CommonAjaxHandler.getInstance().getYearWiseDocuments(baseActionForm.getYear());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.ajax.CommonAjaxExamAction#getExamNameByAcademicYear(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward getExamNameByAcademicYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> examMap = new HashMap<Integer, String>();
		try {
			examMap = CommonAjaxHandler.getInstance().getExamNameByYear(baseActionForm.getAcademicYear());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassesForProgram(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			if (baseActionForm.getYear() != null	&& !StringUtils.isEmpty(baseActionForm.getYear()) &&
					baseActionForm.getProgramTypeId() != null	&& !StringUtils.isEmpty(baseActionForm.getProgramTypeId())){
				// The below map contains key as id and value as name of state.
				map = CommonAjaxHandler.getInstance().getClassesForProgram(baseActionForm.getProgramTypeId(),baseActionForm.getYear());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsByMultipleClassesNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}

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
					Map<Integer, String> tempMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
					subjectsMap.putAll(tempMap);
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectsMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectsMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getClassesByYearNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByYearNew(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStartDateAndEndDateCalculations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			String startDate = baseActionForm.getStartDate();
			String endDate = baseActionForm.getEndDate();
			java.util.Date strtDate = CommonUtil.ConvertStringToDate(startDate);
			java.util.Date endDate1 = CommonUtil.ConvertStringToDate(endDate);
			double msPerGregorianYear = 365.25 * 86400 * 1000;
			 	double years1 =(endDate1.getTime() - strtDate.getTime()) / msPerGregorianYear;
			 	int yy = (int) years1;
		        int mm = (int) ((years1 - yy) * 12);
		        int startDay = Integer.parseInt(startDate.substring(0, 2));
		        int startMonth = Integer.parseInt(startDate.substring(3,5));
		        int endDay = Integer.parseInt(endDate.substring(0, 2));
		        int endMonth = Integer.parseInt(endDate.substring(3,5));
		        if(startDay==endDay){
		        	if(startMonth==endMonth){
		        		mm=0;
		        		yy = (int) Math.round(years1);
		        	}
		        }
		        if(startDay==endDay){
		        	if(startMonth!=endMonth){
		        		mm = (int) Math.round(((years1 - yy) * 12));
		        	}
		        }
			Map<Integer,Integer> map = new HashMap<Integer, Integer>();
			map.put(yy, mm);
			request.setAttribute("optionMap", map);
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmpanelmentNoByguideName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		
		String name =baseActionForm.getGuideName();
		String empNo = "";


		try {
			if (name !=null && !name.isEmpty()) {
				empNo = CommonAjaxHandler.getInstance().getEmpanelmentNoByguideName(name,request);
		   }
			} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("empNo", empNo);

		return mapping.findForward("ajaxResponseForGetGuideNo");

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSpecializationByClass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> specializationMap = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getSelectedClassesArray()!=null && !baseActionForm.getSelectedClassesArray().isEmpty()){
				String selectedClasses[] = baseActionForm.getSelectedClassesArray() .split(",");
				Set<Integer> classesIdsSet = new HashSet<Integer>();
				for (int i = 0; i < selectedClasses.length; i++) {
					classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
			specializationMap = CommonAjaxHandler.getInstance().getSpecializationByClassId(classesIdsSet);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, specializationMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentNameInHostel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String regNo = baseActionForm.getRegNo();
		String applNo=baseActionForm.getAppNo();
		String name = "";
		String gender = "";
		String[] firstname =null;
		String name1 =null;
		try {
				if ((regNo!=null && !regNo.isEmpty()) || 
				    (applNo!=null && !applNo.isEmpty())) {
					name = CommonAjaxHandler.getInstance().getStudentNameInHostel(regNo, applNo,request);
					if(name!=null && !name.isEmpty()){
					firstname=name.split(";");
					name1=firstname[0];
					gender=firstname[1];
					}
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		    if(name1!=null && !name1.isEmpty() && gender!=null && !gender.isEmpty()){
			request.setAttribute("name", name1);
			request.setAttribute("gender", gender);
		    }else{
		    	request.setAttribute("name", "");
		    	request.setAttribute("gender", "");
		    }
		return mapping.findForward("getStudentNameInHostel");

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectListForExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			map = ExamValidationDetailsHandler.getInstance().getSubjectCodeName("", baseActionForm.getSubjectType(), 
					Integer.parseInt(baseActionForm.getExamName()) , baseActionForm.getExamType());
			map = CommonUtil.sortMapByValue(map);
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoomByRoomType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String roomType = request.getParameter("roomTypeId"); //Added by Dilip
		Map<Integer, String> roomMap = CommonAjaxHandler.getInstance()
				.setRoomByRoomType(Integer.parseInt(roomType));
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBlocksByHostel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getHostelId() != null && !baseActionForm.getHostelId().trim().isEmpty()){
				IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();
				map = txImpl.getBlocks(baseActionForm.getHostelId());
				map = CommonUtil.sortMapByValue(map);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUnitsByBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getHostelId() != null && !baseActionForm.getHostelId().trim().isEmpty()){
				IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();
				map = txImpl.getUnits(baseActionForm.getHostelId());
				map = CommonUtil.sortMapByValue(map);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFloorsByHostel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getHostelId() != null && !baseActionForm.getHostelId().trim().isEmpty()){
				IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();
				map = txImpl.getFloorsByHostel(baseActionForm.getHostelId());
				map = CommonUtil.sortMapByValue(map);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNumberOfSeatsAvailable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String hostel = baseActionForm.getHostelId();
		String roomtype=Integer.toString(baseActionForm.getHostelroomTypeId());
		String year=baseActionForm.getAcademicYear();
		BigDecimal number=null;
		try {
			number = HlAdmissionImpl.getInstance().getNumberOfSeatsAvailable(hostel, roomtype,year,request);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		    if(number!=null){
			request.setAttribute("name", number);
			request.setAttribute("gender", "");
		    }else{
		    	request.setAttribute("name", "0");
		    	request.setAttribute("gender", "");
		    }

		return mapping.findForward("getStudentNameInHostel");

	}
	/**
	 * getting courses by multiple programs
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCourseByPrograms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getSelectedProgramArray()!=null && !baseActionForm.getSelectedProgramArray().isEmpty()){
				String selectedPrograms[] = baseActionForm.getSelectedProgramArray() .split(",");
				Set<Integer> programIdsSet = new HashSet<Integer>();
				for (int i = 0; i < selectedPrograms.length; i++) {
					programIdsSet.add(Integer.parseInt(selectedPrograms[i]));
				}
				courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramId(programIdsSet);
				courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyRegisterNumberAndGetName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HttpSession session=request.getSession();
		try{
			HlAdmissionBo hlAdmissionBo=HostelLeaveHandler.getInstance().verifyRegisterNumberAndGetName(baseActionForm);
			if(hlAdmissionBo!=null )
			{
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
					{
					request.setAttribute("studentName", hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
					}else{
						request.setAttribute("studentName", " ");
					}
					if(hlAdmissionBo.getRoomId()!=null )
					{
					if(hlAdmissionBo.getRoomId().getName()!=null && !hlAdmissionBo.getRoomId().getName().isEmpty())
					{
						request.setAttribute("studentRoomNo", hlAdmissionBo.getRoomId().getName());
					}else{
						request.setAttribute("studentRoomNo", " ");
					}
					if(hlAdmissionBo.getRoomId().getHlBlock().getName()!=null && !hlAdmissionBo.getRoomId().getHlBlock().getName().isEmpty())
					{
						request.setAttribute("studentBlock", hlAdmissionBo.getRoomId().getHlBlock().getName());
					}else{
						request.setAttribute("studentBlock", " ");
					}
					if(hlAdmissionBo.getRoomId().getHlUnit().getName()!=null && !hlAdmissionBo.getRoomId().getHlUnit().getName().isEmpty())
					{
						request.setAttribute("studentUnit", hlAdmissionBo.getRoomId().getHlUnit().getName());
					}else{
						request.setAttribute("studentUnit", " ");
					}
					}else{
						request.setAttribute("studentRoomNo", " ");
						request.setAttribute("studentBlock", " ");
						request.setAttribute("studentUnit", " ");
					}
					if(hlAdmissionBo.getBedId()!=null){
					if(hlAdmissionBo.getBedId().getBedNo()!=null && !hlAdmissionBo.getBedId().getBedNo().isEmpty())
					{
						request.setAttribute("studentBedNo", hlAdmissionBo.getBedId().getBedNo());
					}else{
						request.setAttribute("studentBedNo", " ");
					}}
					else{
						request.setAttribute("studentBedNo", " ");
					}
					
					if(hlAdmissionBo.getStudentId().getClassSchemewise()!=null)
					{
					if(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName()!=null && !hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName().isEmpty())
					{
						request.setAttribute("studentClass", hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
					}else{
						request.setAttribute("studentClass", " ");
					}
					}else{
						request.setAttribute("studentClass", " ");
					}
					if(hlAdmissionBo.getHostelId().getName()!=null && !hlAdmissionBo.getHostelId().getName().isEmpty())
					{
						request.setAttribute("studentHostel", hlAdmissionBo.getHostelId().getName());
					}else{
						request.setAttribute("studentHostel", " ");
					}
					if(hlAdmissionBo.getId()!=0)
					{
						session.setAttribute("hostelAdmissionId", hlAdmissionBo.getId());
					}else{
						request.setAttribute("hostelAdmissionId", " ");
					}
				}else
				{
					request.setAttribute("msg","Student is not Checked In");
				}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("getStudentNameInHostelByRegNo");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelBygender(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> hostelmap = CommonAjaxHandler.getInstance().
		getHostelBygender(baseActionForm.getHostelGender());
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), hostelmap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, hostelmap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoomTypeByHostelBYstudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			if(baseActionForm.getHostelId() != null && !baseActionForm.getHostelId().trim().isEmpty()){
				IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();
				map = txImpl.getRoomTypeByHostelBYstudent(Integer.parseInt(baseActionForm.getHostelId()));
				map = CommonUtil.sortMapByValue(map);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTotalSeatsByroomIdAndHostelId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			int totalAvailSeats=AvailableSeatsHandler.getInstance().getTotalAvailSeatsByRoomIdAndHostelId(baseActionForm);
			request.setAttribute("totalAvailSeats",totalAvailSeats);
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("getTotalAvailSeatsByHostelIdAndRoomId");
	}
	

	public ActionForward getStudentNameClass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String regNo = baseActionForm.getRegNo();
		String applNo=baseActionForm.getAppNo();
		String academicYear=baseActionForm.getAcademicYear();
		String hostelApplNo=baseActionForm.getHostelAppNo();
		HttpSession session=request.getSession();
		String checkIndate=CommonUtil.formatDates(new java.util.Date());
		Map<Integer, String> HostelMap = new HashMap<Integer, String>();
		Map<Integer, String> RoomTypeMap = new HashMap<Integer, String>();
		Map<Integer, String> BlockMap = new HashMap<Integer, String>();
		Map<Integer, String> UnitMap = new HashMap<Integer, String>();
		Map<Integer, String> FloorMap = new HashMap<Integer, String>();
		Map<Integer, String> RoomMap = new HashMap<Integer, String>();
		Map<Integer, String> BedMap = new HashMap<Integer, String>();
		try{
			List list=CommonAjaxHandler.getInstance().getStudentNameClass(regNo,applNo,academicYear,hostelApplNo,request);
			if(list!=null && !list.isEmpty())
			{
				Iterator iterator=list.iterator();
				while (iterator.hasNext()) {
					Object[]  object = (Object[]) iterator.next();
					if(object[0]!=null)
					request.setAttribute("studentName", object[0].toString());
					if(object[1]!=null)
					session.setAttribute("hostelAdmissionId", object[1].toString());
					if(object[2]!=null)
					request.setAttribute("studentRoomNo", object[2].toString());
					if(object[3]!=null)
					request.setAttribute("studentBedNo", object[3].toString());
					if(object[4]!=null)
					request.setAttribute("studentBlock", object[4].toString());
					if(object[5]!=null)
					request.setAttribute("studentFloor", object[5].toString());
					if(object[6]!=null)
					request.setAttribute("studentUnit", object[6].toString());
					if(object[7]!=null)
					request.setAttribute("studentHostel", object[7].toString());
					if(object[8]!=null)
					request.setAttribute("studentHostelAppNo", object[8].toString());
					if(object[9]!=null)
					request.setAttribute("admittedDate", object[9].toString());
					if(object[10]!=null)
					request.setAttribute("biometricId", object[10].toString());
					else
						request.setAttribute("biometricId", "");
					if(object[11]!=null)
					request.setAttribute("checkInDate", object[11].toString());
					else
						request.setAttribute("checkInDate",checkIndate);
					if(object[12]==null)
						request.setAttribute("isCheckedIn", object[12].toString());
					else if(object[12].toString().equalsIgnoreCase("true"))
						request.setAttribute("isCheckedIn", true);
					else if(object[12].toString().equalsIgnoreCase("false"))
						request.setAttribute("isCheckedIn", false);
					if(object[13]!=null)
					request.setAttribute("roomTypeId", object[13].toString());
					else
						request.setAttribute("roomTypeId", "");
					if(object[14]!=null)
					request.setAttribute("studentGender", object[14].toString());
					else
						request.setAttribute("studentGender", "");
					
					if(object[14]!=null && !object[14].toString().isEmpty())
						HostelMap = CommonAjaxHandler.getInstance().getHostelBygender(object[14].toString());
					if(object[7]!=null && !object[7].toString().isEmpty()){
						RoomTypeMap = CommonAjaxHandler.getInstance().getRoomTypeByHostelBYstudent(Integer.parseInt(object[7].toString()));
						BlockMap = HostelEntryHandler.getInstance().getBlocks(object[7].toString());
						FloorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(object[7].toString()));
					}
					if(object[4]!=null && !object[4].toString().isEmpty())
						UnitMap = HostelEntryHandler.getInstance().getUnits(object[4].toString());
					
						RoomMap = CommonAjaxHandler.getInstance().getRoomsAvailable(object[7].toString(),object[13].toString(),academicYear,object[4].toString(),object[6].toString(),object[5].toString());
					if(object[2]!=null && !object[2].toString().isEmpty())
						BedMap = CommonAjaxHandler.getInstance().getBedsAvailable(object[2].toString(), Integer.parseInt(academicYear));
					
					request.setAttribute("HostelMap", HostelMap);
					request.setAttribute("RoomTypeMap", RoomTypeMap);
					request.setAttribute("BlockMap", BlockMap);
					request.setAttribute("FloorMap", FloorMap);
					request.setAttribute("UnitMap", UnitMap);
					request.setAttribute("RoomMap", RoomMap);
					request.setAttribute("BedMap", BedMap);
				}
			}
			else
			{
				request.setAttribute("msg","Student not found for this register number");
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("getStudentNameClass");
	}

public ActionForward getRooms(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
	BaseActionForm baseActionForm = (BaseActionForm) form;
	String hstlName=baseActionForm.getHostelId();
	String RoomType=baseActionForm.getHostelroomType();
	String academicYear=baseActionForm.getAcademicYear();
	String block=baseActionForm.getBlockId();
	String unit=baseActionForm.getUnitId();
	String floor=baseActionForm.getFloorNo();
	Map<Integer, String> roomMap = CommonAjaxHandler.getInstance().getRoomsAvailable(hstlName,RoomType,academicYear,block,unit,floor);
	request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
	return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getSupplementryExamNamesByAcademicYear(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	if(baseActionForm.getAcademicYear()!=null && !baseActionForm.getAcademicYear().isEmpty()){
		String academicYear = baseActionForm.getAcademicYear();
		Map<Integer, String> suppExamNameMap = CommonAjaxHandler.getInstance().getSupplementryExamNameByAcademicYear(academicYear);
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), suppExamNameMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, suppExamNameMap);
	}
	
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * Method to get the examName list according to examname id 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getExamNameList(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	try {
		if((baseActionForm.getProgramId() != null && !baseActionForm.getProgramId().isEmpty())
			&& (baseActionForm.getAcademicYear()!=null && !baseActionForm.getAcademicYear().isEmpty())){
			List<ExamValidationDetailsTO> list =  ExamValidationDetailsHandler.getInstance().getExamValidationDetails(baseActionForm.getProgramId(),baseActionForm.getAcademicYear());
			request.setAttribute(CMSConstants.OPTION_MAP, list);
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("ajaxResponseForExamNameList");
}
/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return returns the classMap <key,value>
 * @throws Exception
 */
public ActionForward getClassesBySemester(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int year;
	String semester;
	Map<Integer, String> classMap = new HashMap<Integer, String>();
	if (baseActionForm.getAcademicYear() != null
			&& baseActionForm.getAcademicYear().length() != 0) {
		try {
			year = Integer.parseInt(baseActionForm.getAcademicYear());
			semester=baseActionForm.getSemister();
			// The below map contains key as id and value as name of class.
			classMap = CommonAjaxHandler.getInstance().getClassesBySemAndYear(year,semester);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), classMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, classMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @author jboss
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return returns the classMap <key,value>
 * @throws Exception
 */
public ActionForward getClassesByTeacherAndDate(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int teacherId;
	Map<Integer, String> classMap = new HashMap<Integer, String>();
	try {
		teacherId = Integer.parseInt(request.getParameter("teacherId"));
		String year = request.getParameter("year");
		String day=CommonUtil.getDayForADate(request.getParameter("date"));
		// The below map contains key as id and value as name of class.
		classMap = CommonAjaxHandler.getInstance().getClassesByTeacherAndDate(teacherId, year,day,request.getParameter("date"));

	} catch (Exception e) {
		log.debug(e.getMessage());
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), classMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, classMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * getting the student details
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getStudentDetailsForVisitors(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	try{
		HlAdmissionBo hlAdmissionBo =CommonAjaxHandler.getInstance().getStudentDetailsForVisitors(baseActionForm.getAcademicYear(),baseActionForm.getRegNo());
		if(hlAdmissionBo!=null)
		{
				request.setAttribute("studentName", hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				if(hlAdmissionBo.getCourseId()!=null){
					request.setAttribute("studentCourse", hlAdmissionBo.getCourseId().getName());
				}else{
					request.setAttribute("studentCourse", "-");
				}
				if(hlAdmissionBo.getRoomId()!=null){
					request.setAttribute("studentRoomNo", hlAdmissionBo.getRoomId().getName());
					request.setAttribute("studentBlock", hlAdmissionBo.getRoomId().getHlBlock().getName());
					request.setAttribute("studentUnit", hlAdmissionBo.getRoomId().getHlUnit().getName());
				}else{
					request.setAttribute("studentRoomNo","-");
					request.setAttribute("studentBlock", "-");
					request.setAttribute("studentUnit", "-");
				}
				if(hlAdmissionBo.getBedId()!=null){
					request.setAttribute("studentBedNo", hlAdmissionBo.getBedId().getBedNo());
				}else{
					request.setAttribute("studentBedNo","-");
				}
				if(hlAdmissionBo.getHostelId().getName()!=null){
					request.setAttribute("studentHostel",hlAdmissionBo.getHostelId().getName());
				}else{
					request.setAttribute("studentHostel","-");
				}
			
		}else
		{
			request.setAttribute("msg","Student is not checked in");
		}
		
		}catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("ajaxResponseForStudentList");
}

public ActionForward verifyRegisterNumberAndGetNameAccDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	HttpSession session=request.getSession();
	try{
		HlAdmissionBo hlAdmissionBo=HostelDisciplinaryDetailsHandler.getInstance().verifyRegisterNumberAndGetNameAcc(baseActionForm);
		if(hlAdmissionBo!=null && !hlAdmissionBo.getIsCheckedIn()){
			request.setAttribute("msg","Student is not Checked-In");
			return mapping.findForward("getStudentNameInHostelByRegNo");
		}
		if(hlAdmissionBo!=null)
		{
			if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getAdmAppln()!=null && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData()!=null)
				request.setAttribute("studentName", hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			else
				request.setAttribute("studentName", " ");
			if(hlAdmissionBo.getRoomId()!=null)
				request.setAttribute("studentRoomNo", hlAdmissionBo.getRoomId().getName());
			else
				request.setAttribute("studentRoomNo", " ");
			if(hlAdmissionBo.getBedId()!=null)
				request.setAttribute("studentBedNo", hlAdmissionBo.getBedId().getBedNo());
			else
				request.setAttribute("studentBedNo", " ");
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null)
				request.setAttribute("studentBlock", hlAdmissionBo.getRoomId().getHlBlock().getName());
			else
				request.setAttribute("studentBlock", " ");
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlUnit()!=null)
				request.setAttribute("studentUnit", hlAdmissionBo.getRoomId().getHlUnit().getName());
			else
				request.setAttribute("studentUnit", " ");
			if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getClassSchemewise()!=null && hlAdmissionBo.getStudentId().getClassSchemewise().getClasses()!=null)
				request.setAttribute("studentClass", hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
			else
				request.setAttribute("studentClass", " ");
			if(hlAdmissionBo.getHostelId()!=null)
				request.setAttribute("studentHostel", hlAdmissionBo.getHostelId().getName());
			else
				request.setAttribute("studentHostel", " ");
			if(hlAdmissionBo!=null)
				session.setAttribute("hostelAdmissionId", hlAdmissionBo.getId());
				
		}
		else
		{
			request.setAttribute("msg","Student not found for this register number");
		}
	}catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("getStudentNameInHostelByRegNo");
}
/**
 * to check whether register number duplicate or not
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward checkDupilcateOfRegNo(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
  
	BaseActionForm baseActionForm = (BaseActionForm) form;
	if (baseActionForm.getRegNo() != null && !baseActionForm.getRegNo().isEmpty()) {
		try {
			Boolean b=CommonAjaxHandler.getInstance().checkChildRegisterNo(baseActionForm.getRegNo());
			if(b)
			{
				request.setAttribute("duplicateRegNo", " ");
			}else
			{
				request.setAttribute("duplicateRegNo","Register number is not valid ");
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	return mapping.findForward("ajaxResponseForRegNo");
}
/**
 * Method to get the date of exam according to examname id and subject id
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getExamDateBySubject(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	
	String subId =baseActionForm.getSubjectId();
	String examId =baseActionForm.getExam1Id();
	String date="";

	try {
		if (subId !=null && !subId.isEmpty() && examId != null && !examId.isEmpty()) {
			date = CommonAjaxHandler.getInstance().getExamDateBySubject(examId,subId,request);
			date = CommonUtil.formatSqlDate1(date);
	   }
		} catch (Exception e) {
		log.debug(e.getMessage());
	}
	request.setAttribute("date", date);

	return mapping.findForward("ajaxResponseForGetDate1");

}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getStates(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int cid;
	Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
	if (baseActionForm.getCountryId() != null) {
		try {
			cid = Integer.parseInt(baseActionForm.getCountryId());
			// The below map contains key as id and value as name of state.
			stateMap = CommonAjaxHandler.getInstance()
					.getStatesByCountryId(cid);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), stateMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, stateMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * get amount by category
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getAmountByCategory(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	try{
		String amount=CommonAjaxHandler.getInstance().getAmountByCategory(baseActionForm);
		request.setAttribute("totalAvailSeats",amount);
		}catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("getTotalAvailSeatsByHostelIdAndRoomId");
}
public ActionForward getExamNameListSchedule(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	try {
		if((baseActionForm.getProgramId() != null && !baseActionForm.getProgramId().isEmpty())
			&& (baseActionForm.getAcademicYear()!=null && !baseActionForm.getAcademicYear().isEmpty())){
			List<ValuationScheduleTO> list =  ValuationScheduleHandler.getInstance().getExamNameListSchedule(baseActionForm.getProgramId(),baseActionForm.getAcademicYear());
			request.setAttribute(CMSConstants.OPTION_MAP, list);
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("ajaxResponseForValuationScheduleList");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getEmployeeByprogram(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int pid;
	Map<Integer, String> employeeMap = new HashMap<Integer, String>();
	if (baseActionForm.getProgramId() != null
			&& baseActionForm.getProgramId().length() != 0) {
		try {
			pid = Integer.parseInt(baseActionForm.getProgramId());
			employeeMap = CommonAjaxHandler.getInstance().getEmployeeByprogramId(pid);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), employeeMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, employeeMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward setInternalGuide(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	String check;
	Map<Integer, String> guideMap = new HashMap<Integer, String>();
	if (baseActionForm.getType() != null) {
		try {
			check =baseActionForm.getType();
			guideMap = CommonAjaxHandler.getInstance().getInternalGuide(check);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), guideMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, guideMap);
	return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getDepartments(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> deptMap = new HashMap<Integer, String>();
		try {
			deptMap = CommonAjaxHandler.getInstance().getDepartments(
					);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), deptMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, deptMap);
	return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getPeriodsByClassesvalues(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> periodMap = new HashMap<Integer, String>();
	try {
				
		String allClasses[] = baseActionForm.getAllClassesArray().split(",");
		

		Set<Integer> classesIdsSet = new HashSet<Integer>();
		if(allClasses.length > 0){
		for (int i = 0; i < allClasses.length; i++) {
			classesIdsSet.add(Integer.parseInt(allClasses[0]));
		}
		
		periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(classesIdsSet);
		}

	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	request.setAttribute("periodOptionMap", periodMap);
	return mapping.findForward("ajaxResponseForPeriod");
}
/**
 * get blocks by hostel
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getBlockByHostel(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> blockMap = CommonAjaxHandler.getInstance()
			.getBlockByHostel(Integer.parseInt(baseActionForm.getHostelId()));
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), blockMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, blockMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * get units by block
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getUnitByBlock(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> unitMap = CommonAjaxHandler.getInstance()
			.getUnitByBlock(Integer.parseInt(baseActionForm.getBlockId()));
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), unitMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, unitMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * to check whether staffIdr duplicate or not
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward checkDupilcateOfStaffId(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
  
	BaseActionForm baseActionForm = (BaseActionForm) form;
	if (baseActionForm.getRegNo() != null && !baseActionForm.getRegNo().isEmpty()) {
		try {
			Boolean flag=CommonAjaxHandler.getInstance().checkDupilcateOfStaffId(baseActionForm.getRegNo());
			if(flag)
			{
				request.setAttribute("duplicateRegNo", "Staff Id already exist");
			}else
			{
				request.setAttribute("duplicateRegNo"," ");
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	return mapping.findForward("ajaxResponseForRegNo");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getBlockByLocation(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int locationId;
	Map<Integer, String> blockMap = new HashMap<Integer, String>();
	if (baseActionForm.getLocationId() != null
			&& baseActionForm.getLocationId().length() != 0) {
		try {
			locationId = Integer.parseInt(baseActionForm.getLocationId());
			blockMap = CommonAjaxHandler.getInstance().getBlockByLocation(locationId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), blockMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, blockMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getVenueByWorkLocation(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> venueMap=null;
	if ((baseActionForm.getPropertyName() != null && !baseActionForm.getPropertyName().isEmpty())
			&& (baseActionForm.getWorkLocatId()!=null && !baseActionForm.getWorkLocatId().isEmpty())) {
		 venueMap = CommonAjaxHandler.getInstance()
		.getVenueByWorkLocation(Integer.parseInt(baseActionForm.getWorkLocatId()));
		baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), venueMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, venueMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * get blocks by hostel
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getCoursebyHostels(ActionMapping mapping,ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursebyHostel(Integer.parseInt(baseActionForm.getHostelId()));
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), courseMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * get blocks by hostel
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getClassByHostels(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
			.getClassByHostel(Integer.parseInt(baseActionForm.getHostelId()));
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), classMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, classMap);
	return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getExamByYearOnlyInternal(ActionMapping mapping,ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int year;
	
	Map<Integer, String> examMap = new HashMap<Integer, String>();
	if (baseActionForm.getYear() != null
			&& baseActionForm.getYear().length() != 0) {
		try {
			year = Integer.parseInt(baseActionForm.getYear());
			// The below map contains key as id and value as name of class.
			examMap = ExamMidsemExemptionHandler.getInstance().getExamNameList(year);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), examMap);
	}
	request.setAttribute(CMSConstants.OPTION_MAP, examMap);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getProgramBydeanaryNameAndExam(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;

	int examId = baseActionForm.getExamid();
	String deanaryName=baseActionForm.getDeanaryName();
	Map<Integer, String> programMap = new HashMap<Integer, String>();
	try {
		programMap = CommonAjaxHandler.getInstance().getProgramBydeanaryNameAndExam(deanaryName,examId);
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	
	if (baseActionForm.getPropertyName() != null)
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), programMap);
	request.setAttribute(CMSConstants.OPTION_MAP, programMap);
	return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getClassesByProgramAndAcademicYear(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {

	BaseActionForm baseActionForm = (BaseActionForm) form;
	int programId;
	String deaneryName;
	Map<Integer, String> classMap = new HashMap<Integer, String>();
	if (baseActionForm.getProgramId() != null
			&& baseActionForm.getProgramId().length() != 0) {
		try {
			programId = Integer.parseInt(baseActionForm.getProgramId());
			deaneryName = baseActionForm.getDeanaryName();
			// The below map contains key as id and value as name of class.
			classMap = CommonAjaxHandler.getInstance().getClassesByProgramAndAcademicYear(programId, deaneryName);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	if (baseActionForm.getPropertyName() != null) {
		baseActionForm.getCollectionMap().put(
				baseActionForm.getPropertyName(), classMap);
	}
	
	request.setAttribute(CMSConstants.OPTION_MAP, classMap);
	return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getClassesByProgramTypeAndAcademicYear(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int programTypeId;
		String deaneryName;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				programTypeId = Integer.parseInt(baseActionForm.getProgramTypeId());
				deaneryName = baseActionForm.getDeanaryName();
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByProgramTypeAndAcademicYear(programTypeId, deaneryName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getClassesByCourseAndAcademicYear(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int courseId;
		String deaneryName;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0) {
			try {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				deaneryName = baseActionForm.getDeanaryName();
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesByCourseAndAcademicYear(courseId, deaneryName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getSubjectListForScheduleExam(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
	Map<Integer, String> map = new HashMap<Integer, String>();
	try{
		map = ValuationScheduleHandler.getInstance().getSubjectCodeName("", baseActionForm.getSubjectType(), 
				Integer.parseInt(baseActionForm.getExamName()) , baseActionForm.getExamType());
		map = CommonUtil.sortMapByValue(map);
	}catch (Exception e) {
		log.debug(e.getMessage());
	}
	request.setAttribute(CMSConstants.OPTION_MAP, map);
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * orderNo duplicate checking by location id
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward duplicateCheckingOfOrderNoByLocationId(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			boolean flag =CommonAjaxHandler.getInstance().duplicateCheckingOfOrderNoByLocationId(baseActionForm.getAcademicYear(),baseActionForm.getRegNo());
			if(flag){
					request.setAttribute("msg","Allotment Order No Already exists for this Location");
			}else{
				request.setAttribute("msg"," ");
			}
			
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOderNoChecking");
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward getClassesBySemesterAndAcademicYear(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int semester;
		String deaneryName;
		int programId = 0;
		int courseId = 0;
		int programTypeId = 0;
		
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getSemister() != null
				&& baseActionForm.getSemister().length() != 0) {
			try {
				semester = Integer.parseInt(baseActionForm.getSemister());
				deaneryName = baseActionForm.getDeanaryName();
				if(!baseActionForm.getCourseId().isEmpty())
					courseId = Integer.parseInt(baseActionForm.getCourseId());
				if(!baseActionForm.getProgramTypeId().isEmpty())
					programTypeId = Integer.parseInt(baseActionForm.getProgramTypeId());
				if(!baseActionForm.getProgramId().isEmpty())
					programId = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesBySemesterAndAcademicYear(semester, deaneryName, programTypeId, programId, courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * deanery by stream
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDeptByStream (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		if (baseActionForm.getStreamId()!= null && !baseActionForm.getStreamId().isEmpty()) {
			try {
				examMap = CommonAjaxHandler.getInstance().getDepartmentByStream(baseActionForm.getStreamId());

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * roomNos by work location 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward roomNosByCampus (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomNoMap = new HashMap<Integer, String>();
		if (baseActionForm.getLocationId()!= null && !baseActionForm.getLocationId().isEmpty()) {
			try {
				roomNoMap = CommonAjaxHandler.getInstance().getRoomNosByCampus(baseActionForm.getLocationId());

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), roomNoMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomNoMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * faculty by work location 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward facultyByCampus (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> facultyMap = new HashMap<Integer, String>();
		if (baseActionForm.getLocationId()!= null && !baseActionForm.getLocationId().isEmpty()) {
			try {
				facultyMap = CommonAjaxHandler.getInstance().getFacultyByCampus(baseActionForm.getLocationId());

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), facultyMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, facultyMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * getting the student details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkDuplicateInvigilator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		baseActionForm.setExamType(null);
		try{
			boolean flag =CommonAjaxHandler.getInstance().checkDuplicateInvigilator(baseActionForm);
			if(flag){
				if(baseActionForm.getExamType()!=null && baseActionForm.getExamType().equalsIgnoreCase("duplicate")){
					request.setAttribute("msg","1");
				}else{
					request.setAttribute("msg","2");
				}
			}else{
				boolean flag1=CommonAjaxHandler.getInstance().checkIsFacultyInExamInvigilatorsAvailable(baseActionForm);
				if(flag1){
					request.setAttribute("msg","3");
				}else{
					request.setAttribute("msg","4");
				}
			}
			
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForStudentList");
	}
	/**
	 * getting the student name by regNO
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentNameByRegNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			Student  student =StudentSupportRequestTransImpl.getInstance().checkRegisterNoAvailable(baseActionForm.getRegNo());
			if(student!=null){
				StringBuilder stringBuilder=new StringBuilder();
				if(student.getAdmAppln()!=null){
					if(student.getAdmAppln().getPersonalData()!=null){
						if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
							stringBuilder.append(student.getAdmAppln().getPersonalData().getFirstName());
						}
						if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
							stringBuilder.append(student.getAdmAppln().getPersonalData().getMiddleName());
						}
					}
				}
					request.setAttribute("studentName", stringBuilder.toString());
					request.setAttribute("studentCourse", " ");
					request.setAttribute("studentRoomNo", " ");
					request.setAttribute("studentBedNo", " ");
					request.setAttribute("studentBlock", " ");
					request.setAttribute("studentUnit", " ");
					request.setAttribute("studentHostel", " ");
			}else{
				request.setAttribute("msg","Register No is not valid");
			}
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForStudentList");
	}
	/**
	 * faculty by work location, department and deanary
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward facultyByCampusDeptAndDeanery (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> facultyMap = new HashMap<Integer, String>();
		if (baseActionForm.getLocationId()!= null && !baseActionForm.getLocationId().isEmpty()) {
			try {
				facultyMap = CommonAjaxHandler.getInstance().facultyByCampusDeptAndDeanery(baseActionForm.getLocationId(),baseActionForm.getDepartmentId(),baseActionForm.getDeanaryName());

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), facultyMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, facultyMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * getting the student details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkDuplicateExemption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		baseActionForm.setExamType(null);
		try{
			boolean flag =CommonAjaxHandler.getInstance().checkDuplicateExemption(baseActionForm);
			if(flag){
					request.setAttribute("msg","1");
			}else{
				request.setAttribute("msg","2");
			}
			
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForStudentList");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountsForApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> accountMap = new HashMap<Integer, String>();
			
			try {
				
				List<PcAccountHead> accountList = CashCollectionTransactionImpl.getInstance().getAccountNameWithCodeToList(baseActionForm.getType());
				if(accountList!=null && !accountList.isEmpty()){
					for (PcAccountHead pcAccountHead : accountList) {
						accountMap.put(pcAccountHead.getId(), pcAccountHead.getAccName()+"("+pcAccountHead.getAccCode()+")");
					}
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			accountMap = CommonUtil.sortMapByValue(accountMap);
			
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), accountMap);
			}
			
			request.setAttribute(CMSConstants.OPTION_MAP, accountMap);
			return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * faculty by work location, department and deanary
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProgramByYear (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getAcademicYear()!= null && !baseActionForm.getAcademicYear().isEmpty()) {
			try {
				programMap = CommonAjaxHandler.getInstance().getProgramByYear(baseActionForm);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/**
	 * classes by exam name,exam type,programId and deanary
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassMap (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			if(baseActionForm.getExamName()!=null && !baseActionForm.getExamName().isEmpty()
					&&	baseActionForm.getExamType()!=null && !baseActionForm.getExamType().isEmpty()
					&& ((baseActionForm.getProgramId()!=null && !baseActionForm.getProgramId().isEmpty())||
							(baseActionForm.getDeanaryName()!=null && !baseActionForm.getDeanaryName().isEmpty()))){
				 map=ExamPublishHallTicketHandler.getInstance().getclassesMap(baseActionForm.getExamName(),
							baseActionForm.getExamType(),baseActionForm.getProgramId(),baseActionForm.getDeanaryName());
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), map);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsByMultipleClassesIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = baseActionForm.getSelectedClassesArray()
					.split(",");

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			Map<Integer, String> tempMap = AttendanceSummaryReportHandler.getInstance().getSubjectByClassIDs(classesIdsSet);
			subjectsMap.putAll(tempMap);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectsMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectsMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * getting the student details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward isFacultyAvailable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		baseActionForm.setExamType(null);
		try{
				boolean flag1=CommonAjaxHandler.getInstance().checkIsFacultyInExamInvigilatorsAvailable(baseActionForm);
				if(flag1){
					request.setAttribute("msg","1");
				}else{
					request.setAttribute("msg","2");
				}
			
			}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForStudentList");
	}
	
	

	public ActionForward getVenueBySelectionDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			
			String selectionScheduleId;
			
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectScheduleId() != null
					&& baseActionForm.getSelectScheduleId().length() != 0) {
				try {
					selectionScheduleId= baseActionForm.getSelectScheduleId();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getVenueBySelectionDateHl(selectionScheduleId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	
	
	
	/*public ActionForward getDateByVenueSelectionPref(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			String programId;
			String selectionVenueId;
			
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectVenueId() != null
					&& baseActionForm.getSelectVenueId().length() != 0) {
				try {
					programId=baseActionForm.getProgramId();
					selectionVenueId= baseActionForm.getSelectVenueId();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getDatesBySelectionVenueOffline(selectionVenueId, programId, );
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}*/
	

	public ActionForward getTimeBySelectionDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			String selectionVenueId;
			String selectionScheduleId;
			Map<Integer, String> interviewTimeSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectionProcessVenue() != null
					&& baseActionForm.getSelectionProcessVenue().length() != 0 
					&& (baseActionForm.getSelectScheduleId()!=null && !baseActionForm.getSelectScheduleId().isEmpty())) {
				try {
					selectionVenueId = baseActionForm.getSelectionProcessVenue();
					selectionScheduleId= baseActionForm.getSelectScheduleId();
					interviewTimeSelection = CommonAjaxHandler.getInstance().getTimeBySelectionDateHl(selectionVenueId,selectionScheduleId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewTimeSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	
	public ActionForward getDateByVenueSelection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			String programId;
			String selectionVenueId;
			String programYear;
			
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectVenueId() != null
					&& baseActionForm.getSelectVenueId().length() != 0) {
				try {
					programYear=baseActionForm.getProgramYear();
					programId=baseActionForm.getProgramId();
					selectionVenueId= baseActionForm.getSelectVenueId();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getDatesBySelectionVenueOnline(selectionVenueId, programId,programYear );
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	
	public ActionForward getDateByVenueSelectionOffline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			String programId;
			String selectionVenueId;
			String programYear;
			
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectVenueId() != null
					&& baseActionForm.getSelectVenueId().length() != 0) {
				try {
					programYear=baseActionForm.getProgramYear();
					programId=baseActionForm.getProgramId();
					selectionVenueId= baseActionForm.getSelectVenueId();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getDatesBySelectionVenueOffline(selectionVenueId, programId, programYear);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	
	public ActionForward getDateByVenueSelectionAppEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			String programId;
			String selectionVenueId;
			String programYear;
			String applicationNo;
			String oldSelectedVenue;
			
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectVenueId() != null
					&& baseActionForm.getSelectVenueId().length() != 0) {
				try {
					programYear=baseActionForm.getProgramYear();
					programId=baseActionForm.getProgramId();
					applicationNo=baseActionForm.getApplnNo();
					selectionVenueId= baseActionForm.getSelectVenueId();
				    oldSelectedVenue=baseActionForm.getOldSelectedVenue();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getDatesBySelectionVenueOnlineAppEdit(selectionVenueId, programId,programYear, applicationNo, oldSelectedVenue);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	
	public ActionForward getDateByVenueSelectionOfflineAppEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			BaseActionForm baseActionForm = (BaseActionForm) form;
			String programId;
			String selectionVenueId;
			String programYear;
			String applicationNo;
			String oldSelectedVenue;
			Map<Integer, String> interviewVenueSelection = new HashMap<Integer, String>();
			if (baseActionForm.getSelectVenueId() != null
					&& baseActionForm.getSelectVenueId().length() != 0) {
				try {
					programYear=baseActionForm.getProgramYear();
					programId=baseActionForm.getProgramId();
					applicationNo=baseActionForm.getApplnNo();
					selectionVenueId= baseActionForm.getSelectVenueId();
					oldSelectedVenue=baseActionForm.getOldSelectedVenue();
					interviewVenueSelection = CommonAjaxHandler.getInstance().getDatesBySelectionVenueOfflineAppEdit(selectionVenueId, programId, programYear,applicationNo,oldSelectedVenue );
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			request.setAttribute(CMSConstants.OPTION_MAP, interviewVenueSelection);
			return mapping.findForward("ajaxResponseForOptions");
		}
	public ActionForward getExamByYearOnlyInter(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				examMap = ExamMidsemExemptionHandler.getInstance().getExamNameList(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/** get the years from syllabus entry by department 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getYearMapByDept (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> yearMap = new HashMap<String, String>();
		if (baseActionForm.getUnitId() != null && baseActionForm.getUnitId().length() != 0
				&& baseActionForm.getYear() != null && baseActionForm.getYear().length() != 0) {
			try {
				String deptId= String.valueOf(baseActionForm.getUnitId());
				String year = String.valueOf(baseActionForm.getYear());
				yearMap = CommonAjaxHandler.getInstance().getYearMapByDept(deptId,year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), yearMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, yearMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	/** get the subjects from syllabus entry by department and year 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getsubjectMapByDeptAndYear (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> subMap = new HashMap<String, String>();
		if (baseActionForm.getUnitId() != null && baseActionForm.getUnitId().length() != 0
				&& baseActionForm.getYear() != null && baseActionForm.getYear().length() != 0) {
			try {
				String deptId = String.valueOf(baseActionForm.getUnitId());
				String year = String.valueOf(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				subMap = CommonAjaxHandler.getInstance().getsubjectMapByDeptAndYear(deptId,year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subMap);
		return mapping.findForward("ajaxResponseForOptions");
	}	
	
	
	public ActionForward getExamByYearOnly(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				examMap = ExamMidsemExemptionHandler.getInstance().getExamByYearOnly(year);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkStudentApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String regNo = baseActionForm.getRegNo();
		String applNo=baseActionForm.getAppNo();
		Map<Integer, String> map = new HashMap<Integer, String>();
		String hostel="";
		String roomType="";
		String applnNO="";
		try {
			if ((regNo!=null && !regNo.isEmpty()) || (applNo!=null && !applNo.isEmpty())) {
				ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
				String hostel_roomType = iCommonAjax.checkStudentApplication(regNo,applNo,baseActionForm.getYear());
				if(!hostel_roomType.isEmpty()){
					hostel = hostel_roomType.split("%")[0];
					roomType = hostel_roomType.split("%")[1];
					applnNO = hostel_roomType.split("%")[2];
					IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();
					map = txImpl.getRoomTypeByHostelBYstudent(Integer.parseInt(hostel_roomType.split("%")[0]));
					map = CommonUtil.sortMapByValue(map);
				}
			}
		} catch (Exception e) {
				log.debug(e.getMessage());
		}
		request.setAttribute("applnNO", applnNO);
		request.setAttribute("hostelId", hostel);
		request.setAttribute("roomTypeId", roomType);
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForHlAdm");

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCentersByProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> centerMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramId());
				// The below map contains key as id and value as name of center.
				centerMap = CommonAjaxHandler.getInstance().getCentersByProgram(pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), centerMap);
		request.setAttribute(CMSConstants.OPTION_MAP, centerMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	
	public ActionForward getParishByDiose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		try{
			if(baseActionForm.getMonth()!= null && !baseActionForm.getMonth().isEmpty()){
				String dioId=baseActionForm.getMonth();
				Map<Integer, String> dioMap = AdmissionFormHandler.getInstance().getParishByDiose(dioId);
				request.setAttribute(CMSConstants.OPTION_MAP, dioMap);
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	public ActionForward getDistrictByState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
		if (baseActionForm.getStateId() != null) {
			try {
				cid = Integer.parseInt(baseActionForm.getStateId());
				// The below map contains key as id and value as name of state.
				stateMap = CommonAjaxHandler.getInstance().getDistrictByStateId(cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), stateMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, stateMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returns the courseMap <key,value>
	 * @throws Exception
	 */
	public ActionForward getSubCasteByReligion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid = 0;
		Map<Integer, String> subCasteMap = new HashMap<Integer, String>();
		if (baseActionForm.getReligionId() != null) {
			try {
				if (StringUtils.isNumeric(baseActionForm.getReligionId())) {
					pid = Integer.parseInt(baseActionForm.getReligionId());
					// The below map contains key as id and value as name of
					// state.
					subCasteMap = CommonAjaxHandler.getInstance().getSubCasteByReligion(pid);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subCasteMap);
		if (baseActionForm.getPropertyName() != null) {
			if ((baseActionForm.getCollectionMap().get(
					baseActionForm.getPropertyName()) != null)) {
				baseActionForm.getCollectionMap().remove(
						baseActionForm.getPropertyName());
			}
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subCasteMap);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}

	

	/**
	 * get amount by category
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAttendanceTypeIdBySubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid = 0;
		Integer id=0;
		try{
			if (baseActionForm.getSubjectId()!=null && !baseActionForm.getSubjectId().equalsIgnoreCase("")) {
				pid = Integer.parseInt(baseActionForm.getSubjectId());
				// The below map contains key as id and value as name of
				// state.
				id = CommonAjaxHandler.getInstance().getAttendanceTypeIdBySubject(pid);
				//raghu
				request.setAttribute("totalAvailSeats",id);
				
			}else{
				//raghu
				request.setAttribute("totalAvailSeats",id);
			}
			
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("getTotalAvailSeatsByHostelIdAndRoomId");
	}	
	
	
	// Vinodha for faculty evaluation report
	public ActionForward getClassesForFacultyEvaluationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesForFacultyEvaluationReport(baseActionForm,year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	// Vinodha for faculty evaluation report
	public ActionForward getSubjectsForFacultyEvaluationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		int classId;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		if (baseActionForm.getClassId() != null
				&& baseActionForm.getClassId().length() != 0) {
			try {
				classId = Integer.parseInt(baseActionForm.getClassId());
				// The below map contains key as id and value as name of subject.
				subjectMap = CommonAjaxHandler.getInstance().getSubjectsForFacultyEvaluationReport(baseActionForm,classId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	// Vinodha for faculty evaluation report
	public ActionForward getTeachersForFacultyEvaluationReportPrincipal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		Map<Integer, String> teacherMap = new HashMap<Integer, String>();
		try {
				// The below map contains key as id and value as name of class.
				teacherMap = CommonAjaxHandler.getInstance().getTeachersForFacultyEvaluationReportPrincipal(baseActionForm);
		} catch (Exception e) {
				log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), teacherMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, teacherMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getClassesForFacultyEvaluationReportPrincipal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		Map<Integer, String> classMap = new HashMap<Integer, String>();	
		try {
			classMap = CommonAjaxHandler.getInstance().getClassesForFacultyEvaluationReportPrincipal(baseActionForm);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getSubjectsForFacultyEvaluationReportPrincipal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		int classId;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		if (baseActionForm.getClassId() != null
				&& baseActionForm.getClassId().length() != 0) {
			try {
				classId = Integer.parseInt(baseActionForm.getClassId());
				// The below map contains key as id and value as name of subject.
				subjectMap = CommonAjaxHandler.getInstance().getSubjectsForFacultyEvaluationReportPrincipal(baseActionForm,classId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getClassesForEvaluateFacultyFeedback(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		int year;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getYear() != null
				&& baseActionForm.getYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getYear());
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance().getClassesForEvaluateFacultyFeedback(baseActionForm,year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	// Vinodha for faculty evaluation report
	public ActionForward getDepartmentsForFacultyEvaluationReportPrincipal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		BaseDispatchAction bda=new BaseDispatchAction();
		bda.setUserId(request,baseActionForm);
		Map<Integer, String> departmentMap = new HashMap<Integer, String>();
		try {
				// The below map contains key as id and value as name of class.
				departmentMap = CommonAjaxHandler.getInstance().getDepartmentsForFacultyEvaluationReportPrincipal(baseActionForm);
		} catch (Exception e) {
				log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), departmentMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, departmentMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	
	//new for admission
		
		public ActionForward getCourseByProgramForOnlineNew(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int pid;
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			if (baseActionForm.getProgramId() != null
					&& baseActionForm.getProgramId().length() != 0) {
				try {
					pid = Integer.parseInt(baseActionForm.getProgramId());
					// The below map contains key as id and value as name of state.
					courseMap = CommonAjaxHandler.getInstance().getCourseByProgramForOnline( pid);
					
					//courseMap = CommonAjaxHandler.getInstance().getCourseByProgramForOnlineNew( pid);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), courseMap);
			request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
			return mapping.findForward("ajaxResponseForOptions");
		}





		
		public ActionForward getApplnProgramsByProgramTypeNew(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int ptid;
			Map<Integer, String> programMap = new HashMap<Integer, String>();
			if (baseActionForm.getProgramTypeId() != null
					&& baseActionForm.getProgramTypeId().length() != 0) {
				try {
					// ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
					ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
					// The below map contains key as id and value as name of state.
					programMap = CommonAjaxHandler.getInstance()
							.getApplnProgramsByProgramTypeNew(ptid);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), programMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, programMap);
			return mapping.findForward("ajaxResponseForOptions");
		}

		public ActionForward getPrograms(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int year;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			
				try {
					// ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
					year = Integer.parseInt(baseActionForm.getYear());
					// The below map contains key as id and value as name of state.
					classMap = CommonAjaxHandler.getInstance()
							.getPrograms(year);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
		
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), classMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			return mapping.findForward("ajaxResponseForOptions");
		}	
		
		// vinodha
		public ActionForward getClassesForAttendanceByYear(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int year;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			if (baseActionForm.getYear() != null
					&& baseActionForm.getYear().length() != 0) {
				try {
					year = Integer.parseInt(baseActionForm.getYear());
					// The below map contains key as id and value as name of class.
					classMap = CommonAjaxHandler.getInstance().getClassesForAttendanceByYear(year, baseActionForm.getEvenOrOdd());

				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}

			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), classMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			return mapping.findForward("ajaxResponseForOptions");
		}

		
		// by Arun Sudhakaran
		public ActionForward getSemistersByYearAndProgramType(ActionMapping mapping,
															  ActionForm form, 
															  HttpServletRequest request,
															  HttpServletResponse response) throws Exception 
		{
			BaseActionForm baseActionForm = (BaseActionForm) form;
			
			Map<Integer, String> semMap = new HashMap<Integer, String>();
			
				try {
					int ptid = Integer.parseInt(baseActionForm.getProgramTypeId());
					int year = Integer.parseInt(baseActionForm.getYear());
					semMap = CommonAjaxHandler.getInstance()
							.getSemistersByYearAndProgramType(year, ptid);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
		
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), semMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, semMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		
		//sms raghu
		public ActionForward getClassesByCourseAndYear1(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> rejoinClassMap = new HashMap<Integer, String>();
			try {
				int courseId = 0;
				int year = 0;
				if(baseActionForm.getCourseId()!= null && baseActionForm.getYear()!= null){
					courseId = Integer.parseInt(baseActionForm.getCourseId());
					year = Integer.parseInt(baseActionForm.getYear());
				}
				rejoinClassMap = CommonAjaxHandler.getInstance().getClassesBySelectedCourse1(courseId, year);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), rejoinClassMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, rejoinClassMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		
		// by Arun Sudhakaran
		public ActionForward getSemestersForExams(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> semMap = new HashMap<Integer, String>();
			try {
				int examId = Integer.parseInt(baseActionForm.getExam1Id());				
				semMap = CommonAjaxHandler.getInstance().getSemseterMap(examId);
				
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				
			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), semMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, semMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		
		//for condonation
		public ActionForward getClassesByCourseSemesterAndAcademicYear(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

				BaseActionForm baseActionForm = (BaseActionForm) form;
				int semester;
				int academicyear;
				int programId = 0;
				int courseId = 0;
				int programTypeId = 0;
				
				Map<Integer, String> classMap = new HashMap<Integer, String>();
				if (baseActionForm.getSemister() != null
						&& baseActionForm.getSemister().length() != 0) {
					try {
						semester = Integer.parseInt(baseActionForm.getSemister());
						academicyear = Integer.parseInt(baseActionForm.getAcademicYear());
						if(!baseActionForm.getCourseId().isEmpty())
							courseId = Integer.parseInt(baseActionForm.getCourseId());
						if(!baseActionForm.getProgramTypeId().isEmpty())
							programTypeId = Integer.parseInt(baseActionForm.getProgramTypeId());
						if(!baseActionForm.getProgramId().isEmpty())
							programId = Integer.parseInt(baseActionForm.getProgramId());
						// The below map contains key as id and value as name of class.
						classMap = CommonAjaxHandler.getInstance().getClassesByCourseSemesterAndAcademicYear(semester, academicyear, programTypeId, programId, courseId);
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
				}
				
				if (baseActionForm.getPropertyName() != null) {
					baseActionForm.getCollectionMap().put(
							baseActionForm.getPropertyName(), classMap);
				}
				
				request.setAttribute(CMSConstants.OPTION_MAP, classMap);
				return mapping.findForward("ajaxResponseForOptions");
			}


		public ActionForward condonationRestrict(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			BaseActionForm baseActionForm = (BaseActionForm) form;
			List<AttendanceCondonation> ac = new ArrayList<AttendanceCondonation>(); 
			
			try{
				if(!baseActionForm.getBaseStudentId().isEmpty() && baseActionForm.getBaseStudentId()!=null)
					
					ac = CommonAjaxHandler.getInstance().condonationRestrict(baseActionForm.getBaseStudentId());
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
			
			request.setAttribute("optionList", ac);
			return mapping.findForward("ajaxResponseForCondonationList");	
		}
		
		public ActionForward getClassesByProgramTypeAndAcademicYear1(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

				BaseActionForm baseActionForm = (BaseActionForm) form;
				int programTypeId;
				int academicYear;
				Map<Integer, String> classMap = new HashMap<Integer, String>();
				if (baseActionForm.getProgramTypeId() != null
						&& baseActionForm.getProgramTypeId().length() != 0) {
					try {
						programTypeId = Integer.parseInt(baseActionForm.getProgramTypeId());
						 academicYear=Integer.parseInt(baseActionForm.getAcademicYear());
						// The below map contains key as id and value as name of class.
						classMap = CommonAjaxHandler.getInstance().getClassesByProgramTypeAndAcademicYear1(programTypeId, academicYear);
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
				}
				
				if (baseActionForm.getPropertyName() != null) {
					baseActionForm.getCollectionMap().put(
							baseActionForm.getPropertyName(), classMap);
				}
				
				request.setAttribute(CMSConstants.OPTION_MAP, classMap);
				return mapping.findForward("ajaxResponseForOptions");
		}
		
		public ActionForward getClassMapForTeacherAndDate(ActionMapping mapping,
				  										  ActionForm form,
				  										  HttpServletRequest request,
				  										  HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			try {
				int userId = Integer.parseInt(baseActionForm.getUserId());
				String attendanceDate = baseActionForm.getAttendanceDate();
				Map<Integer, String> subjectMap = CommonAjaxHandler.getInstance().getClassMapForTeacherAndDate(userId, attendanceDate);
				request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getSemisterByYear(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int year;
			Map<Integer, String> semisterMap = new HashMap<Integer, String>();
			if (baseActionForm.getYear() != null
					&& baseActionForm.getYear().length() != 0) {
				try {
					year = Integer.parseInt(baseActionForm.getYear());
					// The below map contains key as id and value as name of class.
					semisterMap = CommonAjaxHandler.getInstance().getSemisterByYear(
							year);

				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}

			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), semisterMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, semisterMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getClassesByYearAndSem(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			BaseActionForm baseActionForm = (BaseActionForm) form;
			int year;
			int semisterNo;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			if (baseActionForm.getYear() != null
					&& baseActionForm.getYear().length() != 0 && baseActionForm.getSemister()!=null) {
				try {
					year = Integer.parseInt(baseActionForm.getYear());
					semisterNo=Integer.parseInt(baseActionForm.getSemister());
					// The below map contains key as id and value as name of class.
					classMap = CommonAjaxHandler.getInstance().getClassesByYearAndSem(
							year,semisterNo);

				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}

			if (baseActionForm.getPropertyName() != null) {
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), classMap);
			}
			request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		
		public ActionForward getClassesByAcademicYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
				BaseActionForm baseActionForm = (BaseActionForm) form;
				Map<Integer, String> classMap = new HashMap<Integer, String>();
				try{
					if((baseActionForm.getAcademicYear() != null && !baseActionForm.getAcademicYear().isEmpty())){
						String academicYear = baseActionForm.getAcademicYear();
						classMap = CommonAjaxHandler.getInstance().getClassByAcademicYear(academicYear);
					}
					 
				}catch (Exception e) {
					log.debug(e.getMessage());
				}
				if (baseActionForm.getPropertyName() != null) {
					baseActionForm.getCollectionMap().put(
							baseActionForm.getPropertyName(), classMap);
				}
				
				request.setAttribute(CMSConstants.OPTION_MAP, classMap);
				return mapping.findForward("ajaxResponseForOptions");
			}
		public ActionForward checkCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws ApplicationException, IOException {
			OnlineApplicationHandler handler=OnlineApplicationHandler.getInstance();
			BaseActionForm baseActionForm = (BaseActionForm) form;
					String result=null;
					
			try{
				result=handler.getCourses(baseActionForm);
				PrintWriter out = response.getWriter();
			      out.print(result);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			 return null;
		}
		public ActionForward getAttendanceType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws ApplicationException, IOException {
			ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
			BaseActionForm baseActionForm = (BaseActionForm) form;
			
					String result=null;
					
					
			try{
				result=transaction.getAttendanceType(baseActionForm);
				PrintWriter out = response.getWriter();
			      out.print(result);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			 return null;
		}
		public ActionForward getSubjectsByTermAndYear(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
			Map<Integer, String> periodMap = new HashMap<Integer, String>();
			HttpSession session = request.getSession(false);
			String roleId=null;
			String year=null;
			String term=null;
			String userId=null;
			if(session.getAttribute("rid")!=null){
				roleId=(session.getAttribute("rid").toString());
			}
			if(session.getAttribute("uid")!=null){
				userId=(session.getAttribute("uid").toString());
			}
			try {

						
						year=baseActionForm.getYear();
						term=baseActionForm.getEvenOrOdd();
						Map<Integer, String> tempMap = CommonAjaxHandler.getInstance()
								.getCourseByyearandTerm(year, term,userId,roleId);
						subjectMap.putAll(tempMap);	



			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			request.setAttribute("periodOptionMap", periodMap);
			request.setAttribute("subjectOptionMap", subjectMap);
			// request.setAttribute("batchOptionMap", batchMap);
			request.setAttribute("timeTable",false);
			return mapping.findForward("ajaxResponseForPeriodSubjectBatch");
		}
		public ActionForward getTeachersForSubjects(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
			Map<Integer, String> periodMap = new HashMap<Integer, String>();
			try {

						String year=null;
						String SubId=null;
						year=baseActionForm.getYear();
						SubId=baseActionForm.getSubjectId();
						Map<Integer, String> tempMap = TimeTableForClassHandler.getInstance().getTeachersMap(SubId);
						subjectMap.putAll(tempMap);	
						request.setAttribute(CMSConstants.OPTION_MAP, tempMap);


			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getClassesForAttendanceByYearAndUserIdAndSubjectId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
				BaseActionForm baseActionForm = (BaseActionForm) form;
				Map<Integer, String> classMap = new HashMap<Integer, String>();
				try{
					if((baseActionForm.getAcademicYear() != null && !baseActionForm.getAcademicYear().isEmpty())){
						int academicYear = Integer.parseInt(baseActionForm.getAcademicYear());
						classMap = TimeTableForClassTransactionImpl.getInstance().getClassesForAttendanceByYearAndUserIdAndSubjectId(academicYear, baseActionForm.getEvenOrOdd(), baseActionForm.getUserId(), baseActionForm.getSubjectId());
					}
					 
				}catch (Exception e) {
					log.debug(e.getMessage());
				}
				if (baseActionForm.getPropertyName() != null) {
					baseActionForm.getCollectionMap().put(
							baseActionForm.getPropertyName(), classMap);
				}
				
				request.setAttribute(CMSConstants.OPTION_MAP, classMap);
				return mapping.findForward("ajaxResponseForOptions");
			}
		
}

