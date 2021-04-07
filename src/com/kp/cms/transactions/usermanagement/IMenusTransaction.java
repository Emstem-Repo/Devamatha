package com.kp.cms.transactions.usermanagement;

import java.util.List;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.exceptions.DuplicateException;

public interface IMenusTransaction {

	public List<Menus> getMenuDetails() throws Exception;

	public Menus menuNameExist(int position, int moduleId) throws Exception;

	public boolean addMenus(Menus menus) throws DuplicateException, Exception;

	public List<Menus> editMenuDetails(int menuId) throws Exception;

	public boolean updateMenus(Menus menus,int oldModuleId) throws Exception;
	
	public boolean deleteMenus(int menuId, String userId) throws Exception ;
	
	public void reActivateMenus(int sequence,int moduleId, String userId) throws Exception;

	public List<Object[]> getRolesFromAcccessPrivilege() throws Exception;

	public List<Object[]> getRolesByMenuId(int id) throws Exception;

	public boolean addMenuAssignAggrement(List<AccessPrivileges> privileges) throws Exception;
}
