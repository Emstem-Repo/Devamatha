package com.kp.cms.actions.hostel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.handlers.hostel.HostelCheckinHandler;
import com.kp.cms.handlers.hostel.HostelReqReportHandler;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelRequisitionReportAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(HostelRequisitionReportAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initHostelDetails of HostelRequisitionReportAction");
		HostelReqReportForm hostelReqReportForm = (HostelReqReportForm) form;
		try {
			hostelReqReportForm.clearMyFields();
			getHostelEntries(request);
		} catch (Exception e) {
			log.error("error in initHostelDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelReqReportForm.setErrorMessage(msg);
				hostelReqReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initHostelDetails of HostelRequisitionReportAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
	}	
	
	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	public ActionForward getReqReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelReqReportDetails of HostelRequisitionReportAction");
		HostelReqReportForm hostelReqReportForm = (HostelReqReportForm) form;
		 ActionErrors errors = hostelReqReportForm.validate(mapping, request);
		validateReqReport(hostelReqReportForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelReqReportForm);
				List<HostelReqReportTo> hostelReqReportToList = HostelReqReportHandler.getInstance().getReqReportDetails(hostelReqReportForm);
				hostelReqReportForm.setHostelReqReportToList(hostelReqReportToList);
				//request.getSession().setAttribute("hostelReqReportDetailsTo", hostelReqReportToList);
				if(hostelReqReportToList.size()==0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelReqReportForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
				} 
				/*else if(hostelReqReportForm.getStatusType().equalsIgnoreCase("CheckedIn")){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_CHECKIN_PERSON_ALREDY_CHECKIN));
					saveErrors(request, errors);
					hostelReqReportForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
				}*/
							
				else{
					//getHostelEntries(request);
					return mapping.findForward(CMSConstants.SECOND_HOSTEL_REQREPORT);
				//	setAllDataToSecondPage(hostelReqReportForm,hostelApplicantDetails);
				}
			}
			else{
				saveErrors(request, errors);
				getHostelEntries(request);
				hostelReqReportForm.clearMyFields();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
			}
		} catch (Exception e) {
			log.error("Error in getHostelReqReportDetails");
				String msg = super.handleApplicationException(e);
				hostelReqReportForm.setErrorMessage(msg);
				hostelReqReportForm.setErrorStack(e.getMessage());
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
	private void validateReqReport(HostelReqReportForm hostelReqReportForm,
			ActionErrors errors) {
		int count = 0;
		if(hostelReqReportForm.getHostelName()!= null && !hostelReqReportForm.getHostelName().trim().isEmpty()){
			if(validSpecialChar(hostelReqReportForm.getHostelName())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_REQREPORT_SPECIAL_NOT_ALLOWED));
			}
		}
		/*else if(hostelReqReportForm.getAppliedDate()!= null && !hostelReqReportForm.getAppliedDate().trim().isEmpty()){
			if(validSpecialChar(hostelReqReportForm.getAppliedDate())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_REQREPORT_SPECIAL_NOT_ALLOWED));
			}
		}*/
		
 		/*if((hostelReqReportForm.getHostelName().length() == 0 && hostelReqReportForm.getAppliedDate().length() == 0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_REQREPORT_HOSTEL_NAME_REQUIRED));
		}*/
 		else if((hostelReqReportForm.getAppliedDate().length() == 0 && hostelReqReportForm.getAppliedDate().length() == 0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_REQREPORT_APPLIED_DATE_REQUIRED));
		}
	 	
	}
	/**
	 * validate special characters of regNo
	 * @param regRollNo
	 * @return
	 */
	private boolean validSpecialChar(String regRollNo)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9  \\s \t]+");
        Matcher matcher = pattern.matcher(regRollNo);
        result = matcher.find();
        return result;

	}
	/**
	 * getting the hostel entries to display in the required jsp
	 * @param request
	 * @throws Exception
	 */
	public void getHostelEntries(HttpServletRequest request) throws Exception{
		log.debug("Entering initHostelDetails of HostelRequisitionReportAction");
		List<HostelTO> hostelList = HostelReqReportHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting initHostelDetails of HostelRequisitionReportAction");
	}
	
	
	
}
