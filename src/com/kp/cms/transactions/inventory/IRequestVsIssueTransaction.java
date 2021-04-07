package com.kp.cms.transactions.inventory;

import java.util.List;

public interface IRequestVsIssueTransaction {

	public List<Object[]> getRequestIssueList(int invLocationId, String startDate, String endDate) throws Exception;
}
