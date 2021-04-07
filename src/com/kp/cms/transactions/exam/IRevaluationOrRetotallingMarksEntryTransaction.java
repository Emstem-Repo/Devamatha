package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;

public interface IRevaluationOrRetotallingMarksEntryTransaction {
	public List<ExamRevaluationApplication> getStudentDetailsList(RevaluationOrRetotallingMarksEntryForm form)throws Exception;
	public boolean saveRevaluationOrRetotalingStudentRecords(List<RevaluationOrRetotallingMarksEntryTo> toList,RevaluationOrRetotallingMarksEntryForm form)throws Exception;
	Double getMaxMarkOfSubject(RevaluationOrRetotallingMarksEntryForm form,int id)throws Exception;
	boolean getEvaluatorType(int id,RevaluationOrRetotallingMarksEntryForm form)throws Exception;
	//new
	List getEvaluatorTypes(ExamRevaluationAppDetails ed) throws Exception;
}
