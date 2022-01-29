package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.RemarkTypeForm;
import com.kp.cms.handlers.admin.RemarkTypeHandler;
import com.kp.cms.to.admin.RemarkTypeTO;

@SuppressWarnings("deprecation")
public class RemarkTypeAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(RemarkTypeAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to remark entry
	 * @throws Exception
	 */
	public ActionForward initRemarkType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		RemarkTypeForm remarkTypeForm = (RemarkTypeForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			remarkTypeForm.setId(0);
			setRemarkListToRequest(request);			
		} catch (Exception e) {
			log.error("error initRemarkType...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				remarkTypeForm.setErrorMessage(msg);
				remarkTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response this will add new record to remark type table
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward addRemarkType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		RemarkTypeForm remarkTypeForm = (RemarkTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = remarkTypeForm.validate(mapping, request);
		remarkTypeForm.setId(0);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRemarkListToRequest(request);			
				return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
			}
			setUserId(request, remarkTypeForm);
			isAdded = RemarkTypeHandler.getInstance().addRemarkType(remarkTypeForm, "Add"); 
			setRemarkListToRequest(request);			
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.remark.type.exists"));
			saveErrors(request, errors);
			setRemarkListToRequest(request);			
			return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.REMARK_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setRemarkListToRequest(request);			
			return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of remark type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				remarkTypeForm.setErrorMessage(msg);
				remarkTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.remark.type.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			remarkTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.grade.addfailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
	}
	

	
	
	/**
	 * setting remark type list to request for UI display
	 * @param request
	 * @throws Exception
	 */
	public void setRemarkListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setRemarkListToRequest");
		List<RemarkTypeTO> remarkTypeList = RemarkTypeHandler.getInstance().getRemarks(); 
		request.setAttribute("remarkTypeList", remarkTypeList);
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  this will update the existing remark type
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRemarkType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	
		log.debug("inside updateRemarkType Action");
		RemarkTypeForm remarkTypeForm = (RemarkTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = remarkTypeForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRemarkListToRequest(request);			
				request.setAttribute("remarkTypeOperation", "edit");
				return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
			}
			setUserId(request, remarkTypeForm);
			isAdded = RemarkTypeHandler.getInstance().addRemarkType(remarkTypeForm, "Edit"); 
			setRemarkListToRequest(request);			
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.remark.type.exists"));
			saveErrors(request, errors);
			setRemarkListToRequest(request);			
			return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.REMARK_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setRemarkListToRequest(request);			
			return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of remark type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				remarkTypeForm.setErrorMessage(msg);
				remarkTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.remark.type.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			remarkTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.grade.addfailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
	
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the remark type
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRemarkType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		RemarkTypeForm remarkTypeForm = (RemarkTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (remarkTypeForm.getId() != 0) {
				int remarkId = remarkTypeForm.getId();
				isDeleted = RemarkTypeHandler.getInstance().deleteRemarkType(remarkId, false, remarkTypeForm);
			}
		} catch (Exception e) {
			log.error("error in deleteRemarkType...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				remarkTypeForm.setErrorMessage(msg);
				remarkTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setRemarkListToRequest(request);			
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.remark.type.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			remarkTypeForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.remark.type.deletefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the remark type
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateRemarkType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		RemarkTypeForm remarkTypeForm = (RemarkTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (remarkTypeForm.getDuplId() != 0) {
				int remarkId = remarkTypeForm.getDuplId();  //setting id for activate
				isActivated = RemarkTypeHandler.getInstance().deleteRemarkType(remarkId, true, remarkTypeForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.REMARK_TYPE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setRemarkListToRequest(request);			
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.REMARK_TYPE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.REMARK_TYPE_ENTRY);
	}
	
	
}
