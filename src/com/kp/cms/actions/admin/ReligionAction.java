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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.ReligionForm;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.to.admin.ReligionTO;

/**
 * DispatchAction to manage Add, Edit, Delete actions for Religion.
 */
@SuppressWarnings("deprecation")
public class ReligionAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ReligionAction.class);
	
	/**
	 * Performs the get Religion action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward getReligion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReligionForm religionForm = (ReligionForm) form;
		List<ReligionTO> religionList = ReligionHandler.getInstance()
				.getReligion();
		religionForm.setReligionList(religionList);
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Religion");
		return mapping.findForward(CMSConstants.RELIGION_ENTRY);
	}

	/**
	 * Performs the add Religion action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward addReligion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReligionForm religionForm = (ReligionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = religionForm.validate(mapping, request);
		boolean isReligionAdded = false;
		boolean isExisting=false;
		String regionName = religionForm.getReligionName();
		try
		{
		if (errors.isEmpty()) {
			String religionName = religionForm.getReligionName();
			isExisting=ReligionHandler.getInstance().existanceCheck(regionName);
			if(isExisting)
			{
				errors.add("error", new ActionError(
				"knowledgepro.admin.programtype.name.exists",regionName));
				saveErrors(request, errors);
						
			}else{
			isReligionAdded = ReligionHandler.getInstance()
					.addReligion(religionName);
			if (isReligionAdded) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.programtype.name.addsuccess",
						regionName);// Adding added message.
				messages.add("messages", message);
				saveMessages(request, messages);
				religionForm.reset(mapping, request);	
			}else{
				errors.add("error", new ActionError(
						"knowledgepro.admin.programtype.name.addfail",
						regionName));// Adding failure message
			}
			}
		} else {
			saveErrors(request, errors);
		}

		List<ReligionTO> religionList = ReligionHandler.getInstance()
				.getReligion();
		religionForm.setReligionList(religionList);
				
		}catch (Exception e) {
			log.error("error in adding religion to page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				religionForm.setErrorMessage(msg);
				religionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
	}
		return mapping.findForward(CMSConstants.RELIGION_ENTRY);
	}

	/**
	 * Performs the edit Religion action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward editReligion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReligionForm religionForm = (ReligionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = religionForm.validate(mapping, request);
		int religionId =  Integer.parseInt(religionForm.getReligionId());
		String religionName = religionForm.getReligionName();
		boolean isReligionEdited = false;
		boolean isExisting=false;
		try
		{
		if (errors.isEmpty()) {
			
			if(!(religionForm.getEditedField().equalsIgnoreCase(religionName)))
			{
				//isExisting=ProgramTypeHandler.getInstance().existanceCheck(religionName);
			}
			if(isExisting)
			{
				errors.add("error", new ActionError(
				"knowledgepro.admin.programtype.name.exists",religionName));
				saveErrors(request, errors);
				
						
			}else
			{
				isReligionEdited=ReligionHandler.getInstance().editReligion(religionId, religionName);
			if (isReligionEdited) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.programtype.name.updatesuccess",
						religionName);// Adding added message.
				messages.add("messages", message);
				saveMessages(request, messages);
				religionForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admin.programtype.name.updatefail",
						religionName));// Adding failure message
				saveErrors(request, errors);
			}
			}
		} else {
			saveErrors(request, errors);
		}
		
		List<ReligionTO> religionList = ReligionHandler
					.getInstance().getReligion();
		religionForm.setReligionList(religionList);
		}catch (Exception e) {
			log.error("error in Updation of religion...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				religionForm.setErrorMessage(msg);
				religionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (!errors.isEmpty()) {
			request.setAttribute("Update", "Update");
		}
		
		return mapping.findForward(CMSConstants.RELIGION_ENTRY);
	}

	/**
	 * Performs the delete Religion action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteReligion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReligionForm religionForm = (ReligionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int religionId = Integer.parseInt(religionForm.getReligionId());
		String religionName = religionForm.getReligionName();
		try
		{
		boolean isReligionDeleted = ReligionHandler.getInstance()
				.deleteReligion(religionId,religionName);
		if (isReligionDeleted) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.programtype.name.deletesuccess",
					religionName);// Adding added message.
			messages.add("messages", message);
			saveMessages(request, messages);
			religionForm.reset(mapping, request);
		} else {
			errors.add("error", new ActionError(
					"knowledgepro.admin.programtype.name.deletefail",
					religionName));// Adding failure message
			saveErrors(request, errors);
		}
		List<ReligionTO> religionList = ReligionHandler
					.getInstance().getReligion();
		religionForm.setReligionList(religionList);
		
		}catch (Exception e) {
			log.error("error in deletion of religion page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				religionForm.setErrorMessage(msg);
				religionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.RELIGION_ENTRY);
	}
}
