package com.kp.cms.helpers.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.forms.usermanagement.MenuScreenMasterForm;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;

public class MenusHelper {
	
	/**
	 * This method is used to get a unique instance when you called handler. 
	 */
	
	public static volatile MenusHelper menusHelper = null;

	public static MenusHelper getInstance() {
		if (menusHelper == null) {
			menusHelper = new MenusHelper();
			return menusHelper;
		}
		return menusHelper;
	}
	
	/**
	 * This method is used for conversion of BO list to TO list.
	 * @param list
	 * @return list of type MenusTO 
	 */
	
	public List<MenusTO>populateBotoTO(List<Menus> list)
	{
		List<MenusTO> menusList = new ArrayList<MenusTO>();
		if(list!=null && !list.isEmpty()){
			Menus menus;
			MenusTO menusTO;
			ModuleTO moduleTO;
			Iterator<Menus> iterator = list.iterator();
			while (iterator.hasNext()) {
				menus = (Menus) iterator.next();
				menusTO = new MenusTO();
				moduleTO = new ModuleTO();
				moduleTO.setName(menus.getModules().getDisplayName());
				moduleTO.setId(menus.getModules().getId());
				
				menusTO.setModuleTO(moduleTO);
				menusTO.setId(menus.getId());
				if(menus.getDisplayName()!=null && menus.getDisplayName().length()>27){
					menusTO.setName(splitString(menus.getDisplayName()));
				}else{
					menusTO.setName(menus.getDisplayName());
				}
				if(menus.getUrl()!=null && menus.getUrl().length()>27){
					menusTO.setUrl(splitString(menus.getUrl()));
				}else{
					menusTO.setUrl(menus.getUrl());
				}
				menusTO.setPosition(menus.getPosition());
				
				menusList.add(menusTO);
			}
	}
		return menusList;
}
	
	/**
	 * This method is used to split the string.
	 * @param value
	 * @return string value.
	 */
	
