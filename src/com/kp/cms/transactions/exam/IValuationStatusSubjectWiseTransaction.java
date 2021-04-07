package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;

public interface IValuationStatusSubjectWiseTransaction {

	public List getDataForQuery(String currentStudentCountQuery)throws Exception;

	public List<Integer> getAbsentStudentIds(String absentStudentQuery)throws Exception;

	public List<ExamValidationDetails> getvaluationDetailsData( String valuationDetailsQuery)throws Exception;


}
