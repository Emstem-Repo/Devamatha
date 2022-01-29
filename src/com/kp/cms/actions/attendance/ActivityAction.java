package com.kp.cms.actions.attendance;

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
import com.kp.cms.forms.attendance.ActivityForm;
import com.kp.cms.handlers.attendance.ActivityHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;

/**
 * 
 * @author
 */
@SuppressWarnings("deprecation")
public class ActivityAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ActivityAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set ActivitiesList having ActivityTO objects to request, forward to
	 *         ActivityEntry
	 * @throws Exception
	 */
	public ActionForward initActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
						HttpServletResponse response) throws Exception {

		log.debug("Entering initActivity");
		ActivityForm activityForm = (ActivityForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setAttendanceTypeListToRequest(request);  //setting attendanceTypeList to request for populating in attendance type combo
			setActivityListToRequest(request);  //setting activity list to request for UI display
		} catch (Exception e) {
			log.error("error in initActivity...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				activityForm.setErrorMessage(msg);
				activityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initActivity ");

		return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
	}

	/**
	 *method used to add new activity to table 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addActivity Action");
		ActivityForm activityForm = (ActivityForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = activityForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAttendanceTypeListToRequest(request);
				setActivityListToRequest(request);
				//empty should not get saved in the table
				if(activityForm.getName().trim().isEmpty()){
					activityForm.setName(null);
				}
				return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
			}
			setUserId(request, activityForm);  //setting userId for updating last changed details
			isAdded = ActivityHandler.getInstance().addActivity(activityForm);
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attn.activity.name.exists",activityForm.getName()));
			saveErrors(request, errors);
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
			return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
			return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of institute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				activityForm.setErrorMessage(msg);
				activityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.attn.activity.addsuccess", activityForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			activityForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.attn.activity.addfailure", activityForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addActivity Action");
		return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
	}

	/**
	 * used for deleting activity from table
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
		ActivityForm activityForm = (ActivityForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (activityForm.getId() != null) {
				int actId = Integer.parseInt(activityForm.getId());
				setUserId(request, activityForm);
				isDeleted = ActivityHandler.getInstance().deleteActivity(actId, false, activityForm.getUserId());
			}
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
		} catch (Exception e) {
			log.error("error in deleteactivity page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				activityForm.setErrorMessage(msg);
				activityForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.attn.activity.deletesuccess", activityForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			activityForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.attn.activity.deletefailure", activityForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("leaving delete Institute Action");
		return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
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
		ActivityForm activityForm = (ActivityForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = activityForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setAttendanceTypeListToRequest(request);
				setActivityListToRequest(request);
				//empty name should not get saved in the table
				if((activityForm.getName().trim()).isEmpty()){
					activityForm.setName(null);
				}
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
			}
			if (activityForm.getId() != null) {
				setUserId(request, activityForm);
				isUpdated = ActivityHandler.getInstance().updateActivity(activityForm); 
			}
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attn.activity.name.exists"));
			saveErrors(request, errors);
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setAttendanceTypeListToRequest(request);
			setActivityListToRequest(request);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
		} catch (Exception e) {
			log.error("error in update activity page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				activityForm.setErrorMessage(msg);
				activityForm.setErrorStack(e.getMessage());
				request.setAttribute("activityOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.attn.activity.updatesuccess",	activityForm.getName());
			messages.add("messages", message);
			activityForm.reset(mapping, request);
			saveMessages(request, messages);
			activityForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.attn.activity.updatefailure", activityForm.getName()));
			saveErrors(request, errors);
			request.setAttribute("activityOperation", "edit");
			return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
		}
		log.debug("leaving update updateActivity action");
		request.setAttribute("activityOperation", "add");
		return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the  attendanceType list to Request useful in
	 *            populating in attendanceType selection.
	 */
	public void setAttendanceTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setAttendanceTypeListToRequest");
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		request.setAttribute("attendanceTypeList", attendanceTypeList);
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
		List<ActivityTO> activityList = ActivityHandler.getInstance().getActivity(); 
		request.setAttribute("activityList", activityList);
	}

	/**
	 * method for activate activity
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
		ActivityForm activityForm = (ActivityForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (activityForm.getDuplActId() != 0) {
				int actId = activityForm.getDuplActId();
				setUserId(request, activityForm);
				isActivated = ActivityHandler.getInstance().deleteActivity(actId, true, activityForm.getUserId()) ; 
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.ACTIVITY_ENTRY_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setAttendanceTypeListToRequest(request);
		setActivityListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.ACTIVITY_ENTRY_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateActivity");
		return mapping.findForward(CMSConstants.ACTIVITY_ENTRY);
	}

}
