package com.kp.cms.actions.usermanagement;

import java.net.Inet4Address;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.usermanagement.MenuScreenMasterForm;
import com.kp.cms.handlers.usermanagement.MenusHandler;
import com.kp.cms.handlers.usermanagement.ModuleHandler;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;

@SuppressWarnings("deprecation")
public class MenuScreenMasterAction extends BaseDispatchAction {

	private static final Logger log = Logger.getLogger(MenuScreenMasterAction.class);

	/**
	 * This method is called when u click the link on left side.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward initMenuScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initMenuScreen method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		//IPADDRESS CHECK
		if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
			
			//return mapping.findForward("logout");
		}
		
		try {
			//load the details for module drop down and menus list.
			screenMasterForm.clearAll();
			loadDetails(screenMasterForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			screenMasterForm.setErrorMessage(msgKey);
			screenMasterForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of initMenuScreen method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN);
	}

	/**
	 * This method is to add menus to database through handler.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward addMenuScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of addMenuScreen method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = screenMasterForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				// errors are empty
				setUserId(request, screenMasterForm);
				int sequence = Integer.parseInt(screenMasterForm.getSequence());
				if(sequence == 0){
					errors.add(CMSConstants.MENU_SCREEN_MASTER_SEQUENCE,
							new ActionError(CMSConstants.MENU_SCREEN_MASTER_SEQUENCE));
						saveErrors(request,errors);
				}else {
					int moduleId = Integer.parseInt(screenMasterForm.getModule());
					Menus menus = MenusHandler.getInstance().menuNameExist(sequence, moduleId);
					//checking for Duplicate entry.
					if (menus != null && menus.getIsActive()) {
						errors.add(CMSConstants.MENU_SCREEN_MASTER_EXIST, new ActionError(
								CMSConstants.MENU_SCREEN_MASTER_EXIST));
						saveErrors(request, errors);
						//checking for reactivation.
					} else if (menus != null && !menus.getIsActive()) {
						errors.add(CMSConstants.MENU_SCREEN_MASTER_REACTIVATE, new ActionError(
								CMSConstants.MENU_SCREEN_MASTER_REACTIVATE));
						saveErrors(request, errors);
						//sending form to handler for saving to database.  
					} else {
						boolean isadded = MenusHandler.getInstance().addMenus(
								screenMasterForm);
						//successfully saved
						if (isadded) {
							ActionMessage message = new ActionMessage(
									CMSConstants.MENU_SCREEN_MASTER_ADDSUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
						}//failure happens
						if (!isadded) {
							ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_ADDFAILURE);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
						}
					}// end of else
			}
			}// end of if
			else {
				//for validation errors
				errors.add(messages);
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			log.info("Exception addMenuScreen");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		loadDetails(screenMasterForm);
		log.info("end of addMenuScreen method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN);
	}
	
	/**
	 * This method is called when u click on edit icon for one record.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */

