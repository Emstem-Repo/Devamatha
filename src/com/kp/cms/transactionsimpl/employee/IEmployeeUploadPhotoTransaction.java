package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmployeeUploadPhoto;

public interface IEmployeeUploadPhotoTransaction {

	public int uploadEmployeePhotos(List<EmployeeUploadPhoto> employeeUploadPhotoList) throws Exception;

	public Employee getEmployee(String fingerFrintId) throws Exception;
	
}
