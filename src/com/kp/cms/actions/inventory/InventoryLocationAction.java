package com.kp.cms.actions.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.InventoryLocationForm;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvLocationTO;

@SuppressWarnings("deprecation")
public class InventoryLocationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(InventoryLocationAction.class);

	/**
	 * setting employee map to form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         INV_LOCATION
	 * @throws Exception
	 */
	public ActionForward initInventoryLocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initInventoryLocation ");
		InventoryLocationForm inventoryLocationForm = (InventoryLocationForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setInventoryListToRequest(request);
			setUserId(request, inventoryLocationForm);
			initFields(inventoryLocationForm);
			if(request.getParameter("mainPageExists")==null){
				inventoryLocationForm.setMainPage(null);
				inventoryLocationForm.setSuperMainPage(null);
			}
		} catch (Exception e) {
			log.error("error initInventoryLocation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				inventoryLocationForm.setErrorMessage(msg);
				inventoryLocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initInventoryLocation ");
	
		return mapping.findForward(CMSConstants.INV_LOCATION);
	}	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  inv location
	 * @return to mapping INV_LOCATION
	 * @throws Exception
	 */
	public ActionForward addInventoryLocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addInventoryLocation Action");
		InventoryLocationForm inventoryLocationForm = (InventoryLocationForm) form;
		inventoryLocationForm.setId(0);
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = inventoryLocationForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setInventoryListToRequest(request);
				return mapping.findForward(CMSConstants.INV_LOCATION);
			}
			isAdded = InventoryLocationHandler.getInstance().addInventoryLocation(inventoryLocationForm, "add");
			setInventoryListToRequest(request);
			
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.InvLocation.name.exists"));
			saveErrors(request, errors);
			setInventoryListToRequest(request);
			return mapping.findForward(CMSConstants.INV_LOCATION);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.INV_LOCATION_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setInventoryListToRequest(request);
			return mapping.findForward(CMSConstants.INV_LOCATION);
		} catch (Exception e) {
			log.error("error in final submit of location page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				inventoryLocationForm.setErrorMessage(msg);
				inventoryLocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
		
			if(inventoryLocationForm.getSuperMainPage()!=null && !inventoryLocationForm.getSuperMainPage().isEmpty()){
				session.setAttribute("newEntryName",inventoryLocationForm.getLocation());
				session.setAttribute("campusId", inventoryLocationForm.getInvCampusId());
			}
			ActionMessage message = new ActionMessage("knowledgepro.admin.InvLocation.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(inventoryLocationForm);
			inventoryLocationForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.InvLocation.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addInventoryLocation Action");
		return mapping.findForward(CMSConstants.INV_LOCATION);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update  inv location
	 * @return to mapping INV_LOCATION
	 * @throws Exception
	 */
	public ActionForward updateInventoryLocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateInventoryLocation Action");
		InventoryLocationForm inventoryLocationForm = (InventoryLocationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = inventoryLocationForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setInventoryListToRequest(request);
				return mapping.findForward(CMSConstants.INV_LOCATION);
			}
			isUpdated = InventoryLocationHandler.getInstance().addInventoryLocation(inventoryLocationForm, "edit");
			setInventoryListToRequest(request);
			inventoryLocationForm.reset(mapping, request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.InvLocation.name.exists"));
			saveErrors(request, errors);
			setInventoryListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INV_LOCATION);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.INV_LOCATION_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setInventoryListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INV_LOCATION);
		} catch (Exception e) {
			log.error("error in final submit of location page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				inventoryLocationForm.setErrorMessage(msg);
				inventoryLocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			HttpSession session=request.getSession();
			if(inventoryLocationForm.getSuperMainPage()!=null && !inventoryLocationForm.getSuperMainPage().isEmpty()){
				session.setAttribute("newEntryName",inventoryLocationForm.getLocation());
				session.setAttribute("campusId", inventoryLocationForm.getInvCampusId());
			}
			ActionMessage message = new ActionMessage("knowledgepro.admin.InvLocation.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(inventoryLocationForm);
			inventoryLocationForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.InvLocation.updatefailure"));
			saveErrors(request, errors);
		}
		inventoryLocationForm.reset(mapping, request);
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving addInventoryLocation Action");
		return mapping.findForward(CMSConstants.INV_LOCATION);
	}	
	/**
	 * Method to set all active location to the request
	 * @param request
	 * @throws Exception
	 */
	public void setInventoryListToRequest(HttpServletRequest request) throws Exception{
		List<InvLocationTO> locationList = InventoryLocationHandler.getInstance().getInvLocation();
		List<InvCampusTo> campusList=InventoryLocationHandler.getInstance().getCampus();
		
		if( locationList != null ){
			request.setAttribute("locationList", locationList);
		}
		request.getSession().setAttribute("campusList", campusList);
	}

	/**
	 * 
	 * @param locationForm
	 */
	public void initFields(InventoryLocationForm locationForm){
		locationForm.setLocation(null);
		locationForm.setEmployeeId(null);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the inv location record
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteInvLocation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteInvLocation");
		InventoryLocationForm locationForm = (InventoryLocationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (locationForm.getId() != 0) {
				int id = locationForm.getId();
				isDeleted = InventoryLocationHandler.getInstance().deleteLocation(id, false, locationForm);
			}
			setInventoryListToRequest(request);
			locationForm.reset(mapping, request);
			} catch (Exception e) {
			log.error("error in deleteInvLocation...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				locationForm.setErrorMessage(msg);
				locationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.InvLocation.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(locationForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.InvLocation.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteInvLocation");
		return mapping.findForward(CMSConstants.INV_LOCATION);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the inv location
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateInvLocation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateInvLocation");
		InventoryLocationForm locationForm = (InventoryLocationForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (locationForm.getDuplId() != 0) {
				int id = locationForm.getDuplId();  //setting id for activate
				isActivated = InventoryLocationHandler.getInstance().deleteLocation(id, true, locationForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.INV_LOCATION_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setInventoryListToRequest(request);
		locationForm.reset(mapping, request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.INV_LOCATION_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(locationForm);
		}
		log.debug("leaving activateInvLocation");
		return mapping.findForward(CMSConstants.INV_LOCATION);
	}
			
		
}
