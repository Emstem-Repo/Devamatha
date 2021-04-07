package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ResetLeavesForm;

public interface IResetLeavesTransaction {
	List<Employee> getEmployeeByEmpTypeId(String empQuery) throws Exception;
	List<EmpInitializeLeaves> getEmpInitializeLeavesByEmpTypeId(String empInitQuery) throws Exception;
	Map<Integer, Integer> getExistedLeavesForLeaveTypeAndEmpType(String empTypeId, int id,ResetLeavesForm resetLeavesForm) throws Exception;
	boolean saveEmpLeaves(List<EmpLeave> empLeaveList) throws Exception;
}
