package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;

public class EmployeeApproverForm extends BaseActionForm{
	 private int id;
	 private String departmentId;
	 private String approverId;
	 private Map<Integer,String> departmentMap;
	 private Map<Integer,String> approverMap;
	 private List<EmployeeTO> employeeToList;
	 
	 public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
	}
	 public void resetFields() {
		 	this.departmentId=null;
			this.employeeToList=null;
			this.approverMap=null;
			this.approverId=null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public Map<Integer, String> getApproverMap() {
		return approverMap;
	}
	public void setApproverMap(Map<Integer, String> approverMap) {
		this.approverMap = approverMap;
	}
	public List<EmployeeTO> getEmployeeToList() {
		return employeeToList;
	}
	public void setEmployeeToList(List<EmployeeTO> employeeToList) {
		this.employeeToList = employeeToList;
	}
	 
}
