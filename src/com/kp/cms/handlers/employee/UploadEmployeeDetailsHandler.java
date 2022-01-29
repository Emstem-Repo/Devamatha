package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.helpers.employee.UploadEmployeeDetailsHelper;
import com.kp.cms.helpers.exam.UploadInternalOverAllHelper;
import com.kp.cms.to.employee.UploadEmployeeDetailsTo;
import com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction;
import com.kp.cms.transactionsimpl.employee.UploadEmployeeDetailsTransaction;

/**
 * @author Administrator
 *
 */
public class UploadEmployeeDetailsHandler {
	
	private static volatile UploadEmployeeDetailsHandler instance=null;
	
	/**
	 * 
	 */
	private UploadEmployeeDetailsHandler(){}
	
	/**
	 * @return
	 */
	public static UploadEmployeeDetailsHandler getInstance(){
		if(instance==null){
			instance=new UploadEmployeeDetailsHandler();
		}
		return instance;
	}
	
	IUploadEmployeeDetailsTransaction uploadTransaction=UploadEmployeeDetailsTransaction.getInstance();

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, Employee> getEmployeeList()throws Exception {
		// TODO Auto-generated method stub
		return uploadTransaction.getUploadEmployeeList();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDepartmentMap()throws Exception {
		// TODO Auto-generated method stub
		return uploadTransaction.getDepartmentMap();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDesignationMap()throws Exception {
		// TODO Auto-generated method stub
		return uploadTransaction.getDesignationMap();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getCountryMap()throws Exception {
		// TODO Auto-generated method stub
		return uploadTransaction.getCountyMap();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getStateMap()throws Exception {
		// TODO Auto-generated method stub
		return uploadTransaction.getStateMap();
	}

	/**
	 * @param result
	 * @param string 
	 * @param employeeMap 
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<UploadEmployeeDetailsTo> result, String string, Map<String, Employee> employeeMap)throws Exception {
		// TODO Auto-generated method stub
		List<Employee> list=UploadEmployeeDetailsHelper.getInstance().convertToListToBoList(result,string,employeeMap);
		return uploadTransaction.saveEmployeeDetails(list);
	}

	public Map<String, String> getEmpTypeMap()throws Exception{
		// TODO Auto-generated method stub
		return uploadTransaction.getEmpTypeMap();
	}
	public Map<String, String> getJobTitleMap()throws Exception{
		// TODO Auto-generated method stub
		return uploadTransaction.getJobTitleMap();
	}
	public Map<String, String> getWorkLocationMap()throws Exception{
		// TODO Auto-generated method stub
		return uploadTransaction.getWorkLocationMap();
	}
	public Map<String, String> getStreamMap()throws Exception{
		// TODO Auto-generated method stub
		return uploadTransaction.getStreamMap();
	}
}
