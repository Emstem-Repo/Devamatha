package com.kp.cms.actions.admission;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ApplicationReportForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admission.ApplicationReportHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.ApplicationReportTO;

/**
 * 
 * @author kshirod.k
 * An action class for Application Report
 *
 */

public class ApplicationReportAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ApplicationReportAction.class);
	
	/**
	 * Initializes application report
	 */
	public ActionForward initapplicationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into ApplicationReportAction of initapplicationReport");
		ApplicationReportForm applicationReportForm=(ApplicationReportForm)form;
		try {
			assignListToForm(applicationReportForm);
			HttpSession session=request.getSession(false);
			if(session!=null){
			session.removeAttribute("applicationList");
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				log.error("Error occured in initapplicationReport of Application Report Action");
				String msg = super.handleApplicationException(e);
				applicationReportForm.setErrorMessage(msg);
				applicationReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		log.info("Entering into ApplicationReportAction of initapplicationReport");
		return mapping.findForward(CMSConstants.INIT_APPLICATIONREPORT);
	}
	
	
	/**
	 * Searches for issued applications and received applications
	 */
	
	@SuppressWarnings("deprecation")
	public ActionForward searchIssuedandReceivedApplications(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into ApplicationReportAction of searchIssuedandReceivedApplications");
		HttpSession session = request.getSession(false);
		if(session.getAttribute("applicationList")==null){
			ApplicationReportForm applicationReportForm=(ApplicationReportForm)form;
			 ActionErrors errors = applicationReportForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					/**
					 * Get the course and year from UI and pass them to handler for getting the issued and received applications
					 */
					int courseId=Integer.parseInt(applicationReportForm.getCourseId());
					int year = Integer.parseInt(applicationReportForm.getYear());
					List<ApplicationReportTO> applicationList= ApplicationReportHandler.getInstance().getIssuedandReceivedApplications(courseId, year);
					//If list is not null then keep in session scope
					if(!applicationList.isEmpty()){			
					session.setAttribute("applicationList",applicationList );	
					applicationReportForm.setApplicationList(applicationList);
					}
					assignListToForm(applicationReportForm);
					return mapping.findForward(CMSConstants.VIEW_APPLICATIONREPORT);
				}
			} catch (Exception e) {
				if (e instanceof ApplicationException) {
					log.error("Error occured in searchIssuedandReceivedApplications of Application Report Action");
					String msg = super.handleApplicationException(e);
					applicationReportForm.setErrorMessage(msg);
					applicationReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}else {
					throw e;
				}
			}
			saveErrors(request, errors);
			assignListToForm(applicationReportForm);
		}	
		log.info("Leaving into ApplicationReportAction of searchIssuedandReceivedApplications");
		return mapping.findForward(CMSConstants.INIT_APPLICATIONREPORT);
	}

	/**
	 * 
	 * @param form
	 * @throws Exception
	 * Returns all the active courses
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into ApplicationReportAction of assignListToForm");
		ApplicationReportForm applicationReportForm=(ApplicationReportForm)form;
		try {
			List<CourseTO> courseList = CourseHandler.getInstance()
					.getActiveCourses();
			if (courseList != null && !courseList.isEmpty()) {
				applicationReportForm.setCourseList(courseList);
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				log.error("Error occured in assignListToForm of Application Report Action");
				String msg = super.handleApplicationException(e);
				applicationReportForm.setErrorMessage(msg);
				applicationReportForm.setErrorStack(e.getMessage());
			}else {
				throw e;
			}
		}
		log.info("End of ApplicationReportAction of assignListToForm");
	}
	
}
