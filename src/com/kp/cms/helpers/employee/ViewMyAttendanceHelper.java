package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.ViewMyAttendanceForm;
import com.kp.cms.handlers.employee.ViewMyAttendanceHandler;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.transactions.employee.IViewMyAttendancetransaction;
import com.kp.cms.transactionsimpl.employee.ViewMyAttendanceTransactionimpl;
import com.kp.cms.utilities.CommonUtil;

public class ViewMyAttendanceHelper {
	/**
	 * Singleton object of ViewMyAttendanceHelper
	 */
	private static volatile ViewMyAttendanceHelper viewMyAttendanceHelper = null;
	private static final Log log = LogFactory.getLog(ViewMyAttendanceHelper.class);
	private static Map<String,String> monthMap = null;
	private ViewMyAttendanceHelper() {
		
	}
	/**
	 * return singleton object of ViewMyAttendanceHelper.
	 * @return
	 */
	public static ViewMyAttendanceHelper getInstance() {
		if (viewMyAttendanceHelper == null) {
			viewMyAttendanceHelper = new ViewMyAttendanceHelper();
		}
		return viewMyAttendanceHelper;
	}
	/**
	 * returning the query to get attendance for the employee for current month
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public String getQuery(ViewMyAttendanceForm viewAttForm) throws Exception {
		String query=null;
	if(viewAttForm.getViewEmpAttendance()){
		if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().trim().isEmpty()){
			 query="select e from EmpAttendance e   where e.employee.fingerPrintId=" +viewAttForm.getFingerPrintId()+
			" and e.isActive=1 and month(e.date)=" +CommonUtil.getCurrentMonth()+
			" and year(e.date)=" +CommonUtil.getCurrentYear();
			}
			else if (viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().trim().isEmpty()){
				 query="select e from EmpAttendance e where e.employee.code=" +viewAttForm.getEmpCode()+
					" and e.isActive=1   and month(e.date)=" +CommonUtil.getCurrentMonth()+
					" and year(e.date)=" +CommonUtil.getCurrentYear();
			}
		}
		else{
		 query="select e from EmpAttendance e join e.employee.userses u  where u.id=" +viewAttForm.getUserId()+
				" and e.isActive=1  and month(e.date)=" +CommonUtil.getCurrentMonth()+
				" and year(e.date)=" +CommonUtil.getCurrentYear();
		}
		query=query+" order by e.date";
		return query;
	}
	/**
	 * converting the obtained empAttBo to EmpAttTo to set in the form
	 * @param empAttBo
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpAttendanceTo> convertBOtoTOs(List<EmpAttendance> empAttBo,ViewMyAttendanceForm viewAttForm) throws Exception {
		List<String> datesOfMonth=null;
		Map<String, EmpAttendanceTo> empAttendanceDetails=new HashMap<String, EmpAttendanceTo>();
		IViewMyAttendancetransaction txn=ViewMyAttendanceTransactionimpl.getInstance();
		if(!viewAttForm.isPrevious()){
		 datesOfMonth=CommonUtil.getDatesOfMonth(CommonUtil.getCurrentMonth(), CommonUtil.getCurrentYear(),true);
		}
		else {
			//added by sudhir
			String month = monthMap.get(viewAttForm.getMonths());
			datesOfMonth=CommonUtil.getDatesOfMonth(month, viewAttForm.getYear(),false);
			//
//			if(Integer.parseInt(CommonUtil.getPreviousMonth())==12){
//				datesOfMonth=CommonUtil.getDatesOfMonth(CommonUtil.getPreviousMonth(), String.valueOf(Integer.parseInt(CommonUtil.getCurrentYear())-1),false);
//			}
//			else
//		 datesOfMonth=CommonUtil.getDatesOfMonth(CommonUtil.getPreviousMonth(), CommonUtil.getCurrentYear(),false);
		}
		
		List<EmpApplyLeave> empLeaveBo;
		if(!viewAttForm.isPrevious()){
		 empLeaveBo=ViewMyAttendanceHandler.getInstance().getEmployeeLeaveDetails(viewAttForm);
		}
		else {
			 empLeaveBo=ViewMyAttendanceHandler.getInstance().getEmployeePreviousLeaveDetails(viewAttForm);
		}
		Map<String,String> leaveDates=getLeaveList(empLeaveBo);
		
		
		if(empAttBo!=null && !empAttBo.isEmpty()){
			Iterator<EmpAttendance> itr=empAttBo.iterator();
			while (itr.hasNext()) {
				EmpAttendance empAttendance = (EmpAttendance) itr.next();
				EmpAttendanceTo empTo=new EmpAttendanceTo();
				String attDate=CommonUtil.ConvertStringToDateFormat(empAttendance.getDate().toString(),"yyyy-MM-dd", "dd-MM-yyyy");
				empTo.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(empAttendance.getDate().toString(),"yyyy-MM-dd", "dd-MM-yyyy"));
				if(empAttendance.getInTime()!=null ){
					
					if(empAttendance.getEmployee().getTimeIn()!=null && !empAttendance.getEmployee().getTimeIn().isEmpty()){
						if(empAttendance.getEmployee().getTimeIn().compareTo(empAttendance.getInTime())<0){
							empTo.setInTime("Late Entry ("+empAttendance.getInTime()+")");
						}else{
							empTo.setInTime(empAttendance.getInTime());	
						}
					}else if(empAttendance.getEmployee().getEmptype().getTimeIn()!=null && !empAttendance.getEmployee().getEmptype().getTimeIn().isEmpty()){
						if(empAttendance.getEmployee().getEmptype().getTimeIn().compareTo(empAttendance.getInTime())<0){
							empTo.setInTime("Late Entry ("+empAttendance.getInTime()+")");
						}else{
							empTo.setInTime(empAttendance.getInTime());	
						}
					}else{
						empTo.setInTime(empAttendance.getInTime());	
					}
				}
				empTo.setOutTime(empAttendance.getOutTime()!=null?empAttendance.getOutTime():"");
				viewAttForm.setDepartmentId(empAttendance.getEmployee().getDepartment()!=null?String.valueOf(empAttendance.getEmployee().getDepartment().getId()):"");
				viewAttForm.setTeachingStaff(empAttendance.getEmployee().getTeachingStaff()!=null?String.valueOf(empAttendance.getEmployee().getTeachingStaff()):"");
				empAttendanceDetails.put(attDate,empTo);
				if(empAttendance.getInTime()!=null && !empAttendance.getInTime().isEmpty() && empAttendance.getOutTime()!=null && !empAttendance.getOutTime().isEmpty() && !leaveDates.containsKey(attDate))
				datesOfMonth.remove(CommonUtil.ConvertStringToDateFormat(empAttendance.getDate().toString(),"yyyy-MM-dd", "dd-MM-yyyy"));
			}
			
		}
		List<Holidays> holidays= txn.getHolidays();
		List<String> holidayDates=getHolidayList(holidays);
		
		Map<String,String> empEventDates=null;
		if(viewAttForm.getDepartmentId()!=null && !viewAttForm.getDepartmentId().isEmpty() && viewAttForm.getTeachingStaff()!=null && !viewAttForm.getTeachingStaff().isEmpty()){
		String queryForEventVacation="select e from EmpEventVacation e " +
				" join e.empEventVacationDepartment d where e.isActive=1 and d.department.id=" +viewAttForm.getDepartmentId()+
				" and e.teachingStaff= "+viewAttForm.getTeachingStaff();
		List<EmpEventVacation> empEventVacations=txn.getEmpEventVacations(queryForEventVacation);
		 empEventDates=getEmpEventVacationList(empEventVacations);
		}
		if(datesOfMonth!=null && !datesOfMonth.isEmpty()){
			Iterator<String> datesIterator=datesOfMonth.iterator();
			while (datesIterator.hasNext()) {
				String dateString = (String) datesIterator.next();
				if(CommonUtil.checkIsSunday(CommonUtil.ConvertStringToDateFormat(dateString, "dd-MM-yyyy", "dd/MM/yyyy"))){
					EmpAttendanceTo empTo=new EmpAttendanceTo();
					empTo.setInTime("Sunday");
					empTo.setOutTime("-");
					empTo.setAttendanceDate(dateString);
					empAttendanceDetails.put(dateString,empTo);
				}
				else if(holidayDates!=null && holidayDates.contains(dateString)){
					EmpAttendanceTo empTo=new EmpAttendanceTo();
					empTo.setInTime("Holiday");
					empTo.setOutTime("-");
					empTo.setAttendanceDate(dateString);
					empAttendanceDetails.put(dateString,empTo);
				}
				else if(leaveDates!=null && leaveDates.containsKey(dateString)){
					if(empAttendanceDetails!=null && !empAttendanceDetails.isEmpty() && empAttendanceDetails.containsKey(dateString)){
						EmpAttendanceTo empTo=empAttendanceDetails.remove(dateString);
						String[] s=leaveDates.get(dateString).split("_");
						if(s[1].equalsIgnoreCase("AM")){
							empTo.setInTime(s[0]);
						}
						else if(s[1].equalsIgnoreCase("PM")){
							empTo.setOutTime(s[0]);
						}
						empAttendanceDetails.put(dateString,empTo);
					}
					else{
					EmpAttendanceTo empTo=new EmpAttendanceTo();
					String[] s=leaveDates.get(dateString).split("_");
					empTo.setInTime(s[0]);
					empTo.setOutTime("-");
					empTo.setAttendanceDate(dateString);
					empAttendanceDetails.put(dateString,empTo);
					}
				}
				else if(empEventDates!=null && empEventDates.containsKey(dateString)){
					if(empEventDates.get(dateString).equalsIgnoreCase("Vacation") || empEventDates.get(dateString).equalsIgnoreCase("Event")){
					EmpAttendanceTo empTo=new EmpAttendanceTo();
					empTo.setInTime(empEventDates.get(dateString));
					empTo.setOutTime("-");
					empTo.setAttendanceDate(dateString);
					empAttendanceDetails.put(dateString,empTo);
					}
					else if(empEventDates.get(dateString).equalsIgnoreCase("Exam")){
						if(empAttendanceDetails!=null && !empAttendanceDetails.isEmpty() && empAttendanceDetails.containsKey(dateString)){
						EmpAttendanceTo empTo=empAttendanceDetails.remove(dateString);
						if(empTo.getInTime()== null || empTo.getInTime().isEmpty()){
							empTo.setInTime("Exam");
						}
						else if (empTo.getOutTime()==null || empTo.getOutTime().isEmpty()){
							empTo.setOutTime("Exam");
						}
						empTo.setAttendanceDate(dateString);
						empAttendanceDetails.put(dateString,empTo);
						}
						else {
							EmpAttendanceTo empTo=new EmpAttendanceTo();
							empTo.setInTime("Absent");
							if(dateString.equalsIgnoreCase(CommonUtil.ConvertStringToDateFormat(CommonUtil.getTodayDate(), "dd/MM/yyyy", "dd-MM-yyyy")))
							empTo.setOutTime(" ");
							else empTo.setOutTime("Absent");
							empTo.setAttendanceDate(dateString);
							empAttendanceDetails.put(dateString,empTo);
						}
					}
				}
				else {
					if(empAttendanceDetails!=null && !empAttendanceDetails.isEmpty() && empAttendanceDetails.containsKey(dateString) ){
						EmpAttendanceTo empTo=empAttendanceDetails.remove(dateString);
					if(empTo.getInTime()== null || empTo.getInTime().isEmpty()){
						empTo.setInTime("Absent");
					}
					if((empTo.getOutTime()==null || empTo.getOutTime().isEmpty()) && dateString.equalsIgnoreCase(CommonUtil.ConvertStringToDateFormat(CommonUtil.getTodayDate(), "dd/MM/yyyy", "dd-MM-yyyy"))){
						empTo.setOutTime("");
					}
						else if (empTo.getOutTime()==null || empTo.getOutTime().isEmpty()){
							empTo.setOutTime("Absent");
					}
					empTo.setAttendanceDate(dateString);
					empAttendanceDetails.put(dateString,empTo);
					}else {
						EmpAttendanceTo empTo=new EmpAttendanceTo();
						empTo.setInTime("Absent");
						if(dateString.equalsIgnoreCase(CommonUtil.ConvertStringToDateFormat(CommonUtil.getTodayDate(), "dd/MM/yyyy", "dd-MM-yyyy"))){
						empTo.setOutTime("");
						} else {
							empTo.setOutTime("Absent");
						}
						empTo.setAttendanceDate(dateString);
						empAttendanceDetails.put(dateString,empTo);
					}
				}
			}
		}
		List<EmpAttendanceTo> displayList = new ArrayList<EmpAttendanceTo>(empAttendanceDetails.values());
		Collections.sort(displayList);
		return displayList;
	}
	/**
	 * returns a list of date String on which dates employee has taken leave
	 * @param empLeaveBo
	 * @return
	 * @throws Exception
	 */
	private Map<String,String> getLeaveList(List<EmpApplyLeave> empLeaveBo) throws Exception{
		Map<String,String> leaveList=new HashMap<String, String>();
		if(empLeaveBo!=null && !empLeaveBo.isEmpty()){
		Iterator<EmpApplyLeave> empBoIterator=empLeaveBo.iterator();
		while (empBoIterator.hasNext()) {
			EmpApplyLeave empApplyLeave = (EmpApplyLeave) empBoIterator.next();
			if(empApplyLeave.getFromDate()!=null && empApplyLeave.getToDate()!=null){
				List<String> tempList=CommonUtil.getDatesBetween(empApplyLeave.getFromDate(), empApplyLeave.getToDate());
				if(tempList!=null && !tempList.isEmpty()){
					Iterator<String> itr=tempList.iterator();
					while (itr.hasNext()) {
						String date = (String) itr.next();
						if(empApplyLeave.getIsHalfDay()!=null && empApplyLeave.getIsHalfDay())
						leaveList.put(CommonUtil.ConvertStringToDateFormat(date, "dd-MMM-yyyy", "dd-MM-yyyy"),empApplyLeave.getEmpLeaveType()!=null?empApplyLeave.getEmpLeaveType().getCode()+"_"+empApplyLeave.getIsAm():"");
						else 
						leaveList.put(CommonUtil.ConvertStringToDateFormat(date, "dd-MMM-yyyy", "dd-MM-yyyy"),empApplyLeave.getEmpLeaveType()!=null?empApplyLeave.getEmpLeaveType().getCode()+"_F":"");
					}
				}
			}
		}
	}
	return leaveList;
	}
	/**
	 * returns the list of holidays present
	 * @param holidays
	 * @return
	 * @throws Exception
	 */
	private List<String> getHolidayList(List<Holidays> holidays) throws Exception {
		List<String> holidayList=null;
		if(holidays!=null && !holidays.isEmpty()){
		 holidayList=new ArrayList<String>();
		Iterator<Holidays> holIterator=holidays.iterator();
		while (holIterator.hasNext()) {
			Holidays hol = (Holidays) holIterator.next();
			if(hol.getStartDate()!=null && hol.getEndDate()!=null){
				List<String> holDatesTemp=CommonUtil.getDatesBetween(hol.getStartDate(), hol.getEndDate());
				if(holDatesTemp!=null && !holDatesTemp.isEmpty()){
					Iterator<String> itr=holDatesTemp.iterator();
					while (itr.hasNext()) {
						String date = (String) itr.next();
						holidayList.add(CommonUtil.ConvertStringToDateFormat(date, "dd-MMM-yyyy", "dd-MM-yyyy"));
					}
				}
			}
		}
	}
	return holidayList;
	}
	/**
	 * returns a list of date String on which dates employee has been out on event or vacation or exam
	 * @param empLeaveBo
	 * @return
	 * @throws Exception
	 */
	private Map<String,String> getEmpEventVacationList(List<EmpEventVacation> empEventVacations) throws Exception{
		Map<String,String> eventList=null;
		if(empEventVacations!=null && !empEventVacations.isEmpty()){
			eventList=new HashMap<String, String>();
		Iterator<EmpEventVacation> empEventIterator=empEventVacations.iterator();
		while (empEventIterator.hasNext()) {
			EmpEventVacation empEvent = (EmpEventVacation) empEventIterator.next();
			if(empEvent.getFromDate()!=null && empEvent.getToDate()!=null){
				List<String> tempList=CommonUtil.getDatesBetween(empEvent.getFromDate(), empEvent.getToDate());
				if(tempList!=null && !tempList.isEmpty()){
					Iterator<String> itr=tempList.iterator();
					while (itr.hasNext()) {
						String date = (String) itr.next();
						eventList.put(CommonUtil.ConvertStringToDateFormat(date, "dd-MMM-yyyy", "dd-MM-yyyy"),empEvent.getType()!=null?empEvent.getType():"");
					}
				}
			}
		}
	}
	return eventList;
	}
	
