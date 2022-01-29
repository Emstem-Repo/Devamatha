package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamEndDate;
import com.kp.cms.forms.exam.ExamEndDateForm;

public interface IExamEndDates {
	List getDataForQuery(String query) throws Exception;
	List getDataFromTable( String query) throws Exception;
	
}
