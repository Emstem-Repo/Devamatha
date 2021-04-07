package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;

/**
 * INTERFACE FOR EMPLOYEE INFORMATION TRANSACTION
 *
 */
public interface IEmployeeInfoTransaction {

	Employee getEmployeeDetails(String code, String lastName, String firstName,
			String middleName, String nickName) throws Exception;

	boolean saveEmployeePersonalInfo(Employee employee) throws Exception;

	List<Employee> getAllEmployees()throws Exception;

	List<Object[]> getEmployeeInfoDetails(String code, String lastName,
			String firstName, String middleName, String nickName) throws Exception;

	Integer addUserInfo(Users users) throws Exception;

	boolean isUserNameDuplicated(String userName,int employeeId)throws Exception;

	int getUserId(int id)throws Exception;

	Employee getEmployeeDetailsByEmployeeId(int employeeId)throws Exception;
	
	Users getUserDetailsByEmployeeId(int employeeId)throws Exception;

	boolean isFingerDuplicated(String fingetPrintId,int employeeId)throws Exception;

	boolean isCodeDuplicated(String newCode, int id)throws Exception ;
}
