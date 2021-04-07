package com.kp.cms.handlers.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.EmployeeReportForm;
import com.kp.cms.helpers.employee.EmployeeReportHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmployeeReportTO;
import com.kp.cms.transactions.employee.IEmployeeReportTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeReportTxnImpl;

public class EmployeeReportHandler {
	
	IEmployeeReportTransaction reportTransaction = EmployeeReportTxnImpl.getInstance();
	private static volatile EmployeeReportHandler employeeReportHandler = null;
	public static EmployeeReportHandler getInstance(){
		if(employeeReportHandler == null){
		employeeReportHandler = new EmployeeReportHandler();
		return employeeReportHandler;
		}
		return employeeReportHandler;
}
/**
 * @param employeeReportForm
 * @throws Exception 
 */
	public void getInitialPageData(EmployeeReportForm employeeReportForm) throws Exception {
		
		Map<String,String> streamDetailsMap=reportTransaction.getStreamMap();
		if(streamDetailsMap!=null && !streamDetailsMap.isEmpty()){
			employeeReportForm.setStreamMap(streamDetailsMap);
		}
		Map<String,String> departmentMap=reportTransaction.getDepartmentMap();
		if(departmentMap!=null && !departmentMap.isEmpty()){
			employeeReportForm.setDepartmentMap(departmentMap);
		}
		Map<String,String> designationMap=reportTransaction.getDesignation();
		if(designationMap!=null && !designationMap.isEmpty()){
			employeeReportForm.setDesignationMap(designationMap);
		}
		Map<String,String> workLocationMap=reportTransaction.getWorkLocationMap();
		if(workLocationMap!=null && !workLocationMap.isEmpty()){
			employeeReportForm.setWorkLocationMap(workLocationMap);
		}
}
/**
 * @param employeeReportForm
 * @return
 * @throws Exception
 */
public List<EmployeeTO> getEmployeeSearchedResults( EmployeeReportForm employeeReportForm) throws Exception {
	 EmployeeReportHelper employeeReportHelper = EmployeeReportHelper.getInstance();
	 int streamId = 0;
	 int departmentId = 0;
	 int designationId = 0;
	 int workLocationId = 0;
	  	if(employeeReportForm.getStreamDetails()!=null && !employeeReportForm.getStreamDetails().isEmpty()){
	  		streamId = Integer.parseInt(employeeReportForm.getStreamDetails());
	  	}
	  	if(employeeReportForm.getDepartment()!=null  && !employeeReportForm.getDepartment().isEmpty()){
	  		departmentId = Integer.parseInt(employeeReportForm.getDepartment());
	  	}
	  	if(employeeReportForm.getDesignation()!=null && !employeeReportForm.getDesignation().isEmpty()){
	  		designationId = Integer.parseInt(employeeReportForm.getDesignation());
	  	}
	  	if(employeeReportForm.getWorkLocation()!=null && !employeeReportForm.getWorkLocation().isEmpty()){
	  		workLocationId = Integer.parseInt(employeeReportForm.getWorkLocation());
	  	}
	  		
	  	StringBuffer query = employeeReportHelper.getSelectionSearchCriteria(employeeReportForm,streamId,departmentId,designationId,workLocationId);
	  	List<Employee> employeelist=reportTransaction.getSearchedEmployee(query);
	  	List<EmployeeTO> employeeTOs=employeeReportHelper.convertEmployeeBoTOTo(employeelist);
	return employeeTOs;
}
	
	/**
	 * @param employeeReportForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(EmployeeReportForm employeeReportForm, HttpServletRequest request)throws Exception {
		boolean isUpdated= false;
		EmployeeReportHelper employeeReportHelper = EmployeeReportHelper.getInstance();
		 int streamId = 0;
		 int departmentId = 0;
		 int designationId = 0;
		 int workLocationId = 0;
		  	if(employeeReportForm.getStreamDetails()!=null && !employeeReportForm.getStreamDetails().isEmpty()){
		  		streamId = Integer.parseInt(employeeReportForm.getStreamDetails());
		  	}
		  	if(employeeReportForm.getDepartment()!=null  && !employeeReportForm.getDepartment().isEmpty()){
		  		departmentId = Integer.parseInt(employeeReportForm.getDepartment());
		  	}
		  	if(employeeReportForm.getDesignation()!=null && !employeeReportForm.getDesignation().isEmpty()){
		  		designationId = Integer.parseInt(employeeReportForm.getDesignation());
		  	}
		  	if(employeeReportForm.getWorkLocation()!=null && !employeeReportForm.getWorkLocation().isEmpty()){
		  		workLocationId = Integer.parseInt(employeeReportForm.getWorkLocation());
		  	}
		StringBuffer query = employeeReportHelper.getSelectionSearchCriteria(employeeReportForm,streamId,departmentId,designationId,workLocationId);
		List<Employee> employeelist=reportTransaction.getSearchedEmployee(query);
		List<EmployeeTO> employeeTOs = EmployeeReportHelper.getInstance().convertBOToExcel(employeelist);
		EmployeeReportTO empReportTo=EmployeeReportHelper.getInstance().getSelectedColumns(employeeReportForm);
		isUpdated=EmployeeReportHelper.getInstance().convertToExcel(employeeTOs,empReportTo,request);
		return isUpdated;
	}

}
