package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;

public interface IEmployeeSmartCardUploadTransaction {

	public Map<String, Employee> getFingerPrintIds()throws Exception;

	public boolean addEmpSmartCardNo(List<Employee> list, String userId)throws Exception;

}
