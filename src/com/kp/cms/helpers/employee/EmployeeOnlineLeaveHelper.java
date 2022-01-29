package com.kp.cms.helpers.employee;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.EmployeeOnlineLeaveForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeOnlineLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeOnlineLeaveHelper {
	/**
	 * Singleton object of EmployeeApplyLeaveHelper
	 */
	private static volatile EmployeeOnlineLeaveHelper employeeApplyLeaveHelper = null;
	private static final Log log = LogFactory.getLog(EmployeeOnlineLeaveHelper.class);
	private EmployeeOnlineLeaveHelper() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHelper.
	 * @return
	 */
	public static EmployeeOnlineLeaveHelper getInstance() {
		if (employeeApplyLeaveHelper == null) {
			employeeApplyLeaveHelper = new EmployeeOnlineLeaveHelper();
		}
		return employeeApplyLeaveHelper;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<LeaveTypeTo> convertBotoTo(List<Object[]> list) throws Exception {
		List<LeaveTypeTo> tos=new ArrayList<LeaveTypeTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj= (Object[]) itr.next();
				if(obj[0]!=null && obj[1]!=null){
					LeaveTypeTo to=new LeaveTypeTo();
					to.setId(Integer.parseInt(obj[0].toString()));
					to.setName(obj[1].toString());
					tos.add(to);
				}
			}
		}
		return tos;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public String getAlreadyExistsQuery( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		String query="from EmpApplyLeave e where e.isCanceled=0" +
				" and e.employee.id="+employeeOnlineLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public String getAlreadyExistsQuery1( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		String query="from EmpOnlineLeave e where e.isActive=1" +
				" and e.employee.id="+employeeOnlineLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public EmpOnlineLeave convertFormToBO( EmployeeOnlineLeaveForm employeeOnlineLeaveForm,double daysDiff,int year) throws Exception {
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		EmployeeApprover approverId = transaction.getApproverId(employeeOnlineLeaveForm.getEmployeeId());
		if(approverId == null){
			throw new ApplicationException();
		}
		EmpOnlineLeave bo=new EmpOnlineLeave();
		bo.setCreatedBy(employeeOnlineLeaveForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(employeeOnlineLeaveForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setReason(employeeOnlineLeaveForm.getReason());
		Employee employee=new Employee();
		employee.setId(Integer.parseInt(employeeOnlineLeaveForm.getEmployeeId()));
		bo.setEmployee(employee);
		if(approverId.getApprover() != null && approverId.getApprover().getId() != 0){
			Employee leaveApprover = new Employee();
			leaveApprover.setId(approverId.getApprover().getId());
			bo.setLeaveApprover(leaveApprover);
			if(approverId.getApprover().getWorkEmail() != null){
				employeeOnlineLeaveForm.setApproverMailId(approverId.getApprover().getWorkEmail());
			}else if(approverId.getApprover().getOtherEmail() != null){
				employeeOnlineLeaveForm.setApproverMailId(approverId.getApprover().getOtherEmail());
			}
		}
		bo.setFromDate(CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getStartDate()));
		bo.setToDate(CommonUtil.ConvertStringToSQLDate(employeeOnlineLeaveForm.getEndDate()));
		bo.setIsActive(true);
		bo.setNoOfDays(daysDiff);
		bo.setYear(year);
		bo.setIsApproved(false);
		bo.setIsAuthorized(false);
		bo.setStatus("Applied");
		EmpLeaveType empLeaveType=new EmpLeaveType();
		empLeaveType.setId(Integer.parseInt(employeeOnlineLeaveForm.getLeaveTypeId()));
		bo.setEmpLeaveType(empLeaveType);
		if(employeeOnlineLeaveForm.getIsHalfday()!=null && !employeeOnlineLeaveForm.getIsHalfday().isEmpty()){
			if(employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				bo.setIsHalfDay(true);
				if(employeeOnlineLeaveForm.getIsAm().equalsIgnoreCase("am")){
					bo.setIsAm("AM");
				}else
					bo.setIsAm("PM");
			}else{
				bo.setIsAm("");
				bo.setIsHalfDay(false);
			}
		}
		return bo;
	}
	/**
	 * @param list
	 * @param employeeOnlineLeaveForm 
	 * @return
	 * @throws Exception
	 */
	public static List<EmpLeaveTO> convertBoListtoToList(List<EmpLeave> list, EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		List<EmpLeaveTO> tos=new ArrayList<EmpLeaveTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<EmpLeave> itr=list.iterator();
			while (itr.hasNext()) {
				EmpLeave bo =itr.next();
				EmpLeaveTO to=new EmpLeaveTO();
				to.setEmpLeaveTypeName(bo.getEmpLeaveType().getName());
				to.setLeavesAllocated(bo.getLeavesAllocated().toString());
				to.setLeavesRemaining(bo.getLeavesRemaining().toString());
				to.setLeavesSanctioned(bo.getLeavesSanctioned().toString());
				tos.add(to);
				if(bo.getEmployee() != null && bo.getEmployee().getFirstName() != null){
					employeeOnlineLeaveForm.setCurrentEmployeeName(bo.getEmployee().getFirstName());
				}
				if(bo.getEmployee() != null && bo.getEmployee().getFingerPrintId() != null){
					employeeOnlineLeaveForm.setCurrentFingerPrintId(bo.getEmployee().getFingerPrintId());
				}
			}
		}
		return tos;
	}
	/**
	 * @param applyLeave
	 * @return
	 */
	public  List<EmpApplyLeaveTO> convertApplyLeaveBOtoTO(List<EmpOnlineLeave> applyLeave){
		List<EmpApplyLeaveTO> applyLeavesTo=new ArrayList<EmpApplyLeaveTO>();
		Iterator<EmpOnlineLeave> itr=applyLeave.iterator();
		while(itr.hasNext()){
			EmpApplyLeaveTO applyLeaveTO=new EmpApplyLeaveTO();
			EmpOnlineLeave applyLeaves=(EmpOnlineLeave)itr.next();
			if(applyLeaves.getId() != 0){
				applyLeaveTO.setId(applyLeaves.getId());
				if(applyLeaves.getEmpLeaveType()!=null){
					applyLeaveTO.setEmpLeaveType(applyLeaves.getEmpLeaveType().getName());
					applyLeaveTO.setEmpLeaveTypeId(applyLeaves.getEmpLeaveType().getId());
				}
				if(applyLeaves.getFromDate()!=null){
					applyLeaveTO.setFromDate(formatDate(applyLeaves.getFromDate()));
				}
				if(applyLeaves.getToDate()!=null){
					applyLeaveTO.setToDate(formatDate(applyLeaves.getToDate()));
				}
				if(applyLeaves.getIsHalfDay()!=null){
					applyLeaveTO.setIsHalfDay(applyLeaves.getIsHalfDay()?"Yes":"No");
				}else
					applyLeaveTO.setIsHalfDay("--");
				if(applyLeaves.getIsAm()!=null && !applyLeaves.getIsAm().isEmpty()){
					if(applyLeaves.getIsAm().equalsIgnoreCase("AM"))
						applyLeaveTO.setIsAm("AM");
					else
						applyLeaveTO.setIsAm("PM");
				}else
					applyLeaveTO.setIsAm("--");
				if(applyLeaves.getNoOfDays()!=null){
					applyLeaveTO.setNoOfDays(applyLeaves.getNoOfDays().toString());
				}
				if(applyLeaves.getReason()!=null){
					applyLeaveTO.setReason(applyLeaves.getReason());
				}
				if(applyLeaves.getStatus() != null){
					applyLeaveTO.setStatus(applyLeaves.getStatus());
				}
				if(applyLeaves.getEmployee() != null && applyLeaves.getEmployee().getFirstName() != null){
					applyLeaveTO.setEmployeeName(applyLeaves.getEmployee().getFirstName());
				}
				if(applyLeaves.getEmployee() != null && applyLeaves.getEmployee().getFingerPrintId() != null){
					applyLeaveTO.setEmployeeId(applyLeaves.getEmployee().getFingerPrintId());
					applyLeaveTO.setEmpId(applyLeaves.getEmployee().getId());
				}
				if(applyLeaves.getEmployee().getEmptype() != null){
					applyLeaveTO.setEmpTypeId(applyLeaves.getEmployee().getEmptype().getId());
				}
				if(applyLeaves.getEmployee() != null){
					applyLeaveTO.setEmployee(applyLeaves.getEmployee());
				}
				if(applyLeaves.getYear() != null){
					applyLeaveTO.setYear(applyLeaves.getYear());
				}
				applyLeaveTO.setOnlineLeave(applyLeaves);
				applyLeavesTo.add(applyLeaveTO);
			}
		}
		return applyLeavesTo;
	}
	/**
	 * @param empLeave
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public  List<EmpLeaveTO> convertEmpLeaveBOtoTO(List<EmpLeave> empLeave,EmployeeApplyLeaveForm employeeApplyLeaveForm){
		List<EmpLeaveTO> leavesTo=new ArrayList<EmpLeaveTO>();
		if(empLeave!=null){
		Iterator itr=empLeave.iterator();
		while(itr.hasNext()){
			EmpLeaveTO leaveTO=new EmpLeaveTO();
			EmpLeave empLeaves=(EmpLeave)itr.next();
			if(empLeaves.getEmpLeaveType()!=null){
				leaveTO.setEmpLeaveTypeName(empLeaves.getEmpLeaveType().getName());
			}
			if(empLeaves.getLeavesAllocated()!=null){
				leaveTO.setLeavesAllocated(empLeaves.getLeavesAllocated().toString());
			}
			if(empLeaves.getLeavesSanctioned()!=null){
				leaveTO.setLeavesSanctioned(empLeaves.getLeavesSanctioned().toString());
			}
			if(empLeaves.getLeavesRemaining()!=null){
				leaveTO.setLeavesRemaining(empLeaves.getLeavesRemaining().toString());
			}
			if(empLeaves.getEmployee().getFirstName()!=null){
				employeeApplyLeaveForm.setEmployeeName(empLeaves.getEmployee().getFirstName());
			}
			if(empLeaves.getEmployee().getFingerPrintId()!=null && !empLeaves.getEmployee().getFingerPrintId().isEmpty()){
				employeeApplyLeaveForm.setFingerPrintId(empLeaves.getEmployee().getFingerPrintId());
			}
			if(empLeaves.getEmployee().getCode()!=null && !empLeaves.getEmployee().getCode().isEmpty()){
				employeeApplyLeaveForm.setEmpCode(empLeaves.getEmployee().getCode());
			}
			leavesTo.add(leaveTO);
		}}
		return leavesTo;
	}
	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	/**
	 * @param date
	 * @return
	 */
	public static String getYearFromDate(Date date){
		DateFormat format=new SimpleDateFormat("yyyy");
		String year=format.format(date);
		return year;
	}
	/**
	 * @param inStream
	 * @param leaveTypeMap
	 * @param employeeApplyLeaveForm
	 * @param employeeMap
	 * @return
	 */
	public List<EmpApplyLeave> parseExcelData(InputStream inStream,Map<String,String> leaveTypeMap,EmployeeApplyLeaveForm employeeApplyLeaveForm,Map<String,Integer> employeeMap){
		List<EmpApplyLeave> applyLeaveList=new ArrayList<EmpApplyLeave>();
		List<String> dateErrorIds=new ArrayList<String>();
		boolean isHalfday=true;
		try{
 		LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
		 while(parser.getLine()!=null){
			 
			 if(parser.getValueByLabel("FingerPrint_Id")!=null && !StringUtils.isEmpty(parser.getValueByLabel("FingerPrint_Id")) && employeeMap.containsKey(parser.getValueByLabel("FingerPrint_Id").trim())
					&& parser.getValueByLabel("Leave_Type")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Leave_Type")) && leaveTypeMap!=null && leaveTypeMap.containsKey(parser.getValueByLabel("Leave_Type"))){
				 EmpApplyLeave applyLeave=new EmpApplyLeave();
				 applyLeave.setModifiedBy(employeeApplyLeaveForm.getUserId());
	             applyLeave.setLastModifiedDate(new Date());
	             applyLeave.setCreatedBy(employeeApplyLeaveForm.getUserId());
	             applyLeave.setCreatedDate(new Date());
	             applyLeave.setIsActive(true);
	             applyLeave.setIsCanceled(false);
	             
	             
				 Employee employee=new Employee();
				 employee.setId(employeeMap.get(parser.getValueByLabel("FingerPrint_Id").trim()));
				 employee.setFingerPrintId(parser.getValueByLabel("FingerPrint_Id"));
				 applyLeave.setEmployee(employee);
				 
				 EmpLeaveType leaveType=new EmpLeaveType();
				 leaveType.setId(Integer.parseInt(leaveTypeMap.get(parser.getValueByLabel("Leave_Type"))));
				 applyLeave.setEmpLeaveType(leaveType);
				 
				 if(parser.getValueByLabel("From_Date")!=null && !StringUtils.isEmpty(parser.getValueByLabel("From_Date")) && CommonUtil.isValidDate(parser.getValueByLabel("From_Date"))){
					 applyLeave.setFromDate(CommonUtil.ConvertStringToDate(parser.getValueByLabel("From_Date")));
				 }else
					 applyLeave.setFromDate(null);
	             if(parser.getValueByLabel("To_Date")!=null && !StringUtils.isEmpty(parser.getValueByLabel("To_Date")) && CommonUtil.isValidDate(parser.getValueByLabel("From_Date"))){
	            	 applyLeave.setToDate(CommonUtil.ConvertStringToDate(parser.getValueByLabel("To_Date")));
				 }else
					 applyLeave.setToDate(null);
	             if(applyLeave.getFromDate()!=null && applyLeave.getToDate()!=null){
	            	   Calendar cal1 = Calendar.getInstance();
		  			   cal1.setTime(applyLeave.getFromDate());
		  			   Calendar cal2 = Calendar.getInstance();
		  			   cal2.setTime(applyLeave.getToDate());
		  			   long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		  			   double days=(double)daysBetween;
	            	   applyLeave.setNoOfDays(days);
	            	 if(parser.getValueByLabel("Is_Half_Day")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Is_Half_Day"))){
		            	 if(parser.getValueByLabel("Is_Half_Day").equalsIgnoreCase("yes")){
						     applyLeave.setIsHalfDay(true);
						     if(applyLeave.getNoOfDays()==1){
		            	        applyLeave.setNoOfDays(0.5);
						     }else
						    	 isHalfday=false;
		            	 }
		            	 else{
		            		 applyLeave.setIsHalfDay(false);
		            		 isHalfday=true;
		            	 }
					 }else{
						 applyLeave.setIsHalfDay(false);
					 }
		             if(parser.getValueByLabel("Is_Am")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Is_Am"))){
		            	 if(parser.getValueByLabel("Is_Am").equalsIgnoreCase("AM")){
						 applyLeave.setIsAm("AM");
		            	 }
		            	 else
		            		 applyLeave.setIsAm("PM");
					 }
		             if(parser.getValueByLabel("Year")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Year"))){
						 applyLeave.setYear(Integer.parseInt(parser.getValueByLabel("Year")));
					 }else{
						 if(parser.getValueByLabel("From_Date")!=null){
							 String year=CommonUtil.getYear(CommonUtil.ConvertStringToDate(parser.getValueByLabel("From_Date")));
							 applyLeave.setYear(Integer.parseInt(year));
						 }
					 }
		             if(isHalfday)
		            	 applyLeaveList.add(applyLeave);
	             }
			 }else
				 dateErrorIds.add(parser.getValueByLabel("FingerPrint_Id"));
			
		 }
		}catch(Exception e){
			log.error(e);
		}
		employeeApplyLeaveForm.setDateErrorIds(dateErrorIds);
		return applyLeaveList;
	}
	/**
	 * @param startDate
	 * @param endDate
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public boolean validateDate(Date startDate,Date endDate,EmployeeApplyLeaveForm employeeApplyLeaveForm) {
		boolean isValid=false;
			if(!startDate.after(endDate) && !endDate.before(startDate)){
			   Calendar cal1 = Calendar.getInstance();
			   cal1.setTime(startDate);
			   Calendar cal2 = Calendar.getInstance();
			   cal2.setTime(endDate);
			   isValid=true;
			   long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			   double days=(double)daysBetween;
			   if (days >= 0) {
				 employeeApplyLeaveForm.setNoOfDays(days);
			   }
			}
		return isValid;	
	}
	/**
	 * @param leaveList
	 * @param employeeOnlineLeaveForm 
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeave> convertApplyLeaveTOtoBO(List<EmpApplyLeaveTO> leaveList, EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		List<EmpApplyLeave> approveList = new ArrayList<EmpApplyLeave>();
		List<Integer> approvedIds = new ArrayList<Integer>();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					approvedIds.add(empApplyLeaveTO.getId());
					EmpApplyLeave bo = new EmpApplyLeave();
					bo.setEmployee(empApplyLeaveTO.getEmployee());
					EmpLeaveType empLeaveType = new EmpLeaveType();
					empLeaveType.setId(empApplyLeaveTO.getEmpLeaveTypeId());
					bo.setEmpLeaveType(empLeaveType);
					if(empApplyLeaveTO.getNoOfDays() != null && !empApplyLeaveTO.getNoOfDays().trim().isEmpty()){
						bo.setNoOfDays(Double.parseDouble(empApplyLeaveTO.getNoOfDays()));
					}
					bo.setIsActive(true);
					bo.setIsAm(empApplyLeaveTO.getIsAm());
					if(empApplyLeaveTO.getIsHalfDay().equalsIgnoreCase("YES")){
						bo.setIsHalfDay(true);
					}else if(empApplyLeaveTO.getIsHalfDay().equalsIgnoreCase("NO")){
						bo.setIsHalfDay(false);
					}
					bo.setIsCanceled(false);
					bo.setYear(empApplyLeaveTO.getYear());
					if(empApplyLeaveTO.getOnlineLeave().getEmpLeaveType() != null && empApplyLeaveTO.getOnlineLeave().getEmpLeaveType().getisExemption() != null){
						bo.setIsExemption(empApplyLeaveTO.getOnlineLeave().getEmpLeaveType().getisExemption());
					}
					if(empApplyLeaveTO.getOnlineLeave().getFromDate() != null){
						bo.setFromDate(empApplyLeaveTO.getOnlineLeave().getFromDate());
					}
					if(empApplyLeaveTO.getOnlineLeave().getToDate() != null){
						bo.setToDate(empApplyLeaveTO.getOnlineLeave().getToDate());
					}
					bo.setApplyThroughOnline(true);
					bo.setCreatedBy(employeeOnlineLeaveForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(employeeOnlineLeaveForm.getUserId());
					bo.setLastModifiedDate(new Date());
					approveList.add(bo);
					employeeOnlineLeaveForm.setApprovedList(approvedIds);
				}
			}
		}
		return approveList;
	}
	/**
	 * @param applyLeave
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> convertEmpOnlineLeaveBoTOTo( List<EmpOnlineLeave> applyLeave) throws Exception{
		List<EmpApplyLeaveTO> applyLeavesTo=new ArrayList<EmpApplyLeaveTO>();
		Iterator<EmpOnlineLeave> itr=applyLeave.iterator();
		while(itr.hasNext()){
			EmpApplyLeaveTO applyLeaveTO=new EmpApplyLeaveTO();
			EmpOnlineLeave applyLeaves=(EmpOnlineLeave)itr.next();
			if(applyLeaves.getId() != 0){
				applyLeaveTO.setId(applyLeaves.getId());
				if(applyLeaves.getEmpLeaveType()!=null){
					applyLeaveTO.setEmpLeaveType(applyLeaves.getEmpLeaveType().getName());
					applyLeaveTO.setEmpLeaveTypeId(applyLeaves.getEmpLeaveType().getId());
				}
				if(applyLeaves.getFromDate()!=null){
					applyLeaveTO.setFromDate(formatDate(applyLeaves.getFromDate()));
				}
				if(applyLeaves.getToDate()!=null){
					applyLeaveTO.setToDate(formatDate(applyLeaves.getToDate()));
				}
				if(applyLeaves.getIsHalfDay()!=null){
					applyLeaveTO.setIsHalfDay(applyLeaves.getIsHalfDay()?"Yes":"No");
				}else
					applyLeaveTO.setIsHalfDay("--");
				if(applyLeaves.getIsAm()!=null && !applyLeaves.getIsAm().isEmpty()){
					if(applyLeaves.getIsAm().equalsIgnoreCase("AM"))
						applyLeaveTO.setIsAm("AM");
					else
						applyLeaveTO.setIsAm("PM");
				}else
					applyLeaveTO.setIsAm("--");
				if(applyLeaves.getNoOfDays()!=null){
					applyLeaveTO.setNoOfDays(applyLeaves.getNoOfDays().toString());
				}
				if(applyLeaves.getReason()!=null){
					applyLeaveTO.setReason(applyLeaves.getReason());
				}
				if(applyLeaves.getStatus() != null){
					applyLeaveTO.setStatus(applyLeaves.getStatus());
				}
				if(applyLeaves.getEmployee() != null && applyLeaves.getEmployee().getFirstName() != null){
					applyLeaveTO.setEmployeeName(applyLeaves.getEmployee().getFirstName());
				}
				if(applyLeaves.getEmployee() != null && applyLeaves.getEmployee().getFingerPrintId() != null){
					applyLeaveTO.setEmployeeId(applyLeaves.getEmployee().getFingerPrintId());
					applyLeaveTO.setEmpId(applyLeaves.getEmployee().getId());
				}
				if(applyLeaves.getEmployee().getEmptype() != null){
					applyLeaveTO.setEmpTypeId(applyLeaves.getEmployee().getEmptype().getId());
				}
				if(applyLeaves.getEmployee() != null){
					applyLeaveTO.setEmployee(applyLeaves.getEmployee());
				}
				if(applyLeaves.getYear() != null){
					applyLeaveTO.setYear(applyLeaves.getYear());
				}
				if(applyLeaves.getApprovedBy()!=null && !applyLeaves.getApprovedBy().isEmpty()){
					Integer approvedBy = Integer.parseInt(applyLeaves.getApprovedBy());
					String employeeName = EmployeeOnlineLeaveTransactionImpl.getInstance().getapprovedByName(approvedBy);
					if(employeeName!=null && !employeeName.isEmpty()){
						applyLeaveTO.setApprovedBy(employeeName);
					}
				}
				applyLeaveTO.setOnlineLeave(applyLeaves);
				applyLeavesTo.add(applyLeaveTO);
			}
		}
		return applyLeavesTo;
	}
}

