package com.kp.cms.actions.reports;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.OfflineIssueForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.reports.OfflineIssueHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.to.reports.OfflineIssueTO;

public class OfflineIssueReportAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(OfflineIssueReportAction.class);
	
	/**
	 * Setting the data for displaying the OfflineIssueReport.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOfflineIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initOfflineIssue of OfflineIssue Report Action");
		OfflineIssueForm offlineIssueForm = (OfflineIssueForm)form;
		try {
			assignListToForm(offlineIssueForm);
		} catch (Exception e) {
			log.error("Error occured at initOfflineDetails of OfflineDetailsAction",e);
				String msg = super.handleApplicationException(e);
				offlineIssueForm.setErrorMessage(msg);
				offlineIssueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from initOfflineIssue of OfflineIssue Report Action");
		offlineIssueForm.clearWhileReset();
		return mapping.findForward(CMSConstants.OFFLINE_Report);
	}
	/**
	 * Setting the List of courses to the OfflineIssueReport.jsp
	 * @param form
	 * @throws Exception
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into assignListToForm of OfflineIssueReportAction");
		OfflineIssueForm offlineIssueForm = (OfflineIssueForm)form;
		List<CourseTO> courseList=CourseHandler.getInstance().getActiveCourses();
		if(courseList!=null && !courseList.isEmpty()){
			offlineIssueForm.setCourseList(courseList);
		}
		log.info("Leaving from assignListToForm of OfflineIssueReportAction");
	}
	
	/**
	 * getting the list of offline admission students
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOfflineList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submit student vehicle  type action");	
		OfflineIssueForm offlineIssueForm = (OfflineIssueForm)form;
		 ActionErrors errors = offlineIssueForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);		
			return mapping.findForward(CMSConstants.OFFLINE_Report);
		}
		try {
			OfflineIssueHandler offlineIssueHandler=OfflineIssueHandler.getInstance();
			List<OfflineIssueTO> StudentOfflineList=offlineIssueHandler.getAllOfflineDetails(offlineIssueForm);
			if (StudentOfflineList.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				assignListToForm(offlineIssueForm);
				log.info("Exit offline list In Offline Issue Report Action");
				return mapping.findForward(CMSConstants.OFFLINE_Report);
			} 
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				offlineIssueForm.setOrganizationName(orgTO.getOrganizationName());
			}
			request.getSession().setAttribute("StudentOfflineList", StudentOfflineList);
			
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			offlineIssueForm.setErrorMessage(msg);
			offlineIssueForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.OFFLINE_Report_Result);
	}
}
