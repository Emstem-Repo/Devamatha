package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.bo.admin.EmployeeDutyPerformed;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceEntryDayForm;
import com.kp.cms.forms.attendance.AttendanceEntryForm;




public interface IAttendanceEntryDayTransaction {
	public List<AttendanceEntryDay> getAttendanceEntyDays()throws Exception;
	public boolean addAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay ,String mode)
	throws Exception;
	public boolean deleteAttendanceEntryDay(int id, boolean activate,
			AttendanceEntryDayForm attendanceEntryDayForm) throws Exception;
	public boolean updateAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay) throws Exception;
	public boolean reActivateAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay,String userId) throws Exception;
	public AttendanceEntryDay getAttendanceEntryDayDetails(String id) throws Exception;
	
	public AttendanceEntryDay isDuplicateAttendanceEntryDayDetail(
			AttendanceEntryDayForm attendanceEntryDayForm, String mode) throws Exception;
	public AttendanceEntryDay getLastAttendanceEntryDayDetails();
	public void addAttendanceEntryDayByRemainder(AttendanceEntryDay attendanceEntryDay);
	public List<EmployeeDutyPerformed> getDutyPerformedList() throws Exception;
	public EmployeeDutyPerformed isDuplicateEmpDutyPerformed(
			AttendanceEntryForm attendanceEntryForm, String mode) throws ApplicationException;
	
	public boolean addEmployeeDutyPerformed(EmployeeDutyPerformed bo ,String mode)
			throws Exception;

}
