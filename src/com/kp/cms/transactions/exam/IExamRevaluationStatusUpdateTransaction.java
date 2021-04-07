package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.forms.exam.ExamRevaluationStatusUpdateForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public interface IExamRevaluationStatusUpdateTransaction {

	List<ExamRevaluationAppDetails> getRevaluationAppDetailsList(String registerNo) throws Exception;

	boolean saveNewStatus(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm) throws Exception;

}
