package com.kp.cms.handlers.reportusermanagement;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.ReportAccessPrivileges;
import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.bo.admin.ReportsMenus;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.reportusermanagement.ReportsMenuScreenMasterForm;
import com.kp.cms.helpers.reportusermanagement.ReportsMenusHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.transactions.reportusermanagement.IReportsMenusTransaction;
import com.kp.cms.transactionsimpl.reportusermanagement.ReportsMenusTransactionImpl;

public class ReportsMenusHandler {

	Logger log=Logger.getLogger(ReportsMenusHandler.class);
	
	/**
	 * This method is used to get a unique instance when you called handler. 
	 */
	
	public static volatile ReportsMenusHandler menusHandler = null;

	public static ReportsMenusHandler getInstance() {
		if (menusHandler == null) {
			menusHandler = new ReportsMenusHandler();
			return menusHandler;
		}
		return menusHandler;
	}
	
	IReportsMenusTransaction transaction = ReportsMenusTransactionImpl.getInstance();
	
	/**
	 * This method will get the menu details from IMPL and pass the list to helper for populateBOtoTO.
	 * @return list of type MenusTO
	 * @throws Exception
	 */
	
	public List<MenusTO> getMenuDetails()throws Exception
	{
		log.info("call of getMenuDetails in MenusHandler class.");
		List<ReportsMenus> menuList = transaction.getMenuDetails();
		List<MenusTO> menusTOList = ReportsMenusHelper.getInstance().populateBotoTO(menuList) ;
		log.info("end of getMenuDetails in MenusHandler class.");
		return menusTOList;
	}
	
	/**
	 * This method is used for checking duplicate entry.
	 * @param position
	 * @param moduleId
	 * @return Menus BO instance.
	 * @throws Exception
	 */
	
	public ReportsMenus menuNameExist(int position, int moduleId) throws Exception {
		log.info("call of menuNameExist in MenusHandler class.");
		//sending position,  to MenusTransactionImpl for Duplicate Entry checking 
		ReportsMenus menus = transaction.menuNameExist(position, moduleId);
		log.info("end of menuNameExist in MenusHandler class.");
		return menus;
	}
	
	/**
	 * This method is used to add menus from form
	 * @param screenMasterForm
	 * @return boolean value.
	 * @throws DuplicateException
	 * @throws Exception
	 */
	
	public boolean addMenus(ReportsMenuScreenMasterForm screenMasterForm) throws DuplicateException, Exception {
		log.info("call of addMenus in MenusHandler class.");
		ReportsMenus menus = ReportsMenusHelper.getInstance().convertTOstoBOs(screenMasterForm);
		//sending feeGroupId, fees name to MenusTransactionImpl for Duplicate Entry checking 
		boolean isAdded = transaction.addMenus(menus);
		log.info("end of addMenus in MenusHandler class.");
		return isAdded;
	}
	
	/**
	 * This method is used to get the menu details based on menu id.
	 * @param menuId
	 * @return MenusTO instance.
	 * @throws Exception
	 */
	
	public MenusTO editMenuDetails(int menuId) throws Exception{
		log.info("call of editMenuDetails in MenusHandler class.");
		//getting a list of type FeeHeading BO from MenusTransactionImpl based on id.
		List<ReportsMenus> menusList = transaction.editMenuDetails(menuId);
		//converting list of type FeeHeading BO to FeeHeading TO.
		MenusTO menusTO = ReportsMenusHelper.getInstance().convertBOstoTOsForEdit(menusList);
		log.info("end of editMenuDetails in MenusHandler class.");
		return menusTO;
	}
	
	/**
	 * This method is used to update Menus record
	 * @param screenMasterForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean updateMenus(ReportsMenuScreenMasterForm screenMasterForm) throws Exception{
		log.info("call of updateMenus in MenusHandler class.");
		ReportsMenus menus = new ReportsMenus();
		ReportModules modules = new ReportModules();
		
		modules.setId(Integer.parseInt(screenMasterForm.getModule()));
		menus.setModules(modules);
		int oldModuleId = screenMasterForm.getOldModuleId();
		menus.setId(screenMasterForm.getMenuId());
		menus.setIsActive(Boolean.TRUE);
		menus.setModifiedBy(screenMasterForm.getUserId());
		menus.setLastModifiedDate(new Date());
		menus.setDisplayName(screenMasterForm.getScreenName());
		menus.setPosition(Integer.parseInt(screenMasterForm.getSequence()));
		menus.setUrl(screenMasterForm.getPath().trim());
		menus.setIsMenuLink(true);
		boolean isUpdated = transaction.updateMenus(menus,oldModuleId);
		
		log.info("end of updateMenus in MenusHandler class.");
		
		return isUpdated;
	}
	
	/**
	 * This method is used to delete menus and making isActive = 0.
	 * @param menuId
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean deleteMenus(int menuId, String userId) throws Exception {
		log.info("call of deleteMenus in MenusHandler class.");
		//sending id to MenusTransactionImpl for deleting a record from database.
		boolean isDeleted = transaction.deleteMenus(menuId, userId);
		log.info("end of deleteMenus in MenusHandler class.");
		return isDeleted;
	}
	
	/**
	 * This method is used for reactivation.
	 * @param menuName
	 * @throws Exception
	 */
	
	public void reActivateMenus(int sequence,int moduleId, String userId) throws Exception { 
		log.info("call of reActivateMenus in MenusHandler class.");
		transaction.reActivateMenus(sequence,moduleId,userId);
		
		log.info("call of reActivateMenus in MenusHandler class.");
		
	}

	public List<AssignPrivilegeTO> getReportsAssignPrivilegeRole(int menuId) throws Exception{
		log.info("Inside of getRolesFromAcccessPrivilege of PrivilegeHandler");
		List<Object[]> roleIdList=transaction.getReportRolesByMenuId(menuId);
		List<Object[]> roleList=transaction.getReportsAssignPrivilegeRole();
		log.info("End of getRolesFromAcccessPrivilege of PrivilegeHandler");
		return ReportsMenusHelper.getInstance().populateAssignPrivilegeBotoTO(roleList,roleIdList);
	}

	public boolean addMenuAssignAggrement(ReportsMenuScreenMasterForm screenMasterForm) throws Exception{
		log.info("call of addMenus in MenusHandler class.");
		List<ReportAccessPrivileges> privileges = ReportsMenusHelper.getInstance().convertTOstoBOsAssignPrivilege(screenMasterForm);
		boolean isAdded = transaction.addMenuAssignAggrement(privileges);
		log.info("end of addMenus in MenusHandler class.");
		return isAdded;
	}
}