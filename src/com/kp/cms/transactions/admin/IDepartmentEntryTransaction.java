package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.forms.admin.DepartmentEntryForm;

public interface IDepartmentEntryTransaction {
	
	public List<Department> getDepartmentFields() throws Exception;

	public Department isDuplicateDepartmentEntry(Department dupliDepartment)throws Exception;

	public boolean addDepartment(Department department, String mode) throws Exception;

	public boolean deleteDepartmentEntry(int departmentId, boolean activate,
			DepartmentEntryForm departmentEntryForm)throws Exception;

	public Map<Integer, String> getEmpStream()throws Exception;

	public Department isDuplicateDepartmentCodeEntry(Department dupliDepartmentBO) throws Exception;

	public Department getDepartmentdetails(int id) throws Exception;
}
