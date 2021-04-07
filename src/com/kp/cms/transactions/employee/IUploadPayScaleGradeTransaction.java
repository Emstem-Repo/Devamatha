package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;

public interface IUploadPayScaleGradeTransaction {

	Map<String, Integer> getEmpMap() throws Exception;

	Map<String, Integer> getPayScale() throws Exception;

	Map<String, Integer> getAllowanceDetails() throws Exception;

	Map<String, String> getPayScaleMap() throws Exception;

	boolean addUploadedData(List<Employee> empBoList, String user) throws Exception;

}
