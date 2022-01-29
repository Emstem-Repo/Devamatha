package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.ValuationStatisticsForm;



public interface IValuationStatisticsTransaction {

	List<Integer> getTotalSubjects(ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	int getValuationNotStarted(ValuationStatisticsForm valuationStatisticsForm, List<Integer> subjects) throws Exception;

	List<MarksEntryDetails> getMarksEntryDetails(ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	List getDataForQuery(String query, List<Integer> subjectList) throws Exception;

	List getDataForCurrentClassStudents(ValuationStatisticsForm valuationStatisticsForm) throws Exception ;

	List<Subject> getTotalSubjectsForExam(ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	List<ExamValidationDetails> getExamValidationList(String examId) throws Exception;

	List<Object[]> getTotalVerfiedStudentForSupplementary(String examId,List<Integer> schemeNoList, List<Integer> deptSubjects,boolean absent) throws Exception;

	List<Object[]> getTotalVerfiedStudent(String examId,
			List<Integer> schemeNoList, List<Integer> deptSubjects, boolean absent, String examType) throws Exception;

	List<ExamValuationProcess> getProcessCompletedDetails() throws Exception;

	Map<Integer, List<Student>> getTotalStudentsForSubjects(List<Integer> deptSubjects, ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	Map<Integer, List<Student>> getMarkEntryStudents(List<Integer> deptSubjects,
			ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	List<Student> getTotalStudents(String queryForCurrentClassStudents) throws Exception;

	List<MarksEntryDetails> getDetailsForView(ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	List<Student> getTotalStudents1(String queryForSupplementaryCurrentClassStudents) throws Exception;

	Map<Integer, String> getVerificationDetailsForView(ValuationStatisticsForm valuationStatisticsForm) throws Exception;


	List<Integer> getTotalSubjectsForUser(ValuationStatisticsForm valuationStatisticsForm) throws Exception ;

	List<Object[]> getTotalVerfiedStudentForSupplementaryForDashBord(String examId, List<Integer> schemeNoList, List<Integer> subjects,boolean b) throws Exception;

	List<Object[]> getTotalVerfiedStudentForDashBord(String examId,	List<Integer> schemeNoList, List<Integer> subjects, boolean b, String examType) throws Exception;

	List<Integer> getIssuedForValuationSubjects(ValuationStatisticsForm valuationStatisticsForm) throws Exception;

	void setExamSubjectDatesToForm(List<Integer> deptSubjects,
			ValuationStatisticsForm valuationStatisticsForm) throws Exception;



}
