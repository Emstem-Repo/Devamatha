package com.kp.cms.transactions.attandance;



import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceCondonationForm;

public interface IAttendanceCondonationTransaction {

	List getCurrentClassStudents(AttendanceCondonationForm stform, int i) throws ApplicationException;

	List getEditStudent(AttendanceCondonationForm stform) throws ApplicationException;

	boolean SaveStudents(List studentBo) throws ApplicationException;

	List<Object[]> getstudentattendance(Student bo,AttendanceCondonationForm stform, int mode) throws ApplicationException;

	List<Object[]> gettotalattendance(AttendanceCondonationForm stform) throws ApplicationException;

	

}
