package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.handlers.employee.EmployeeApplyLeaveHandler;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeApplyLeaveAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(EmployeeApplyLeaveAction.class);
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
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpApplyLeave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initEmpApplyLeave");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		setUserId(request,employeeApplyLeaveForm);
		//employeeApplyLeaveForm.setFocusValue("fingerPrintId");
		setRequiredDatatoForm(employeeApplyLeaveForm);
		log.info("Exit initEmpApplyLeave");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE);
	}

	
	/**
	 * @param employeeApplyLeaveForm
	 */
	private void setRequiredDatatoForm(EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception{
		if((employeeApplyLeaveForm.getUserId()!=null && !employeeApplyLeaveForm.getUserId().isEmpty() || 
				(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()))){
			Employee employee=EmployeeApplyLeaveHandler.getInstance().getEmployeeDetails(employeeApplyLeaveForm.getUserId(),employeeApplyLeaveForm.getFingerPrintId());
			if(employee !=null){
				if(employee.getFirstName() != null){
					employeeApplyLeaveForm.setEmployeeName(employee.getFirstName());
				}
				if(employee.getDepartment() !=null && employee.getDepartment().getName() != null){
					employeeApplyLeaveForm.setDepartmentName(employee.getDepartment().getName());
				}
				if(employee.getDesignation() != null && employee.getDesignation().getName() != null ){
					employeeApplyLeaveForm.setDesignationName(employee.getDesignation().getName());
				}
				if(employee.getId() != 0)
					employeeApplyLeaveForm.setEmployeeId(String.valueOf(employee.getId()));
				if(employee.getEmptype() != null && employee.getEmptype().getId() != 0){
					employeeApplyLeaveForm.setEmpTypeId(String.valueOf(employee.getEmptype().getId()));
				}else{
					employeeApplyLeaveForm.setEmpTypeId("0");
				}
					
			}
		}
		if(employeeApplyLeaveForm.getStartDate()!=null && !employeeApplyLeaveForm.getStartDate().isEmpty() 
				&& employeeApplyLeaveForm.getEndDate()!=null && !employeeApplyLeaveForm.getEndDate().isEmpty() 
				&& CommonUtil.isValidDate(employeeApplyLeaveForm.getStartDate()) && CommonUtil.isValidDate(employeeApplyLeaveForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getEndDate());
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween == 1) {
				employeeApplyLeaveForm.setHalfDayDisplay(true);
				if(employeeApplyLeaveForm.getIsHalfday()!=null && !employeeApplyLeaveForm.getIsHalfday().isEmpty() && employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes"))
					employeeApplyLeaveForm.setAmDisplay(true);
				else
					employeeApplyLeaveForm.setAmDisplay(false);
			}else{
				employeeApplyLeaveForm.setHalfDayDisplay(false);
				employeeApplyLeaveForm.setAmDisplay(false);
			}
		}else{
			employeeApplyLeaveForm.setHalfDayDisplay(false);
			employeeApplyLeaveForm.setAmDisplay(false);
		}
		List<LeaveTypeTo> leaveTypes=EmployeeApplyLeaveHandler.getInstance().getLeaveTypesForEmployee();
		employeeApplyLeaveForm.setLeaveTypes(leaveTypes);
		/*if(employeeApplyLeaveForm.getEmployeeId()!=null && !employeeApplyLeaveForm.getEmployeeId().isEmpty()
				&& employeeApplyLeaveForm.getStartDate()!=null && !employeeApplyLeaveForm.getStartDate().isEmpty() && CommonUtil.isValidDate(employeeApplyLeaveForm.getStartDate()) 
				&& (employeeApplyLeaveForm.getEmpTypeId()!=null && !employeeApplyLeaveForm.getEmpTypeId().isEmpty() && !employeeApplyLeaveForm.getEmpTypeId().equalsIgnoreCase("0"))){*/
			//int year=getCurrentYearForGivenEmployee(employeeApplyLeaveForm.getStartDate(),employeeApplyLeaveForm.getEmpTypeId());
			//List<LeaveTypeTo> leaveTypes=EmployeeApplyLeaveHandler.getInstance().getLeaveTypesForEmployee(employeeApplyLeaveForm.getEmployeeId(),employeeApplyLeaveForm.getIsExemption(),employeeApplyLeaveForm.getStartDate());
		
			/*}else{
			employeeApplyLeaveForm.setLeaveTypes(new ArrayList<LeaveTypeTo>());
		}*/
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
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = employeeApplyLeaveForm.validate(mapping, request);
		//validateApplyLeave(employeeApplyLeaveForm,errors);
		setUserId(request,employeeApplyLeaveForm);
		boolean isLeavesAdded = false;
		if (errors.isEmpty()) {
			int year=getCurrentYearForGivenEmployee(employeeApplyLeaveForm.getStartDate(),employeeApplyLeaveForm.getEmpTypeId());
			//boolean isAlreadyExist=EmployeeApplyLeaveHandler.getInstance().checkAlreadyExists(employeeApplyLeaveForm);
			boolean isAlreadyExist=false;
			if(isAlreadyExist){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.already.exists"));
				saveErrors(request, errors);
			}else{
				//boolean isLeavesAvailable=EmployeeApplyLeaveHandler.getInstance().checkLeavesAvailableOrNot(employeeApplyLeaveForm,year);
				boolean isLeavesAvailable=false;
				//if(!isLeavesAvailable){
					isLeavesAdded=EmployeeApplyLeaveHandler.getInstance().saveApplyLeave(employeeApplyLeaveForm,year);
					if(isLeavesAdded){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Leaves"));
						saveMessages(request, messages);
						employeeApplyLeaveForm.resetFields();
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Leaves"));
						saveErrors(request, errors);
					}
				/*}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.not.available"));
					saveErrors(request, errors);
				}*/
			}
		} else {
			saveErrors(request, errors);
			setRequiredDatatoForm(employeeApplyLeaveForm);
			return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE);
		}
		setRequiredDatatoForm(employeeApplyLeaveForm);
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_LEAVE);
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
	 * @param employeeApplyLeaveForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateApplyLeave( EmployeeApplyLeaveForm employeeApplyLeaveForm, ActionErrors errors) throws Exception {
		if((employeeApplyLeaveForm.getFingerPrintId()==null || employeeApplyLeaveForm.getFingerPrintId().isEmpty()) &&
				(employeeApplyLeaveForm.getEmpCode()==null || employeeApplyLeaveForm.getEmpCode().isEmpty())){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Emp Code or EmployeeId"));
		}
		if(employeeApplyLeaveForm.getEmployeeId()!=null && !employeeApplyLeaveForm.getEmployeeId().isEmpty() 
				&& employeeApplyLeaveForm.getEmployeeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("errors.invalid"," Emp Code or EmployeeId"));
		}
		if(employeeApplyLeaveForm.getEmpTypeId()!=null && !employeeApplyLeaveForm.getEmpTypeId().isEmpty() 
				&& employeeApplyLeaveForm.getEmpTypeId().equalsIgnoreCase("0")){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.boardDetails.duplicateEntry"," Employee Type Should Assign For the Given Emp Code Or Employee ID"));
		}
		if(CommonUtil.checkForEmpty(employeeApplyLeaveForm.getStartDate()) && CommonUtil.checkForEmpty(employeeApplyLeaveForm.getEndDate()) &&
				CommonUtil.isValidDate(employeeApplyLeaveForm.getStartDate()) && CommonUtil.isValidDate(employeeApplyLeaveForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}else if(daysBetween==1){
				if(employeeApplyLeaveForm.getIsHalfday()==null || employeeApplyLeaveForm.getIsHalfday().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is HalfDay"));
				else if(employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					if(employeeApplyLeaveForm.getIsAm()==null || employeeApplyLeaveForm.getIsAm().isEmpty())
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Is AM"));
				}
				boolean isSunday=CommonUtil.checkIsSunday(employeeApplyLeaveForm.getStartDate());
				if(isSunday){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.leave.sunday.selected"));
				}
			}
		}
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered getDetails");
		
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		try {
			//int year=getCurrentYearForGivenEmployee(employeeApplyLeaveForm.getStartDate(),employeeApplyLeaveForm.getEmpTypeId());
			List<EmpLeaveTO> list=EmployeeApplyLeaveHandler.getInstance().getDetails(employeeApplyLeaveForm.getEmployeeId());
			request.setAttribute("details",list);
				
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			employeeApplyLeaveForm.setErrorMessage(msg);
			employeeApplyLeaveForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_RESULT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewMyLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initViewMyLeave in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		setUserId(request,employeeApplyLeaveForm);
		try{
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeApplyLeaveHandler.getInstance().getEmpApplyLeaves(employeeApplyLeaveForm);
		employeeApplyLeaveForm.setApplyLeaveTo(applyLeaveTo);
		List<EmpLeaveTO> empLeaveTO=EmployeeApplyLeaveHandler.getInstance().getEmpLeaves(employeeApplyLeaveForm);
		employeeApplyLeaveForm.setEmpLeaveTO(empLeaveTO);
		//added by sudhir
		List<EmpApplyLeaveTO> empOnlineLeave= EmployeeApplyLeaveHandler.getInstance().getEmpOnlineLeaves(employeeApplyLeaveForm);
		employeeApplyLeaveForm.setEmpOnlineLeave(empOnlineLeave);
		//
		request.setAttribute("Operation", "viewMyLeave");
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeApplyLeaveForm.setErrorMessage(msg);
			employeeApplyLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the initViewMyLeave in EmployeeApplyLeaveAction");
		return mapping.findForward(CMSConstants.VIEW_MY_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the initViewEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeLeaves(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		log.info("Entering into the getEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		try{
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeApplyLeaveHandler.getInstance().getAplyLeavesWithFingerPrintIdOrEmpCode(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setApplyLeaveTo(applyLeaveTo);
			List<EmpLeaveTO> empLeaveTO=EmployeeApplyLeaveHandler.getInstance().getEmpLeavesWithFingerPrintIdOrEmpCode(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setEmpLeaveTO(empLeaveTO);
			//added by sudhir
			List<EmpApplyLeaveTO> empOnlineLeave= EmployeeApplyLeaveHandler.getInstance().getEmpOnlineLeavesForEmpDepartment(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setEmpOnlineLeave(empOnlineLeave);
			//
			request.setAttribute("Operation", "viewEmpLeave");
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeApplyLeaveForm.setErrorMessage(msg);
			employeeApplyLeaveForm.setErrorStack(e.getMessage());
			employeeApplyLeaveForm.resetFields();
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_MY_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewDepartmentEmpLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the initViewEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		request.setAttribute("Operation", "viewDepEmpLeaves");
		return mapping.findForward(CMSConstants.VIEW_DEPARTMENT_EMPLOYEE_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDepartmentEmpLeaves(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		log.info("Entering into the getEmployeeLeaves in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		setUserId(request,employeeApplyLeaveForm);
		employeeApplyLeaveForm.setDisplayMessage(null);
		try{
			List<EmpApplyLeaveTO> applyLeaveTo=EmployeeApplyLeaveHandler.getInstance().getAplyLeavesForHod(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setApplyLeaveTo(applyLeaveTo);
			List<EmpLeaveTO> empLeaveTO=EmployeeApplyLeaveHandler.getInstance().getEmpLeavesForHod(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setEmpLeaveTO(empLeaveTO);
			//added by sudhir
			List<EmpApplyLeaveTO> empOnlineLeave= EmployeeApplyLeaveHandler.getInstance().getEmpOnlineLeavesForEmpDepartment(employeeApplyLeaveForm);
			employeeApplyLeaveForm.setEmpOnlineLeave(empOnlineLeave);
			//
			request.setAttribute("Operation", "viewDepEmpLeave");
		}catch(ApplicationException e){
			String str = "";
			if(employeeApplyLeaveForm.getDepartmentName() != null && !employeeApplyLeaveForm.getDepartmentName().isEmpty()){
				str = "Employee department belongs to "+employeeApplyLeaveForm.getDepartmentName();
			}else{
				str = "No records found";
			}
			request.setAttribute("Operation", "viewDepEmpLeaves");
			employeeApplyLeaveForm.setDisplayMessage(str);
			return mapping.findForward(CMSConstants.VIEW_DEPARTMENT_EMPLOYEE_LEAVES);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			employeeApplyLeaveForm.setErrorMessage(msg);
			employeeApplyLeaveForm.setErrorStack(e.getMessage());
			employeeApplyLeaveForm.resetFields();
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_MY_LEAVES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entering into the initViewEmployeeLeave in EmployeeApplyLeaveAction");
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		employeeApplyLeaveForm.resetFields();
		//request.setAttribute("Operation", "viewDepEmpLeaves");
		return mapping.findForward(CMSConstants.UPLOAD_LEAVE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadLeave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
		setUserId(request, employeeApplyLeaveForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = employeeApplyLeaveForm.validate(mapping, request);
		FormFile csvFile=employeeApplyLeaveForm.getCsvFile();
		boolean isError=false;
		try {
		if(csvFile.getFileName()!=null && !csvFile.getFileName().isEmpty()){
			if(csvFile.getFileName()!=null && !StringUtils.isEmpty(csvFile.getFileName())){
				String extn="";
				int indx=csvFile.getFileName().lastIndexOf(".");
				if(indx!=-1)
				extn=csvFile.getFileName().substring(indx, csvFile.getFileName().length());
				if(!extn.equalsIgnoreCase(".CSV")){
					
					 if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR);
						errors.add(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR, error);
						}
				}
			}else{
				if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_OMR_REQUIRED, error);
					}
			}
			
			if(errors.isEmpty()){
			Map<String,String> leaveTypeMap=EmployeeApplyLeaveHandler.getInstance().getLeaveTypeMap();
			boolean flag=EmployeeApplyLeaveHandler.getInstance().addUploadedData(csvFile.getInputStream(), leaveTypeMap,employeeApplyLeaveForm);
			
			List<String> fingerPrintIds=employeeApplyLeaveForm.getFingerPrintIds();
			if(fingerPrintIds!=null && !fingerPrintIds.isEmpty()){
				StringBuilder ids=new StringBuilder();
				Iterator it=fingerPrintIds.iterator();
				while(it.hasNext()){
					ids.append(it.next().toString()).append(", ");
				}
				int len=ids.length()-2;
		        if(ids.toString().endsWith(", ")){
		        	ids.setCharAt(len, ' ');
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.duplicate" ,ids.toString().trim()));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setFingerPrintIds(null);
			}
			List<String> dateErrorIds=employeeApplyLeaveForm.getDateErrorIds();
			if(dateErrorIds!=null && !dateErrorIds.isEmpty()){
				String ids="";
				Iterator <String>it=dateErrorIds.iterator();
				/*while(it.hasNext()){
					if(it.next()!=null)
						ids= ids+it.next()+", ";
				}*/
				while (it.hasNext()) {
					String dateErrorId = (String) it.next();
					if(dateErrorId!=null && !dateErrorId.isEmpty()){
						ids= ids+dateErrorId+", ";
					}
				}
				int len=ids.length()-2;
		        if(ids.endsWith(", ")){
		            StringBuffer buff=new StringBuffer(ids);
		            buff.setCharAt(len, ' ');
		            ids=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.date.invalid" ,ids));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setDateErrorIds(null);
			}
			List<String> overFlowIds=employeeApplyLeaveForm.getOverFlowIds();
			if(overFlowIds!=null && !overFlowIds.isEmpty()){
				String ids="";
				Iterator it=overFlowIds.iterator();
				while(it.hasNext()){
					ids= ids+it.next().toString()+", ";
				}
				int len=ids.length()-2;
		        if(ids.endsWith(", ")){
		            StringBuffer buff=new StringBuffer(ids);
		            buff.setCharAt(len, ' ');
		            ids=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.overflow" ,ids));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setOverFlowIds(null);
			}
			
			List<String> dupDatesErrorIds=employeeApplyLeaveForm.getDupFingDatesErrorMes();
			if(dupDatesErrorIds!=null && !dupDatesErrorIds.isEmpty()){
				String ids="";
				Iterator <String>it=dupDatesErrorIds.iterator();
				/*while(it.hasNext()){
					if(it.next()!=null)
						ids= ids+it.next()+", ";
				}*/
				while (it.hasNext()) {
					String dateErrorId = (String) it.next();
					if(dateErrorId!=null && !dateErrorId.isEmpty()){
						ids= ids+dateErrorId+", ";
					}
				}
				int len=ids.length()-2;
		        if(ids.endsWith(", ")){
		            StringBuffer buff=new StringBuffer(ids);
		            buff.setCharAt(len, ' ');
		            ids=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.duplicate.dates" ,ids));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setDupFingDatesErrorMes(null);
			}
			
			List<String> wrongDatesErrorIds=employeeApplyLeaveForm.getWrongDatesErrorMes();
			if(wrongDatesErrorIds!=null && !wrongDatesErrorIds.isEmpty()){
				String ids="";
				Iterator <String>it=wrongDatesErrorIds.iterator();
				
				while (it.hasNext()) {
					String dateErrorId = (String) it.next();
					if(dateErrorId!=null && !dateErrorId.isEmpty()){
						ids= ids+dateErrorId+", ";
					}
				}
				int len=ids.length()-2;
		        if(ids.endsWith(", ")){
		            StringBuffer buff=new StringBuffer(ids);
		            buff.setCharAt(len, ' ');
		            ids=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.wrong.dates" ,ids));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setWrongDatesErrorMes(null);
			}
			
			List<String> wrongDatesFormats=employeeApplyLeaveForm.getWrongDatesFormats();
			if(wrongDatesFormats!=null && !wrongDatesFormats.isEmpty()){
				String ids="";
				Iterator <String>it=wrongDatesFormats.iterator();
				
				while (it.hasNext()) {
					String dateErrorId = (String) it.next();
					if(dateErrorId!=null && !dateErrorId.isEmpty()){
						ids= ids+dateErrorId+", ";
					}
				}
				int len=ids.length()-2;
		        if(ids.endsWith(", ")){
		            StringBuffer buff=new StringBuffer(ids);
		            buff.setCharAt(len, ' ');
		            ids=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.employee.uploadLeave.wrong.format.dates" ,ids));
				addErrors(request, errors);
				isError=true;
				employeeApplyLeaveForm.resetFields();
				employeeApplyLeaveForm.setWrongDatesFormats(null);
			}
			
			
			if(flag && isError){
				messages.add(CMSConstants.MESSAGES,new ActionMessage( "knowledgepro.employee.uploadLeave.remain.success"));
				saveMessages(request, messages);
				employeeApplyLeaveForm.resetFields();
			}
			 else if(flag){
					messages.add(CMSConstants.MESSAGES,new ActionMessage( "knowledgepro.employee.uploadLeave.success"));
					saveMessages(request, messages);
					employeeApplyLeaveForm.resetFields();
				}
			 else{
				ActionMessage error=new ActionMessage("knowledgepro.employee.uploadLeave.fail");
				errors.add("error", error);
				employeeApplyLeaveForm.resetFields();
			}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPLOAD_LEAVE);
			}
			 
		}else{
			ActionMessage error=new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_REQUIRED);
			errors.add("error",error);
		}
			}
			catch (Exception exception) {
				log.error("Error occured in ApplicationStatusUpdateAction", exception);
				String msg = super.handleApplicationException(exception);
				employeeApplyLeaveForm.setErrorMessage(msg);
				employeeApplyLeaveForm.setErrorStack(exception.getMessage());
				if(employeeApplyLeaveForm.isWrong()){
					ActionMessage error=new ActionMessage("knowledgepro.employee.upload.leave");
					errors.add("error", error);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPLOAD_LEAVE);
				}else
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.UPLOAD_LEAVE);
	}
}
