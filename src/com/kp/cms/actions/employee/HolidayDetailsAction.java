package com.kp.cms.actions.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.admin.SubjectAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.employee.HolidayDetailsHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.to.employee.HolidayDetailsTO;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.utilities.CommonUtil;

public class HolidayDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SubjectAction.class);
	
	public ActionForward initHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		setRequestedDataToForm(holidaysForm);
		holidaysForm.reset();
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
	}
	public ActionForward addHolidays(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		setUserId(request, holidaysForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = holidaysForm.validate(mapping, request);
		HttpSession session=request.getSession();
		validateDate(holidaysForm, errors,request);
		if (errors.isEmpty()&& messages.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=HolidayDetailsHandler.getInstance().duplicateCheck(holidaysForm,errors,session);
				if(!isDuplicate){
				isAdded = HolidayDetailsHandler.getInstance().addHolidays(holidaysForm);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.holidays.add.success"));
					saveMessages(request, messages);
					holidaysForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.holidays.add.failed"));
					addErrors(request, errors);
					holidaysForm.reset();
				}
				}
			} catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				holidaysForm.setErrorMessage(msg);
				holidaysForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(holidaysForm);
			return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
		}
		setRequestedDataToForm(holidaysForm);
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
	}
	public void setRequestedDataToForm(HolidayDetailsForm holidaysForm) throws Exception {
		// 1. Set the PayScale list
		List<HolidayDetailsTO> holidaysList = HolidayDetailsHandler.getInstance().getHolidaysList();
		holidaysForm.setHolidaysTO(holidaysList);
	}
	public void validateDate(HolidayDetailsForm holidaysForm,
			ActionErrors actionErrors,HttpServletRequest request) {
		log.info("entering into validateDate of HolidaysDetailsAction class.");
		ActionError message=null;
		if (holidaysForm.getStartDate() != null
				&& holidaysForm.getStartDate().length() != 0
				&& holidaysForm.getEndDate() != null
				&& holidaysForm.getEndDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(holidaysForm
					.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(holidaysForm
					.getEndDate());
			if(!startDate.after(endDate) && !endDate.before(startDate)){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if (daysBetween <= 0) {
				actionErrors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
			}else{
				message = new ActionError(
				"knowledgepro.employee.holidays.dateError");
				actionErrors.add(CMSConstants.MESSAGES, message);
				addErrors(request, actionErrors);
			}
		}
		log.info("exit of validateDate of ApproveLeaveAction class.");
	}
	public ActionForward editHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		log.debug("Entering EditHolidays ");
		try {
			HolidayDetailsHandler.getInstance().editHolidays(holidaysForm);
			request.setAttribute("holidaysOperation", "edit");
			log.debug("Leaving EditHolidays ");
		} catch (Exception e) {
			log.error("error in editing holidays...", e);
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
	}
	public ActionForward updateHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updateHolidays Action");
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidaysForm.validate(mapping, request);
		HttpSession session=request.getSession();
		boolean isUpdated = false;
		validateDate(holidaysForm, errors,request);
        if(errors.isEmpty() && messages.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				holidaysForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				HolidayDetailsHandler.getInstance().editHolidays(holidaysForm);
				request.setAttribute("holidaysOperation", "edit");
				return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
			}
			setUserId(request, holidaysForm); // setting user id to update
			boolean isDuplicate=HolidayDetailsHandler.getInstance().duplicateCheck(holidaysForm,errors,session);
			if(!isDuplicate){
			isUpdated = HolidayDetailsHandler.getInstance()
					.updatePayScale(holidaysForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.holidays.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				holidaysForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.holidays.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				holidaysForm.reset();
			}}
		} catch (Exception e) {
			log.error("Error occured in edit holidays", e);
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(holidaysForm);
			request.setAttribute("holidaysOperation", "edit");
			return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
		}
		setRequestedDataToForm(holidaysForm);
		saveErrors(request, errors);
		log.debug("Exit: action class updateHolidays");
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);

	}
	public ActionForward deleteHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = HolidayDetailsHandler.getInstance()
					.deleteHolidays(holidaysForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.holidays.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				holidaysForm.reset();
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.holidays.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
				holidaysForm.reset();
			}
			setRequestedDataToForm(holidaysForm);
		} catch (Exception e) {
			log.error("error submit payscale...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				holidaysForm.setErrorMessage(msg);
				holidaysForm.setErrorStack(e.getMessage());
				holidaysForm.reset();
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				holidaysForm.setErrorMessage(msg);
				holidaysForm.setErrorStack(e.getMessage());
				holidaysForm.reset();
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete PayScale ");
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS);
	}
	public ActionForward reactivateHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivatePayscale Action");
		HolidayDetailsForm holidaysForm = (HolidayDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, holidaysForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = holidaysForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			holidaysForm.setId(Integer.parseInt(duplicateId));
			isReactivate = HolidayDetailsHandler.getInstance().reactivateHolidays(holidaysForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.holidays.reactivate.success"));
				setRequestedDataToForm(holidaysForm);
				holidaysForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(holidaysForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.holidays.reactivate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateHolidays of HolidayDetailsAction", e);
			String msg = super.handleApplicationException(e);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateHolidays of HolidayDetailsAction");
		return mapping.findForward(CMSConstants.EMP_INIT_HOLIDAYS); 
	}
}
