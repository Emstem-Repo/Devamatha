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
import com.kp.cms.bo.admin.EmpExceptionDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.ExceptionDetailseForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.ExceptionDetailsHandler;
import com.kp.cms.to.employee.ExceptionDetailsTo;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExceptionDetailsAction extends BaseDispatchAction 
{
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static final Log log = LogFactory
			.getLog(ExceptionTypesAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExceptionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExceptionDetailseForm exceptionDetailsForm = (ExceptionDetailseForm) form;
		exceptionDetailsForm.setEmployeeMap(CommonAjaxHandler.getInstance().getEmployeeCodeName("eCode"));
		exceptionDetailsForm.setExceptionTypeMap(ExceptionDetailsHandler.getInstance()
				.getExceptionTypes());
		exceptionDetailsForm.resetFields();
		setToListToForm(exceptionDetailsForm);
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExceptionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside addException Types Action");
		ExceptionDetailseForm exceptionDetailsForm = (ExceptionDetailseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = exceptionDetailsForm.validate(mapping, request);
		validateDate(exceptionDetailsForm,errors);
		
		try {
			if(errors.isEmpty())
			{
				setUserId(request, exceptionDetailsForm);
				//int id = exceptionDetailsForm.getId();
				EmpExceptionDetailsBO exceptionDetailsBO = ExceptionDetailsHandler.getInstance().duplicateCheckException(exceptionDetailsForm);
				
				/**
				 * Checks for the duplicate entry based on the fromDate ,toDate and ExceptionType. If it
				 * exists in active mode then add the appropriate error message
				 */
				if(exceptionDetailsBO != null)
				{
					if(exceptionDetailsBO.getIsActive())
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EXCEPTION_DETAILS_EXISTS));
						setToListToForm(exceptionDetailsForm);
						saveErrors(request, errors);
					}
					/**
					 * If it is in inactive mode then show the message to
					 * reactivate the same.
					 */
					else if(!exceptionDetailsBO.getIsActive())
					{
						errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.employee.exceptionDetails.exists.reactivate",exceptionDetailsBO.getId()));
						setToListToForm(exceptionDetailsForm);
						saveErrors(request, errors);
					}
					
				}
				else {
					boolean isAdded ;
					
					isAdded = ExceptionDetailsHandler.getInstance().addExceptionDetails(
							exceptionDetailsForm);
					
					/**
					 * If add operation is success then append the success
					 * message else add the appropriate error message
					 */
					if(isAdded)
					{
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXCEPTION_DETAILS_ADDED_SUCCESS));
						saveMessages(request, messages);
						setToListToForm(exceptionDetailsForm);
						exceptionDetailsForm.resetFields();
					}
					else {
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EXCEPTION_DETAILS_ADDED_FAILED));
						setToListToForm(exceptionDetailsForm);
						saveErrors(request, errors);
					}
				}
					
			} else {
				//setToListToForm(exceptionDetailsForm);
				saveErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("error in adding exception details of Action.", e);
			
				String msg = super.handleApplicationException(e);
				exceptionDetailsForm.setErrorMessage(msg);
				exceptionDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);
	}
			
			
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExceptionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("inside delete Exception Details");
		ExceptionDetailseForm exceptionDetailseForm = (ExceptionDetailseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, exceptionDetailseForm);
			String userId = exceptionDetailseForm.getUserId();
			int id = exceptionDetailseForm.getId();
			boolean isDeleted;
			isDeleted = ExceptionDetailsHandler.getInstance()
					.deleteExceptionDetails(id, userId);

			/**
			 * If delete operation is success then add the success message. Else
			 * add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.EXCEPTION_DETAILS_DELETED_SUCCESS));
				saveMessages(request, messages);
				setToListToForm(exceptionDetailseForm);
				//exceptionDetailseForm.resetFields();
			} else {
				errors.add(CMSConstants.ERRORS, new ActionError(
						CMSConstants.EXCEPTION_DETAILS_DELETED_FAILED));
				saveErrors(request, errors);
				setToListToForm(exceptionDetailseForm);
				//exceptionDetailseForm.resetFields();
			}
		} catch (Exception e) {
			log.error("error in deleting Exception Details");
			String msgKey = super.handleApplicationException(e);
			exceptionDetailseForm.setErrorMessage(msgKey);
			exceptionDetailseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Existing from deleting exception details");
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateExceptionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("Entering into reActivateExceptionDetails in ExceptionDetailsAction");
		ExceptionDetailseForm exceptionDetailseForm = (ExceptionDetailseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			setUserId(request, exceptionDetailseForm);
			String userId = exceptionDetailseForm.getUserId();
			int id = exceptionDetailseForm.getId();
			boolean isReActivate;
			isReActivate = ExceptionDetailsHandler.getInstance().reActivateExceptionDetails(id, userId);
			if(isReActivate)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXCEPTION_DETAILS_REACTIVATE));
				saveMessages(request, messages);
				setToListToForm(exceptionDetailseForm);
				//exceptionDetailseForm.resetFields();
			}
			else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EXCEPTION_DETAILS_REACTIVATE_FAILED));
				saveErrors(request, errors);
				setToListToForm(exceptionDetailseForm);
			}
			
		}
		catch(Exception e)
		{
			log.error("error in reActivating Exception Details");
			String msgKey = super.handleApplicationException(e);
			exceptionDetailseForm.setErrorMessage(msgKey);
			exceptionDetailseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Existing from reActivating exception details");
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);
		
	}
	
	/**
	 * used to edit exception details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editExceptionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExceptionDetailseForm exceptionDetailseForm = (ExceptionDetailseForm) form;
		try {
			/*int id = exceptionDetailseForm.getId();
			EmpExceptionDetailsBO exceptionDetailsBO = ExceptionDetailsTransactionImpl
					.getInstance().getExceptionDetailsOnId(id);
			if (exceptionDetailsBO != null) {
				exceptionDetailseForm.setDupId(exceptionDetailsBO.getId());
				exceptionDetailseForm.setEmployeeId(Integer.toString(exceptionDetailsBO.getEmployee().getId()));
				exceptionDetailseForm.setExceptionTypeId(Integer
						.toString(exceptionDetailsBO.getExceptionTypeBO().getId()));
				exceptionDetailseForm.setFromDate(CommonUtil
						.ConvertStringToDateFormat(CommonUtil
								.getStringDate(exceptionDetailsBO
										.getStaffStartDate()),
								ExceptionDetailsAction.SQL_DATEFORMAT,
								ExceptionDetailsAction.FROM_DATEFORMAT));
				exceptionDetailseForm.setToDate(CommonUtil
						.ConvertStringToDateFormat(CommonUtil
								.getStringDate(exceptionDetailsBO
										.getStaffEndDate()),
								ExceptionDetailsAction.SQL_DATEFORMAT,
								ExceptionDetailsAction.FROM_DATEFORMAT));
				exceptionDetailseForm.setRemarks(exceptionDetailsBO.getRemarks());
				if(exceptionDetailsBO.getStaffStartDateAm()){
					exceptionDetailseForm.setFromAM("AM");
				}
				if(exceptionDetailsBO.getStaffStartDatePm()){
					exceptionDetailseForm.setFromAM("PM");
				}
				if(exceptionDetailsBO.getStaffEndDateAm()){
					exceptionDetailseForm.setToAM("AM");
				}
				if(exceptionDetailsBO.getStaffEndDatePm()){
					exceptionDetailseForm.setToAM("PM");
				}
				exceptionDetailseForm.setRemarks(exceptionDetailsBO.getRemarks());
			}*/
			//exceptionDetailseForm.resetFields(); 
			//setToListToForm(exceptionDetailseForm);
			ExceptionDetailsHandler.getInstance().getExceptionDetailsToEdit(exceptionDetailseForm);
			request.setAttribute(CMSConstants.OPERATION,
					CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("error occured editing exception details");
			String msg = super.handleApplicationException(e);
			exceptionDetailseForm.setErrorMessage(msg);
			exceptionDetailseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("existing editing exception details");
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);

	}
	
	public ActionForward updateExceptionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("inside updateExceptionDetails of ExceptionDetailsAction");
		ExceptionDetailseForm exceptionDetailseForm = (ExceptionDetailseForm) form;
		 ActionErrors errors = exceptionDetailseForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		validateDate(exceptionDetailseForm,errors);
		try
		{
			if(isCancelled(request))
			{
			/*	int id = exceptionDetailseForm.getId();
				EmpExceptionDetailsBO exceptionDetailsBO = ExceptionDetailsTransactionImpl
				.getInstance().getExceptionDetailsOnId(id);
		
		if (exceptionDetailsBO != null) {
			exceptionDetailseForm.setEmployeeId(Integer.toString(exceptionDetailsBO.getEmployee().getId()));
			exceptionDetailseForm.setExceptionTypeId(Integer
					.toString(exceptionDetailsBO.getExceptionTypeBO().getId()));
			exceptionDetailseForm.setFromDate(CommonUtil
					.ConvertStringToDateFormat(CommonUtil
							.getStringDate(exceptionDetailsBO
									.getStaffStartDate()),
							ExceptionDetailsAction.SQL_DATEFORMAT,
							ExceptionDetailsAction.FROM_DATEFORMAT));
			exceptionDetailseForm.setToDate(CommonUtil
					.ConvertStringToDateFormat(CommonUtil
							.getStringDate(exceptionDetailsBO
									.getStaffEndDate()),
							ExceptionDetailsAction.SQL_DATEFORMAT,
							ExceptionDetailsAction.FROM_DATEFORMAT));
			exceptionDetailseForm.setRemarks(exceptionDetailsBO.getRemarks());
				}*/
		//setToListToForm(exceptionDetailseForm);
				ExceptionDetailsHandler.getInstance().getExceptionDetailsToEdit(exceptionDetailseForm);
		request.setAttribute(CMSConstants.OPERATION,
				CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);
			}
			else if(errors.isEmpty())
			{
				setUserId(request,exceptionDetailseForm);
				int id = exceptionDetailseForm.getId();
				EmpExceptionDetailsBO exceptionDetailsBO = ExceptionDetailsHandler.getInstance().duplicateCheckException(exceptionDetailseForm);
				if(exceptionDetailsBO !=null && exceptionDetailsBO.getId() != id){
				
				/**
				 * Checks for the duplicate entry based on the fromDate ,toDate and ExceptionType. If it
				 * exists in active mode then add the appropriate error message
				 */
				if(exceptionDetailsBO != null)
				{
					if(exceptionDetailsBO.getIsActive())
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EXCEPTION_DETAILS_EXISTS));
						setToListToForm(exceptionDetailseForm);
						saveErrors(request, errors);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					}
					/**
					 * If it is in inactive mode then show the message to
					 * reactivate the same.
					 */
					else if(!exceptionDetailsBO.getIsActive())
					{
						errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.employee.exceptionDetails.exists.reactivate",exceptionDetailsBO.getId()));
						setToListToForm(exceptionDetailseForm);
						saveErrors(request, errors);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					}
					
				}
			}
				else {
				boolean isUpdated;
				isUpdated = ExceptionDetailsHandler.getInstance().updateExceptionDetails(exceptionDetailseForm);
				if(isUpdated)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EXCEPTION_DETAILS_UPDATED_SUCCESS));
					saveMessages(request,messages);
					exceptionDetailseForm.resetFields();
					setToListToForm(exceptionDetailseForm);
				}
				if(!isUpdated)
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.EXCEPTION_DETAILS_UPDATED_FAILED));
					saveErrors(request, errors);
					setToListToForm(exceptionDetailseForm);
				}
			}
		} else{
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			saveErrors(request, errors);
			//setToListToForm(exceptionDetailseForm);
			//exceptionDetailseForm.resetFields();
		}
			
	}
		catch(Exception e)
		{
			log.error("error occured updating exception details");
			String msg = super.handleApplicationException(e);
			exceptionDetailseForm.setErrorMessage(msg);
			exceptionDetailseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("existing updating exception details");
		return mapping.findForward(CMSConstants.EXCEPTION_DETAILS);
		
	}
	
