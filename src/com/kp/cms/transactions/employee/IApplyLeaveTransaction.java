package com.kp.cms.transactions.employee;

import java.sql.Date;
import java.util.List;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.forms.employee.ApplyLeaveForm;

public interface IApplyLeaveTransaction {
	//Emp leave type list which are active
	List<EmpLeaveType> getLeaveTypeList() throws Exception;
	//adding the emp leaves to database
	boolean applyLeave(EmpApplyLeave empAppLeave)throws Exception;
	int getemployeeId(String userId)throws Exception;
	boolean checkDuplicateLeaves(String query) throws Exception;
	public List<EmpApplyLeave> getApplyLeaveDetails(String employeeId)throws Exception;
	public List<EmpLeave> getAllotedLeaveTypeList(String employeeId) throws Exception;
	public boolean isHoliday(Date time, String employeeId)throws Exception;
	public Integer getRemainingDays(ApplyLeaveForm applyLeaveForm)throws Exception;
	public boolean isReportingMangerAssigned(String employeeId)throws Exception;
	public boolean isEmployeeTypeSet(String employeeId)throws Exception;
	public boolean cancelLeave(Integer leaveId)throws Exception;
	public EmpApplyLeave getLeaveDetails(Integer leaveId)throws Exception;
	public boolean startCancellation(ApplyLeaveForm applyLeaveForm)throws Exception;
	public Integer getPendingDays(ApplyLeaveForm applyLeaveForm)throws Exception;
}
