package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.TelephoneExtensionNumBo;
import com.kp.cms.forms.employee.TelephoneDirectoryForm;

public interface ITelephoneDirectoryTransaction {

	List<Employee> getSearchDetails(TelephoneDirectoryForm objForm)throws Exception;

	public List<Department> getDepartmentList()throws Exception;
	public EmpImages getEmpimages(int id)throws Exception;

	List<TelephoneExtensionNumBo> getTelephoneExtensionNumBo()throws Exception;

}
