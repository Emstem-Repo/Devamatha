package com.kp.cms.transactions.employee;


public interface IGeneratePasswordForEmployeeTransaction {

	boolean updatePassword(String query,String userId) throws Exception;

}
