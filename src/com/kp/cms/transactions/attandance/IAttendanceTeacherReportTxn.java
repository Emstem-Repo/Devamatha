package com.kp.cms.transactions.attandance;

import java.util.Date;
import java.util.List;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;

public interface IAttendanceTeacherReportTxn {
	public List getTeacherAttendance(String searchCriteria) throws ApplicationException;
	public List getPeriodDetails(int userId, int classId, int subjectId, String startDate, String endDate, String batchName) throws ApplicationException;
	public String getUserName(int userId);
	public List<Integer> getClassesByTeacherId(int teacherId,int year);
	public List<Integer> getClassesByTeacherIdReport(int teacherId,int year);
	public Date getStartDate(List classes,int academicYear);
	public List getAbsentStudents(AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception;
	public List getUserIds(AttendanceTeacherReportForm attendanceTeacherReportForm);
	public List getUserIdsFromTeacherDepartment(AttendanceTeacherReportForm attendanceTeacherReportForm);
	public List getActivityPeriodDetails(int userId, int classId, String startDate, String endDate) throws ApplicationException;
	public String getEmpId(int userId)throws Exception;
}
