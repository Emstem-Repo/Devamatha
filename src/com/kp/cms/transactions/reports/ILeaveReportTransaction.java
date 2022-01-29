package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.StudentLeave;

public interface ILeaveReportTransaction {
	
	public List<StudentLeave> getLeaveReportDetails(String searchCriteria) throws Exception;
	

}
