package com.kp.cms.actions.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.employee.ModifyEmployeeLeaveForm;
import com.kp.cms.handlers.employee.EmployeeApplyLeaveHandler;
import com.kp.cms.handlers.employee.ModifyEmployeeLeaveHandler;
import com.kp.cms.to.admin.ModifyEmployeeLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ModifyEmployeeLeaveAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ModifyEmployeeLeaveAction.class);
	private static Map<String,Integer> monthMap = null;
	
	static {
		monthMap = new HashMap<String,Integer>();
		monthMap.put("JANUARY",1);
		monthMap.put("FEBRUARY",2);
		monthMap.put("MARCH",3);
		monthMap.put("APRIL",4 );
		monthMap.put("MAY",5 );
		monthMap.put("JUNE",6 );
		monthMap.put("JULY",7 );
		monthMap.put("AUGUST",8 );
		monthMap.put("SEPTEMBER",9 );
		monthMap.put("OCTOBER",10 );
		monthMap.put("NOVEMBER",11 );
		monthMap.put("DECEMBER",12 );
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initModofyEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initModofyEmployeeLeave in ModifyLeaveAction");
		ModifyEmployeeLeaveForm objform = (ModifyEmployeeLeaveForm) form;
		objform.resetFields();
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.MODIFY_EMPLOYEE_LEAVE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewEmployeeLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the viewEmployeeLeaveDetails in ModifyLeaveAction");
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request,modifyEmployeeLeaveForm);
		try {
			if((modifyEmployeeLeaveForm.getFingerPrintId()==null || modifyEmployeeLeaveForm.getFingerPrintId().isEmpty()) &&
					(modifyEmployeeLeaveForm.getEmpCode()==null || modifyEmployeeLeaveForm.getEmpCode().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Emp Code or EmployeeId"));
			}
			if(errors.isEmpty()){
				List<ModifyEmployeeLeaveTO> modifyEmployeeLeaveTOs = ModifyEmployeeLeaveHandler.getInstance().getEmployeeLeaveDetails(modifyEmployeeLeaveForm);
				modifyEmployeeLeaveForm.setModifyEmployeeLeaveTOs(modifyEmployeeLeaveTOs);
				if(modifyEmployeeLeaveTOs == null || modifyEmployeeLeaveTOs.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MODIFY_EMPLOYEE_LEAVE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.employee.display.failure","Leaves"));
			saveErrors(request, errors);
			modifyEmployeeLeaveForm.setErrorMessage(msg);
			modifyEmployeeLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.MODIFY_EMPLOYEE_LEAVE);
		}
		
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.MODIFY_EMPLOYEE_LEAVE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editEmployeeLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the viewEmployeeLeaveDetails in ModifyLeaveAction");
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		try {
			ModifyEmployeeLeaveHandler.getInstance().editEmployeeLeaveDetails(modifyEmployeeLeaveForm);
			setDataToForm(modifyEmployeeLeaveForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			modifyEmployeeLeaveForm.setErrorMessage(msg);
			modifyEmployeeLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.EDIT_EMPLOYEE_LEAVE);
	}

	/**
	 * @param modifyEmployeeLeaveForm
	 * @throws Exception
	 */
	private void setDataToForm(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm) throws Exception {
		if(modifyEmployeeLeaveForm.getEmployeeId()!=null && !modifyEmployeeLeaveForm.getEmployeeId().isEmpty()
				&& modifyEmployeeLeaveForm.getStartDate()!=null && !modifyEmployeeLeaveForm.getStartDate().isEmpty() && CommonUtil.isValidDate(modifyEmployeeLeaveForm.getStartDate()) 
				&& (modifyEmployeeLeaveForm.getEmpTypeId()!=null && !modifyEmployeeLeaveForm.getEmpTypeId().isEmpty() && !modifyEmployeeLeaveForm.getEmpTypeId().equalsIgnoreCase("0"))){
			int year=getCurrentYearForGivenEmployee(modifyEmployeeLeaveForm.getStartDate(),modifyEmployeeLeaveForm.getEmpTypeId());
			List<LeaveTypeTo> leaveTypes=ModifyEmployeeLeaveHandler.getInstance().getLeaveTypesForEmployee(modifyEmployeeLeaveForm.getEmployeeId(),modifyEmployeeLeaveForm.getIsExemption(),modifyEmployeeLeaveForm.getStartDate(),year);
			modifyEmployeeLeaveForm.setLeaveTypes(leaveTypes);
			modifyEmployeeLeaveForm.setLeaveTypeId(modifyEmployeeLeaveForm.getLeaveTypeId());
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
	@SuppressWarnings("deprecation")
	public ActionForward updateLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = modifyEmployeeLeaveForm.validate(mapping, request);
		validateApplyLeave(modifyEmployeeLeaveForm,errors);
		setUserId(request,modifyEmployeeLeaveForm);
		try{
			boolean isLeavesAdded = false;
			if (errors.isEmpty()) {
				int year=getCurrentYearForGivenEmployee(modifyEmployeeLeaveForm.getStartDate(),modifyEmployeeLeaveForm.getEmpTypeId());
				boolean isAlreadyExist=ModifyEmployeeLeaveHandler.getInstance().checkAlreadyExists(modifyEmployeeLeaveForm);
				if(isAlreadyExist){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.already.exists"));
					saveErrors(request, errors);
				}else{
				boolean isLeavesAvailable=ModifyEmployeeLeaveHandler.getInstance().checkLeavesAvailableOrNot(modifyEmployeeLeaveForm,year);
				if(!isLeavesAvailable){
					isLeavesAdded=ModifyEmployeeLeaveHandler.getInstance().saveApplyLeave(modifyEmployeeLeaveForm,year);
					if(isLeavesAdded){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.updatesuccess","Leaves"));
						saveMessages(request, messages);
						modifyEmployeeLeaveForm.resetFields();
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Leaves"));
						saveErrors(request, errors);
					}
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.not.available"));
					saveErrors(request, errors);
				}
				}
			}
			 else {
				saveErrors(request, errors);
				//setRequiredDatatoForm(modifyEmployeeLeaveForm);
				return mapping.findForward(CMSConstants.EDIT_EMPLOYEE_LEAVE);
			}
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Leaves"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EDIT_EMPLOYEE_LEAVE);
		}
		//setRequiredDatatoForm(modifyEmployeeLeaveForm);
		return mapping.findForward(CMSConstants.MODIFY_EMPLOYEE_LEAVE);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateApplyLeave( ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, ActionErrors errors) throws Exception {
		if((modifyEmployeeLeaveForm.getFingerPrintId()==null || modifyEmployeeLeaveForm.getFingerPrintId().isEmpty()) &&
				(modifyEmployeeLeaveForm.getEmpCode()==null || modifyEmployeeLeaveForm.getEmpCode().isEmpty())){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Emp Code or EmployeeId"));
		}
		if(modifyEmployeeLeaveForm.getEmployeeId()!=null && !modifyEmployeeLeaveForm.getEmployeeId().isEmpty() 
				&& modifyEmployeeLeaveForm.getEmployeeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("errors.invalid"," Emp Code or EmployeeId"));
		}
		if(modifyEmployeeLeaveForm.getEmpTypeId()!=null && !modifyEmployeeLeaveForm.getEmpTypeId().isEmpty() 
				&& modifyEmployeeLeaveForm.getEmpTypeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.boardDetails.duplicateEntry"," Employee Type Should Assign For the Given Emp Code Or Employee ID"));
		}
		if(modifyEmployeeLeaveForm.getLeaveTypeId() == null || modifyEmployeeLeaveForm.getLeaveTypeId().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.invalid"," Leave Type"));
		}
		if(CommonUtil.checkForEmpty(modifyEmployeeLeaveForm.getStartDate()) && CommonUtil.checkForEmpty(modifyEmployeeLeaveForm.getEndDate()) &&
				CommonUtil.isValidDate(modifyEmployeeLeaveForm.getStartDate()) && CommonUtil.isValidDate(modifyEmployeeLeaveForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}else if(daysBetween==1){
				if(modifyEmployeeLeaveForm.getIsHalfday()==null || modifyEmployeeLeaveForm.getIsHalfday().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is HalfDay"));
				else if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					if(modifyEmployeeLeaveForm.getIsAm()==null || modifyEmployeeLeaveForm.getIsAm().isEmpty())
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is AM"));
				}
				boolean isSunday=CommonUtil.checkIsSunday(modifyEmployeeLeaveForm.getStartDate());
				if(isSunday){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.sunday.selected"));
				}
			}
		}
	}
	
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public static int getCurrentYearForGivenEmployee( String startDate,String empTypeId) throws Exception{
		ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
		Map<Integer,String> map=transaction2.getMonthByEmployeeType();
		String[] date=startDate.split("/");
		int dateMonth=Integer.parseInt(date[1]);
		
		int empTypeMonth=0;
		if(map.containsKey(Integer.parseInt(empTypeId))){
			empTypeMonth=monthMap.get(map.get(Integer.parseInt(empTypeId)).toUpperCase());
		}
		int year=0;
		if(dateMonth==empTypeMonth || dateMonth>empTypeMonth){
			year=Integer.parseInt(date[2]);
		}else{
			year=Integer.parseInt(date[2])-1;
		}
			
		return year;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCancelEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initModofyEmployeeLeave in ModifyLeaveAction");
		ModifyEmployeeLeaveForm objform = (ModifyEmployeeLeaveForm) form;
		setUserId(request,objform);
		objform.resetFields();
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward EmployeeLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the viewEmployeeLeaveDetails in ModifyLeaveAction");
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request,modifyEmployeeLeaveForm);
		try {
			if((modifyEmployeeLeaveForm.getFingerPrintId()==null || modifyEmployeeLeaveForm.getFingerPrintId().isEmpty()) &&
					(modifyEmployeeLeaveForm.getEmpCode()==null || modifyEmployeeLeaveForm.getEmpCode().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Emp Code or EmployeeId"));
			}
			if(errors.isEmpty()){
				List<ModifyEmployeeLeaveTO> modifyEmployeeLeaveTOs = ModifyEmployeeLeaveHandler.getInstance().getEmployeeLeaveDetails(modifyEmployeeLeaveForm);
				modifyEmployeeLeaveForm.setModifyEmployeeLeaveTOs(modifyEmployeeLeaveTOs);
				if(modifyEmployeeLeaveTOs == null || modifyEmployeeLeaveTOs.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.employee.display.failure","Leaves"));
			saveErrors(request, errors);
			modifyEmployeeLeaveForm.setErrorMessage(msg);
			modifyEmployeeLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE);
		}
		
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelEmployeeLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		ModifyEmployeeLeaveHandler.getInstance().editEmployeeLeaveDetails(modifyEmployeeLeaveForm);
		return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the viewEmployeeLeaveDetails in ModifyLeaveAction");
		ModifyEmployeeLeaveForm modifyEmployeeLeaveForm = (ModifyEmployeeLeaveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = modifyEmployeeLeaveForm.validate(mapping, request);
		//validateApplyLeave(modifyEmployeeLeaveForm,errors);
		setUserId(request,modifyEmployeeLeaveForm);
		boolean isCanceled = false;
		try {
			if(modifyEmployeeLeaveForm.getCancelReason() == null || modifyEmployeeLeaveForm.getCancelReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.employee.cancel.leave.reason"));
				saveErrors(request, errors);
			}
			if(errors.isEmpty()){
				int year = Integer.parseInt(modifyEmployeeLeaveForm.getAcademicYear());
				isCanceled = ModifyEmployeeLeaveHandler.getInstance().cancelEmployeeLeaveDetails(modifyEmployeeLeaveForm, year);
				if(isCanceled){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.cancelsuccess","Leaves"));
					saveMessages(request, messages);
					modifyEmployeeLeaveForm.resetFields();
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.cancelfailure","Leaves"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			modifyEmployeeLeaveForm.setErrorMessage(msg);
			modifyEmployeeLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE_PAGE);
		}
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.CANCEL_EMPLOYEE_LEAVE);
	}
	/**
	 * This method will set the user in to the form.
	 * @param request
	 * @param form
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}		
}
