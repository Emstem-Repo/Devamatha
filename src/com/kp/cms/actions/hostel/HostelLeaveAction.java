package com.kp.cms.actions.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.HostelLeaveForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelLeaveHandler;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;
import com.kp.cms.transactionsimpl.hostel.HostelLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class HostelLeaveAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(HostelLeaveAction.class);
	private static Map<String, Integer> monthMap = null;
	/**
	 * This method performs the retrieving of initHostelLeave in HostelLeaveAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward initHostelLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		hostelLeaveForm.reset(mapping, request);
		setRequiredDataToForm(hostelLeaveForm);
		// /* code added by chandra
		if (hostelLeaveForm.getRgNoFromHlTransaction() != null && !hostelLeaveForm.getRgNoFromHlTransaction().isEmpty()) {
			hostelLeaveForm.setRegisterNo(hostelLeaveForm.getRgNoFromHlTransaction());
			hostelLeaveForm.setAcademicYear1(hostelLeaveForm.getAcademicYear());
			if (hostelLeaveForm.getAcademicYear1() != null && !hostelLeaveForm.getAcademicYear1().isEmpty() 
					&& hostelLeaveForm.getRegisterNo() != null && !hostelLeaveForm.getRegisterNo().isEmpty()) {
				setStudentDetails(hostelLeaveForm);
			}
		}
		// */ code added by chandra
		return mapping.findForward(CMSConstants.HOSTEL_LEAVE);
	}

	/**
	 * This method performs the retrieving of submitHostelLeave in HostelLeaveAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward submitHostelLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelLeaveForm.validate(mapping, request);
		validateAttendanceDate(hostelLeaveForm, errors);
		HttpSession session=request.getSession();
		boolean isDuplicate;
		boolean registerNo;
		String mode="save";
		if (errors.isEmpty()) {	
			try {
		setUserId(request, hostelLeaveForm);
//		verifying whether registerNO is valid or not and also setting mobileNo, email of the student and parent. 
		registerNo=HostelLeaveTransactionImpl.getInstance().verifyRegisterNo(hostelLeaveForm);
		if(registerNo)
		{
			validateHostelLeave(hostelLeaveForm,errors);
			if(!errors.isEmpty()){
				 saveErrors(request, errors);
				 setRequiredDataToForm(hostelLeaveForm);
				 setStudentDetails(hostelLeaveForm);
			}
			else{
		isDuplicate = HostelLeaveHandler.getInstance().duplicateHostelLeaveCheck(hostelLeaveForm);
		if(isDuplicate)
		 {
			 errors.add("errors", new ActionError(CMSConstants.HOSTEL_LEAVE_DETAILS_EXISTS));
			 saveErrors(request, errors);
			 setRequiredDataToForm(hostelLeaveForm);
			 setStudentDetails(hostelLeaveForm);
		 }else 
		 {
		boolean isAdded = HostelLeaveHandler.getInstance().saveOrUpdateLeaveDetails(hostelLeaveForm,session,mode);
			if(isAdded){
//				Sending sms and email ,whenever the leave entry is entered by Admin.
//				code added by sudhir.
				HostelLeaveHandler.getInstance().sendSMSAndEmailToStudents(hostelLeaveForm);
				HostelLeaveHandler.getInstance().sendSMSTOParent(hostelLeaveForm);
//				
				ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_LEAVE_SUCCESS);// Adding success message.
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelLeaveForm.reset(mapping, request);
				session.setAttribute("hostelAdmissionId", null);
			}else{
				ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_LEAVE_FAILURE);// Adding failure message.
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequiredDataToForm(hostelLeaveForm);
				}
			}
		}
		}
		else{
			errors.add("error", new ActionError("knowledgepro.hostel.leave.student.notcheckedIn"));
			saveErrors(request, errors);
			setRequiredDataToForm(hostelLeaveForm);
			return mapping.findForward(CMSConstants.HOSTEL_LEAVE);	
		}
		}catch (BusinessException businessException) {
				log.info("Exception submitHostelLeave");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				hostelLeaveForm.setErrorMessage(msg);
				hostelLeaveForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			}
		
			else {
				addErrors(request, errors);
				setRequiredDataToForm(hostelLeaveForm);
				setStudentDetails(hostelLeaveForm);
				//setHostelEntryDetailsToRequest(hostelLeaveForm);
				return mapping.findForward(CMSConstants.HOSTEL_LEAVE);
			}
		
		setRequiredDataToForm(hostelLeaveForm);
		//setHostelEntryDetailsToRequest(hostelLeaveForm);
		return mapping.findForward(CMSConstants.HOSTEL_LEAVE);
	}
	
	
	/**
	 * This method is used to set Leave Type to form.
	 * @param hostelLeaveForm
	 * @param request
	 * @throws Exception
	 */
	
	private void setRequiredDataToForm(HostelLeaveForm hostelLeaveForm) throws Exception {
		List<HlLeave> leaveTypeList = HostelLeaveHandler.getInstance().getLeaveTypeList(); 
		hostelLeaveForm.setLeaveTypeList(leaveTypeList);
	}
	
	/**
	 * This method is used to validate date fields.
	 * @param hostelLeaveForm
	 * @param errors
	 * This method is used to validate DATE 
	 */
	private void validateAttendanceDate(HostelLeaveForm hostelLeaveForm,ActionMessages errors) {
			if(hostelLeaveForm.getLeaveFrom()!=null && !StringUtils.isEmpty(hostelLeaveForm.getLeaveFrom())&& !CommonUtil.isValidDate(hostelLeaveForm.getLeaveFrom())){
					errors.add("errors",new ActionError("knowledgepro.hostel.leavefrom"));
			}
			if(hostelLeaveForm.getLeaveTo()!=null && !StringUtils.isEmpty(hostelLeaveForm.getLeaveTo())&& !CommonUtil.isValidDate(hostelLeaveForm.getLeaveTo())){
					errors.add("errors",new ActionError("knowledgepro.hostel.leaveto"));
			}
			if(hostelLeaveForm.getRegisterNo()==null ||hostelLeaveForm.getRegisterNo().isEmpty()){
				errors.add("errors",new ActionError(CMSConstants.HOSTEL_LEAVE_REGISTRATIONNO_REQUIRED));
			}
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			   Date date = new Date();
			   String currentDate = dateFormat.format(date);
			if(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveFrom()).before(CommonUtil.ConvertStringToDate(currentDate))){
					errors.add("errors",new ActionError("knowledgepro.hostel.leavefrom.lessThan.currentDate"));
			}
			if(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).before(CommonUtil.ConvertStringToDate(currentDate))){
				errors.add("errors",new ActionError("knowledgepro.hostel.leaveto.lessThan.currentDate"));
			}
			
		
	}
	
	/**
	 * used to set Hostel Entry Details to Form
	 * @param formObj
	 * @throws Exception
	 */
	public void setHostelEntryDetailsToRequest(HostelLeaveForm formObj) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		formObj.setHostelList(hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}

	public ActionForward initCancelLeaveHostel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE);
	}

	public ActionForward studentLeaveDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HostelLeaveForm  hostelLeaveForm=(HostelLeaveForm) form;
		ActionErrors errors = hostelLeaveForm.validate(mapping, request);
		if(errors.isEmpty())
		{
		try {
			List<HostelTO> hostelTOList=HostelLeaveHandler.getInstance().getStudentLeaveDetails(hostelLeaveForm);
			if(hostelTOList!=null && !hostelTOList.isEmpty())
			{
               hostelLeaveForm.setHostelList(hostelTOList);
			   hostelLeaveForm.setTempAcademicYear(null);
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.hostel.leave.invalid.registerNo"));
				hostelLeaveForm.setHostelList(null);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else
		{
		addErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE);
	}
	
	public ActionForward editStudentLeaveDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
       HostelLeaveForm hostelLeaveForm =(HostelLeaveForm) form;
       HttpSession session=request.getSession();
       try {
		HostelLeaveHandler.getInstance().editStudentLeaveDetails(hostelLeaveForm,session);
		setRequiredDataToForm(hostelLeaveForm);
	} catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		hostelLeaveForm.setErrorMessage(msg);
		hostelLeaveForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.HOSTEL_EDIT_STUDENT_LEAVE);
		
	}
	
	public ActionForward updateHostelLeave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HostelLeaveForm hostelLeaveForm=(HostelLeaveForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		hostelLeaveForm.setRegisterNo((String)session.getAttribute("registerNumber"));
		ActionErrors errors = hostelLeaveForm.validate(mapping, request);
		String mode="update";
		boolean isDuplicate;
		boolean isUpdated;
		if (errors.isEmpty()) {	
		try {
			setUserId(request, hostelLeaveForm);
			validateHostelLeave(hostelLeaveForm,errors);
			if(!errors.isEmpty()){
				 saveErrors(request, errors);
				 setRequiredDataToForm(hostelLeaveForm);
				 setStudentDetails(hostelLeaveForm);
			}else{
			isDuplicate = HostelLeaveHandler.getInstance().duplicateHostelLeaveCheck(hostelLeaveForm);
			if(isDuplicate)
			 {
				 errors.add("errors", new ActionError(CMSConstants.HOSTEL_LEAVE_DETAILS_EXISTS));
				 saveErrors(request, errors);
			 }else 
			 {
			isUpdated = HostelLeaveHandler.getInstance().saveOrUpdateLeaveDetails(hostelLeaveForm,session,mode);
		if(isUpdated){
			ActionMessage message = new ActionMessage("knowledgepro.hostel.leave.updated.Success");// Updating success message.
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelLeaveForm.reset(mapping, request);
		}else{
			ActionMessage message = new ActionMessage("knowledgepro.hostel.leave.updated.Failure");// Updating failure message.
			messages.add("messages", message);
			saveMessages(request, messages);
			}
		 }
	    }
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}
		else {
			addErrors(request, errors);
			setRequiredDataToForm(hostelLeaveForm);
			return mapping.findForward(CMSConstants.HOSTEL_EDIT_STUDENT_LEAVE);
		}
		return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE);
	}
	
	public ActionForward reasonForCancelLeave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HostelLeaveForm hostelLeaveForm =(HostelLeaveForm) form;
		HttpSession session =request.getSession();
		session.setAttribute("studentLeaveId", hostelLeaveForm.getId());
		return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE_FOR_REASON);
	}
	public ActionForward updateReasonForCancelLeave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HostelLeaveForm  hostelLeaveForm=(HostelLeaveForm) form;
		HttpSession session=request.getSession();
		ActionMessages  messages=new ActionMessages();
		ActionErrors errors = hostelLeaveForm.validate(mapping, request);
		setUserId(request, hostelLeaveForm);
		if(errors.isEmpty())
		{
		try {
		String id= (String) session.getAttribute("studentLeaveId");
			boolean isCanceled=HostelLeaveHandler.getInstance().updateReasonForCancelLeave(hostelLeaveForm,id);
			if(isCanceled)
			{
				ActionMessage message = new ActionMessage("knowledgepro.hostel.leave.canceled.Success");// Adding success message.
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelLeaveForm.reset(mapping, request);	
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.hostel.leave.canceled.failure");// Adding failure message.
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}
		else
		{
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE_FOR_REASON);
		}
		return mapping.findForward(CMSConstants.HOSTEL_CANCEl_LEAVE);
	}
	public void setStudentDetails(HostelLeaveForm hostelLeaveForm) throws Exception
	{
	 HlAdmissionBo hlAdmissionBo=HostelLeaveHandler.getInstance().getStudentDetailsByFormCall(hostelLeaveForm);
	 if(hlAdmissionBo!=null )
		{
				if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
				{
					hostelLeaveForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				}
				if(hlAdmissionBo.getRoomId()!=null )
				{
				if(hlAdmissionBo.getRoomId().getName()!=null && !hlAdmissionBo.getRoomId().getName().isEmpty())
				{ 
                 hostelLeaveForm.setRoomNo(hlAdmissionBo.getRoomId().getName());
				}
				if(hlAdmissionBo.getRoomId().getHlBlock().getName()!=null && !hlAdmissionBo.getRoomId().getHlBlock().getName().isEmpty())
				{
					hostelLeaveForm.setStudentBlock(hlAdmissionBo.getRoomId().getHlBlock().getName());
				}
				if(hlAdmissionBo.getRoomId().getHlUnit().getName()!=null && !hlAdmissionBo.getRoomId().getHlUnit().getName().isEmpty())
				{
				    hostelLeaveForm.setStudentUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
				}
				}
				if(hlAdmissionBo.getBedId()!=null){
				if(hlAdmissionBo.getBedId().getBedNo()!=null && !hlAdmissionBo.getBedId().getBedNo().isEmpty())
				{
				    hostelLeaveForm.setBedNo(hlAdmissionBo.getBedId().getBedNo());
				}
				}
				if(hlAdmissionBo.getStudentId().getClassSchemewise()!=null)
				{
				if(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName()!=null && !hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName().isEmpty())
				{
					hostelLeaveForm.setClassName(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
				}
				}
				if(hlAdmissionBo.getHostelId().getName()!=null && !hlAdmissionBo.getHostelId().getName().isEmpty())
				{
					hostelLeaveForm.setHostelName(hlAdmissionBo.getHostelId().getName());
				}
				hostelLeaveForm.setDisplayStudentDetails(true);
	}
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentHostelLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		clearForm(hostelLeaveForm);
		try{
			HttpSession session = request.getSession();
			HostelLeaveHandler.getInstance().setStudentHostelDetailsToForm(hostelLeaveForm,session.getAttribute("studentid").toString());
			List<HlLeave> leaveTypeList = HostelLeaveHandler.getInstance().getLeaveTypeList(); 
			hostelLeaveForm.setLeaveTypeList(leaveTypeList);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE);
	}
	/**
	 * @param hostelLeaveForm
	 */
	private void clearForm(HostelLeaveForm hostelLeaveForm) {
		hostelLeaveForm.setNoRecordFound(null);
		
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveApplyLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		ActionMessages  messages=new ActionMessages();
		ActionErrors errors = hostelLeaveForm.validate(mapping, request);
		hostelLeaveForm.setErrorMessage(null);
		try{
			if(errors.isEmpty())
				validateForm(hostelLeaveForm,errors);
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE);
			}else{
				boolean duplicate = HostelLeaveHandler.getInstance().checkForDuplicateLeave(hostelLeaveForm);
				/*modified by sudhir*/
				if(duplicate){
					if(hostelLeaveForm.getErrorMessage()==null){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.already.exists"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE);
					}
					errors .add( CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message",
					"Leave Application already exits for "+ hostelLeaveForm.getStartDate()));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE);
					/*-------------------*/
				}else{
					boolean save  = HostelLeaveHandler.getInstance().saveLeave(hostelLeaveForm);
					if(save){
						HostelLeaveHandler.getInstance().sendSMSAndEmailToStudents(hostelLeaveForm);
						HostelLeaveHandler.getInstance().sendSMSTOParent(hostelLeaveForm);
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Leaves"));
						saveMessages(request, messages);
						hostelLeaveForm.reset(mapping, request);
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Leaves"));
						saveErrors(request, errors);
					}
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE);
	}

	/**
	 * @param hostelLeaveForm
	 * @param errors
	 * @throws Exception 
	 */
	private void validateForm(HostelLeaveForm hostelLeaveForm,
			ActionErrors errors) throws Exception {
		if(CommonUtil.checkForEmpty(hostelLeaveForm.getStartDate()) && CommonUtil.checkForEmpty(hostelLeaveForm.getEndDate())){
			if(CommonUtil.isValidDate(hostelLeaveForm.getStartDate())){
				if(CommonUtil.isValidDate(hostelLeaveForm.getEndDate())){
					
				Date startDate = CommonUtil.ConvertStringToDate(hostelLeaveForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(hostelLeaveForm.getEndDate());
				Date currentDate = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				int totalDays = cal1.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
				// code added by sudhir
				String monthName = CommonUtil.getMonthName(currentDate.getMonth());
				int monthDays = 0;
				if(!monthName.equalsIgnoreCase("Feb")){
					 monthDays = monthMap.get(monthName);
				}else{
					if ((currentDate.getYear() % 400 == 0) || ((currentDate.getYear()  % 4 == 0) && (currentDate.getYear()  % 100 != 0))) {
						monthDays = 29;
					} else {
						monthDays = 28;
					}
				}
				
				long limitToOneMonth = CommonUtil.getDaysBetweenDates(cal, cal1);
				if(limitToOneMonth> monthDays){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
				}
				
				//
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}else if(daysBetween==1){
					if(hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening") && hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning"))
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.select.session"));
				}else if(daysBetween > 15){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
				}
				/* code added by sudhir */
	//			if leave applied dates are weekends or holidays then only leave is giving ,otherwise it is not allowing to take leave.
				if(errors.isEmpty()){
					boolean leaveNotAvailable = false;
					Calendar cal3 = Calendar.getInstance();
					Date d = cal1.getTime();
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					cal3.setTime(d);
					cal3.add(Calendar.DATE, 1);
					String holidayDate = dateFormat.format(cal3.getTime());
					if(CommonUtil.isSaturday(cal1) && CommonUtil.isMonday(cal2)){
						if(!CommonUtil.isHoliday(hostelLeaveForm.getStartDate()) && !CommonUtil.isHoliday(hostelLeaveForm.getEndDate())){
							if(!hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening") || !hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning")){
								leaveNotAvailable = true;
							}else if( daysBetween!=3){
								leaveNotAvailable = true;
							}
						}else if(CommonUtil.isHoliday(hostelLeaveForm.getStartDate()) && !CommonUtil.isHoliday(hostelLeaveForm.getEndDate())){
							if(!hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning")){
								leaveNotAvailable = true;
							}else{
								leaveNotAvailable = true;
							}
						}else {
							leaveNotAvailable = true;
						}
					}else if(CommonUtil.isSaturday(cal1) && CommonUtil.isSunday(cal2)){
						if(!hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening")){
							leaveNotAvailable = true;
						}else if( daysBetween!=2){
							 leaveNotAvailable = true;
						}
					}else if(CommonUtil.isSunday(cal1) && CommonUtil.isMonday(cal2)){
						if(!hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning")){
							if(!CommonUtil.isHoliday(hostelLeaveForm.getEndDate())){
								leaveNotAvailable = true;
							}
						}else if( daysBetween!=2){
							leaveNotAvailable = true;
						}
					} else if(CommonUtil.isSunday(cal1) && CommonUtil.isSunday(cal2)){
						if( daysBetween!=1){
							leaveNotAvailable = true;
						}
					}else if(CommonUtil.isSaturday(cal1) && CommonUtil.isHoliday(hostelLeaveForm.getEndDate())){
						if(!hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening")){
							leaveNotAvailable = true;
						}else{
							leaveNotAvailable = checkNextDayIsHolidayOrNot(daysBetween,hostelLeaveForm.getStartDate(),hostelLeaveForm,dateFormat,cal2);
						}
					}else if(CommonUtil.isSaturday(cal1) && !CommonUtil.isHoliday(hostelLeaveForm.getEndDate())){
						if(!hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening")){
							leaveNotAvailable = true;
						}else{
							leaveNotAvailable = checkNextDayIsHolidayOrNot(daysBetween,hostelLeaveForm.getStartDate(),hostelLeaveForm,dateFormat,cal2);
						}
					}
	//				checking startdate is  holiday or not
					else if(CommonUtil.isHoliday(hostelLeaveForm.getStartDate())){
						if(hostelLeaveForm.getStartDate().equalsIgnoreCase(hostelLeaveForm.getEndDate())){
							leaveNotAvailable = false;
						}else {
							String nextDayHoliday = hostelLeaveForm.getStartDate();
							leaveNotAvailable = checkNextDayIsHolidayOrNot(daysBetween,nextDayHoliday,hostelLeaveForm,dateFormat,cal2);
							
						}
					} 
	//				checking nextday is holiday or not
					else if(CommonUtil.isHoliday(holidayDate)){
						String endDay = dateFormat.format(cal2.getTime());
					   if(CommonUtil.isHoliday(holidayDate)&& !CommonUtil.isHoliday(endDay)){
							 if(!hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening")|| !hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning")){
								 leaveNotAvailable = true;
							 }else {
								 String nextDayHoliday = holidayDate;
								 leaveNotAvailable = checkNextDayIsHolidayOrNot(daysBetween,nextDayHoliday,hostelLeaveForm,dateFormat,cal2);
									
							 }
					   } else{
						   leaveNotAvailable = false;
					   }
					}else{
						leaveNotAvailable = true;
					}
					if(leaveNotAvailable){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
					}
				}
				
				/* -----------------------*/
					if(errors.isEmpty()){
						if(hostelLeaveForm.getUnitsTo() != null){
							if(!CommonUtil.isSaturday(cal)){
								if(totalDays==0){
									//errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.today.apply",hostelLeaveForm.getStartDate()));
									errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
								}
								if(errors.isEmpty() && hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays() != null){
									if(totalDays<Integer.parseInt(hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays())){
			//							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.noofdays",hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays()));
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
									}
								}
								if(errors.isEmpty() && totalDays==1){
									if(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours() != null && !hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours().isEmpty()
											&& hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin() != null && !hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin().isEmpty()){
										if(currentDate.getHours()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours())){
	//										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin()));
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
										}else if(currentDate.getHours()==Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours())){
											if(currentDate.getMinutes()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin())){
	//											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin()));
												errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
											}
										}
									}
								}
							}else{
								if(hostelLeaveForm.getUnitsTo().getApplyBeforeHours()!=null && !hostelLeaveForm.getUnitsTo().getApplyBeforeHours().isEmpty()
										&& hostelLeaveForm.getUnitsTo().getApplyBeforeMin() != null && !hostelLeaveForm.getUnitsTo().getApplyBeforeMin().isEmpty())
								if(totalDays==0){
									if(hostelLeaveForm.getUnitsTo().getApplyBeforeHours() != null && hostelLeaveForm.getUnitsTo().getApplyBeforeMin() != null){
										if(currentDate.getHours()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeHours())){
	//										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeMin()));
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
										}else if(currentDate.getHours()==Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeHours())){
											if(currentDate.getMinutes()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeMin())){
	//											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeMin()));
												errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
											}
										}
									}
								}else if(hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays() != null && !hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays().trim().isEmpty() &&
										totalDays>=Integer.parseInt(hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays())){
	//								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.noofdays",hostelLeaveForm.getUnitsTo().getLeaveBeforeNoOfDays()));
									errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));}
								if(totalDays==1){
									if(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours() != null && !hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours().isEmpty()
											&& hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin() != null && !hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin().isEmpty()){
										if(currentDate.getHours()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours())){
	//										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin()));
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));
										}else if(currentDate.getHours()==Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours())){
											if(currentDate.getMinutes()>Integer.parseInt(hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin())){
	//											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.before.time",hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayHours()+":"+hostelLeaveForm.getUnitsTo().getApplyBeforeNextDayMin()));
												errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.not.available",hostelLeaveForm.getStartDate(),hostelLeaveForm.getEndDate()));}
										}
									}
								}
							}
						}
						}
					}else{
						errors.add("errors",new ActionError("knowledgepro.hostel.leaveto"));
					}
			}else{
				errors.add("errors",new ActionError("knowledgepro.hostel.leavefrom"));
			}
		}
	}
	
	static {
		monthMap = new HashMap<String, Integer>();
		monthMap.put("Jan",31);
		monthMap.put("Feb",28);
		monthMap.put("Mar",31);
		monthMap.put("Apr",30);
		monthMap.put("May",31);
		monthMap.put("Jun",30);
		monthMap.put("Jul",31);
		monthMap.put("Aug",31);
		monthMap.put("Sep",30);
		monthMap.put("Oct",31);
		monthMap.put("Nov",30);
		monthMap.put("Dec",31);
		
	}

	/**
	 * @param daysBetween
	 * @param nextDayHoliday
	 * @param hostelLeaveForm
	 * @param dateFormat
	 * @param cal2 
	 * @return
	 * @throws Exception
	 */
	private boolean checkNextDayIsHolidayOrNot(long daysBetween, String nextDayHoliday, HostelLeaveForm hostelLeaveForm, DateFormat dateFormat, Calendar cal2) throws Exception {
		boolean leaveNotAvailable = false;
		for (int i = 1; i <= daysBetween - 1; i++) {
			int dd = Integer.parseInt(nextDayHoliday.substring(0, 2));
			int mm = Integer.parseInt(nextDayHoliday.substring(3, 5)) - 1;
			int yy = Integer.parseInt(nextDayHoliday.substring(6, 10));
			Calendar holidayNextDay = Calendar.getInstance();
			holidayNextDay.set(yy, mm, dd);
			holidayNextDay.add(Calendar.DATE, 1);
			nextDayHoliday = dateFormat.format(holidayNextDay.getTime());
			if (cal2.before(holidayNextDay)) {
				if (!hostelLeaveForm.getEndDate().equalsIgnoreCase( nextDayHoliday)) {
					break;
				}
			}
			if (CommonUtil.isSunday(holidayNextDay)) {
				leaveNotAvailable = false;
			} else if (!CommonUtil.isHoliday(nextDayHoliday)) {
				if(!nextDayHoliday.equalsIgnoreCase(hostelLeaveForm.getEndDate())){
					leaveNotAvailable = true;
					break;
				} else if (!hostelLeaveForm.getLeaveToSession().equalsIgnoreCase( "Morning")) {
						leaveNotAvailable = true;
					}
					
			} else if (CommonUtil.isHoliday(nextDayHoliday)) {
				leaveNotAvailable = false;
			} else {
				/*
				 * if(nextDayHoliday.equalsIgnoreCase(hostelLeaveForm.getEndDate(
				 * ))){ leaveNotAvailable = false; }else{
				 */
				leaveNotAvailable = true;
				// }
			}
		}
		return leaveNotAvailable;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewStudentLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		try{
			HttpSession session = request.getSession();
			List<HostelUnitsTO> totalLeaves = HostelLeaveHandler.getInstance().getTotalLeaves(session.getAttribute("studentid").toString(),hostelLeaveForm);
			hostelLeaveForm.setTotalLeaves(totalLeaves);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE_VIEW);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelStudentLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		try{
			HostelLeaveHandler.getInstance().cancelLeave(hostelLeaveForm.getHlLeaveId());
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelLeaveForm.setErrorMessage(msg);
			hostelLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_HOSTEL_LEAVE_VIEW);
	}
	
	private void validateHostelLeave(HostelLeaveForm hostelLeaveForm,ActionErrors errors)
	{
		if(CommonUtil.checkForEmpty(hostelLeaveForm.getLeaveFrom()) && CommonUtil.checkForEmpty(hostelLeaveForm.getLeaveTo()) &&
				CommonUtil.isValidDate(hostelLeaveForm.getLeaveFrom()) && CommonUtil.isValidDate(hostelLeaveForm.getLeaveTo())){
			Date startDate = CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveFrom());
			Date endDate = CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo());
			Date currentDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			//int totalDays = cal1.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}else if(daysBetween==1){
				if(hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening") && hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning"))
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.select.session"));
			}
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPreviousLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveForm hostelLeaveForm = (HostelLeaveForm)form;
		hostelLeaveForm.setPreviousList(null);
		hostelLeaveForm.setStudentName(null);
		hostelLeaveForm.setClassName(null);
		try{
			String regNo=hostelLeaveForm.getRegisterNo();
			List<HostelLeaveApprovalTo> previousList = HostelLeaveHandler.getInstance().getPreviousList(regNo,hostelLeaveForm);
			hostelLeaveForm.setPreviousList(previousList);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(CMSConstants.VIEW_PREVIOUS_LEAVE_DETAILS);
	}
}
