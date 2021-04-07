package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;

public interface IApplicationReceivedTransaction {
	
	public List<Integer> getAppNoRangeList(String searchQuery, boolean isOnline)throws Exception;

	public List<AdmAppln> getAllAdmAppls(String searchCondition)throws Exception;

}
