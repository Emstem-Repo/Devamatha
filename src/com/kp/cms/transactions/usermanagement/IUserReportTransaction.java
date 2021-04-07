package com.kp.cms.transactions.usermanagement;

import java.util.List;

import com.kp.cms.bo.admin.Users;

public interface IUserReportTransaction {
	public List<Users> getUsers(String dob, String firstName, String middleName, String lastName, String dep) throws Exception;

}
