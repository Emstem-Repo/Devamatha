package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.ConfigReportsColumn;

public interface IConfigureColumnTransaction {
	
	public boolean saveColumnForReport(ConfigReportsColumn configReportsColumn)throws Exception;
	public int getMaxPosition(String reportName) throws Exception;
	
	/**
	 * Used to get all the report names
	 */
	
	public List<String> getReportNames()throws Exception;
	
	public List<Object[]> getDetailsOnReportName(String reportName)throws Exception;
	
	public boolean updateConfigReport(List<ConfigReportsColumn> newList) throws Exception;
}