	/**
	 * returns the query to get the emp leave list from database for the employee logged in
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public static String getQueryForLeave(ViewMyAttendanceForm viewAttForm) throws Exception {
		String query=null;
		String queryDate = CommonUtil.getCurrentYear()+"-"+CommonUtil.getCurrentMonth()+"-01";
		if(viewAttForm.getViewEmpAttendance()){
			
			if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().trim().isEmpty()){
		 query=" select e from EmpApplyLeave e " +
				"  where e.employee.fingerPrintId=" +viewAttForm.getFingerPrintId()+
				" and e.isActive=1 and e.isCanceled=0 and (('"+queryDate+"' between e.fromDate and e.toDate) or "+
				"  (month(e.fromDate)='"+CommonUtil.getCurrentMonth()+"' or month(e.toDate)='"+CommonUtil.getCurrentMonth()+"'))"+
				"  and  (year(e.fromDate)="+CommonUtil.getCurrentYear()+" or year(e.toDate)="+CommonUtil.getCurrentYear()+")";
			}
			else if (viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().trim().isEmpty()){
				 query=" select e from EmpApplyLeave e " +
					"  where e.employee.code=" +viewAttForm.getEmpCode()+
					" and e.isActive=1 and e.isCanceled=0 and (('"+queryDate+"' between e.fromDate and e.toDate) or "+
					" (month(e.fromDate)='"+CommonUtil.getCurrentMonth()+"' or month(e.toDate)='"+CommonUtil.getCurrentMonth()+"'))"+
					"  and  (year(e.fromDate)="+CommonUtil.getCurrentYear()+" or year(e.toDate)="+CommonUtil.getCurrentYear()+")";
			}
		}
		else{
			 query=" select e from EmpApplyLeave e join e.employee.userses u" +
				"  where u.id=" +viewAttForm.getUserId()+
				" and e.isActive=1 and e.isCanceled=0 and (('"+queryDate+"' between e.fromDate and e.toDate) or "+
				"  (month(e.fromDate)='"+CommonUtil.getCurrentMonth()+"' or month(e.toDate)="+CommonUtil.getCurrentMonth()+"))"+
				"  and  (year(e.fromDate)="+CommonUtil.getCurrentYear()+" or year(e.toDate)="+CommonUtil.getCurrentYear()+")";
		}
		query= query+" order by e.fromDate";
		return query;
	}
	/**
	 * Converting the EmpApplyLeaveBO to EmpApplyLeaveTO to set in the form
	 * @param empLeaveList
	 * @return
	
	public List<EmpApplyLeaveTO> convertBOtoTOLeave(List<EmpApplyLeave> empLeaveList) {
		List<EmpApplyLeaveTO> empLeaveListTo=null;
		if(empLeaveList!=null && !empLeaveList.isEmpty()){
			empLeaveListTo=new ArrayList<EmpApplyLeaveTO>();
			Iterator<EmpApplyLeave> empIterator=empLeaveList.iterator();
			while (empIterator.hasNext()) {
				EmpApplyLeave empApplyLeave = (EmpApplyLeave) empIterator.next();
				EmpApplyLeaveTO empTo=new EmpApplyLeaveTO();
				empTo.setEmpLeaveType(empApplyLeave.getEmpLeaveType()!=null?empApplyLeave.getEmpLeaveType().getName():"");
				empTo.setFromDate(empApplyLeave.getFromDate()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getFromDate()), "dd-MMM-yyyy","dd/MM/yyyy"):"");
				empTo.setToDate(empApplyLeave.getToDate()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getToDate()), "dd-MMM-yyyy","dd/MM/yyyy"):"");
				empLeaveListTo.add(empTo);
			}
		}
		return empLeaveListTo;
	} */
	/**
	 * Query for previous month attendance of an employee
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousAttendance(ViewMyAttendanceForm viewAttForm) throws Exception {
		String query=null;
		
		if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().isEmpty()){
			query="select e from EmpAttendance e  where e.isActive=1 and e.employee.fingerPrintId=" +viewAttForm.getFingerPrintId();
		}
		else if(viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().isEmpty())
		 query="select e from EmpAttendance e  where e.isActive=1 and e.employee.code=" +viewAttForm.getEmpCode();
		else query="select e from EmpAttendance e join e.employee.userses u where e.isActive=1 and u.id=" +viewAttForm.getUserId();
		//Added by sudhir
		if(viewAttForm.getYear()!=null && !viewAttForm.getYear().isEmpty() && viewAttForm.getMonths()!=null && !viewAttForm.getMonths().isEmpty()){
			String month = monthMap.get(viewAttForm.getMonths());
			query= query+" and month(e.date)=" +month+
		 " and year(e.date)=" +viewAttForm.getYear();
		}
		//
//		if(CommonUtil.getPreviousMonth()!=null && Integer.parseInt(CommonUtil.getPreviousMonth())==12){
//		 query= query+" and month(e.date)=" +CommonUtil.getPreviousMonth()+
//		 " and year(e.date)=" +(Integer.parseInt(CommonUtil.getCurrentYear())-1);
//		}
//		else {
//			 query= query+" and month(e.date)=" +CommonUtil.getPreviousMonth()+
//			 " and year(e.date)=" +CommonUtil.getCurrentYear();
//		}
		
		
		query=query+" order by e.date";
		return query;
	}
	/**
	 * query to fetch previous month leave details
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public static String getQueryForPreviousLeave(ViewMyAttendanceForm viewAttForm) throws Exception{
		String query=null;
		if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().trim().isEmpty()){
		 query=" select e from EmpApplyLeave e  where e.isActive=1 and e.isCanceled=0 and e.employee.fingerPrintId=" +viewAttForm.getFingerPrintId();
			}
		 else if(viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().isEmpty()){
			 query=" select e from EmpApplyLeave e  where e.isActive=1 and e.isCanceled=0 and e.employee.code=" +viewAttForm.getEmpCode();
		 }
		 else query=" select e from EmpApplyLeave e join e.employee.userses u where e.isActive=1  and e.isCanceled=0 and u.id=" +viewAttForm.getUserId();
			
//		if(CommonUtil.getPreviousMonth()!=null && Integer.parseInt(CommonUtil.getPreviousMonth())==12){
//			query = query+" and '"+ CommonUtil.getPreviousMonth()+
//			" ' between month(e.fromDate) and month(e.toDate)" +
//			"  and  year(e.fromDate)="+(Integer.parseInt(CommonUtil.getCurrentYear())-1);
//		}
//		else {
//			query = query+" and '"+ CommonUtil.getPreviousMonth()+
//			" ' between month(e.fromDate) and month(e.toDate)" +
//			"  and  year(e.fromDate)="+CommonUtil.getCurrentYear();
//		}
		
		//Added by Sudhir
		/*if(viewAttForm.getYear()!=null && !viewAttForm.getYear().isEmpty() && viewAttForm.getMonths()!=null && !viewAttForm.getMonths().isEmpty()){
			String month = monthMap.get(viewAttForm.getMonths());
			query = query+" and '"+ month+
			" ' between month(e.fromDate) and month(e.toDate)" +
			"  and  year(e.fromDate)="+viewAttForm.getYear();
		}*/
		
		
		
