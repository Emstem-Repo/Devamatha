package com.kp.cms.handlers.teacherfeedback;

import java.util.List;

import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackSessionForm;
import com.kp.cms.helpers.teacherfeedback.EvaluationTeacherFeedbackSessionHelper;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo;
import com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaluationTeacherFeedbackSessionTxnImpl;

public class EvaluationTeacherFeedbackSessionHandler {
	public static volatile EvaluationTeacherFeedbackSessionHandler evaluationTeacherFeedbackSessionHandler = null;
	public static EvaluationTeacherFeedbackSessionHandler getInstance(){
		if(evaluationTeacherFeedbackSessionHandler == null){
			evaluationTeacherFeedbackSessionHandler = new EvaluationTeacherFeedbackSessionHandler();
			return evaluationTeacherFeedbackSessionHandler;
		}
		return evaluationTeacherFeedbackSessionHandler;
	}
	IEvaluationTeacherFeedbackSessionTransaction transaction = EvaluationTeacherFeedbackSessionTxnImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public List<EvaluationTeacherFeedbackSessionTo> getFeedbackSessionList() throws Exception{
		List<EvaluationTeacherFeedbackSession> sessionBoList = transaction.getFeedbackSessionList();
		List<EvaluationTeacherFeedbackSessionTo> list = EvaluationTeacherFeedbackSessionHelper.getInstance().copyFromBoToTO(sessionBoList);
		return list;
	}
	/**
	 * @param evaTeacherFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean addTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm,String mode) throws Exception{
		EvaluationTeacherFeedbackSessionTo sessionTo = EvaluationTeacherFeedbackSessionHelper.getInstance().copyFromFormToTO(evaTeacherFeedbackSessionForm);
		boolean isAdded = transaction.addFeedbackSession(sessionTo,evaTeacherFeedbackSessionForm,mode);
		return isAdded;
	}
	/**
	 * @param evaTeacherFeedbackSessionForm
	 */
	public void editTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception{
		EvaluationTeacherFeedbackSession feedbackSession = transaction.editTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		evaTeacherFeedbackSessionForm  = EvaluationTeacherFeedbackSessionHelper.getInstance().populateBoTOTo(feedbackSession,evaTeacherFeedbackSessionForm);
		
	}
	/**
	 * @param evaTeacherFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEvaTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception{
		boolean isDeleted = transaction.deleteEvalTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		return isDeleted;
	}
	/**
	 * @param evaTeacherFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception{
		boolean isExist = transaction.checkDuplicateSession(evaTeacherFeedbackSessionForm);
		return isExist;
	}
}
