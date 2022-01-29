package com.kp.cms.transactions.usermanagement;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.DuplicateException;

/**
 * Interface for IUserInfoTransactionImpl
 */
public interface IUserInfoTransaction {
	
	public Map<Integer, String> getDepartment()throws Exception;

	public Map<Integer, String> getDesignation()throws Exception;
	
	public Map<Integer, String> getRoles()throws Exception;
	
	public boolean addUserInfo(Users users, String mode) throws DuplicateException,Exception;
	
	public List<Users> getTeachingStaffs() throws Exception;

	public List<Users> getUsers(String dob, String firstName, String middleName, String lastName, String dep) throws Exception;
	
	public List<Users> getUsersById(int id) throws Exception;
	
	public Boolean deleteUsers(Integer userId) throws Exception;
	
	public boolean isUserNameDuplcated(String userName, int uId) throws Exception;

	public int getemployeeId(int id)throws Exception;
	public int getGuestId(int id)throws Exception;
	
	public void deleteEmployee(int employeeId)throws Exception;	
	
	public void deleteGuest(int guestId)throws Exception;	
	
	public Map<Integer, String> getDepartmentMap()throws Exception;
	public Map<Integer, String> getGuestMap()throws Exception;

}
