package com.kp.cms.handlers.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmployeeInitializeLeavesForm;
import com.kp.cms.helpers.employee.EmployeeInitializeLeavesHelper;
import com.kp.cms.to.employee.EmpInitializeTo;
import com.kp.cms.transactions.employee.IEmployeeInitializeLeavesTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInitializeLeavesTransactionImpl;

public class EmployeeInitializeLeavesHandler {
	/**
	 * Singleton object of EmployeeInitializeLeavesHandler
	 */
	private static volatile EmployeeInitializeLeavesHandler employeeInitializeLeavesHandler = null;
	private static final Log log = LogFactory.getLog(EmployeeInitializeLeavesHandler.class);
	private EmployeeInitializeLeavesHandler() {
		
	}
	/**
	 * return singleton object of EmployeeInitializeLeavesHandler.
	 * @return
	 */
	public static EmployeeInitializeLeavesHandler getInstance() {
		if (employeeInitializeLeavesHandler == null) {
			employeeInitializeLeavesHandler = new EmployeeInitializeLeavesHandler();
		}
		return employeeInitializeLeavesHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<EmpInitializeTo> getInitializeData() throws Exception {
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		List<EmpInitializeLeaves> list=transaction.getInitializeData();
		return EmployeeInitializeLeavesHelper.getInstance().convertBoListToToList(list);
	}
	/**
	 * @param employeeInitializeLeavesForm
	 * @param request
	 * @return
	 */
	public boolean addEmployeeInitialize(
			EmployeeInitializeLeavesForm employeeInitializeLeavesForm,
			HttpServletRequest request) throws Exception {
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		EmpInitializeLeaves empInitializeLeaves=EmployeeInitializeLeavesHelper.getInstance().convertFormToBo(employeeInitializeLeavesForm);
		return transaction.addEmployeeInitialize(empInitializeLeaves);
	}
	/**
	 * @param employeeInitializeLeavesForm
	 * @throws Exception
	 */
	public void checkForDupliate(EmployeeInitializeLeavesForm employeeInitializeLeavesForm) throws Exception {
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		EmpInitializeLeaves empInitializeLeaves=transaction.getEmpInitializeLeavesByEmpTypeId(employeeInitializeLeavesForm.getEmpTypeId(),employeeInitializeLeavesForm.getLeaveTypeId(),employeeInitializeLeavesForm.getAllotedDate());
		if(empInitializeLeaves!=null && empInitializeLeaves.getIsActive()){
			throw new DuplicateException();
		}else if(empInitializeLeaves!=null && !empInitializeLeaves.getIsActive()){
			employeeInitializeLeavesForm.setDupId(empInitializeLeaves.getId());
			throw new ReActivateException();
		}
	}
	
	/**
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEmployeeInitialize(int empId) throws Exception {
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		return transaction.deleteEmployeeInitialize(empId);
	}
	
	/**
	 * @param employeeInitializeLeavesForm
	 * @throws Exception
	 */
	public void editEmployeeInitialize(EmployeeInitializeLeavesForm employeeInitializeLeavesForm) throws Exception{
		IEmployeeInitializeLeavesTransaction transaction = new EmployeeInitializeLeavesTransactionImpl();
		EmpInitializeLeaves empInitializeLeaves = transaction.getEmpInitializeLeavesToEdit(employeeInitializeLeavesForm);
		EmployeeInitializeLeavesHelper.getInstance().convertBOToForm(empInitializeLeaves,employeeInitializeLeavesForm);
	}
	
	public boolean updateEmployeeInitialize(EmployeeInitializeLeavesForm employeeInitializeLeavesForm) throws Exception{
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		EmpInitializeLeaves empInitializeLeaves=EmployeeInitializeLeavesHelper.getInstance().convertFormToBo(employeeInitializeLeavesForm);
		return transaction.updateEmployeeInitialize(empInitializeLeaves);
	}
	
	public boolean reActivateEmployeeInitialize(int empId) throws Exception {
		IEmployeeInitializeLeavesTransaction transaction=new EmployeeInitializeLeavesTransactionImpl();
		return transaction.reActivateEmployeeInitialize(empId);
	}
}
