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
import com.kp.cms.forms.admin.GuideLinesCheckListForm;
import com.kp.cms.handlers.admin.GuideLinesCheckListHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.to.admin.OrganizationTO;

@SuppressWarnings("deprecation")
public class GuideLinesCheckListAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(GuideLinesCheckListAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set organizationList having organizationTo objects to request, forward to
	 *         GUIDELINE_CHECKLIST_ENTRY
	 * @throws Exception
	 */

	public ActionForward initGuidelinesCheckList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GuideLinesCheckListForm guideLinesCheckListForm = (GuideLinesCheckListForm) form;
		setOrganizationListToRequest(request);
		setGuideLinesListToRequest(request);
		setUserId(request, guideLinesCheckListForm); // setting user id here to save last changed user information in table
		log.debug("Leaving initSubReligion ");
		return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Guideline CheckList
	 * @return to mapping religion entry
	 * @throws Exception
	 */
	public ActionForward addGuidelines(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside addGuidelines Action");
		GuideLinesCheckListForm guideLinesCheckListForm = (GuideLinesCheckListForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = guideLinesCheckListForm.validate(mapping, request);
		guideLinesCheckListForm.setId(0);
		boolean isAdded = false;
			try {
				if (!errors.isEmpty()) {
					setOrganizationListToRequest(request);
					setGuideLinesListToRequest(request);
					saveErrors(request, errors);
					//if any space is entered in text box then, assigning as null.
					if(guideLinesCheckListForm.getDescription().trim().isEmpty()){       
						guideLinesCheckListForm.setDescription(null);
					}

					return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
				}
				
				isAdded = GuideLinesCheckListHandler.getInstance().addGuideLinesCheckList(guideLinesCheckListForm, "add"); 
				//addGuidelines  method is using for add & edit. second param is used to identify add or edit
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.guideline.checklist.exists"));
				saveErrors(request, errors);
				setOrganizationListToRequest(request);
				setGuideLinesListToRequest(request);
				return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.GUIDELINE_CHECKLIST_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setOrganizationListToRequest(request);
				setGuideLinesListToRequest(request);
				return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
			} catch (Exception e) {
				log.error("error in update state page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					guideLinesCheckListForm.setErrorMessage(msg);
					guideLinesCheckListForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					String msg = super.handleApplicationException(e);
					guideLinesCheckListForm.setErrorMessage(msg);
					guideLinesCheckListForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			setOrganizationListToRequest(request);
			setGuideLinesListToRequest(request);
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.guideline.checklist.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			guideLinesCheckListForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.guideline.checklist.addfailure "));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);		
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update the Guidelines checklist
	 * @return
	 * @throws Exception
	 */
	

	public ActionForward updateGuideLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
											HttpServletResponse response) throws Exception {

		GuideLinesCheckListForm guideLinesCheckListForm = (GuideLinesCheckListForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = guideLinesCheckListForm.validate(mapping, request);
		boolean isUpdated = false;
			try {
				if (!errors.isEmpty()) {
					setOrganizationListToRequest(request);
					setGuideLinesListToRequest(request);
					saveErrors(request, errors);
					if(guideLinesCheckListForm.getDescription().trim().isEmpty()){       
						guideLinesCheckListForm.setDescription(null);
					}
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);					
					
				}
				
				isUpdated = GuideLinesCheckListHandler.getInstance().addGuideLinesCheckList(guideLinesCheckListForm, "edit");
				//addSubReligion  method is using for add & edit. second param is used to identify add or edit
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.subrel.exists"));
				saveErrors(request, errors);
				setOrganizationListToRequest(request);
				setGuideLinesListToRequest(request);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);					
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.GUIDELINE_CHECKLIST_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setOrganizationListToRequest(request);
				setGuideLinesListToRequest(request);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);					
			} catch (Exception e) {
				log.error("error in update guidelines checklist page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					guideLinesCheckListForm.setErrorMessage(msg);
					guideLinesCheckListForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					String msg = super.handleApplicationException(e);
					guideLinesCheckListForm.setErrorMessage(msg);
					guideLinesCheckListForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			setOrganizationListToRequest(request);
			setGuideLinesListToRequest(request);
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.guideline.checklist.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			guideLinesCheckListForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.guideline.checklist.updatefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
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
	public ActionForward deleteGuideLineChecklist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		GuideLinesCheckListForm guideCheckListForm = (GuideLinesCheckListForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (guideCheckListForm.getId() != 0) {
				int id = guideCheckListForm.getId();
				isDeleted = GuideLinesCheckListHandler.getInstance().deleteGuideLines(id, false, guideCheckListForm); 
			}
		} catch (Exception e) {
			log.error("error in Delete guidelines Action", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				guideCheckListForm.setErrorMessage(msg);
				guideCheckListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setOrganizationListToRequest(request);
		setGuideLinesListToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.guideline.checklist.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			guideCheckListForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.guideline.checklist.deletefailure"));
			saveErrors(request, errors);
		}
		
		return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);
	}

	/**
	 * 
	 * @param request
	 *            This method useful in populating in Religion selection.
	 * @throws Exception 
	 */
	public void setOrganizationListToRequest(HttpServletRequest request) throws Exception {
		List<OrganizationTO> organizationList = OrganizationHandler.getInstance().getOrganizationDetails(); 
		request.setAttribute("organizationList", organizationList);
		log.debug("leaving setOrganizationListToRequest in Action");
	}

	/**
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public void setGuideLinesListToRequest(HttpServletRequest request) throws Exception {
		List<GuideLinesCheckListTO> guidelinesList = GuideLinesCheckListHandler.getInstance().getGuidelinesChecklist();
		request.setAttribute("guidelinesList", guidelinesList);
		log.debug("leaving setGuideLinesListToRequest in Action");
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
	public ActionForward activateGuidelinesCheckList(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {

		GuideLinesCheckListForm guideCheckListForm = (GuideLinesCheckListForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			int id = guideCheckListForm.getDuplId();
			isActivated = GuideLinesCheckListHandler.getInstance().deleteGuideLines(id, true, guideCheckListForm);
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.GUIDELINE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setOrganizationListToRequest(request);
		setGuideLinesListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.GUIDELINE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateSubReligion in Action");
		return mapping.findForward(CMSConstants.GUIDELINE_CHECKLIST_ENTRY);

	}

	

}
