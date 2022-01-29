package com.kp.cms.transactions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.bo.exam.SemesterWiseExamResults;

public interface IUpdateStudentSGPATxn {
	public ArrayList<Integer> getClassIdsByCourseAndScheme(int courseId, int schemeNo, Integer academicYear) throws Exception;
	public Integer getStudentsTheoryMarkForSubj(Integer subjectId, Integer studentId, int classId) throws Exception;
	public Integer getStudentsPracticalMarkForSubj(Integer subjectId, Integer studentId, int classId) throws Exception;
	public List<Object[]> getStudentsInternalMarkForSubj(Integer subjectId, Integer studentId, int classId) throws Exception;
	public Double getGradePointForSubject(Integer subjectId, int courseId, Double per) throws Exception;
	public Integer getCreditForSubject(Integer subjectId, int courseId, int schemeNo, String subjectType, Double per, Integer academicYear) throws Exception;
	public boolean updateSgpa(List<ExamStudentSgpa> sgpaList) throws Exception;
	public Map<Integer, Double> getSubjectMark(ArrayList<Integer> subjectList,
			Integer academicYear, Integer courseId, int schemeNo) throws Exception;
	public boolean deleleAlreadyExistedRecords(int classId, int schemeNo) throws Exception;
	public ArrayList<Integer> getSubjectsForStudentWithPrevExam(Integer studentId, String isPrevExam, int schemeNo);
	public String getResultClass (int courseId, Double per, int year, int sid) throws Exception;
	public ArrayList<ExamStudentSgpa> getStudentSemesterResults(int studentId) throws Exception;
	public ArrayList<Integer> getClassIdsByCourse(int courseId,Integer academicYear) throws Exception;
	public boolean updateCCPA(List<ExamStudentCCPA> ccpaList,boolean isRankGeneration) throws Exception;
	public ArrayList<ExamStudentSgpa> getStudentSemesterCount(int courseId) throws Exception;
	public ArrayList<ExamStudentSgpa> getStudentResultDetails(int stuId,int appliedYear) throws Exception;
	public boolean saveSemesterWiseMarksDetails(List<SemesterWiseExamResults> boList) throws Exception;
	public boolean deleleAlreadyExistingCCPARecords(int courseId,int batchYear)throws Exception;
	public ArrayList<ExamStudentCCPA> getStudentCCPAForRank(int appliedYear,int courseId) throws Exception;
	public Map<Integer, String> getExtraLearning(Integer stuId, int courseId)throws Exception;
	public Map<Integer, String> getminMarks(int courseId, String year)throws Exception;
}

