package com.kp.cms.actions.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.ApplicationReceivedForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.reports.ApplicationReceivedHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.reports.ApplicationReceivedTO;

public class ApplicationReceivedAction extends BaseDispatchAction{

private static final Log log = LogFactory.getLog(ApplicationReceivedAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns initializes application received report with the required fields
	 * @throws Exception
	 */
	public ActionForward initApplicationReceived(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initApplicationReceived of ApplicationReceivedAction");
		ApplicationReceivedForm receivedForm = (ApplicationReceivedForm)form;
		try {
			assignListToForm(receivedForm);
			receivedForm.clear();
			HttpSession session=request.getSession(false);
			if(session.getAttribute("applicationList")!=null){
				session.removeAttribute("applicationList");
			}
		} catch (Exception e) {			
				log.error("Error occured in initapplicationReport of ApplicationReportAction");
				String msg = super.handleApplicationException(e);
				receivedForm.setErrorMessage(msg);
				receivedForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initApplicationReceived of ApplicationReceivedAction");
		return mapping.findForward(CMSConstants.INIT_APPLICATION_RECEIVED);
	}
	/**
	 * 
	 * @param form
	 * @throws Exception
	 * Returns all the active courses
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into assignListToForm of ApplicationReceivedAction");
		ApplicationReceivedForm receivedForm = (ApplicationReceivedForm)form;
		try {
			List<CourseTO> courseList = CourseHandler.getInstance()
					.getActiveCourses();
				receivedForm.setCourseList(courseList);
		} catch (Exception e) {
				log.error("Error occured in assignListToForm of ApplicationReceivedAction");
				String msg = super.handleApplicationException(e);
				receivedForm.setErrorMessage(msg);
				receivedForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving into assignListToForm of ApplicationReceivedAction");
	}
		/**
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @returns received applications based on the selection either online/offline
		 * @throws Exception
		 */
		public ActionForward searchReceivedApplications(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			log.info("Entering into searchReceivedApplications of ApplicationReceivedAction");
			ApplicationReceivedForm receivedForm = (ApplicationReceivedForm)form;
			HttpSession session=request.getSession(false);
			if(session.getAttribute("applicationList")==null){
				try {
					 ActionErrors errors = receivedForm.validate(mapping, request);
					if (errors.isEmpty()) {
						List<ApplicationReceivedTO> applicationList = ApplicationReceivedHandler.getInstance().getReceivedApplications(receivedForm);
						session.setAttribute("applicationList", applicationList);
					} else {
						addErrors(request, errors);
						assignListToForm(receivedForm);
						return mapping.findForward(CMSConstants.INIT_APPLICATION_RECEIVED);
					}
				} catch (Exception e) {
					log.error("Error occured in searchReceivedApplications of ApplicationReceivedAction");
					String msg = super.handleApplicationException(e);
					receivedForm.setErrorMessage(msg);
					receivedForm.setErrorStack(e.getMessage());
				}
			}
			log.info("Leaving into searchReceivedApplications of ApplicationReceivedAction");
			return mapping.findForward(CMSConstants.APPLICATION_RECEIVED_RESULT);
		}
}
