package com.kp.cms.actions.inventory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.InventoryLocationForm;
import com.kp.cms.forms.inventory.POTermsConditionsForm;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.handlers.inventory.POTermsConditionsHandler;

public class POTermsConditionsAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(POTermsConditionsAction.class);
	
	/**
	 * Method to set the required data to the form to display it in POTermsConditions.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPOTermsConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered POTermsConditionsAction -initPOTermsConditions Batch input");
		POTermsConditionsForm poTermsConditionsForm=(POTermsConditionsForm)form;
		poTermsConditionsForm.resetFields();
		poTermsConditionsForm.setId(null);
		poTermsConditionsForm.setSavedTcDescription(null);
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.removeAttribute(CMSConstants.OPERATION);
		}
		POTermsConditionsHandler.getInstance().setTCtoForm(poTermsConditionsForm);
		log.info("Exit POTermsConditionsAction-initPOTermsConditions input");
		return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
	}
	
	/**
	 * Adds or updates terms and conditions entered into the database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTermsAndConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered POTermsConditionsAction -initPOTermsConditions Batch input");
		POTermsConditionsForm poTermsConditionsForm=(POTermsConditionsForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, poTermsConditionsForm);
		HttpSession session = request.getSession(false);
		boolean editMode=false;
		if(session!=null && session.getAttribute(CMSConstants.OPERATION)!=null){
			if(session.getAttribute(CMSConstants.OPERATION).toString().equalsIgnoreCase("edit"))
				editMode=true;
		}
		try {
			POTermsConditionsHandler.getInstance().setTCtoForm(poTermsConditionsForm);
			validateForm(poTermsConditionsForm,errors,editMode);
			if(!errors.isEmpty()){
				addErrors(request, errors);
				log.info("Exit POTermsConditionsAction-initPOTermsConditions input");
				return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
						
			}
			boolean isAdded=POTermsConditionsHandler.getInstance().addTermsConditions(poTermsConditionsForm,editMode);
			if(isAdded){
				if(!editMode)
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.PO_TC_ADDED_SUCCESSFULLY));
				else messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.termsandconditions.updatesuccess"));
			}
			else{
				if(!editMode)
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.termsandconditions.addfailure"));
				else messages.add(CMSConstants.MESSAGES,new ActionMessage("unable to update Terms & Conditions."));
				poTermsConditionsForm.setTcDescription(null);
			}
		}
		catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.TERMS_CONDITIONS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
		} 
		catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			poTermsConditionsForm.setErrorMessage(msg);
			poTermsConditionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		saveMessages(request, messages);
		poTermsConditionsForm.setSavedTcDescription(poTermsConditionsForm.getTcDescription());
		poTermsConditionsForm.setTcDescription(null);
		session.removeAttribute(CMSConstants.OPERATION);
		log.info("Exit POTermsConditionsAction-initPOTermsConditions input");
		return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
	}
	
	/**
	 * validates the form data
	 * @param poTermsConditionsForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateForm(POTermsConditionsForm poTermsConditionsForm,
			ActionErrors errors,boolean editMode) throws Exception {
		if(poTermsConditionsForm.isTcDescInActive()){
			throw new ReActivateException();
		}
		else{
			if(!editMode && poTermsConditionsForm.isTcDescExists()){
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PO_TC_DESC_ALREADY_EXISTS));
			}
			if(poTermsConditionsForm.getTcDescription()==null || poTermsConditionsForm.getTcDescription().trim().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PO_TC_DESC_REQD));
			}
			if(poTermsConditionsForm.getTcDescription()!=null && !poTermsConditionsForm.getTcDescription().isEmpty() &&
					poTermsConditionsForm.getTcDescription().length()>2000){
				errors.add(CMSConstants.PURCHASEORDER_TC_LARGE,new ActionError(CMSConstants.PURCHASEORDER_TC_LARGE));
			}
		}
	}

	/**
	 * setting the data to form for editing
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTermsConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered POTermsConditionsAction -editTermsConditions Batch input");
		POTermsConditionsForm poTermsConditionsForm=(POTermsConditionsForm)form;
		HttpSession session=request.getSession();
		String[] args=poTermsConditionsForm.getSavedTcDescription().split("<br>");
		StringBuilder strTc=new StringBuilder();
		for (String string : args) {
			strTc.append(string);
			strTc.append("\r\n");
		}
		if(strTc!=null && !strTc.toString().isEmpty())
		poTermsConditionsForm.setTcDescription(strTc.toString());
		session.setAttribute(CMSConstants.OPERATION, "edit");
		log.info("Exit POTermsConditionsAction-editTermsConditions input");
		return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
	}
	
	/**
	 * deletes the terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTermsAndConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered POTermsConditionsAction -deleteTermsAndConditions Batch input");
		POTermsConditionsForm poTermsConditionsForm=(POTermsConditionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		setUserId(request, poTermsConditionsForm);
		try {
			if (poTermsConditionsForm.getId()!=null && !poTermsConditionsForm.getId().isEmpty() && Integer.parseInt(poTermsConditionsForm.getId()) != 0) {
				int id = Integer.parseInt(poTermsConditionsForm.getId());
				isDeleted = POTermsConditionsHandler.getInstance().deleteTermsConditions(id,false,poTermsConditionsForm.getUserId());
			}
			POTermsConditionsHandler.getInstance().setTCtoForm(poTermsConditionsForm);
			poTermsConditionsForm.resetFields();
			} 
		catch (Exception e) {
			log.error("error in deleteTermsAndConditions...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				poTermsConditionsForm.setErrorMessage(msg);
				poTermsConditionsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.deletefailure"));
			saveErrors(request, errors);
		}
		log.info("Exit POTermsConditionsAction-deleteTermsAndConditions");
		return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
	}
	
	
	/**
	 * activates the deleted terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateTermsAndConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateTermsAndConditions");
		POTermsConditionsForm poTermsConditionsForm=(POTermsConditionsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (poTermsConditionsForm.getId()!=null && !poTermsConditionsForm.getId().isEmpty()) {
				int id = Integer.parseInt(poTermsConditionsForm.getId());  //setting id for activate
				isActivated = POTermsConditionsHandler.getInstance().deleteTermsConditions(id, true, poTermsConditionsForm.getUserId()); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.TERMS_CONDITIONS_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		POTermsConditionsHandler.getInstance().setTCtoForm(poTermsConditionsForm);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.TERMS_CONDITIONS_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			
		}
		POTermsConditionsHandler.getInstance().setTCtoForm(poTermsConditionsForm);
		poTermsConditionsForm.resetFields();
		log.debug("leaving activateTermsAndConditions");
		return mapping.findForward(CMSConstants.INIT_PO_TERMS_CONDITIONS);
	}
			
}
