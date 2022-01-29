package com.kp.cms.actions.admin;

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
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DesignationEntryForm;
import com.kp.cms.handlers.admin.DesignationEntryHandler;
import com.kp.cms.to.admin.DesignationEntryTo;


public class DesignationEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(DesignationEntryAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set admittedThroughList having admittedThroughTo objects to
	 *         request, forward to admittedThroughEntry
	 * @throws Exception
	 */
	public ActionForward initDesignationEntry(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) 
	        throws Exception {

		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
		try {
			designationEntryForm.reset(mapping, request);
			List<DesignationEntryTo> designationList=DesignationEntryHandler.getInstance().getDesignationEntry();
			designationEntryForm.setDesignationList(designationList);
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				designationEntryForm.setErrorMessage(msgKey);
				designationEntryForm.setErrorStack(businessException.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				designationEntryForm.setErrorMessage(msg);
				designationEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	
		
		log.debug("Leaving initDesignationEntry ");

		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
	
	public ActionForward addDesignationEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = designationEntryForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isAdded = false;
		String name="";
		String order = "";
		try
		{
		if (errors.isEmpty()) {
			setUserId(request, designationEntryForm);
			if(designationEntryForm.getName()!=null && !designationEntryForm.getName().isEmpty()){
				name = designationEntryForm.getName().trim();
				order=designationEntryForm.getOrder().trim();
			}else{
				return mapping.findForward(CMSConstants.LOGIN_PAGE);
			}
			Designation designation =DesignationEntryHandler.getInstance().isNameExist(name, 0);
			//Designation designation1 =DesignationEntryHandler.getInstance().isorderExist(order, 0);
			if(designation!=null && designation.getIsActive()){
				errors.add(CMSConstants.DESIGNATION_NAME_EXIST, new ActionError(CMSConstants.DESIGNATION_NAME_EXIST));
				saveErrors(request, errors);
			}
			else if(designation!=null && !designation.getIsActive()){
				errors.add(CMSConstants.DESIGNATION_NAME_REACTIVATE, new ActionError(CMSConstants.DESIGNATION_NAME_REACTIVATE));
				session.setAttribute("designationId",designation.getId());
				saveErrors(request, errors);			
			}
			else{
				isAdded = DesignationEntryHandler.getInstance().addDesignationEntry(designationEntryForm);
		
			if (isAdded) {
				ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_ADD_SUCCESS);// Adding success message.
				messages.add("messages", message);
				saveMessages(request, messages);
				designationEntryForm.reset(mapping, request);	
			}else{
				errors.add(CMSConstants.DESIGNATION_ADD_FAILURE, new ActionError(CMSConstants.DESIGNATION_ADD_FAILURE));// Adding failure message
			}
			}
		} else {
			saveErrors(request, errors);
			}
		
		}catch (BusinessException businessException) {
			log.info("Exception addDesignationEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			designationEntryForm.setErrorMessage(msg);
			designationEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//getting all currency details.
		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		designationEntryForm.setDesignationList(designationList);
		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
	
	
	
	
	
	public ActionForward editDesignationEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = designationEntryForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		DesignationEntryTo designationEntryTo =DesignationEntryHandler.getInstance().editDesignationEntry(designationEntryForm.getId());
	    		designationEntryForm.setName(designationEntryTo.getName().trim());
	    		if(designationEntryTo.getOrder() != null){
	    			designationEntryForm.setOrder(designationEntryTo.getOrder().trim());
	    		}
	    		designationEntryForm.setId(designationEntryTo.getId());
	    		request.setAttribute("designation","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
	    			session.setAttribute("name",designationEntryTo.getName());
	    			session.setAttribute("order",designationEntryTo.getOrder());
	    		}
	    	}
	    	else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception editCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			designationEntryForm.setErrorMessage(msg);
			designationEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.	
		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		designationEntryForm.setDesignationList(designationList);
		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
	
	
	
	
	
	
	public ActionForward updateDesignationEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = designationEntryForm.validate(mapping, request);
	    try{
	    	//if reset is clicked when validation fails.
	    		if(isCancelled(request)){
	    			DesignationEntryTo designationEntryTo =DesignationEntryHandler.getInstance().editDesignationEntry(designationEntryForm.getId());
	    			designationEntryForm.setName(designationEntryTo.getName().trim());
	    			designationEntryForm.setOrder(designationEntryTo.getOrder().trim());
	    			designationEntryForm.setId(designationEntryTo.getId());
		    		request.setAttribute("designation","edit");
		    		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		    		designationEntryForm.setDesignationList(designationList);	
	    			return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	    		}
	    		if(errors.isEmpty()){
		    		setUserId(request, designationEntryForm);
		    		HttpSession session=request.getSession(false);
		    		String cname = session.getAttribute("name").toString();
		    		String csname="";
		    		if(session.getAttribute("order") != null){
		    			csname = session.getAttribute("order").toString();
		    		}
		    		String name = designationEntryForm.getName().trim();
		    		String order= designationEntryForm.getOrder().trim();
		    		if(!cname.equalsIgnoreCase(name) || !csname.equalsIgnoreCase(order)){
		    			Designation designation = DesignationEntryHandler.getInstance().isNameExist(name, designationEntryForm.getId());
//		    			Designation designation1 = DesignationEntryHandler.getInstance().isorderExist(order, designationEntryForm.getId());
		    		if(designation!=null && designation.getIsActive() && designation.getName().equalsIgnoreCase(name)){
						errors.add(CMSConstants.DESIGNATION_NAME_EXIST, new ActionError(CMSConstants.DESIGNATION_NAME_EXIST));
						request.setAttribute("designation","edit");
						saveErrors(request, errors);
		    		}else if(designation!=null && !designation.getIsActive()){
						errors.add(CMSConstants.DESIGNATION_NAME_REACTIVATE, new ActionError(CMSConstants.DESIGNATION_NAME_REACTIVATE));
						saveErrors(request, errors);			
						request.setAttribute("designation","edit");
					}else{
					boolean isUpdated =DesignationEntryHandler.getInstance().updateDesignationEntry(designationEntryForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("name");
		    			session.removeAttribute("order");
		    			ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						designationEntryForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						designationEntryForm.reset(mapping, request);
		    		}
		    	}
	    		}else{
		    		boolean isUpdated =DesignationEntryHandler.getInstance().updateDesignationEntry(designationEntryForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("name");
		    			session.removeAttribute("order");
		    			ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_UPDATE_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						designationEntryForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_UPDATE_FAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						designationEntryForm.reset(mapping, request);
		    		}
		    	}
	    	}else{
	    		//errors are present
				errors.add(messages);
				request.setAttribute("designation","edit");
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception updatedesignationEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			designationEntryForm.setErrorMessage(msg);
			designationEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		designationEntryForm.setDesignationList(designationList);
		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
	
	
	
	
	
	
	
	public ActionForward deleteDesignationEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = designationEntryForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, designationEntryForm);
	    		boolean isDelete=DesignationEntryHandler.getInstance().deleteDesignationEntry(designationEntryForm.getId(),designationEntryForm.getUserId());
				if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					designationEntryForm.reset(mapping, request);
				}if(!isDelete){
					//if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_DELETE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					designationEntryForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception deleteCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			designationEntryForm.setErrorMessage(msg);
			designationEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		designationEntryForm.setDesignationList(designationList);
		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
	
	
	
	
	
	
	
	public ActionForward reActivateDesignationEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DesignationEntryForm designationEntryForm = (DesignationEntryForm) form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors = designationEntryForm.validate(mapping, request);
	    HttpSession session=request.getSession();
	    try{
	    	if(errors.isEmpty()){
	    		setUserId(request, designationEntryForm);
	    		int id= (Integer)session.getAttribute("designationId");
	    		//errors are empty
	    		boolean isActivated = DesignationEntryHandler.getInstance().reActivateDesignationEntry(designationEntryForm.getName(),designationEntryForm.getUserId(),id);
	    		if(isActivated){
	    			//if reactivation is success.
		    		ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_REACTIVATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					designationEntryForm.reset(mapping, request);
	    		}else{
	    			//if reactivation is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.DESIGNATION_REACTIVATE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					designationEntryForm.reset(mapping, request);
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception reActivateCurrencyMaster");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			designationEntryForm.setErrorMessage(msg);
			designationEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    //getting all currency details.
		List<DesignationEntryTo> designationList =DesignationEntryHandler.getInstance().getDesignationEntry();
		designationEntryForm.setDesignationList(designationList);
		return mapping.findForward(CMSConstants.DESIGNATION_ENTRY);
	}
		}
