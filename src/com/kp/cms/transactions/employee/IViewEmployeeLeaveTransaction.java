package com.kp.cms.transactions.employee;

import java.util.List;

public interface IViewEmployeeLeaveTransaction {

	 List<Object[]> getEmployeeDetails(String academicYear,
			String employeeId) throws Exception;

}
