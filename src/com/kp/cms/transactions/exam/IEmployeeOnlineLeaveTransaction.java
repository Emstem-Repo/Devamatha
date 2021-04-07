package com.kp.cms.transactions.exam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.EmployeeOnlineLeaveForm;

public interface IEmployeeOnlineLeaveTransaction {
	

	Employee getEmployeeDetails(String userId) throws Exception;

	double getRemainingDaysForEmployeeAndLeaveType(EmployeeOnlineLeaveForm employeeOnlineLeaveForm,int year) throws Exception;

	boolean saveApplyLeave(EmpOnlineLeave bo);

	double getLeavesTaken(String query) throws Exception;
	
    public List<EmpOnlineLeave> getEmpApplyLeaves(int userId,int year)throws Exception;
	
	public List<EmpLeave> getEmpLeaves(int userId,int year)throws Exception;
	
	public String getEmpMonth(int userId)throws Exception;
	
	public Date getStartDate(int userId)throws Exception;
	
	public List<EmpApplyLeave> getApplyLeavesWithFingerPrintId(int fingerPrintId,int year)throws Exception;
	
	public List<EmpLeave> getEmpLeaves(String fingerPrintId,int year,String mode)throws Exception;
	
	public String getEmpMonthWithFingerPrintId(int fingerPrintId)throws Exception;
	
	public Date getStartDateWithFingerPrintId(int fingerPrintId)throws Exception;
	
	public List<EmpApplyLeave> getApplyLeavesWithEmpCode(String empCode,int year)throws Exception;
	
	public String getEmpMonthWithEmpCode(String empCode)throws Exception;
	
	public Date getStartDateWithEmpCode(String empCode)throws Exception;
	
	public List<EmpLeave> getEmpLeavesWithEmpCode(String empCode,int year)throws Exception;
	
	public List<String> getFingerPrintIds(int userId)throws Exception;
	
	public List<String> getEmpCodes(int userId)throws Exception;
	
	public List<String> getEmpNames(int userId)throws Exception;
	
	public List<EmpApplyLeave> getApplyLeavesWithName(String empName,int year)throws Exception;
	
	public List<EmpLeave> getEmpLeavesWithName(String empName,int year)throws Exception;
	
	public String getEmpMonthWithEmpName(String empName)throws Exception;
	
	public Date getStartDateWithEmpName(String empName)throws Exception;
	
	public Map<String,String> getLeaveTypeMap()throws Exception;
	
	public boolean saveEmpApplyLeave(List<EmpApplyLeave> list,EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception;
	
	public int getInitializeMonth(String fingerPrintId,String mode,String name)throws Exception;

	Map<String, Integer> getAllEmployees() throws Exception;
	
	public boolean saveEmployeeApplyLeave(List<EmpApplyLeave> list,EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception;

	boolean checkLeaveType(String leaveTypeId) throws Exception;

	List<EmpOnlineLeave> getEmpApproveLeaves(int parseInt, int year) throws Exception;

	boolean saveAndApproveEmployeeLeave(List<EmpApplyLeave> approveList,EmployeeOnlineLeaveForm employeeOnlineLeaveForm,List<Integer> commonLeaves, Map<Integer, String> monthMap) throws Exception;

	Map<Integer, String> getEmployeeMap() throws Exception;

	void updateEmpOnlineLeave(List<Integer> forwardList,EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception;

	void rejectEmpOnlineLeave(List<Integer> forwardList,
			EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception;

	EmployeeApprover getApproverId(String employeeId) throws Exception;

	Employee getEmployee(String employeeId) throws Exception;

	public boolean approveEmployeeLeave(EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception;

	List<EmpOnlineLeave> getEmpAuthorizationLeaves(int parseInt, int year)throws Exception;

	void requestDocEmpLeave(List<Integer> forwardList, EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception;

	List<EmpOnlineLeave> getViewReturnedReqDoc(int parseInt)throws Exception;
	
	List<EmpOnlineLeave> getViewReturnedReqDocForApproval(int parseInt)throws Exception;
	
	boolean saveAndApproveReturnedLeaves(List<EmpApplyLeave> approveList,EmployeeOnlineLeaveForm employeeOnlineLeaveForm,List<Integer> commonLeaves, Map<Integer, String> monthMap) throws Exception;

}
