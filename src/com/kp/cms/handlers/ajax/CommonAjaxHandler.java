package com.kp.cms.handlers.ajax;

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
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.AttendanceTypeMandatory;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.MarksCardForm;
import com.kp.cms.helpers.exam.ExamGenHelper;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;
import com.kp.cms.to.exam.ExamClassTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.exam.IMarksCardTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewSelectionScheduleTransImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.transactionsimpl.exam.MarksCardTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * This handler class for Ajax realted work It has methods will loads the data
 * from database send request to ajaxengine.
 */
public class CommonAjaxHandler extends CommonAjaxExamHandler {

	public static volatile CommonAjaxHandler commonAjaxHandler = null;

	public static CommonAjaxHandler getInstance() {
		if (commonAjaxHandler == null) {
			commonAjaxHandler = new CommonAjaxHandler();
		}
		return commonAjaxHandler;
	}

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getStatesByCountryId(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> stateMap = iCommonAjax.getStatesByCountry(id);
		stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
		return stateMap;
	}
	

	/**
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCitiesByStateId(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> cityMap = iCommonAjax.getCitiesByState(id);
		cityMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(cityMap);
		return cityMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCitiesByCountry(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> cityMap = iCommonAjax.getCitiesByCountry(id);
		cityMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(cityMap);
		return cityMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByProgramType(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> programMap = iCommonAjax
				.getProgramsByProgramType(id);
		programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getApplnProgramsByProgramType(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> programMap = iCommonAjax
				.getApplnProgramsByProgramType(id);
		programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getActivityByAttendenceType(Set id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> activityMap = iCommonAjax
				.getActivityByAttendenceType(id);
		activityMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(activityMap);
		return activityMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgram(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getCourseByProgram(id);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	
	public Map<Integer, String> getCourseByProgram(Set<Integer> ids) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		//return iCommonAjax.getDetailsOnClassSchemewiseId(ids);
		Map<Integer, String> courseMap = iCommonAjax.getCourseByProgram(ids);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypeByCourse(int id, int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getinterViewTypybyCourse(
				id, year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypeByProgram(int id, int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getInterviewTypebyProgram(
				id, year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewType(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> interviewSubroundsMap = iCommonAjax
				.getInterviewSubroundsByInterviewType(id);
		interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
		return interviewSubroundsMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByApplicationId(int id,
			int applicationId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> interviewSubroundsMap = iCommonAjax
				.getInterviewSubroundsByApplicationId(id, applicationId);
		interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
		return interviewSubroundsMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCollegeByUniversity(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> collegeMap = iCommonAjax
				.getCollegeByUniversity(id);
		collegeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(collegeMap);
		return collegeMap;
	}
	public List<CollegeTO> getCollegeByUniversityList(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<CollegeTO> collegeMap = iCommonAjax
				.getCollegeByUniversityList(id);
		return collegeMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubReligionByReligion(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> subreligionMap = iCommonAjax
				.getSubReligionByReligion(id);
		subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
		return subreligionMap;
	}

	/**
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectGroupsByCourse(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> subGroupMap = iCommonAjax
				.getSubjectGroupsByCourse(id);
		subGroupMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subGroupMap);
		return subGroupMap;
	}

	public int getCandidateCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId) {
		int candidateCount = 0;

		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		candidateCount = iCommonAjax.getCandidateCount(courseId, programId,
				year, interviewList, examCenterId);
		
		return candidateCount;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getSemistersByYearAndCourse(int year,
			int courseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<CurriculumScheme> curriculumSchemeList = iCommonAjax
				.getSemistersOnYearAndCourse(courseId, year);
		int noOfScheme = 0;
		if (curriculumSchemeList != null && !curriculumSchemeList.isEmpty()) {
			noOfScheme = ((CurriculumScheme) curriculumSchemeList.get(0))
					.getNoScheme();
		}
		Map<Integer, Integer> semistersMap = new HashMap<Integer, Integer>();
		for (int i = 1; i <= noOfScheme; i++) {
			semistersMap.put(Integer.valueOf(i), Integer.valueOf(i));
		}
		semistersMap = (HashMap<Integer, Integer>) CommonUtil.sortMapByValue(semistersMap);
		return semistersMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getSemistersByYearAndCourseScheme(int year,
			int courseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<CurriculumSchemeDuration> curriculumSchemeList = iCommonAjax
				.getSemistersOnYearAndCourseScheme(courseId, year);

		int noOfScheme = 0;
		int schemeDurationId = 0;
		Map<Integer, Integer> semistersMap = new LinkedHashMap<Integer, Integer>();
		if (curriculumSchemeList != null && !curriculumSchemeList.isEmpty()) {
			for (int i = 0; i < curriculumSchemeList.size(); i++) {
				schemeDurationId = ((CurriculumSchemeDuration) curriculumSchemeList
						.get(i)).getId();
				noOfScheme = ((CurriculumSchemeDuration) curriculumSchemeList
						.get(i)).getSemesterYearNo();
				semistersMap.put(schemeDurationId, noOfScheme);
			}
		}
		semistersMap = (HashMap<Integer, Integer>) CommonUtil.sortMapByValue(semistersMap);
		return semistersMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectGroupsByYearAndCourse(int year,
			int courseId, int semister) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
		List<CurriculumScheme> curriculumSchemeList = iCommonAjax
				.getSemistersOnYearAndCourse(courseId, year);
		if(curriculumSchemeList.size()!=0)
		{	
			CurriculumScheme curriculumScheme = ((CurriculumScheme) curriculumSchemeList
					.get(0));
			CurriculumSchemeDuration curriculumSchemeDuration;
			CurriculumSchemeSubject curriculumSchemeSubject;
			Iterator<CurriculumSchemeDuration> itr = curriculumScheme
					.getCurriculumSchemeDurations().iterator();
			while (itr.hasNext()) {
				curriculumSchemeDuration = (CurriculumSchemeDuration) itr.next();
				if (curriculumSchemeDuration.getSemesterYearNo() == semister) {
					Iterator<CurriculumSchemeSubject> itr1 = curriculumSchemeDuration
							.getCurriculumSchemeSubjects().iterator();
					while (itr1.hasNext()) {
						curriculumSchemeSubject = (CurriculumSchemeSubject) itr1
								.next();
						subjectGroupMap.put(curriculumSchemeSubject
								.getSubjectGroup().getId(), curriculumSchemeSubject
								.getSubjectGroup().getName());
					}
					break;
				}
			}
		}	
		subjectGroupMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectGroupMap);
		return subjectGroupMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return classMap <key,value>
	 */

