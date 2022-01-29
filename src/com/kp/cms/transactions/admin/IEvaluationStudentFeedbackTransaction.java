package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.EvaluationStudentFeedback;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentFeedbackInstructions;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;

public interface IEvaluationStudentFeedbackTransaction {


	public StudentLogin getStudentLoginDetails(String userId)throws Exception;

	public List<Integer> getSubjectIds(int admApplnId)throws Exception;

	public List<TeacherClassSubject> getTeacherClassSubjects(List<Integer> subjectIds, String classSchemeWiseId)throws Exception;

	public List<EvaStudentFeedbackQuestion> getfacultyEvalQuestionList()throws Exception;

	public boolean saveFacultyEvaluationFeedback( EvaluationStudentFeedback facultyEvaluationFeedback)throws Exception;

	public boolean checkStuIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception;

	public List<EvaStudentFeedBackGroupTo> getGroupDetailsList( EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception;

	public String getBatchId(int studentId, int classId, int subjectId)throws Exception;

	public String getAttendancePercentage(String attendanceQuery)throws Exception;

	public int getprogramtypeId(EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception;
	
	public List<ExitEvaluationQuestion> getExitEvalQuestionList() throws Exception;
	
	public boolean checkExitEvalIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception;
	
	public EvaluationStudentFeedback getEvalFeedBack( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception;
	
	public boolean saveExitEvaluationFeedback( EvaluationStudentFeedback facultyEvaluationFeedback) throws Exception;

	public List<EvaStudentFeedbackQuestion> getSubjectWiseEvalQuestionList()throws Exception;



}
