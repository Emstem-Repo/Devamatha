package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlHostel;

public interface IHostelAbsentiesReportTransactions {
	
	public java.util.List<HlHostel> getHostelNames() throws Exception;
	public java.util.List<HlGroup> getHlGroupDetails() throws Exception;
	public List<Object> getHostelAbsentDetails(String searchQuery) throws Exception ;

}
