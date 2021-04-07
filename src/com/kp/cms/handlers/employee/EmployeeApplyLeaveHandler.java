package com.kp.cms.handlers.employee;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.helpers.employee.EmployeeApplyLeaveHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeApplyLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeApplyLeaveHandler {
	/**
	 * Singleton object of EmployeeApplyLeaveHandler
	 */
	private static volatile EmployeeApplyLeaveHandler employeeApplyLeaveHandler = null;
	private static final Log log = LogFactory.getLog(EmployeeApplyLeaveHandler.class);
	private EmployeeApplyLeaveHandler() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHandler.
	 * @return
	 */
	public static EmployeeApplyLeaveHandler getInstance() {
		if (employeeApplyLeaveHandler == null) {
			employeeApplyLeaveHandler = new EmployeeApplyLeaveHandler();
		}
		return employeeApplyLeaveHandler;
	}
	/**
	 * @param empCode
	 * @param fingerPrintId
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeDetails(String userId, String fingerPrintId) throws Exception {
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		return transaction.getEmployeeDetails(userId,fingerPrintId);
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
					") or (etype.isLeave=0 and etype.isExemption=0)) and etype.isExemption=0 and etype.isActive=1 group by etype.id";
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
	public boolean checkAlreadyExists( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception{
		String query=EmployeeApplyLeaveHelper.getInstance().getAlreadyExistsQuery(employeeApplyLeaveForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		String query1 = EmployeeApplyLeaveHelper.getInstance().getAlreadyExistsQuery1(employeeApplyLeaveForm);
		List list1=transaction.getDataForQuery(query1);
		boolean isHalfDay=false;
		boolean isExist=false;
		boolean isAm=false;
		if(employeeApplyLeaveForm.getIsHalfday()!=null && employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes"))
			isHalfDay=true;
		if(employeeApplyLeaveForm.getIsAm()!=null && employeeApplyLeaveForm.getIsAm().equalsIgnoreCase("am"))
			isAm=true;
		
		if(list!=null && !list.isEmpty()){
			if(isHalfDay){
			Iterator<EmpApplyLeave> itr=list.iterator();
			while (itr.hasNext()) {
				EmpApplyLeave bo= itr.next();
				if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
					isExist=true;
				else{
					if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
						isExist=true;
					else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
						isExist=true;
				}
			}
			}else{
				isExist=true;
			}
			
		}
		if(list1!=null && !list1.isEmpty()){
			if(isHalfDay){
			Iterator<EmpOnlineLeave> itr=list1.iterator();
			while (itr.hasNext()) {
				EmpOnlineLeave bo= itr.next();
				if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
					isExist=true;
				else{
					if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
						isExist=true;
					else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
						isExist=true;
				}
			}
			}else{
				isExist=true;
			}
		}
		
		return isExist;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkLeavesAvailableOrNot(EmployeeApplyLeaveForm employeeApplyLeaveForm,int year) throws Exception{
		List<Integer> list=getCommonLeaves();
		boolean isValid=true;
		if(!list.contains(Integer.parseInt(employeeApplyLeaveForm.getLeaveTypeId()))){
			IEmployeeApplyLeaveTransaction transaction1=EmployeeApplyLeaveTransactionImpl.getInstance();
			double remainingDays=transaction1.getRemainingDaysForEmployeeAndLeaveType(employeeApplyLeaveForm,year);
			Date startDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(employeeApplyLeaveForm.getIsHalfday()!=null && !employeeApplyLeaveForm.getIsHalfday().isEmpty()){
				if(employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					daysBetween=daysBetween-0.5;
				}
			}
			if(remainingDays>=daysBetween){
				isValid=false;
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
	public boolean saveApplyLeave(EmployeeApplyLeaveForm employeeApplyLeaveForm,int year) throws Exception {
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		double daysBetween=new Double("0.00");
		/*Date startDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getEndDate());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(employeeApplyLeaveForm.getIsHalfday()!=null && !employeeApplyLeaveForm.getIsHalfday().isEmpty()){
			if(employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				daysBetween=daysBetween-0.5;
			}
		}
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		boolean continuousdays = transaction.checkLeaveType(employeeApplyLeaveForm.getLeaveTypeId());
		if(!continuousdays){
			int count = CommonUtil.getSundaysForDateRange(employeeApplyLeaveForm.getStartDate(),employeeApplyLeaveForm.getEndDate());
			daysBetween = daysBetween-count;
		}
		EmpApplyLeave bo=EmployeeApplyLeaveHelper.getInstance().convertFormToBO(employeeApplyLeaveForm,daysBetween,year);
		ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
		Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();*/
		List<EmpLeave> boList=EmployeeApplyLeaveHelper.getInstance().convertFormToBO(employeeApplyLeaveForm);
		return transaction.saveApplyLeave(boList,employeeApplyLeaveForm);
	}
	/**
	 * @param employeeId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public double getLeavesTaken(String employeeId, String date,int year) throws Exception{
		String[] dates=date.split("/");
		String query="select sum(e.noOfDays) from EmpApplyLeave e where e.isCanceled=0 and (e.year)='"+year+"' and month(e.fromDate)='"+dates[1]+"' and e.employee.id="+employeeId;
		//String query="select sum(e.noOfDays) from EmpApplyLeave e where e.isCanceled=0 and year(e.fromDate)='"+year+"' and month(e.fromDate)='"+dates[1]+"' and e.employee.id="+employeeId;
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		return transaction.getLeavesTaken(query);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public List<EmpLeaveTO> getDetails( String employeeId)  throws Exception{
		String query="from EmpLeave e where e.employee.id="+employeeId+" and e.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<EmpLeave> list=transaction.getDataForQuery(query);
		return EmployeeApplyLeaveHelper.convertBoListtoToList(list);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getEmpApplyLeaves(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			year=year-1;
	     }
		List<EmpApplyLeave> applyLeave=transaction.getEmpApplyLeaves(Integer.parseInt(employeeApplyLeaveForm.getUserId()),year);
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		return applyLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getEmpLeaves(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			academicYear=academicYear-1;
	     }
		List<EmpLeave> empLeave=transaction.getEmpLeaves(Integer.parseInt(employeeApplyLeaveForm.getUserId()),academicYear);
		List<EmpLeaveTO> empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		
		return empLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getAplyLeavesWithFingerPrintIdOrEmpCode(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="";
		String name="";
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		List<EmpApplyLeaveTO> applyLeaveTo=null;
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			mode="fingerPrint";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
			if(month==6 && curMonth < month){
				year=year-1;
		     }
		List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithFingerPrintId(Integer.parseInt(employeeApplyLeaveForm.getFingerPrintId()),year);
		 applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty()){
			mode="empCode";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
			if(month==6 && curMonth < month){
				year=year-1;
		     }
			List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithEmpCode(employeeApplyLeaveForm.getEmpCode(),year);
			applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		}
		return applyLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getEmpLeavesWithFingerPrintIdOrEmpCode(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		//int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		List<EmpLeaveTO> empLeaveTo=null;
		String mode="";
		String name="";
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			mode="fingerPrint";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
			
			if(month==6 && curMonth < month){
			      academicYear=academicYear-1;
		     }
			List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getFingerPrintId(),academicYear,mode);
			empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty())
		{   
			mode="empCode";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
			if(month==6 && curMonth < month){
			      academicYear=academicYear-1;
		     }
			List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getEmpCode(),academicYear,mode);
			empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		}
		return empLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getAplyLeavesForHod(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		List<EmpApplyLeaveTO> applyLeaveTo=null;
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			List<String> fingerPrintIds=transaction.getFingerPrintIds(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(fingerPrintIds.contains(employeeApplyLeaveForm.getFingerPrintId())){
				List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithFingerPrintId(Integer.parseInt(employeeApplyLeaveForm.getFingerPrintId()),year);
			    applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
			}else{
				String departmentName = transaction.getDepartmentNameForFingerPrintId(employeeApplyLeaveForm.getFingerPrintId());
				employeeApplyLeaveForm.setDepartmentName(departmentName);
				throw new ApplicationException();
			}
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty()){
			List<String> empCodes=transaction.getEmpCodes(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(empCodes.contains(employeeApplyLeaveForm.getEmpCode())){
			List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithEmpCode(employeeApplyLeaveForm.getEmpCode(),year);
			applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
			}else{
				String departmentName = transaction.getDepartmentNameForEmpCode(employeeApplyLeaveForm.getEmpCode());
				employeeApplyLeaveForm.setDepartmentName(departmentName);
				throw new ApplicationException();
			}
		}else if(employeeApplyLeaveForm.getEmployeeName()!=null && !employeeApplyLeaveForm.getEmployeeName().isEmpty()){
			List<String> empNames=transaction.getEmpNames(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(empNames.contains(employeeApplyLeaveForm.getEmployeeName())){
				List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithName(employeeApplyLeaveForm.getEmployeeName(), year);
				applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
			}else{
				String departmentName = transaction.getDepartmentNameForEmpName(employeeApplyLeaveForm.getEmployeeName());
				employeeApplyLeaveForm.setDepartmentName(departmentName);
				throw new ApplicationException();
			}
		}
		return applyLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getEmpLeavesForHod(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		//int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		List<EmpLeaveTO> empLeaveTo=null;
		String mode="";
		String name="";
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			mode="fingerPrint";
			List<String> fingerPrintIds=transaction.getFingerPrintIds(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(fingerPrintIds.contains(employeeApplyLeaveForm.getFingerPrintId())){
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
				
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
				List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getFingerPrintId(),academicYear,mode);
				empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
			}
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty())
		{   
			mode="empCode";
			List<String> empCodes=transaction.getEmpCodes(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(empCodes.contains(employeeApplyLeaveForm.getEmpCode())){
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
				List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getEmpCode(),academicYear,mode);
				empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
			}
		}else if(employeeApplyLeaveForm.getEmployeeName()!=null && !employeeApplyLeaveForm.getEmployeeName().isEmpty()){
			mode="name";
			List<String> empNames=transaction.getEmpNames(Integer.parseInt(employeeApplyLeaveForm.getUserId()));
			if(empNames.contains(employeeApplyLeaveForm.getEmployeeName())){
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,employeeApplyLeaveForm.getEmployeeName());
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
				List<EmpLeave> empLeave=transaction.getEmpLeavesWithName(employeeApplyLeaveForm.getEmployeeName(), academicYear);
				empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
				}
			}
		return empLeaveTo;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getLeaveTypeMap()throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		return transaction.getLeaveTypeMap();
	}
	/**
	 * @param input
	 * @param leaveTypeMap
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(InputStream input,Map<String,String> leaveTypeMap,EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		Map<String,Integer> employeeMap=transaction.getAllEmployees();
		List<EmpApplyLeave> applyLeaveList=EmployeeApplyLeaveHelper.getInstance().parseExcelData(input, leaveTypeMap,employeeApplyLeaveForm,employeeMap);
		return transaction.saveEmployeeApplyLeave(applyLeaveList,employeeApplyLeaveForm);
	}
	/**code added by sudhir
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception 
	 */
	public List<EmpApplyLeaveTO> getEmpOnlineLeaves( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception {
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			year=year-1;
	     }
		List<EmpOnlineLeave> onlineLeaves = transaction.getEmpOnlineLeaves(Integer.parseInt(employeeApplyLeaveForm.getUserId()),year);
		List<EmpApplyLeaveTO> applyLeaveTOs = EmployeeApplyLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(onlineLeaves);
		return applyLeaveTOs;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception 
	 */
	public List<EmpApplyLeaveTO> getEmpOnlineLeavesForEmpDepartment( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception {
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		//int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		String mode="";
		String name="";
		List<EmpApplyLeaveTO> empLeaveTo =null;
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
				mode="fingerPrint";
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
					List<EmpOnlineLeave> empLeave=transaction.getDepartmentWiseOnlineleave(employeeApplyLeaveForm.getFingerPrintId(),academicYear,mode);
					empLeaveTo = EmployeeApplyLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(empLeave);
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty())
		{   
				mode="empCode";
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
					List<EmpOnlineLeave> empLeave=transaction.getDepartmentWiseOnlineleave(employeeApplyLeaveForm.getEmpCode(),academicYear,mode);
					empLeaveTo = EmployeeApplyLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(empLeave);
		}else if(employeeApplyLeaveForm.getEmployeeName()!=null && !employeeApplyLeaveForm.getEmployeeName().isEmpty()){
				mode="name";
				int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,employeeApplyLeaveForm.getEmployeeName());
				if(month==6 && curMonth < month){
				      academicYear=academicYear-1;
			     }
				List<EmpOnlineLeave> empLeave=transaction.getOnlineLeavesWithName(employeeApplyLeaveForm.getEmployeeName(), academicYear);
				empLeaveTo = EmployeeApplyLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(empLeave);
				}
			
		return empLeaveTo;
	}
	public List<LeaveTypeTo> getLeaveTypesForEmployee() throws Exception {
		String query="";
			query="select e.id,e.name from EmpLeaveType e where e.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> list=transaction.getDataForQuery(query);
		return EmployeeApplyLeaveHelper.getInstance().convertBotoTo(list);
	}
}
