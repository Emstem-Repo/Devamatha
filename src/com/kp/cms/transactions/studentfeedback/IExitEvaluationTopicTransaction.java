package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;

public interface IExitEvaluationTopicTransaction {
	
	public List<ExitEvaluationTopic> getExitEvalTopics();
	
	public ExitEvaluationTopic isExitEvalTopicDuplicated(ExitEvaluationTopic oldExitEvalTopic) throws Exception;
	
	public boolean addExitEvalTopic(ExitEvaluationTopic exitEvalTopic) throws Exception;
	
	public boolean updateExitEvalTopic(ExitEvaluationTopic exitEvalTopic) throws Exception;
	
	public boolean deleteExitEvalTopic(int exitEvalTopicId,String userId) throws Exception;
	
	public boolean reActivateExitEvalTopic(ExitEvaluationTopic exitEvalTopic,String userId) throws Exception;
	
	public ExitEvaluationTopic getExitEvalTopicList(int exitEvalTopicId);

}
