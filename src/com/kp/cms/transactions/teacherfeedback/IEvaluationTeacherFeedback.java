package com.kp.cms.transactions.teacherfeedback;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedback;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackAnswer;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;

public interface IEvaluationTeacherFeedback {

	List<EvaTeacherFeedbackOpenConnection> getConnectionClassesList(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	List<Student> getStudentsForFeedBack(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	boolean checkStudentIsDuplicate(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	EvaluationTeacherFeedback getExistsBO(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	boolean saveTeacherEvaluationFeedback(EvaluationTeacherFeedback evaluationTeacherFeedback, EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	Map<Integer, String> getClassesListForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;
	
	List<EvaluationTeacherFeedbackAnswer> getTecherFeedbackDetailsForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	Map<Integer, Double> getStudentInternalMarks(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	Map<Integer, List<EvaTeacherFeedBackQuestionTo>> getPointsScoredByTeacher(
			EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)throws Exception;

	Map<Integer, String> getSubjectsByClassForTeacher(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	List<Integer> getStudentsFeedbackAleardyGiven(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;

	Map<Integer, String> getTeacherMapByYearForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception;
}
