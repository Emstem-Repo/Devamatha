package com.kp.cms.helpers.usermanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.forms.usermanagement.AssignPrivilegeForm;
import com.kp.cms.handlers.usermanagement.AssignPrivilegeHandler;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.to.usermanagement.RolesTO;


public class AssignPrivilegeHelper {
	
	public static volatile AssignPrivilegeHelper assignPrivilegeHelper = null;
	
	private static final Log log = LogFactory.getLog(AssignPrivilegeHandler.class);

	/**
	 * 
	 * @return a single instance every time when called
	 */
	public static AssignPrivilegeHelper getInstance() {
		if (assignPrivilegeHelper == null) {
			assignPrivilegeHelper = new AssignPrivilegeHelper();
			return assignPrivilegeHelper;
		}
		return assignPrivilegeHelper;
	}
	/**
	 * 
	 * @param Coverts role BO to TO
	 * @return
	 */
	public List<RolesTO> populateRoleBotoTO(List<Roles> roles)throws Exception
	{	
		log.info("Inside of populateRoleBotoTO of Privilege Helper");
		List<RolesTO> rolesList=new ArrayList<RolesTO>();
		
		if(roles!=null)
		{
				Iterator<Roles> iterator=roles.iterator();
				while (iterator.hasNext()) {
						RolesTO rolesTO=new RolesTO();
						Roles role = iterator.next();
						rolesTO.setId(role.getId());
						rolesTO.setName(role.getName());
						rolesList.add(rolesTO);
				}
		}
		log.info("End of populateRoleBotoTO of PrivilegeHelper");
		return rolesList;
	}
	
	/**
	 * Display module and menus in UI
	 * Converts all module and menu BO to TO
	 */
	
	public List<ModuleTO> populateModuleBotoTO(List<Modules> moduleList)throws Exception
	{
		log.info("Inside of populateModuleBotoTO of PrivilegeHelper");
		List<ModuleTO> allModuleList=new ArrayList<ModuleTO>();
		if(moduleList!=null && !moduleList.isEmpty()){
			Iterator<Modules> it=moduleList.iterator();
				while (it.hasNext()) {
						Modules modules = it.next();
						ModuleTO moduleTO=new ModuleTO();
						moduleTO.setId(modules.getId());
						moduleTO.setName(modules.getDisplayName()!=null ? modules.getDisplayName():null);
						moduleTO.setChooseTemp(false);
						
						Set<Menus> menuSet=modules.getMenuses();
						
						List<Menus> sortedList=new ArrayList<Menus>();
						
						//Sort the menus based on the menu id
						sortedList.addAll(menuSet);
						Collections.sort(sortedList);
						
						List<MenusTO>menuTOList= new ArrayList<MenusTO>();
						Iterator<Menus> it1=sortedList.iterator();
						while (it1.hasNext()) {
								Menus menus = it1.next();
								if(menus.getIsActive()==true){
									
								MenusTO menusTO=new MenusTO();
								menusTO.setId(menus.getId());
								menusTO.setChooseTemp(false);
								menusTO.setName(menus.getDisplayName()!=null ? menus.getDisplayName():null);
								menuTOList.add(menusTO);
							}
							moduleTO.setMenuCount(menuTOList.size());		
						}
						moduleTO.setMenusTO(menuTOList);
						allModuleList.add(moduleTO);
				}
		}
		log.info("End of populateModuleBotoTO of PrivilegeHelper");
		return allModuleList;
	}
	/**
	 * 
	 * @param roleModuleMenuList
	 * @param Used while edit button is clicked
	 * @returns Mules TO
	 */
	public List<ModuleTO> populateModuleBotoTOWhileEdit(List<AccessPrivileges> roleModuleMenuList,List<Modules> moduleList)throws Exception
	{
		log.info("Inside of populateModuleBotoTOWhileEdit of PrivilegeHelper");
		Set<Integer> moduleSet = new HashSet<Integer>();
		Set<Integer> menuBoSet = new HashSet<Integer>();
		
		Iterator<AccessPrivileges> itr = roleModuleMenuList.iterator();
		AccessPrivileges accessPrivileges;
		while(itr.hasNext()) {
			accessPrivileges = itr.next();
			/**
			 * Setting all the moduleId and menuId
			 */
			if(accessPrivileges.getIsActive()){
				moduleSet.add(accessPrivileges.getModules().getId());
				menuBoSet.add(accessPrivileges.getMenus().getId());
			}
		}
		
		List<ModuleTO> allModuleList=new ArrayList<ModuleTO>();
		if(moduleList!=null && !moduleList.isEmpty()){
			Iterator<Modules> it=moduleList.iterator();
			while (it.hasNext()) {
					Modules modules = it.next();
					ModuleTO moduleTO=new ModuleTO();
					moduleTO.setId(modules.getId());
					moduleTO.setName(modules.getDisplayName()!=null ? modules.getDisplayName():null);
					
					Set<Menus> menuSet=modules.getMenuses();
					
					List<Menus> sortedMenuList=new ArrayList<Menus>();
					// below both for a module.
					int menuSelectedCount = 0;
					int activeMenuCount = 0;
					//Sort the menus based on the menu id
					sortedMenuList.addAll(menuSet);
					Collections.sort(sortedMenuList);
					
					
					List<MenusTO>menuTOList= new ArrayList<MenusTO>();
					Iterator<Menus> it1=sortedMenuList.iterator();
				while (it1.hasNext()) {
					Menus menus = it1.next();
					if(menus.getIsActive()==true){
						activeMenuCount++;	
						MenusTO menusTO=new MenusTO();
						menusTO.setId(menus.getId());
					//Checks whether the menuId is present or not
					if(menuBoSet.contains(menus.getId())){
							menusTO.setChooseTemp(true);
							menuSelectedCount++;
					}	
					else {
						menusTO.setChooseTemp(false);
					}	
						menusTO.setName(menus.getDisplayName()!=null ? menus.getDisplayName():null);
						menuTOList.add(menusTO);
					}
					moduleTO.setMenuCount(menuTOList.size());		
				}
				if(menuSelectedCount == activeMenuCount && menuSelectedCount != 0) {
					moduleTO.setChooseTemp(true);
				} else {
					moduleTO.setChooseTemp(false);
				}
				
				moduleTO.setMenusTO(menuTOList);
				allModuleList.add(moduleTO);
			}
		}
		log.info("End of populateModuleBotoTOWhileEdit of PrivilegeHelper");
		return allModuleList;
	}
	
