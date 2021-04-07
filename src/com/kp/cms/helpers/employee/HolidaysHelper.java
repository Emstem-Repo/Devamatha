package com.kp.cms.helpers.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmpAcademicHolidays;
import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.forms.employee.HolidaysForm;
import com.kp.cms.to.employee.HolidaysDatesTO;
import com.kp.cms.to.employee.HolidaysTO;
import com.kp.cms.transactions.employee.IHolidaysTransaction;
import com.kp.cms.transactionsimpl.employee.HolidaysTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HolidaysHelper {
	
	private static volatile HolidaysHelper holidaysHelper = null; 
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";

	public static HolidaysHelper getInstance() {
		if (holidaysHelper == null) {
			holidaysHelper = new HolidaysHelper();
		}
		return holidaysHelper;
	}

	public List<HolidaysTO> convertBoListToTOList(List<EmployeeTypeBO> list) {
		
		List<HolidaysTO> empTypelist = new ArrayList<HolidaysTO>();
		
		if(!list.isEmpty()){
			Iterator<EmployeeTypeBO> iterator = list.iterator();
				while (iterator.hasNext()) {
					EmployeeTypeBO employeeTypeBO = (EmployeeTypeBO) iterator.next();
					HolidaysTO hTo = new HolidaysTO();
					hTo.setEmpTypeId(employeeTypeBO.getId());
					hTo.setEmpTypeName(employeeTypeBO.getName());
					empTypelist.add(hTo);
				}
		}
		return empTypelist;
	}

	public EmpAcademicHolidays getBoFromForm(HolidaysForm holidaysForm,String mode,int yearId)throws Exception 
	{
		
		IHolidaysTransaction transaction=new HolidaysTransactionImpl();
		EmpAcademicHolidays emp = new EmpAcademicHolidays();
		if(holidaysForm.getId() > 0)
		{
			emp.setId(holidaysForm.getId());
		}
		if(holidaysForm.getEmpTypeId()!=null){
		int empTypeId = Integer.parseInt(holidaysForm.getEmpTypeId());
		EmployeeTypeBO eTypeBO =new EmployeeTypeBO();
		eTypeBO.setId(empTypeId);
		emp.setEmployeeTypeBO(eTypeBO);
		}
		EmpAcademicHolidaysDates empHolidaysDates = null;
		Set<EmpAcademicHolidaysDates> empAcademicHolidaysDatesSet = new HashSet<EmpAcademicHolidaysDates>();
		HolidaysDatesTO holidaysDatesTO;
		emp.setStartDate(CommonUtil.ConvertStringToDate(holidaysForm.getStartDate()));
		emp.setEndDate(CommonUtil.ConvertStringToDate(holidaysForm.getEndDate()));

	    String textDate = holidaysForm.getStartDate().substring(3, 5) + "/" + holidaysForm.getStartDate().substring(0, 2)+"/" + holidaysForm.getStartDate().substring(6, 10);
	    String textDate1 = holidaysForm.getEndDate().substring(3, 5) + "/" + holidaysForm.getEndDate().substring(0, 2)+"/" + holidaysForm.getEndDate().substring(6, 10);

	    Date frm = null;
	    Date tl = null;

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    try {
	      frm = df.parse(textDate);
	      tl = df.parse(textDate1);
	    } catch (ParseException parseException) {
	      parseException.printStackTrace();
	      System.exit(1);
	    }
	   
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(frm);
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(tl);

	    
	    AcademicYear year = new AcademicYear();
	    year.setId(yearId);
		emp.setAcademicYearBO(year);
		emp.setAcademicYear(Integer.parseInt(holidaysForm.getAcademicYear()));
		emp.setIsActive(true);
		emp.setHoliday(holidaysForm.getHoliday());
		if(mode.equalsIgnoreCase("Add"))
		{
		    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) {
		      Date theDate = calendar.getTime();
		      empHolidaysDates = new EmpAcademicHolidaysDates();
		      empHolidaysDates.setDate(theDate);
		      empAcademicHolidaysDatesSet.add(empHolidaysDates);
		    }
		    emp.setEmpAcademicHolidaysDates(empAcademicHolidaysDatesSet);
			
			emp.setModifiedBy(holidaysForm.getUserId());
			emp.setCreatedBy(holidaysForm.getUserId());
			emp.setCreatedDate(new Date());
			emp.setLastModifiedDate(new Date());
		}
		else
		{
			List<Date> dateListFromUi=new ArrayList<Date>();
			List<EmpAcademicHolidaysDates>dateTobeDeleted=new ArrayList<EmpAcademicHolidaysDates>();
		    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) 
		    {
		      Date theDate = calendar.getTime();
		      dateListFromUi.add(theDate);
		    }
		    Set<EmpAcademicHolidaysDates> dateListFromDb=transaction.getHolidaysDetailsToEdit(holidaysForm.getId()).getEmpAcademicHolidaysDates();
		    for(EmpAcademicHolidaysDates holidaysDates:dateListFromDb)
		    {
		    	if(dateListFromUi.contains(holidaysDates.getDate()))
		    		empAcademicHolidaysDatesSet.add(holidaysDates);
		    	else
		    	{
		    		dateTobeDeleted.add(holidaysDates);
		    	}
		    }
		    for(Date date:dateListFromUi)
		    {
		    	boolean dateNotFound=false;
		    	Date newDate=null;
		    	Iterator<EmpAcademicHolidaysDates> iterator=dateListFromDb.iterator();
		    	while(iterator.hasNext())
		    	{
		    		EmpAcademicHolidaysDates holidaysDates=iterator.next();
		    		if(!CommonUtil.getStringDate(holidaysDates.getDate()).equals(CommonUtil.getStringDate(date)))
		    		{
		    			dateNotFound=true;
		    			newDate=date;
		    		}
		    		else
		    		{
		    			dateNotFound=false;
		    			break;
		    		}
		    	}
		    	if(dateNotFound)
		    	{
		    		empHolidaysDates = new EmpAcademicHolidaysDates();
				    empHolidaysDates.setDate(newDate);
				    empAcademicHolidaysDatesSet.add(empHolidaysDates);
		    	}
		    }
		    holidaysForm.setDatesToBeDeleted(dateTobeDeleted);
		    emp.setEmpAcademicHolidaysDates(empAcademicHolidaysDatesSet);
		    emp.setModifiedBy(holidaysForm.getUserId());
			emp.setCreatedBy(holidaysForm.getUserId());
			emp.setCreatedDate(new Date());
			emp.setLastModifiedDate(new Date());
		}
		return emp;
	}

	public List<HolidaysTO> setBOListToTOList(List<EmpAcademicHolidays> empHolidaysList) {
		
		List<HolidaysTO> holidaysTOsList = new ArrayList<HolidaysTO>();
		Iterator<EmpAcademicHolidays> iterator = empHolidaysList.iterator();
		HolidaysTO holidaysTO;
		while (iterator.hasNext()) {
			EmpAcademicHolidays empAcademicHolidays = (EmpAcademicHolidays) iterator.next();
			holidaysTO = new HolidaysTO();
			holidaysTO.setId(empAcademicHolidays.getId());
			holidaysTO.setEmpTypeId(empAcademicHolidays.getEmployeeTypeBO().getId());
			holidaysTO.setEmpTypeName(empAcademicHolidays.getEmployeeTypeBO().getName());
			holidaysTO.setAcademicYear(empAcademicHolidays.getAcademicYear());
			holidaysTO.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAcademicHolidays.getStartDate()),HolidaysHelper.SQL_DATEFORMAT,HolidaysHelper.FROM_DATEFORMAT));
			holidaysTO.setHoliday(empAcademicHolidays.getHoliday());
			holidaysTO.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAcademicHolidays.getEndDate()),HolidaysHelper.SQL_DATEFORMAT,HolidaysHelper.FROM_DATEFORMAT));
			holidaysTOsList.add(holidaysTO);
		}
		return holidaysTOsList;
	}

	public HolidaysTO setBOListToTOListForEdit(EmpAcademicHolidays empHolidays) {
		EmpAcademicHolidaysDates empAcademicHolidaysDates;
		List<HolidaysDatesTO> datesTOList=new ArrayList<HolidaysDatesTO>();
		HolidaysDatesTO holidaysDatesTO = null; 
		HolidaysTO holidaysTO = new HolidaysTO();
		holidaysTO.setId(empHolidays.getId());
		holidaysTO.setEmpTypeId(empHolidays.getEmployeeTypeBO().getId());
		holidaysTO.setEmpTypeName(empHolidays.getEmployeeTypeBO().getName());
		holidaysTO.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empHolidays.getStartDate()),HolidaysHelper.SQL_DATEFORMAT,HolidaysHelper.FROM_DATEFORMAT));
		holidaysTO.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empHolidays.getEndDate()),HolidaysHelper.SQL_DATEFORMAT,HolidaysHelper.FROM_DATEFORMAT));
		holidaysTO.setHoliday(empHolidays.getHoliday());
		holidaysTO.setAcademicYear(empHolidays.getAcademicYear());
		holidaysTO.setCreatedBy(empHolidays.getCreatedBy());
		holidaysTO.setCreatedDate(empHolidays.getCreatedDate());
		Set<EmpAcademicHolidaysDates> empAcademicHolidaysDatesSet = empHolidays.getEmpAcademicHolidaysDates();
		String datesArray[] = new String[empAcademicHolidaysDatesSet.size()];
		int datesId[]=new int[empAcademicHolidaysDatesSet.size()];
		
		Set<Integer> datesIdSet=new HashSet<Integer>();
		int i=0;
		if(empAcademicHolidaysDatesSet!=null){
			//EmpAcademicHolidaysDates empAcademicHolidaysDates;				
			Iterator<EmpAcademicHolidaysDates> iterator=empAcademicHolidaysDatesSet.iterator();
			while (iterator.hasNext()) {				
				empAcademicHolidaysDates = iterator.next();
				datesArray[i]=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAcademicHolidaysDates.getDate()), "dd-MMM-yyyy","dd/MM/yyyy")!=null ? CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAcademicHolidaysDates.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"):null;
					datesId[i]=empAcademicHolidaysDates.getId()!= 0 ? empAcademicHolidaysDates.getId():0;
					datesIdSet.add(empAcademicHolidaysDates.getId()!= 0 ? empAcademicHolidaysDates.getId():0);
						i++;
			}
			holidaysTO.setDatesArray(datesArray);
			holidaysTO.setDatesId(datesId);
			holidaysTO.setDatesIdSet(datesIdSet);
		}
		//Below code are used to keep all the previous records in TO object
		if(empAcademicHolidaysDatesSet!=null){
			Iterator<EmpAcademicHolidaysDates> iterator=empAcademicHolidaysDatesSet.iterator();
			while (iterator.hasNext()) {
				empAcademicHolidaysDates = iterator.next();
				holidaysDatesTO=new HolidaysDatesTO();
				holidaysDatesTO.setDatesId(empAcademicHolidaysDates.getId());
				holidaysDatesTO.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAcademicHolidaysDates.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
				holidaysDatesTO.setHolidaysId(empAcademicHolidaysDates.getEmpAcademicHolidays().getId());
				datesTOList.add(holidaysDatesTO);
			}
			holidaysTO.setHolidaysDatesTOList(datesTOList);
		}
		return holidaysTO;
	}
	
