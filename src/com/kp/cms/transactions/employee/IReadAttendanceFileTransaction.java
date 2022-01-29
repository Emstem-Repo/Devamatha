package com.kp.cms.transactions.employee;

import java.util.Date;
import java.util.List;
import com.kp.cms.bo.admin.EmpAttendance;

public interface IReadAttendanceFileTransaction {

	boolean addAttendance(List<EmpAttendance> listOfBo)throws Exception;

	Integer getEmployeeId(String employeeCode)throws Exception;

	EmpAttendance getEmployeeAttendanceForDate(int employeeId,String attendanceDate)throws Exception ;

}
