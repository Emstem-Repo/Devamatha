package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.UGCoursesForm;
import com.kp.cms.handlers.admin.UGCoursesHandler;
import com.kp.cms.to.admin.UGCoursesTO;

public class UGCoursesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UGCoursesAction.class);

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
	public ActionForward initUGCourses(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		UGCoursesForm ugCoursesForm = (UGCoursesForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","UG Courses");
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setUGCoursesToRequest(ugCoursesForm);  //setting admitted through list to request for UI display
		}

		catch (Exception e) {
			log.error("error in initUGCourses...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				ugCoursesForm.setErrorMessage(msg);
				ugCoursesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
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
	public ActionForward addUGCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		UGCoursesForm ugCoursesForm = (UGCoursesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = ugCoursesForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				if((ugCoursesForm.getUgCoursesName().trim()).isEmpty()){
					ugCoursesForm.setUgCoursesName(null);
				}
				setUGCoursesToRequest(ugCoursesForm);
				return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
			}
			
			boolean isSpcl=nameValidate(ugCoursesForm.getUgCoursesName().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			
			
			setUserId(request, ugCoursesForm); //setting user is to update last changed details
			isAdded = UGCoursesHandler.getInstance().addUGCourses(ugCoursesForm, "Add"); //Add parameter used to identify add/edit

			setUGCoursesToRequest(ugCoursesForm);

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.name.exists"));
			saveErrors(request, errors);
			setUGCoursesToRequest(ugCoursesForm);
			return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setUGCoursesToRequest(ugCoursesForm);
			return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of ug courses page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				ugCoursesForm.setErrorMessage(msg);
				ugCoursesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.ugcourses.addsuccess",ugCoursesForm.getUgCoursesName());
			messages.add("messages", message);
			saveMessages(request, messages);
			ugCoursesForm.reset(mapping, request);
			setUGCoursesToRequest(ugCoursesForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.addfailure",ugCoursesForm.getUgCoursesName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);

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
	public ActionForward updateUGCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		UGCoursesForm ugCoursesForm = (UGCoursesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = ugCoursesForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setUGCoursesToRequest(ugCoursesForm);
				//if space is entered then assigning null, else blank space will get updated in the table
				if((ugCoursesForm.getUgCoursesName().trim()).isEmpty()){       
					ugCoursesForm.setUgCoursesName(null);
				}
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
			}
			
			boolean isSpcl=nameValidate(ugCoursesForm.getUgCoursesName().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setUGCoursesToRequest(ugCoursesForm);
				if((ugCoursesForm.getUgCoursesName().trim()).isEmpty()){
					ugCoursesForm.setUgCoursesName(null);
				}
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
			}
			
			setUserId(request, ugCoursesForm);  //setting user for updating last changed details
			isUpdated = UGCoursesHandler.getInstance().addUGCourses(ugCoursesForm, "Edit");
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.name.exists"));
			saveErrors(request, errors);
			setUGCoursesToRequest(ugCoursesForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.UG_COURSES_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setUGCoursesToRequest(ugCoursesForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
		} catch (Exception e) {
			log.error("error in update ug courses page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				ugCoursesForm.setErrorMessage(msg);
				ugCoursesForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.ugcourses.updatesuccess",
					ugCoursesForm.getUgCoursesName());
			messages.add("messages", message);
			saveMessages(request, messages);
			ugCoursesForm.reset(mapping, request);
			setUGCoursesToRequest(ugCoursesForm);

		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.updatefailure",
					ugCoursesForm.getUgCoursesName()));
			saveErrors(request, errors);
		}
		request.setAttribute("Operation", "add");
		return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
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
	public ActionForward deleteUGCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		UGCoursesForm ugCoursesForm = (UGCoursesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (ugCoursesForm.getUgCoursesId() != 0) {
				int ugCoursesId = ugCoursesForm.getUgCoursesId();
				setUserId(request, ugCoursesForm); //setting user id for updating last changed details
				isDeleted = UGCoursesHandler.getInstance().deleteUGCourses(ugCoursesId, false, ugCoursesForm);
			}
		} catch (Exception e) {
			log.error("error in delete ug courses page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				ugCoursesForm.setErrorMessage(msg);
				ugCoursesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.ugcourses.deletesuccess", ugCoursesForm.getUgCoursesName());
			messages.add("messages", message);
			saveMessages(request, messages);
			ugCoursesForm.reset(mapping, request);
			setUGCoursesToRequest(ugCoursesForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.ugcourses.deletefailure", ugCoursesForm.getUgCoursesName()));
			saveErrors(request, errors);
			setUGCoursesToRequest(ugCoursesForm);

		}
		return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
	}

	/**
	 * setting admittedThroughList to request for UI display
	 * @param ugCoursesForm 
	 * @param request
	 * @throws Exception
	 */
	public void setUGCoursesToRequest(UGCoursesForm ugCoursesForm) throws Exception {
		List<UGCoursesTO> ugCoursesList = UGCoursesHandler.getInstance().getUGCourses();
		ugCoursesForm.setUgCoursesList(ugCoursesList);
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
	public ActionForward activateUGCourses(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
												 HttpServletResponse response) throws Exception {

		UGCoursesForm ugCoursesForm = (UGCoursesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (ugCoursesForm.getDuplId() != 0) {
				int ugcoursesId = ugCoursesForm.getDuplId();  //setting id for activate
				setUserId(request, ugCoursesForm);  //setting userId for updating last changed details
				isActivated = UGCoursesHandler.getInstance().deleteUGCourses(ugcoursesId, true, ugCoursesForm);
				//deleteAdmittedThrough(admThroughId, true, admittedThroughForm) is using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.UG_COURSES_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setUGCoursesToRequest(ugCoursesForm);
		
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.UG_COURSES_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.UG_COURSES_ENTRY);
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