	/**
	 * Used in Adding a privilege
	 * Converts the List of TO objects to BO
	 */
	
	public List<AccessPrivileges> populateTotoBO(List<ModuleTO> moduleList, int roleId, String userId)throws Exception
	{
		log.info("Inside of populateTotoBO of PrivilegeHelper");
		List<AccessPrivileges> accessPrivList = new ArrayList<AccessPrivileges>();
		if(moduleList!=null && !moduleList.isEmpty()){
			AccessPrivileges privileges;;
			Roles roles;
			Menus menus;
			Modules modules;
			ModuleTO moduleTO;
			MenusTO menusTO;
			Iterator<ModuleTO> iterator=moduleList.iterator();
			while (iterator.hasNext()) {
				moduleTO = iterator.next();
					List<MenusTO> menuList=moduleTO.getMenusTO();
					if(menuList!=null && !menuList.isEmpty()){
						Iterator<MenusTO> it=menuList.iterator();
						while (it.hasNext()) {
							menusTO =  it.next();
							if(menusTO.isChoosed()==true){
								privileges = new AccessPrivileges();
								roles = new Roles();
								menus = new Menus();
								modules = new Modules();
								
								roles.setId(roleId);
								menus.setId(menusTO.getId());
								modules.setId(moduleTO.getId());
								
								privileges.setRoles(roles);
								privileges.setModules(modules);
								privileges.setMenus(menus);
								
								privileges.setIsActive(true);
								privileges.setCreatedDate(new Date());
								privileges.setLastModifiedDate(new Date());
								privileges.setCreatedBy(userId);
								privileges.setModifiedBy(userId);
								privileges.setAllowAccess(true);
								accessPrivList.add(privileges);
							}
						}
					}
			}
		}
		log.info("End of populateTotoBO of PrivilegeHelper");
		return accessPrivList;
	}
	
	/**
	 * Display all roles
	 * Converts BO to TO
	 * 
	 */
	
