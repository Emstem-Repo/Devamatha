package com.kp.cms.actions.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.employee.ApplyLeaveForm;
import com.kp.cms.handlers.employee.ApplyLeaveHandler;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.utilities.CommonUtil;

public class ApplyLeaveAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ApplyLeaveAction.class);
	String employeeId = null;
	
	/**
	 * setting the requested data to request for applyLeave.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpApplyLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the initEmpApplyLeave in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		applyLeaveForm.resetFields();
		applyLeaveForm.setIsOndutyLeave(false);
		HttpSession session = request.getSession();
		employeeId = session.getAttribute("employeeId").toString();
		applyLeaveForm.setEmployeeId(employeeId);
		setRequestedDataToForm(applyLeaveForm);
		setAllotedLeaveListToForm(applyLeaveForm, employeeId);
		setApplyLeaveDetailsToRequest(applyLeaveForm,request);
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EMP_APPLY_LEAVE);
	}

	/**
	 * setting the leave type to form
	 * @param applyLeaveForm
	 */
	private void setRequestedDataToForm(ApplyLeaveForm applyLeaveForm) throws Exception{
		List<EmpLeaveTypeTO> leaveList=ApplyLeaveHandler.getInstance().getLeaveTypeList();
		applyLeaveForm.setLeaveList(leaveList);
	}
	
	/**
	 * applying the leaves for emp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addEmpLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the addEmpLeaves in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = applyLeaveForm.validate(mapping, request);
		validateDate(applyLeaveForm,errors);
		if (applyLeaveForm.getReason() != null &&applyLeaveForm.getReason().length() > 50) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_EMP_LEAVE));
		}
		boolean isAdded;
		try{
			if(errors.isEmpty()){
				setUserId(request, applyLeaveForm);
				applyLeaveForm.setEmployeeId(employeeId);
				
				boolean isReportingManagerAssigned=ApplyLeaveHandler.getInstance().isReportingMangerAssigned(applyLeaveForm.getEmployeeId());
				boolean isEmployeeTypeSet=ApplyLeaveHandler.getInstance().isEmployeeTypeSet(applyLeaveForm.getEmployeeId());
				if(!isReportingManagerAssigned)
				{
					errors.add("error", new ActionError("knowledgepro.emp.applyLeave.noReportingTo"));
					saveErrors(request, errors);
				}
				else
				if(!isEmployeeTypeSet)
				{
					errors.add("error", new ActionError("knowledgepro.emp.applyLeave.noEmployeeType"));
					saveErrors(request, errors);
				}
				else
				{
					boolean isDuplicate=ApplyLeaveHandler.getInstance().checkDuplicateLeaves(applyLeaveForm);
					if(isDuplicate){
						errors.add("error", new ActionError("knowledgepro.employee.applyLeave.duplicate"));
						saveErrors(request, errors);
					}
					else
					{
						Integer noOfDays=getNumberOfDaysLeaveApplied(applyLeaveForm);
						if(noOfDays==0)
						{
							errors.add("error",new ActionMessage("knowledgepro.emp.applyLeave.noOfDaysLeaveApplied"));
						}
						else
						{
							Integer noOfDaysRemaining=ApplyLeaveHandler.getInstance().getRemainingDays(applyLeaveForm);
							Integer noOfDaysPending=ApplyLeaveHandler.getInstance().getPendingDays(applyLeaveForm);
							if(noOfDaysRemaining!=null)
							{	
								if((noOfDaysRemaining-noOfDaysPending)<noOfDays)
								{
									errors.add("error",new ActionMessage("knowledgepro.emp.applyLeave.leaveRemaining", (noOfDaysRemaining-noOfDaysPending)));	
								}
							}
						}	
						if(errors.isEmpty())
						{
							applyLeaveForm.setNoOfDays(noOfDays);
							isAdded=ApplyLeaveHandler.getInstance().applyLeave(applyLeaveForm);
							if (isAdded) {
								ActionMessage message = new ActionMessage("knowledgepro.emp.leave.apply.success");
								applyLeaveForm.resetFields();
								setApplyLeaveDetailsToRequest(applyLeaveForm,request);
								messages.add("messages", message);
								saveMessages(request, messages);
							}else{
								// failed
								errors.add("error", new ActionError("knowledgepro.emp.leave.apply.failure"));
								saveErrors(request, errors);
							}
						}
						else
						{
							saveErrors(request, errors);
						}
					}
				}
			}
			else
			{
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			applyLeaveForm.setErrorMessage(msg);
			applyLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//setRequestedDataToForm(applyLeaveForm);
		setAllotedLeaveListToForm(applyLeaveForm, employeeId);
		log.info("Existing from the addEmpLeaves in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EMP_APPLY_LEAVE);
	}
	private Integer getNumberOfDaysLeaveApplied(ApplyLeaveForm applyLeaveForm) throws Exception
	{
		int noOfDays=0;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String textDate = applyLeaveForm.getFromDate().substring(3, 5) + "/" + applyLeaveForm.getFromDate().substring(0, 2)+"/" + applyLeaveForm.getFromDate().substring(6, 10);
	    String textDate1 = applyLeaveForm.getToDate().substring(3, 5) + "/" + applyLeaveForm.getToDate().substring(0, 2)+"/" + applyLeaveForm.getToDate().substring(6, 10);;
	    
	    Date frm = null;
	    Date tl = null;
		
	    try 
		{
	      frm = df.parse(textDate);
	      tl = df.parse(textDate1);
	    }
		catch (ParseException parseException) 
	    {
	      parseException.printStackTrace();
	    }
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(frm);
	    
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(tl);

	    ApplyLeaveHandler handler=ApplyLeaveHandler.getInstance();
	    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) 
	    {
	    	if(!(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) && !handler.isHoliday(calendar.getTime(),applyLeaveForm.getEmployeeId()))
	    		noOfDays++;
	    }
		
	    return noOfDays;
	}


	/**
	 * Method to validate the Date format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateDate(ApplyLeaveForm applyLeaveForm, ActionErrors errors) throws Exception{
		
		if(applyLeaveForm.getFromDate()!=null && !StringUtils.isEmpty(applyLeaveForm.getFromDate())&& !CommonUtil.isValidDate(applyLeaveForm.getFromDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(applyLeaveForm.getToDate()!=null && !StringUtils.isEmpty(applyLeaveForm.getToDate())&& !CommonUtil.isValidDate(applyLeaveForm.getToDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(applyLeaveForm.getFromDate()) && CommonUtil.checkForEmpty(applyLeaveForm.getToDate())&& CommonUtil.isValidDate(applyLeaveForm.getToDate())&& CommonUtil.isValidDate(applyLeaveForm.getFromDate())){
			Date startDate = CommonUtil.ConvertStringToDate(applyLeaveForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(applyLeaveForm.getToDate());

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
	 * setting the requested data to request for applyLeave.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpApplyOnDutyLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the initEmpApplyLeave in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		applyLeaveForm.setIsOndutyLeave(true);
		applyLeaveForm.resetFields();
		setRequestedDataToForm(applyLeaveForm);
		setEmployeeListToForm(applyLeaveForm);
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EMP_APPLY_LEAVE);
	}	
	/**
	 * setting employee map to form for selecting employee
	 * @param locationForm
	 * @throws Exception
	 */
	public void setEmployeeListToForm(ApplyLeaveForm applyLeaveForm) throws Exception{
		log.info("inside setEmployeeListToForm");
			Map<Integer, String> employeeMap = null;
			employeeMap = InventoryLocationHandler.getInstance().getEmployee();
			applyLeaveForm.setEmployeeMap(employeeMap);
		log.info("exit setEmployeeListToForm");
	}
	public void setApplyLeaveDetailsToRequest(ApplyLeaveForm applyLeaveForm,HttpServletRequest request) throws Exception{
		String employeeId=request.getSession().getAttribute("employeeId").toString();
		List<EmployeeLeaveTO> applyLeaveToList = ApplyLeaveHandler.getInstance().getApplyLeaveDetails(employeeId);
		applyLeaveForm.setApplyLeaveList(applyLeaveToList);
		//request.setAttribute("applyLeaveList", applyLeaveToList);
	}
	
	public void setAllotedLeaveListToForm(ApplyLeaveForm applyLeaveForm,String employeeId) throws Exception{
		List<EmpLeaveTO> allotedLeaveList = ApplyLeaveHandler.getInstance().getAllotedLeaveTypeList(employeeId);
		applyLeaveForm.setAllotedLeaveList(allotedLeaveList);
	}
	
	public ActionForward cancelLeave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		log.info("Entering into the cancel leave in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isCancelled;
		try
		{
			isCancelled=ApplyLeaveHandler.getInstance().cancelLeave(applyLeaveForm);
			if (isCancelled) 
			{
				ActionMessage message = new ActionMessage("knowledgepro.emp.leave.apply.success");
				applyLeaveForm.resetFields();
				setApplyLeaveDetailsToRequest(applyLeaveForm,request);
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.emp.leave.apply.failure"));
				saveErrors(request, errors);
			}
		}
		catch (Exception e) 
		{
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			applyLeaveForm.setErrorMessage(msg);
			applyLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//setRequestedDataToForm(applyLeaveForm);
		setAllotedLeaveListToForm(applyLeaveForm, employeeId);
		log.info("Existing from the addEmpLeaves in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EMP_APPLY_LEAVE);
	}
	
	public ActionForward initCancelLeave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		log.info("Entering into the cancel leave in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		try
		{
			ApplyLeaveHandler.getInstance().getLeaveDetails(applyLeaveForm);
			return mapping.findForward(CMSConstants.EMP_CANCEL_LEAVE);
			
		}
		catch (Exception e) 
		{
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			applyLeaveForm.setErrorMessage(msg);
			applyLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionForward startCancelLeave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		log.info("Entering into the cancel leave in ApplyLeaveAction");
		ApplyLeaveForm applyLeaveForm=(ApplyLeaveForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = applyLeaveForm.validate(mapping, request);
		if (applyLeaveForm.getCancelReason() != null && applyLeaveForm.getCancelReason().length() > 50) 
		{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_EMP_LEAVE));
		}
		boolean isCanceled;
		try{
			if(errors.isEmpty())
			{
				setUserId(request, applyLeaveForm);
				isCanceled=ApplyLeaveHandler.getInstance().startCancellation(applyLeaveForm);
				if (isCanceled) 
				{
					ActionMessage message = new ActionMessage("knowledgepro.emp.applyLeave.cancelLeave.started");
					applyLeaveForm.resetFields();
					setApplyLeaveDetailsToRequest(applyLeaveForm,request);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				else
				{
					// failed
					errors.add("error", new ActionError("knowledgepro.emp.applyLeave.cancelLeave.failed"));
					saveErrors(request, errors);
				}
			}
			else
			{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EMP_CANCEL_LEAVE);
			}
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			applyLeaveForm.setErrorMessage(msg);
			applyLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//setRequestedDataToForm(applyLeaveForm);
		setAllotedLeaveListToForm(applyLeaveForm, employeeId);
		log.info("Existing from the addEmpLeaves in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EMP_APPLY_LEAVE);
		
	}
	
	
}
