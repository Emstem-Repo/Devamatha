package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;

public class ResetLeavesForm extends BaseActionForm {
	
	private String month;
	private String year;
	private String empTypeId;
	List<EmployeeKeyValueTO> listEmployeeType;
	List<EmpLeaveTypeTO> leaveList;
	private String leaveTypeId;
	Map<Integer,EmpLeave> empLeaveMap;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	public List<EmployeeKeyValueTO> getListEmployeeType() {
		return listEmployeeType;
	}
	public void setListEmployeeType(List<EmployeeKeyValueTO> listEmployeeType) {
		this.listEmployeeType = listEmployeeType;
	}
	
	public List<EmpLeaveTypeTO> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(List<EmpLeaveTypeTO> leaveList) {
		this.leaveList = leaveList;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public Map<Integer, EmpLeave> getEmpLeaveMap() {
		return empLeaveMap;
	}
	public void setEmpLeaveMap(Map<Integer, EmpLeave> empLeaveMap) {
		this.empLeaveMap = empLeaveMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields(){
		this.year=null;
		this.empTypeId=null;
		this.month=null;
		this.leaveTypeId=null;
	}
}
