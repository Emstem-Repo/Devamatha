package com.kp.cms.transactions.studentfeedback;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;

public interface IFacultyEvaluationReport {
	public Map<Integer, String> getClasses(FacultyEvaluationReportForm facultyEvaluationReportForm,int year) throws Exception;
	public Map<Integer, String> getSubjects(FacultyEvaluationReportForm facultyEvaluationReportForm,int year) throws Exception;
	public List<Object[]> getFacultyEvaluationDetails(FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception;
	public List<EvaStudentFeedbackQuestion> getEvaluationQuestions();
	public List<Object[]> getAnswers(FacultyEvaluationReportForm facultyEvaluationReportForm,int queId,int ansId,String teacherId,String subjectId);
	public List<Object[]> getTeachersByClass(String string);
	public List<Object[]> getTeachersByDepartment(FacultyEvaluationReportForm facultyEvaluationReportForm);
	public boolean isDataExist(
			FacultyEvaluationReportForm facultyEvaluationReportForm);
	public List<EvaluationStudentFeedbackFaculty> getRemarksGivenByStudent(
			FacultyEvaluationReportForm facultyEvaluationReportForm);
}
