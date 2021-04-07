package com.kp.cms.actions.employee;

import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.AttributeMasterForm;
import com.kp.cms.handlers.employee.AttributeMasterHandler;
import com.kp.cms.to.employee.EmpAttributeTO;

@SuppressWarnings("deprecation")
public class AttributeMasterAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttributeMasterAction.class);
	/**
	 * setting AttributeList
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         ATTRIBUTE_MASTER
	 * @throws Exception
	 */
	public ActionForward initAttributeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initAttributeMaster");
		AttributeMasterForm attributeMasterForm = (AttributeMasterForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setAttributeListRequest(request);
			setUserId(request, attributeMasterForm);
			initFields(attributeMasterForm);
		} catch (Exception e) {
			log.error("error initAttributeMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attributeMasterForm.setErrorMessage(msg);
				attributeMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initAttributeMaster ");
	
		return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  attribute
	 * @return to mapping ATTRIBUTE_MASTER
	 * @throws Exception
	 */
	public ActionForward addAttributeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addAttributeMaster Action");
		AttributeMasterForm attributeMasterForm = (AttributeMasterForm) form;
		attributeMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attributeMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAttributeListRequest(request);
				return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
			}
			isAdded = AttributeMasterHandler.getInstance().addAttributeMaster(attributeMasterForm, "add"); 
			setAttributeListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.attribute.master.exists"));
			saveErrors(request, errors);
			setAttributeListRequest(request);
			return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ATTRIBUTE_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAttributeListRequest(request);
			return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of attribute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attributeMasterForm.setErrorMessage(msg);
				attributeMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.attribute.master.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(attributeMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.attribute.master.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addAttributeMaster Action");
		return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
	}	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update attribute master
	 * @return to mapping ATTRIBUTE_MASTER
	 * @throws Exception
	 */
	public ActionForward updateAttributeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateAttributeMaster Action");
		AttributeMasterForm attributeMasterForm = (AttributeMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attributeMasterForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAttributeListRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
			}
			isUpdated = AttributeMasterHandler.getInstance().addAttributeMaster(attributeMasterForm, "edit"); 
			setAttributeListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.attribute.master.exists"));
			saveErrors(request, errors);
			setAttributeListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ATTRIBUTE_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAttributeListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
		} catch (Exception e) {
			log.error("error in updateAttributeMaster page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attributeMasterForm.setErrorMessage(msg);
				attributeMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.attribute.master.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(attributeMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.attribute.master.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving updateAttributeMaster Action");
		return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
	}	
	
	
	/**
	 * 
	 * @param request
	 *            This method sets the attributeList to Request used to display
	 *            attributeList record on UI.
	 * @throws Exception
	 */
	public void setAttributeListRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setAttributeListRequest");
		List<EmpAttributeTO> attributeList = AttributeMasterHandler.getInstance().getAttributeMasterDetails();
		request.setAttribute("attributeList", attributeList);
	}
	/**
	 * 
	 * @param attributeMasterForm
	 */
	public void initFields(AttributeMasterForm attributeMasterForm){
		attributeMasterForm.setName(null);
		attributeMasterForm.setIsEmployee(null);
		
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the attribute record
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAttribute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteAttribute");
		AttributeMasterForm attributeMasterForm = (AttributeMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (attributeMasterForm.getId() != 0) {
				int id = attributeMasterForm.getId();
				isDeleted = AttributeMasterHandler.getInstance().deleteAttribute(id, false, attributeMasterForm);
			}
		} catch (Exception e) {
			log.error("error in deleteAttribute...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attributeMasterForm.setErrorMessage(msg);
				attributeMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setAttributeListRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.employee.attribute.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(attributeMasterForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.employee.attribute.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteAttribute");
		return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
	}
	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the education master
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateAttribute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateAttribute");
		AttributeMasterForm attributeMasterForm = (AttributeMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (attributeMasterForm.getDuplId() != 0) {
				int id = attributeMasterForm.getDuplId();  //setting id for activate
				isActivated = AttributeMasterHandler.getInstance().deleteAttribute(id, true, attributeMasterForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.ATTRIBUTE_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setAttributeListRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.ATTRIBUTE_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(attributeMasterForm);
		}
		log.debug("leaving activateAttribute");
		return mapping.findForward(CMSConstants.ATTRIBUTE_MASTER);
	}
		
}
