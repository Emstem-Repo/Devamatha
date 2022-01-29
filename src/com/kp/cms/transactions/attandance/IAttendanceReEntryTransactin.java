package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.AttendanceReEntryForm;

public interface IAttendanceReEntryTransactin {

	List<Student> getStudent(String query) throws Exception;

	List<Object[]> getDuplicateAttendance(String query,AttendanceReEntryForm attendanceReEntryForm) throws Exception;

	List<Attendance> getAttendanceList(String query) throws Exception;

	Student getStudentByRegNo(String studentByRegnoQuery) throws Exception;

	boolean saveAttendanceStudent(List<AttendanceStudent> finalList) throws Exception;

	List<Integer> getAttendanceIdByAttendanceStudent(String query) throws Exception;

}
