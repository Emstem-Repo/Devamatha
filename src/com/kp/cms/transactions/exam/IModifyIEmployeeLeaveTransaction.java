package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.employee.ModifyEmployeeLeaveForm;

public interface IModifyIEmployeeLeaveTransaction {

	Object[] getEmployeeDetails(String empCode, String fingerPrintId) throws Exception;

	EmpLeave getRemainingDaysForEmployeeAndLeaveType(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, int year) throws Exception;

	boolean saveApplyLeave(EmpApplyLeave bo, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, List<Integer> commonLeaves,double daysDifference, int year,String mode, double daysBetween);

	double getLeavesTaken(String query) throws Exception;

	public List<EmpApplyLeave> getEmployeeLeaveDetails(String empCode, String fingerPrintId, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm) throws Exception;

	EmpApplyLeave getLeaveDetails(int id) throws Exception;

	boolean cancelLeave(EmpApplyLeave bo, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, List<Integer> commonLeaves, double daysBetween, int year) throws Exception;
	
	int getEmployeeTypeIdB(int empId)throws Exception;

}
