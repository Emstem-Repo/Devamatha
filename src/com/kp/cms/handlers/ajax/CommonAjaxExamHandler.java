package com.kp.cms.handlers.ajax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.handlers.exam.ExamInternalRetestApplicationHandler;
import com.kp.cms.helpers.exam.ExamGenHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.ajax.ICommonExamAjax;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxExamImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class CommonAjaxExamHandler {

	
	private static volatile CommonAjaxExamHandler commonAjaxExamHandler = null;
	private static final Log log = LogFactory.getLog(CommonAjaxExamHandler.class);
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static CommonAjaxExamHandler getInstance() {
		if (commonAjaxExamHandler == null) {
			commonAjaxExamHandler = new CommonAjaxExamHandler();
		}
		return commonAjaxExamHandler;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgramType(int pid) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = iCommonExamAjax
				.getCourseByProgramType(pid);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCoursesByAcademicYear(int year) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getCoursesByAcademicYear(year);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemeByCourseId(int cid) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemeByCourseId(cid);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByPType(int pid) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> programMap = ICommonExamAjax
				.getProgramsByPType(pid);
		programMap = (Map<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClasesByExamName(String examName) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> classMap = ICommonExamAjax
				.getClasesByExamName(examName);
		classMap = (Map<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	
	}

	public Map<Integer, String> getProgramesByDepartmentId(String deptId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		ExamGenHelper helper = new ExamGenHelper();
		return helper.convertBOToTO_programme_map(ICommonExamAjax
				.getProgramesByDepartmentId(deptId));
		
	
	}


	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClasesByJoingBatch(String joiningBatch) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> classMap = ICommonExamAjax
				.getClasesByJoingBatch(joiningBatch);
		classMap = (Map<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemesByCourseId(String courseId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemesByCourseId(courseId);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByExamName(String examName) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getCourseByExamName(examName);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	// ExamTimeTable
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamNameByExamTypeId(int examTypeId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> examNameMap = ICommonExamAjax
				.getExamNameByExamTypeId(examTypeId);
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		return examNameMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemeValuesBySchemeId(int fromSchemeId,
			int toSchemeId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemeValuesBySchemeId(fromSchemeId, toSchemeId);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByPTypes(String pids) {
		ArrayList<Integer> listOfPTypes = new ArrayList<Integer>();
		String[] ids = pids.split(",");
		for (int i = 0; i < ids.length; i++) {
			listOfPTypes.add(Integer.parseInt(ids[i]));
		}
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> programMap = ICommonExamAjax
				.getProgramsByPTypes(listOfPTypes);
		programMap = (Map<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramByAcademicYear(int academicYear) {

		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> programMap = ICommonExamAjax
				.getProgramByAcademicYear(academicYear);
		programMap = (Map<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;

	}

	// ajay
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemeNoByCourseIdAcademicyear(int courseId,
			int year) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemeNoByCourseIdAcademicYear(courseId, year);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;

	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSchemeNo_SchemeIDByCourseIdAcademicId(
			int courseId, int year) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<String, String> schemeMap = ICommonExamAjax
				.getSchemeNo_SchemeIDByCourseIdAcademicId(courseId, year);
		schemeMap = (Map<String, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;

	}

	// ashwin
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemeNoByCourse(int cid) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemeNoByCourse(cid);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsByCourse(int cid, int sid,
			int schemeNo) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax.getSubjectsByCourse(
				cid, sid, schemeNo);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getCourseByExamNameRegNoRollNo(examId, regNo,
				rollNo);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
		
	}

	public Map<Integer, String> getClassByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo) {
		
		return new ExamInternalRetestApplicationHandler()
				.getClassesByExamNameRegNoRollNo(examId, rollNo, regNo);

	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamNameByExamType(String examType) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getExamNameByExamType(examType);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSchemeNoByCourseId(int cid) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSchemeNoByCourseId(cid);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClasesByAcademicYear(int academicYear) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getClasesByAcademicYear(academicYear);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
	
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCoursesByProgramTypes(String pids) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getCoursesByProgramTypes(pids);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamNameByAcademicYear(String academicYear) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getExamNameByAcademicYear(academicYear);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}

	// public Map<Integer, String> getRoomNoByExamName(String examName) {
	// ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
	// Map<Integer, String> roomMap = ICommonExamAjax
	// .getRoomNoByExamName(examName);
	// return roomMap;
	// }

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSectionByCourseIdSchemeId(String courseId,
			String schemeId, String schemeNo, String academicYear) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = ICommonExamAjax
				.getSectionByCourseIdSchemeId(courseId, schemeId, schemeNo,
						academicYear);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassCodeByExamName(int examName) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		
		Map<Integer, String> schemeMap = ICommonExamAjax.getClassCodeByExamName(examName);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getAgreementNameByClassId(
			ArrayList<Integer> classListIds) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
	
		Map<Integer, String> schemeMap =ICommonExamAjax.getAgreementNameByClassId(classListIds);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getFooterNameByClassId(
			ArrayList<Integer> classListIds) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getFooterNameByClassId(classListIds);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	}

	// ExamPublishHallTicket
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getAgreementNameByProgramTypeId(
			String programTypeID) {

		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getAgreementNameByProgramTypeId(programTypeID);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	}

	// ExamPublishHallTicket
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getFooterNameByClassId(String programTypeID) {

		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getFooterNameByProgramTypeId(programTypeID);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInternalExamByAcademicYear(
			String academicYear) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getInternalExamByAcademicYear(academicYear);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		
	}

	public String getDecryptRegNo(String regNo) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		String regNo_return = ICommonExamAjax.getDecryptRegNo(regNo);
		return regNo_return;
	}

	// Exam Assign Students To Room
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectNameByClassIds(String classId,
			String date, String time, String examName) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap =ICommonExamAjax.getSubjectNameByClassIds(classId, date, time,
				examName);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		 
	}

	public String getCurrentExam(int examTypeId) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxImpl.getInstance();
		return iCommonExamAjax.getCurrentExam(examTypeId);
	}

	public String getDateTimeByExamId(int examId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		return ICommonExamAjax.getDateTimeByExamId(examId);
	}

	public int getInternalComponentsByClasses(int examId) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		return iCommonExamAjax.getInternalComponentsByClasses(examId);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSchemeNoByExamIdCourseId(int examId,
			int courseId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<String, String> schemeMap =ICommonExamAjax.getSchemeNoByExamIdCourseId(examId, courseId);
        schemeMap = (Map<String, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsByCourseIdSchemeNo(int courseId,
			int shemeId, int shemeNo) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		
		Map<Integer, String> schemeMap =ICommonExamAjax.getSubjectsByCourseIdSchemeNo(courseId, shemeId,
				shemeNo);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	}

	public String getSubjectsTypeBySubjectId(int subjectId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		return ICommonExamAjax.getSubjectsTypeBySubjectId(subjectId);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getTypeByAssignmentOverall(
			String assignmentOverall) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		
		Map<Integer, String> schemeMap =ICommonExamAjax.getTypeByAssignmentOverall(assignmentOverall);
        schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
	}

	public int getMaxTheoryMarcks(BigDecimal marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, int studentId, String assignmentOverall, String type) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		return iCommonExamAjax.getMaxTheoryMarcks(marcks, courseId, schemeNo,
				subjectId, subjectType, studentId,assignmentOverall,type);
		
	}

	public int getMaxPracticalMarcks(int marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, String assignmentOverall) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		return iCommonExamAjax.getMaxPracticalMarcks(marcks, courseId,
				schemeNo, subjectId, subjectType,assignmentOverall);
	}

	public String getExamDateTimeByExamId(int examId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		return ICommonExamAjax.getExamDateTimeByExamId(examId);
	}

	public ArrayList<KeyValueTO> getSubjectCodeName(String sCodeName, int examId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		ArrayList<KeyValueTO> courseMap =ICommonExamAjax.getSubjectCodeName(sCodeName, examId);
		Collections.sort(courseMap,new KeyValueTOComparator());
		return courseMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsByCourseSchemeExamId(int courseId,
			int schemeId, int schemeNo, Integer examId) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = iCommonExamAjax.getSubjectsByCourseSchemeExamId(
				courseId, schemeId, schemeNo,examId);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsByCourseSchemeExamIdJBY(int courseId,
			int schemeId, int schemeNo, Integer examId, Integer jby) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = iCommonExamAjax.getSubjectsByCourseSchemeExamIdJBY(
				courseId, schemeId, schemeNo,examId,jby);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}
	
	public ArrayList<KeyValueTO> getCoursesByProgramTypes1(String pids) {
		
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		 ArrayList<KeyValueTO> courseMap = ICommonExamAjax
				.getCoursesByProgramTypes1(pids);
		 Collections.sort(courseMap,new KeyValueTOComparator());
		return courseMap;
	}
	public Map<String, String> getSchemeNoByAcademicYear(
			String CourseId, String acdemicYear) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		@SuppressWarnings("unchecked")
		ArrayList<CurriculumSchemeUtilBO> listBO = new ArrayList(ICommonExamAjax
				.getSchemeByAcademicYear(Integer.parseInt(CourseId), Integer
						.parseInt(acdemicYear)));
		ExamGenHelper helper = new ExamGenHelper();
		return helper.convertBOToTO_course_SchemeId_SchemeNo(listBO);

	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByExamName(String examName) 
	{
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax.getProgramsByExamName(examName);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsByProgram(Integer programId) 
	{
		ICommonExamAjax iCommonAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = iCommonAjax.getSubjectsByProgram(programId);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	public Integer getAcademicYearByExam(int examId) throws Exception 
	{
		ICommonExamAjax iCommonAjax = CommonAjaxExamImpl.getInstance();
		Integer academicYear = iCommonAjax.getAcademicYear(examId);
		return academicYear;
	}
	
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeExamId(String sCodeName,int courseId,
			int schemeId, int schemeNo, Integer examId,Integer academicYear) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = iCommonExamAjax.getSubjectsCodeNameByCourseSchemeExamId(
				sCodeName,courseId, schemeId, schemeNo,examId,academicYear);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}
	public ArrayList<KeyValueTO> getSubjectFromRevaluationOrRetotaling(String sCodeName, int examId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		List<Object> listBO =ICommonExamAjax.getSubjectFromRevaluationOrRetotaling(sCodeName, examId);
		ArrayList<KeyValueTO> courseMap=convertBOToTO_Subject(listBO,sCodeName);
		Collections.sort(courseMap,new KeyValueTOComparator());
		return courseMap;
	}
	public ArrayList<KeyValueTO> convertBOToTO_Subject(
			List<Object> listBO, String sCodeName) {
		ArrayList<KeyValueTO> listOfValues = new ArrayList<KeyValueTO>();

		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object row[] = (Object[]) itr.next();
			if (sCodeName.equalsIgnoreCase("sCode")) {
				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[1].toString() + "( "
						+ row[2].toString()+")"));
			} else {

				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[2].toString() + "( "
						+ row[1].toString()+")"));
			}
		}

		return listOfValues;
	}
	public Map<Integer, String> getExamNameByAcademicYearAndExamType(String academicYear,String examType) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getExamNameByAcademicYearAndExamType(academicYear,examType);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	
	public Map<Integer, String> getClassNameByExamNameForSupplementary(
			int examId) {
		ICommonExamAjax iCommonAjax = CommonAjaxExamImpl.getInstance();
		return iCommonAjax.getClassNameByExamNameForSupplementary(examId);
	}
	
	public Map<Integer, String> getClassesByCourseSemesterAndAcademicYear(
			int semester, int academicyear, int programTypeId,
			int programId, int courseId) {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> classMap = iCommonAjax.getClassesByCourseSemesterAndAcademicYear(semester, academicyear, programTypeId, programId, courseId);
		classMap = CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getSchemeNoByExamId(int examId) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<String, String> schemeMap =ICommonExamAjax.getSchemeNoByExamId(examId);
        schemeMap = (Map<String, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByTeacher(String teacherId, String year) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> courseMap = ICommonExamAjax
				.getCourseByTeacher(teacherId,year);
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getSchemeNoByCourseIdByTeacher(int courseId, String teacherId, int year) {
		ICommonExamAjax ICommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<String, String> schemeMap =ICommonExamAjax.getSchemeNoByCourseIdByTeacher(courseId, teacherId, year);
        schemeMap = (Map<String, String>) CommonUtil.sortMapByValue(schemeMap);
        return schemeMap;
		
	}

	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeIdByTeacher(String sCodeName,int courseId,
			int schemeId, int schemeNo, String teacherId) {
		ICommonExamAjax iCommonExamAjax = CommonAjaxExamImpl.getInstance();
		Map<Integer, String> schemeMap = iCommonExamAjax.getSubjectsCodeNameByCourseSchemeIdByTeacher(
				sCodeName,courseId, schemeId, schemeNo,teacherId);
		schemeMap = (Map<Integer, String>) CommonUtil.sortMapByValue(schemeMap);
		return schemeMap;
	}
}
