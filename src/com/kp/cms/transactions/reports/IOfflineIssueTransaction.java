package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.OfflineDetails;

public interface IOfflineIssueTransaction {

	List<OfflineDetails> getAllOfflineDetails(String SearchCriteria) throws Exception;

}
