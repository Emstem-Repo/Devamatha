package com.kp.cms.transactions.reports;

import java.util.List;

public interface ICandidatesRecommendorTransaction {
	
	public List<Object[]> getAppRecommendorDetails(String query) throws Exception;

}