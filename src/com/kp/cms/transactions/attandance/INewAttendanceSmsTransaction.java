package com.kp.cms.transactions.attandance;


import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;

public interface INewAttendanceSmsTransaction {

	List<Integer> getAllPeriodIdsByInput(NewAttendanceSmsForm newAttendanceSmsForm);

	List<Object[]> getAbsentees(int year, String[] classes, java.sql.Date date);

	boolean updateAttendanceStudent(List<AttendanceStudent> atteStudList);

	List<AttendanceStudent> getAttendanceStudent(String string) throws Exception;

	List<Integer> getStudentIds(java.sql.Date attDate) throws Exception;

	List<Object[]> getAbsenteesForSms(int year, String[] classes,java.sql.Date date, String dayOfthePeriods)throws Exception;

}
