package com.kp.cms.transactions.employee;

import java.util.List;

public interface IEmployeeSearchTransaction {
	
	public List<Object[]> getEmployeeDetails(String search) throws Exception;

}