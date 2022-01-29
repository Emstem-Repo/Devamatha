package com.kp.cms.transactions.exam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;

public interface IEmployeeApplyLeaveTransaction {
	

	Employee getEmployeeDetails(String empCode, String fingerPrintId) throws Exception;

	double getRemainingDaysForEmployeeAndLeaveType(EmployeeApplyLeaveForm employeeApplyLeaveForm,int year) throws Exception;

	boolean saveApplyLeave(List<EmpLeave> boList, EmployeeApplyLeaveForm employeeApplyLeaveForm);

	double getLeavesTaken(String query) throws Exception;
	
    public List<EmpApplyLeave> getEmpApplyLeaves(int userId,int year)throws Exception;
	
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

	List<EmpOnlineLeave> getEmpOnlineLeaves(int parseInt, int year) throws Exception;

	public String getDepartmentNameForFingerPrintId(String fingerPrintId)throws Exception;

	public String getDepartmentNameForEmpCode(String empCode)throws Exception;

	public String getDepartmentNameForEmpName(String employeeName)throws Exception;

	public List<EmpOnlineLeave> getDepartmentWiseOnlineleave(String fingerPrintId, int academicYear, String mode)throws Exception;

	public List<EmpOnlineLeave> getOnlineLeavesWithName(String employeeName, int academicYear)throws Exception;

	

}
