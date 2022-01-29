package com.kp.cms.actions.admission;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.constants.CMSConstants;

import com.kp.cms.forms.admission.OfflineDetailsForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admission.OfflineDetailsHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * @author kshirod.k
 * An action class for OfflineDetails Entry
 *
 */

	@SuppressWarnings("deprecation")
	public class OfflineDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(OfflineDetailsHandler.class);
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes offline details
	 * @throws Exception
	 */

	public ActionForward initOfflineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initOfflineDetails of OfflineDetailsAction");
		OfflineDetailsForm offlineDetailsForm = (OfflineDetailsForm)form;
		try {
			assignListToForm(offlineDetailsForm);
		} catch (Exception e) {
			log.error("Error occured at initOfflineDetails of OfflineDetailsAction",e);
				String msg = super.handleApplicationException(e);
				offlineDetailsForm.setErrorMessage(msg);
				offlineDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving into initOfflineDetails of OfflineDetailsAction");
		offlineDetailsForm.clearWhileReset();
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Adds one offline details entry
	 * @throws Exception
	 */

	public ActionForward addOfflineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initOfflineDetails of OfflineDetailsAction");
		OfflineDetailsForm offlineDetailsForm = (OfflineDetailsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = offlineDetailsForm.validate(mapping, request);
		if(offlineDetailsForm.getAmount()!=null && !StringUtils.isEmpty(offlineDetailsForm.getAmount().trim())){
			if(!CommonUtil.isValidDecimal(offlineDetailsForm.getAmount().trim())){
				if (errors.get(CMSConstants.AMOUNT_INVALID)!=null && !errors.get(CMSConstants.AMOUNT_INVALID).hasNext()) {
					errors.add(CMSConstants.AMOUNT_INVALID, new ActionError(CMSConstants.AMOUNT_INVALID));
				}
			}
		}
		if(!errors.isEmpty())
		{
		saveErrors(request, errors);
		assignListToForm(offlineDetailsForm);
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
		}
		try {
			if (errors.isEmpty()) {
				setUserId(request, offlineDetailsForm);
				
				boolean isValidApplicationNumber;
				/**
				 * Checks for valid date. Can not be future date
				 */
				boolean isValidDate = validatefutureDate(offlineDetailsForm.getDate());
				if(!isValidDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DATE_INVALID));
				}
				else{
				//Check for duplicate entry based on the course, application no and year
				int courseId=Integer.parseInt(offlineDetailsForm.getCourseId().trim());
				String applicationNo=offlineDetailsForm.getApplicationNo().trim();
				int year = Integer.parseInt(offlineDetailsForm.getAcademicYear().trim());
				//Check the application number in the offline application range for a course
				isValidApplicationNumber = OfflineDetailsHandler.getInstance().checkValidOfflineNumberForCourse(courseId, Integer.parseInt(applicationNo),year);				
				if(isValidApplicationNumber){
				OfflineDetails offlineDetails=OfflineDetailsHandler.getInstance().getOfflineDetailsonApplNoYear(applicationNo, year);
					if(offlineDetails!=null){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_EXIST));
						assignListToForm(offlineDetailsForm);
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
					}
				//If add operation is true append the success message else add the error message.
				boolean isAdded;
				isAdded = OfflineDetailsHandler.getInstance().addOfflineDetails(offlineDetailsForm);
					if (isAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_ADD_SUCCESS));
						offlineDetailsForm.clear();
					} else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_ADD_FAILURE));
					offlineDetailsForm.clear();
					}
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.offlinedetails.appnumber.invalid"));
					}
				}
			}
		} catch (Exception e) {
			log.info("Error occured at addOfflineDetails of OfflineDetails Action",e);
			String msg = super.handleApplicationException(e);
			offlineDetailsForm.setErrorMessage(msg);
			offlineDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into OfflineDetailsAction of addOfflineDetails");
		assignListToForm(offlineDetailsForm);
		saveErrors(request, errors);
		saveMessages(request, messages);
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
	}
	
	/**
	 * 
	 * @param form
	 * @throws Exception
	 * Returns all the active courses
	 * Max Receipt No.
	 */
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into assignListToForm of OfflineDetailsAction");
		OfflineDetailsForm offlineDetailsForm = (OfflineDetailsForm)form;
		List<CourseTO> courseList=CourseHandler.getInstance().getActiveCourses();
		if(courseList!=null && !courseList.isEmpty()){
			offlineDetailsForm.setCourseList(courseList);
		}
		//Gets the max receipt no. from DB
		int maxReceiptNo=OfflineDetailsHandler.getInstance().getMaxReceiptNo();
		if(maxReceiptNo!=0){
			maxReceiptNo++;
			offlineDetailsForm.setReceiptNo(String.valueOf(maxReceiptNo));
		}
		else{
			maxReceiptNo++;
			offlineDetailsForm.setReceiptNo(String.valueOf(maxReceiptNo));
		}
		/**
		 * Get all the offline details available in DB
		 */
		List<OfflineDetailsTO> offlineDetailsList=OfflineDetailsHandler.getInstance().getAllOfflineDetails();
		if(offlineDetailsList!=null){
			offlineDetailsForm.setOfflineDetailsList(offlineDetailsList);
		}
		log.info("Leaving into assignListToForm of OfflineDetailsAction");
	}
	
	
	/**
	 * Used in deleting an offlinedetails entry
	 * 
	 */

	public ActionForward deleteOfflineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into OfflineDetailsAction of deleteOfflineDetails");
		OfflineDetailsForm offlineDetailsForm=(OfflineDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			
			//Get the id from formbean and pass to helper for deleting
			int offlineId = offlineDetailsForm.getId();
			boolean isDeleted;
			isDeleted=OfflineDetailsHandler.getInstance().deleteOfflineDetails(offlineId);
			//If delete operation is success then add the success message else add the error message. 
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DELETE_SUCCESS));
				offlineDetailsForm.clear();
			} else {
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DELETE_FAILURE)));
				offlineDetailsForm.clear();	
			}
		} catch (Exception e) {
			log.info("Error occured at deleteOfflineDetails of OfflineDetails Action",e);
			String msg = super.handleApplicationException(e);
			offlineDetailsForm.setErrorMessage(msg);
			offlineDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into OfflineDetailsAction of deleteOfflineDetails");
		saveErrors(request, errors);
		saveMessages(request, messages);
		assignListToForm(offlineDetailsForm);
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
	}
	
	/**
	 * Used in edit operation
	 */

	public ActionForward editOfflineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into OfflineDetailsAction of editOfflineDetails");
		OfflineDetailsForm offlineDetailsForm=(OfflineDetailsForm)form;
		try {
			int id = offlineDetailsForm.getId();
			//Pass the id to hander and get the record based on the id
			OfflineDetailsTO offlineDetailsTO=OfflineDetailsHandler.getInstance().getOfflineDetailsbyId(id);
			if(offlineDetailsTO != null){
				offlineDetailsForm.setId(offlineDetailsTO.getId());
				offlineDetailsForm.setCourseId(String.valueOf(offlineDetailsTO.getCourseId()!=0 ? offlineDetailsTO.getCourseId():0));
				offlineDetailsForm.setApplicationNo(offlineDetailsTO.getApplicationNo()!=null ? offlineDetailsTO.getApplicationNo():null);
				offlineDetailsForm.setAcademicYear(String.valueOf(offlineDetailsTO.getAcademicYear() !=0 ? offlineDetailsTO.getAcademicYear():0));
				offlineDetailsForm.setReceiptNo(offlineDetailsTO.getReceiptNo()!=null ? offlineDetailsTO.getReceiptNo():null);
				offlineDetailsForm.setAmount(offlineDetailsTO.getAmount()!=null ? offlineDetailsTO.getAmount():null);
				offlineDetailsForm.setDate(offlineDetailsTO.getDate()!=null ? offlineDetailsTO.getDate():null);
				offlineDetailsForm.setOldYear(offlineDetailsTO.getAcademicYear()!=0 ? offlineDetailsTO.getAcademicYear():0);
				offlineDetailsForm.setOldApplicationNo(Integer.parseInt(offlineDetailsTO.getApplicationNo()!=null ? offlineDetailsTO.getApplicationNo():null));
				
				/**
				 * Get all the active courses
				 */
				List<CourseTO> courseList=CourseHandler.getInstance().getActiveCourses();
				if(courseList!=null && !courseList.isEmpty()){
					offlineDetailsForm.setCourseList(courseList);
				}
				/**
				 * Get all the active offline details available in DB
				 */
				List<OfflineDetailsTO> offlineDetailsList=OfflineDetailsHandler.getInstance().getAllOfflineDetails();
				if(offlineDetailsList!=null){
					offlineDetailsForm.setOfflineDetailsList(offlineDetailsList);
				}
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			}
		} catch (Exception e) {
			log.info("Error occured at editOfflineDetails of OfflineDetails Action",e);
			String msg = super.handleApplicationException(e);
			offlineDetailsForm.setErrorMessage(msg);
			offlineDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into OfflineDetailsAction of editOfflineDetails");
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Used in updating offline details entry
	 * @throws Exception
	 */
	public ActionForward updateOfflineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into OfflineDetailsAction of updateOfflineDetails");
		OfflineDetailsForm offlineDetailsForm=(OfflineDetailsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = offlineDetailsForm.validate(mapping, request);
		//The below condition is executed if reset button is clicked in update mode.
		//Re-populating the old saved values.
		if(isCancelled(request)){
			try {
				int id = offlineDetailsForm.getId();
				//Pass the id to hander and get the record based on the id
				OfflineDetailsTO offlineDetailsTO = OfflineDetailsHandler.getInstance().getOfflineDetailsbyId(id);
				if (offlineDetailsTO != null) {
						offlineDetailsForm.setId(offlineDetailsTO.getId());
						offlineDetailsForm.setCourseId(String.valueOf(offlineDetailsTO.getCourseId() != 0 ? offlineDetailsTO.getCourseId() : 0));
						offlineDetailsForm.setApplicationNo(offlineDetailsTO.getApplicationNo() != null ? offlineDetailsTO.getApplicationNo() : null);
						offlineDetailsForm.setAcademicYear(String.valueOf(offlineDetailsTO.getAcademicYear() != 0 ? offlineDetailsTO.getAcademicYear() : 0));
						offlineDetailsForm.setReceiptNo(offlineDetailsTO.getReceiptNo() != null ? offlineDetailsTO.getReceiptNo() : null);
						offlineDetailsForm.setAmount(offlineDetailsTO.getAmount() != null ? offlineDetailsTO.getAmount(): null);
						offlineDetailsForm.setDate(offlineDetailsTO.getDate() != null ? offlineDetailsTO.getDate(): null);
						offlineDetailsForm.setOldYear(offlineDetailsTO.getAcademicYear() != 0 ? offlineDetailsTO.getAcademicYear() : 0);
						offlineDetailsForm.setOldApplicationNo(Integer.parseInt(offlineDetailsTO.getApplicationNo() != null ? offlineDetailsTO.getApplicationNo(): null));
	
						/**
						 * Get all the active courses
						 */
						List<CourseTO> courseList = CourseHandler.getInstance().getActiveCourses();
						if (courseList != null && !courseList.isEmpty()) {
							offlineDetailsForm.setCourseList(courseList);
						}
						/**
						 * Get all the active offline details available in DB
						 */
						List<OfflineDetailsTO> offlineDetailsList = OfflineDetailsHandler.getInstance().getAllOfflineDetails();
						offlineDetailsForm.setOfflineDetailsList(offlineDetailsList);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				}
				return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				offlineDetailsForm.setErrorMessage(msg);
				offlineDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			
		}		
		if(offlineDetailsForm.getAmount()!=null && !StringUtils.isEmpty(offlineDetailsForm.getAmount().trim())){
			if(!CommonUtil.isValidDecimal(offlineDetailsForm.getAmount().trim())){
				if (errors.get(CMSConstants.AMOUNT_INVALID)!=null && !errors.get(CMSConstants.AMOUNT_INVALID).hasNext()) {
					errors.add(CMSConstants.AMOUNT_INVALID, new ActionError(CMSConstants.AMOUNT_INVALID));
				}
			}
		}
		if(errors!=null && !errors.isEmpty() )
		{
				assignListToForm(offlineDetailsForm);
				saveErrors(request, errors);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
		}
		
		try {
			if(errors.isEmpty()){
			setUserId(request, offlineDetailsForm);
			//Validate the entered date. Can not be future date
			boolean isUpdated;
			boolean isValidDate = validatefutureDate(offlineDetailsForm.getDate());
				if(!isValidDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DATE_INVALID));
				}
				else{
					boolean isValidApplicationNumber;
				//Check for duplicate entry based on the course, application no and year
				int courseId=Integer.parseInt(offlineDetailsForm.getCourseId().trim());
				String applicationNo=offlineDetailsForm.getApplicationNo().trim();
				int year = Integer.parseInt(offlineDetailsForm.getAcademicYear().trim());				
				//Check the application number in the offline application range for the course
				isValidApplicationNumber = OfflineDetailsHandler.getInstance().checkValidOfflineNumberForCourse(courseId, Integer.parseInt(applicationNo),year);				
				if(isValidApplicationNumber){				
				String oldApplicationNo=String.valueOf(offlineDetailsForm.getOldApplicationNo());
				int oldYear = offlineDetailsForm.getOldYear();
				if(!applicationNo.equals(oldApplicationNo) || year != oldYear){
				OfflineDetails offlineDetails=OfflineDetailsHandler.getInstance().getOfflineDetailsonApplNoYear(applicationNo, year);
					if(offlineDetails!=null){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_EXIST));
						assignListToForm(offlineDetailsForm);
						saveErrors(request, errors);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
						return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
					}
				else{
					//If update operation is success then append the success message else add the error message.
					isUpdated = OfflineDetailsHandler.getInstance().updateOfflineDetails(offlineDetailsForm);
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_SUCCESS));
							offlineDetailsForm.clear();
							saveMessages(request, messages);
							assignListToForm(offlineDetailsForm);
							return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
						} else {
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ERROR, CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_FAILURE));
							offlineDetailsForm.clear();	
						}
					}
				}
				else{
					//If update operation is success then append the success message else add the error message.
					isUpdated = OfflineDetailsHandler.getInstance().updateOfflineDetails(offlineDetailsForm);
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_SUCCESS));
							offlineDetailsForm.clear();
							assignListToForm(offlineDetailsForm);
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
						} else {
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ERROR, CMSConstants.KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_FAILURE));
						offlineDetailsForm.clear();	
						}
					}
				} 
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.offlinedetails.appnumber.invalid"));
					}
				}
			}
		}catch (Exception e) {
			log.info("Error occured at updateOfflineDetails of OfflineDetails Action",e);
			String msg = super.handleApplicationException(e);
			offlineDetailsForm.setErrorMessage(msg);
			offlineDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into OfflineDetailsAction of updateOfflineDetails");
		saveErrors(request, errors);
		saveMessages(request, messages);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.OFFLINE_DETAILS);
	}
	
	/**
	 * Method to check the entered date is not a future date
	 * @param dateString
	 * @return boolean value
	 */
	
	private boolean validatefutureDate(String dateString) {
		log.info("Entering into OfflineDetailsAction of validatefutureDate");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, CMSConstants.DEST_DATE,DATE_FORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		if (date.compareTo(curdate) == 1){
			return false;
		}
		else{
			log.info("Leaving into OfflineDetailsAction of validatefutureDate");
			return true;
		}
	}
}