		//Added by venkat
		if(viewAttForm.getYear()!=null && !viewAttForm.getYear().isEmpty() && viewAttForm.getMonths()!=null && !viewAttForm.getMonths().isEmpty()){
			String month = monthMap.get(viewAttForm.getMonths());
			String selectedDate = viewAttForm.getYear()+"-"+month+"-01";
			//query = query+" and '"+ month+
			query = query+ "and (('"+selectedDate+"' between e.fromDate and e.toDate) or (month(e.fromDate)='"+month+"' or month(e.toDate)='"+month+"'))" +
			"  and  (year(e.fromDate)="+viewAttForm.getYear()+" or year(e.toDate)="+viewAttForm.getYear()+")";
		}
		//
			query= query+" order by e.fromDate";	
		return query;
	}
	//Added by Sudhir
	static {
		monthMap = new HashMap<String, String>();
		monthMap.put("JANUARY","1");
		monthMap.put("FEBRUARY", "2");
		monthMap.put("MARCH", "3");
		monthMap.put("APRIL", "4");
		monthMap.put("MAY", "5");
		monthMap.put("JUNE", "6");
		monthMap.put("JULY", "7");
		monthMap.put("AUGUST","8");
		monthMap.put("SEPTEMBER","9");
		monthMap.put("OCTOBER", "10");
		monthMap.put("NOVEMBER", "11");
		monthMap.put("DECEMBER", "12");
		
	}
	//
}
