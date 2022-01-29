package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ResetLeavesForm;
import com.kp.cms.helpers.employee.ResetLeavesHelper;
import com.kp.cms.transactions.employee.IResetLeavesTransaction;
import com.kp.cms.transactionsimpl.employee.ResetLeavesTransactionImpl;

public class ResetLeavesHandler {
	/**
	 * Singleton object of ResetLeavesHandler
	 */
	private static volatile ResetLeavesHandler resetLeavesHandler = null;
	private static final Log log = LogFactory.getLog(ResetLeavesHandler.class);
	private ResetLeavesHandler() {
		
	}
	/**
	 * return singleton object of ResetLeavesHandler.
	 * @return
	 */
	public static ResetLeavesHandler getInstance() {
		if (resetLeavesHandler == null) {
			resetLeavesHandler = new ResetLeavesHandler();
		}
		return resetLeavesHandler;
	}
	/**
	 * @param resetLeavesForm
	 * @return
	 */
	public boolean updateResetLeavesOfEmployee(ResetLeavesForm resetLeavesForm) throws Exception {
		IResetLeavesTransaction transaction=new ResetLeavesTransactionImpl();
		String empQuery=ResetLeavesHelper.getInstance().getEmpQuery(resetLeavesForm);
		List<Employee> empList=transaction.getEmployeeByEmpTypeId(empQuery);
		String empInitQuery=ResetLeavesHelper.getInstance().getEmpInitalizeQuery(resetLeavesForm);
		List<EmpInitializeLeaves> initList=transaction.getEmpInitializeLeavesByEmpTypeId(empInitQuery);
		List<EmpLeave> empLeaveList=ResetLeavesHelper.getInstance().getEmpLeaveList(empList,initList,resetLeavesForm,transaction);
		return transaction.saveEmpLeaves(empLeaveList);
	}
}
