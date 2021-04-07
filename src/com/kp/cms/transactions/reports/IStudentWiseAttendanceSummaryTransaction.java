package com.kp.cms.transactions.reports;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.PrincipalRemarks;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;

public interface IStudentWiseAttendanceSummaryTransaction {
	
	/**
	 * Get the list of studentwise attendance summary summary list.
	 * @param absenceInformationQuery
	 * @return
	 * @throws ApplicationException
	 */
	public List<Object[]> getStudentWiseAttendanceSummaryInformation(
			String absenceInformationQuery) throws ApplicationException;
	
	public Student getStudentByRegdRollNo(int regdNo)throws Exception;
	public List<Object[]> getStudentBySearch(String dyanmicQuery) throws Exception;

	public List<AttendanceStudent> getAbsencePeriodDetails(
			String absenceSearchCriteria)throws Exception;
	public List<StudentRemarks> getStaffRemarks(int studentId) throws Exception;
	public boolean addPrincipalRemarks(PrincipalRemarks principalRemarks) throws Exception;	
	public PrincipalRemarks getPricipalRemarks(int studentId) throws Exception;

	public List<Integer> getPeriodList(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception;
	public List<ExamMarksEntryDetailsBO> getStudentWiseExamMarkDetails(	int studentId) throws ApplicationException;
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsView(int studentId, int classId) throws ApplicationException;
	
	public HashMap<Integer, String> getSubjectsBySubjectGroupId(String groupIds) throws ApplicationException;
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdCJC(String groupIds) throws ApplicationException;
	public List<Integer> getExamPublishedIds(int classId) throws ApplicationException;
	public Integer getClassId(int studentId) throws ApplicationException;
	public Map<Integer, Integer> getSubjectOrder(int courseId,int semNo,int semesterAcademicYear) throws ApplicationException;
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupId(String groupIds) throws ApplicationException;
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupIdCJC(String groupIds) throws ApplicationException;

	public HashMap<Integer, String> getSubjectsBySubjectGroupIdLogin(String groupIds) throws ApplicationException;
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdAdditional(String groupIds) throws ApplicationException;
	//public Integer getClassIdPrevious(int studentId, int Semester, int year) throws ApplicationException;
	public Integer getClassIdPrevious(int studentId, int Semester) throws ApplicationException;


	public List<ExamSubjectRuleSettingsSubInternalBO> getMaxMarksFromExamSubjectRuleSettingsSubInternal(
			int internalExamTypeId, int subjectId, String courseId, int studentId) throws Exception;

	public List<ExamDefinitionBO> getExamDefinationList(List<String> examCodeList)throws Exception;

	public List<Subject> getSubjectsListForStudent(int studentId) throws Exception;
	//public HashMap<Integer, String> getSubjectsBySubjectGroupIdAdditional(String groupIds) throws ApplicationException;
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdCJCAdditional(String groupIds) throws ApplicationException;
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupIdCJCAdditional(String groupIds) throws ApplicationException;
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsViewAdditional(int studentId, int classId) throws ApplicationException;

	public Student getSudentSemAcademicYear(int studentId) throws Exception;

	ClassSchemewise getClassSchemeSemAcademicYear(int classId) throws Exception;

	Map<Integer, String> setPreviousClassId(String studentid) throws Exception;

	HashMap<Integer, String> getSubjectsByClassId(String classesId) throws Exception;

	HashMap<Integer, String> getSubjectsByClassIdAdditional(String classesId) throws Exception;

	List<String> getPreviousPeriodList(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception;

	String getClassesName (String classesId) throws Exception;
	//raghu
	public List<Object[]> getAttendanceDateAndDayOnClass(int classesId) throws Exception;

	public List<Object[]> getTeacherNotTakenAttendanceSummary(int classesId,int studentId,List<Object[]> attDates)throws Exception;
	
	public List<Object[]> getAttendanceDateAndDayOnClassTeacher(int classesId) throws Exception;
	
	public List<Object[]> getClassTeacherNotTakenAttendanceSummary(int classesId,int userId,List<Object[]> attDates)throws Exception;

	public List<Object[]> getAttendenceRecord(int studentId, String courseId,int classId, int acadamicYear) throws Exception;

	public List<Object[]> getSubjectWiseAttendenceInHrs(int studentId,
			String courseId, int classId, int acadamicYear, Integer subjectId) throws Exception;

}
