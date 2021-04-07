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
import com.kp.cms.forms.admin.ExtraCurricularActivitiesForm;
import com.kp.cms.handlers.admin.ExtraCurricularActivitiesHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.OrganizationTO;

@SuppressWarnings("deprecation")
public class ExtraCurricularActivitiesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExtraCurricularActivitiesAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set ActivitiesList having ExtracurricularActivityTO objects to request, forward to
	 *         ExtraCurricularActivityEntry
	 * @throws Exception
	 */
	public ActionForward initActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
						HttpServletResponse response) throws Exception {

		log.debug("Entering initActivity");
		ExtraCurricularActivitiesForm extraCurricularActivitiesForm = (ExtraCurricularActivitiesForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setOrganisationListToRequest(request); //setting organizatioList to request for UI display
			setActivityListToRequest(request);
		} catch (Exception e) {
			log.error("error init institute page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extraCurricularActivitiesForm.setErrorMessage(msg);
				extraCurricularActivitiesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initInstitute ");

		return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
	}

	/**
	 * this will add a new record to extracurricularactivities table while submitting the form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to extracurricular activity entry
	 * @throws Exception
	 */
	public ActionForward addActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addActivity Action");
		ExtraCurricularActivitiesForm extraCurricularActivitiesForm = (ExtraCurricularActivitiesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = extraCurricularActivitiesForm.validate(mapping, request);
		boolean isAdded;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setOrganisationListToRequest(request);
				setActivityListToRequest(request);
				//empty space should not allow in the table. so if space is entered then assigning null
				if(extraCurricularActivitiesForm.getName().trim().isEmpty()){ 
					extraCurricularActivitiesForm.setName(null);
				}
				return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
			}
			setUserId(request, extraCurricularActivitiesForm);
			isAdded = ExtraCurricularActivitiesHandler.getInstance().addActivity(extraCurricularActivitiesForm);
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.extra.cur.act.name.exists",extraCurricularActivitiesForm.getName()));
			saveErrors(request, errors);
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
			return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
			return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of institute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extraCurricularActivitiesForm.setErrorMessage(msg);
				extraCurricularActivitiesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.extra.cur.act.addsuccess", extraCurricularActivitiesForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			extraCurricularActivitiesForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.extra.cur.act.addfailure", extraCurricularActivitiesForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addActivity Action");
		return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward This action method will called when particular
	 *         Activity name need to be deleted based on the id.
	 * @throws Exception
	 */
	
	public ActionForward deleteActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside delete deleteActivity Action");
		ExtraCurricularActivitiesForm extraCurricularActivitiesForm = (ExtraCurricularActivitiesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (extraCurricularActivitiesForm.getId() != null) {
				int actId = Integer.parseInt(extraCurricularActivitiesForm.getId());
				setUserId(request, extraCurricularActivitiesForm);  //used to update last changed details
				isDeleted = ExtraCurricularActivitiesHandler.getInstance().deleteActivity(actId, false, extraCurricularActivitiesForm.getUserId()); 
			}
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
		} catch (Exception e) {
			log.error("error in delete extra curricular activity page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extraCurricularActivitiesForm.setErrorMessage(msg);
				extraCurricularActivitiesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.extra.cur.act.deletesuccess", extraCurricularActivitiesForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			extraCurricularActivitiesForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.extra.cur.act.deletefailure", extraCurricularActivitiesForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("leaving delete Institute Action");
		return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This action method will called when particular Activity need to be
	 *         updated based on the id.
	 * @throws Exception
	 */
	public ActionForward updateActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside update updateActivity Action");
		ExtraCurricularActivitiesForm extraCurricularActivitiesForm = (ExtraCurricularActivitiesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = extraCurricularActivitiesForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setOrganisationListToRequest(request);
				setActivityListToRequest(request);
				//empty space should not allow in the table. so if space is entered then assigning null
				if(extraCurricularActivitiesForm.getName().trim().isEmpty()){
					extraCurricularActivitiesForm.setName(null);
				}
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
			}
			if (extraCurricularActivitiesForm.getId() != null) {
				setUserId(request, extraCurricularActivitiesForm);
				isUpdated = ExtraCurricularActivitiesHandler.getInstance().updateActivity(extraCurricularActivitiesForm);
			}
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.extra.cur.act.name.exists"));
			saveErrors(request, errors);
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setOrganisationListToRequest(request);
			setActivityListToRequest(request);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
		} catch (Exception e) {
			log.error("error in update extra curricular activity page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extraCurricularActivitiesForm.setErrorMessage(msg);
				extraCurricularActivitiesForm.setErrorStack(e.getMessage());
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.admin.extra.cur.act.updatesuccess",	extraCurricularActivitiesForm.getName());
			messages.add("messages", message);
			extraCurricularActivitiesForm.reset(mapping, request);
			saveMessages(request, messages);
			extraCurricularActivitiesForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.admin.extra.cur.act.updatefailure", extraCurricularActivitiesForm.getName()));
			saveErrors(request, errors);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
		}
		log.debug("leaving update updateActivity action");
		request.setAttribute("activityOperation", "add");
		return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the  organization list to Request useful in
	 *            populating in organization selection.
	 */
	public void setOrganisationListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setOrganisationListToRequest");
		List<OrganizationTO> organizationList = OrganizationHandler.getInstance().getOrganizationDetails(); 
		request.setAttribute("organizationList", organizationList);
		log.debug("leaving setOrganisationListToRequest");
	}

	/**
	 * 
	 * @param request
	 *            This method sets the activityList to Request used to display
	 *            activity record on UI.
	 * @throws Exception
	 */
	public void setActivityListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setActivityListToRequest");
		List<ExtracurricularActivityTO> activityList = ExtraCurricularActivitiesHandler.getInstance().getActivity(); 
		request.setAttribute("activityList", activityList);
		log.debug("leaving setActivityListToRequest");
	}

	/**
	 * this method will activate extracurricular activity record
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
											HttpServletResponse response) throws Exception {

		log.debug("inside activateActivity");
		ExtraCurricularActivitiesForm activitiesForm = (ExtraCurricularActivitiesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (activitiesForm.getDuplActId() != 0) {
				int actId = activitiesForm.getDuplActId();
				setUserId(request, activitiesForm);
				isActivated = ExtraCurricularActivitiesHandler.getInstance().deleteActivity(actId, true, activitiesForm.getUserId()) ;
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_ENTRY_FAILURE));
			saveErrors(request, errors);
		}
		setOrganisationListToRequest(request);
		setActivityListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.ACTIVITY_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateActivity");
		return mapping.findForward(CMSConstants.EXTRA_CURRICULAR_ACTIVITY_ENTRY);
	}

}
