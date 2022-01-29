package com.kp.cms.actions.admission;

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
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CourseChangeForm;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.CourseChangeHandler;
import com.kp.cms.to.admission.AdmApplnTO;

public class CourseChangeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CourseChangeAction.class);	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCourseChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initCourseChange..");
		CourseChangeForm courseChangeForm = (CourseChangeForm) form;
		setUserId(request, courseChangeForm);
		log.info("exit initCourseChange..");
		return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_PAGE);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Grade record
	 * @return to mapping gradesEntry
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward updateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateCourse Action");
		CourseChangeForm courseChangeForm = (CourseChangeForm) form;
		ActionMessages messages = new ActionMessages();
		
		ActionErrors errors = courseChangeForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				request.setAttribute("courseMap", CourseChangeHandler.getInstance().getCourseByProgramType(courseChangeForm));
				//space should not get added in the table
				return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_UPDATE);
			}
			setUserId(request, courseChangeForm);
		
			isAdded = CourseChangeHandler.getInstance().updateCourse(courseChangeForm);
			request.setAttribute("courseMap", CourseChangeHandler.getInstance().getCourseByProgramType(courseChangeForm));
		} catch (Exception e) {
			log.error("error in final submit of updateCourse...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseChangeForm.setErrorMessage(msg);
				courseChangeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.course.change.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			clear(courseChangeForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.course.change.update.failure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving updateCourse Action");
		return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_PAGE);
	}
	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Grade record
	 * @return to mapping gradesEntry
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward displayCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside displayCourse Action");
		CourseChangeForm courseChangeForm = (CourseChangeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = courseChangeForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
//				request.setAttribute("courseList", CourseChangeHandler.getInstance().getCourseByProgramType(courseChangeForm));
				//space should not get added in the table
				return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_PAGE);
			}
			
			String applicationNumber = courseChangeForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(courseChangeForm.getApplicationYear());
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear,false);

			if( applicantDetails == null){
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_PAGE);
			}			
			
			setUserId(request, courseChangeForm);
			CourseChangeHandler.getInstance().assignCourseDetails(courseChangeForm);
			request.setAttribute("courseMap", CourseChangeHandler.getInstance().getCourseByProgramType(courseChangeForm));
		} catch (Exception e) {
			log.error("error in final submit of updateCourse...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseChangeForm.setErrorMessage(msg);
				courseChangeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving displayCourse Action");
		return mapping.findForward(CMSConstants.INIT_COURSE_CHANGE_UPDATE);
	}
		
	
	
	
	public void clear(CourseChangeForm courseChangeForm){
		courseChangeForm.setFromCourse(null);
		courseChangeForm.setCourse(null);
		courseChangeForm.setAcademicYear(null);
		courseChangeForm.setApplicationNumber(null);
		courseChangeForm.setModifiedBy(null);
		courseChangeForm.setLastModifiedDate(null);
		
	}
	
}
