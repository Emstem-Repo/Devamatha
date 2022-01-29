package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.LeaveInitializationForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.employee.LeaveInitializationHelper;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class LeaveInitializationHandler {
	/**
	 * Singleton object of LeaveInitializationHandler
	 */
	private static volatile LeaveInitializationHandler leaveInitializationHandler = null;
	private static final Log log = LogFactory.getLog(LeaveInitializationHandler.class);
	private LeaveInitializationHandler() {
		
	}
	/**
	 * return singleton object of LeaveInitializationHandler.
	 * @return
	 */
	public static LeaveInitializationHandler getInstance() {
		if (leaveInitializationHandler == null) {
			leaveInitializationHandler = new LeaveInitializationHandler();
		}
		return leaveInitializationHandler;
	}
	/**
	 * @param month
	 * @return
	 */
	public Map<Integer, String> getEmployeeTypesForMonth(String month) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<EmpType> list=transaction.getDataForQuery(LeaveInitializationHelper.getInstance().getEmployeeTypeQuery(month));
		return LeaveInitializationHelper.getInstance().convertBotoMap(list);
	}
	/**
	 * @param leaveInitializationForm
	 * @return
	 */
	public boolean saveLeaveInitialization( LeaveInitializationForm leaveInitializationForm) throws Exception {
		ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
		List<Integer> list=getEmployeeTypeForMonth(leaveInitializationForm.getMonth());
		List<EmpLeave> mainBoList=new ArrayList<EmpLeave>();
		if(list!=null && !list.isEmpty()){
			Iterator<Integer> itr=list.iterator();
			while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
				List<Employee> empList=transaction.getDataForQuery(LeaveInitializationHelper.getInstance().getEmployeeForEmpType(integer.toString()));
				List<EmpLeaveAllotment> allotmentList=transaction.getDataForQuery(LeaveInitializationHelper.getInstance().getEmpLeaveAllotementForEmpType(integer.toString()));
				int accumulateLeaveTypeId=0;
				String accLeaveId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(0,"EmployeeSettings",false,"accumulateLeaveType.id");
				if(!accLeaveId.contains("null"))
					accumulateLeaveTypeId=Integer.parseInt(accLeaveId);
				
				Map<Integer,EmpLeave> oldAlLeave=transaction2.getOldAcumulateLeaves(accumulateLeaveTypeId,leaveInitializationForm);
				Map<String,EmpLeave> map=transaction2.getExistedData(leaveInitializationForm,accumulateLeaveTypeId);
//				Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();
				List<EmpLeave> boList=LeaveInitializationHelper.getInstance().convertTotoBoList(empList,allotmentList,leaveInitializationForm,map,oldAlLeave,accumulateLeaveTypeId);
				if(!boList.isEmpty())
					mainBoList.addAll(boList);
				}
			}
		return transaction2.saveLeaveInitialization(mainBoList);
	}
	/**
	 * @param leaveInitializationForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkAlreadyExists(LeaveInitializationForm leaveInitializationForm) throws Exception {
		List<Integer> list=getEmployeeTypeForMonth(leaveInitializationForm.getMonth());
		String ids="";
		if(list!=null && !list.isEmpty()){
			Iterator<Integer> itr=list.iterator();
			while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				if(ids.isEmpty())
					ids=integer.toString();
				else
					ids=ids+","+integer.toString();
			}
		}
		boolean isAlreadyExist=false;
		String query="from EmpLeave e join e.employee.emptype empType where empType.id in ("+ids+") and e.year="+leaveInitializationForm.getYear();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list1=transaction.getDataForQuery(query);
		if(list1!=null && !list1.isEmpty()){
			isAlreadyExist=true;
		}
		return isAlreadyExist;
	}
	/**
	 * @param month
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getEmployeeTypeForMonth(String month) throws Exception{
		String query="select e.id from EmpType e where e.leaveInitializeMonth='"+month+"'";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getMonthFromEmployeeType() throws Exception {
		Map<String, String> monthMap=new HashMap<String, String>();
		String query="select e.leaveInitializeMonth from EmpType e group by e.leaveInitializeMonth";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<String> list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			Iterator<String> itr=list.iterator();
			while (itr.hasNext()) {
				String month = (String) itr.next();
				monthMap.put(month, month);
			}
		}
		return monthMap;
	}
}