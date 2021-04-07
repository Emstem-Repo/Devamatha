package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.FeeDivision;

public interface IDivisionReportTransaction {
	public List<FeeDivision> getFeeDivisions(String divId) throws Exception;
}
