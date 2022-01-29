package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.forms.employee.EmployeeApproverForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.transactions.exam.IEmployeeApproverTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeApproverTransactionImpl;

public class EmployeeApproverHandler {
	/**
	 * Singleton object of EmployeeApplyLeaveHandler
	 */
	private static volatile EmployeeApproverHandler handler = null;
	private static final Log log = LogFactory.getLog(EmployeeApproverHandler.class);
	private EmployeeApproverHandler() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHandler.
	 * @return
	 */
	public static EmployeeApproverHandler getInstance() {
		if (handler == null) {
			handler = new EmployeeApproverHandler();
		}
		return handler;
	}
	public List<EmployeeTO> getEmployeeDetailsDeptWise(EmployeeApproverForm employeeApproverForm) throws Exception{
		List<EmployeeTO> employeeTOs = new ArrayList<EmployeeTO>();
		IEmployeeApproverTransaction transaction=EmployeeApproverTransactionImpl.getInstance();
		List<Employee> employees = transaction.getEmployeeDetailsDeptWise(employeeApproverForm.getDepartmentId());
		Map<Integer, Employee> approverMap = transaction.getAllApproverEmployees();
		if(employees != null){
			Iterator<Employee> iterator = employees.iterator();
			while (iterator.hasNext()) {
				Employee employee = (Employee) iterator.next();
				EmployeeTO to = new EmployeeTO();
				to.setId(employee.getId());
				to.setFirstName(employee.getFirstName());
				to.setDepartmentName(employee.getDepartment().getName());
				if(approverMap.containsKey(employee.getId())){
					Employee approver = approverMap.get(employee.getId());
					if(approver.getTitleId() != null && approver.getTitleId().getTitle() != null){
						to.setApproverName(approver.getFirstName()+"("+approver.getTitleId().getTitle()+")");
					}else{
						to.setApproverName(approver.getFirstName());
					}
				}
				employeeTOs.add(to);
			}
		}
		return employeeTOs;
	}
	/**
	 * @param employeeToList
	 * @param employeeApproverForm
	 * @throws Exception
	 */
	public boolean saveDetails(List<EmployeeTO> employeeToList,EmployeeApproverForm employeeApproverForm) throws Exception{
		List<EmployeeApprover> boList = new ArrayList<EmployeeApprover>();
		if(employeeToList != null){
			Iterator<EmployeeTO> iterator = employeeToList.iterator();
			while (iterator.hasNext()) {
				EmployeeTO employeeTO = (EmployeeTO) iterator.next();
				if(employeeTO.getChecked1() != null && employeeTO.getChecked1().equalsIgnoreCase("on")){
					EmployeeApprover bo = new EmployeeApprover();
					Employee approver = new Employee();
					approver.setId(Integer.parseInt(employeeApproverForm.getApproverId()));
					bo.setApprover(approver );
					Employee employee = new Employee();
					employee.setId(employeeTO.getId());
					bo.setEmployee(employee);
					bo.setCreatedBy(employeeApproverForm.getUserId());
					bo.setModifiedBy(employeeApproverForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					boList.add(bo);
				}
			}
		}
		IEmployeeApproverTransaction transaction=EmployeeApproverTransactionImpl.getInstance();
		return transaction.saveDetails(boList);
	}
	
}
