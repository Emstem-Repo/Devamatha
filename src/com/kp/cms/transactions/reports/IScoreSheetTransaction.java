package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Grade;

public interface IScoreSheetTransaction {
	public List getListOfCandidates(String searchCriteria) throws Exception;
	public List<Grade> getListOfGrades() throws Exception;
}
