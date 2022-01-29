package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.ViewMyAttendanceForm;
import com.kp.cms.to.employee.EmpApplyLeaveTO;

public interface IViewMyAttendancetransaction {

	List<EmpAttendance> getEmpAttendanceBO(String query) throws Exception;

	List<EmpApplyLeave> getEmpLeaveList(String query) throws Exception;

	String getEmployeeName(ViewMyAttendanceForm viewAttForm) throws Exception;

	List<Holidays> getHolidays() throws Exception;

	List<EmpEventVacation> getEmpEventVacations(String queryForEventVacation) throws Exception;

	Employee getEmployeeDetails(int userId, ViewMyAttendanceForm viewAttForm,String employee) throws Exception ;


}
