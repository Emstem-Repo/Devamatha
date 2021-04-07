package com.kp.cms.actions.employee;

import java.util.Calendar;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.HolidaysForm;
import com.kp.cms.handlers.employee.HolidaysHandler;
import com.kp.cms.to.employee.HolidaysTO;
import com.kp.cms.utilities.CommonUtil;

public class HolidaysAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(HolidaysAction.class);
	
	
	public ActionForward initHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the initHolidays in HolidaysAction");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		holidaysForm.resetFields();
		setEmployeeTypeListToForm(holidaysForm);
		setEmployeeHolidaysListToForm(holidaysForm);
		log.info("Exit from the initHolidays in HolidaysAction");
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}
	
	private void setEmployeeTypeListToForm(HolidaysForm holidaysForm) throws Exception{
		List<HolidaysTO> empTypeList = HolidaysHandler.getInstance().getEmployeeTypeList();	
		holidaysForm.setEmpTypeList(empTypeList);
	}
	
	public ActionForward addEmpHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the addEmpHolidays in HolidaysAction");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidaysForm.validate(mapping, request);
		validateDate(holidaysForm,errors);
		boolean isAdded;
		try{
			if(errors.isEmpty()){
				setUserId(request, holidaysForm);
					isAdded=HolidaysHandler.getInstance().addHolidays(holidaysForm,"Add");
					if (isAdded) {
						ActionMessage message = new ActionMessage("knowledgepro.emp.holiday.apply.success");
						holidaysForm.resetFields();
						setEmployeeHolidaysListToForm(holidaysForm);
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						// failed
						errors.add("error", new ActionError("knowledgepro.emp.holiday.apply.failure"));
						saveErrors(request, errors);
					}
			}else{
				saveErrors(request, errors);
			}
		}catch (DuplicateException duplicateException) {
			log.info("Entered updateInterviewDefinition Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_ADDEXIST));
			saveErrors(request, errors);
			setEmployeeHolidaysListToForm(holidaysForm);
			return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
		} catch (ReActivateException reActivateException) {
			log.info("Entered updateInterviewDefinition Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_REACTIVATE,holidaysForm.getId()));
			saveErrors(request, errors);
			setEmployeeHolidaysListToForm(holidaysForm);
			return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
		}catch (Exception e) {
			log.error("Error occured in holidays Action", e);
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//setRequestedDataToForm(applyLeaveForm);
		//setAllotedLeaveListToForm(applyLeaveForm, employeeId);
		log.info("Existing from the holidays in Holidays Action");
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}
	/**
	 * Method to validate the Date format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateDate(HolidaysForm holidaysForm, ActionErrors errors) throws Exception{
		
		if(holidaysForm.getStartDate()!=null && !StringUtils.isEmpty(holidaysForm.getStartDate())&& !CommonUtil.isValidDate(holidaysForm.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(holidaysForm.getEndDate()!=null && !StringUtils.isEmpty(holidaysForm.getEndDate())&& !CommonUtil.isValidDate(holidaysForm.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(holidaysForm.getStartDate()) && CommonUtil.checkForEmpty(holidaysForm.getEndDate())&& CommonUtil.isValidDate(holidaysForm.getEndDate())&& CommonUtil.isValidDate(holidaysForm.getStartDate())){
			Date startDate = CommonUtil.ConvertStringToDate(holidaysForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(holidaysForm.getEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
	}
	
	public void setEmployeeHolidaysListToForm(HolidaysForm holidaysForm) throws Exception{
		List<HolidaysTO> holidaysTOsList = HolidaysHandler.getInstance().getEmployeeHolidaysList();
		holidaysForm.setHolidaysTOsList(holidaysTOsList);
	}
	
	public ActionForward editEmpHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the editEmpHolidays in HolidaysAction");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		try{
			int id = holidaysForm.getId();
			HolidaysTO holidaysTO = HolidaysHandler.getInstance().getHolidaysDetailsToEdit(id);
			if(holidaysTO != null){
				holidaysForm.setId(holidaysTO.getId());
			holidaysForm.setEmpTypeId(Integer.toString(holidaysTO.getEmpTypeId()));
			holidaysForm.setStartDate(holidaysTO.getStartDate());
			holidaysForm.setEndDate(holidaysTO.getEndDate());
			holidaysForm.setAcademicYear(Integer.toString(holidaysTO.getAcademicYear()));
			holidaysForm.setHoliday(holidaysTO.getHoliday());
			holidaysForm.setCreatedBy(holidaysTO.getCreatedBy());
			holidaysForm.setCreatedDate(holidaysTO.getCreatedDate());
			holidaysForm.setOldId(holidaysTO.getId());
			holidaysForm.setDatesArray(holidaysTO.getDatesArray());
			holidaysForm.setDatesId(holidaysTO.getDatesId());
			holidaysForm.setDatesIdSet(holidaysTO.getDatesIdSet());
			holidaysForm.setHolidaysTO(holidaysTO);
			}
		} catch (Exception e) {
			log.error("error occured editing holidays details");
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		log.info("existing editing holidays details");
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}
	
	public ActionForward updateEmpHolidays(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.info("inside updateEmpHolidays of HolidaysAction");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		 ActionErrors errors = holidaysForm.validate(mapping, request);
		validateDate(holidaysForm,errors);
		ActionMessages messages = new ActionMessages();
		try
		{
			if(isCancelled(request))
			{
				int id = holidaysForm.getId();
				HolidaysTO holidaysTO = HolidaysHandler.getInstance().getHolidaysDetailsToEdit(id);
				if(holidaysTO != null){
					holidaysForm.setId(holidaysTO.getId());
					holidaysForm.setEmpTypeId(Integer.toString(holidaysTO.getEmpTypeId()));
				holidaysForm.setStartDate(holidaysTO.getStartDate());
				holidaysForm.setEndDate(holidaysTO.getEndDate());
				holidaysForm.setAcademicYear(Integer.toString(holidaysTO.getAcademicYear()));
				holidaysForm.setHoliday(holidaysTO.getHoliday());
				holidaysForm.setCreatedBy(holidaysTO.getCreatedBy());
				holidaysForm.setCreatedDate(holidaysTO.getCreatedDate());
				holidaysForm.setOldId(holidaysTO.getId());
				}
		
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
			}
			else if(errors.isEmpty())
			{
				setUserId(request,holidaysForm);
				boolean isUpdated;
				isUpdated = HolidaysHandler.getInstance().addHolidays(holidaysForm,"Edit");
				if(isUpdated)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOLIDAYS_DETAILS_UPDATED_SUCCESS));
					saveMessages(request,messages);
					holidaysForm.resetFields();
					setEmployeeHolidaysListToForm(holidaysForm);
				}
				if(!isUpdated)
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOLIDAYS_DETAILS_UPDATED_FAILED));
					saveErrors(request, errors);
					setEmployeeHolidaysListToForm(holidaysForm);
				}
		} else{
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			//setToListToForm(exceptionDetailseForm);
			//exceptionDetailseForm.resetFields();
		}
			
	}catch (DuplicateException duplicateException) {
		log.info("Entered updateInterviewDefinition Action duplicate exception");
		errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_ADDEXIST));
		saveErrors(request, errors);
		setEmployeeHolidaysListToForm(holidaysForm);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	} catch (ReActivateException reActivateException) {
		log.info("Entered updateInterviewDefinition Action reactivate exception");
		errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_REACTIVATE,holidaysForm.getId()));
		saveErrors(request, errors);
		setEmployeeHolidaysListToForm(holidaysForm);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}catch(Exception e)
		{
			log.error("error occured updating exception details");
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("existing updating holidays details");
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
		
	}
	
	public ActionForward deleteHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered deleteHolidays Action");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted;
		try {
			if (holidaysForm.getId() != 0) {
				int id = holidaysForm.getId();
				String uid = holidaysForm.getUserId();
				 
				isDeleted = HolidaysHandler.getInstance().deleteHolidays(id, false, uid);
			
				if (isDeleted) {
					setEmployeeHolidaysListToForm(holidaysForm);
	
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_DELETESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					holidaysForm.resetFields();
				}
			}else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_DELETEFAILURE));
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			log.info("Exception deleteHolidays");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			log.info("Exception deleteHolidays");
			String msg = super.handleApplicationException(exception);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}
	
	public ActionForward activateHolidays(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entered activateHolidays");
		HolidaysForm holidaysForm =(HolidaysForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated;
		try {
			if (holidaysForm.getId() != 0) {
				int id = holidaysForm.getId();
				String uid = holidaysForm.getUserId();
				 
				isActivated = HolidaysHandler.getInstance().deleteHolidays(id, true, uid);
				if (isActivated) {
					setEmployeeHolidaysListToForm(holidaysForm);
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_ACTIVATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					holidaysForm.resetFields();
				}
			}
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_HOLIDAYS_ACTIVATEFAILURE));
			saveErrors(request, errors);
		}
		log.debug("Exit activateHolidays");
		return mapping.findForward(CMSConstants.EMP_HOLIDAYS);
	}
}
