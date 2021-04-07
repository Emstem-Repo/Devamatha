package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.forms.employee.WorkTimeEntryForm;

public interface IWorkTimeEntryTransaction {
	public void isWorkTimeEntryDuplcated(WorkTimeEntryForm workEntryForm) throws Exception;
	public boolean addWorkTimeEntry(List<EmpWorkTime> workTime, String mode) throws Exception;
	public List<EmpWorkTime> getWorkTime() throws Exception;
	public boolean deleteWorkTimeEntry(int employeeTypeId) throws Exception;
	public List<EmpWorkTime> getWorkTimeEntryToEdit(WorkTimeEntryForm workTimeEntryForm) throws Exception;
	public boolean updateWorkTimeEntry(List<EmpWorkTime> worktimeListBo) throws Exception;
	public List<EmployeeTypeBO> getEmployeeTypeList() throws Exception;
	public boolean reActivateWorkTimeEntry(int employeeTypeId) throws Exception;
}
