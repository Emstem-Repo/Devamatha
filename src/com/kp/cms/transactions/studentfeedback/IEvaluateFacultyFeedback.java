package com.kp.cms.transactions.studentfeedback;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackOverallAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackScore;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.studentfeedback.EvaluateFacultyFeedbackForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;


public interface IEvaluateFacultyFeedback {
	public Map<Integer, String> getClasses(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm,int year) throws Exception;
	public List<Object[]> getTeachersByClass(String classId);
	public List<Object[]> getAnswers(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm,int queId,int ansId,String subjectId);
	public List<EvaStudentFeedbackQuestion> getEvaluationQuestions();
	public boolean setAverage(EvaStudentFeedbackAverage avg);
	public boolean setOverallAverage(
			Set<EvaStudentFeedbackOverallAverage> overallAvg);
	public boolean setScore(EvaStudentFeedbackOverallAverage overallAvgBo);
	public boolean deleteIfExist(
			EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm) throws Exception;
	public int getSemester(String classId);

}
