package com.kp.cms.actions.usermanagement;

import java.net.Inet4Address;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.AssignPrivilegeForm;
import com.kp.cms.handlers.usermanagement.AssignPrivilegeHandler;
import com.kp.cms.helpers.usermanagement.AssignPrivilegeHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.to.usermanagement.RolesTO;



@SuppressWarnings("deprecation")
public class AssignPrivilegeAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(AssignPrivilegeAction.class);
	private static final String PRIVILEGE_OPERATION = "privilegeoperation";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes privilege page 
	 * @throws Exception
	 */

	public ActionForward initPrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into privilege --- initPrivilege");
		AssignPrivilegeForm assignPrivilegeForm = (AssignPrivilegeForm) form;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		//IPADDRESS CHECK
		if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
			
			//return mapping.findForward("logout");
		}
		
		try {			
			assignListToForm(assignPrivilegeForm);
		} catch (Exception e) {
			log.error("Error occured in initPrivilege of Privilege Action",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		assignPrivilegeForm.clear();
		log.info("Leaving from privilege --- initPrivilege");
		return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Adds of privilege entry for a particular role
	 * @throws Exception
	 */
	public ActionForward addPrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addPrivilege --- Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignPrivilegeForm.validate(mapping, request);
		try {
			if(errors.isEmpty())
			{
			setUserId(request, assignPrivilegeForm);
			String userId=assignPrivilegeForm.getUserId();
			//Get the roleId from UI
			int roleId = Integer.parseInt(assignPrivilegeForm.getRoleId());
			/**
			 * Checks for the duplicate (If privilege for the particular role is already exists)
			 */
			List<AccessPrivileges> isPrevilegeExist=AssignPrivilegeHandler.getInstance().getPrivilegebyRole(roleId);
			if(isPrevilegeExist!=null && !isPrevilegeExist.isEmpty()){
				ActionError error = new ActionError(CMSConstants.USERMANAGEMENT_PRIVILEGE_EXIST);
				ActionError error1 = new ActionError(CMSConstants.USERMANAGEMENT_PRIVILEGE_REACTIVATE);
				Iterator<AccessPrivileges> iterator=isPrevilegeExist.iterator();
				while (iterator.hasNext()) {
					AccessPrivileges accessPrivileges = iterator.next();
					//If privilege is exists and is in active mode. Then  show the error message
					if(accessPrivileges.getIsActive()){
						errors.add("error",error);
						saveErrors(request, errors);
						assignPrivilegeForm.clear();
						assignListToForm(assignPrivilegeForm);
						return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
					}
					//If privilege is already exists and in inactive mode. Then show error message for reactivating the same.
					if(!accessPrivileges.getIsActive()){
						errors.add("error",error1);
						saveErrors(request, errors);
						assignListToForm(assignPrivilegeForm);
						return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
					}			
				}
			}
			//If the both of the above condition failed then add the privilege for the selected role.
			List<ModuleTO>moduleList=assignPrivilegeForm.getModuleList()!=null ? assignPrivilegeForm.getModuleList():null;
			
			if(AssignPrivilegeHelper.getInstance().populateTotoBO(moduleList , roleId, userId).size()== 0){
				errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_SELECT_ANY));
			}else{
				boolean isAdded;
				isAdded=AssignPrivilegeHandler.getInstance().addPrivilege(moduleList, roleId, userId);
				//If added successful then show the success message 
				if (isAdded)
				{
					messages.add("messages", new ActionMessage(CMSConstants.USERMANAGEMENT_ADD_SUCCESS));
					saveMessages(request, messages);
					assignPrivilegeForm.clear();
					assignListToForm(assignPrivilegeForm);
					return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
				}
				//If add operation is failed then show the error message
				if(!isAdded){
					errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_ADD_FAILED));
					saveErrors(request, errors);
					assignPrivilegeForm.clear();
					assignListToForm(assignPrivilegeForm);
					return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
				}
			}	
			}
		} catch (Exception e) {
			log.error("Error occured in addPrivilege of Privilege Action",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		saveErrors(request, errors);
		saveMessages(request, messages);
		log.info("Leaving from addPrivilege --- Action");
		assignListToForm(assignPrivilegeForm);
		return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
	}
		
	/*
	 * Gets all the roles and displayed in the dropdown of UI
	 * Gets all the modules available
	 * Gets all the available menus based on the module 
	 * Gets all the records available for access privilege which are in active mode
	 * @throws Exception
	 */
	public void assignListToForm(AssignPrivilegeForm assignPrivilegeForm) throws Exception {
		log.info("Inside of assignListToForm --- Action");
		//Gets all the roles from DB which are in active mode
		List<RolesTO> roleList=AssignPrivilegeHandler.getInstance().getRoles();
		if(roleList!=null){
			assignPrivilegeForm.setRoleList(roleList);
		}
		//Gets all the modules along with menus based on the module from DB which all are in active mode 
		List<ModuleTO> moduleList = AssignPrivilegeHandler.getInstance().getModuleDetails();
		if(moduleList!=null && !moduleList.isEmpty()){
			assignPrivilegeForm.setModuleList(moduleList);
		}
		//Get the roles available in access privilege table in active mode for which privilege is assigned 
		List<AssignPrivilegeTO> privilegeList=AssignPrivilegeHandler.getInstance().getRolesFromAcccessPrivilege();
		if(privilegeList!=null && !privilegeList.isEmpty()){
			assignPrivilegeForm.setPrivilegeList(privilegeList);
		}
		log.info("End of assignListToForm --- Action");
	}
	/**
	 * Deletes one assigned privilege i.e. makes that particular records to inactive mode
	 */
	public ActionForward deletePrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of deletePrivilege --- Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignPrivilegeForm.validate(mapping, request);
		try {
			setUserId(request, assignPrivilegeForm);
			
			boolean isDeleted;
			//Delete based on the roleId captured from UI
			isDeleted = AssignPrivilegeHandler.getInstance().deletePrivilege(
					Integer.parseInt(assignPrivilegeForm.getRoleId()), assignPrivilegeForm.getUserId());
			//If delete operation is success then add the success message else add the error message
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.USERMANAGEMENT_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(assignPrivilegeForm);
				assignPrivilegeForm.clear();
				log.info("Leaving from deletePrivilege --- Action");
				return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
			}
			else{
				errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(assignPrivilegeForm);
				assignPrivilegeForm.clear();
				log.info("Leaving from deletePrivilege --- Action");
				return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
			}
		} catch (Exception e) {
			log.error("Error occured in deletePrivilege of Privilege Action",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
	}
		
	/**
	 * Displays all module and menu names when view is clicked in a new window
	 */
	
	public ActionForward getModuleMenuOnRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of getModuleMenuOnRole --- Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		try {
			int roleId = Integer.parseInt(assignPrivilegeForm.getRoleId());
			Map<String, Set<String>> moduleMenuMap = AssignPrivilegeHandler
					.getInstance().getModuleMenuOnRole(roleId);
			if (moduleMenuMap != null && !moduleMenuMap.isEmpty()) {
				assignPrivilegeForm.setModuleMenuMap(moduleMenuMap);
			}
		} catch (Exception e) {
			log.error("Error occured in getModuleMenuOnRole of Privilege Action",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from getModuleMenuOnRole --- Privilege Action");
		assignListToForm(assignPrivilegeForm);
		return mapping.findForward(CMSConstants.PRIVILEGE_VIEW);
	}
	
	/**
	 * Used in reactivation
	 * Reactivates the privilege for the role if it is in inactive state
	 */
	
	public ActionForward reActivatePrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of reActivatePrivilege --- Privilege Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty()){
			setUserId(request, assignPrivilegeForm);
			boolean reActivate;
			/**
			 * If reactivation is success then append the success mesage
			 * Else add the appropriate error message
			 */
			reActivate=AssignPrivilegeHandler.getInstance().reActivatePrivilege(Integer.parseInt(assignPrivilegeForm.getRoleId()), assignPrivilegeForm.getUserId());
			if(reActivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.USERMANAGEMENT_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				assignPrivilegeForm.clear();
				assignListToForm(assignPrivilegeForm);
				return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
			} else {
				errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignPrivilegeForm.clear();
				assignListToForm(assignPrivilegeForm);
			}
		}
		} catch (Exception e) {
				log.error("Error occured in privilege action of reActivatePrivilege",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		saveErrors(request, errors);
		assignListToForm(assignPrivilegeForm);
		log.info("End of reActivatePrivilege --- Privilege Action");
		return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
	}
	
	/**
	 * Used in Edit
	 * Populates fields in UI based on the role
	 */

	public ActionForward editPrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of editPrivilege --- Privilege Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		try {
			AssignPrivilegeHandler.getInstance().getRoleModuleMenuonroleId(assignPrivilegeForm);
			request.setAttribute(PRIVILEGE_OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
				log.error("Error occured in privilege action of editPrivilege",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("End of editPrivilege --- Privilege Action");
		return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
	}
	
	/**
	 * Used in update
	 * 
	 */

	public ActionForward updatePrivilege(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of updatePrivilege --- Privilege Action");
		AssignPrivilegeForm assignPrivilegeForm=(AssignPrivilegeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignPrivilegeForm.validate(mapping, request);
		try {
			if(errors.isEmpty())
			{
			setUserId(request, assignPrivilegeForm);
			int roleId=Integer.parseInt(assignPrivilegeForm.getRoleId());
			int oldRoleId=Integer.parseInt(assignPrivilegeForm.getOldRoleId());
			
			/**
			 * If user selects different role while update then check for that with old role
			 * If that exists and in inactive. so activate that 
			 */
			
			if(roleId != oldRoleId){
				ActionError actionError = new ActionError(CMSConstants.USERMANAGEMENT_PRIVILEGE_REACTIVATE);
			List<AccessPrivileges> isPrevilegeExist=AssignPrivilegeHandler.getInstance().getPrivilegebyRole(roleId);
			if(isPrevilegeExist!=null && !isPrevilegeExist.isEmpty()){
				Iterator<AccessPrivileges> iterator=isPrevilegeExist.iterator();
				while (iterator.hasNext()) {
					AccessPrivileges accessPrivileges = iterator.next();
					//If privilege is already exists and in inactive mode. Then show error message for reactivating the same.
					if(!accessPrivileges.getIsActive()){
						errors.add("error", actionError);
						saveErrors(request, errors);
						assignListToForm(assignPrivilegeForm);
						return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
					}		
				}
			}
			}
			//Request for update operation
			boolean isUpdated;
			isUpdated=AssignPrivilegeHandler.getInstance().updatePrivilege(assignPrivilegeForm);
			//If update is successful then append the success message
			if(isUpdated){
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.USERMANAGEMENT_UPDATE_SUCCESS));
				saveMessages(request, messages);
				assignPrivilegeForm.clear();
				assignListToForm(assignPrivilegeForm);
				return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
			}	
			//Else add the failure message
			else{
				errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_UPDATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(assignPrivilegeForm);
				assignPrivilegeForm.clear();
				return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
			}
			}			 
			}catch (Exception e) {
				log.error("Error occured while updating in privilege action",e);
				String msg = super.handleApplicationException(e);
				assignPrivilegeForm.setErrorMessage(msg);
				assignPrivilegeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		request.setAttribute(PRIVILEGE_OPERATION, CMSConstants.EDIT_OPERATION);
		assignListToForm(assignPrivilegeForm);
		assignPrivilegeForm.setRoleId(assignPrivilegeForm.getOldRoleId());
		AssignPrivilegeHandler.getInstance().getRoleModuleMenuonroleId(assignPrivilegeForm);
		assignPrivilegeForm.clear();
		saveErrors(request, errors);
		log.info("End of updatePrivilege --- Privilege Action");
		return mapping.findForward(CMSConstants.INIT_PRIVILEGE);
	
	}
}
