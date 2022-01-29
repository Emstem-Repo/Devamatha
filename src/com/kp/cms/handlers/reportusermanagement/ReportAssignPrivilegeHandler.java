package com.kp.cms.handlers.reportusermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.ReportAccessPrivileges;
import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.forms.reportusermanagement.ReportAssignPrivilegeForm;
import com.kp.cms.forms.usermanagement.AssignPrivilegeForm;
import com.kp.cms.helpers.reportusermanagement.ReportAssignPrivilegeHelper;
import com.kp.cms.helpers.usermanagement.AssignPrivilegeHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.reportusermanagement.IReportAssignPrivilegeTransaction;
import com.kp.cms.transactions.usermanagement.IAssignPrivilegeTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.reportusermanagement.ReportAssignPrivilegeTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.AssignPrivilegeTransactionImpl;



public class ReportAssignPrivilegeHandler {
	
	private static final Log log = LogFactory.getLog(ReportAssignPrivilegeHandler.class);
	
	public static volatile ReportAssignPrivilegeHandler assignPrivilegeHandler = null;
	
	//Creates a reference of privilege transaction interface and assigns the implemented class object to that	
	
	/**
	 * 
	 * @returns single object when this class is access
	 */
	public static ReportAssignPrivilegeHandler getInstance() {
		if (assignPrivilegeHandler == null) {
			assignPrivilegeHandler = new ReportAssignPrivilegeHandler();
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
		return ReportAssignPrivilegeHelper.getInstance().populateRoleBotoTO(roleList);
	}

	
	/**
	 * Get all the module and menus based on the modules which all are in active state
	 */
	public List<ModuleTO> getModuleDetails()throws Exception
	{
		log.info("Inside of getModuleDetails of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		List<ReportModules> moduleList = transaction.getModuleDetails();
		log.info("End of getModuleDetails of PrivilegeHandler");
		return ReportAssignPrivilegeHelper.getInstance().populateModuleBotoTO(moduleList);
	}
	
	/**
	 * Used while edit
	 */
	
	public List<ModuleTO> getModuleDetailsWhileEdit(List<ReportAccessPrivileges> roleModuleMenuList)throws Exception
	{
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		log.info("Inside of getModuleDetails of Handler");
			List<ReportModules> moduleList = transaction.getModuleDetails();
			log.info("End of getModuleDetails of Handler");
			return ReportAssignPrivilegeHelper.getInstance().populateModuleBotoTOWhileEdit(roleModuleMenuList,moduleList);
	}
	
	
	/**
	 * Used in adding privilege 
	 */
	
	public boolean addPrivilege(List<ModuleTO> moduleList, int roleId, String userId)throws Exception{
		log.info("Inside of addPrivilege of Handler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		List<ReportAccessPrivileges> privileges=ReportAssignPrivilegeHelper.getInstance().populateTotoBO(moduleList , roleId, userId);
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
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		List<Object[]> roleList=transaction.getRolesFromAcccessPrivilege();
		log.info("End of getRolesFromAcccessPrivilege of PrivilegeHandler");
		return ReportAssignPrivilegeHelper.getInstance().populateAssignPrivilegeBotoTO(roleList);
	}
	
	/**
	 * Used in deleting an assigned privilege for a particular role (Make that to inactive)
	 */
	public boolean deletePrivilege(int roleId, String userId)throws Exception{
		log.info("Inside of deletePrivilege of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		
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
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		List<Object[]> detailsList=transaction.getModuleMenuOnRole(roleId);
		log.info("End of getModuleMenuOnRole of PrivilegeHandler");
		return ReportAssignPrivilegeHelper.getInstance().populateModuleMenuBotoTO(detailsList);
	}
	
	/**
	 * Checking duplicate entry for the assigned role
	 */
	
	public List<ReportAccessPrivileges> getPrivilegebyRole(int roleId)throws Exception{
		log.info("Inside of getPrivilegebyRole of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
			if(transaction!=null){
				return transaction.getPrivilegebyRole(roleId);			
			}
		log.info("End of getPrivilegebyRole of PrivilegeHandler");
		return new ArrayList<ReportAccessPrivileges>();
	}
	
	/**
	 * Used in reactivation based on the role
	 */
	
	public boolean reActivatePrivilege(int roleId, String userId)throws Exception{
		log.info("Inside of reActivatePrivilege of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
			if(transaction!=null){
				return transaction.reActivatePrivilege(roleId, userId);
			}
		log.info("End of reActivatePrivilege of PrivilegeHandler");
		return false;
	}
	
	/**
	 * Used in Edit
	 */
	
	public void getRoleModuleMenuonroleId(ReportAssignPrivilegeForm assignPrivilegeForm)throws Exception{
		log.info("Inside of getRoleModuleMenuonroleId of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		/**
		 * Gets modules and menus based on the roleId while edit is clicked
		 */
		List<ReportAccessPrivileges> roleModuleMenuList=transaction.getRoleModuleMenuonroleId(Integer.parseInt(assignPrivilegeForm.getRoleId()));		
		//Pass the BO list to handler and create the Assignprivilege To list		
		List<AssignPrivilegeTO> assignPrivilegeTOList=ReportAssignPrivilegeHelper.getInstance().copyAccessPrivilegeBOtoTO(roleModuleMenuList);
		if(!assignPrivilegeTOList.isEmpty()){
			assignPrivilegeForm.setAssignPrivilegeTOList(assignPrivilegeTOList);
		}		
		//Sets the roleId to form while editing
		assignPrivilegeForm.setOldRoleId(assignPrivilegeForm.getRoleId());		
		//Get all the roles which are in active state
		List<RolesTO> roleList=ReportAssignPrivilegeHandler.getInstance().getRoles();
		if(roleList!=null){
			assignPrivilegeForm.setRoleList(roleList);
		}		
		List<ModuleTO> moduleList = getModuleDetailsWhileEdit(roleModuleMenuList);
		if(moduleList!=null){
			assignPrivilegeForm.setModuleList(moduleList);
		}
		/**
		 * Gets all the records of AccessPrivilege for the roles and set to form.
		 */
		List<AssignPrivilegeTO> privilegeList=ReportAssignPrivilegeHandler.getInstance().getRolesFromAcccessPrivilege();
		if(privilegeList!=null && !privilegeList.isEmpty()){
			assignPrivilegeForm.setPrivilegeList(privilegeList);
		}
		log.info("End of getRoleModuleMenuonroleId of PrivilegeHandler");
	}
	
	/**
	 * Used in update
	 */
	
	public boolean updatePrivilege(ReportAssignPrivilegeForm assignPrivilegeForm) throws Exception{
		log.info("Inside of updatePrivilege of PrivilegeHandler");
		IReportAssignPrivilegeTransaction transaction=new ReportAssignPrivilegeTransactionImpl();
		List<ReportAccessPrivileges>updateList=ReportAssignPrivilegeHelper.getInstance().populateModuleTOtoAccessPrivilegeBO(assignPrivilegeForm);
			if(transaction!=null){
			return transaction.updatePrivilege(updateList);
			}
		log.info("End of updatePrivilege of PrivilegeHandler");
		return false;
	}	
}
