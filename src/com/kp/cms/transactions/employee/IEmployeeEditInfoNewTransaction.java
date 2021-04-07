package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;

public interface IEmployeeEditInfoNewTransaction {

	

	List<Employee> getSerchedEmployee(StringBuffer query) throws Exception;

	StringBuffer getSerchedEmployeeQuery(int departmentId, int designationId,
			int streamId, int empTypeId, Integer eid, Integer rid2,
			int principleid, int adminid, int testid, String tempTeachingStaff) throws Exception;

	List<Department> getEmployeeDepartment() throws Exception;

	List<Designation> getEmployeeDesignation() throws Exception;

	Employee GetEmpDetails(int employeeId) throws Exception;

	boolean saveEmployee(Employee employee,EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception;

	Users userExists(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception;

	boolean updateUser(Users user) throws Exception;

	Map<String, String> getDesignationMap() throws Exception;

	Map<String, String> getDepartmentMap() throws Exception;

	Users getUser(EmployeeInfoEditNewForm objform) throws Exception;

	GuestFaculty getGuestDetails(int guestId) throws Exception;

	

	boolean saveGuestData(GuestFaculty guest,EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception;

	Users userExists1(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception;

	

	
		
}
