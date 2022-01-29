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
import org.apache.struts.action.ActionMessages;
import com.kp.cms.to.hostel.HostelStudentExtractTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelStudentExtractForm;
import com.kp.cms.handlers.hostel.HostelStudentExtractHandler;


public class HostelStudentExtractAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(HostelStudentExtractAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentExtractDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initStudentExtractDetails of HostelStudentExtractAction");
		HostelStudentExtractForm hostelStudentExtractForm = (HostelStudentExtractForm) form;
		try {
			hostelStudentExtractForm.clearMyFields();
			
		} catch (Exception e) {
			log.error("error in initHostelDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelStudentExtractForm.setErrorMessage(msg);
				hostelStudentExtractForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initStudentExtractDetails of HostelStudentExtractAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_EXTRACT);
	}	

	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	public ActionForward getStudentExtractDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelReqReportDetails of HostelRequisitionReportAction");
		HostelStudentExtractForm hostelStudentExtractForm = (HostelStudentExtractForm) form;
		 ActionErrors errors = hostelStudentExtractForm.validate(mapping, request);
		validateStudentExtract(hostelStudentExtractForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelStudentExtractForm);
				List<HostelStudentExtractTo> hostelStudentExtractTo = HostelStudentExtractHandler.getInstance().getStudentExtractDetails(hostelStudentExtractForm);
				hostelStudentExtractForm.setHostelStudExtractToList(hostelStudentExtractTo);
				if(hostelStudentExtractTo.size()==0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelStudentExtractForm.clearMyFields();
					return mapping.findForward(CMSConstants.INIT_HOSTEL_EXTRACT);
				} 
				else{
					
					return mapping.findForward(CMSConstants.SECOND_HOSTEL_EXTRACT);
				}
			}
			else{
				saveErrors(request, errors);
				hostelStudentExtractForm.clearMyFields();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_EXTRACT);
			}
		} catch (Exception e) {
			log.error("Error in getHostelReqReportDetails");
				String msg = super.handleApplicationException(e);
				hostelStudentExtractForm.setErrorMessage(msg);
				hostelStudentExtractForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		//log.info("Exiting getHostelReqReportDetails of HostelRequisitionReportAction");
		//return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
	}

	
	/**
	 * common validation for input jsp
	 * @param hostelCheckinForm
	 * @param errors
	 */
	private void validateStudentExtract(HostelStudentExtractForm hostelStudentExtractForm,ActionErrors errors) {
		
		if(hostelStudentExtractForm.getFromDate()!= null && hostelStudentExtractForm.getFromDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_FROM_DATE_REQUIRED));
		}else if(hostelStudentExtractForm.getToDate()!= null && hostelStudentExtractForm.getToDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_TO_DATE_REQUIRED));
		}else if(CommonUtil.ConvertStringToSQLDate(hostelStudentExtractForm.getFromDate()).after(CommonUtil.ConvertStringToSQLDate(hostelStudentExtractForm.getToDate()))){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TO_DATE_GREATER_TO_FROM_DATE));
		}
	}
	
	
}
