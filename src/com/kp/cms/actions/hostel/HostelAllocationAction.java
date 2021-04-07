package com.kp.cms.actions.hostel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.kp.cms.forms.hostel.HostelAllocationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelAllocationHandler;
import com.kp.cms.helpers.hostel.HostelAllocationHelper;
import com.kp.cms.to.hostel.HostelAllocationTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class HostelAllocationAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelAllocationAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAllocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initAllocation of HostelAllocationAction");
		HostelAllocationForm hostelAllocationForm = (HostelAllocationForm) form;
		try {
			getHostelEntries(request);
			hostelAllocationForm.setAcademicYr(Integer.parseInt(CommonUtil.getCurrentYear()));
		} catch (Exception e) {
			log.error("error in initAllocation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelAllocationForm.setErrorMessage(msg);
				hostelAllocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initAllocation of HostelAllocationAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReservedHostelDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelAllocationDetails of HostelAllocationAction");
		HostelAllocationForm hostelAllocationForm = (HostelAllocationForm) form;
		 ActionErrors errors = hostelAllocationForm.validate(mapping, request);
		if(hostelAllocationForm.getRegNo()!= null && !hostelAllocationForm.getRegNo().trim().isEmpty()){
 			if(validSpecialChar(hostelAllocationForm.getRegNo())){
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_SPECIAL_NOT_ALLOWED));
 			}
 		}
		else if(hostelAllocationForm.getRollNo()!= null && !hostelAllocationForm.getRollNo().trim().isEmpty()){
 			if(validSpecialChar(hostelAllocationForm.getRollNo())){
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_SPECIAL_NOT_ALLOWED));
 			}
		}
 		else if(hostelAllocationForm.getStaffId()!= null && !hostelAllocationForm.getStaffId().trim().isEmpty()){
 			if(validSpecialChar(hostelAllocationForm.getStaffId())){
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_SPECIAL_NOT_ALLOWED));
 			}
 		}
		int count = 0;
 		if(hostelAllocationForm.getAppNo() != null && hostelAllocationForm.getAppNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelAllocationForm.getRegNo() !=null && hostelAllocationForm.getRegNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelAllocationForm.getRollNo() !=null && hostelAllocationForm.getRollNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelAllocationForm.getStaffId()!= null && hostelAllocationForm.getStaffId().length()!= 0){
 			count = count + 1;
 		}
 		if((hostelAllocationForm.getAppNo().length() == 0 && hostelAllocationForm.getRegNo().length() == 0
				&& hostelAllocationForm.getStaffId().length() == 0 && hostelAllocationForm.getRollNo().length()==0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_APPREGROLL_REQUIRED));
		}
	 	else if (count > 1 ) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_BOTH_NOTREQUIRED));
	 	}
		try
		{
			if (errors.isEmpty()) 
			{
				setUserId(request, hostelAllocationForm);
				List<HostelAllocationTO> reservedHostelList = HostelAllocationHandler.getInstance().getReservedHostelDetails(hostelAllocationForm);
				if( reservedHostelList.size() == 0)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelAllocationForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
				}
				else
				{
					hostelAllocationForm.setReservedHostelList(reservedHostelList);
				}
			}
			else
			{
				saveErrors(request, errors);
				getHostelEntries(request);
				hostelAllocationForm.clearMyFields();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
			}
		} catch (Exception e) {
			log.error("Error in getHostelAllocationDetails");
				String msg = super.handleApplicationException(e);
				hostelAllocationForm.setErrorMessage(msg);
				hostelAllocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getHostelAllocationDetails of HostelAllocationAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_RESERVED);
	}
	
	public ActionForward getHostelAllocationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelAllocationDetails of HostelAllocationAction");
		HostelAllocationForm hostelAllocationForm = (HostelAllocationForm) form;
		hostelAllocationForm.setAllocationDate(CommonUtil.getTodayDate());
		ActionErrors errors = new ActionErrors();
		try
		{
			if(isCancelled(request))
			{
				hostelAllocationForm.clearMyFields();
				getHostelEntries(request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
			}
			if(hostelAllocationForm.getAppNo()!=null && hostelAllocationForm.getAppNo().length()==0 )
			{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_ALLOCATION_SELECT_ONE_HOSTEL));
			}
			if (errors.isEmpty()) 
			{
				setUserId(request, hostelAllocationForm);
				HostelAllocationTO hostelApplicantDetails = HostelAllocationHandler.getInstance().getApplicantHostelDetails(hostelAllocationForm);
				request.getSession().setAttribute("hostelApplicantDetails", hostelApplicantDetails);
				if( hostelApplicantDetails == null)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelAllocationForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_RESERVED);
				}
				else
				{
					setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
					getRoomAndBedEntries(request,hostelAllocationForm);
				}
			}
			else
			{
				saveErrors(request, errors);
				getHostelEntries(request);
				hostelAllocationForm.clearMyFields();
				return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_RESERVED);
			}
		} catch (Exception e) {
			log.error("Error in getHostelAllocationDetails");
				String msg = super.handleApplicationException(e);
				hostelAllocationForm.setErrorMessage(msg);
				hostelAllocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getHostelAllocationDetails of HostelAllocationAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAllocationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering submitAllocationDetails of HostelAllocationAction");
		HostelAllocationForm hostelAllocationForm = (HostelAllocationForm) form;
		setUserId(request, hostelAllocationForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors =  hostelAllocationForm.validate(mapping, request);
		String isAllocationDetailsSaved = "failed" ;
		if(isCancelled(request))
		{
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_RESERVED);
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			hostelAllocationForm.clearMyFields();
			HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
			setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
			getRoomAndBedEntries(request, hostelAllocationForm);
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
		}
		else{
			try {
				HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
				validateTime(hostelAllocationForm,errors);
				if (errors != null && !errors.isEmpty()) {
					saveErrors(request, errors);
					setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
					getRoomAndBedEntries(request, hostelAllocationForm);
					return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
				}
				else
				{	
					isAllocationDetailsSaved = HostelAllocationHandler.getInstance().saveAllocationDetails(hostelAllocationForm,hostelApplicantDetails);
				}	
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				hostelAllocationForm.setErrorMessage(msg);
				hostelAllocationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		if(isAllocationDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_ALLOCATION_BED_ALLREADY_ALLOTTED)){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.hostel.allocation.bedallreadyallotted"));
			saveErrors(request, errors);
			hostelAllocationForm.clearMyFields();
			HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
			setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
			getRoomAndBedEntries(request, hostelAllocationForm);
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
		}
		else if(isAllocationDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_ALLOCATION_ALLREADY_ALLOTTED)){
			ActionMessage message = new ActionMessage("knowledgepro.hostel.allocation.allreadyallotted");
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelAllocationForm.clearMyFields();
			HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
			setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
			getRoomAndBedEntries(request, hostelAllocationForm);
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
		
		}
		else if(isAllocationDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_ALLOCATION_RESERVERD_ROOM)){
			ActionMessage message = new ActionMessage("knowledgepro.hostel.allocation.cannotallocate");
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelAllocationForm.clearMyFields();
			HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
			setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
			getRoomAndBedEntries(request, hostelAllocationForm);
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
		
		}
		else if(isAllocationDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_ALLOCATION_RESERVERD_ROOM_FULL)){
			ActionMessage message = new ActionMessage("knowledgepro.hostel.allocation.allocated");
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelAllocationForm.clearMyFields();
			HostelAllocationTO hostelApplicantDetails = (HostelAllocationTO)request.getSession().getAttribute("hostelApplicantDetails");
			setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
			getRoomAndBedEntries(request, hostelAllocationForm);
			return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
		
		}
		else if (isAllocationDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_ALLOCATION_SUCCESS)) {
			ActionMessage message = new ActionMessage("knowledgepro.hostel.allocation.done.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			getHostelEntries(request);
			hostelAllocationForm.clearMyFields();
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.allocation.save.failure"));
			saveErrors(request, errors);
			hostelAllocationForm.clearMyFields();
		}
		getHostelEntries(request);
		hostelAllocationForm.clearMyFields();
		log.info("Exiting getHostelAllocationDetails of HostelAllocationAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
	}
	
	/**
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
	 * @param hostelAllocationForm
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */
	public void setAllDataToForm(HostelAllocationForm hostelAllocationForm,HostelAllocationTO hostelApplicantDetails ) throws Exception
	{
		log.info("Entering setAllDataToForm of HostelAllocationAction");
		HostelAllocationHelper hostelAllocationHelper = HostelAllocationHelper.getInstance();
		hostelAllocationHelper.setAllDataToForm(hostelAllocationForm,hostelApplicantDetails);
		log.info("Exiting setAllDataToForm of HostelAllocationAction");
	}
	
	/**
	 * @param request
	 * @throws Exception
	 */
	public void getHostelEntries(HttpServletRequest request) throws Exception{
		log.debug("Entering getHostelEntries HostelAllocationAction");
		List<HostelTO> hostelList = HostelAllocationHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting getHostelEntries of HostelAllocationAction ");
	}
	
	private void getRoomAndBedEntries(HttpServletRequest request,
			HostelAllocationForm hostelAllocationForm) {
		Map<Integer,String>roomMap=CommonAjaxHandler.getInstance().getRoomsByFloors(Integer.parseInt(hostelAllocationForm.getHostelId()),Integer.parseInt(hostelAllocationForm.getFloorNo()),Integer.parseInt(hostelAllocationForm.getRoomId()));
		request.setAttribute("roomMap",roomMap);
		Map<Integer,String>bedMap=CommonAjaxHandler.getInstance().getBedByRoomId(Integer.parseInt(hostelAllocationForm.getRoomId()));
		request.setAttribute("bedMap",bedMap);
	}
	
	private void validateTime(HostelAllocationForm hlAllocationForm, ActionErrors errors) {
	
			Date startDate = CommonUtil.ConvertStringToDate(CommonUtil.ConvertStringToDateFormat(hlAllocationForm.getReservationDate(),"dd-MMM-yyyy","dd/MM/yyyy"));
			Date endDate = CommonUtil.ConvertStringToDate(hlAllocationForm.getAllocationDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.HOSTEL_ALLOCATION_VALID_DATE));
			}
	}
		
}