	public ActionForward editMenus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("call of editMenus method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = screenMasterForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				// errors are empty
				MenusTO menusTO = MenusHandler.getInstance().editMenuDetails(screenMasterForm.getMenuId());
				//setting to screenMasterForm variables from menusTO variables.
				screenMasterForm.setModule(Integer.toString(menusTO.getModuleTO().getId()));
				screenMasterForm.setOldModuleId(menusTO.getModuleTO().getId());
				screenMasterForm.setScreenName(menusTO.getName());
				screenMasterForm.setPath(menusTO.getUrl());
				if(menusTO.isNewtab())
					screenMasterForm.setNewtab("true");
				else
					screenMasterForm.setNewtab("false");
				screenMasterForm.setSequence(Integer.toString(menusTO.getPosition()));
				HttpSession session=request.getSession(false);
				if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
				}else{
					session.setAttribute("Seq",menusTO.getPosition());
					request.setAttribute("menuOperation", "edit");
				}
			}else {
				// errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		loadDetails(screenMasterForm);
		log.info("end of editMenus method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN);
	}
	
	/**
	 * This method is called when u click on update button after editing for one record.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward updateMenus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of updateMenus method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = screenMasterForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request,screenMasterForm);
	    		HttpSession session=request.getSession(false);
	    		//getting sequence form session attribute.
	    		int seq = (Integer)session.getAttribute("Seq");
	    		int sequence = Integer.parseInt(screenMasterForm.getSequence());
	    		if(sequence == 0){
	    			errors.add(CMSConstants.MENU_SCREEN_MASTER_SEQUENCE,
							new ActionError(CMSConstants.MENU_SCREEN_MASTER_SEQUENCE));
						saveErrors(request,errors);
	    		}else{
					int moduleId = Integer.parseInt(screenMasterForm.getModule());
					String menuName = screenMasterForm.getScreenName();
					
					if(seq!=sequence){
					
					Menus menus = MenusHandler.getInstance().menuNameExist(sequence, moduleId);
					if (menus != null && menus.getIsActive() && !menus.getDisplayName().equals(menuName)) {
						errors.add(CMSConstants.MENU_SCREEN_MASTER_EXIST, new ActionError(
								CMSConstants.MENU_SCREEN_MASTER_EXIST));
						saveErrors(request, errors);
						request.setAttribute("menuOperation", "edit");
					}else if (menus != null && !menus.getIsActive() && menus.getDisplayName().equals(menuName)) {
						errors.add(CMSConstants.MENU_SCREEN_MASTER_REACTIVATE, new ActionError(
								CMSConstants.MENU_SCREEN_MASTER_REACTIVATE));
						saveErrors(request, errors);
						request.setAttribute("menuOperation", "edit");
					}else {
			    		boolean isUpdated =MenusHandler.getInstance().updateMenus(screenMasterForm);
			    		if(isUpdated){
			    			//if update is success.
			    			session.removeAttribute("Seq");
			    			ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_UPDATESUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
			    		}if(!isUpdated){
			    			//if update fails.
			    			ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_UPDATEFAILURE);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
			    		}
					}
		    		}else{
		    			boolean isUpdated =MenusHandler.getInstance().updateMenus(screenMasterForm);
			    		if(isUpdated){
			    			//if update success.
			    			session.removeAttribute("Seq");
			    			ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_UPDATESUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
			    		}if(!isUpdated){
			    			//if update fails.
			    			ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_UPDATEFAILURE);
							messages.add("messages", message);
							saveMessages(request, messages);
							screenMasterForm.clearAll();
							screenMasterForm.reset(mapping, request);
			    		}
		    		}
	    	}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception updateMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    
	    //load the details for module drop down and menus list.
	    loadDetails(screenMasterForm);			
		log.info("end of updateMenus method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN);
	}

	/**
	 * This method is called when u click delete icon in jsp for one record.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward deleteMenus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of deleteMenus method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = screenMasterForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request,screenMasterForm);
	    		boolean isDelete=MenusHandler.getInstance().deleteMenus(screenMasterForm.getMenuId(),screenMasterForm.getUserId());
				if(isDelete){
					//if deleted is success.
					ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_DELETESUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					screenMasterForm.clearAll();
					screenMasterForm.reset(mapping, request);
				}if(!isDelete){
					//if deleted is failure.
					ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_DELETEFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					screenMasterForm.clearAll();
					screenMasterForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception deleteMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //load the details for module drop down and menus list.
		loadDetails(screenMasterForm);
			
		log.info("end of deleteMenus method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN);
	}
	
	/**
	 * This method is called when u click yes for reactivation in jsp.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward reActivateMenus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of reActivateMenus method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = screenMasterForm.validate(mapping, request);
	    try{
	    	setUserId(request, screenMasterForm);
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		int seq = 0;
	    		int moduleId = 0;
				if(screenMasterForm.getSequence() != null)
	    		seq = Integer.parseInt(screenMasterForm.getSequence());
				if(screenMasterForm.getOldModuleId() != 0){
					moduleId = screenMasterForm.getOldModuleId() ;
				}
	    		MenusHandler.getInstance().reActivateMenus(seq,moduleId,screenMasterForm.getUserId());
	    		ActionMessage message = new ActionMessage(CMSConstants.MENU_SCREEN_MASTER_REACTIVATESUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				screenMasterForm.clearAll();
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception reActivateMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //load the details for module drop down and menus list.
	    loadDetails(screenMasterForm);
	    log.info("end of reActivateMenus method in MenuScreenMasterAction class.");
	    return mapping.findForward(CMSConstants.MENU_SCREEN);
	}
	
	/**
	 *	This method is called for displaying, after add,delete,edit,update,restore. 
	 * @param screenMasterForm
	 * @throws Exception
	 */
	
	private void loadDetails(MenuScreenMasterForm screenMasterForm)
			throws Exception {
		log.info("call of loadDetails method in MenuScreenMasterAction class.");
		//loads the data from menus table and setting to list of type menusTO.
		List<MenusTO> list = MenusHandler.getInstance().getMenuDetails();
		//setting menus list to form.
		screenMasterForm.setMenusList(list);
		//loads the data form modules table and setting to list of type moduleTO.
		List<ModuleTO> moduleList = ModuleHandler.getInstance().getModule();
		//setting the module list to form.
		screenMasterForm.setModuleToList(moduleList);
		log.info("end of loadDetails method in MenuScreenMasterAction class.");
	}
	// Added By Manu For AssignPrivilage In menuScreen
	/**
	 * This method is called when u click the link on left side.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward assignPrivilegeRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initMenuScreen method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
		try {
			setUserId(request, screenMasterForm);
			screenMasterForm.setPrivilegeList(null);
			List<AssignPrivilegeTO> privilegeList=MenusHandler.getInstance().getRolesFromAcccessPrivilege(screenMasterForm.getMenuId());
			if(privilegeList!=null && !privilegeList.isEmpty()){
				screenMasterForm.setPrivilegeList(privilegeList);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			screenMasterForm.setErrorMessage(msgKey);
			screenMasterForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of initMenuScreen method in MenuScreenMasterAction class.");
		return mapping.findForward(CMSConstants.MENU_SCREEN_ASSIGN_ROLE);
	}
	/**
	 * This method is called when u click the link on left side.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 */
	
	public ActionForward addMenuAssignAggrement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initMenuScreen method in MenuScreenMasterAction class.");
		MenuScreenMasterForm screenMasterForm = (MenuScreenMasterForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = screenMasterForm.validate(mapping, request);
		try {
			//Request for update operation
			setUserId(request, screenMasterForm);
			boolean isAdded;
			isAdded=MenusHandler.getInstance().addMenuAssignAggrement(screenMasterForm);
			//If update is successful then append the success message
			if(isAdded){
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.USERMANAGEMENT_ADD_SUCCESS));
				saveMessages(request, messages);
				screenMasterForm.clearAll();
				loadDetails(screenMasterForm);
				return mapping.findForward(CMSConstants.MENU_SCREEN);
			}	
			//Else add the failure message
			else{
				errors.add("error", new ActionError(CMSConstants.USERMANAGEMENT_ADD_FAILED));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MENU_SCREEN_ASSIGN_ROLE);
			}
		
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			screenMasterForm.setErrorMessage(msgKey);
			screenMasterForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			screenMasterForm.setErrorMessage(msg);
			screenMasterForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	// end By Manu For AssignPrivilage In menuScreen
}