	public String splitString(String value){
		StringBuffer appendedvalue = new StringBuffer();
		int length = value.length();
		String[] temp = new String[length];
		int begindex = 0, endindex = 27;
		int count = 0;

		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + 27;
				endindex = endindex + 27;
				appendedvalue.append(temp[count]).append(" ").toString();
			} else {
				if (count == 0)
					temp[count] = value.substring(0, length);
				temp[count] = value.substring(begindex, length);
				appendedvalue.append(temp[count]).toString();
				break;
			}
			count++;
		}
		return appendedvalue.toString();
	}
	
	/**
	 * This method is used for conversion of TOs to BOs and 
	 * @param screenMasterForm
	 * @return Menus BO instance
	 */
	
	public Menus convertTOstoBOs(MenuScreenMasterForm screenMasterForm){
		Menus menus = new Menus();
		Modules modules = new Modules();
		
		modules.setId(Integer.parseInt(screenMasterForm.getModule()));
		menus.setModules(modules);
		
		menus.setNewtab(screenMasterForm.getNewtab().equalsIgnoreCase("true")? true:false);
		menus.setIsActive(Boolean.TRUE);
		menus.setCreatedBy(screenMasterForm.getUserId());
		menus.setModifiedBy(screenMasterForm.getUserId());
		menus.setCreatedDate(new Date());
		menus.setDisplayName(screenMasterForm.getScreenName());
		menus.setPosition(Integer.parseInt(screenMasterForm.getSequence()));
		menus.setUrl(screenMasterForm.getPath().trim());
		
		menus.setIsMenuLink(true);
		
		return menus;
	}
	
	/**
	 * This method is get the details of Menus BO and setting to MenusTO
	 * @param menusList
	 * @return instance of type MenusTO.
	 */
	
	public MenusTO convertBOstoTOsForEdit(List<Menus> menusList){
		
		MenusTO menusTO = new MenusTO();
		ModuleTO moduleTO = new ModuleTO();
		Menus menus;
		Iterator<Menus> iterator = menusList.iterator();
		while (iterator.hasNext()) {
			menus = (Menus) iterator.next();
			
			moduleTO.setName(menus.getModules().getDisplayName());
			moduleTO.setId(menus.getModules().getId());
			
			menusTO.setModuleTO(moduleTO);
			menusTO.setId(menus.getId());
			menusTO.setName(menus.getDisplayName());
			menusTO.setUrl(menus.getUrl());
			menusTO.setPosition(menus.getPosition());
			menusTO.setNewtab(menus.getNewtab());
		}
		
		return menusTO;
	}
	// Added By Manu For AssignPrivilage In menuScreen
	/**
	 * @param roleList
	 * @param roleIdList
	 * @return
	 */
	public List<AssignPrivilegeTO> populateAssignPrivilegeBotoTO(List<Object[]> roleList, List<Object[]> roleIdList) {
		List<AssignPrivilegeTO> privilegeList=new ArrayList<AssignPrivilegeTO>();
		AssignPrivilegeTO assignPrivilegeTO;
		if(roleList!=null && !roleList.isEmpty()){
			Iterator<Object[]> iterator=roleList.iterator();
			while (iterator.hasNext()) {
				Object[] row = iterator.next();
				assignPrivilegeTO=new AssignPrivilegeTO();
				assignPrivilegeTO.setRoleId((Integer)row[0]);
				assignPrivilegeTO.setRoleName((String)row[1]);
				
			Iterator<Object[]> itr=roleIdList.iterator();
		    	while (itr.hasNext()) {
				  Object[] obj = (Object[]) itr.next();
				   if(obj[1]!=null){
				     if(row[0].equals(obj[1])){
					   assignPrivilegeTO.setTempChecked("on");
					   assignPrivilegeTO.setId((Integer)obj[0]);
					   break;
				     }
				  }
			   }
		    privilegeList.add(assignPrivilegeTO);
			}
			if(privilegeList!=null && !privilegeList.isEmpty()){
				return privilegeList;
			}
		}
		return new ArrayList<AssignPrivilegeTO>();
	}

	/**
	 * @param screenMasterForm
	 * @return
	 */
	public List<AccessPrivileges> convertTOstoBOsAssignPrivilege(MenuScreenMasterForm screenMasterForm) {
		
		List<AccessPrivileges> accessPrivList = new ArrayList<AccessPrivileges>();
		int menuId=screenMasterForm.getMenuId();
		int moduleId=screenMasterForm.getModuleId();
		
		Iterator<AssignPrivilegeTO> iterator=screenMasterForm.getPrivilegeList().iterator();
			
			while (iterator.hasNext()) {
				
				AssignPrivilegeTO assignPrivilegeTO= iterator.next();
				AccessPrivileges privileges = new AccessPrivileges();	
				Roles roles;
				Menus menus;
				Modules modules;
				if(assignPrivilegeTO.getId()>0){
					privileges.setId(assignPrivilegeTO.getId());
					
				if(assignPrivilegeTO.getChecked()!=null){
					
					roles = new Roles();
					menus = new Menus();
					modules = new Modules();
					
					roles.setId(assignPrivilegeTO.getRoleId());
					menus.setId(menuId);
					modules.setId(moduleId);
					
					privileges.setRoles(roles);
					privileges.setModules(modules);
					privileges.setMenus(menus);
					
					privileges.setIsActive(true);
					privileges.setCreatedBy(screenMasterForm.getUserId());
					privileges.setCreatedDate(new Date());
					privileges.setModifiedBy(screenMasterForm.getUserId());
					privileges.setLastModifiedDate(new Date());
					privileges.setAllowAccess(true);
					accessPrivList.add(privileges);
											
				      }else{
				    	    roles = new Roles();
							menus = new Menus();
							modules = new Modules();
							
							roles.setId(assignPrivilegeTO.getRoleId());
							menus.setId(menuId);
							modules.setId(moduleId);
							
							privileges.setRoles(roles);
							privileges.setModules(modules);
							privileges.setMenus(menus);
							
							privileges.setIsActive(false);
							privileges.setLastModifiedDate(new Date());
							privileges.setModifiedBy(screenMasterForm.getUserId());
							privileges.setLastModifiedDate(new Date());
							privileges.setAllowAccess(false);
							accessPrivList.add(privileges);
				      }
			      }else{
			    	     if(assignPrivilegeTO.getChecked()!=null){
							
							roles = new Roles();
							menus = new Menus();
							modules = new Modules();
							
							roles.setId(assignPrivilegeTO.getRoleId());
							menus.setId(menuId);
							modules.setId(moduleId);
							
							privileges.setRoles(roles);
							privileges.setModules(modules);
							privileges.setMenus(menus);
							
							privileges.setIsActive(true);
							privileges.setCreatedBy(screenMasterForm.getUserId());
							privileges.setCreatedDate(new Date());
							privileges.setModifiedBy(screenMasterForm.getUserId());
							privileges.setLastModifiedDate(new Date());
							privileges.setAllowAccess(true);
							accessPrivList.add(privileges);
					   }
			      }
			  }
			return accessPrivList;		
		}
	// end By Manu For AssignPrivilage In menuScreen
}