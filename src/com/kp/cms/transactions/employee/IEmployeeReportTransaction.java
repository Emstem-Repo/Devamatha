package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;

public interface IEmployeeReportTransaction {

	public Map<String, String> getStreamMap()throws Exception;

	public Map<String, String> getDepartmentMap()throws Exception;

	public Map<String, String> getDesignation()throws Exception;

	public Map<String, String> getWorkLocationMap()throws Exception;

	public List<Employee> getSearchedEmployee(StringBuffer query)throws Exception;

}
