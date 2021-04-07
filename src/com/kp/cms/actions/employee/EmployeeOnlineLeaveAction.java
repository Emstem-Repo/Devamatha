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
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.employee.EmployeeOnlineLeaveForm;
import com.kp.cms.handlers.employee.EmployeeOnlineLeaveHandler;
import com.kp.cms.handlers.employee.OnlineLeaveAppInstructionHandler;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.employee.OnlineLeaveAppInstructionTO;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeOnlineLeaveAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(EmployeeOnlineLeaveAction.class);
	private static Map<String,Integer> monthMap = null;
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
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
	public ActionForward initOnlineLeave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initOnlineLeave");
		EmployeeOnlineLeaveForm employeeApplyLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		setUserId(request, employeeApplyLeaveForm);
		setRequiredDatatoForm(employeeApplyLeaveForm,request);
		log.info("Exit initOnlineLeave");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_ONLINE_LEAVE);
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
	/**
	 * @param request 
	 * @param employeeApplyLeaveForm
	 */
	private void setRequiredDatatoForm(EmployeeOnlineLeaveForm employeeOnlineLeaveForm, HttpServletRequest request) throws Exception{
		if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
			Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
			if(employee !=null){
				if(employee.getFingerPrintId() != null){
					employeeOnlineLeaveForm.setFingerPrintId(employee.getFingerPrintId());
				}
				if(employee.getFirstName() != null){
					employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
				}
				if(employee.getDepartment() !=null && employee.getDepartment().getName() != null){
					employeeOnlineLeaveForm.setDepartmentName(employee.getDepartment().getName());
				}
				if(employee.getDesignation() != null && employee.getDesignation().getName() != null ){
					employeeOnlineLeaveForm.setDesignationName(employee.getDesignation().getName());
				}
				if(employee.getId() != 0)
					employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
				if(employee.getEmptype() != null && employee.getEmptype().getId() != 0){
					employeeOnlineLeaveForm.setEmpTypeId(String.valueOf(employee.getEmptype().getId()));
				}else{
					employeeOnlineLeaveForm.setEmpTypeId("0");
				}
				if(employee.getWorkEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
				}else if(employee.getOtherEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
				}
			}
		}
		if(employeeOnlineLeaveForm.getStartDate()!=null && !employeeOnlineLeaveForm.getStartDate().isEmpty() 
				&& employeeOnlineLeaveForm.getEndDate()!=null && !employeeOnlineLeaveForm.getEndDate().isEmpty() 
				&& CommonUtil.isValidDate(employeeOnlineLeaveForm.getStartDate()) && CommonUtil.isValidDate(employeeOnlineLeaveForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getEndDate());
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween == 1) {
				employeeOnlineLeaveForm.setHalfDayDisplay(true);
				if(employeeOnlineLeaveForm.getIsHalfday()!=null && !employeeOnlineLeaveForm.getIsHalfday().isEmpty() && employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes"))
					employeeOnlineLeaveForm.setAmDisplay(true);
				else
					employeeOnlineLeaveForm.setAmDisplay(false);
			}else{
				employeeOnlineLeaveForm.setHalfDayDisplay(false);
				employeeOnlineLeaveForm.setAmDisplay(false);
			}
		}else{
			employeeOnlineLeaveForm.setHalfDayDisplay(false);
			employeeOnlineLeaveForm.setAmDisplay(false);
		}
		/*List<LeaveTypeTo> leaveTypes=EmployeeOnlineLeaveHandler.getInstance().getLeaveTypesForEmployee();
		employeeOnlineLeaveForm.setLeaveTypes(leaveTypes);*/
		Map<Integer, String> leaveTypes = EmployeeOnlineLeaveHandler.getInstance().getEmployeeLeaveTypeMap();
		request.getSession().setAttribute("leaveTypes", leaveTypes);
		String currentDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), EmployeeOnlineLeaveAction.SQL_DATEFORMAT,	EmployeeOnlineLeaveAction.FROM_DATEFORMAT);
		int year=getCurrentYearForGivenEmployee(currentDate,employeeOnlineLeaveForm.getEmpTypeId());
		List<EmpLeaveTO> list=EmployeeOnlineLeaveHandler.getInstance().getDetails(employeeOnlineLeaveForm.getEmployeeId(),year,employeeOnlineLeaveForm);
		request.setAttribute("details",list);
		List<OnlineLeaveAppInstructionTO> onlineLeaveAppTO= OnlineLeaveAppInstructionHandler.getInstance().getOnlineLeaveInstructions();
		employeeOnlineLeaveForm.setLeaveInstructionsTo(onlineLeaveAppTO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward applyLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = employeeOnlineLeaveForm.validate(mapping, request);
		validateApplyLeave(employeeOnlineLeaveForm,errors);
		setUserId(request,employeeOnlineLeaveForm);
		try{
			boolean isLeavesAdded = false;
			if (errors.isEmpty()) {
				int year=getCurrentYearForGivenEmployee(employeeOnlineLeaveForm.getStartDate(),employeeOnlineLeaveForm.getEmpTypeId());
				boolean isAlreadyExist=EmployeeOnlineLeaveHandler.getInstance().checkAlreadyExists(employeeOnlineLeaveForm);
				if(isAlreadyExist){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.already.exists"));
					saveErrors(request, errors);
				}else{
					boolean isLeavesAvailable=EmployeeOnlineLeaveHandler.getInstance().checkLeavesAvailableOrNot(employeeOnlineLeaveForm,year);
					if(!isLeavesAvailable){
						isLeavesAdded=EmployeeOnlineLeaveHandler.getInstance().saveApplyLeave(employeeOnlineLeaveForm,year);
						if(isLeavesAdded){
							EmployeeOnlineLeaveHandler.getInstance().sendMailToApprover(employeeOnlineLeaveForm,request);
							messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Leaves"));
							saveMessages(request, messages);
							employeeOnlineLeaveForm.resetFields();
						}else{
							errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Leaves"));
							saveErrors(request, errors);
						}
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.not.available"));
						saveErrors(request, errors);
					}
				}
			} else {
				saveErrors(request, errors);
				setRequiredDatatoForm(employeeOnlineLeaveForm,request);
				return mapping.findForward(CMSConstants.INIT_EMPLOYEE_ONLINE_LEAVE);
			}
		}catch (ApplicationException e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.approver.notadded"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EMPLOYEE_ONLINE_LEAVE);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(employeeOnlineLeaveForm,request);
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_ONLINE_LEAVE);
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
		if(empTypeId != null){
			if(map.containsKey(Integer.parseInt(empTypeId))){
				empTypeMonth=monthMap.get(map.get(Integer.parseInt(empTypeId)).toUpperCase());
			}
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
	 * @param employeeApplyLeaveForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateApplyLeave( EmployeeOnlineLeaveForm employeeOnlineLeaveForm, ActionErrors errors) throws Exception {
		if((employeeOnlineLeaveForm.getFingerPrintId()==null || employeeOnlineLeaveForm.getFingerPrintId().isEmpty()) &&
				(employeeOnlineLeaveForm.getEmpCode()==null || employeeOnlineLeaveForm.getEmpCode().isEmpty())){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Emp Code or EmployeeId"));
		}
		if(employeeOnlineLeaveForm.getEmployeeId()!=null && !employeeOnlineLeaveForm.getEmployeeId().isEmpty() 
				&& employeeOnlineLeaveForm.getEmployeeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("errors.invalid"," Emp Code or EmployeeId"));
		}
		if(employeeOnlineLeaveForm.getEmpTypeId()!=null && !employeeOnlineLeaveForm.getEmpTypeId().isEmpty() 
				&& employeeOnlineLeaveForm.getEmpTypeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.boardDetails.duplicateEntry"," Employee Type Should Assign For the Given Emp Code Or Employee ID"));
		}
		if(employeeOnlineLeaveForm.getReason() == null || employeeOnlineLeaveForm.getReason().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason "));
		}
		if(employeeOnlineLeaveForm.getLeaveTypeId() == null || employeeOnlineLeaveForm.getLeaveTypeId().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Leave Type "));
		}
		if(employeeOnlineLeaveForm.getStartDate() == null || employeeOnlineLeaveForm.getStartDate().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Start Date "));
		}
		if(employeeOnlineLeaveForm.getEndDate() == null || employeeOnlineLeaveForm.getEndDate().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," End Date "));
		}
		if(CommonUtil.checkForEmpty(employeeOnlineLeaveForm.getStartDate()) && CommonUtil.checkForEmpty(employeeOnlineLeaveForm.getEndDate()) &&
				CommonUtil.isValidDate(employeeOnlineLeaveForm.getStartDate()) && CommonUtil.isValidDate(employeeOnlineLeaveForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}else if(daysBetween==1){
				if(employeeOnlineLeaveForm.getIsHalfday()==null || employeeOnlineLeaveForm.getIsHalfday().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is HalfDay"));
				else if(employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					if(employeeOnlineLeaveForm.getIsAm()==null || employeeOnlineLeaveForm.getIsAm().isEmpty())
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is AM"));
				}
				boolean isSunday=CommonUtil.checkIsSunday(employeeOnlineLeaveForm.getStartDate());
				if(isSunday){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.sunday.selected"));
				}
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
	public ActionForward initViewLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initViewLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeOnlineLeaveForm.resetFields();
		setUserId(request,employeeOnlineLeaveForm);
		try{
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApplyLeaves(employeeOnlineLeaveForm);
			employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			setEmployeeDetailsToForm(employeeOnlineLeaveForm);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the initViewLeave in EmployeeApplyLeaveAction");
		return mapping.findForward(CMSConstants.VIEW_MY_ONLINE_LEAVES);
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	private void setEmployeeDetailsToForm(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
			Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
			if(employee !=null){
				if(employee.getFingerPrintId() != null){
					employeeOnlineLeaveForm.setFingerPrintId(employee.getFingerPrintId());
				}
				if(employee.getFirstName() != null){
					employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
				}
				if(employee.getDepartment() !=null && employee.getDepartment().getName() != null){
					employeeOnlineLeaveForm.setDepartmentName(employee.getDepartment().getName());
				}
				if(employee.getDesignation() != null && employee.getDesignation().getName() != null ){
					employeeOnlineLeaveForm.setDesignationName(employee.getDesignation().getName());
				}
				if(employee.getId() != 0)
					employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
				if(employee.getEmptype() != null && employee.getEmptype().getId() != 0){
					employeeOnlineLeaveForm.setEmpTypeId(String.valueOf(employee.getEmptype().getId()));
				}else{
					employeeOnlineLeaveForm.setEmpTypeId("0");
				}
				if(employee.getWorkEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
				}else if(employee.getOtherEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
				}
			}
		} 
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	private void setEmployeeMailDetails(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
			Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
			if(employee !=null){
				if(employee.getFirstName() != null){
					employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
				}
				if(employee.getWorkEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
				}else if(employee.getOtherEmail() != null){
					employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
				}
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
	public ActionForward initApproveLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the initApproveLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeOnlineLeaveForm.resetFields();
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
			employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the approveEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isApproved = false;
		setUserId(request,employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
				Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
				if(employee !=null){
					if(employee.getId() != 0)
						employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
					if(employee.getFirstName() != null){
						employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
					}
					if(employee.getWorkEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
					}else if(employee.getOtherEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
					}
				}
			} 
			isApproved=EmployeeOnlineLeaveHandler.getInstance().saveEmpLeave(employeeOnlineLeaveForm);
			if(isApproved){
				EmployeeOnlineLeaveHandler.getInstance().sendMailToEmployees(employeeOnlineLeaveForm);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.approve"));
				saveMessages(request, messages);
				employeeOnlineLeaveForm.resetFields();
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("Knowledgepro.employee.online.leave.approve.fail"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			Map<Integer, String> employeeMap = EmployeeOnlineLeaveHandler.getInstance().getEmployeeMap();
			employeeMap = CommonUtil.sortMapByValue(employeeMap);
			request.getSession().setAttribute("employeeMap", employeeMap);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.FROWARD_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getEmployeeId() == null || employeeOnlineLeaveForm.getEmployeeId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Employee "));
			}
			if(employeeOnlineLeaveForm.getMailBody() == null || employeeOnlineLeaveForm.getMailBody().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Mail body "));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().updateEmpOnlineLeave(employeeOnlineLeaveForm);
				setEmployeeInfoTOForm(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendMailToLeaveApprover(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.forward"));
					saveMessages(request, messages);
					employeeOnlineLeaveForm.resetFields();
				}
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.FROWARD_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	private void setEmployeeInfoTOForm(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployee(employeeOnlineLeaveForm.getEmployeeId());
		if(employee != null){
			if(employee.getWorkEmail() != null){
				employeeOnlineLeaveForm.setApproverMailId(employee.getWorkEmail());
			}else if(employee.getOtherEmail() != null){
				employeeOnlineLeaveForm.setApproverMailId(employee.getOtherEmail());
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
	public ActionForward returnEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRejectedReason() == null || employeeOnlineLeaveForm.getRejectedReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().rejectEmpOnlineLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendReturnMailTOEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewEmployeeLeaves(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the viewEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		try{
			String currentDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), EmployeeOnlineLeaveAction.SQL_DATEFORMAT,	EmployeeOnlineLeaveAction.FROM_DATEFORMAT);
			int year=getCurrentYearForGivenEmployee(currentDate,employeeOnlineLeaveForm.getEmpTypeId());
			List<EmpLeaveTO> list=EmployeeOnlineLeaveHandler.getInstance().getDetails(employeeOnlineLeaveForm.getEmpId(),year,employeeOnlineLeaveForm);
			request.setAttribute("details1",list);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APPROVE_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAuthorizationLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the initAuthorizationLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeOnlineLeaveForm.resetFields();
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			employeeOnlineLeaveForm.setApplyLeaveTo(null);
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpAuthorizationLeaves(employeeOnlineLeaveForm);
			employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward authorizationEmployeeLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the approveEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isAuthorized = false;
		setUserId(request,employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
				Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
				if(employee !=null){
					if(employee.getId() != 0)
						employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
					if(employee.getFirstName() != null){
						employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
					}
					if(employee.getWorkEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
					}else if(employee.getOtherEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
					}
				}
			} 
			isAuthorized=EmployeeOnlineLeaveHandler.getInstance().saveEmpAuthorizedLeave(employeeOnlineLeaveForm);
			if(isAuthorized){
				EmployeeOnlineLeaveHandler.getInstance().sendAuthorizationMailToEmployees(employeeOnlineLeaveForm);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.approve"));
				saveMessages(request, messages);
				employeeOnlineLeaveForm.resetFields();
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpAuthorizationLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("Knowledgepro.employee.online.leave.approve.fail"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnAuthorizationEmpLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRejectedReason() == null || employeeOnlineLeaveForm.getRejectedReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().rejectEmpOnlineLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendReturnMail(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpAuthorizationLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward requestDocEmpLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRequestDocReason() == null || employeeOnlineLeaveForm.getRequestDocReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().requestDocEmpLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendRequestDocMailToEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpAuthorizationLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewAuthorizedEmployeeLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the viewAuthorizedEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		try{
			String currentDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), EmployeeOnlineLeaveAction.SQL_DATEFORMAT,	EmployeeOnlineLeaveAction.FROM_DATEFORMAT);
			int year=getCurrentYearForGivenEmployee(currentDate,employeeOnlineLeaveForm.getEmpTypeId());
			List<EmpLeaveTO> list=EmployeeOnlineLeaveHandler.getInstance().getDetails(employeeOnlineLeaveForm.getEmpId(),year,employeeOnlineLeaveForm);
			request.setAttribute("details1",list);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.AUTHORIZATION_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewReturnedRequestDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the viewReturnedRequestDoc in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeOnlineLeaveForm.resetFields();
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			employeeOnlineLeaveForm.setApplyLeaveTo(null);
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDoc(employeeOnlineLeaveForm);
			employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward authorizedPendingAuthorization(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the authorizedPendingAuthorization in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isAuthorized = false;
		setUserId(request,employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
				Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
				if(employee !=null){
					if(employee.getId() != 0)
						employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
					if(employee.getFirstName() != null){
						employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
					}
					if(employee.getWorkEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
					}else if(employee.getOtherEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
					}
				}
			} 
			isAuthorized=EmployeeOnlineLeaveHandler.getInstance().saveEmpAuthorizedLeave(employeeOnlineLeaveForm);
			if(isAuthorized){
				EmployeeOnlineLeaveHandler.getInstance().sendAuthorizationMailToEmployees(employeeOnlineLeaveForm);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.approve"));
				saveMessages(request, messages);
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDoc(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("Knowledgepro.employee.online.leave.approve.fail"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnedPendingAuthorization(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the returnedPendingAuthorization in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRejectedReason() == null || employeeOnlineLeaveForm.getRejectedReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().rejectEmpOnlineLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendReturnMail(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDoc(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward requestDocAuthorization(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the requestDocAuthorization in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRequestDocReason() == null || employeeOnlineLeaveForm.getRequestDocReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().requestDocEmpLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendRequestDocMailToEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDoc(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewReturnedReqPendingAuthorization(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the viewAuthorizedEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		try{
			String currentDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), EmployeeOnlineLeaveAction.SQL_DATEFORMAT,	EmployeeOnlineLeaveAction.FROM_DATEFORMAT);
			int year=getCurrentYearForGivenEmployee(currentDate,employeeOnlineLeaveForm.getEmpTypeId());
			List<EmpLeaveTO> list=EmployeeOnlineLeaveHandler.getInstance().getDetails(employeeOnlineLeaveForm.getEmpId(),year,employeeOnlineLeaveForm);
			request.setAttribute("details1",list);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES);
	}
	
	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewReturnedRequestDocForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the viewReturnedRequestDoc in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		employeeOnlineLeaveForm.resetFields();
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			employeeOnlineLeaveForm.setApplyLeaveTo(null);
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDocForApproval(employeeOnlineLeaveForm);
			employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	
	public ActionForward viewReturnedReqPendingApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the viewAuthorizedEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		try{
			String currentDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), EmployeeOnlineLeaveAction.SQL_DATEFORMAT,	EmployeeOnlineLeaveAction.FROM_DATEFORMAT);
			int year=getCurrentYearForGivenEmployee(currentDate,employeeOnlineLeaveForm.getEmpTypeId());
			List<EmpLeaveTO> list=EmployeeOnlineLeaveHandler.getInstance().getDetails(employeeOnlineLeaveForm.getEmpId(),year,employeeOnlineLeaveForm);
			request.setAttribute("details1",list);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approvePendingApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the authorizedPendingAuthorization in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isApproved = false;
		setUserId(request,employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
				Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
				if(employee !=null){
					if(employee.getId() != 0)
						employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
					if(employee.getFirstName() != null){
						employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
					}
					if(employee.getWorkEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
					}else if(employee.getOtherEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
					}
				}
			} 
			isApproved=EmployeeOnlineLeaveHandler.getInstance().saveEmpLeave(employeeOnlineLeaveForm);
			if(isApproved){
				EmployeeOnlineLeaveHandler.getInstance().sendMailToEmployees(employeeOnlineLeaveForm);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.approve"));
				saveMessages(request, messages);
				employeeOnlineLeaveForm.resetFields();
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDocForApproval(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("Knowledgepro.employee.online.leave.approve.fail"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnedPendingApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the returnedPendingAuthorization in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRejectedReason() == null || employeeOnlineLeaveForm.getRejectedReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().rejectEmpOnlineLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendReturnMailTOEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDocForApproval(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward requestDocApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the requestDocApproval in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRequestDocReason() == null || employeeOnlineLeaveForm.getRequestDocReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().requestDocEmpLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendRequestDocMailToEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getViewReturnedReqDocForApproval(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveEmployeeLeaveReturned(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the approveEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isApproved = false;
		setUserId(request,employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getUserId() != null && !employeeOnlineLeaveForm.getUserId().isEmpty()){
				Employee employee=EmployeeOnlineLeaveHandler.getInstance().getEmployeeDetails(employeeOnlineLeaveForm.getUserId());
				if(employee !=null){
					if(employee.getId() != 0)
						employeeOnlineLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
					if(employee.getFirstName() != null){
						employeeOnlineLeaveForm.setEmployeeName(employee.getFirstName());
					}
					if(employee.getWorkEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getWorkEmail());
					}else if(employee.getOtherEmail() != null){
						employeeOnlineLeaveForm.setEmployeeEmailId(employee.getOtherEmail());
					}
				}
			} 
			isApproved=EmployeeOnlineLeaveHandler.getInstance().saveEmpLeave(employeeOnlineLeaveForm);
			if(isApproved){
				EmployeeOnlineLeaveHandler.getInstance().sendMailToEmployees(employeeOnlineLeaveForm);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.approve"));
				saveMessages(request, messages);
				employeeOnlineLeaveForm.resetFields();
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("Knowledgepro.employee.online.leave.approve.fail"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeLeaveReturned(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			Map<Integer, String> employeeMap = EmployeeOnlineLeaveHandler.getInstance().getEmployeeMap();
			employeeMap = CommonUtil.sortMapByValue(employeeMap);
			request.getSession().setAttribute("employeeMap", employeeMap);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.FROWARD_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendEmployeeLeaveReturned(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getEmployeeId() == null || employeeOnlineLeaveForm.getEmployeeId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Employee "));
			}
			if(employeeOnlineLeaveForm.getMailBody() == null || employeeOnlineLeaveForm.getMailBody().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Mail body "));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().updateEmpOnlineLeave(employeeOnlineLeaveForm);
				setEmployeeInfoTOForm(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendMailToLeaveApprover(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.forward"));
					saveMessages(request, messages);
					employeeOnlineLeaveForm.resetFields();
				}
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.FROWARD_EMPLOYEE_LEAVES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnEmployeeLeaveReturned(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the forwardEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeOnlineLeaveForm employeeOnlineLeaveForm = (EmployeeOnlineLeaveForm) form;
		setUserId(request,employeeOnlineLeaveForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setEmployeeMailDetails(employeeOnlineLeaveForm);
		try{
			if(employeeOnlineLeaveForm.getRejectedReason() == null || employeeOnlineLeaveForm.getRejectedReason().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Reason"));
			}
			if(errors.isEmpty()){
				EmployeeOnlineLeaveHandler.getInstance().rejectEmpOnlineLeave(employeeOnlineLeaveForm);
				boolean send = EmployeeOnlineLeaveHandler.getInstance().sendReturnMailTOEmployee(employeeOnlineLeaveForm);
				if(send){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("Knowledgepro.employee.online.leave.mail.send.success"));
					saveMessages(request, messages);
				}
				employeeOnlineLeaveForm.resetFields();
				employeeOnlineLeaveForm.setApplyLeaveTo(null);
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHandler.getInstance().getEmpApproveLeaves(employeeOnlineLeaveForm);
				employeeOnlineLeaveForm.setApplyLeaveTo(applyLeaveTo);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			employeeOnlineLeaveForm.setErrorMessage(msg);
			employeeOnlineLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL);
	}
	
	
}
