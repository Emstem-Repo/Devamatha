package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmpAcademicHolidays;
import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.forms.employee.HolidaysForm;
import com.kp.cms.to.employee.HolidaysTO;

public interface IHolidaysTransaction {

	public List<EmployeeTypeBO> getEmployeeTypeList() throws Exception;

	public boolean addHolidays(EmpAcademicHolidays empHolidays, List<EmpAcademicHolidaysDates> datesTobeDeleted) throws Exception;
	
	public List<EmpAcademicHolidays> getEmployeeHolidaysList() throws Exception;

	public EmpAcademicHolidays getHolidaysDetailsToEdit(int id) throws Exception;
	
	public AcademicYear getYearId(HolidaysForm holidaysForm) throws Exception;

	public boolean deleteHolidays(int id, boolean activate, String userId) throws Exception;

	public EmpAcademicHolidays duplicateCheck(String query) throws Exception;

}
