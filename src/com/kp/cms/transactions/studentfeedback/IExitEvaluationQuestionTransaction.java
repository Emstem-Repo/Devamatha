package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;

public interface IExitEvaluationQuestionTransaction {
	
	public boolean addExitEvalQuestion(ExitEvaluationQuestion exitEvalQues) throws Exception;
	
	public List<ExitEvaluationQuestion> getAllExitEvalQues() throws Exception;
	
	public List<ExitEvaluationQuestion> editExitEvalQues(int id) throws Exception;
	
	public boolean updateExitEvalQues(ExitEvaluationQuestion exitEvalQues) throws Exception;
	
	public boolean deleteExitEvalQues(int id, String userId) throws Exception;
	
	public void reActivateExitEvalQues(String feesName, String userId)throws Exception;
	
	public ExitEvaluationQuestion isExitEvalQuesExist(int topicId, String question) throws Exception;

}
