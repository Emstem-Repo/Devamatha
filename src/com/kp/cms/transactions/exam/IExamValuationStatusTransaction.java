package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.CourseSchemeDetails;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.ExamValuationStatusForm;


public interface IExamValuationStatusTransaction {


	List<ExamValidationDetails> getExamValidationList(String currentExam) throws Exception;

	List<Object[]> getValuationDetails(String examId) throws Exception;

	List<Subject> getSubjectList(String examId) throws Exception;

	List<Object[]> getTotalVerfiedStudent(String examId, String termNumber, String courseId,Boolean finalYears, boolean absent, Integer internalExamId, String examType) throws Exception;

	List<MarksEntryDetails> getDetailsForView(ExamValuationStatusForm examValuationStatusForm) throws Exception;

	List<Course> getCourseList() throws Exception;

	List<Student> getTotalStudents(String query) throws Exception;

	Map<Integer, String> getVerificationDetailsForView(ExamValuationStatusForm examValuationStatusForm) throws Exception;

	boolean saveProcessCompletedDetails(List<ExamValuationProcess> boList) throws Exception;

	List<ExamValuationProcess> getProcessCompletedDetails(int examId) throws Exception;

	List<Object[]> getTotalVerfiedStudentForSupplementary(String examId,String termNumber, String courseId, Boolean finalYears, boolean absent, Integer internalExamId) throws Exception;

	List<Object[]> getTotalStudents1(String queryForSupplementaryCurrentClassStudents) throws Exception;

	List<Object[]> checkMismatchFoundForStudents(List<Integer> courseListIds, List<Integer> subjectListIds, ExamValuationStatusForm examValuationStatusForm)throws Exception;

	String getProgramTypeByCourseId(int courseId)throws Exception;

	Integer getInternalExamId(String examId) throws Exception ;
	
	List<CourseSchemeDetails> getCourseByExamNameForStatus(int examId) throws Exception ;

	Map<Integer, String> getExamSubjectDatesMap(List<Integer> subjectListIds, String examId) throws Exception;


}