	public List<AssignPrivilegeTO> populateAssignPrivilegeBotoTO(List<Object[]> roleList)throws Exception{
		log.info("Inside of populateAssignPrivilegeBotoTO of PrivilegeHelper");
		List<AssignPrivilegeTO> privilegeList=new ArrayList<AssignPrivilegeTO>();
		AssignPrivilegeTO assignPrivilegeTO;
		if(roleList!=null && !roleList.isEmpty()){
			Iterator<Object[]> iterator=roleList.iterator();
			while (iterator.hasNext()) {
				Object[] row = iterator.next();
				assignPrivilegeTO=new AssignPrivilegeTO();
				assignPrivilegeTO.setRoleId((Integer)row[0]);
				assignPrivilegeTO.setRoleName((String)row[1]);
				privilegeList.add(assignPrivilegeTO);
			}
			if(privilegeList!=null && !privilegeList.isEmpty()){
				return privilegeList;
			}
		}
		log.info("End of populateAssignPrivilegeBotoTO of Helper");
		return new ArrayList<AssignPrivilegeTO>();
	}
	
	/**
	 * Used in click of view button
	 * Creates a map and set the menus based on the modulename
	 */
	
	public Map<String, Set<String>> populateModuleMenuBotoTO(List<Object[]> detailsList)throws Exception{
		log.info("Inside of populateModuleMenuBotoTO of Helper");
			
		Map<String, Set<String>> moduleMenuMap=new LinkedHashMap<String, Set<String>>();
		Set<String> menus;
		
		if(detailsList!= null && !detailsList.isEmpty()){
			Iterator<Object[]> iterator=detailsList.iterator();
			while (iterator.hasNext()) {
				Object[] row = iterator.next();
				
				if(moduleMenuMap.containsKey(row[0])) {
						menus = moduleMenuMap.get((String)row[0]);
						menus.add((String)row[1]);
						moduleMenuMap.put((String) row[0],menus);
				} else {
					 menus = new LinkedHashSet<String>();
					 menus.add((String)row[1]);
					 moduleMenuMap.put((String) row[0],menus);
				}
			}
		}
		
		log.info("End of populateModuleMenuBotoTO of PrivilegeHelper");
		return moduleMenuMap;
	}
	/**
	 * 
	 * @param assignPrivilegeTOList
	 * @param roleId
	 * @param moduleId
	 * @param menuId
	 * Used while update button is clicked.
	 * Checked for the existing record
	 * @return
	 */
	public String[] getAccessPriviligeID(List<AssignPrivilegeTO>assignPrivilegeTOList , int roleId,int moduleId,int menuId)throws Exception {
		log.info("Entering into getAccessPriviligeID of PrivilegeHelper");
		String[] array = {"0","0"};
		Iterator<AssignPrivilegeTO> itr = assignPrivilegeTOList.iterator();
		AssignPrivilegeTO assignPrivilegeTO;
		while(itr.hasNext()) {
			assignPrivilegeTO = itr.next();
			if(assignPrivilegeTO.getRoleId() == roleId && assignPrivilegeTO.getMenuId() == menuId && assignPrivilegeTO.getModuleId() == moduleId) {
					array[0] = String.valueOf(assignPrivilegeTO.getId());
					array[1] = String.valueOf(assignPrivilegeTO.isActive());
					break;
			}
		}
		log.info("End of getAccessPriviligeID of PrivilegeHelper");
		return array;
	}

	/**
	 * Used while update button is clicked
	 */
	
