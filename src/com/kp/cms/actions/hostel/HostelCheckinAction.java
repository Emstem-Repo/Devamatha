package com.kp.cms.actions.hostel;

import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.handlers.hostel.HostelCheckinHandler;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class HostelCheckinAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelCheckinAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCheckin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initCheckin of HostelCheckinAction");
		HostelCheckinForm HostelCheckinForm = (HostelCheckinForm) form;
		try {
			HostelCheckinForm.clearMyFields();
			getHostelEntries(request);
		} catch (Exception e) {
			log.error("error in initCheckin...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				HostelCheckinForm.setErrorMessage(msg);
				HostelCheckinForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initCheckin of HostelCheckinAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
	}
	
	public ActionForward getHostelAllocatedDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelCheckinForm hostelCheckinForm = (HostelCheckinForm) form;
		 ActionErrors errors = hostelCheckinForm.validate(mapping, request);
		validateRoomCheckIn(hostelCheckinForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelCheckinForm);
				List<HostelCheckinTo> hostelApplicantDetails = HostelCheckinHandler.getInstance().getApplicantHostelDetails(hostelCheckinForm);
				if(hostelApplicantDetails.size()==0){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelCheckinForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
				} 
				else
				{					
					hostelCheckinForm.setAllotedList(hostelApplicantDetails);
				}
			}
			else{
				saveErrors(request, errors);
				getHostelEntries(request);
				//hostelCheckinForm.clearMyFields();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
			}
		} catch (Exception e) {
			log.error("Error in getHostelCheckinDetails");
				String msg = super.handleApplicationException(e);
				hostelCheckinForm.setErrorMessage(msg);
				hostelCheckinForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getHostelCheckinDetails of HostelCheckinAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOTED);
	}
	/**
	 * getting the hostel Checkin details for input fields
	 */
	public ActionForward getHostelCheckinDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelCheckinDetails of HostelCheckinAction");
		HostelCheckinForm hostelCheckinForm = (HostelCheckinForm) form;
		ActionErrors errors = new ActionErrors();
		try 
		{
			if(isCancelled(request))
			{
				hostelCheckinForm.clearMyFields();
				getHostelEntries(request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
			}
			if(hostelCheckinForm.getTransactionId()!=null && hostelCheckinForm.getTransactionId().length()==0 )
			{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_SELECT_ONE_HOSTEL));
			}
			if (errors.isEmpty()) 
			{
				setUserId(request, hostelCheckinForm);
				boolean isCheckedIn=HostelCheckinHandler.getInstance().isCheckedIn(hostelCheckinForm);
				if(isCheckedIn)
				{	
					errors.add(CMSConstants.MESSAGES, new ActionError(CMSConstants.HOSTEL_CHECKIN_PERSON_ALREDY_CHECKIN));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOTED);
				}
				else
				{	
					List<HostelCheckinTo> hostelApplicantDetails = HostelCheckinHandler.getInstance().getAllotedHostelDetails(hostelCheckinForm);
					request.getSession().setAttribute("hostelApplicantDetails",hostelApplicantDetails);
					hostelCheckinForm.setTxnDate(CommonUtil.getTodayDate());
					setAllDataToSecondPage(hostelCheckinForm, hostelApplicantDetails);
				}	
			}
			else{
				saveErrors(request, errors);
				getHostelEntries(request);
				//hostelCheckinForm.clearMyFields();
				return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOTED);
			}
		} catch (Exception e) {
			log.error("Error in getHostelCheckinDetails");
				String msg = super.handleApplicationException(e);
				hostelCheckinForm.setErrorMessage(msg);
				hostelCheckinForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getHostelCheckinDetails of HostelCheckinAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKIN);
	}
	

	/**
	 * common validation for input jsp
	 * @param hostelCheckinForm
	 * @param errors
	 */
	private void validateRoomCheckIn(HostelCheckinForm hostelCheckinForm,
			ActionErrors errors) {
		int count = 0;
		if(hostelCheckinForm.getRegNo()!= null && !hostelCheckinForm.getRegNo().trim().isEmpty()){
			if(validSpecialChar(hostelCheckinForm.getRegNo())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKIN_SPECIAL_NOT_ALLOWED));
			}
		}
		else if(hostelCheckinForm.getRollNo()!= null && !hostelCheckinForm.getRollNo().trim().isEmpty()){
			if(validSpecialChar(hostelCheckinForm.getRollNo())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKIN_SPECIAL_NOT_ALLOWED));
			}
		}
		else if(hostelCheckinForm.getStaffId()!= null && !hostelCheckinForm.getStaffId().trim().isEmpty()){
			if(validSpecialChar(hostelCheckinForm.getStaffId())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKIN_SPECIAL_NOT_ALLOWED));
			}
		}
		if(hostelCheckinForm.getAppNo() != null && hostelCheckinForm.getAppNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckinForm.getRegNo() !=null && hostelCheckinForm.getRegNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckinForm.getRollNo() !=null && hostelCheckinForm.getRollNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckinForm.getStaffId()!= null && hostelCheckinForm.getStaffId().length()!= 0){
 			count = count + 1;
 		}
 		if((hostelCheckinForm.getAppNo().length() == 0 && hostelCheckinForm.getRegNo().length() == 0
				&& hostelCheckinForm.getStaffId().length() == 0 && hostelCheckinForm.getRollNo().length()==0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKIN_APPREGROLL_REQUIRED));
		}
	 	else if (count > 1 ) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKIN_BOTH_NOTREQUIRED));
	 	}
	}
	
	/**
	 * submitting form details from a jsp page
	 * @param hostelCheckinForm
	 * @param errors
	 */
	public ActionForward submitHostelCheckinDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

			log.info("Entering submitHostelCheckinDetails of HostelCheckinAction");
			HostelCheckinForm hostelCheckinForm = (HostelCheckinForm) form;
			setUserId(request, hostelCheckinForm);
			ActionMessages messages = new ActionMessages();
			if(isCancelled(request))
			{
				return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOTED);
			}
			ActionErrors errors =  hostelCheckinForm.validate(mapping, request);
			String checkDetailsSaved="false";		
			validateTime(hostelCheckinForm, errors);
			validateFingerPrintId(hostelCheckinForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				List<HostelCheckinTo> hostelApplicantDetails = (List<HostelCheckinTo>)request.getSession().getAttribute("hostelApplicantDetails");
				setAllDataToSecondPage(hostelCheckinForm,hostelApplicantDetails);				
				return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKIN);
			}
			else{
				try {
					List<HostelCheckinTo> hostelApplicantDetails =(List<HostelCheckinTo>)request.getSession().getAttribute("hostelApplicantDetails");
					HostelCheckinTo hostelCheckinTo=setAllDataToSecondPage(hostelCheckinForm,hostelApplicantDetails);
					checkDetailsSaved = HostelCheckinHandler.getInstance().saveCheckinDetails(hostelCheckinForm,hostelCheckinTo);
					if(checkDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_FEES_TRUE)){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_CHECKIN_DETAILS_SUCCESS));
					}
					else if(checkDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_CHECKIN_ACTIVEEXISTS)){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_CHECKIN_HOSTELWITHROOMTYPE_EXIST));
					}
					else if(checkDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_CHECKIN_NONACTIVEEXISTS)){
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_CHECKIN_REACTIVATE));
						saveErrors(request, errors);
						hostelCheckinForm.clearMyFields();
						getHostelEntries(request);
						return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKIN);
					
					}else{
						errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.checkin.failure"));
						saveErrors(request, errors);
					}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					hostelCheckinForm.setErrorMessage(msg);
					hostelCheckinForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			saveMessages(request, messages);
			hostelCheckinForm.clearMyFields();
			getHostelEntries(request);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
	}
	
	/**
	 * @param hostelCheckinForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateFingerPrintId(HostelCheckinForm hostelCheckinForm,
			ActionErrors errors)  throws Exception{
		if(hostelCheckinForm.getFingerPrintId()!=null && !hostelCheckinForm.getFingerPrintId().isEmpty() && hostelCheckinForm.getHostelId()!=null && !hostelCheckinForm.getHostelId().isEmpty()){
			boolean isDuplicate=HostelCheckinHandler.getInstance().checkDuplicateFingerPrintId(hostelCheckinForm.getFingerPrintId(),hostelCheckinForm.getHostelId());
			if(isDuplicate){
				errors.add("error", new ActionError("knowledgepro.hostel.allocation.checkin.fingerprintid.duplicate"));
			}
		}
	}
	/**
	 * setting the data fetched from database to form
	 * validate special characters of regNo
	 * @param regRollNo
	 * @return
	 */
	
	public HostelCheckinTo setAllDataToSecondPage(HostelCheckinForm hostelCheckinForm,List<HostelCheckinTo> hostelApplicantDetails) throws Exception{
		HostelCheckinHandler checkHandler=HostelCheckinHandler.getInstance();
		HostelCheckinTo hostelCheckinTo=checkHandler.setAllDataToForm(hostelCheckinForm,hostelApplicantDetails);
		return hostelCheckinTo;
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateCheckinDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Start of reActivateCheckinDetailsof HostelFeesAction");

		HostelCheckinForm hostelCheckinForm= (HostelCheckinForm) form;

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, hostelCheckinForm); 
			String userId=hostelCheckinForm.getUserId();
			boolean isReactivate;
			if((hostelCheckinForm.getStatusId()>0) && (hostelCheckinForm.getRoomId()!=null && !hostelCheckinForm.getRoomId().isEmpty())){
			
				isReactivate = HostelCheckinHandler.getInstance().reActivateCheckinDetails(Integer.valueOf(hostelCheckinForm.getStatusId()),Integer.valueOf(hostelCheckinForm.getRoomId()),userId);
			}
			else{
			isReactivate = false;
			}
			/**
			 * If reactivation is success then add the success message.
			 * Else add the appropriate error message.
			 */
			if (isReactivate) {
				getHostelEntries(request);
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_CHECKIN_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKIN);
				
			} else {
				errors.add("error", new ActionError(CMSConstants.HOSTEL_CHECKIN_REACTIVATE_FAILED));
				saveErrors(request, errors);
				getHostelEntries(request);
				
			}
		} catch (Exception e) {
			log.error("Error in reactivating Checkin Details");			
				String msg = super.handleApplicationException(e);
				hostelCheckinForm.setErrorMessage(msg);
				hostelCheckinForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateFeeDetails of HostelFeesAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKIN);
	}

	
	/**
	 * getting the hostel entries to display in the required jsp
	 * @param request
	 * @throws Exception
	 */
	public void getHostelEntries(HttpServletRequest request) throws Exception{
		log.debug("Entering getHostelEntries HostelCheckinAction");
		List<HostelTO> hostelList = HostelCheckinHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting getHostelEntries of HostelCheckinAction ");
	}
	/**
	 * @param hlAllocationForm
	 * @param errors
	 */
	private void validateTime(HostelCheckinForm hostelCheckinForm, ActionErrors errors) {
		if(hostelCheckinForm.getTxnDate()!=null && CommonUtil.isValidDate(hostelCheckinForm.getTxnDate())){
			Date startDate = CommonUtil.ConvertStringToDate(hostelCheckinForm.getAllotedDate());
			Date endDate = CommonUtil.ConvertStringToDate(hostelCheckinForm.getTxnDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.hostel.allocation.checkin.date.greater"));
			}
		}
}
		
}
