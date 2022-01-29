package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;

public interface IEvaluationStudentFeedbackSessionTransaction {

	public List<EvaluationStudentFeedbackSession> getFeedbackSessionList()throws Exception;

	public boolean addFeedbackSession( EvaluationStudentFeedbackSessionTo sessionTo, EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm, String mode)throws Exception;

	public EvaluationStudentFeedbackSession editStuFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)throws Exception;

	public boolean deleteEvalStudentFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)throws Exception;

	public boolean checkDuplicateSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)throws Exception;

}
