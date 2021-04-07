package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.Holidays;

public interface IEmployeeSettingsTxn {
	public List<EmployeeSettings> getEmpSettList();
	public List<EmpLeaveType> getEmpLeaveType();
	public EmployeeSettings getEmpSettingsListById(int id); 
	public Integer getCurrentApplicationNO();
	public boolean updateEmpSettings(EmployeeSettings empSettings);
}
