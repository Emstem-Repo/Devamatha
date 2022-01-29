package com.kp.cms.transactions.ajax;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.attendance.AttendanceTO;

public interface ICommonAjax {

	public Map<Integer, String> getStatesByCountry(int id);

	public Map<Integer, String> getCitiesByState(int id);

	public Map<Integer, String> getCitiesByCountry(int id);

	public Map<Integer, String> getProgramsByProgramType(int id);

	public Map<Integer, String> getApplnProgramsByProgramType(int id);

	public Map<Integer, String> getActivityByAttendenceType(Set id);

	public Map<Integer, String> getCourseByProgram(int id);
	
	public Map<Integer, String> getCourseByProgram(Set<Integer> ids);

	public Map<Integer, String> getCollegeByUniversity(int id);
	public List<CollegeTO> getCollegeByUniversityList(int id);

	public Map<Integer, String> getSubReligionByReligion(int id);

	public Map<Integer, String> getSubjectGroupsByCourse(int id);

	public int getCandidateCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId);

	public List<CurriculumScheme> getSemistersOnYearAndCourse(int courseId,
			int year);

	public List<CurriculumSchemeDuration> getSemistersOnYearAndCourseScheme(
			int courseId, int year);

	public Map<Integer, String> getinterViewTypybyCourse(int id, int year);

	public Map<Integer, String> getInterviewTypebyProgram(int id, int year);

	public Map<Integer, String> getInterviewSubroundsByInterviewType(int id);

	public Map<Integer, String> getInterviewSubroundsByApplicationId(int id,
			int applicationId);

	public AttendanceType getMandatoryByAttenadnce(int attendanceTypeId);

	public Map<Integer, String> getClassesByYear(int year);
	
	public Map<Integer, String> getClassesByYearNew(int year);

	// Get Details by classId from Class
	public ClassSchemewise getDetailsonClassSchemewiseId(int id);
	public ClassSchemewise getDetailsonClassSchemewiseIdNew(int id);


	public List<ClassSchemewise> getDetailsOnClassSchemewiseId(Set<Integer> ids);

	// Gets subject from curriculumscheme
	public Map<Integer, String> getBatchesByClassSchemewiseId(Set<Integer> ids);

	public Map<Integer, String> getBatchesBySubjectAndClassScheme(
			int subjectId, Set<Integer> ids);

	public Map<Integer, String> getSubjectByCourseIdTermYear(int courseId,
			int year, int term);

	public Map<Integer, String> getPeriodByClassSchemewiseId(
			Set<Integer> classSchemewiseId);

	public Map<Integer, String> getPeriodByClassSchemewiseId(
			int classSchemewiseId);

	public Map<Integer, String> getSubjectsDetailsByCourse(int courseId);

	public Map<Integer, String> getFloorsByHostel(int hostelId);

	public Map<Integer, String> getHlGroupsByHostel(int hostelId);

	public Map<Integer, String> getRoomTypeByHostel(int hostelId);

	public Map<Integer, String> getEmployeesByDepartment(int departmentId);

	public Map<Integer, String> getClassesByProgram(int programId);

	public Map<Integer, String> getinterViewTypybyCourseWithoutYear(int id);

	public Map<Integer, String> getInterviewTypebyProgramWithoutYear(int id);

	public Map<Integer, String> getClassesByProgramType(int programTypeId);

	// petticash Related
	public List<Object[]> getStudentName(int optionNo, String appRegRollNo,
			String academicYear, HttpServletRequest request) throws Exception;

	public PcAccountHead getAmount(int id) throws Exception;

	public Map<Integer, String> getRoomsMapByHostelId(String hostelId);

	// hostel Allocation Related
	public Map<Integer, String> getRoomsByFloors(int hostelId, int floorNo,
			int roomTypeid);

	public Map<Integer, String> getRoomsByHostel(int hostelId);

	public Map<Integer, String> getBedByRoomId(int roomtypeId);

	public Map<Integer, String> getRoomTypesByHostelAndByFloor(int hostelId)
			throws Exception;

	// Exam Module Related
	public Map<Integer, String> getCourseByProgramType(int pid);

	public Map<Integer, String> getCoursesByAcademicYear(int year);

	public Map<Integer, String> getSchemeByCourseId(int cid);

	public Map<Integer, String> getProgramsByPType(int pid);

	public Map<Integer, String> getClasesByExamName(String examName);

	public Map<Integer, String> getClasesByJoingBatch(String joiningBatch);

	public Map<Integer, String> getSchemesByCourseId(String courseId);

	Map<Integer, String> getCourseByExamName(String examName);

	public Map<Integer, String> getSectionByCourseIdSchemeId(String courseId,
			String schemeId);

	public Map<Integer, String> getExamNameByExamTypeId(int examTypeId);

	public Map<Integer, String> getSchemeValuesBySchemeId(int fromschemeId,
			int toSchemeId);

	public Map<Integer, String> getProgramsByPTypes(ArrayList<Integer> pids);

	public Map<Integer, String> getProgramByAcademicYear(int academicYear);

	// ajay
	public Map<Integer, String> getSchemeNoByCourseIdAcademicYear(int CourseId,
			int year);

	// ajay
	public Map<String, String> getSchemeNo_SchemeIDByCourseIdAcademicId(
			int CourseId, int year);

	// ashwin
	public Map<Integer, String> getSchemeNoByCourse(int cid);

	public Map<Integer, String> getSubjectsByCourse(int cid, int sid);

	public Map<Integer, String> getCourseByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo);

	public Map<Integer, String> getExamNameByExamType(String examType);

	public Map<Integer, String> getSchemeNoByCourseId(int cid);

	public Map<Integer, String> getClasesByAcademicYear(int academicYear);

	public Map<Integer, String> getClassCodeByExamName(int examName);

	public Map<Integer, String> getExamCenterForProgram(int id);

	/**
	 * @author jboss
	 * @param teacherId
	 * @return
	 */
	public Map<Integer, String> getClassesByTeacher(int teacherId, String year);

	/**
	 * @author jboss
	 * @param courseId
	 * @param year
	 * @param term
	 * @param teacher
	 */
	//method parameter removed by mehaboob no use of those parameter
	public Map<String, String> getSubjectByCourseIdTermYearTeacher(int teacher,String[] selectedClasses,HttpSession session,boolean subjectChange);

	public Map<Integer, String> getProgramsByAdmitedYear(int year)
			throws Exception;

	public Map<Integer, String> getClassesByYearForMuliSelect(int year);

	public Map<Integer, String> getPeriodByClassSchemewiseIds(
			String[] classSchemeWiseIds);

	public Map<Integer, String> getRoomsByClassSchemewiseIds(
			ArrayList<Integer> classSchemeWiseIds);
	
	public Map<Integer, String> getEducationByQualificationId(
			int qualificationId);

	public Map<Integer, String> getClassesByYearFromGeneratedTimeTable(int year);
	public Map<Integer, String> getSpecializationByCourse(int courseId); 

	public Map<Integer, String> getBatchesByActivityAndClassScheme(
			int activityId, Set<Integer> ids);
	public List<Object[]> getYearandTermNo(int courseid, Integer year) throws Exception;
	public List<ClassSchemewise> getClassSchemeByCourseId(int semNo,
			Integer year, int courseId) throws Exception;

	public List<String> getSectionsByCourseAndSemester(int courseId,int year);
	public Map<Integer, String> getCourseByProgramInOrder(int id);

	public int getCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId, String stime,
			String etime, List<Date> dateList);
	
	public Map<Integer, String> getRoomsByFloor(int hostelId, int floorNo);
	
	public Map<Integer, String> getInterviewSubroundsByInterviewTypeForMultiSelect(String[] id);

	public String getRegisterNo(String ipAddress);

	public Map<Integer, String> getEmployeeCodeName(String eCodeName);

	public Map<Integer, String> getSubjectGroupByFeeAdditionalForMultiSelect(
			String[] arrayList);

	public Map<Integer, String> getAttendanceSubjectsByRegisterNo(String regNo) throws Exception;
	
	public Map<Integer, String> getCoursesByYear(int year);
	
	// Down Three Methods added by Balaji For the Change of Practical Batch (Class Schemewise Id instead of batch to BatchStudent)
	public Map<Integer, String> getBatchesByClassSchemewiseId1(Set<Integer> classSchemeId);
	public Map<Integer, String> getBatchesByActivityAndClassScheme1(int activityId, Set<Integer> classSchemeId);
	public Map<Integer, String> getBatchesBySubjectAndClassScheme1(String SubjectId, Set<Integer> classSchemeId,HttpSession session);
	
	public List<String> getDynamicFingerPrintIds(String fingerPrintId,String userId)throws Exception;
	public Map<Integer, String> getCourseByProgramForOnline(int id);

	public Map<Integer, String> getExamNameByExamTypeAndYear(String examType, int year) throws Exception;
	
	public Map<Integer,String> getCourseByYear(int year)throws Exception;
	
	public Map<Integer,String> getClassesByCourse(int courseId,int year)throws Exception;

	public AttendanceTO getPeriodsByTeacher(String teacherId, String year,String day,String date) throws Exception;

	public Map<Integer, String> getClassesByPeriodsAndTeachers(String teachers, String year, String periods,String day,String date) throws Exception;

	public AttendanceTO getSubjectsByPeriodsAndTeachers( String teachers, String year, String periods,String day,String date,String periodName,HttpSession session) throws Exception;

	public Map<Integer, String> getBatchesByPeriodsAndTeachers( String teacherId, String year, String periods, String day,String date) throws Exception;

	public Map<Integer, String> getSubCategoryMap(String categoryId) throws Exception;

	public Map<Integer, String> getSpecializationBySubGrpWithoutCommSubjAndSecndLang(
			String subjectGroupId)throws Exception;
	public Map<Integer, String> getInvLocationMap(String campusId) throws Exception;
	
    public String getName(String query)throws Exception;
	
	public boolean checkReceivedThrough(String query,HttpServletRequest request)throws Exception;

	public Map<Integer, String> getSubjectByYear(String year) throws Exception;

	public Map<Integer, String> getExamByYear(String year) throws Exception;

	public Map<Integer, String> getCourseByApplnNoYear(String appNo, String year) throws Exception;
	
	public Map<Integer, String> getClassByYearUserId(int year, int teacherId);
	
	public Map<Integer, String> getinterViewTypybyCourseNew(List<Integer> courseList, int year) throws Exception;

	public Map<Integer, String> getInterviewSubroundsByInterviewTypeNew(String interviewTypeName, List<Integer> courseList, String year) throws Exception;

	public Map<Integer, String> getFacultyByDepartmentWise(int departmentId)throws Exception;

	public Map<Integer, String> getGuestFacultyByDepartmentId(int departmentId, Map<Integer, String> facultyMap)throws Exception;

	public Map<Integer, String> getUsersByDepartment(int departmentId, Map<Integer, String> facultyMap1)throws Exception;

	public Map<Integer, String> getYearWiseDocuments(String year) throws Exception;
	
	public Map<Integer, String> getExamNameByYear(String academicYear) throws Exception;
	
	public Map<Integer, String> getClassesForProgram(String programTypeId, String year) throws Exception;

	public List<ClassSchemewise> getDetailsOnClassSchemewiseIdNew(Set<Integer> ids) throws Exception;
	
	public String getEmpanelmentNoByguideName(String name,HttpServletRequest request) throws Exception;
	
	public Map<Integer, String> getSpecializationByClassId( Set<Integer> classesIdsSet)throws Exception;

	public List<Object[]> getStudentNameInHostel(String regNo, String applNo,HttpServletRequest request) throws Exception;

	public Map<Integer, String> getRoomByRoomType(int roomTypeId) throws Exception;

	public BigDecimal getNumberOfSeatsAvailable(String hostel,String roomtype, String year, HttpServletRequest request) throws Exception;
	public Map<Integer, String> getCourseByProgramId( Set<Integer> programIdsSet)throws Exception;

	public Map<Integer, String> getHostelBygender(String hostelGender) throws Exception;

	public Map<Integer, String> getRoomTypeByHostelBYstudent(int hostelId) throws Exception;

	public Map<Integer, String> getHostel() throws Exception;
	
	public Map<Integer, String> getRoomsAvailable(String hstlName,String RoomType,String academicYear,String block,String unit,String floor) throws Exception;

	public Map<Integer, String> getBedsAvailable(int roomId, int academicYear)throws Exception;
	
	public List getStudentNameClass(String regNo, String applNo,String academicYear, String hostelApplNo, HttpServletRequest request) throws Exception;

	public Map<Integer, String> getBedByRoom(int roomId) throws Exception;
	
	public Map<Integer, String> getRoomsByFloorsCheckIn(int hostelId, int floorNo,int roomTypeid, int roomAlreadyAllotted, int academicYear );
	
	public Map<Integer, String> getBedByRoomCheckIn(int roomId, int allotted, int academicYear) throws Exception;
	
	public Map<Integer, String> getSupplementryExamNameByAcademicYear( String academicYear)throws Exception;
	
	public Map<Integer, String> getClassesBySemAndYear(int year,List<Integer> list)throws Exception;
	
	public List<Integer> getTermNumber()throws Exception;
	
	public Map<Integer, String> getClassesByTeacherAndDate(int teacherId, String year,String day,String date)throws Exception;
	
	public HlAdmissionBo getHlAdmissionBo(String year,String regNo)throws Exception;
	
	public List<Student> checkRgNoDuplicate(String regNo);
	
	public String getExamDateBySubject(String examId, String subjectId, HttpServletRequest request) throws Exception;

	public Map<Integer, String> getStates(int id) throws Exception;
	
	public String getAmountByCategory(int id)throws Exception;

	public Map<Integer, String> getEmployeeByprogramId(int pid) throws Exception;

	public Map<Integer, String> getInternalGuide(String check)  throws Exception;
	public Map<Integer, String> getDepartments()throws Exception;
	public Map<Integer, String> getBlockByHostel(int hostelId)throws Exception;
	public Map<Integer, String> getUnitByBlock(int blockId)throws Exception;

	public List<Integer> checkDupilcateOfStaffId(String staffId)throws Exception;
	public Map<Integer, String> getBlockByLocation(int locationId);
	public Map<Integer, String> getvenueByWorkLocation(int workLocationId)throws Exception;

	public Map<Integer, String> getCoursebyHostel(int hostelId) throws Exception;

	public Map<Integer, String> getClassByHostel(int hostelId) throws Exception;
	public Map<Integer, String> getFloorsByUnit(int unitId) ;
	
	public Map<Integer, String> getProgramBydeanaryNameAndExam(String deanaryName,int examId)throws Exception;
	
	public Map<Integer, String> getClassesByProgramAndAcademicYear(int programId, String deaneryName);
	
	public Map<Integer, String> getClassesByProgramTypeAndAcademicYear(int programTypeId, String deaneryName);
	
	public Map<Integer, String> getClassesByCourseAndAcademicYear(int courseId, String deaneryName);

	public boolean duplicateCheckingOfOrderNoByLocationId(String orderNo,
			String locationId)throws Exception;
	
	public Map<Integer, String> getClassesBySemesterAndAcademicYear(int semester, String deaneryName, int programTypeId, int programId, int courseId);
	public Map<Integer, String> getDepartmentByStream(String streamId)throws Exception;

	public Map<Integer, String> getRoomNosByCampus(String locationId)throws Exception;

	public Map<Integer, String> getFacultyByCampus(String locationId)throws Exception;

	public List<ExamInviligatorDuties> checkDuplicateInvigilator(String string)throws Exception;

	public Map<Integer, String> facultyByCampusDeptAndDeanery(String string)throws Exception;

	public ExamInviligatorExemptionDatewise checkDuplicateExemption(
			String string)throws Exception;

	public boolean checkIsFacultyInExamInvigilatorsAvailable(String locationId,
			String exam1Id, String userId)throws Exception;
	
	public Map<Integer, String> getVenueBySelectionDateHl(String selectionScheduleId)throws Exception;
	
	public Map<Integer, String> getTimeBySelectionDateHl(String venueId, String selectionScheduleId)throws Exception;

	//public Map<Integer, String> getVenueBySelectionDateFirstPref(String selectionScheduleId)throws Exception;
	
	public Map<Integer, String> getDatesBySelectionVenueOnline(String selectionVenueId, String programId, String programYear)throws Exception;
	
	public Map<Integer, String> getDatesBySelectionVenueOffline(String selectionVenueId, String programId, String programYear)throws Exception;
	
	public Map<Integer, String> getDatesBySelectionVenueOnlineAppEdit(String selectionVenueId, String programId, String programYear, String Applno, String oldSeledctedVenue)throws Exception;
	
	public Map<Integer, String> getDatesBySelectionVenueOfflineAppEdit(String selectionVenueId, String programId, String programYear, String Applno, String oldSeledctedVenue)throws Exception;
	
	public Map<Integer, String> getPeriodByClassSchemewise(Set<Integer> classSchemewiseId);
	
	public Map<Integer, String> getCommonSubjectsByClass(Set<Integer> classSchemewiseId);

	public Map<String, String> getYearMapByDept(int deptId, int year)throws Exception;

	public Map<String, String> getsubjectMapByDeptAndYear(int deptId,int year)throws Exception;
	
	public String checkStudentApplication(String regNo, String applNo, String year) throws Exception;
	
	public Map<Integer, String> getExamCentersByProgramId(int id);
	
	public Map<Integer, String> getCentersByProgram(int id);
	
	public Map<Integer, String> getDistrictByState(int id);
	
	public Map<Integer, String> getSubReligion();
	
	public Map<Integer, String> getSubCasteByReligion(int id);
	
	public Integer getAttendanceTypeIdBySubject(int id);

	public Map<Integer, String> getClassesForFacultyEvaluationReport(BaseActionForm baseActionForm, int year) throws Exception;
	
	public Map<Integer, String> getSubjectsForFacultyEvaluationReport(BaseActionForm baseActionForm, int className) throws Exception;
	
	public Map<Integer, String> getTeachersForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception;

	public Map<Integer, String> getClassesForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception;

	public Map<Integer, String> getSubjectsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm, int className) throws Exception;

	public Map<Integer, String> getClassesForEvaluateFacultyFeedback(BaseActionForm baseActionForm, int year) throws Exception;
	
	public Map<Integer, String> getDepartmentsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception;

	public List<ClassSchemewise> getClassSchemeByCourseId1(int semNo,Integer year, int courseId) throws Exception;

	//new for admission
	public Map<Integer, String> getApplnProgramsByProgramTypeNew(int id)throws Exception;
	
	public Map<Integer, String> getCourseByProgramTypeForOnlineNew(int id)throws Exception;
	
	public Map<Integer, String> getProgram(int year);
	
	public Map<Integer, String> getClassNameByExamName(int examId)throws Exception;
	
	public Map<Integer, String> getSemistersByYearAndProgramType(int academicYear, int programTypeId)throws Exception;

	public Map<Integer, String> getClassesForAttendanceByYear(int year, String evenOrOdd);
	
	public Map<Integer, String> getSemesterMapForExam(int examId) throws Exception;
	
	//for condonation
	public Map<Integer, String> getClassesByCourseSemesterAndAcademicYear(
			int semester, int academicyear, int programTypeId, int programId,
			int courseId);

	public List<AttendanceCondonation> condonationRestrict(String baseStudentId);
	
	public Map<Integer, String> getClassesByProgramTypeAndAcademicYear1(int programTypeId, int academicYear) throws Exception;

	public String getAcademicYearForClass(int studentId, int semester) throws Exception;

	public ArrayList<Object[]> getProgramsByActivityId(
			String extraCreditActivityType)throws Exception;
	public Map<Integer, String> getSemisterByYear(int year);

	public Map<Integer, String> getClassesByYearAndSem(int year, int semisterNo);

	public Map<Integer, String> getClassesByAcademicYear(String academicYear) throws Exception;

	public Map<Integer, String> getCourseByyearandTerm(String year, String term, String userId, String roleId);
	
	public Map<Integer, String> getExamNameByYearAndCourseAndSem(int year,int course,int sem, String type, String subId) throws Exception;

}

