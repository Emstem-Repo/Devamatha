package com.kp.cms.handlers.employee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpWorkDairy;
import com.kp.cms.forms.employee.WorkDiaryForm;
import com.kp.cms.helpers.employee.WorkDiaryHelper;
import com.kp.cms.transactions.employee.IWorkDiaryTransaction;
import com.kp.cms.transactionsimpl.employee.WorkDiaryTransactionImpl;

public class WorkDiaryHandler {
	private static final Log log = LogFactory.getLog(WorkDiaryHandler.class);
	public static volatile WorkDiaryHandler workDiaryHandler = null;

	public static WorkDiaryHandler getInstance() {
		if (workDiaryHandler == null) {
			workDiaryHandler = new WorkDiaryHandler();
			return workDiaryHandler;
		}
		return workDiaryHandler;
	}

	/**
	 * 
	 * @param workDiaryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addWorkDiary(WorkDiaryForm workDiaryForm, String mode) throws Exception {
		log.debug("inside addWorkDiary");
		IWorkDiaryTransaction iTransaction = WorkDiaryTransactionImpl.getInstance();
		boolean isAdded = false;
	
		EmpWorkDairy empDairy = WorkDiaryHelper.getInstance().copyDataFromFormToBO(workDiaryForm); 
		isAdded = iTransaction.addWorkDiary(empDairy, mode);
		log.debug("leaving addWorkDiary");
		return isAdded;
	}
	
	
}
