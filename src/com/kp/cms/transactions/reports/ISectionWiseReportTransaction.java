package com.kp.cms.transactions.reports;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;

public interface ISectionWiseReportTransaction {
	
	public List<Object[]> getSectionWiseReportDetails(String searchCriteria) throws Exception;

	public Map<Integer, ExamStudentDetentionRejoinDetails> getAllStudentDetentionDetails() throws Exception;

}
