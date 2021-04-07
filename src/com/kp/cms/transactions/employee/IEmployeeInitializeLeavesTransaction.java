package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.forms.employee.EmployeeInitializeLeavesForm;

public interface IEmployeeInitializeLeavesTransaction {

	List<EmpInitializeLeaves> getInitializeData() throws Exception;

	boolean addEmployeeInitialize(EmpInitializeLeaves empInitializeLeaves) throws Exception;

	EmpInitializeLeaves getEmpInitializeLeavesByEmpTypeId(String empTypeId,String leaveTypeId,String allotedDate) throws Exception;
	public boolean deleteEmployeeInitialize(int empId) throws Exception;
	public EmpInitializeLeaves getEmpInitializeLeavesToEdit(EmployeeInitializeLeavesForm employeeInitializeLeavesForm)throws Exception;
	public boolean updateEmployeeInitialize(EmpInitializeLeaves empInitializeLeaves)throws Exception;
	public boolean reActivateEmployeeInitialize(int empId) throws Exception;
}
