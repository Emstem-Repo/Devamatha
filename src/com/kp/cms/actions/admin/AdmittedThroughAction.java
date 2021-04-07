package com.kp.cms.actions.admin;

/** 
 * 
 * @author Date Created : 19 Jan 2009 This action class used for Admitted Through
 *         related config operation
 */

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.to.admin.AdmittedThroughTO;

@SuppressWarnings("deprecation")
public class AdmittedThroughAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AdmittedThroughAction.class);

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
	public ActionForward initAdmittedThrough(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		AdmittedThroughForm admittedThroughForm = (AdmittedThroughForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Admitted Through");
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setAdmittedThroughToRequest(request);  //setting admitted through list to request for UI display
		}

		catch (Exception e) {
			log.error("error in initAdmittedThrough...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admittedThroughForm.setErrorMessage(msg);
				admittedThroughForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Admitted Through
	 * @return to mapping admitted through entry
	 * @throws Exception
	 */
	public ActionForward addAdmittedThrough(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		AdmittedThroughForm admittedThroughForm = (AdmittedThroughForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = admittedThroughForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAdmittedThroughToRequest(request);
				//if space is entered then no need to save
				if((admittedThroughForm.getAdmittedThrough().trim()).isEmpty()){
					admittedThroughForm.setAdmittedThrough(null);
				}
				return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
			}
			
			boolean isSpcl=nameValidate(admittedThroughForm.getAdmittedThrough().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAdmittedThroughToRequest(request);
				if((admittedThroughForm.getAdmittedThrough().trim()).isEmpty()){
					admittedThroughForm.setAdmittedThrough(null);
				}
				return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
			}
			
			setUserId(request, admittedThroughForm); //setting user is to update last changed details
			isAdded = AdmittedThroughHandler.getInstance().addAdmittedThrough(admittedThroughForm, "Add"); //Add parameter used to identify add/edit

			setAdmittedThroughToRequest(request);

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.admittedthrough.name.exists"));
			saveErrors(request, errors);
			setAdmittedThroughToRequest(request);
			return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.AMITTED_THROUGH_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAdmittedThroughToRequest(request);
			return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admittedThroughForm.setErrorMessage(msg);
				admittedThroughForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.admthrough.addsuccess",admittedThroughForm.getAdmittedThrough());
			messages.add("messages", message);
			saveMessages(request, messages);
			admittedThroughForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.admthrough.addfailure",admittedThroughForm.getAdmittedThrough()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update the existing Admitted Through
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAdmittedThrough(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		AdmittedThroughForm admittedThroughForm = (AdmittedThroughForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = admittedThroughForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAdmittedThroughToRequest(request);
				//if space is entered then assigning null, else blank space will get updated in the table
				if((admittedThroughForm.getAdmittedThrough().trim()).isEmpty()){       
					admittedThroughForm.setAdmittedThrough(null);
				}
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
			}
			
			boolean isSpcl=nameValidate(admittedThroughForm.getAdmittedThrough().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAdmittedThroughToRequest(request);
				if((admittedThroughForm.getAdmittedThrough().trim()).isEmpty()){
					admittedThroughForm.setAdmittedThrough(null);
				}
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
			}
			
			setUserId(request, admittedThroughForm);  //setting user for updating last changed details
			isUpdated = AdmittedThroughHandler.getInstance().addAdmittedThrough(admittedThroughForm, "Edit");
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.admittedthrough.name.exists"));
			saveErrors(request, errors);
			setAdmittedThroughToRequest(request);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.AMITTED_THROUGH_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAdmittedThroughToRequest(request);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
		} catch (Exception e) {
			log.error("error in update admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admittedThroughForm.setErrorMessage(msg);
				admittedThroughForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setAdmittedThroughToRequest(request);
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.admthrough.updatesuccess",
					admittedThroughForm.getAdmittedThrough());
			messages.add("messages", message);
			saveMessages(request, messages);
			admittedThroughForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.admthrough.updatefailure",
					admittedThroughForm.getAdmittedThrough()));
			saveErrors(request, errors);
		}
		request.setAttribute("admOperation", "add");
		return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing Admitted Through
	 * @return ActionForward This action method will called when particular
	 *         Admitted Through need to be deleted based on the Admitted Through
	 *         id.
	 * @throws Exception
	 */
	public ActionForward deleteAdmittedThrough(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		AdmittedThroughForm admittedThroughForm = (AdmittedThroughForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (admittedThroughForm.getAdmittedThroughId() != 0) {
				int admThroughId = admittedThroughForm.getAdmittedThroughId();
				setUserId(request, admittedThroughForm); //setting user id for updating last changed details
				isDeleted = AdmittedThroughHandler.getInstance().deleteAdmittedThrough(admThroughId, false, admittedThroughForm);
			}
		} catch (Exception e) {
			log.error("error in delete admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admittedThroughForm.setErrorMessage(msg);
				admittedThroughForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setAdmittedThroughToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.admthrough.deletesuccess", admittedThroughForm.getAdmittedThrough());
			messages.add("messages", message);
			saveMessages(request, messages);
			admittedThroughForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.admthrough.deletefailure", admittedThroughForm.getAdmittedThrough()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
	}

	/**
	 * setting admittedThroughList to request for UI display
	 * @param request
	 * @throws Exception
	 */
	public void setAdmittedThroughToRequest(HttpServletRequest request) throws Exception {
		List<AdmittedThroughTO> admittedThroughList = AdmittedThroughHandler.getInstance().getAdmittedThrough();
		request.setAttribute("admittedThroughList", admittedThroughList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method Reactivate the Admitted Through.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward activateAdmittedThrough(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
												 HttpServletResponse response) throws Exception {

		AdmittedThroughForm admittedThroughForm = (AdmittedThroughForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (admittedThroughForm.getDuplId() != 0) {
				int admThroughId = admittedThroughForm.getDuplId();  //setting id for activate
				setUserId(request, admittedThroughForm);  //setting userId for updating last changed details
				isActivated = AdmittedThroughHandler.getInstance().deleteAdmittedThrough(admThroughId, true, admittedThroughForm);
				//deleteAdmittedThrough(admThroughId, true, admittedThroughForm) is using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.ADMITTED_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setAdmittedThroughToRequest(request);
		
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.ADMITTED_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.ADMITTED_THROUGH_ENTRY);
	}
	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher m = p.matcher(name);
        result = m.find();
        return result;

	}
}
