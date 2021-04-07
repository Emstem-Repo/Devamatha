package com.kp.cms.helpers.employee;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeApplyLeaveHelper {
	/**
	 * Singleton object of EmployeeApplyLeaveHelper
	 */
	private static volatile EmployeeApplyLeaveHelper employeeApplyLeaveHelper = null;
	private static final Log log = LogFactory.getLog(EmployeeApplyLeaveHelper.class);
	private static Map<Integer,String> monthMap=new HashMap<Integer, String>();
	private EmployeeApplyLeaveHelper() {
		monthMap.put(1, "January");
		monthMap.put(2, "February");
		monthMap.put(3, "March");
		monthMap.put(4, "April");
		monthMap.put(5, "May");
		monthMap.put(6, "June");
		monthMap.put(7, "July");
		monthMap.put(8, "August");
		monthMap.put(9, "September");
		monthMap.put(10, "October");
		monthMap.put(11, "November");
		monthMap.put(12, "December");
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHelper.
	 * @return
	 */
	public static EmployeeApplyLeaveHelper getInstance() {
		if (employeeApplyLeaveHelper == null) {
			employeeApplyLeaveHelper = new EmployeeApplyLeaveHelper();
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
	public String getAlreadyExistsQuery( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception{
		String query="from EmpApplyLeave e where e.isCanceled=0" +
				" and e.employee.id="+employeeApplyLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public String getAlreadyExistsQuery1( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception{
		String query="from EmpOnlineLeave e where e.isActive=1" +
				" and e.employee.id="+employeeApplyLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeave> convertFormToBO( EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception {
		String[] periods=employeeApplyLeaveForm.getPeriod();
		List<EmpLeave> boList=new ArrayList<EmpLeave>();
		for (String period : periods) {
		EmpLeave bo=new EmpLeave();
		Date startDate = CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		int month=cal1.get(Calendar.MONTH);
		int year=cal1.get(Calendar.YEAR);
		bo.setCreatedBy(employeeApplyLeaveForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(employeeApplyLeaveForm.getUserId());
		bo.setLastModifiedDate(new Date());
		//bo.setReason(employeeApplyLeaveForm.getReason());
		Employee employee=new Employee();
		employee.setId(Integer.parseInt(employeeApplyLeaveForm.getEmployeeId()));
		bo.setEmployee(employee);
		bo.setIsActive(true);
		bo.setYear(year);
		bo.setMonth(monthMap.get(month));
		bo.setPeriod(period);
		bo.setLeaveTypeName(employeeApplyLeaveForm.getLeaveTypeId());
		bo.setDate(CommonUtil.ConvertStringToDate(employeeApplyLeaveForm.getStartDate()));
		boList.add(bo);
		}
		/*EmpLeaveType empLeaveType=new EmpLeaveType();
		empLeaveType.setId(Integer.parseInt(employeeApplyLeaveForm.getLeaveTypeId()));
		bo.setEmpLeaveType(empLeaveType);*/
		
		//bo.setFromDate(CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate()));
		//bo.setToDate(CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate()));
		//bo.setIsCanceled(false);
		//bo.setNoOfDays(daysDiff);
		/*bo.setApplyThroughOnline(false);
		if(employeeApplyLeaveForm.getIsExemption().equalsIgnoreCase("yes")){
			bo.setIsExemption(true);
		}
		if(employeeApplyLeaveForm.getIsExemption().equalsIgnoreCase("no")){
			bo.setIsExemption(false);
		}*/
		/*if(employeeApplyLeaveForm.getIsHalfday()!=null && !employeeApplyLeaveForm.getIsHalfday().isEmpty()){
			if(employeeApplyLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				bo.setIsHalfDay(true);
				if(employeeApplyLeaveForm.getIsAm().equalsIgnoreCase("am")){
					bo.setIsAm("AM");
				}else
					bo.setIsAm("PM");
			}else{
				bo.setIsAm("");
				bo.setIsHalfDay(false);
			}
		}else{
			bo.setIsAm("");
			bo.setIsHalfDay(false);
		}*/
		return boList;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<EmpLeaveTO> convertBoListtoToList(List<EmpLeave> list) throws Exception{
		List<EmpLeaveTO> tos=new ArrayList<EmpLeaveTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<EmpLeave> itr=list.iterator();
			while (itr.hasNext()) {
				EmpLeave bo =itr.next();
				EmpLeaveTO to=new EmpLeaveTO();
				to.setEmpLeaveTypeName(bo.getLeaveTypeName());
				to.setEmpLeaveTypeName(bo.getLeaveTypeName());
				to.setYear(bo.getYear());
				to.setMonth(bo.getMonth());
				to.setPeriod(bo.getPeriod());
				to.setDate(bo.getDate());
				tos.add(to);
				//to.setLeavesAllocated(bo.getLeavesAllocated().toString());
				//to.setLeavesRemaining(bo.getLeavesRemaining().toString());
				//to.setLeavesSanctioned(bo.getLeavesSanctioned().toString());
				
			}
		}
		return tos;
	}
	/**
	 * @param applyLeave
	 * @return
	 */
	public  List<EmpApplyLeaveTO> convertApplyLeaveBOtoTO(List<EmpApplyLeave> applyLeave){
		List<EmpApplyLeaveTO> applyLeavesTo=new ArrayList<EmpApplyLeaveTO>();
		Iterator itr=applyLeave.iterator();
		while(itr.hasNext()){
			EmpApplyLeaveTO applyLeaveTO=new EmpApplyLeaveTO();
			EmpApplyLeave applyLeaves=(EmpApplyLeave)itr.next();
			if(applyLeaves.getEmpLeaveType()!=null){
				applyLeaveTO.setEmpLeaveType(applyLeaves.getEmpLeaveType().getName());
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
			applyLeavesTo.add(applyLeaveTO);
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
		List<String> dupFingIds=new ArrayList<String>();
		List<String> wrongDatesErrors=new ArrayList<String>();
		List<String> wrongDatesFormats=new ArrayList<String>();
		Map<Integer,Map<String,String>> dupFigIdsMap=new HashMap<Integer,Map<String,String>>();
		boolean isHalfday=true;
		try{
 		LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
// /* code modified by chandra 		
 while(parser.getLine()!=null){
	 DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
 		if(parser.getValueByLabel("FingerPrint_Id")!=null && !StringUtils.isEmpty(parser.getValueByLabel("FingerPrint_Id")) && employeeMap.containsKey(parser.getValueByLabel("FingerPrint_Id").trim())
				&& parser.getValueByLabel("Leave_Type")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Leave_Type")) && leaveTypeMap!=null && leaveTypeMap.containsKey(parser.getValueByLabel("Leave_Type"))){
 			boolean flag=false;
 			if(dupFigIdsMap.containsKey(Integer.parseInt(parser.getValueByLabel("FingerPrint_Id").trim()))){
 				if((parser.getValueByLabel("From_Date")!=null && !parser.getValueByLabel("From_Date").isEmpty())&&
	            		 (parser.getValueByLabel("To_Date")!=null && !parser.getValueByLabel("To_Date").isEmpty())){
 					Date newFromDate=df.parse(parser.getValueByLabel("From_Date"));
 					Date newToDate=df.parse(parser.getValueByLabel("To_Date"));
		if(newFromDate.before(newToDate)|| (newFromDate.equals(newToDate))){
 				Map<String,String> subMap1=dupFigIdsMap.get(Integer.parseInt(parser.getValueByLabel("FingerPrint_Id").trim()));
 				for (Map.Entry<String, String> entry:subMap1.entrySet()) {
 					String fromDate=entry.getKey();
 					String toDate=entry.getValue();
 					
 					Date oldFromDate=df.parse(fromDate);
 					Date oldToDate=df.parse(toDate);
 					
 					if((oldFromDate.equals(newFromDate)) && (oldToDate.equals(newToDate))){
 						dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 						flag=true;
 					}else {
 						if((oldFromDate.before(newFromDate)) && (newToDate.before(oldToDate))){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}else if((oldFromDate.equals(newFromDate)) && (newToDate.before(oldToDate))){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}else if((oldFromDate.before(newFromDate)) && (newToDate.equals(oldToDate))){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}else if((newFromDate.before(oldFromDate)) && (newToDate.equals(oldFromDate))){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}else if((oldFromDate.before(newFromDate)) && (oldToDate.before(newToDate))&&(newFromDate).before(oldToDate)){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}else if((newFromDate.before(oldFromDate)) && (oldToDate.before(newToDate))){
 							
 							dupFingIds.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 							flag=true;
 						}
 					}
 				}
 				if(!flag){
						
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
						if((CommonUtil.isValidDate(parser.getValueByLabel("From_Date"))) && (CommonUtil.isValidDate(parser.getValueByLabel("From_Date")))){
			             Map<String,String> subMap=dupFigIdsMap.get(Integer.parseInt(parser.getValueByLabel("FingerPrint_Id").trim()));
							subMap.put(parser.getValueByLabel("From_Date"), parser.getValueByLabel("To_Date"));
							dupFigIdsMap.put(Integer.parseInt(parser.getValueByLabel("FingerPrint_Id").trim()),subMap);
						}else {
							wrongDatesFormats.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
						}
					}
 				}else{
 					wrongDatesErrors.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 				}
 		}	
 			}else{
 				if((parser.getValueByLabel("From_Date")!=null && !parser.getValueByLabel("From_Date").isEmpty())&&
	            		 (parser.getValueByLabel("To_Date")!=null && !parser.getValueByLabel("To_Date").isEmpty())){
 					
 					Date fromDate=df.parse(parser.getValueByLabel("From_Date"));
 					Date toDate=df.parse(parser.getValueByLabel("To_Date"));
 			if(fromDate.before(toDate)||(fromDate.equals(toDate))){
 				
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
	             
	             Map<String,String> subMap=new HashMap<String,String>();
	             if((parser.getValueByLabel("From_Date")!=null && !parser.getValueByLabel("From_Date").isEmpty() && CommonUtil.isValidDate(parser.getValueByLabel("From_Date")))&&
	            		 (parser.getValueByLabel("To_Date")!=null && !parser.getValueByLabel("To_Date").isEmpty()&& CommonUtil.isValidDate(parser.getValueByLabel("To_Date")))){
	 				subMap.put(parser.getValueByLabel("From_Date"), parser.getValueByLabel("To_Date"));
	 				dupFigIdsMap.put(Integer.parseInt(parser.getValueByLabel("FingerPrint_Id").trim()),subMap);
	             	}else if((parser.getValueByLabel("From_Date")!=null && !parser.getValueByLabel("From_Date").isEmpty())&& (parser.getValueByLabel("To_Date")!=null && !parser.getValueByLabel("To_Date").isEmpty())){
	             		wrongDatesFormats.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
	             	}
 				  }else{
 					 wrongDatesErrors.add(parser.getValueByLabel("FingerPrint_Id").trim()+"("+parser.getValueByLabel("From_Date")+","+parser.getValueByLabel("To_Date")+")");
 				  }
 				}
 				
 			}
 		}else{
			 dateErrorIds.add(parser.getValueByLabel("FingerPrint_Id"));
 		}
 	 }
 //*/ code modified by chandra		
 
 
 
 		//code commented by chandra
		 /*while(parser.getLine()!=null){
			 
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
			
		 }*/
		}catch(Exception e){
			log.error(e);
		}
		employeeApplyLeaveForm.setDateErrorIds(dateErrorIds);
		employeeApplyLeaveForm.setDupFingDatesErrorMes(dupFingIds);
		employeeApplyLeaveForm.setWrongDatesErrorMes(wrongDatesErrors);
		employeeApplyLeaveForm.setWrongDatesFormats(wrongDatesFormats);
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
	 * @param onlineLeaves
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> convertEmpOnlineLeaveBoTOTo( List<EmpOnlineLeave> onlineLeaves) throws Exception{
		List<EmpApplyLeaveTO> list = new ArrayList<EmpApplyLeaveTO>();
		if(onlineLeaves!=null){
			Iterator<EmpOnlineLeave> iterator = onlineLeaves.iterator();
			while (iterator.hasNext()) {
				EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator .next();
				EmpApplyLeaveTO leaveTO = new EmpApplyLeaveTO();
				if(empOnlineLeave.getEmpLeaveType().getName()!=null && !empOnlineLeave.getEmpLeaveType().getName().isEmpty()){
					leaveTO.setEmpLeaveType(empOnlineLeave.getEmpLeaveType().getName());
				}
				if(empOnlineLeave.getFromDate()!=null){
					leaveTO.setFromDate(formatDate(empOnlineLeave.getFromDate()));
				}
				if(empOnlineLeave.getToDate()!=null){
					leaveTO.setToDate(formatDate(empOnlineLeave.getToDate()));
				}
				if(empOnlineLeave.getStatus()!=null && !empOnlineLeave.getStatus().isEmpty()){
					leaveTO.setStatus(empOnlineLeave.getStatus());
				}
				if(empOnlineLeave.getNoOfDays()!=null){
					leaveTO.setNoOfDays(empOnlineLeave.getNoOfDays().toString());
				}
				if(empOnlineLeave.getIsHalfDay()!=null){
					leaveTO.setIsHalfDay(empOnlineLeave.getIsHalfDay()?"Yes":"No");
				}else{
					leaveTO.setIsHalfDay("--");
				}
				if(empOnlineLeave.getIsAm()!=null && !empOnlineLeave.getIsAm().isEmpty()){
					if(empOnlineLeave.getIsAm().equalsIgnoreCase("AM"))
						leaveTO.setIsAm("AM");
					else
						leaveTO.setIsAm("PM");
				}else{
					leaveTO.setIsAm("--");
				}
				list.add(leaveTO);
			}
		}
		return list;
	}
}

