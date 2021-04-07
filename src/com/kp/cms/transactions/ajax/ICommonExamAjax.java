package com.kp.cms.transactions.ajax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.to.exam.KeyValueTO;

public interface ICommonExamAjax {
	// Exam Module Related
	public Map<Integer, String> getCourseByProgramType(int pid);

	/*
	 * This function is uses in Exam module like Assignment/Overall marks,
	 */

	public Map<Integer, String> getCoursesByAcademicYear(int year);

	public Map<Integer, String> getSchemeByCourseId(int cid);

	public Map<Integer, String> getProgramsByPType(int pid);

	public Map<Integer, String> getClasesByExamName(String examName);

	public Map<Integer, String> getClasesByJoingBatch(String joiningBatch);

	public Map<Integer, String> getSchemesByCourseId(String courseId);

	Map<Integer, String> getCourseByExamName(String examName);

	public Map<Integer, String> getSectionByCourseIdSchemeId(String courseId,
			String schemeId,String schemeNo,String academicYear);

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

	public Map<Integer, String> getSubjectsByCourse(int cid, int sid,
			int schemeNo);

	public Map<Integer, String> getCourseByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo);

	public Map<Integer, String> getExamNameByExamType(String examType);

	public Map<Integer, String> getSchemeNoByCourseId(int cid);

	public Map<Integer, String> getClasesByAcademicYear(int academicYear);

	public Map<Integer, String> getClassCodeByExamName(int examName);

	public Map<Integer, String> getCoursesByProgramTypes(String pids);

	public int getInternalComponentsByClasses(int examId);

	public Map<String, String> getSchemeNoByExamIdCourseId(int examId,
			int courseId);

	public Map<Integer, String> getSubjectsByCourseIdSchemeNo(int courseId,
			int shemeId, int shemeNo);

	public String getSubjectsTypeBySubjectId(int subjectId);

	public Map<Integer, String> getTypeByAssignmentOverall(
			String assignmentOverall);

	public Map<Integer, String> getExamNameByAcademicYear(String academicYear);

	public Map<Integer, String> getAgreementNameByClassId(
			ArrayList<Integer> classListIds);

	public Map<Integer, String> getFooterNameByClassId(
			ArrayList<Integer> classListIds);

	// ExamPublishHallTicket
	public Map<Integer, String> getAgreementNameByProgramTypeId(
			String programTypeID);

	// ExamPublishHallTicket
	public Map<Integer, String> getFooterNameByProgramTypeId(
			String programTypeID);

	// Exam Definition
	public Map<Integer, String> getInternalExamByAcademicYear(
			String academicYear);

	public String getDecryptRegNo(String regNo);

	// Exam Assign Students To Room
	public Map<Integer, String> getSubjectNameByClassIds(String classId,
			String date, String time, String examName);

	public String getDateTimeByExamId(int examId);

	public String getCurrentExam(int examTypeId);

	public int getMaxTheoryMarcks(BigDecimal marcks, int courseId, int schemeNo,
			int subjectId, String subjectType,int studentId, String assignmentOverall, String type);

	public int getMaxPracticalMarcks(int marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, String assignmentOverall);

	public String getExamDateTimeByExamId(int examId);

	public ArrayList<KeyValueTO> getSubjectCodeName(String sCodeName, int examId);
	public ArrayList<KeyValueTO> getCoursesByProgramTypes1(String pids);

	public Map<Integer, String> getSubjectsByCourseSchemeExamId(int courseId,
			int schemeId, int schemeNo, Integer examId);
	public Map<Integer, String> getSubjectsByCourseSchemeExamIdJBY(int courseId,
			int schemeId, int schemeNo, Integer examId, Integer jby);
	public List<CurriculumScheme> getSchemeByAcademicYear(int courseId,int academicYear);

	public Map<Integer, String> getProgramsByExamName(String examName);
	public Map<Integer, String> getSubjectsByProgram(Integer programId);
	public Integer getAcademicYear(int examId) throws Exception;
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeExamId(String sCodeName,int courseId,
			int schemeId, int schemeNo, Integer examId,Integer academicYear);
	public List<Object> getSubjectFromRevaluationOrRetotaling(String sCodeName, int examId);
	
	public Map<Integer, String> getExamNameByAcademicYearAndExamType(
			String academicYear, String examType);
	
	public Map<Integer, String> getClassNameByExamNameForSupplementary(
			int examId);
	
	public Map<String, String> getSchemeNoByExamId(int examId) ;
	
	public ArrayList<ProgrammeEntryBo> getProgramesByDepartmentId(
			String deptId);
	public Map<Integer, String> getCourseByTeacher(String teacherId, String year);
	
	public Map<String, String> getSchemeNoByCourseIdByTeacher(int courseId, String teacherId, int year);
	
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeIdByTeacher(String sCodeName,int courseId,
			int schemeId, int schemeNo, String teacherId);
}
