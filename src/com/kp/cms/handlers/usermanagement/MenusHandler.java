package com.kp.cms.handlers.usermanagement;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.usermanagement.MenuScreenMasterForm;
import com.kp.cms.helpers.usermanagement.MenusHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.transactions.usermanagement.IMenusTransaction;
import com.kp.cms.transactionsimpl.usermanagement.MenusTransactionImpl;

public class MenusHandler {

	Logger log=Logger.getLogger(MenusHandler.class);
	
	/**
	 * This method is used to get a unique instance when you called handler. 
	 */
	
	public static volatile MenusHandler menusHandler = null;

	public static MenusHandler getInstance() {
		if (menusHandler == null) {
			menusHandler = new MenusHandler();
			return menusHandler;
		}
		return menusHandler;
	}
	
	IMenusTransaction transaction = MenusTransactionImpl.getInstance();
	
	/**
	 * This method will get the menu details from IMPL and pass the list to helper for populateBOtoTO.
	 * @return list of type MenusTO
	 * @throws Exception
	 */
	
	public List<MenusTO> getMenuDetails()throws Exception
	{
		log.info("call of getMenuDetails in MenusHandler class.");
		List<Menus> menuList = transaction.getMenuDetails();
		List<MenusTO> menusTOList = MenusHelper.getInstance().populateBotoTO(menuList) ;
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
	
	public Menus menuNameExist(int position, int moduleId) throws Exception {
		log.info("call of menuNameExist in MenusHandler class.");
		//sending position,  to MenusTransactionImpl for Duplicate Entry checking 
		Menus menus = transaction.menuNameExist(position, moduleId);
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
	
	public boolean addMenus(MenuScreenMasterForm screenMasterForm) throws DuplicateException, Exception {
		log.info("call of addMenus in MenusHandler class.");
		Menus menus = MenusHelper.getInstance().convertTOstoBOs(screenMasterForm);
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
		List<Menus> menusList = transaction.editMenuDetails(menuId);
		//converting list of type FeeHeading BO to FeeHeading TO.
		MenusTO menusTO = MenusHelper.getInstance().convertBOstoTOsForEdit(menusList);
		log.info("end of editMenuDetails in MenusHandler class.");
		return menusTO;
	}
	
	/**
	 * This method is used to update Menus record
	 * @param screenMasterForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean updateMenus(MenuScreenMasterForm screenMasterForm) throws Exception{
		log.info("call of updateMenus in MenusHandler class.");
		Menus menus = new Menus();
		Modules modules = new Modules();
		
		modules.setId(Integer.parseInt(screenMasterForm.getModule()));
		menus.setModules(modules);
		int oldModuleId = screenMasterForm.getOldModuleId();
		menus.setId(screenMasterForm.getMenuId());
		menus.setIsActive(Boolean.TRUE);
		menus.setModifiedBy(screenMasterForm.getUserId());
		menus.setLastModifiedDate(new Date());
		menus.setDisplayName(screenMasterForm.getScreenName());
		menus.setNewtab(screenMasterForm.getNewtab().equalsIgnoreCase("true")? true:false);
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

	public List<AssignPrivilegeTO> getRolesFromAcccessPrivilege(int id) throws Exception{
		log.info("Inside of getRolesFromAcccessPrivilege of PrivilegeHandler");
		List<Object[]> roleIdList=transaction.getRolesByMenuId(id);
		List<Object[]> roleList=transaction.getRolesFromAcccessPrivilege();
		log.info("End of getRolesFromAcccessPrivilege of PrivilegeHandler");
		return MenusHelper.getInstance().populateAssignPrivilegeBotoTO(roleList,roleIdList);
	}

	public boolean addMenuAssignAggrement(MenuScreenMasterForm screenMasterForm) throws Exception{
		log.info("call of addMenus in MenusHandler class.");
		List<AccessPrivileges> privileges = MenusHelper.getInstance().convertTOstoBOsAssignPrivilege(screenMasterForm);
		boolean isAdded = transaction.addMenuAssignAggrement(privileges);
		log.info("end of addMenus in MenusHandler class.");
		return isAdded;
	}
}