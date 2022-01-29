package com.kp.cms.actions.hostel;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelerDatabaseForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelerDatabaseHandler;
import com.kp.cms.handlers.hostel.RequisitionHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelerDataBaseResultsTO;
import com.kp.cms.to.hostel.HostelerDatabaseTO;
import com.kp.cms.to.hostel.RequisitionsTo;

@SuppressWarnings("deprecation")
public class HostelerDatabaseAction extends BaseDispatchAction{
	
	private static Log log = LogFactory.getLog(HostelerDatabaseAction.class);
	
	/**
	 * Used to initialize HostelerDatabase Screen
	 */
	
	public ActionForward initHostelerDatabase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initHostelerDatabase of HostelerDatabaseAction");
		HostelerDatabaseForm databaseForm = (HostelerDatabaseForm)form;
		try {
			databaseForm.clear();
			setHostelEntryDetailsToRequest(request);
		} catch (Exception e) {
			log.error("Error occured in initHostelerDatabase of HostelerDatabaseAction", e);
			String msg = super.handleApplicationException(e);
			databaseForm.setErrorMessage(msg);
			databaseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initHostelerDatabase of HostelerDatabaseAction");
		return mapping.findForward(CMSConstants.HOSTEL_INIT_HOSTLER_DATABASE);
	}
	
	/**
	 * Used to search the records 
	 */

	public ActionForward submitHostelerDatabase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitHostelerDatabase of HostelerDatabaseAction");
		HostelerDatabaseForm databaseForm = (HostelerDatabaseForm)form;
		 ActionErrors errors = databaseForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				List<HostelerDatabaseTO> studentList = HostelerDatabaseHandler.getInstance().submitHostelerDatabase(databaseForm);
				if(studentList!=null && !studentList.isEmpty()){
					databaseForm.setStudentList(studentList);
					return mapping.findForward(CMSConstants.HOSTEL_HOSTLER_DATABASE_RESULT);
				}
				else{
					databaseForm.clear();
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.noresultsfound"));
				}
			}
		} catch (Exception e) {
			log.error("Error occured in submitHostelerDatabase of HostelerDatabaseAction", e);
			String msg = super.handleApplicationException(e);
			databaseForm.setErrorMessage(msg);
			databaseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		addErrors(request, errors);
		setHostelEntryDetailsToRequest(request);
		log.info("Leaving into submitHostelerDatabase of HostelerDatabaseAction");
		return mapping.findForward(CMSConstants.HOSTEL_INIT_HOSTLER_DATABASE);
	}
	
	/**
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gettHostelerDatabase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitHostelerDatabase of HostelerDatabaseAction");
		String hlAppId=request.getParameter("applicationNumber");
		HostelerDataBaseResultsTO studentDetails = HostelerDatabaseHandler.getInstance().studentDetailsToShow(hlAppId,request);
		request.setAttribute("studentDetails", studentDetails);
		log.info("Leaving into submitHostelerDatabase of HostelerDatabaseAction");
		return mapping.findForward(CMSConstants.POPUP_STUDENTS_DATABASE);
	}
	
}