/**
 * used to validate fromdate to todate
 * @param exceptionDetailsForm
 * @param errors
 * @throws Exception
 */
private void validateDate(ExceptionDetailseForm exceptionDetailsForm, ActionErrors errors) throws Exception{
		
		if(exceptionDetailsForm.getFromDate()!=null && !StringUtils.isEmpty(exceptionDetailsForm.getFromDate())&& !CommonUtil.isValidDate(exceptionDetailsForm.getFromDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(exceptionDetailsForm.getToDate()!=null && !StringUtils.isEmpty(exceptionDetailsForm.getToDate())&& !CommonUtil.isValidDate(exceptionDetailsForm.getToDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(exceptionDetailsForm.getFromDate()) && CommonUtil.checkForEmpty(exceptionDetailsForm.getToDate())&& CommonUtil.isValidDate(exceptionDetailsForm.getToDate())&& CommonUtil.isValidDate(exceptionDetailsForm.getFromDate())){
			Date startDate = CommonUtil.ConvertStringToDate(exceptionDetailsForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(exceptionDetailsForm.getToDate());

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

	/**
	 * used to set ToList to Form
	 * @param exceptionDetailsForm
	 * @throws Exception
	 */
	public void setToListToForm(ExceptionDetailseForm exceptionDetailsForm)
			throws Exception {
		List<ExceptionDetailsTo> exceptionDetailsTo = ExceptionDetailsHandler
				.getInstance().getExceptionDetails();
		exceptionDetailsForm.setExceptionList(exceptionDetailsTo);
	}
	
}
