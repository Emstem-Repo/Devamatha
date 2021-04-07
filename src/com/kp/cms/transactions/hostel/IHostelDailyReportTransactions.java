package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlHostel;

public interface IHostelDailyReportTransactions {
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public List<Object> getHostelDailyReportDetails(String searchQuery) throws Exception;

}
