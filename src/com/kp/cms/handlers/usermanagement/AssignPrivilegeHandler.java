package com.kp.cms.handlers.usermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.forms.usermanagement.AssignPrivilegeForm;
import com.kp.cms.helpers.usermanagement.AssignPrivilegeHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.usermanagement.IAssignPrivilegeTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.AssignPrivilegeTransactionImpl;



public class AssignPrivilegeHandler {
	
	private static final Log log = LogFactory.getLog(AssignPrivilegeHandler.class);
	
	public static volatile AssignPrivilegeHandler assignPrivilegeHandler = null;
	
	//Creates a reference of privilege transaction interface and assigns the implemented class object to that	
	
	/**
	 * 
	 * @returns single object when this class is access
	 */
	public static AssignPrivilegeHandler getInstance() {
		if (assignPrivilegeHandler == null) {
			assignPrivilegeHandler = new AssignPrivilegeHandler();
			return assignPrivilegeHandler;
		}
		return assignPrivilegeHandler;
	}
	/**
	 * 
	 * @returns all the roles from role master which are in active mode
	 * @throws Exception
	 */
	
	public List<RolesTO> getRoles()throws Exception
	{
		log.info("Inside of getRoles of PrivilegeHandler");
		ISingleFieldMasterTransaction transaction=new SingleFieldMasterTransactionImpl();
		List<Roles> roleList=transaction.getRolesFields();
		log.info("End of getRoles of PrivilegeHandler");
		return AssignPrivilegeHelper.getInstance().populateRoleBotoTO(roleList);
	}

	
	/**
	 * Get all the module and menus based on the modules which all are in active state
	 */
	public List<ModuleTO> getModuleDetails()throws Exception
	{
		log.info("Inside of getModuleDetails of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		List<Modules> moduleList = transaction.getModuleDetails();
		log.info("End of getModuleDetails of PrivilegeHandler");
		return AssignPrivilegeHelper.getInstance().populateModuleBotoTO(moduleList);
	}
	
	/**
	 * Used while edit
	 */
	
	public List<ModuleTO> getModuleDetailsWhileEdit(List<AccessPrivileges> roleModuleMenuList)throws Exception
	{
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		log.info("Inside of getModuleDetails of Handler");
			List<Modules> moduleList = transaction.getModuleDetails();
			log.info("End of getModuleDetails of Handler");
			return AssignPrivilegeHelper.getInstance().populateModuleBotoTOWhileEdit(roleModuleMenuList,moduleList);
	}
	
	
	/**
	 * Used in adding privilege 
	 */
	
	public boolean addPrivilege(List<ModuleTO> moduleList, int roleId, String userId)throws Exception{
		log.info("Inside of addPrivilege of Handler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		List<AccessPrivileges> privileges=AssignPrivilegeHelper.getInstance().populateTotoBO(moduleList , roleId, userId);
		log.info("End of addPrivilege of Handler");
		return transaction.addPrivilege(privileges);
	}
	/**
	 * 
	 * @returns all the roles from access privilege table
	 * @throws Exception
	 */
	public List<AssignPrivilegeTO> getRolesFromAcccessPrivilege()throws Exception{
		log.info("Inside of getRolesFromAcccessPrivilege of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		List<Object[]> roleList=transaction.getRolesFromAcccessPrivilege();
		log.info("End of getRolesFromAcccessPrivilege of PrivilegeHandler");
		return AssignPrivilegeHelper.getInstance().populateAssignPrivilegeBotoTO(roleList);
	}
	
	/**
	 * Used in deleting an assigned privilege for a particular role (Make that to inactive)
	 */
	public boolean deletePrivilege(int roleId, String userId)throws Exception{
		log.info("Inside of deletePrivilege of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		log.info("End of deletePrivilege of PrivilegeHandler");
		return transaction.deletePrivilege(roleId, userId);
	}
	/**
	 * 
	 * @param roleId
	 * @returns all the active module and menus for the same while clicking the view button
	 * @throws Exception
	 */
	public Map<String, Set<String>> getModuleMenuOnRole(int roleId)throws Exception{
		log.info("Inside of getModuleMenuOnRole of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		List<Object[]> detailsList=transaction.getModuleMenuOnRole(roleId);
		log.info("End of getModuleMenuOnRole of PrivilegeHandler");
		return AssignPrivilegeHelper.getInstance().populateModuleMenuBotoTO(detailsList);
	}
	
	/**
	 * Checking duplicate entry for the assigned role
	 */
	
	public List<AccessPrivileges> getPrivilegebyRole(int roleId)throws Exception{
		log.info("Inside of getPrivilegebyRole of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
			if(transaction!=null){
				return transaction.getPrivilegebyRole(roleId);			
			}
		log.info("End of getPrivilegebyRole of PrivilegeHandler");
		return new ArrayList<AccessPrivileges>();
	}
	
	/**
	 * Used in reactivation based on the role
	 */
	
	public boolean reActivatePrivilege(int roleId, String userId)throws Exception{
		log.info("Inside of reActivatePrivilege of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
			if(transaction!=null){
				return transaction.reActivatePrivilege(roleId, userId);
			}
		log.info("End of reActivatePrivilege of PrivilegeHandler");
		return false;
	}
	
	/**
	 * Used in Edit
	 */
	
	public void getRoleModuleMenuonroleId(AssignPrivilegeForm assignPrivilegeForm)throws Exception{
		log.info("Inside of getRoleModuleMenuonroleId of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		/**
		 * Gets modules and menus based on the roleId while edit is clicked
		 */
		List<AccessPrivileges> roleModuleMenuList=transaction.getRoleModuleMenuonroleId(Integer.parseInt(assignPrivilegeForm.getRoleId()));		
		//Pass the BO list to handler and create the Assignprivilege To list		
		List<AssignPrivilegeTO> assignPrivilegeTOList=AssignPrivilegeHelper.getInstance().copyAccessPrivilegeBOtoTO(roleModuleMenuList);
		if(!assignPrivilegeTOList.isEmpty()){
			assignPrivilegeForm.setAssignPrivilegeTOList(assignPrivilegeTOList);
		}		
		//Sets the roleId to form while editing
		assignPrivilegeForm.setOldRoleId(assignPrivilegeForm.getRoleId());		
		//Get all the roles which are in active state
		List<RolesTO> roleList=AssignPrivilegeHandler.getInstance().getRoles();
		if(!roleList.isEmpty()){
			assignPrivilegeForm.setRoleList(roleList);
		}		
		List<ModuleTO> moduleList = getModuleDetailsWhileEdit(roleModuleMenuList);
		if(!moduleList.isEmpty()){
			assignPrivilegeForm.setModuleList(moduleList);
		}
		/**
		 * Gets all the records of AccessPrivilege for the roles and set to form.
		 */
		List<AssignPrivilegeTO> privilegeList=AssignPrivilegeHandler.getInstance().getRolesFromAcccessPrivilege();
		if(privilegeList!=null && !privilegeList.isEmpty()){
			assignPrivilegeForm.setPrivilegeList(privilegeList);
		}
		log.info("End of getRoleModuleMenuonroleId of PrivilegeHandler");
	}
	
	/**
	 * Used in update
	 */
	
	public boolean updatePrivilege(AssignPrivilegeForm assignPrivilegeForm) throws Exception{
		log.info("Inside of updatePrivilege of PrivilegeHandler");
		IAssignPrivilegeTransaction transaction=new AssignPrivilegeTransactionImpl();
		List<AccessPrivileges>updateList=AssignPrivilegeHelper.getInstance().populateModuleTOtoAccessPrivilegeBO(assignPrivilegeForm);
			if(transaction!=null){
			return transaction.updatePrivilege(updateList);
			}
		log.info("End of updatePrivilege of PrivilegeHandler");
		return false;
	}	
}
