package com.kp.cms.actions.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.forms.employee.ExceptionDetailseForm;
import com.kp.cms.forms.employee.ManualAttendanceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.ExceptionDetailsHandler;
import com.kp.cms.handlers.employee.ManualAttendanceEntryHandler;
import com.kp.cms.handlers.employee.ViewEmployeeLeaveHandler;
import com.kp.cms.handlers.employee.WorkTimeEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.employee.ManualAttendanceEntryTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ManualAttendanceEntryAction extends BaseDispatchAction {
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		manualAttendanceEntryForm.clear();
		manualAttendanceEntryForm.setEmployeeList(CommonAjaxHandler.getInstance().getEmployeeCodeName("eCode"));
		setUserId(request, manualAttendanceEntryForm);
	/*	HttpSession session=request.getSession();
		if(session.getAttribute("employeeId")!=null)
		{
			manualAttendanceEntryForm.setEmployeeId(session.getAttribute("employeeId").toString());
		}*/
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = manualAttendanceEntryForm.validate(mapping, request);
		validateDate(manualAttendanceEntryForm,errors);
		try{
			if(errors.isEmpty())
			{
				List<ManualAttendanceEntryTO> employeesToMarkAttendanceList = ManualAttendanceEntryHandler.getInstance().getEmployeesAttendanceList(manualAttendanceEntryForm);
				manualAttendanceEntryForm.setEmployeesToMarkAttendanceList(employeesToMarkAttendanceList);
				request.setAttribute("operation","add");
				return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
			}
			else
			{
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			manualAttendanceEntryForm.setErrorMessage(msg);
			manualAttendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = manualAttendanceEntryForm.validate(mapping, request);
		boolean isAttendanceMarked;
		validateAttendanceError(manualAttendanceEntryForm, errors);
		try{
			if(errors.isEmpty())
			{
				isAttendanceMarked = ManualAttendanceEntryHandler.getInstance().markEmployeesAttendance(manualAttendanceEntryForm);
				if(isAttendanceMarked)
				{
					ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					manualAttendanceEntryForm.clear();
					return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
				}
			}
			else
			{
				saveErrors(request, errors);
			}
		}
		catch (DuplicateException dup) 
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_TAKEN));
			saveErrors(request,errors);
			request.setAttribute("operation","add");
			return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
		}
		catch (ReActivateException rea) 
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_REACTIVATE,rea.getID()));
			saveErrors(request,errors);
			request.setAttribute("operation","add");
			return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
		}
		catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			manualAttendanceEntryForm.setErrorMessage(msg);
			manualAttendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	public ActionForward editAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = manualAttendanceEntryForm.validate(mapping, request);
		try{
			if(errors.isEmpty())
			{
				ManualAttendanceEntryHandler.getInstance().editAttendance(manualAttendanceEntryForm);
				manualAttendanceEntryForm.setDummyAttendanceDate(manualAttendanceEntryForm.getAttendanceDate());
				request.setAttribute("operation","edit");
			}
			else
			{
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			manualAttendanceEntryForm.setErrorMessage(msg);
			manualAttendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	public ActionForward updateAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = manualAttendanceEntryForm.validate(mapping, request);
		boolean isAttendanceUpdated;
		validateAttendanceError(manualAttendanceEntryForm, errors);
		try{
			if(isCancelled(request))
			{
				ManualAttendanceEntryHandler.getInstance().editAttendance(manualAttendanceEntryForm);
				manualAttendanceEntryForm.setDummyAttendanceDate(manualAttendanceEntryForm.getAttendanceDate());
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			
			}else
			if(errors.isEmpty())
			{
				isAttendanceUpdated = ManualAttendanceEntryHandler.getInstance().updateEmployeesAttendance(manualAttendanceEntryForm);
				if(isAttendanceUpdated)
				{
						ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_UPDATED_SUCCESSFULLY);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						manualAttendanceEntryForm.clear();
						return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
				
				}
				else
				{
					request.setAttribute("operation","edit");
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_TAKEN));
		    		saveErrors(request,errors);
				}
			}
			else
			{
				request.setAttribute("operation","edit");
				saveErrors(request, errors);
			}
		}catch (DuplicateException dup) 
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_TAKEN));
			saveErrors(request,errors);
			request.setAttribute("operation","edit");
			return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
		}
		catch (ReActivateException rea) 
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_REACTIVATE,rea.getID()));
			saveErrors(request,errors);
			request.setAttribute("operation","edit");
			return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
		}
		catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			manualAttendanceEntryForm.setErrorMessage(msg);
			manualAttendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	public ActionForward deleteAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isAttendanceDeleted;
		try{
			if(errors.isEmpty()){
				isAttendanceDeleted = ManualAttendanceEntryHandler.getInstance().deleteAttendance(manualAttendanceEntryForm);
					
					if(isAttendanceDeleted){
						ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_DELETED_SUCCESSFULLY);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						manualAttendanceEntryForm.clear();
						return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
				
				}else{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_TAKEN));
		    		saveErrors(request,errors);
				}
			}else{
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			manualAttendanceEntryForm.setErrorMessage(msg);
			manualAttendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
	
	
	private void validateAttendanceError(ManualAttendanceEntryForm formObj,ActionErrors errors)
	{
		if(formObj.getAttendanceDate()==null || formObj.getAttendanceDate().isEmpty())
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_DATE_REQUIRED));
		}
		if (formObj.getAttendanceDate() != null && !StringUtils.isEmpty(formObj.getAttendanceDate()) && !CommonUtil.isValidDate(formObj.getAttendanceDate())) 
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_DATE_IS_INVALID));
		}
		if(errors.isEmpty())
		{
			Date startDate = CommonUtil.ConvertStringToDate(formObj.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(formObj.getEndDate());
			Date attendanceDate=CommonUtil.ConvertStringToDate(formObj.getAttendanceDate());
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			Calendar cal3=Calendar.getInstance();
			cal3.setTime(attendanceDate);
			
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal3);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.employee.manualAttendanceEntry.attendanceDate.less.startDate"));
			}
			else
			{	
				daysBetween = CommonUtil.getDaysBetweenDates(cal3, cal2);
				if(daysBetween <= 0) 
				{
					errors.add("error", new ActionError("knowledgepro.employee.manualAttendanceEntry.attendanceDate.greater.endDate"));
				}
			}	
		}
		boolean errorInTime=false;
		if(formObj.getInTimeHrs().equals("0") && formObj.getInTimeMins().equals("0"))
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.IN_TIME_REQUIRED));
			errorInTime=true;
		}
		if(formObj.getOutTimeHrs().equals("0") && formObj.getOutTimeMins().equals("0"))
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.OUT_TIME_REQUIRED));
			errorInTime=true;
		}
		if(CommonUtil.checkForEmpty(formObj.getInTimeHrs())){
			if(!StringUtils.isNumeric(formObj.getInTimeHrs())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					errorInTime=true;
				}
			}			
		}
		if(CommonUtil.checkForEmpty(formObj.getInTimeMins())){
			if(!StringUtils.isNumeric(formObj.getInTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					errorInTime=true;
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(formObj.getInTimeHrs())){
			if(Integer.parseInt(formObj.getInTimeHrs())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					errorInTime=true;
				}
			}			
		}
		if(CommonUtil.checkForEmpty(formObj.getInTimeMins())){
			if(Integer.parseInt(formObj.getInTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					errorInTime=true;
				}
			}			
		}
		if(CommonUtil.checkForEmpty(formObj.getOutTimeHrs())){
			if(!StringUtils.isNumeric(formObj.getOutTimeHrs())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					errorInTime=true;
				}
			}			
		}
		if(CommonUtil.checkForEmpty(formObj.getOutTimeMins())){
			if(!StringUtils.isNumeric(formObj.getOutTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					errorInTime=true;
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(formObj.getOutTimeHrs())){
			if(Integer.parseInt(formObj.getOutTimeHrs())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					errorInTime=true;
				}
			}			
		}
		if(CommonUtil.checkForEmpty(formObj.getOutTimeMins())){
			if(Integer.parseInt(formObj.getOutTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					errorInTime=true;
				}
			}			
		}
		if(!errorInTime)
		{
			if(Integer.parseInt(formObj.getInTimeHrs())>Integer.parseInt(formObj.getOutTimeHrs()))
			{
				errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.OUT_TIME_CAN_NOT_BE_LESS_THAN_IN_TIME));
			}
		}
		if(Integer.parseInt(formObj.getInTimeHrs())==Integer.parseInt(formObj.getOutTimeHrs()) && Integer.parseInt(formObj.getInTimeMins())==Integer.parseInt(formObj.getOutTimeMins()))
			errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.employee.manualAttendanceEntry.inTime.equals.outTime"));
		
	}
	
	private void validateDate(ManualAttendanceEntryForm formObj, ActionErrors errors) throws Exception
	{
		
		if(formObj.getStartDate()!=null && !StringUtils.isEmpty(formObj.getStartDate())&& !CommonUtil.isValidDate(formObj.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(formObj.getEndDate()!=null && !StringUtils.isEmpty(formObj.getEndDate())&& !CommonUtil.isValidDate(formObj.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(formObj.getStartDate()) && CommonUtil.checkForEmpty(formObj.getEndDate())&& CommonUtil.isValidDate(formObj.getEndDate())&& CommonUtil.isValidDate(formObj.getStartDate())){
			Date startDate = CommonUtil.ConvertStringToDate(formObj.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(formObj.getEndDate());

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
	
	public ActionForward reActivateAttendance(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ManualAttendanceEntryForm manualAttendanceEntryForm = (ManualAttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			if(errors.isEmpty()){
			setUserId(request, manualAttendanceEntryForm);
			/*String userId = manualAttendanceEntryForm.getUserId();
			int id = manualAttendanceEntryForm.getId();*/
			boolean isReActivate;
			isReActivate = ManualAttendanceEntryHandler.getInstance().reActivateAttendance(manualAttendanceEntryForm);
			if(isReActivate)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_DETAILS_REACTIVATE));
				saveMessages(request, messages);
				manualAttendanceEntryForm.clear();
				return mapping.findForward(CMSConstants.INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
			}
			else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_DETAILS_REACTIVATE_FAILED));
				saveErrors(request, errors);
			}
			}else{
				saveErrors(request, errors);
			}
			
		}
		catch(Exception e)
		{
			String msgKey = super.handleApplicationException(e);
			manualAttendanceEntryForm.setErrorMessage(msgKey);
			manualAttendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_MANUAL_ATTENDANCE_ENTRY);
	}
}