package com.kp.cms.transactions.employee;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.forms.employee.EmpEventVacationForm;

public interface IEmpEventVacation {
	
	public Map<String, String> getDepartments()throws Exception;

	public Boolean submitEvent(EmpEventVacation empEventVacation)throws Exception;

	public List<EmpEventVacation> getEmpVacationList()throws Exception;

	public EmpEventVacation getEmpEventVacation(String id)throws Exception;

	public List<EmpEventVacation> getEmpEventUnique(EmpEventVacationForm empEventVacationForm)throws Exception;

	public Map<Integer, String> getStreamsMap()throws Exception;

	public List<Department> getSearchedDepartmentStreamNames(String streamId,String teachingStaff)throws Exception;

	public List<Department> getSearchedDepartmentStreamNames(String teaching)throws Exception;
	
	public List<EmpEventVacation> getEmpReactivation(EmpEventVacationForm empEventVacationForm) throws Exception ;

}
