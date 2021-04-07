package com.kp.cms.actions.hostel;

import java.util.List;
import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
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
import com.kp.cms.forms.hostel.HostelAbsentiesReportForm;
import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.forms.hostel.HostelStudentExtractForm;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.handlers.hostel.HostelReqReportHandler;
import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HlGroupTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.handlers.hostel.HostelAbsentiesReportHandler;
import com.kp.cms.helpers.hostel.HostelAbsentiesReportHelper;

public class HostelAbsentiesReportAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(HostelAbsentiesReportAction.class);
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
		log.debug("Entering initHostelDetails of HostelAbsentiesReportAction");
		HostelAbsentiesReportForm hostelAbsentiesReportForm = (HostelAbsentiesReportForm)form;
		try {
			hostelAbsentiesReportForm.clearMyFields();
			getHostelEntries(request,hostelAbsentiesReportForm);
		} catch (Exception e) {
			log.error("error in initHostelDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelAbsentiesReportForm.setErrorMessage(msg);
				hostelAbsentiesReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initHostelDetails of HostelAbsentiesReportAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ABSENT_REPORT);
	}	
	

	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	public ActionForward getHostelAbsentReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelDailyReportDetails of HostelAbsentiesReportAction");
		HostelAbsentiesReportForm hostelAbsentiesReportForm = (HostelAbsentiesReportForm) form;
		 ActionErrors errors = hostelAbsentiesReportForm.validate(mapping, request);
		validateDailyReport(hostelAbsentiesReportForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelAbsentiesReportForm);
				List<HostelAbsentiesReportTo> hostelAbsentiesReportToList = HostelAbsentiesReportHandler.getInstance().getHostelAbsentDetails(hostelAbsentiesReportForm);
				hostelAbsentiesReportForm.setHostelAbsentiesReportToList(hostelAbsentiesReportToList);
				request.getSession().setAttribute("hostelAbsentiesReportToList", hostelAbsentiesReportToList);
				if(hostelAbsentiesReportToList.size()==0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelAbsentiesReportForm.clearMyFields();
					getHostelEntries(request,hostelAbsentiesReportForm);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ABSENT_REPORT);
				} 
								
				else{
					
					return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ABSENT_REPORT);
				
				}
			}
			else{
				saveErrors(request, errors);
				hostelAbsentiesReportForm.clearMyFields();
				getHostelEntries(request,hostelAbsentiesReportForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ABSENT_REPORT);
			}
		} catch (Exception e) {
			log.error("Error in getHostelReqReportDetails");
				String msg = super.handleApplicationException(e);
				hostelAbsentiesReportForm.setErrorMessage(msg);
				hostelAbsentiesReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		//log.info("Exiting getHostelReqReportDetails of HostelRequisitionReportAction");
		//return mapping.findForward(CMSConstants.INIT_HOSTEL_REQREPORT);
	}

	/**
	 * common validation for input 
	 * @param hostelCheckinForm
	 * @param errors
	 */
	private void validateDailyReport(HostelAbsentiesReportForm hostelAbsentiesReportForm,ActionErrors errors) {
		
		if(hostelAbsentiesReportForm.getHostelId()!= null && hostelAbsentiesReportForm.getHostelId().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.DISPLAY_HOSTEL_ABSENT_HOSTELID));
		}
		if(hostelAbsentiesReportForm.getHlGroupId()!= null && hostelAbsentiesReportForm.getHlGroupId().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.DISPLAY_HOSTEL_ABSENT_GROUPID));
		}
		if(hostelAbsentiesReportForm.getFromDate()!= null && hostelAbsentiesReportForm.getFromDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_FROM_DATE_REQUIRED));
		}else if(hostelAbsentiesReportForm.getToDate()!= null && hostelAbsentiesReportForm.getToDate().length() == 0){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_EXTRACT_TO_DATE_REQUIRED));
		}else if(CommonUtil.ConvertStringToSQLDate(hostelAbsentiesReportForm.getFromDate()).after(CommonUtil.ConvertStringToSQLDate(hostelAbsentiesReportForm.getToDate()))){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TO_DATE_GREATER_TO_FROM_DATE));
		}
	}
	
	/**
	 * getting the hostel ReqReportdetails for input fields
	 */
	public ActionForward getDetailsToPrint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.debug("Entering getDetailsToPrint of HostelAbsentiesReportAction");
			HostelAbsentiesReportForm hostelAbsentiesReportForm = (HostelAbsentiesReportForm) form;
			//List<HostelAbsentiesReportTo> hostelAbsentiesReportToList = HostelAbsentiesReportHandler.getInstance().getHostelAbsentDetails(hostelAbsentiesReportForm);
			List<HostelAbsentiesReportTo> hostelAbsentiesReportToList=(List)request.getSession().getAttribute("hostelAbsentiesReportToList");
			hostelAbsentiesReportForm.setHostelAbsentiesReportToList(hostelAbsentiesReportToList);
			return mapping.findForward(CMSConstants.PRINT_HOSTEL_ABSENT_REPORT);
			}
	
	/**
	 * getting the hostel entries to display in the required jsp
	 * @param request
	 * @throws Exception
	 */
	public void getHostelEntries(HttpServletRequest request,HostelAbsentiesReportForm hostelAbsentiesReportForm) throws Exception{
		
		log.debug("Entering getHostelEntries of HostelAbsentiesReportAction");
		List<HostelTO> hostelList = HostelAbsentiesReportHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		List<HlGroupTo> hlGroupTOList = HostelAbsentiesReportHandler.getInstance().getHlGroupDetails();
		hostelAbsentiesReportForm.setHlGroupTOList(hlGroupTOList);
		request.setAttribute("hlGroupTOList",hlGroupTOList);
		log.debug("Exiting getHostelEntries of HostelAbsentiesReportAction");
	}

}
