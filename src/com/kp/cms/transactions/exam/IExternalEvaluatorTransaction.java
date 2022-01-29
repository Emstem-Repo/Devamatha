package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.forms.exam.ExternalEvaluatorForm;

public interface IExternalEvaluatorTransaction {
	
	public boolean addExternalEvaluator(ExamValuators examValuators)throws Exception;
	
	public List<ExamValuators> getExternalEvaluatorDetails()throws Exception;
	
	public boolean deleteExternalEvaluator(int id, String userId)throws Exception;
	
	public boolean updateExternalEvaluator(ExamValuators examValuators)throws Exception;
	
	public boolean reActivateExternalEvaluator(String name, String userId)throws Exception;
	
	public ExamValuators getDetailsonId(int id)throws Exception ;

	public ExamValuators checkForDuplicateonName(String name)throws Exception;

	public boolean checkForDuplicateonName1(String name, ExternalEvaluatorForm externalEvaluatorForm)throws Exception;

}