	public Map<Integer, String> getClassesByYear(int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByYear(year);
		return classMap;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYearNew(int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByYearNew(year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYearFromGeneratedTimeTable(int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByYearFromGeneratedTimeTable(year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getPeriodByClassSchemewiseId(
			int classSchemewiseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getPeriodByClassSchemewiseId(classSchemewiseId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getPeriodByClassSchemewiseId(
			Set<Integer> classSchemewiseIds) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getPeriodByClassSchemewiseId(classSchemewiseIds);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	/**
	 * Get Details from Classes on classId
	 */
	public ClassSchemewise getDetailsonClassSchemewiseId(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getDetailsonClassSchemewiseId(id);
		
	}

	/**
	 * Get Details from Classes on classId
	 */
	public List<ClassSchemewise> getDetailsonClassSchemewiseId(Set<Integer> ids) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getDetailsOnClassSchemewiseId(ids);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesByClasses(
			Set<Integer> classSchemewiseIds) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		
		Map<Integer, String> BatchesByClassesMap = iCommonAjax.getBatchesByClassSchemewiseId(classSchemewiseIds);
		BatchesByClassesMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BatchesByClassesMap);
        return BatchesByClassesMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesBySubjectAndClassIds(int subjectId,
			Set<Integer> classSchemewiseIds) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();

		Map<Integer, String> BatchesBySubjectAndClassMap = iCommonAjax.getBatchesBySubjectAndClassScheme(subjectId,
				classSchemewiseIds);
		BatchesBySubjectAndClassMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BatchesBySubjectAndClassMap);
        return BatchesBySubjectAndClassMap;
	}

	/**
	 * Gets subject from curriculumscheme
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectByCourseIdTermYear(int courseId,
			int year, int term) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
	
		Map<Integer, String> SubjectByCourseIdTermYearMap = iCommonAjax.getSubjectByCourseIdTermYear(courseId, year, term);
		SubjectByCourseIdTermYearMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(SubjectByCourseIdTermYearMap);
        return SubjectByCourseIdTermYearMap;
	}

	public List<AttendanceTypeMandatoryTO> getMandatoryByAttenadnce(
			int attendanceId) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		AttendanceType attendanceType = iCommonAjax
				.getMandatoryByAttenadnce(attendanceId);
		List<AttendanceTypeMandatoryTO> list = new ArrayList<AttendanceTypeMandatoryTO>();
		AttendanceTypeMandatory attendanceTypeMandatory;
		AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
		Set<String> mandatorySet = new HashSet<String>();
		if (attendanceType != null) {
			Iterator<AttendanceTypeMandatory> itr = attendanceType
					.getAttendanceTypeMandatories().iterator();
			while (itr.hasNext()) {
				attendanceTypeMandatory = itr.next();
				if (attendanceTypeMandatory.getIsActive()) {
					attendanceTypeMandatoryTO = new AttendanceTypeMandatoryTO();
					attendanceTypeMandatoryTO.setName(attendanceTypeMandatory
							.getName());
					attendanceTypeMandatoryTO.setIsMandatory("yes");
					mandatorySet.add(attendanceTypeMandatory.getName());
					list.add(attendanceTypeMandatoryTO);
				}
			}
			String[] all = CMSConstants.MANDATORY_FIELDS;
			for (String str : all) {
				if (!mandatorySet.contains(str)) {
					attendanceTypeMandatoryTO = new AttendanceTypeMandatoryTO();
					attendanceTypeMandatoryTO.setName(str);
					attendanceTypeMandatoryTO.setIsMandatory("no");
					list.add(attendanceTypeMandatoryTO);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectDetailsByCourse(int courseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> SubjectDetailsByCourseMap = iCommonAjax.getSubjectsDetailsByCourse(courseId);
		SubjectDetailsByCourseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(SubjectDetailsByCourseMap);
        return SubjectDetailsByCourseMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getFloorsByHostel(int hostelId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> FloorsByHostelMap = iCommonAjax.getFloorsByHostel(hostelId);
		FloorsByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(FloorsByHostelMap);
        return FloorsByHostelMap;
	}

	/**
	 * removes duplicate from preference list
	 * 
	 * @param prefcourses
	 * @param selectedId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getUpdatedPreferenceList(
			List<CourseTO> prefcourses, int selectedId) {
		Map<Integer, String> prefMap = new HashMap<Integer, String>();
		if (prefcourses != null) {
			Iterator<CourseTO> crsItr = prefcourses.iterator();
			while (crsItr.hasNext()) {
				CourseTO prefTO = (CourseTO) crsItr.next();
				if (prefTO.getId() == selectedId) {
					crsItr.remove();
				} else {
					prefMap.put(prefTO.getId(), prefTO.getName());
				}
			}
		}
		prefMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(prefMap);
		return prefMap;
	}

	/**
	 * 
	 * @param
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getHlGroupNameByHostel(int hostelId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		
		Map<Integer, String> HlGroupNameByHostelMap = iCommonAjax.getHlGroupsByHostel(hostelId);
		HlGroupNameByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(HlGroupNameByHostelMap);
        return HlGroupNameByHostelMap;
	}
	

	/**
	 * 
	 * @param
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomTypeByHostel(int hostelId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		 
		
		Map<Integer, String> RoomTypeByHostelMap = iCommonAjax.getRoomTypeByHostel(hostelId);
		RoomTypeByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(RoomTypeByHostelMap);
        return RoomTypeByHostelMap;
	}
	

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsByHostel(int hostelId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		 
		Map<Integer, String> RoomsByHostelMap = iCommonAjax.getRoomsByHostel(hostelId);
		RoomsByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(RoomsByHostelMap);
        return RoomsByHostelMap;
	
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return employeeMap <key,value> by Department
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getEmployeesByDepartment(int departmentId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> employeeMap = iCommonAjax
				.getEmployeesByDepartment(departmentId);
		employeeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(employeeMap);
		return employeeMap;
	}

	/**
	 * 
	 * @param programId
	 * @returns classes by program
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByProgram(int programId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getClassesByProgram(programId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypeByCourseWithoutYear(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax
				.getinterViewTypybyCourseWithoutYear(id);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	/**
	 * \
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypeByProgramWithoutYear(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax
				.getInterviewTypebyProgramWithoutYear(id);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	/**
	 * 
	 * @param programTypeId
	 * @returns classes by programTypeId
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByProgramTypeId(int programTypeId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getClassesByProgramType(programTypeId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	// petticash related
	public String getStudentName(int optionNo, String appRegRollno,
			String academicYear, HttpServletRequest request) throws Exception {
		String candidateName = null;

		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Object[]> studentNameList = iCommonAjax.getStudentName(optionNo,
				appRegRollno, academicYear,request);

		return covertToString(studentNameList);
	}

	public static String covertToString(List<Object[]> studentNameList) {
		Iterator<Object[]> it = studentNameList.iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()) {
			Object[] string = it.next();
			if (string[0] != null) {
				buffer.append((String) string[0]);
			}
			if (string[1] != null) {
				buffer.append(" " + (String) string[1]);
			}
			if (string[2] != null) {
				buffer.append(" " + (String) string[2]);
			}
		}

		return buffer.toString();
	}

	public PcAccountHead getAmount(int accid) throws Exception {
		PcAccountHead pcAccountHead = null;

		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		pcAccountHead = iCommonAjax.getAmount(accid);
		return pcAccountHead;
	}

	// Added Hostel Admin Message
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getHostelRoomsMapByHostelId(String hostelId) {
		Map<Integer, String> roomsMap = null;
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		roomsMap = iCommonAjax.getRoomsMapByHostelId(hostelId);
		roomsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomsMap);
		return roomsMap;
	}

	// End Hostel Admin Message
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsByFloors(int hostelId, int flooNo, int roomTypeId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> roomByFloorMap= iCommonAjax.getRoomsByFloors(hostelId, flooNo, roomTypeId);
		roomByFloorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomByFloorMap);
		return roomByFloorMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBedByRoomId(int roomTypeId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		 
		Map<Integer, String> BedByRoomMap= iCommonAjax.getBedByRoomId(roomTypeId);
		BedByRoomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BedByRoomMap);
		return BedByRoomMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomTypesByHostel(int hostelId)
			throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		
		Map<Integer, String> RoomTypesByHostelAndByFloorMap= iCommonAjax.getRoomTypesByHostelAndByFloor(hostelId);
		RoomTypesByHostelAndByFloorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(RoomTypesByHostelAndByFloorMap);
		return RoomTypesByHostelAndByFloorMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassCodeByExamName(int examName) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
	
		Map<Integer, String> ClassCodeByExamNameMap= iCommonAjax.getClassCodeByExamName(examName);
		ClassCodeByExamNameMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(ClassCodeByExamNameMap);
		return ClassCodeByExamNameMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamCenterForProgram(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> examCenterMap = iCommonAjax
				.getExamCenterForProgram(id);
		examCenterMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examCenterMap);
		return examCenterMap;
	}

	/**
	 * @author jboss
	 * @param courseId
	 * @param year
	 * @param term
	 * @param teacher
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getSubjectByCourseIdTermYearTeacher(int teacher,String[] selectedClasses,HttpSession session,boolean subjectChange) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		 
		Map<String, String> SubjectByCourseIdTermYearTeacherMap = iCommonAjax.getSubjectByCourseIdTermYearTeacher(teacher, selectedClasses,session,subjectChange);
		SubjectByCourseIdTermYearTeacherMap = (HashMap<String, String>) CommonUtil.sortMapByValue(SubjectByCourseIdTermYearTeacherMap);
        return SubjectByCourseIdTermYearTeacherMap;

	}

	/**
	 * @author jboss
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByTeacher(int teacherId, String year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByTeacher(
				teacherId, year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByAdmitedYear(int year)
			throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> examCenterMap = iCommonAjax
				.getProgramsByAdmitedYear(year);
		examCenterMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examCenterMap);
		return examCenterMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYearForMuliSelect(int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> examCenterMap = iCommonAjax
				.getClassesByYearForMuliSelect(year);
		examCenterMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examCenterMap);
		return examCenterMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getPeriodByClassSchemewiseIds(
			String[] classSchemeWiseIds) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getPeriodByClassSchemewiseIds(classSchemeWiseIds);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsByClassSchemewiseIds(
			ArrayList<Integer> classSchemeWiseIds) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> roomMap = iCommonAjax
				.getRoomsByClassSchemewiseIds(classSchemeWiseIds);
	roomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomMap);
		return roomMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getEducationByQualificationId(
			int qualificationId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax
				.getEducationByQualificationId(qualificationId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	/**
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSpecializationByCourse(int courseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> specializationMap = iCommonAjax
				.getSpecializationByCourse(courseId);
		specializationMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(specializationMap);
		return specializationMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesByActivityAndClassIds(int subjectId,
			Set<Integer> classSchemewiseIds) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classSchemewiseMap = iCommonAjax.getBatchesByActivityAndClassScheme(subjectId,
				classSchemewiseIds);
		classSchemewiseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classSchemewiseMap);
		return classSchemewiseMap;
		
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesBySelectedCourse(int courseid,
			Integer year) throws Exception {
		ICommonAjax txn = new CommonAjaxImpl();
		Map<Integer, String> map = new HashMap<Integer, String>();
		/*List<Object[]> objList = txn.getYearandTermNo(courseid, year);
		Iterator<Object[]> it = objList.iterator();
		int semNo = 0;
		int year1 = 0;
		if (objList != null) {
			while (it.hasNext()) {
				Object[] objects = (Object[]) it.next();
				if (objects[0] != null) {
					semNo = Integer.parseInt(objects[0].toString());
				}
				if (objects[1] != null) {
					year1 = Integer.parseInt(objects[1].toString());
				}
				if (semNo > 0 && year1 > 0) {
					List<ClassSchemewise> classList = txn
							.getClassSchemeByCourseId(semNo, year1, courseid);
					ClassSchemewise classSchemewise;
					Iterator<ClassSchemewise> itr = classList.iterator();
					while (itr.hasNext()) {
						classSchemewise = itr.next();
						map.put(classSchemewise.getId(), classSchemewise
								.getClasses().getName());
					}
				}
			}
		}*/
		List<ClassSchemewise> classList = txn.getClassSchemeByCourseId(0, year, courseid);
		Iterator<ClassSchemewise> iterator = classList.iterator();
		while (iterator.hasNext()) {
			ClassSchemewise classSchemewise = (ClassSchemewise) iterator.next();
			map.put(classSchemewise.getId(), classSchemewise
					.getClasses().getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String>getSectionsByCourseAndSemester(int courseId,int year)
	{
		Map<String, String> map = new HashMap<String, String>();
		ICommonAjax txn = new CommonAjaxImpl();
		List<String> objList = txn.getSectionsByCourseAndSemester(courseId,year);
		Iterator<String> it = objList.iterator();
		if (objList != null && objList.size()!=0) {
			while (it.hasNext()) 
			{
				String objects = (String) it.next().trim();
				if(objects.length()!=0)
					map.put(objects,objects);
			}
		}
		map = (HashMap<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgramInOrder(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getCourseByProgramInOrder(id);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	public int getCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId,String stime,String etime,List<Date> dateList) {
		int candidateCount = 0;

		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		candidateCount = iCommonAjax.getCount(courseId, programId,
				year, interviewList, examCenterId,stime,etime,dateList);
		
		return candidateCount;
	}
	
	// End Hostel Admin Message
	public Map<Integer, String> getRoomsByFloor(int hostelId, int flooNo) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getRoomsByFloor(hostelId, flooNo);
		
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewTypeForMultiSelect(
			String[] arrayList) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> interviewSubroundsMap = iCommonAjax
				.getInterviewSubroundsByInterviewTypeForMultiSelect(arrayList);
		interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
		return interviewSubroundsMap;
	}

	public String getRegisterNo(String ipAddress) 
	{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getRegisterNo(ipAddress);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getEmployeeCodeName(String eCodeName) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return CommonUtil.sortMapByValue(iCommonAjax.getEmployeeCodeName(eCodeName));
	
	}

	public Map<Integer, String> getSubjectGroupByFeeAdditionalForMultiSelect(String[] arrayList) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getSubjectGroupByFeeAdditionalForMultiSelect(arrayList);
		
	}

	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getAttendanceSubjectsByRegisterNo(String regNo) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getAttendanceSubjectsByRegisterNo(regNo);
	}
	public Map<Integer, String> getClassCodeByExamNameWithYear(int examName) {
		ExamGenHelper helper = new ExamGenHelper();

		ExamGenImpl impl = new ExamGenImpl();
		
		ArrayList<ExamClassTO> list = helper.convertTOTO(impl
				.getCourseIdSchemeNo(examName));
		List<KeyValueTO> listValues = new ArrayList();
		for (ExamClassTO examClassTO : list) {
			impl.getClassValuesByCourseIdWithYear(examClassTO.getCourseId(),
					examClassTO.getCourseSchemeId(), examClassTO.getSchemeNo(),
					listValues, examName);

		}
		
		return helper.convertTOClassMap(listValues);
		
		//ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		//return iCommonAjax.getClassCodeByExamName(examName);
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCoursesByYear(int year) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getCoursesByYear(year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	// Down Three Methods added by Balaji For the Change of Practical Batch (Class Schemewise Id instead of batch to BatchStudent)
	/**
	 * @param classSchemewiseIds
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getBatchesByClasses1(Set<Integer> classSchemewiseIds) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		
		Map<Integer, String> BatchesByClassesMap = iCommonAjax.getBatchesByClassSchemewiseId1(classSchemewiseIds);
		BatchesByClassesMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BatchesByClassesMap);
        return BatchesByClassesMap;
	}
	/**
	 * @param subjectId
	 * @param classSchemewiseIds
	 * @return
	 * @throws Exception
	 * code changed by mehaboob  getBatchesBySubjectAndClassIds1
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesBySubjectAndClassIds1(String subjectId,Set<Integer> classSchemewiseIds,HttpSession session) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> BatchesBySubjectAndClassMap = iCommonAjax.getBatchesBySubjectAndClassScheme1(subjectId,classSchemewiseIds,session);
		BatchesBySubjectAndClassMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BatchesBySubjectAndClassMap);
        return BatchesBySubjectAndClassMap;
	}
	/**
	 * @param subjectId
	 * @param classSchemewiseIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesByActivityAndClassIds1(int subjectId,Set<Integer> classSchemewiseIds) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classSchemewiseMap = iCommonAjax.getBatchesByActivityAndClassScheme1(subjectId,classSchemewiseIds);
		classSchemewiseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classSchemewiseMap);
		return classSchemewiseMap;
	}

	/**
	 * @param employeeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkTeachingStaff(String employeeId) throws Exception {
		String query="from Employee e where e.id=" +employeeId+
				" and e.teachingStaff=true";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	
	public boolean checkGuestTeachingStaff(String guestId) throws Exception {
		String query="from GuestFaculty e where e.id=" +guestId+
				" and e.teachingStaff=true";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	public List<String> getDynamicFingerPrintIds(String fingerPrintId,String userId) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<String> fingerPrintIds=iCommonAjax.getDynamicFingerPrintIds(fingerPrintId, userId);
		return fingerPrintIds;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgramForOnline(int id) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getCourseByProgramForOnline(id);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	/**
	 * @param examType
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamNameByExamTypeAndYear(String examType, int year) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> examMap = iCommonAjax.getExamNameByExamTypeAndYear(examType,year);
		examMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examMap);
		return examMap;
	}
	
	/**
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseByYear(int year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getCourseByYear(year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	public Map<Integer, String> getClassesByCourse(int courseId,int year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByCourse(courseId,year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	/**
	 * @param teacherId
	 * @param year
	 * @return
	 */
	public AttendanceTO getPeriodsByTeacher(String teacherId, String year,String day,String date) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getPeriodsByTeacher(teacherId,year,day,date);
	}

	/**
	 * @param parameter
	 * @param string
	 * @param parameter2
	 * @return
	 */
	public Map<Integer, String> getClassesByPeriods(String teachers, String year, String periods,String day,String date) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getClassesByPeriodsAndTeachers(teachers,year,periods,day,date);
	}

	/**
	 * @param teachers
	 * @param year
	 * @param periods
	 * @return
	 * @throws Exception
	 */
	public AttendanceTO getSubjectsByPeriods(String teachers, String year, String periods,String day,String date,String periodName,HttpSession session) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getSubjectsByPeriodsAndTeachers(teachers,year,periods,day,date,periodName,session);
	}

	/**
	 * @param teacherId
	 * @param year
	 * @param periods
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getBatchesByPeriods(String teacherId, String year, String periods, String day,String date) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		return iCommonAjax.getBatchesByPeriodsAndTeachers(teacherId,year,periods,day,date);
	}

	/**
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getItemSubCategory(String categoryId) throws Exception {
		ICommonAjax iCommonAjax=CommonAjaxImpl.getInstance();
		return iCommonAjax.getSubCategoryMap(categoryId);
	}
	/**
	 * @param subjectGroupId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getSpecializationBySubGrpWithoutCommSubjAndSecndLang(
			String subjectGroupId) throws Exception{
		ICommonAjax iCommonAjax=CommonAjaxImpl.getInstance();
		return iCommonAjax.getSpecializationBySubGrpWithoutCommSubjAndSecndLang(subjectGroupId);
	}
	/**
	 * @param campusId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getInvLocation(String campusId) throws Exception {
		ICommonAjax iCommonAjax=CommonAjaxImpl.getInstance();
		return iCommonAjax.getInvLocationMap(campusId);
	}
	/**
	 * @param appNo
	 * @return
	 * @throws Exception
	 */
	public String getName(String appNo) throws Exception {
		String query="select appln.personalData.firstName from AdmAppln appln where appln.applnNo='"+appNo+"'";
		ICommonAjax iCommonAjax=CommonAjaxImpl.getInstance();
		String name=iCommonAjax.getName(query);
		return name;
	}
	public boolean checkReceivedThrough(String receivedThrough,HttpServletRequest request) throws Exception {
		String query="from ReceivedThrough receive where receive.receivedThrough = '"+receivedThrough+"'";
		ICommonAjax iCommonAjax=CommonAjaxImpl.getInstance();
		boolean isAvailable=iCommonAjax.checkReceivedThrough(query,request);
		return isAvailable;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectByYear(String year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> subjectmap = iCommonAjax.getSubjectByYear(year);
		subjectmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectmap);
		return subjectmap;
	}

	public Map<Integer, String> getExamByYear(String year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> exammap = iCommonAjax.getExamByYear(year);
		exammap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(exammap);
		return exammap;
	}

	/**
	 * Loads the courses which is assigned for the appln no range and year entered
	 * @param appNo
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseByApplnNoYear(String appNo, String year)  throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> coursesmap = iCommonAjax.getCourseByApplnNoYear(appNo,year);
		//coursesmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(coursesmap);
		return coursesmap;
	}
	public Map<Integer, String> getClassByYearUserId(int year, int teacherId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassByYearUserId(year,teacherId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	/**
	 * \
	 * 
	 * @param courses
	 * @return cityMap <key,value>
	 * @throws Exception 
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypeByCourseNew(String courses, int year) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Integer> courseList = new ArrayList<Integer>();
		String[] courseIds = courses.split(",");
		for (int i = 0; i < courseIds.length; i++) {
			if(courseIds[i] != null){
				courseList.add(Integer.parseInt(courseIds[i]));
			}
		}
		Map<Integer, String> courseMap = iCommonAjax.getinterViewTypybyCourseNew(
				courseList, year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	/**
	 * @param interviewTypeName
	 * @param courseIds 
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewTypeNew(String interviewTypeName, String courseIds, String year) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Integer> courseList = new ArrayList<Integer>();
		String[] coursees = courseIds.split(",");
		for (int i = 0; i < coursees.length; i++) {
			if(coursees[i] != null){
				courseList.add(Integer.parseInt(coursees[i]));
			}
		}
		Map<Integer, String> interviewSubroundsMap = iCommonAjax.getInterviewSubroundsByInterviewTypeNew(interviewTypeName,courseList,year);
		interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
		return interviewSubroundsMap;
	}

	/**
	 * @param courses
	 * @param valueOf
	 * @return
	 * @throws Exception
	 */
	public Map getInterviewTypeByCoursees(String[] courses, Integer year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Integer> courseList = new ArrayList<Integer>();
		for (int i = 0; i < courses.length; i++) {
			if(courses[i] != null){
				courseList.add(Integer.parseInt(courses[i]));
			}
		}
		Map<Integer, String> courseMap = iCommonAjax.getinterViewTypybyCourseNew(courseList, year);
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	public Map getInterviewSubroundsByInterviewTypeNew(String[] courses,String interviewTypeId,String year)  throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Integer> courseList = new ArrayList<Integer>();
		for (int i = 0; i < courses.length; i++) {
			if(courses[i] != null){
				courseList.add(Integer.parseInt(courses[i]));
			}
		}
		Map<Integer, String> interviewSubroundsMap = iCommonAjax.getInterviewSubroundsByInterviewTypeNew(interviewTypeId,courseList,year);
		interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
		return interviewSubroundsMap;
	}

	/**
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getFacultyByDepartment(int departmentId) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> facultyMap = iCommonAjax.getFacultyByDepartmentWise(departmentId);
		Map<Integer, String> facultyMap1 = iCommonAjax.getGuestFacultyByDepartmentId(departmentId,facultyMap);
		Map<Integer, String> facultyMap2 = iCommonAjax.getUsersByDepartment(departmentId,facultyMap1);
		return facultyMap2;
	}
	/**
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getYearWiseDocuments(String year) throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> map = iCommonAjax.getYearWiseDocuments(year);
		return map;
	}
	public Map<Integer, String> getExamNameByYear(String year) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> examMap = iCommonAjax.getExamNameByYear(year);
		examMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examMap);
		return examMap;
	}

	 public Map<Integer, String> getClassesForProgram(String programTypeId, String year) throws Exception{
	     ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
	     Map<Integer, String> map = iCommonAjax.getClassesForProgram(programTypeId, year);
	     return map;
	 }

	 /**
		 * Get Details from Classes on classId
	 * @throws Exception 
		 */
	 public ClassSchemewise getDetailsonClassSchemewiseIdNew(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getDetailsonClassSchemewiseIdNew(id);
			
		}
	 public List<ClassSchemewise> getDetailsonClassSchemewiseIdNew(Set<Integer> ids) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getDetailsOnClassSchemewiseIdNew(ids);
		}
	 public String getEmpanelmentNoByguideName(String name,HttpServletRequest request) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			String studentNameList = iCommonAjax.getEmpanelmentNoByguideName(name,request);
			return studentNameList;
		}
	 public Map<Integer, String> getSpecializationByClassId( Set<Integer> classesIdsSet)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			 Map<Integer, String> map = iCommonAjax.getSpecializationByClassId(classesIdsSet);
			return map;
		}

	public String getStudentNameInHostel(String regNo, String applNo,HttpServletRequest request) throws Exception{
		String candidateName = null;
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List<Object[]> studentNameList = iCommonAjax.getStudentNameInHostel(regNo,applNo,request);
		return covertToStringHostel(studentNameList);
	}

	public List getStudentNameClass(String regNo, String applNo,String academicYear,String hostelApplNo,HttpServletRequest request) throws Exception{
		String candidateName = null;
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		List studentNameList = iCommonAjax.getStudentNameClass(regNo,applNo,academicYear,hostelApplNo,request);
		
		return studentNameList;
	}
	public Map<Integer, String> setRoomByRoomType(int roomTypeId)  throws Exception{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		 
		Map<Integer, String> BedByRoomMap= iCommonAjax.getRoomByRoomType(roomTypeId);
		BedByRoomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BedByRoomMap);
		return BedByRoomMap;
	}

