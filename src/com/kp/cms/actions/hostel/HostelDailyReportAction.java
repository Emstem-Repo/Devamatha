package com.kp.cms.actions.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.forms.hostel.HostelStudentExtractForm;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.handlers.hostel.HostelReqReportHandler;
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.handlers.hostel.HostelDailyReportHandler;

public class HostelDailyReportAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(HostelDailyReportAction.class);
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
		log.debug("Entering initHostelDetails of HostelDailyReportAction");
		HostelDailyReportForm hostelDailyReportForm = (HostelDailyReportForm) form;
		try {
			hostelDailyReportForm.clearMyFields();
			getHostelEntries(request);
		} catch (Exception e) {
			log.error("error in initHostelDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelDailyReportForm.setErrorMessage(msg);
				hostelDailyReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initHostelDetails of HostelDailyReportAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_DAILY_REPORT);
	}	
	

	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	public ActionForward getHostelDailyReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelDailyReportDetails of HostelRequisitionReportAction");
		HostelDailyReportForm hostelDailyReportForm = (HostelDailyReportForm) form;
		 ActionErrors errors = hostelDailyReportForm.validate(mapping, request);
		validateDailyReport(hostelDailyReportForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelDailyReportForm);
				List<HostelDailyReportTo> hostelDailyReportToList = HostelDailyReportHandler.getInstance().getHostelDailyReportDetails(hostelDailyReportForm);
				hostelDailyReportForm.setHostelDailyReportToList(hostelDailyReportToList);
				//request.getSession().setAttribute("hostelReqReportDetailsTo", hostelReqReportToList);
				if(hostelDailyReportToList.size()==0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelDailyReportForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_DAILY_REPORT);
				} 
								
				else{
					/*hostelDailyReportForm.clearMyFields();
					getHostelEntries(request);*/
					return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_DAILY_REPORT);
				
				}
			}
			else{
				saveErrors(request, errors);
				getHostelEntries(request);
				hostelDailyReportForm.clearMyFields();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_DAILY_REPORT);
			}
		} catch (Exception e) {
			log.error("Error in getHostelReqReportDetails");
				String msg = super.handleApplicationException(e);
				hostelDailyReportForm.setErrorMessage(msg);
				hostelDailyReportForm.setErrorStack(e.getMessage());
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
	private void validateDailyReport(HostelDailyReportForm hostelDailyReportForm,ActionErrors errors) {
		
		if(hostelDailyReportForm.getFromDate()!= null && hostelDailyReportForm.getFromDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_FROM_DATE_REQUIRED));
		}else if(hostelDailyReportForm.getToDate()!= null && hostelDailyReportForm.getToDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_TO_DATE_REQUIRED));
		}else if(CommonUtil.ConvertStringToSQLDate(hostelDailyReportForm.getFromDate()).after(CommonUtil.ConvertStringToSQLDate(hostelDailyReportForm.getToDate()))){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TO_DATE_GREATER_TO_FROM_DATE));
		}
	}
	/**
	 * getting the hostel entries to display in the required jsp
	 * @param request
	 * @throws Exception
	 */
	public void getHostelEntries(HttpServletRequest request) throws Exception{
		log.debug("Entering initHostelDetails of HostelDailyReportAction");
		List<HostelTO> hostelList = HostelDailyReportHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting initHostelDetails of HostelDailyReportAction");
	}

}
