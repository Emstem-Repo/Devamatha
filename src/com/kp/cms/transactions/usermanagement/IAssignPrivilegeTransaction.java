package com.kp.cms.transactions.usermanagement;

import java.util.List;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Modules;

public interface IAssignPrivilegeTransaction {
	/**
	 * 
	 * @returns all the module and menus which are in active
	 * @throws Exception
	 */
	public List<Modules> getModuleDetails()throws Exception;
	/**
	 * 
	 * @param privileges
	 * @return Adds
	 * @throws Exception
	 */
	public boolean addPrivilege(List<AccessPrivileges> privileges)throws Exception;
	/**
	 * 
	 * @returns all the roles from access privilege table
	 * @throws Exception
	 */
	public List<Object[]> getRolesFromAcccessPrivilege()throws Exception;
	/**
	 * 
	 * @param roleId
	 * @return Deletes a privilege (makes inactive) for a particular role
	 * @throws Exception
	 */
	
	public boolean deletePrivilege(int roleId, String userId) throws Exception;
	
	/**
	 * Displays on click of view button
	 */
	public List<Object[]> getModuleMenuOnRole(int roleId)throws Exception;
	
	/**
	 * Check for duplicate on role
	 */
	
	public List<AccessPrivileges> getPrivilegebyRole(int roleId)throws Exception;
	
	/**
	 * Reactivation of the role
	 */
	
	public boolean reActivatePrivilege(int roleId, String userId)throws Exception;
	
	/**
	 * Used in Edit
	 */
	
	public List<AccessPrivileges> getRoleModuleMenuonroleId(int roleId)throws Exception;
	
	/**
	 * Used in update
	 */
	
	public boolean updatePrivilege(List<AccessPrivileges>updateList)throws Exception;

}
