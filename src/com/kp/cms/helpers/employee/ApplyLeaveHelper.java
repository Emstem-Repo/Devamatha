package com.kp.cms.helpers.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpApplyLeaveDates;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ApplyLeaveForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactions.employee.IApplyLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.ApplyLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ApplyLeaveHelper {
	/**
	 * Singleton object of ApplyLeaveHelper
	 */
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static volatile ApplyLeaveHelper applyLeaveHelper = null;
	private static final Log log = LogFactory.getLog(ApplyLeaveHelper.class);
	private ApplyLeaveHelper() {
		
	}
	/**
	 * return singleton object of ApplyLeaveHelper.
	 * @return
	 */
	public static ApplyLeaveHelper getInstance() {
		if (applyLeaveHelper == null) {
			applyLeaveHelper = new ApplyLeaveHelper();
		}
		return applyLeaveHelper;
	}
	/**
	 * converting the list of BO to TO
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTypeTO> convertBoListToTOList(List<EmpLeaveType> list) throws Exception{
		List<EmpLeaveTypeTO> leaveList=new ArrayList<EmpLeaveTypeTO>();
		if(!list.isEmpty()){
			Iterator<EmpLeaveType> itr=list.iterator();
			while (itr.hasNext()) {
				EmpLeaveType empLeaveType = (EmpLeaveType) itr.next();
				EmpLeaveTypeTO eto=new EmpLeaveTypeTO();
				eto.setId(empLeaveType.getId());
				eto.setName(empLeaveType.getName());
				leaveList.add(eto);
			}
		}
		return leaveList;
	}
	public EmpApplyLeave getBoFromForm(ApplyLeaveForm applyLeaveForm) throws Exception {
		IApplyLeaveTransaction transaction=new ApplyLeaveTransactionImpl();
		int employeeId=transaction.getemployeeId(applyLeaveForm.getUserId());
		EmpApplyLeave emp=new EmpApplyLeave();
		int leaveId=Integer.parseInt(applyLeaveForm.getLeaveId());
		EmpLeaveType elt=new EmpLeaveType();
		elt.setId(leaveId);
		emp.setEmpLeaveType(elt);
		Employee employee=new Employee();
		Set<EmpApplyLeaveDates> empApplyLeaveDatesSet = new HashSet<EmpApplyLeaveDates>();
		if(applyLeaveForm.getIsOndutyLeave()){
			emp.setIsOnDuty(true);
			employee.setId(Integer.parseInt(applyLeaveForm.getEmployeeId()));
		}
		else{
			emp.setIsOnDuty(false);
			employee.setId(employeeId);
		}
		emp.setEmployee(employee);
		emp.setFromDate(CommonUtil.ConvertStringToDate(applyLeaveForm.getFromDate()));
		emp.setToDate(CommonUtil.ConvertStringToDate(applyLeaveForm.getToDate()));
		emp.setStatus("Applied");

	    String textDate = applyLeaveForm.getFromDate().substring(3, 5) + "/" + applyLeaveForm.getFromDate().substring(0, 2)+"/" + applyLeaveForm.getFromDate().substring(6, 10);
	    String textDate1 = applyLeaveForm.getToDate().substring(3, 5) + "/" + applyLeaveForm.getToDate().substring(0, 2)+"/" + applyLeaveForm.getToDate().substring(6, 10);;

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
	      EmpApplyLeaveDates empApplyLeaveDates = new EmpApplyLeaveDates();
	      empApplyLeaveDates.setDate(theDate);
	      empApplyLeaveDatesSet.add(empApplyLeaveDates);
	    }
	    emp.setEmpApplyLeaveDates(empApplyLeaveDatesSet);
		
		
		emp.setModifiedBy(applyLeaveForm.getUserId());
		emp.setCreatedBy(applyLeaveForm.getUserId());
		emp.setCreatedDate(new Date());
		emp.setLastModifiedDate(new Date());
		emp.setAppliedOn(new Date());
		emp.setIsActive(true);
		emp.setFromTime(applyLeaveForm.getFromAM());
		emp.setToTime(applyLeaveForm.getToAM());
		emp.setReason(applyLeaveForm.getReason());
		//emp.setNoOfDays(applyLeaveForm.getNoOfDays());
		return emp;
	}
	/**
	 * @param applyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryforCheckDuplicate(ApplyLeaveForm applyLeaveForm) throws Exception{
		
		
		String textDate = applyLeaveForm.getFromDate().substring(3, 5) + "/" + applyLeaveForm.getFromDate().substring(0, 2)+"/" + applyLeaveForm.getFromDate().substring(6, 10);
	    String textDate1 = applyLeaveForm.getToDate().substring(3, 5) + "/" + applyLeaveForm.getToDate().substring(0, 2)+"/" + applyLeaveForm.getToDate().substring(6, 10);;

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
		String query="select e.EmpApplyLeave from EmpApplyLeaveDates e where ("+dateQuery+" ) and (e.EmpApplyLeave.status='Applied' or e.EmpApplyLeave.status='Approved' or e.EmpApplyLeave.status='On Hold')"; 
		
		if(applyLeaveForm.getIsOndutyLeave()){
			query=query+ "and e.EmpApplyLeave.employee.id="+applyLeaveForm.getEmployeeId();
		}else{
			query=query+ "and e.EmpApplyLeave.employee.id="+applyLeaveForm.getUserId();
		}
		return query;
	}
	
	public List<EmployeeLeaveTO> setBoListToToList(List<EmpApplyLeave> applyLeaveBoList)throws Exception
	{
		List<EmployeeLeaveTO> applyLeaveToList =  new ArrayList<EmployeeLeaveTO>();
		if(applyLeaveBoList != null)
		{
			Iterator<EmpApplyLeave> iterator= applyLeaveBoList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeave empApplyLeave = (EmpApplyLeave) iterator.next();
				EmployeeLeaveTO applyLeaveTo = new EmployeeLeaveTO();
				applyLeaveTo.setLeaveType(empApplyLeave.getEmpLeaveType().getName());
				applyLeaveTo.setId(empApplyLeave.getId());
				applyLeaveTo.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getFromDate()),ApplyLeaveHelper.SQL_DATEFORMAT,ApplyLeaveHelper.FROM_DATEFORMAT));
				applyLeaveTo.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getToDate()),ApplyLeaveHelper.SQL_DATEFORMAT,ApplyLeaveHelper.FROM_DATEFORMAT));
				applyLeaveTo.setStatus(empApplyLeave.getStatus());
				//applyLeaveTo.setNoOfDays(empApplyLeave.getNoOfDays());
				applyLeaveToList.add(applyLeaveTo);
			}
			
		}
		
		return applyLeaveToList;
		
	}
	
	public List<EmpLeaveTO> setAllotedBoListToTOList(List<EmpLeave> list) throws Exception{
		List<EmpLeaveTO> leaveList=new ArrayList<EmpLeaveTO>();
		if(!list.isEmpty()){
			Iterator<EmpLeave> itr=list.iterator();
			while (itr.hasNext()) {
				EmpLeave empLeave = (EmpLeave) itr.next();
				EmpLeaveTO empLeaveTO = new EmpLeaveTO();
				empLeaveTO.setId(empLeave.getEmpLeaveType().getId());
				empLeaveTO.setName(empLeave.getEmpLeaveType().getName());
				//EmpLeaveTypeTO eto=new EmpLeaveTypeTO();
				//eto.setId(empLeave.getEmpLeaveType().getId());
				//eto.setName(empLeave.getEmpLeaveType().getName());
				leaveList.add(empLeaveTO);
			}
		}
		return leaveList;
	}
	public void convertBoToTO(EmpApplyLeave leave, ApplyLeaveForm applyLeaveForm) 
	{
		if(leave!=null)
		{
			applyLeaveForm.setId(leave.getId());
			applyLeaveForm.setLeaveId(""+leave.getEmpLeaveType().getId());
			applyLeaveForm.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leave.getFromDate()),"dd-MMM-yyyy", "dd/MM/yyyy"));
			applyLeaveForm.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leave.getToDate()),"dd-MMM-yyyy", "dd/MM/yyyy"));
		}
	}
}
