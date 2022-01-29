package com.kp.cms.transactions.attandance;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.forms.attendance.ViewMyAttendanceLeaveForm;

public interface IViewMyAttendanceLeaveTransaction {

	public List<Attendance> getSearchMyAttendanceLeave(ViewMyAttendanceLeaveForm attendanceLeaveForm, Date attendanceLeaveDate)throws Exception;

	public List<Attendance> getSearchEmpAttnLeave(Date date, String teacherName)throws Exception;

}
