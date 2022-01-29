package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.PendingExamResultsForm;


public interface IPendingExamResultsTransactions {

	public List<Object[]> getPendingExamResultsList(PendingExamResultsForm objForm) throws Exception;

	public List<StudentSupplementaryImprovementApplication> getTotalStudents(String queryForCurrentClassStudents) throws Exception;

//	public boolean addPendingExamResults(List<PendingExamResults> pendingExamResults, ActionErrors errors,PendingExamResultsForm objForm);
	
	List<Object[]> getTotalVerfiedStudentForSupplementary(String examId,String classId , boolean absent, Integer internalExamId) throws Exception;
	
	List<Object[]> getTotalVerfiedStudent(String examId ,String classId ,Boolean finalYears, boolean absent, Integer internalExamId) throws Exception;
	
	List<Object[]> checkMismatchFoundForStudents(List<Integer> courseListIds, List<Integer> subjectListIds, PendingExamResultsForm objForm)throws Exception;
	
	List<MarksEntryDetails> getDetailsForView(PendingExamResultsForm objForm) throws Exception;
	
	List<Student> getTotalStudentsForVerifyDetails(String query) throws Exception;
	
	List<Object[]> getTotalStudents1(String queryForSupplementaryCurrentClassStudents) throws Exception;
	
	Map<Integer, String> getVerificationDetailsForView(PendingExamResultsForm objForm) throws Exception;
}
