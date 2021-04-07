package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;

public interface IUploadEmployeeDetailsTransaction {

	Map<String, Employee> getUploadEmployeeList()throws Exception;

	Map<String, String> getDepartmentMap()throws Exception;

	Map<String, String> getDesignationMap()throws Exception;

	Map<String, String> getCountyMap()throws Exception;

	Map<String, String> getStateMap()throws Exception;

	boolean saveEmployeeDetails(List<Employee> list)throws Exception;

	Map<String, String> getEmpTypeMap()throws Exception;
	
	Map<String, String> getJobTitleMap()throws Exception;
	
	Map<String, String> getWorkLocationMap()throws Exception;
	
	Map<String, String> getStreamMap()throws Exception;

}
