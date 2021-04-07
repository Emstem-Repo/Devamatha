package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm;
import com.kp.cms.helpers.admin.EvaluationStudentFeedbackSessionHelper;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction;
import com.kp.cms.transactionsimpl.admin.EvaluationStudentFeedbackSessionTxnImpl;

public class EvaluationStudentFeedbackSessionHandler {
	public static volatile EvaluationStudentFeedbackSessionHandler evaluationStudentFeedbackSessionHandler = null;
	public static EvaluationStudentFeedbackSessionHandler getInstance(){
		if(evaluationStudentFeedbackSessionHandler == null){
			evaluationStudentFeedbackSessionHandler = new EvaluationStudentFeedbackSessionHandler();
			return evaluationStudentFeedbackSessionHandler;
		}
		return evaluationStudentFeedbackSessionHandler;
	}
	IEvaluationStudentFeedbackSessionTransaction transaction = EvaluationStudentFeedbackSessionTxnImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public List<EvaluationStudentFeedbackSessionTo> getFeedbackSessionList() throws Exception{
		List<EvaluationStudentFeedbackSession> sessionBoList = transaction.getFeedbackSessionList();
		List<EvaluationStudentFeedbackSessionTo> list = EvaluationStudentFeedbackSessionHelper.getInstance().copyFromBoToTO(sessionBoList);
		return list;
	}
	/**
	 * @param evaStuFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean addStuFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm,String mode) throws Exception{
		EvaluationStudentFeedbackSessionTo sessionTo = EvaluationStudentFeedbackSessionHelper.getInstance().copyFromFormToTO(evaStuFeedbackSessionForm);
		boolean isAdded = transaction.addFeedbackSession(sessionTo,evaStuFeedbackSessionForm,mode);
		return isAdded;
	}
	/**
	 * @param evaStuFeedbackSessionForm
	 */
	public void editStuFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm) throws Exception{
		EvaluationStudentFeedbackSession feedbackSession = transaction.editStuFeedbackSession(evaStuFeedbackSessionForm);
		evaStuFeedbackSessionForm  = EvaluationStudentFeedbackSessionHelper.getInstance().populateBoTOTo(feedbackSession,evaStuFeedbackSessionForm);
		
	}
	/**
	 * @param evaStuFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEvaStudentFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm) throws Exception{
		boolean isDeleted = transaction.deleteEvalStudentFeedbackSession(evaStuFeedbackSessionForm);
		return isDeleted;
	}
	/**
	 * @param evaStuFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistStuFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm) throws Exception{
		boolean isExist = transaction.checkDuplicateSession(evaStuFeedbackSessionForm);
		return isExist;
	}
}
