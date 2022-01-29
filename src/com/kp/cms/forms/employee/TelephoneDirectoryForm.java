package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;

public class TelephoneDirectoryForm extends BaseActionForm{
	private int id;
	private String departmentName;
	private String employeeName;
	private String extNo;
	private List<EmployeeTO> empList;
	private List<DepartmentEntryTO> deptListTO;
	//private Map<String,List<TelephoneDirectoryTo>> extensionNumMap;
	private Map<String,Map<String,String>> extensionNumMap;
	
	public Map<String, Map<String, String>> getExtensionNumMap() {
		return extensionNumMap;
	}
	public void setExtensionNumMap(Map<String, Map<String, String>> extensionNumMap) {
		this.extensionNumMap = extensionNumMap;
	}
	/*public Map<String, List<TelephoneDirectoryTo>> getExtensionNumMap() {
		return extensionNumMap;
	}
	public void setExtensionNumMap(
			Map<String, List<TelephoneDirectoryTo>> extensionNumMap) {
		this.extensionNumMap = extensionNumMap;
	}*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public List<EmployeeTO> getEmpList() {
		return empList;
	}
	public void setEmpList(List<EmployeeTO> empList) {
		this.empList = empList;
	}
	public List<DepartmentEntryTO> getDeptListTO() {
		return deptListTO;
	}
	public void setDeptListTO(List<DepartmentEntryTO> deptListTO) {
		this.deptListTO = deptListTO;
	}
	public String getExtNo() {
		return extNo;
	}
	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}
	
}
