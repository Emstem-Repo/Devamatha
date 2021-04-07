package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpAttendanceBc;
import com.kp.cms.forms.employee.ManualAttendanceEntryForm;

public interface IManualAttendanceEntryTransaction {

	public boolean markEmployeesAttendance(EmpAttendance manualAttendance, EmpAttendanceBc attendanceBc)throws Exception;
	
	public EmpAttendance isAttendanceTaken(String attendanceDate, Integer userId)throws Exception;

	public List<Object[]> getEmployeesToMarkAttendanceList(int departmentId,
			int designationId, String date)throws Exception;

	public List<EmpAttendance> getEmployeesAttendanceList(String query)throws Exception;

	public EmpAttendance getEmployeeAttendance(Integer id)throws Exception;

	public boolean deleteAttendance(Integer id,String userId, EmpAttendanceBc attendanceBc)throws Exception;

	public boolean reActivateAttendance(Integer id, String userId,EmpAttendanceBc attendanceBc) throws Exception;
}