	public List<AccessPrivileges> populateModuleTOtoAccessPrivilegeBO(AssignPrivilegeForm assignPrivilegeForm)throws Exception{
		log.info("Inside of populateModuleTOtoAccessPrivilegeBO of PrivilegeHelper");
		
		List<AccessPrivileges> accessPrivList = new ArrayList<AccessPrivileges>();
		
		int roleId=Integer.parseInt(assignPrivilegeForm.getRoleId());
		
		List<ModuleTO> moduleMenuList=assignPrivilegeForm.getModuleList();
		
		List<AssignPrivilegeTO>assignPrivilegeTOList = assignPrivilegeForm.getAssignPrivilegeTOList();
		
		if(moduleMenuList!=null && !moduleMenuList.isEmpty()){
			AccessPrivileges privileges;;
			Roles roles;
			Menus menus;
			Modules modules;
			ModuleTO moduleTO;
			MenusTO menusTO;
			int oldRoleId = Integer.parseInt(assignPrivilegeForm.getOldRoleId());
			Iterator<ModuleTO> iterator=moduleMenuList.iterator();
			
			while (iterator.hasNext()) {
				moduleTO = iterator.next();
					List<MenusTO> menuList=moduleTO.getMenusTO();
					if(menuList!=null && !menuList.isEmpty()){
						Iterator<MenusTO> it=menuList.iterator();
						while (it.hasNext()) {
							menusTO =  it.next();
							String[] array;
									if(menusTO.isChoosed()==true){
											
											array = getAccessPriviligeID(assignPrivilegeTOList,roleId,moduleTO.getId(),menusTO.getId());
											if((array[0].equals("0")) ||(!array[0].equals("0") && array[1].equals("false"))) {
												privileges = new AccessPrivileges();
												roles = new Roles();
												menus = new Menus();
												modules = new Modules();
												
												if(!array[0].equals("0") && array[1].equals("false")){
													privileges.setId(Integer.parseInt(array[0]));
												} 
												
												roles.setId(roleId);
												menus.setId(menusTO.getId());
												modules.setId(moduleTO.getId());
												
												privileges.setRoles(roles);
												privileges.setModules(modules);
												privileges.setMenus(menus);
												
												privileges.setIsActive(true);
												privileges.setCreatedBy(assignPrivilegeForm.getUserId());
												privileges.setCreatedDate(new Date());
												privileges.setModifiedBy(assignPrivilegeForm.getUserId());
												privileges.setLastModifiedDate(new Date());
												privileges.setAllowAccess(true);
												accessPrivList.add(privileges);
											}
											
										}
										else if(menusTO.isChoosed() == false){
											if(oldRoleId == roleId){
												 array = getAccessPriviligeID(assignPrivilegeTOList,roleId,moduleTO.getId(),menusTO.getId());
											}else {
												 array = getAccessPriviligeID(assignPrivilegeTOList,oldRoleId,moduleTO.getId(),menusTO.getId());
											}
											if(!array[0].equals("0")) {
												privileges = new AccessPrivileges();
												roles = new Roles();
												menus = new Menus();
												modules = new Modules();
												if(oldRoleId == roleId)
													roles.setId(roleId);
												else 
													roles.setId(oldRoleId);
												menus.setId(menusTO.getId());
												modules.setId(moduleTO.getId());
												privileges.setId(Integer.parseInt(array[0]));
												privileges.setRoles(roles);
												privileges.setModules(modules);
												privileges.setMenus(menus);
												
												privileges.setIsActive(false);
												privileges.setLastModifiedDate(new Date());
												privileges.setModifiedBy(assignPrivilegeForm.getUserId());
												privileges.setAllowAccess(false);
												accessPrivList.add(privileges);
											}
										}
								}
						}
				}
			}
		log.info("End of populateModuleTOtoAccessPrivilegeBO of PrivilegeHelper");
			return accessPrivList;		
		}
	
	/**
	 * Used while edit button is clicked
	 * Setting all privilegeId, roleId, moduleId and menuId to AssignPrivilegeTo.
	 * Returns a list of AssignPrivilege TO
	 */
	
	public List<AssignPrivilegeTO> copyAccessPrivilegeBOtoTO(List<AccessPrivileges> roleModuleMenuList)throws Exception{
		log.info("Entering into copyAccessPrivilegeBOtoTO of PrivilegeHelper");
		List<AssignPrivilegeTO> assignPrivilegeTOList = new ArrayList<AssignPrivilegeTO>();
		AssignPrivilegeTO assignPrivilegeTO;
		AccessPrivileges accessPrivileges; 
		if(roleModuleMenuList!=null && !roleModuleMenuList.isEmpty()){
			Iterator<AccessPrivileges> iterator=roleModuleMenuList.iterator();
			while (iterator.hasNext()) {
				accessPrivileges = iterator.next();
				assignPrivilegeTO = new AssignPrivilegeTO();
				assignPrivilegeTO.setId(accessPrivileges.getId());
				assignPrivilegeTO.setRoleId(accessPrivileges.getRoles().getId());
				assignPrivilegeTO.setModuleId(accessPrivileges.getModules().getId());
				assignPrivilegeTO.setMenuId(accessPrivileges.getMenus().getId());
				assignPrivilegeTO.setActive(accessPrivileges.getIsActive());
				assignPrivilegeTOList.add(assignPrivilegeTO);
			}
		}
		log.info("Leaving from copyAccessPrivilegeBOtoTO of PrivilegeHelper");
		return assignPrivilegeTOList;
	}
}