public String getQueryforCheckDuplicate(HolidaysForm holidaysForm) throws Exception{
		
		
	String textDate = holidaysForm.getStartDate().substring(3, 5) + "/" + holidaysForm.getStartDate().substring(0, 2)+"/" + holidaysForm.getStartDate().substring(6, 10);
    String textDate1 = holidaysForm.getEndDate().substring(3, 5) + "/" + holidaysForm.getEndDate().substring(0, 2)+"/" + holidaysForm.getEndDate().substring(6, 10);

	    Date frm = null;
	    Date tl = null;

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    try {
	      frm = df.parse(textDate);
	      tl = df.parse(textDate1);
	    } catch (ParseException parseException) {
	      parseException.printStackTrace();
	      System.exit(1);
	    }
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(frm);
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(tl);

	    String dateQuery="";
	    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) {
	      Date theDate = calendar.getTime();
	      if(dateQuery.isEmpty())
	    	  dateQuery="e.date='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(theDate), "dd-MMM-yyyy", "dd/MM/yyyy") )+"'";
	      else
	    	  dateQuery=dateQuery+" or e.date='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(theDate), "dd-MMM-yyyy", "dd/MM/yyyy") )+"'";
	    }
		String query="select e.empAcademicHolidays from EmpAcademicHolidaysDates e where ("+dateQuery+" ) "; 
		
			query=query+ "and e.empAcademicHolidays.employeeTypeBO.id="+holidaysForm.getEmpTypeId()+" and e.empAcademicHolidays.academicYear = "+Integer.parseInt(holidaysForm.getAcademicYear());
		
		return query;
	}
}
