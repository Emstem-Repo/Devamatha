package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.bo.employee.HodApprover;

public interface IEmployeeApproverTransaction {
	

	List<Employee> getEmployeeDetailsDeptWise(String departmentId) throws Exception;

	Map<Integer, Employee> getAllApproverEmployees() throws Exception;

	boolean saveDetails(List<EmployeeApprover> boList) throws Exception;
	boolean saveHodDetails(List<HodApprover > boList) throws Exception;
	Map<Integer, Employee> getAllHodApproverEmployees() throws Exception;

}
