package com.kp.cms.transactions.studentfeedback;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportPrincipalForm;

public interface IFacultyEvaluationReportPrincipal {

	public Map<Integer, String> getClasses(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,int year) throws Exception;
	public Map<Integer, String> getSubjects(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,int year) throws Exception;
	public List<Object[]> getFacultyEvaluationDetails(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception;
	public List<EvaStudentFeedbackQuestion> getEvaluationQuestions();
	public List<Object[]> getAnswers(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,int queId,int ansId,String teacherId,String subjectId);
	public List<Object[]> getTeachersByClass(String string);
	public List<Object[]> getTeachersByDepartment(String subjectId);
	public boolean isDataExist(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm);
	public List<Object[]> getTeachersAndSubjects(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm);
	public List<Object[]> getFacultyEvaluationDetailsDepartmentWise(String teacher, String subject, FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,String classId);
	public Department getDepartment(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm);
	public List<EvaluationStudentFeedbackFaculty> getRemarksGivenByStudent(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, String teacher);
}
