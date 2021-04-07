package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.ModifyEmployeeLeaveForm;
import com.kp.cms.helpers.employee.EmployeeApplyLeaveHelper;
import com.kp.cms.helpers.employee.ModifyEmployeeLeaveHelper;
import com.kp.cms.to.admin.ModifyEmployeeLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction;
import com.kp.cms.transactions.exam.IModifyIEmployeeLeaveTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeApplyLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.employee.ModifyEmployeeLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ModifyEmployeeLeaveHandler {
	/**
	 * Singleton object of ModifyEmployeeLeaveHandler
	 */
	private static volatile ModifyEmployeeLeaveHandler modifyEmployeeLeaveHandler = null;
	private static final Log log = LogFactory.getLog(ModifyEmployeeLeaveHandler.class);
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	private ModifyEmployeeLeaveHandler() {
		
	}
	/**
	 * return singleton object of ModifyEmployeeLeaveHandler.
	 * @return
	 */
	public static ModifyEmployeeLeaveHandler getInstance() {
		if (modifyEmployeeLeaveHandler == null) {
			modifyEmployeeLeaveHandler = new ModifyEmployeeLeaveHandler();
		}
		return modifyEmployeeLeaveHandler;
	}
	/**
	 * @param objform
	 * @throws Exception
	 */
	public List<ModifyEmployeeLeaveTO> getEmployeeLeaveDetails(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm)throws Exception {
		
		IModifyIEmployeeLeaveTransaction transaction = ModifyEmployeeLeaveTransactionImpl.getInstance();
		String empCode = modifyEmployeeLeaveForm.getEmpCode();
		String fingerPrintId = modifyEmployeeLeaveForm.getFingerPrintId();
		List<EmpApplyLeave> leaveList = transaction.getEmployeeLeaveDetails(empCode, fingerPrintId, modifyEmployeeLeaveForm);
		List<ModifyEmployeeLeaveTO> employeeLeaveTOs = new ArrayList<ModifyEmployeeLeaveTO>();
		if(leaveList != null && !leaveList.isEmpty()){
			Iterator<EmpApplyLeave> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeave empApplyLeave = (EmpApplyLeave) iterator.next();
				ModifyEmployeeLeaveTO to = new ModifyEmployeeLeaveTO();
				to.setId(empApplyLeave.getId());
				to.setEmpCode(empApplyLeave.getEmployee().getCode());
				to.setFingerPrintId(empApplyLeave.getEmployee().getFingerPrintId());
				to.setEmployeeName(empApplyLeave.getEmployee().getFirstName());
				to.setDepartmentName(empApplyLeave.getEmployee().getDepartment().getName());
				to.setDesignationName(empApplyLeave.getEmployee().getDesignation().getName());
				to.setStartDate(CommonUtil.getStringDate(empApplyLeave.getFromDate()));
				to.setEndDate(CommonUtil.getStringDate(empApplyLeave.getToDate()));
				to.setEmployeeId(String.valueOf(empApplyLeave.getEmployee().getId()));
				to.setReason(empApplyLeave.getReason());
				to.setLeaveTypeId(empApplyLeave.getEmpLeaveType().getName());
				employeeLeaveTOs.add(to);
			}
		}
		return employeeLeaveTOs;
	}
	/**
	 * @param modifyEmployeeLeaveForm
	 */
	public void editEmployeeLeaveDetails(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm) throws Exception {
		
		IModifyIEmployeeLeaveTransaction transaction = ModifyEmployeeLeaveTransactionImpl.getInstance();
		int id = modifyEmployeeLeaveForm.getApplyLeaveId();
		EmpApplyLeave empApplyLeave = transaction.getLeaveDetails(id);
		
		if(empApplyLeave.getEmployee() != null && empApplyLeave.getEmployee().getId() != 0){
			modifyEmployeeLeaveForm.setEmployeeId(String.valueOf(empApplyLeave.getEmployee().getId()));
		}
		if(empApplyLeave.getIsExemption() != null){
			if(empApplyLeave.getIsExemption()){
				modifyEmployeeLeaveForm.setIsExemption("yes");
			}
			if(!empApplyLeave.getIsExemption()){
				modifyEmployeeLeaveForm.setIsExemption("no");
			}
		}
		if(empApplyLeave.getEmployee().getCode() != null && !empApplyLeave.getEmployee().getCode().isEmpty()){
			modifyEmployeeLeaveForm.setEmpCode(empApplyLeave.getEmployee().getCode());
		}
		if(empApplyLeave.getEmployee().getFingerPrintId() != null && !empApplyLeave.getEmployee().getFingerPrintId().isEmpty()){
			modifyEmployeeLeaveForm.setFingerPrintId(empApplyLeave.getEmployee().getFingerPrintId());
		}
		if(empApplyLeave.getEmployee().getFirstName() != null && !empApplyLeave.getEmployee().getFirstName().isEmpty()){
			modifyEmployeeLeaveForm.setEmployeeName(empApplyLeave.getEmployee().getFirstName());
		}
		if(empApplyLeave.getEmployee().getDepartment() != null && empApplyLeave.getEmployee().getDepartment().getName() != null && !empApplyLeave.getEmployee().getFingerPrintId().isEmpty()){
			modifyEmployeeLeaveForm.setDepartmentName(empApplyLeave.getEmployee().getDepartment().getName());
		}
		if(empApplyLeave.getEmployee().getDesignation() != null && empApplyLeave.getEmployee().getDesignation().getName() != null && !empApplyLeave.getEmployee().getDesignation().getName().isEmpty()){
			modifyEmployeeLeaveForm.setDesignationName(empApplyLeave.getEmployee().getDesignation().getName());
		}
		if(empApplyLeave.getFromDate() != null){
			String startDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getFromDate()), ModifyEmployeeLeaveHandler.SQL_DATEFORMAT,	ModifyEmployeeLeaveHandler.FROM_DATEFORMAT);
			modifyEmployeeLeaveForm.setStartDate(startDate);
		}
		if(empApplyLeave.getToDate() != null ){
			String endDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getToDate()), ModifyEmployeeLeaveHandler.SQL_DATEFORMAT,	ModifyEmployeeLeaveHandler.FROM_DATEFORMAT);
			modifyEmployeeLeaveForm.setEndDate(endDate);
		}
		if(empApplyLeave.getNoOfDays() != null ){
			modifyEmployeeLeaveForm.setNoOfDays(empApplyLeave.getNoOfDays());
		}
		if(empApplyLeave.getReason() != null && !empApplyLeave.getReason().isEmpty()){
			modifyEmployeeLeaveForm.setReason(empApplyLeave.getReason());
		}
		if(empApplyLeave.getEmployee().getEmptype() != null && empApplyLeave.getEmployee().getEmptype().getId() != 0){
			modifyEmployeeLeaveForm.setEmpTypeId(String.valueOf(empApplyLeave.getEmployee().getEmptype().getId()));
		}
		if(empApplyLeave.getEmployee().getEmptype() != null && empApplyLeave.getEmployee().getEmptype().getId() != 0){
			modifyEmployeeLeaveForm.setLeaveTypeId(String.valueOf(empApplyLeave.getEmpLeaveType().getId()));
		}
		if(empApplyLeave.getEmployee().getEmptype() != null && empApplyLeave.getEmployee().getEmptype().getId() != 0){
			modifyEmployeeLeaveForm.setOldLeaveTypeId(String.valueOf(empApplyLeave.getEmpLeaveType().getId()));
		}
		if(empApplyLeave.getIsHalfDay() != null && empApplyLeave.getIsHalfDay()){
			modifyEmployeeLeaveForm.setHalfDayDisplay(true);
			modifyEmployeeLeaveForm.setIsHalfday("yes");
		}else if(empApplyLeave.getIsHalfDay() != null && !empApplyLeave.getIsHalfDay()){
			modifyEmployeeLeaveForm.setHalfDayDisplay(true);
			modifyEmployeeLeaveForm.setIsHalfday("no");
		}
		if(empApplyLeave.getIsAm() != null && !empApplyLeave.getIsAm().isEmpty()){
			modifyEmployeeLeaveForm.setAmDisplay(true);
			if(empApplyLeave.getIsAm().equalsIgnoreCase("AM")){
				modifyEmployeeLeaveForm.setIsAm("am");
			}
			if(empApplyLeave.getIsAm().equalsIgnoreCase("PM")){
				modifyEmployeeLeaveForm.setIsAm("pm");
			}
		}
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkLeavesAvailableOrNot(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm,int year) throws Exception{
		List<Integer> list=getCommonLeaves();
		boolean isValid=true;
		if(!list.contains(Integer.parseInt(modifyEmployeeLeaveForm.getLeaveTypeId()))){
			IModifyIEmployeeLeaveTransaction transaction = ModifyEmployeeLeaveTransactionImpl.getInstance();
			double remainingDays =0.0;
			EmpLeave empLeave=transaction.getRemainingDaysForEmployeeAndLeaveType(modifyEmployeeLeaveForm,year);
			if(empLeave != null){
				if(!empLeave.getEmpLeaveType().getIsLeave()){
					isValid = false;
				}else{
					remainingDays = empLeave.getLeavesRemaining()+ modifyEmployeeLeaveForm.getLeavesTaken();
					Date startDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getStartDate());
					Date endDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getEndDate());
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(startDate);
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(endDate);
					double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
					if(modifyEmployeeLeaveForm.getIsHalfday()!=null && !modifyEmployeeLeaveForm.getIsHalfday().isEmpty()){
						if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
							daysBetween=daysBetween-0.5;
						}
					}
					if(remainingDays>=daysBetween){
						isValid=false;
					}
				}
			}
		}else{
			isValid=false;
		}
		return isValid;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getCommonLeaves() throws Exception{
		String query="select e.id from EmpLeaveType e where e.isActive=1 and e.isLeave=0";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveApplyLeave(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm,int year) throws Exception {
		Date startDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getEndDate());
		if(!modifyEmployeeLeaveForm.getStartDate().equalsIgnoreCase(modifyEmployeeLeaveForm.getEndDate())){
			modifyEmployeeLeaveForm.setIsHalfday(null);
			modifyEmployeeLeaveForm.setIsAm(null);
			modifyEmployeeLeaveForm.setHalfDayDisplay(false);
			modifyEmployeeLeaveForm.setAmDisplay(false);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(modifyEmployeeLeaveForm.getIsHalfday()!=null && !modifyEmployeeLeaveForm.getIsHalfday().isEmpty()){
			if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				daysBetween=daysBetween-0.5;
			}
		}
		IEmployeeApplyLeaveTransaction transaction1=EmployeeApplyLeaveTransactionImpl.getInstance();
		boolean continuousdays = transaction1.checkLeaveType(modifyEmployeeLeaveForm.getLeaveTypeId());
		if(!continuousdays){
			int count = CommonUtil.getSundaysForDateRange(modifyEmployeeLeaveForm.getStartDate(),modifyEmployeeLeaveForm.getEndDate());
			daysBetween = daysBetween-count;
		}
		EmpApplyLeave bo=ModifyEmployeeLeaveHelper.getInstance().convertFormToBO(modifyEmployeeLeaveForm,daysBetween,year);
		IModifyIEmployeeLeaveTransaction transaction = ModifyEmployeeLeaveTransactionImpl.getInstance();
		double noOfDays = modifyEmployeeLeaveForm.getNoOfDays();
		double noOfLeaves = 0.0;
		String mode = "";
		if(daysBetween>noOfDays){
			noOfLeaves = daysBetween - noOfDays;
			mode = mode + "Add";
		}else if(daysBetween<noOfDays){
			noOfLeaves = noOfDays - daysBetween;
			mode = mode + "Subtract";
		}
		return transaction.saveApplyLeave(bo,modifyEmployeeLeaveForm,getCommonLeaves(),noOfLeaves,year,mode,daysBetween);
	}
	
	/**
	 * @param employeeId
	 * @param isExemption
	 * @return
	 */
	public List<LeaveTypeTo> getLeaveTypesForEmployee(String employeeId, String isExemption,String date,int year) throws Exception {
		String query="";
		if(!isExemption.equalsIgnoreCase("yes")){
			query="select etype.id,etype.name from EmpLeaveType etype,EmpLeave e where ((etype.id=e.empLeaveType.id " +
					"and e.employee.id= " +employeeId+
					"and e.year=" +year+
					") or (etype.isLeave=0 and etype.isExemption=0)) and etype.isActive=1 group by etype.id";
		}else{
			query="select e.id,e.name from EmpLeaveType e where e.isActive=1 and e.isLeave=0 and e.isExemption=1";
		}
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> list=transaction.getDataForQuery(query);
		return EmployeeApplyLeaveHelper.getInstance().convertBotoTo(list);
	}
	
	
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean cancelEmployeeLeaveDetails(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm,int year) throws Exception {
		Date startDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToDate(modifyEmployeeLeaveForm.getEndDate());
		if(!modifyEmployeeLeaveForm.getStartDate().equalsIgnoreCase(modifyEmployeeLeaveForm.getEndDate())){
			modifyEmployeeLeaveForm.setIsHalfday(null);
			modifyEmployeeLeaveForm.setIsAm(null);
			modifyEmployeeLeaveForm.setHalfDayDisplay(false);
			modifyEmployeeLeaveForm.setAmDisplay(false);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(modifyEmployeeLeaveForm.getIsHalfday()!=null && !modifyEmployeeLeaveForm.getIsHalfday().isEmpty()){
			if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				daysBetween=daysBetween-0.5;
			}
		}
		int count = CommonUtil.getSundaysForDateRange(modifyEmployeeLeaveForm.getStartDate(),modifyEmployeeLeaveForm.getEndDate());
		daysBetween = daysBetween-count;
		EmpApplyLeave bo=ModifyEmployeeLeaveHelper.getInstance().convertFormToBOForCancel(modifyEmployeeLeaveForm,daysBetween,year);
		IModifyIEmployeeLeaveTransaction transaction = ModifyEmployeeLeaveTransactionImpl.getInstance();
		return transaction.cancelLeave(bo,modifyEmployeeLeaveForm,getCommonLeaves(),daysBetween,year);
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkAlreadyExists( ModifyEmployeeLeaveForm modifyEmployeeLeaveForm) throws Exception{
		String query=ModifyEmployeeLeaveHelper.getInstance().getAlreadyExistsQuery(modifyEmployeeLeaveForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		boolean isHalfDay=false;
		boolean isExist=false;
		boolean isAm=false;
		double leaveDaysTaken=0.0;
		int count=0;
			if(modifyEmployeeLeaveForm.getIsHalfday()!=null && modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes"))
				isHalfDay=true;
			if(modifyEmployeeLeaveForm.getIsAm()!=null && modifyEmployeeLeaveForm.getIsAm().equalsIgnoreCase("am"))
				isAm=true;
			if(list!=null && !list.isEmpty()){
				Iterator<EmpApplyLeave> itr=list.iterator();
				while (itr.hasNext()) {
					EmpApplyLeave bo= itr.next();
				   if(bo.getId()==modifyEmployeeLeaveForm.getApplyLeaveId()){
					 if(isHalfDay){
						if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
							{
							  isExist=false; 
							   count=count+1; 	
							}
							else
							{
								if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
								{
									count=count+1;
								}
								else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
								{
									count=count+1;
								}
								else if(!isAm && bo.getIsAm().equalsIgnoreCase("am"))
								{
									count=count+1;
								}
							}
					  }else
					  {
						  if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
							{
							   count=count+1; 	
							}
						  	else 
						  	{
						  		if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
						  		{
						  			count=count+1;
						  		}
						  		else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
						  		{
						  			count=count+1;
						  		}
						  		else if(!isAm && bo.getIsAm().equalsIgnoreCase("am"))
								{
									count=count+1;
								}
					  		}
					  }
				   }else 
				   {
					   count=count+1;
				   }
				   if(modifyEmployeeLeaveForm.getOldLeaveTypeId().equals(modifyEmployeeLeaveForm.getLeaveTypeId()))
				   {
					   	leaveDaysTaken=leaveDaysTaken+bo.getNoOfDays(); 
				   }
				}	
			}
			
			if(count>1)
			{
				 isExist=true;
			}
			modifyEmployeeLeaveForm.setLeavesTaken(leaveDaysTaken);
		return isExist;
	}
}
