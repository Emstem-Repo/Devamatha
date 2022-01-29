package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.WorkTimeEntryForm;
import com.kp.cms.helpers.employee.WorkTimeEntryHelper;
import com.kp.cms.to.attendance.WorkTimeTO;
import com.kp.cms.to.employee.EmpWorkTimeTO;
import com.kp.cms.transactions.employee.IWorkTimeEntryTransaction;
import com.kp.cms.transactionsimpl.employee.WorkTimeEntryTransactionImpl;

public class WorkTimeEntryHandler {
	private static final Log log = LogFactory.getLog(WorkTimeEntryHandler.class);
	public static volatile WorkTimeEntryHandler workTimeEntryHandler = null;
	
	public static WorkTimeEntryHandler getInstance() {
		if (workTimeEntryHandler == null) {
			workTimeEntryHandler = new WorkTimeEntryHandler();
			return workTimeEntryHandler;
		}
		return workTimeEntryHandler;
	}
	

	/**
	 * 
	 * @param workEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addWorkTimeEntry(WorkTimeEntryForm workEntryForm, String mode) throws Exception {
		log.debug("inside addWorkTimeEntry");
		IWorkTimeEntryTransaction iTransaction = WorkTimeEntryTransactionImpl.getInstance();
		boolean isAdded = false;
		iTransaction.isWorkTimeEntryDuplcated(workEntryForm);  
		 List<EmpWorkTime> worktimeList = WorkTimeEntryHelper.getInstance().copyDataFromFormToBO(workEntryForm);
		isAdded = iTransaction.addWorkTimeEntry(worktimeList, mode); 
		log.debug("leaving addWorkTimeEntry");
		return isAdded;
	}
	/**
	 * @return list of EmpWorkTimeTO objects, will be used in UI to display.
	 * @throws Exception
	 */
	public List<EmpWorkTimeTO> getEmpWorkTypes() throws Exception {
		log.debug("inside getEmpWorkTypes");
		IWorkTimeEntryTransaction ieTransaction = WorkTimeEntryTransactionImpl.getInstance();
		List<EmpWorkTime> workTimeList = ieTransaction.getWorkTime();
		List<EmpWorkTimeTO> empWorkTimeToList = WorkTimeEntryHelper.getInstance().copyWorkTimeBosToTos(workTimeList);; 
		log.debug("leaving getEmpWorkTypes");
		return empWorkTimeToList;
	}	
	
	public boolean deleteWorkTimeEntry(int employeeTypeId) throws Exception{
		log.debug("inside deleteWorkTimeEntry");
		IWorkTimeEntryTransaction ieTransaction = WorkTimeEntryTransactionImpl.getInstance();
		return ieTransaction.deleteWorkTimeEntry(employeeTypeId);
		
	}
	
	public void editWorkTimeEntry(WorkTimeEntryForm workTimeEntryForm) throws Exception{
		log.debug("inside editWorkTimeEntry");
		IWorkTimeEntryTransaction iTransaction = WorkTimeEntryTransactionImpl.getInstance();
		 List<EmpWorkTime> workTimeBoList = iTransaction.getWorkTimeEntryToEdit(workTimeEntryForm); 
		 WorkTimeEntryHelper.getInstance().convertWorkTimeBosToTos(workTimeBoList, workTimeEntryForm);
	}
	
	public boolean updateWorkTimeEntry(WorkTimeEntryForm workTimeEntryForm,List<WorkTimeTO> workTimeList) throws Exception
	{
		log.debug("inside updateWorkTimeEntry");
		IWorkTimeEntryTransaction iTransaction = WorkTimeEntryTransactionImpl.getInstance(); 
		 List<EmpWorkTime> worktimeListBo = WorkTimeEntryHelper.getInstance().copyDataFromFormToBO(workTimeEntryForm);
		log.debug("leaving addWorkTimeEntry");
		return iTransaction.updateWorkTimeEntry(worktimeListBo);
	}
	
	public List<EmpWorkTimeTO> getEmployeeTypeList() throws Exception{
		
		List<EmployeeTypeBO> employeeTypeBoList =WorkTimeEntryTransactionImpl.getInstance().getEmployeeTypeList();
		List<EmpWorkTimeTO> empWorkTimeToList = WorkTimeEntryHelper.getInstance().copyEmployeeTypeBosToTos(employeeTypeBoList);; 
		log.debug("leaving getEmployeeTypeList");
		return empWorkTimeToList;
		
	}
	
	public boolean reActivateWorkTimeEntry(int employeeTypeId) throws Exception{
		log.debug("inside reActivateWorkTimeEntry");
		IWorkTimeEntryTransaction ieTransaction = WorkTimeEntryTransactionImpl.getInstance();
		return ieTransaction.reActivateWorkTimeEntry(employeeTypeId);
		
	}
}
