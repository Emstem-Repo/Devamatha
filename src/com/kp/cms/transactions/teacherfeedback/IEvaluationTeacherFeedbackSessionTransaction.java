package com.kp.cms.transactions.teacherfeedback;

import java.util.List;

import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackSessionForm;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo;

public interface IEvaluationTeacherFeedbackSessionTransaction {

	public List<EvaluationTeacherFeedbackSession> getFeedbackSessionList()throws Exception;

	public boolean addFeedbackSession( EvaluationTeacherFeedbackSessionTo sessionTo, EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm, String mode)throws Exception;

	public EvaluationTeacherFeedbackSession editTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm)throws Exception;

	public boolean deleteEvalTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm)throws Exception;

	public boolean checkDuplicateSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm)throws Exception;

}
