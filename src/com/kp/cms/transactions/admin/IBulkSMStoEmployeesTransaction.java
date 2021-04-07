package com.kp.cms.transactions.admin;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;

public interface IBulkSMStoEmployeesTransaction {

	public Map<Integer, String> getRollMap() throws Exception;
	public List<Users> getEmployeeList(String selectedRolls) throws Exception;
	public List<Integer> getUserIds(Date smsDate)throws Exception;

}
