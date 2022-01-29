package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.HodApprover;
import com.kp.cms.forms.employee.HodApproverForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.transactions.exam.IEmployeeApproverTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeApproverTransactionImpl;

public class HodApproverHandler {
	private static volatile HodApproverHandler handler = null;
	private static final Log log = LogFactory.getLog(HodApproverHandler.class);
	private HodApproverHandler() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHandler.
	 * @return
	 */
	public static HodApproverHandler getInstance() {
		if (handler == null) {
			handler = new HodApproverHandler();
		}
		return handler;
	}
	
	public boolean saveHodDetails(List<EmployeeTO> employeeToList,HodApproverForm hodApproverForm) throws Exception{
		List<HodApprover > boList = new ArrayList<HodApprover >();
		if(employeeToList != null){
			Iterator<EmployeeTO> iterator = employeeToList.iterator();
			while (iterator.hasNext()) {
				EmployeeTO employeeTO = (EmployeeTO) iterator.next();
				if(employeeTO.getChecked1() != null && employeeTO.getChecked1().equalsIgnoreCase("on")){
					HodApprover  bo = new HodApprover ();
					Employee approver = new Employee();
					approver.setId(Integer.parseInt(hodApproverForm.getApproverId()));
					bo.setApprover(approver );
					Employee employee = new Employee();
					employee.setId(employeeTO.getId());
					bo.setEmployee(employee);
					bo.setCreatedBy(hodApproverForm.getUserId());
					bo.setModifiedBy(hodApproverForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					boList.add(bo);
				}
			}
		}
		IEmployeeApproverTransaction transaction=EmployeeApproverTransactionImpl.getInstance();
		return transaction.saveHodDetails(boList);
	}
	
	public List<EmployeeTO> getEmployeeDetailsDeptWiseForHodApprover(HodApproverForm hodApproverForm) throws Exception{
		List<EmployeeTO> employeeTOs = new ArrayList<EmployeeTO>();
		IEmployeeApproverTransaction transaction=EmployeeApproverTransactionImpl.getInstance();
		List<Employee> employees = transaction.getEmployeeDetailsDeptWise(hodApproverForm.getDepartmentId());
		Map<Integer, Employee> approverMap = transaction.getAllHodApproverEmployees();
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

}
