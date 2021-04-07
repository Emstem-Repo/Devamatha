package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.employee.LeaveInitializationForm;

public interface ILeaveInitializationTransaction {

	Map<String, EmpLeave> getExistedData( LeaveInitializationForm leaveInitializationForm,int accumulateLeaveTypeId) throws Exception;

	Map<Integer, EmpLeave> getOldAcumulateLeaves(int accLeaveId, LeaveInitializationForm leaveInitializationForm) throws Exception;

	boolean saveLeaveInitialization(List<EmpLeave> boList) throws Exception;

	Map<Integer, String> getMonthByEmployeeType() throws Exception;

}
