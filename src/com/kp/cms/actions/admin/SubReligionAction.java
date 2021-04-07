package com.kp.cms.actions.admin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;

/**
 * 
 * @author
 *
 */
@SuppressWarnings("deprecation")
public class SubReligionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SubReligionAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set religionList having religionTo objects to request, forward to
	 *         subReligionEntry
	 * @throws Exception
	 */

	public ActionForward initSubReligion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SubReligionForm subReligionForm = (SubReligionForm) form;
		try {
			setReligionListToRequest(request);
			setSubReligionListToRequest(request);
			setUserId(request, subReligionForm); // setting user id here to save last changed user information in table
		}
		catch (Exception e) {
			log.error("error initSubReligion...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subReligionForm.setErrorMessage(msg);
				subReligionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward("subReligionEntry");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Sub Religion
	 * @return to mapping religion entry
	 * @throws Exception
	 */
	public ActionForward addSubReligion(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside addSubReligion Action");
		SubReligionForm subReligionForm = (SubReligionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = subReligionForm.validate(mapping, request);
		boolean isAdded = false;
			try {
				if (!errors.isEmpty()) {
					setSubReligionListToRequest(request);
					setReligionListToRequest(request);
					saveErrors(request, errors);
					//if any space is entered in text box then, assigning as null.
					if(subReligionForm.getSubReligionName().trim().isEmpty()){       
						subReligionForm.setSubReligionName(null);
					}
					return mapping.findForward("subReligionEntry");
				}
				final boolean isSpcl=nameValidate(subReligionForm.getSubReligionName().trim()); //validation checking for special characters
				if(isSpcl)
				{
					//errors.add("error", new ActionError("knowledgepro.admin.special"));
				}
				if (!errors.isEmpty()) {
					setSubReligionListToRequest(request);
					setReligionListToRequest(request);
					saveErrors(request, errors);
					//if any space is entered in text box then, assigning as null.
					if(subReligionForm.getSubReligionName().trim().isEmpty()){       
						subReligionForm.setSubReligionName(null);
					}
					return mapping.findForward("subReligionEntry");
				}
				
				
				isAdded = SubReligionHandler.getInstance().addSubReligion(subReligionForm, "Add");
				//addSubReligion  method is using for add & edit. second param is used to identify add or edit
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.subrel.exists"));
				saveErrors(request, errors);
				setSubReligionListToRequest(request);
				setReligionListToRequest(request);
				return mapping.findForward("subReligionEntry");
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.SUBREL_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setSubReligionListToRequest(request);
				setReligionListToRequest(request);
				return mapping.findForward("subReligionEntry");
			} catch (Exception e) {
				log.error("error in update state page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					subReligionForm.setErrorMessage(msg);
					subReligionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					String msg = super.handleApplicationException(e);
					subReligionForm.setErrorMessage(msg);
					subReligionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			setSubReligionListToRequest(request);
			setReligionListToRequest(request);
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.subrel.addsuccess", subReligionForm.getSubReligionName());
			messages.add("messages", message);
			saveMessages(request, messages);
			subReligionForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.subrel.addfailure", subReligionForm.getSubReligionName()));
			saveErrors(request, errors);
		}
		return mapping.findForward("subReligionEntry");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update the existing Sub Religion
	 * @return
	 * @throws Exception
	 */
	

	public ActionForward updateSubReligion(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
											HttpServletResponse response) throws Exception {

		SubReligionForm subReligionForm = (SubReligionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = subReligionForm.validate(mapping, request);
		boolean isUpdated = false;
			try {
				if (!errors.isEmpty()) {
					setSubReligionListToRequest(request);
					setReligionListToRequest(request);
					saveErrors(request, errors);
					if(subReligionForm.getSubReligionName().trim().isEmpty()){
						subReligionForm.setSubReligionName(null);
					}
					request.setAttribute("subRelOperation", "edit");
					return mapping.findForward("subReligionEntry");
				}
				boolean isSpcl=nameValidate(subReligionForm.getSubReligionName().trim()); //validation checking for special characters
				if(isSpcl)
				{
					//errors.add("error", new ActionError("knowledgepro.admin.special"));
				}
				if (!errors.isEmpty()) {
					setSubReligionListToRequest(request);
					setReligionListToRequest(request);
					saveErrors(request, errors);
					if(subReligionForm.getSubReligionName().trim().isEmpty()){
						subReligionForm.setSubReligionName(null);
					}
					request.setAttribute("subRelOperation", "edit");
					return mapping.findForward("subReligionEntry");
				}
				
				isUpdated = SubReligionHandler.getInstance().addSubReligion(subReligionForm, "Edit");
				//addSubReligion  method is using for add & edit. second param is used to identify add or edit
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.subrel.exists"));
				saveErrors(request, errors);
				setSubReligionListToRequest(request);
				setReligionListToRequest(request);
				request.setAttribute("subRelOperation", "edit");
				return mapping.findForward("subReligionEntry");
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.SUBREL_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setSubReligionListToRequest(request);
				setReligionListToRequest(request);
				request.setAttribute("subRelOperation", "edit");
				return mapping.findForward("subReligionEntry");
			} catch (Exception e) {
				log.error("error in update state page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					subReligionForm.setErrorMessage(msg);
					subReligionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					String msg = super.handleApplicationException(e);
					subReligionForm.setErrorMessage(msg);
					subReligionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			setSubReligionListToRequest(request);
			setReligionListToRequest(request);
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.subrel.updatesuccess", subReligionForm.getSubReligionName());
			messages.add("messages", message);
			saveMessages(request, messages);
			subReligionForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.subrel.updatefailure", subReligionForm.getSubReligionName()));
			saveErrors(request, errors);
		}
		return mapping.findForward("subReligionEntry");
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing sub religion
	 * @return ActionForward This action method will called when particular Sub
	 *         Religion need to be deleted based on the sub religion id.
	 * @throws Exception
	 */
	public ActionForward deleteSubReligion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		SubReligionForm subReligionForm = (SubReligionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (subReligionForm.getSubReligionId() != 0) {
				int subReligionId = subReligionForm.getSubReligionId();
				isDeleted = SubReligionHandler.getInstance().deleteSubReligion(subReligionId, false, subReligionForm);
			}
		} catch (Exception e) {
			log.error("error in Delete Sub Religion Action", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subReligionForm.setErrorMessage(msg);
				subReligionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setSubReligionListToRequest(request);
		setReligionListToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.subrel.deletesuccess", subReligionForm
										.getSubReligionName());
			messages.add("messages", message);
			saveMessages(request, messages);
			subReligionForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.subrel.deletefailure", subReligionForm
												.getSubReligionName()));
			saveErrors(request, errors);
		}
		return mapping.findForward("subReligionEntry");
	}

	/**
	 * 
	 * @param request
	 *            This method useful in populating in Religion selection.
	 */
	public void setReligionListToRequest(HttpServletRequest request) {
		List<ReligionTO> religionList = ReligionHandler.getInstance().getReligion();
		request.setAttribute("religionList", religionList);
		log.debug("leaving setReligionListToRequest in Action");
	}

	/**
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public void setSubReligionListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setSubReligionListToRequest in Action");
		List<ReligionSectionTO> subReligionList = SubReligionHandler.getInstance().getSubReligion();
		request.setAttribute("subReligionList", subReligionList);
		log.debug("leaving setSubReligionListToRequest in Action");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateSubReligion(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {

		SubReligionForm subReligionForm = (SubReligionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			int subRelId = subReligionForm.getDuplId();
			isActivated = SubReligionHandler.getInstance().deleteSubReligion(subRelId, true, subReligionForm); 
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.SUBREL_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setSubReligionListToRequest(request);
		setReligionListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.SUBREL_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateSubReligion in Action");
		return mapping.findForward("subReligionEntry");
	}

	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}
	
}
