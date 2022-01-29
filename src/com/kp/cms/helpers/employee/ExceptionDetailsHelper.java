package com.kp.cms.helpers.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.EmpExceptionDetailsBO;
import com.kp.cms.bo.admin.EmpExceptionDetailsDates;
import com.kp.cms.bo.admin.EmpExceptionTypeBO;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ExceptionDetailseForm;
import com.kp.cms.to.employee.ExceptionDetailsTo;
import com.kp.cms.transactions.employee.IExceptionDetailsTransaction;
import com.kp.cms.transactionsimpl.employee.ExceptionDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExceptionDetailsHelper {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ExceptionDetailsHelper.class);
	public static volatile ExceptionDetailsHelper objHelper = null;

	public static ExceptionDetailsHelper getInstance() {
		if (objHelper == null) {
			objHelper = new ExceptionDetailsHelper();
			return objHelper;
		}
		return objHelper;
	}

	public Map<Integer, String> convertBOToTO(
			List<EmpExceptionTypeBO> exceptionTypes) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (exceptionTypes != null) {
			for (EmpExceptionTypeBO objBO : exceptionTypes) {
				String name = "";
				if (objBO.getExceptionType() != null) {
					name = objBO.getExceptionType();
				}
				if (objBO.getExceptionShortName() != null) {
					name = name + "( " + objBO.getExceptionShortName() + " )";
				}
				map.put(objBO.getId(), name);
			}
		}
		return map;
	}

	/**
	 * used to convert To to Bo
	 * @param objForm
	 * @return
	 */
	public EmpExceptionDetailsBO convertFormTOToBO(ExceptionDetailseForm objForm) {
		EmpExceptionDetailsBO bo = new EmpExceptionDetailsBO();
		EmpExceptionDetailsDates empExceptionDetailsDates;
		Set<EmpExceptionDetailsDates> empExceptionDetailsDatesSet = new HashSet<EmpExceptionDetailsDates>();
		
		if (objForm.getEmployeeId() != null
				&& objForm.getEmployeeId().trim().length() > 0) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(objForm.getEmployeeId()));
			bo.setEmployee(employee);
		}
		if (objForm.getExceptionTypeId() != null
				&& objForm.getExceptionTypeId().trim().length() > 0) {
			EmpExceptionTypeBO etBO = new EmpExceptionTypeBO();
			etBO.setId(Integer.parseInt(objForm.getExceptionTypeId()));
			bo.setExceptionTypeBO(etBO);
		}
		if (objForm.getFromDate() != null
				&& objForm.getFromDate().trim().length() > 0) {
			bo.setStaffStartDate(CommonUtil.ConvertStringToSQLDate(objForm
					.getFromDate()));
		}
		if (objForm.getFromAM() != null
				&& objForm.getFromAM().trim().length() > 0) {
			if (objForm.getFromAM().equals("AM")) {
				bo.setStaffStartDateAm(true);
			}
		}
		if (objForm.getFromAM() != null
				&& objForm.getFromAM().trim().length() > 0) {
			if (objForm.getFromAM().equals("PM")) {
				bo.setStaffStartDatePm(true);
			}
		}

		if (objForm.getToDate() != null
				&& objForm.getToDate().trim().length() > 0) {
			bo.setStaffEndDate(CommonUtil.ConvertStringToSQLDate(objForm
					.getToDate()));
		}

		if (objForm.getToAM() != null && objForm.getToAM().trim().length() > 0) {
			if (objForm.getToAM().equals("AM")) {
				bo.setStaffEndDateAm(true);
			}
		}
		if (objForm.getToAM() != null && objForm.getToAM().trim().length() > 0) {
			if (objForm.getToAM().equals("PM")) {
				bo.setStaffEndDatePm(true);
			}
		}
		if (objForm.getRemarks() != null
				&& objForm.getRemarks().trim().length() > 0) {
			bo.setRemarks(objForm.getRemarks());
		}
		
	    String textDate = objForm.getFromDate().substring(3, 5) + "/" + objForm.getFromDate().substring(0, 2)+"/" + objForm.getFromDate().substring(6, 10);
	    String textDate1 = objForm.getToDate().substring(3, 5) + "/" + objForm.getToDate().substring(0, 2)+"/" + objForm.getToDate().substring(6, 10);;

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

	    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) {
	      Date theDate = calendar.getTime();
	      empExceptionDetailsDates = new EmpExceptionDetailsDates();
	      empExceptionDetailsDates.setDate(theDate);
	      empExceptionDetailsDatesSet.add(empExceptionDetailsDates);
	    }
	    bo.setEmpExceptionDetailsDates(empExceptionDetailsDatesSet);
		bo.setCreatedBy(objForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(objForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		return bo;
	}
	
	/**
	 * used to convert BoList to ToList
	 * @param exceptionDetailsBoList
	 * @return
	 */
	public List<ExceptionDetailsTo> convertBoListToToList(
			List<EmpExceptionDetailsBO> exceptionDetailsBoList) {
		List<ExceptionDetailsTo> exceptionDetailsToList = new ArrayList<ExceptionDetailsTo>();
		if (exceptionDetailsBoList != null) {
			Iterator<EmpExceptionDetailsBO> itratorBo = exceptionDetailsBoList
					.iterator();
			while (itratorBo.hasNext()) {
				EmpExceptionDetailsBO empExceptionDetailsBO = (EmpExceptionDetailsBO) itratorBo
						.next();
				ExceptionDetailsTo exceptionDetailsTo = new ExceptionDetailsTo();
				exceptionDetailsTo.setId(empExceptionDetailsBO.getId());
				if (empExceptionDetailsBO.getExceptionTypeBO() != null)
					exceptionDetailsTo.setExceptionType(empExceptionDetailsBO
							.getExceptionTypeBO().getExceptionType());
					exceptionDetailsTo.setEmployeeName(empExceptionDetailsBO.getEmployee().getFirstName());
					exceptionDetailsTo.setStaffStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empExceptionDetailsBO.getStaffStartDate()),ExceptionDetailsHelper.SQL_DATEFORMAT,ExceptionDetailsHelper.FROM_DATEFORMAT));
					exceptionDetailsTo.setStaffEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empExceptionDetailsBO.getStaffEndDate()),ExceptionDetailsHelper.SQL_DATEFORMAT,ExceptionDetailsHelper.FROM_DATEFORMAT));
					exceptionDetailsToList.add(exceptionDetailsTo);
			}
		}
		return exceptionDetailsToList;

	}
	
	public EmpExceptionDetailsBO convertTOToBO(ExceptionDetailseForm exceptionDetailseForm,EmpExceptionDetailsBO exceptionDetailsBO) throws Exception{
		//EmpExceptionDetailsBO bo = new EmpExceptionDetailsBO();
		IExceptionDetailsTransaction transaction = new ExceptionDetailsTransactionImpl();
		
		EmpExceptionTypeBO exceptionTypeBO = new EmpExceptionTypeBO();
		if (exceptionDetailseForm != null) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(exceptionDetailseForm.getEmployeeId()));
			exceptionDetailsBO.setEmployee(employee);
			exceptionTypeBO.setId(Integer.parseInt(exceptionDetailseForm
					.getExceptionTypeId()));
			exceptionDetailsBO
					.setStaffStartDate(CommonUtil
							.ConvertStringToSQLDate(exceptionDetailseForm
									.getFromDate()));
			exceptionDetailsBO.setStaffEndDate(CommonUtil
					.ConvertStringToSQLDate(exceptionDetailseForm.getToDate()));
			exceptionDetailsBO.setLastModifiedDate(new java.util.Date());
			exceptionDetailsBO.setModifiedBy(exceptionDetailseForm.getUserId());
			exceptionDetailsBO.setExceptionTypeBO(exceptionTypeBO);
			if(exceptionDetailseForm.getFromAM().equals("AM"))
			{
				exceptionDetailsBO.setStaffStartDateAm(true);
			}
			if(exceptionDetailseForm.getFromAM().equals("PM"))
			{
				exceptionDetailsBO.setStaffStartDatePm(true);
			}
			if(exceptionDetailseForm.getToAM().equals("AM"))
			{
				exceptionDetailsBO.setStaffEndDateAm(true);
			}
			if(exceptionDetailseForm.getToAM().equals("PM"))
			{
				exceptionDetailsBO.setStaffEndDatePm(true);
			}
			exceptionDetailsBO.setRemarks(exceptionDetailseForm.getRemarks());
			
		    String textDate = exceptionDetailseForm.getFromDate().substring(3, 5) + "/" + exceptionDetailseForm.getFromDate().substring(0, 2)+"/" + exceptionDetailseForm.getFromDate().substring(6, 10);
		    String textDate1 = exceptionDetailseForm.getToDate().substring(3, 5) + "/" + exceptionDetailseForm.getToDate().substring(0, 2)+"/" + exceptionDetailseForm.getToDate().substring(6, 10);;

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
		    Set<EmpExceptionDetailsDates> empExceptionDetailsDatesSet = new HashSet<EmpExceptionDetailsDates>();
		    EmpExceptionDetailsDates empExceptionDetailsDates;
				List<Date> dateListFromUi=new ArrayList<Date>();
				List<EmpExceptionDetailsDates>dateTobeDeleted=new ArrayList<EmpExceptionDetailsDates>();
			    for (; calendar.before(calendar1) || calendar.equals(calendar1); calendar.add(Calendar.DATE, 1)) 
			    {
			      Date theDate = calendar.getTime();
			      dateListFromUi.add(theDate);
			    }
			    Set<EmpExceptionDetailsDates> dateListFromDb=transaction.getExceptionDetailsOnId(exceptionDetailseForm.getId()).getEmpExceptionDetailsDates();
			    for(EmpExceptionDetailsDates exceptionDates:dateListFromDb)
			    {
			    	if(dateListFromUi.contains(exceptionDates.getDate()))
			    		empExceptionDetailsDatesSet.add(exceptionDates);
			    	else
			    	{
			    		dateTobeDeleted.add(exceptionDates);
			    	}
			    }
			    for(Date date:dateListFromUi)
			    {
			    	boolean dateNotFound=false;
			    	Date newDate=null;
			    	Iterator<EmpExceptionDetailsDates> iterator=dateListFromDb.iterator();
			    	while(iterator.hasNext())
			    	{
			    		EmpExceptionDetailsDates exceptionDates=iterator.next();
			    		if(!CommonUtil.getStringDate(exceptionDates.getDate()).equals(CommonUtil.getStringDate(date)))
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
			    		empExceptionDetailsDates = new EmpExceptionDetailsDates();
			    		empExceptionDetailsDates.setDate(newDate);
			    		empExceptionDetailsDatesSet.add(empExceptionDetailsDates);
			    	}
			    }
			    exceptionDetailseForm.setDatesToBeDeleted(dateTobeDeleted);
			    exceptionDetailsBO.setEmpExceptionDetailsDates(empExceptionDetailsDatesSet);
				
			}
		return exceptionDetailsBO;	
	}

	public void convertBOToForm(ExceptionDetailseForm exceptionDetailseForm,
			EmpExceptionDetailsBO exceptionDetailsBO) throws Exception{
		if (exceptionDetailsBO != null) {
			exceptionDetailseForm.setDupId(exceptionDetailsBO.getId());
			exceptionDetailseForm.setEmployeeId(Integer.toString(exceptionDetailsBO.getEmployee().getId()));
			exceptionDetailseForm.setExceptionTypeId(Integer
					.toString(exceptionDetailsBO.getExceptionTypeBO().getId()));
			exceptionDetailseForm.setFromDate(CommonUtil
					.ConvertStringToDateFormat(CommonUtil
							.getStringDate(exceptionDetailsBO
									.getStaffStartDate()),
									ExceptionDetailsHelper.SQL_DATEFORMAT,
									ExceptionDetailsHelper.FROM_DATEFORMAT));
			exceptionDetailseForm.setToDate(CommonUtil
					.ConvertStringToDateFormat(CommonUtil
							.getStringDate(exceptionDetailsBO
									.getStaffEndDate()),
									ExceptionDetailsHelper.SQL_DATEFORMAT,
									ExceptionDetailsHelper.FROM_DATEFORMAT));
			exceptionDetailseForm.setRemarks(exceptionDetailsBO.getRemarks());
			if(exceptionDetailsBO.getStaffStartDateAm()){
				exceptionDetailseForm.setFromAM("AM");
			}
			if(exceptionDetailsBO.getStaffStartDatePm()){
				exceptionDetailseForm.setFromAM("PM");
			}
			if(exceptionDetailsBO.getStaffEndDateAm()){
				exceptionDetailseForm.setToAM("AM");
			}
			if(exceptionDetailsBO.getStaffEndDatePm()){
				exceptionDetailseForm.setToAM("PM");
			}
			exceptionDetailseForm.setRemarks(exceptionDetailsBO.getRemarks());
			
			Set<EmpExceptionDetailsDates> empExcSet=exceptionDetailsBO.getEmpExceptionDetailsDates();
			String datesArray[] = new String[empExcSet.size()];
			int datesId[]=new int[empExcSet.size()];
			
			Set<Integer> datesIdSet=new HashSet<Integer>();
			int i=0;
			if(empExcSet!=null){
				EmpExceptionDetailsDates empExceptionDetailsDates;				
				Iterator<EmpExceptionDetailsDates> iterator=empExcSet.iterator();
				while (iterator.hasNext()) {				
					empExceptionDetailsDates = iterator.next();
						datesArray[i]=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empExceptionDetailsDates.getDate()), "dd-MMM-yyyy","dd/MM/yyyy")!=null ? CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empExceptionDetailsDates.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"):null;
						datesId[i]=empExceptionDetailsDates.getId()!= 0 ? empExceptionDetailsDates.getId():0;
						datesIdSet.add(empExceptionDetailsDates.getId()!= 0 ? empExceptionDetailsDates.getId():0);
							i++;
				}
				exceptionDetailseForm.setDatesArray(datesArray);
				exceptionDetailseForm.setDatesId(datesId);
				exceptionDetailseForm.setDatesIdSet(datesIdSet);
			}
		}
		
	}

	public String getQueryforCheckDuplicate(ExceptionDetailseForm exceptionDetailseForm) {
		
		String textDate = exceptionDetailseForm.getFromDate().substring(3, 5) + "/" + exceptionDetailseForm.getFromDate().substring(0, 2)+"/" + exceptionDetailseForm.getFromDate().substring(6, 10);
	    String textDate1 = exceptionDetailseForm.getToDate().substring(3, 5) + "/" + exceptionDetailseForm.getToDate().substring(0, 2)+"/" + exceptionDetailseForm.getToDate().substring(6, 10);;

	    Date frm = null;
	    Date tl = null;

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    try {
	      frm = df.parse(textDate);
	      tl = df.parse(textDate1);
	    } catch (ParseException parseException) {
	      parseException.printStackTrace();
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
	    String query="select e.empExceptionDetailsBO from EmpExceptionDetailsDates e where ("+dateQuery+" ) "; 
		
		query=query+ "and e.empExceptionDetailsBO.exceptionTypeBO.id="+exceptionDetailseForm.getExceptionTypeId()+" and e.empExceptionDetailsBO.employee.id="+exceptionDetailseForm.getEmployeeId();
	
	return query;
		
	}
}
