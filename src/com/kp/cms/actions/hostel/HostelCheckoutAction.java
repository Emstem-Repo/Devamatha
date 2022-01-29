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
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.handlers.hostel.HostelCheckoutHandler;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;


public class HostelCheckoutAction extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(HostelCheckoutAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCheckout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initCheckout of HostelCheckoutAction");
		HostelCheckoutForm hostelCheckoutForm = (HostelCheckoutForm)form;
		hostelCheckoutForm.clearMyFields();
		try {
			getHostelEntries(request);
		} catch (Exception e) {
			log.error("error in initCheckout...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelCheckoutForm.setErrorMessage(msg);
				hostelCheckoutForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initCheckout of HostelCheckioutAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
	}	
	
	/**
	 * getting the hostel Checkout details for input fields
	 */
	public ActionForward getHostelCheckoutDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getHostelCheckoutDetails of HostelCheckoutAction");
		HostelCheckoutForm hostelCheckoutForm = (HostelCheckoutForm) form;
		 ActionErrors errors = hostelCheckoutForm.validate(mapping, request);
		validateRoomCheckout(hostelCheckoutForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelCheckoutForm);
				HostelCheckoutTo hostelCheckoutTo = HostelCheckoutHandler.getInstance().getApplicantHostelDetails(hostelCheckoutForm);
				if(hostelCheckoutTo==null){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					hostelCheckoutForm.clearMyFields();
					getHostelEntries(request);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
				} 
				request.getSession().setAttribute("hostelApplicantDetails", hostelCheckoutTo);
				setAllDataToSecondPage(hostelCheckoutForm,hostelCheckoutTo);
				List<HlDamageTO>  hlDamageToList=HostelCheckoutHandler.getInstance().getDamageDetails(hostelCheckoutForm);
				hostelCheckoutForm.setHlDamageToList(hlDamageToList);
			}
			else{
				saveErrors(request, errors);
				getHostelEntries(request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
			}
		} catch (Exception e) {
			log.error("Error in getHostelCheckoutDetails");
				String msg = super.handleApplicationException(e);
				hostelCheckoutForm.setErrorMessage(msg);
				hostelCheckoutForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getHostelCheckoutDetails of HostelCheckoutAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKOUT);
	}
	
	/**
	 * getting the hostel Checkout fine details for input fields
	 */
	public ActionForward displayFineDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering displayFineDetails of HostelCheckoutAction");
		log.debug("Exiting displayFineDetails of HostelCheckoutAction");
		return mapping.findForward(CMSConstants.VIEW_FINE_Details);
	}
	
	

	/**		
	 * submitting form details from a jsp page
	 * @param hostelCheckinForm
	 * @param errors
	 */
	public ActionForward submitHostelCheckoutDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			log.info("Entering submitHostelCheckoutDetails of HostelCheckinAction");
			HostelCheckoutForm hostelCheckoutForm = (HostelCheckoutForm) form;
			setUserId(request, hostelCheckoutForm);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors =  hostelCheckoutForm.validate(mapping, request);
			String checkDetailsSaved="false";		
			validateTime(hostelCheckoutForm, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				HostelCheckoutTo hostelCheckoutTo = hostelCheckoutForm.getCheckoutTo();
				setAllDataToSecondPage(hostelCheckoutForm,hostelCheckoutTo);				
				return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_CHECKOUT);
			}
			else{
				try {
					HostelCheckoutTo hostelCkoutTo =(HostelCheckoutTo)request.getSession().getAttribute("hostelApplicantDetails");
					HostelCheckoutTo hostelCheckoutTo=setAllDataToSecondPage(hostelCheckoutForm,hostelCkoutTo);
					checkDetailsSaved = HostelCheckoutHandler.getInstance().saveCheckoutDetails(hostelCheckoutForm,hostelCheckoutTo);
					if(checkDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_CHECKOUT_TRUE)){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_CHECKOUT_DETAILS_SUCCESS));
						saveMessages(request, messages);
						hostelCheckoutForm.clearMyFields();
						getHostelEntries(request);
						return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
					}
					else{
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_CHECKOUT_DETAILS_FAIL));
						saveErrors(request, errors);
						getHostelEntries(request);
						return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
					
					}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					hostelCheckoutForm.setErrorMessage(msg);
					hostelCheckoutForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			//return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
	}
	
	/**
	 * setting the data fetched from database to form
	 * validate special characters of regNo
	 * @param regRollNo
	 * @return
	 */	
	public HostelCheckoutTo setAllDataToSecondPage(HostelCheckoutForm hostelCheckoutForm,HostelCheckoutTo hostelChekoutTo) throws Exception{
		HostelCheckoutHandler checkHandler=HostelCheckoutHandler.getInstance();
		HostelCheckoutTo hostelCheckoutTo=checkHandler.setAllDataToForm(hostelCheckoutForm,hostelChekoutTo);
		return hostelCheckoutTo;
	}
	

	/**
	 * common validation for input jsp
	 * @param hostelCheckinForm
	 * @param errors
	 */
	private void validateRoomCheckout(HostelCheckoutForm hostelCheckoutForm,
			ActionErrors errors) {
		int count = 0;
		if(hostelCheckoutForm.getHostelId()==null || hostelCheckoutForm.getHostelId().isEmpty()){
			errors.add(CMSConstants.ERRORS, new ActionError("errors.required","Hostel Name"));
		}
		if(hostelCheckoutForm.getRegNo()!= null && !hostelCheckoutForm.getRegNo().trim().isEmpty()){
			if(validSpecialChar(hostelCheckoutForm.getRegNo())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKOUT_SPECIAL_NOT_ALLOWED));
			}
		}
		else if(hostelCheckoutForm.getRollNo()!= null && !hostelCheckoutForm.getRollNo().trim().isEmpty()){
			if(validSpecialChar(hostelCheckoutForm.getRollNo())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKOUT_SPECIAL_NOT_ALLOWED));
			}
		}
		else if(hostelCheckoutForm.getStaffId()!= null && !hostelCheckoutForm.getStaffId().trim().isEmpty()){
			if(validSpecialChar(hostelCheckoutForm.getStaffId())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKOUT_SPECIAL_NOT_ALLOWED));
			}
		}
		if(hostelCheckoutForm.getAppNo() != null && hostelCheckoutForm.getAppNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckoutForm.getRegNo() !=null && hostelCheckoutForm.getRegNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckoutForm.getRollNo() !=null && hostelCheckoutForm.getRollNo().length() !=0){
 			count = count + 1;
 		}
 		if(hostelCheckoutForm.getStaffId()!= null && hostelCheckoutForm.getStaffId().length()!= 0){
 			count = count + 1;
 		}
 		if((hostelCheckoutForm.getAppNo().length() == 0 && hostelCheckoutForm.getRegNo().length() == 0
				&& hostelCheckoutForm.getStaffId().length() == 0 && hostelCheckoutForm.getRollNo().length()==0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKOUT_APPREGROLL_REQUIRED));
		}
	 	else if (count > 1 ) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_CHECKOUT_BOTH_NOTREQUIRED));
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
		log.debug("Entering getHostelEntries HostelCheckoutAction");
		List<HostelTO> hostelList = HostelCheckoutHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting getHostelEntries of HostelCheckoutAction ");
	}
	
	/**
	 * @param hlAllocationForm
	 * @param errors
	 */
	private void validateTime(HostelCheckoutForm hostelCheckoutForm, ActionErrors errors) {
		if(hostelCheckoutForm.getTxnDate()!=null && CommonUtil.isValidDate(hostelCheckoutForm.getTxnDate()) && hostelCheckoutForm.getCheckoutDate()!=null && CommonUtil.isValidDate(hostelCheckoutForm.getCheckoutDate())){
			Date startDate = CommonUtil.ConvertStringToDate(hostelCheckoutForm.getTxnDate());
			Date endDate = CommonUtil.ConvertStringToDate(hostelCheckoutForm.getCheckoutDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.hostel.allocation.checkout.date.greater"));
			}
		}
}

}