	public BigDecimal getNumberOfSeatsAvailable(String hostel, String roomtype,	String year, HttpServletRequest request) throws Exception{
		String candidateName = null;
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		BigDecimal studentNameList = iCommonAjax.getNumberOfSeatsAvailable(hostel,roomtype,year,request);
		return studentNameList;
	}
	 public Map<Integer, String> getCoursesByProgramId( Set<Integer> programIdsSet)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			 Map<Integer, String> map = iCommonAjax.getCourseByProgramId(programIdsSet);
			return map;
		}
	 
		@SuppressWarnings("unchecked")
		public static String covertToStringHostel(List<Object[]> studentNameList) {
			Iterator<Object[]> it = studentNameList.iterator();
			StringBuffer buffer = new StringBuffer();
			while (it.hasNext()) {
				Object[] string = it.next();
				if (string[0] != null) {
					buffer.append((String) string[0]);
				}
				if (string[1] != null) {
					buffer.append(" " + (String) string[1]);
				}
				if (string[2] != null) {
					buffer.append(" " + (String) string[2]);
				}if (string[3] != null) {
					buffer.append(";" + (String) string[3]);
				}
				
			}

			return buffer.toString();
		}

		

		public Map<Integer, String> getHostelBygender(String hostelGender) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> hostelmap = iCommonAjax.getHostelBygender(hostelGender);
			hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
	        return hostelmap;
		}

		public Map<Integer, String> getRoomTypeByHostelBYstudent(int hostelId)  throws Exception{	
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			
			Map<Integer, String> RoomTypeByHostelMap = iCommonAjax.getRoomTypeByHostelBYstudent(hostelId);
			RoomTypeByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(RoomTypeByHostelMap);
	        return RoomTypeByHostelMap;
		}

		public Map<Integer, String> getHostel() throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> hostelmap = iCommonAjax.getHostel();
			hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
	        return hostelmap;
		}
		
		public Map<Integer, String> getRoomsAvailable(String hstlName,String RoomType,String academicYear,String block,String unit,String floor) throws Exception
		{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> hostelmap = iCommonAjax.getRoomsAvailable(hstlName, RoomType,academicYear, block, unit,floor);
			hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
	        return hostelmap;
		}
		
		
		public Map<Integer, String> getBedsAvailable(String roomId, int academicYear) throws Exception
		{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			int room=Integer.parseInt(roomId);
			Map<Integer, String> hostelmap = iCommonAjax.getBedsAvailable(room, academicYear);
			hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
	        return hostelmap;
		}
		public Map<Integer, String> getBedByRoom(int roomId) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			 
			Map<Integer, String> BedByRoomMap= iCommonAjax.getBedByRoom(roomId);
			BedByRoomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BedByRoomMap);
			return BedByRoomMap;
		}
		
		public Map<Integer, String> getBedByRoomCheckIn(int roomId,int allottedBed, int academicYear) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			 
			Map<Integer, String> BedByRoomMap= iCommonAjax.getBedByRoomCheckIn(roomId, allottedBed, academicYear);
			BedByRoomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BedByRoomMap);
			return BedByRoomMap;
		}
		
		
		
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getRoomsByFloorsCheckIn(int hostelId, int flooNo, int roomTypeId, int allottedRoom,  int academicYear) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> roomByFloorMap= iCommonAjax.getRoomsByFloorsCheckIn(hostelId, flooNo, roomTypeId, allottedRoom, academicYear );
			roomByFloorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomByFloorMap);
			return roomByFloorMap;
		}
		
		public Map<Integer,String> getSupplementryExamNameByAcademicYear( String academicYear) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer,String> suppExamNameMap = iCommonAjax.getSupplementryExamNameByAcademicYear(academicYear);
			suppExamNameMap = (HashMap<Integer, String>)CommonUtil.sortMapByValue(suppExamNameMap);
			return suppExamNameMap;
		}
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getClassesBySemAndYear(int year,String semester)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			List<Integer> list=iCommonAjax.getTermNumber();
			List oddList=new ArrayList();
			List evenList=new ArrayList();
			Iterator iterator=list.iterator();
			while (iterator.hasNext()) {
				int num = Integer.valueOf((Integer)iterator.next());
				if(num%2==0)
					evenList.add(num);
				else
					oddList.add(num);
				
			}
			Map<Integer, String> classMap;
			if(semester.equalsIgnoreCase("odd")){
				classMap = iCommonAjax.getClassesBySemAndYear(year,oddList);
			}else{
				classMap = iCommonAjax.getClassesBySemAndYear(year,evenList);
			}
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getClassesByTeacherAndDate(int teacherId, String year,String day,String date)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByTeacherAndDate(teacherId, year,day,date);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		public HlAdmissionBo getStudentDetailsForVisitors(String year,String regNo)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			HlAdmissionBo hlAdmissionBo=iCommonAjax.getHlAdmissionBo(year,regNo);
			return hlAdmissionBo;
		}
		@SuppressWarnings("unchecked")
		public Boolean checkChildRegisterNo(String regNo) {
			Boolean b=false;
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			List<Student> list= iCommonAjax.checkRgNoDuplicate(regNo);
			if(list!=null&& !list.isEmpty()){
				b=true;
			}
			return b;
		}
		
		public String getExamDateBySubject(String examId, String subjectId, HttpServletRequest request) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			String date = iCommonAjax.getExamDateBySubject(examId, subjectId, request);
			return date;
		}
		
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getStates(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> stateMap = iCommonAjax.getStatesByCountry(id);
			stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
			return stateMap;
		}
		/**
		 * get amount by category
		 * @param actionForm
		 * @return
		 * @throws Exception
		 */
		public String getAmountByCategory(BaseActionForm actionForm) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			String amount=iCommonAjax.getAmountByCategory(Integer.parseInt(actionForm.getHostelId()));
			return amount;
			
		}

		/**
		 * @param pid
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getEmployeeByprogramId(int pid) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> employeeMap = iCommonAjax.getEmployeeByprogramId(pid);
			employeeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(employeeMap);
			return employeeMap;
		}

		/**
		 * @param check
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getInternalGuide(String check) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> guideMap = iCommonAjax.getInternalGuide(check);
			guideMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(guideMap);
			return guideMap;
		}
		/**
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getDepartments() throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> deptMap = iCommonAjax.getDepartments();
			deptMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(deptMap);
			return deptMap;
		}
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getBlockByHostel(int hostelId)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> blockByHostelMap = iCommonAjax.getBlockByHostel(hostelId);
			blockByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(blockByHostelMap);
	        return blockByHostelMap;
		}
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getUnitByBlock(int blockId)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> unitMap = iCommonAjax.getUnitByBlock(blockId);
			unitMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(unitMap);
	        return unitMap;
		}

		public Boolean checkDupilcateOfStaffId(String staffId)throws Exception {
			Boolean flag=false;
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			List<Integer> list= iCommonAjax.checkDupilcateOfStaffId(staffId);
			if(list!=null&& !list.isEmpty()){
				flag=true;
			}
			return flag;
		}
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getBlockByLocation(int locationId) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> blockMap = iCommonAjax.getBlockByLocation(locationId);
			blockMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(blockMap);
			return blockMap;
		}
		/**
		 * @param workLocationId
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getVenueByWorkLocation(int workLocationId)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> venueMap = iCommonAjax.getvenueByWorkLocation(workLocationId);
			venueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(venueMap);
	        return venueMap;
		}
		/**
		 * @param hostelId
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getCoursebyHostel(int hostelId)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> hostelByCourseMap = iCommonAjax.getCoursebyHostel(hostelId);
			hostelByCourseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelByCourseMap);
	        return hostelByCourseMap;
		}
		/**
		 * @param hostelId
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getClassByHostel(int hostelId)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> hostelByClassMap = iCommonAjax.getClassByHostel(hostelId);
			hostelByClassMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelByClassMap);
	        return hostelByClassMap;
		}
		public Map<Integer, String> getFloorsByUnit(int unitId) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> FloorsByHostelMap = iCommonAjax.getFloorsByUnit(unitId);
			FloorsByHostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(FloorsByHostelMap);
	        return FloorsByHostelMap;
		}

		public Map<Integer, String> getClassesForProgm(String programTypeId,
				String year, Map<Integer, String> selClassesMap) throws Exception{
		     ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		     Map<Integer, String> map = iCommonAjax.getClassesForProgram(programTypeId, year);
		     Iterator<Map.Entry<Integer, String>> iterator=selClassesMap.entrySet().iterator();
		     while (iterator.hasNext()) {
				Map.Entry<java.lang.Integer, java.lang.String> entry = (Map.Entry<java.lang.Integer, java.lang.String>) iterator.next();
				map.remove(entry.getKey());
			}
		     return map;
		 }
		
		public Map<Integer, String> getProgramBydeanaryNameAndExam(String deanaryName, int examId) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> examMap = iCommonAjax.getProgramBydeanaryNameAndExam(deanaryName,examId);
			examMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examMap);
			return examMap;
		}
		
		/**
		 * @param programId
		 * @param deaneryName
		 * @return
		 */
		public Map<Integer, String> getClassesByProgramAndAcademicYear(int programId, String deaneryName) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByProgramAndAcademicYear(programId, deaneryName);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		
		/**
		 * @param programTypeId
		 * @param deaneryName
		 * @return
		 */
		public Map<Integer, String> getClassesByProgramTypeAndAcademicYear(int programTypeId, String deaneryName) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByProgramTypeAndAcademicYear(programTypeId, deaneryName);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		
		/**
		 * @param courseId
		 * @param deaneryName
		 * @return
		 */
		public Map<Integer, String> getClassesByCourseAndAcademicYear(int courseId, String deaneryName) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByCourseAndAcademicYear(courseId, deaneryName);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}

		public boolean duplicateCheckingOfOrderNoByLocationId(
				String orderNo, String locationId) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			boolean flag=iCommonAjax.duplicateCheckingOfOrderNoByLocationId(orderNo, locationId);
			return flag;
		}
		/**
		 * @param semester
		 * @param deaneryName
		 * @param courseId 
		 * @param programId 
		 * @param programTypeId 
		 * @return
		 */
		public Map<Integer, String> getClassesBySemesterAndAcademicYear(int semester, String deaneryName, int programTypeId, int programId, int courseId) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesBySemesterAndAcademicYear(semester, deaneryName, programTypeId, programId, courseId);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		/**
		 * department by stream
		 * @param streamId
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getDepartmentByStream(String streamId) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> deptMap = iCommonAjax.getDepartmentByStream(streamId);
			return deptMap;
		}

		public Map<Integer, String> getRoomNosByCampus(String locationId) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> roomNoMap = iCommonAjax.getRoomNosByCampus(locationId);
			return roomNoMap;
		}

		public Map<Integer, String> getFacultyByCampus(String locationId) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> facultyMap = iCommonAjax.getFacultyByCampus(locationId);
			return facultyMap;
		}

		public boolean checkDuplicateInvigilator(BaseActionForm baseActionForm) throws Exception{
			boolean flag=false;
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("from ExamInviligatorDuties e where e.isActive=1 and e.examDate='"+CommonUtil.ConvertStringToSQLDate(baseActionForm.getDate())+
					"' and e.id !="+baseActionForm.getExamid()+" and e.teacherId.id="+Integer.parseInt(baseActionForm.getUserId()));
			if(baseActionForm.getGuestId().equalsIgnoreCase("R")){
				stringBuilder.append(" and e.invOrReliver='"+baseActionForm.getGuestId()+"'");
			}
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			List<ExamInviligatorDuties> list=iCommonAjax.checkDuplicateInvigilator(stringBuilder.toString());
			if(baseActionForm.getGuestId().equalsIgnoreCase("I")){
				if(list!=null && !list.isEmpty()){
					flag=true;
					Iterator<ExamInviligatorDuties> iterator=list.iterator();
					while (iterator.hasNext()) {
						ExamInviligatorDuties examInviligatorDuties = (ExamInviligatorDuties) iterator.next();
							if(!examInviligatorDuties.getSession().equalsIgnoreCase(baseActionForm.getStime())){
								baseActionForm.setExamType("duplicate");
							}
					}
				}
			}
			return flag;
		}

		public Map<Integer, String> facultyByCampusDeptAndDeanery(
				String locationId, String departmentId, String deanaryName) throws Exception{
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("from ExamInvigilatorAvailable e where e.isActive=1 and e.workLocationId.id="+Integer.parseInt(locationId));
			if(departmentId!=null && !departmentId.isEmpty()){
				stringBuilder.append(" and e.department.id="+Integer.parseInt(departmentId));
			}
			if(deanaryName!=null && !deanaryName.isEmpty()){
				stringBuilder.append(" and e.department.employeeStreamBO.id="+Integer.parseInt(deanaryName));
			}
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> facultyMap = iCommonAjax.facultyByCampusDeptAndDeanery(stringBuilder.toString());
			return facultyMap;
		}

		public boolean checkDuplicateExemption(BaseActionForm baseActionForm) throws Exception{
			boolean flag=false;
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("from ExamInviligatorExemptionDatewise e where e.isActive=1 and e.date='"+CommonUtil.ConvertStringToSQLDate(baseActionForm.getDate())+
					"' and e.id !="+baseActionForm.getExamid()+" and e.teacherId.id="+Integer.parseInt(baseActionForm.getUserId())+" and e.session='"+baseActionForm.getStime()+"'");
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise=iCommonAjax.checkDuplicateExemption(stringBuilder.toString());
			if(examInviligatorExemptionDatewise!=null){
				flag=true;
			}
			return flag;
		}

		public Map<Integer, String> getProgramByYear(
				BaseActionForm baseActionForm) throws Exception{
			Map<Integer, String> map=InterviewSelectionScheduleTransImpl.getInstance().getprogramMap();
			return CommonUtil.sortMapByValue(map);
		}

		public boolean checkIsFacultyInExamInvigilatorsAvailable(
				BaseActionForm baseActionForm) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			boolean flag=iCommonAjax.checkIsFacultyInExamInvigilatorsAvailable(baseActionForm.getLocationId(),baseActionForm.getExamName(),baseActionForm.getUserId());
			return flag;
		}
		
		public Map<Integer, String> getVenueBySelectionDateHl(String selectionScheduleId)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> VenueMap = iCommonAjax.getVenueBySelectionDateHl(selectionScheduleId);
			VenueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(VenueMap);
			return VenueMap;
		}

		/*public Map<Integer, String> getVenueBySelectionDatePref(String selectionScheduleId)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> VenueMap = iCommonAjax.getVenueBySelectionDateFirstPref(selectionScheduleId);
			VenueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(VenueMap);
			return VenueMap;
		}*/
		
		public Map<Integer, String> getTimeBySelectionDateHl(String VenueId, String selectionScheduleId)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> TimeMap = iCommonAjax.getTimeBySelectionDateHl(VenueId, selectionScheduleId);
			TimeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(TimeMap);
			return TimeMap;
		}
		
		
		
		public Map<Integer, String> getDatesBySelectionVenueOnline(String selectionVenueId, String programId,String programYear)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> dateMap = iCommonAjax.getDatesBySelectionVenueOnline(selectionVenueId, programId, programYear);
			dateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(dateMap);
			return dateMap;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOffline(String selectionVenueId, String programId,String programYear)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> dateMap = iCommonAjax.getDatesBySelectionVenueOffline(selectionVenueId, programId, programYear);
			dateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(dateMap);
			return dateMap;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOnlineAppEdit(String selectionVenueId, String programId,String programYear, String applNo,String oldSeledctedVenue)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> dateMap = iCommonAjax.getDatesBySelectionVenueOnlineAppEdit(selectionVenueId, programId, programYear, applNo, oldSeledctedVenue);
			dateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(dateMap);
			return dateMap;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOfflineAppEdit(String selectionVenueId, String programId,String programYear, String applNo,String oldSeledctedVenue)throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> dateMap = iCommonAjax.getDatesBySelectionVenueOfflineAppEdit(selectionVenueId, programId, programYear, applNo,oldSeledctedVenue);
			dateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(dateMap);
			return dateMap;
		}
		public Map<Integer, String> getPeriodByClassSchemewiseValues(Set<Integer> classSchemewiseIds) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax
					.getPeriodByClassSchemewise(classSchemewiseIds);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		
		public Map<Integer, String> getCommonSubjectsByClass(Set<Integer> classSchemewiseIds) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subjectsMap = iCommonAjax.getCommonSubjectsByClass(classSchemewiseIds);
			//Map<Integer, String> valueMap = sortByValue(classMap);
			return subjectsMap;
		}

		public Map<String, String> getYearMapByDept(String deptId, String year) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<String, String> yearMap = iCommonAjax.getYearMapByDept(Integer.parseInt(deptId),Integer.parseInt(year));
			//Map<Integer, String> valueMap = sortByValue(classMap);
			return yearMap;
		}

		public Map<String, String> getsubjectMapByDeptAndYear(String deptId,
				String year) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<String, String> yearMap = iCommonAjax.getsubjectMapByDeptAndYear(Integer.parseInt(deptId),Integer.parseInt(year));
			//Map<Integer, String> valueMap = sortByValue(classMap);
			return yearMap;
		
		}
		
		public Map<Integer, String> getExamCentersByProgram(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> examCenterMap = iCommonAjax.getExamCentersByProgramId(id);
			examCenterMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examCenterMap);
			return examCenterMap;
		}
		
		public Map<Integer, String> getCentersByProgram(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> centerMap = iCommonAjax.getCentersByProgram(id);
			centerMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(centerMap);
			return centerMap;
		}
		
		public Map<Integer, String> getDistrictByStateId(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> stateMap = iCommonAjax.getDistrictByState(id);
			stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
			return stateMap;
		}
	
		
		/**
		 * \
		 * 
		 * @param id
		 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
		 */

		@SuppressWarnings("unchecked")
		public Map<Integer, String> getSubReligion() {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subreligionMap = iCommonAjax.getSubReligion();
			subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
			return subreligionMap;
		}


		/**
		 * \
		 * 
		 * @param id
		 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
		 */

		@SuppressWarnings("unchecked")
		public Map<Integer, String> getSubCasteByReligion(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subreligionMap = iCommonAjax.getSubCasteByReligion(id);
			subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
			return subreligionMap;
		}
		
		
		/**
		 * \
		 * 
		 * @param id
		 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
		 */

		@SuppressWarnings("unchecked")
		public Integer getAttendanceTypeIdBySubject(int id) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Integer attendanceTypeId = iCommonAjax.getAttendanceTypeIdBySubject(id);
			return attendanceTypeId;
		}
		
		// Vinodha for faculty evaluation report
		public Map<Integer, String> getClassesForFacultyEvaluationReport(BaseActionForm baseActionForm, int year) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesForFacultyEvaluationReport(baseActionForm,year);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		// Vinodha for faculty evaluation report
		public Map<Integer, String> getSubjectsForFacultyEvaluationReport(BaseActionForm baseActionForm, int className) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subjectMap = iCommonAjax.getSubjectsForFacultyEvaluationReport(baseActionForm,className);
			subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			return subjectMap;
		}
		// Vinodha for faculty evaluation report principal
		public Map<Integer, String> getTeachersForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> teacherMap = iCommonAjax.getTeachersForFacultyEvaluationReportPrincipal(baseActionForm);
			teacherMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
			return teacherMap;
		}
		public Map<Integer, String> getClassesForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesForFacultyEvaluationReportPrincipal(baseActionForm);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		public Map<Integer, String> getSubjectsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm,int className) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subjectMap = iCommonAjax.getSubjectsForFacultyEvaluationReportPrincipal(baseActionForm,className);
			subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			return subjectMap;
		}		
		public Map<Integer, String> getClassesForEvaluateFacultyFeedback(BaseActionForm baseActionForm, int year) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesForEvaluateFacultyFeedback(baseActionForm,year);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}		
		public Map<Integer, String> getDepartmentsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> teacherMap = iCommonAjax.getDepartmentsForFacultyEvaluationReportPrincipal(baseActionForm);
			teacherMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
			return teacherMap;
		}
		//mobile sms criteria
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getClassesBySelectedCourse1(int courseid,
				Integer year) throws Exception {
			ICommonAjax txn = new CommonAjaxImpl();
			Map<Integer, String> map = new HashMap<Integer, String>();
			/*List<Object[]> objList = txn.getYearandTermNo(courseid, year);
			Iterator<Object[]> it = objList.iterator();
			int semNo = 0;
			int year1 = 0;
			if (objList != null) {
				while (it.hasNext()) {
					Object[] objects = (Object[]) it.next();
					if (objects[0] != null) {
						semNo = Integer.parseInt(objects[0].toString());
					}
					if (objects[1] != null) {
						year1 = Integer.parseInt(objects[1].toString());
					}
					if (semNo > 0 && year1 > 0) {
						List<ClassSchemewise> classList = txn
								.getClassSchemeByCourseId(semNo, year1, courseid);
						ClassSchemewise classSchemewise;
						Iterator<ClassSchemewise> itr = classList.iterator();
						while (itr.hasNext()) {
							classSchemewise = itr.next();
							map.put(classSchemewise.getId(), classSchemewise
									.getClasses().getName());
						}
					}
				}
			}*/
			List<ClassSchemewise> classList = txn.getClassSchemeByCourseId1(0, year, courseid);
			Iterator<ClassSchemewise> iterator = classList.iterator();
			while (iterator.hasNext()) {
				ClassSchemewise classSchemewise = (ClassSchemewise) iterator.next();
				map.put(classSchemewise.getId(), classSchemewise
						.getClasses().getName());
			}
			map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
			return map;
		}
		
		
		
		//new
		
		public Map<Integer, String> getApplnProgramsByProgramTypeNew(int id) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> programMap = iCommonAjax.getApplnProgramsByProgramTypeNew(id);
			programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
			return programMap;
		}
		
		public Map<Integer, String> getCourseByProgramTypeForOnlineNew(int id) throws Exception{
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> programMap = iCommonAjax.getCourseByProgramTypeForOnlineNew(id);
			programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
			return programMap;
		}
		
		public Map<Integer, String> getPrograms(int year) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getProgram(year);
			return classMap;
		}

		public Map<Integer, String> getClassNameByExamName(int examId) throws Exception{
			// TODO Auto-generated method stub
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getClassNameByExamName(examId);
		}
		
		public Map<Integer, String> getSemistersByYearAndProgramType(int academicYear, int programTypeId) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getSemistersByYearAndProgramType(academicYear, programTypeId);
		}


		// vinodha
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getClassesForAttendanceByYear(int year, String evenOrOdd) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			//Map<Integer, String> classMap = iCommonAjax.getClassesByYear(year);
			Map<Integer, String> classMap = iCommonAjax.getClassesForAttendanceByYear(year, evenOrOdd);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		
		public Map<Integer, String> getSemseterMap(int examId) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getSemesterMapForExam(examId);
		}
		
		public List<AttendanceCondonation> condonationRestrict(String baseStudentId) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			List<AttendanceCondonation> ac = iCommonAjax.condonationRestrict(baseStudentId);
			return ac;
		}
		
		public Map<Integer, String> getClassesByProgramTypeAndAcademicYear1(int programTypeId, int academicYear) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByProgramTypeAndAcademicYear1(programTypeId, academicYear);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		}
		
		public String getAcademicYearForClass(int studentId, int semester) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			return iCommonAjax.getAcademicYearForClass(studentId, semester);
		}

		public Map<Integer, String> getProgramesByActivityId(
				String extraCreditActivityType)throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			ExamGenHelper helper = new ExamGenHelper();
			return helper.convertBOToTO_programme_code(iCommonAjax
					.getProgramsByActivityId(extraCreditActivityType));
		}
		public Map<Integer, String> getClassMapForTeacherAndDate(int userId, String attendanceDate) throws Exception {
			IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
			return attendanceEntryTransaction.getClassesByTeacherForADay(userId, attendanceDate);
		}

		public Map<Integer, String> getSemisterByYear(int year) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> semisterMap = iCommonAjax.getSemisterByYear(year);
			return semisterMap;
		}

		public Map<Integer, String> getClassesByYearAndSem(int year,
				int semisterNo) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByYearAndSem(year,semisterNo);
			return classMap;
		}

		public Map<Integer, String> getClassByAcademicYear(String academicYear) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> classMap = iCommonAjax.getClassesByAcademicYear(academicYear);
			return classMap;
		}

		public Map<Integer, String> getCourseByyearandTerm(String year, String term, String userId, String roleId) {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> subjectMap = iCommonAjax
					.getCourseByyearandTerm(year, term, userId,roleId);
			return subjectMap;
		}

		public Map<Integer, String> getExamNameByYearAndCourseAndSem(int year,int course,int sem,String type,String subId) throws Exception {
			ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
			Map<Integer, String> examMap = iCommonAjax.getExamNameByYearAndCourseAndSem(year, course, sem, type, subId);
			examMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examMap);
			return examMap;
		}

		
}
