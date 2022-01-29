package com.kp.cms.actions.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.AdmissionIncompleteForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.AdmissionIncompleteHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

public class AdmissionIncompleteAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmissionIncompleteAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns initializes Incomplete Admission Report
	 * @throws Exception
	 */
	public ActionForward initAdmissionIncomplete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initAdmissionIncomplete. of AdmissionIncompleteAction");	
		AdmissionIncompleteForm incompleteForm = (AdmissionIncompleteForm)form;
		try {
			setRequiredDataToForm(incompleteForm, request);
			incompleteForm.clear();
			request.removeAttribute("courseMap");
			HttpSession session = request.getSession(false);
			if(session.getAttribute("incompleteStudents")!=null){
				session.removeAttribute("incompleteStudents");
			}
		} catch (Exception e) {
			log.error("Error occured in initFreshersList of AdmissionIncompleteAction", e);
			String msg = super.handleApplicationException(e);
			incompleteForm.setErrorMessage(msg);
			incompleteForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAdmissionIncomplete. of AdmissionIncompleteAction");	
		return mapping.findForward(CMSConstants.INIT_INCOMPLETE_ADMISSION);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns incomplete admission student lists
	 * @throws Exception
	 */
	public ActionForward submitAdmissionIncomplete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitAdmissionIncomplete. of AdmissionIncompleteAction");
		AdmissionIncompleteForm incompleteForm = (AdmissionIncompleteForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("incompleteStudents")==null){
			try {
				List<StudentTO> incompleteStudents = AdmissionIncompleteHandler.getInstance().getIncompleteAdmssionStudents(incompleteForm);
				session.setAttribute("incompleteStudents", incompleteStudents);
			}catch (Exception e) {
				log.error("Error occured in submitAdmissionIncomplete of AdmissionIncompleteAction", e);
				String msg = super.handleApplicationException(e);
				incompleteForm.setErrorMessage(msg);
				incompleteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving into submitAdmissionIncomplete. of AdmissionIncompleteAction");	
		return mapping.findForward(CMSConstants.INCOMPLETE_ADMISSION_RESULT);
	}
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(AdmissionIncompleteForm incompleteForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of AdmissionIncompleteAction");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);			
			Map<Integer,String> courseMap = new HashMap<Integer,String>();
			//Setting courseMap to Request
			if(incompleteForm.getProgramId()!=null && !incompleteForm.getProgramId().isEmpty()){
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(incompleteForm.getProgramId()));
			}
			request.setAttribute("courseMap", courseMap);
			log.info("Exit setRequiredDataToForm.AdmissionIncompleteAction");	
	}
}
