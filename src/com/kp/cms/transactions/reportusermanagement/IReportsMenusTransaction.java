package com.kp.cms.transactions.reportusermanagement;

import java.util.List;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.ReportAccessPrivileges;
import com.kp.cms.bo.admin.ReportsMenus;
import com.kp.cms.exceptions.DuplicateException;

public interface IReportsMenusTransaction {

	public List<ReportsMenus> getMenuDetails() throws Exception;

	public ReportsMenus menuNameExist(int position, int moduleId) throws Exception;

	public boolean addMenus(ReportsMenus menus) throws DuplicateException, Exception;

	public List<ReportsMenus> editMenuDetails(int menuId) throws Exception;

	public boolean updateMenus(ReportsMenus menus,int oldModuleId) throws Exception;
	
	public boolean deleteMenus(int menuId, String userId) throws Exception ;
	
	public void reActivateMenus(int sequence,int moduleId, String userId) throws Exception;

	public List<Object[]> getReportRolesByMenuId(int menuId) throws Exception;

	public List<Object[]> getReportsAssignPrivilegeRole() throws Exception;

	public boolean addMenuAssignAggrement(List<ReportAccessPrivileges> privileges) throws Exception;
}
