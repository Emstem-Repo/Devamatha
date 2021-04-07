package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmpInitializeTo;
import com.kp.cms.to.employee.EmployeeKeyValueTO;

public class EmployeeInitializeLeavesForm extends BaseActionForm {
	private int id;
	private String empTypeId;
	private String allotedLeaves;
	private boolean isInitializeRequired;
	private String dupEmpTypeId;
	List<EmployeeKeyValueTO> listEmployeeType;
	List<EmpInitializeTo> list;
	private String leaveTypeId;
	List<EmpLeaveTypeTO> leaveList;
	private String dupLeaveTypeId;
	private int dupId;
	private String allotedDate;
	private String dupDate;
	
	public String getEmpTypeId() {
		return empTypeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	public String getAllotedLeaves() {
		return allotedLeaves;
	}
	public void setAllotedLeaves(String allotedLeaves) {
		this.allotedLeaves = allotedLeaves;
	}
	public boolean getIsInitializeRequired() {
		return this.isInitializeRequired;
	}
	public void setIsInitializeRequired(boolean isInitializeRequired) {
		this.isInitializeRequired = isInitializeRequired;
	}
	public String getDupEmpTypeId() {
		return dupEmpTypeId;
	}
	public void setDupEmpTypeId(String dupEmpTypeId) {
		this.dupEmpTypeId = dupEmpTypeId;
	}
	public List<EmployeeKeyValueTO> getListEmployeeType() {
		return listEmployeeType;
	}
	public void setListEmployeeType(List<EmployeeKeyValueTO> listEmployeeType) {
		this.listEmployeeType = listEmployeeType;
	}
	public List<EmpInitializeTo> getList() {
		return list;
	}
	public void setList(List<EmpInitializeTo> list) {
		this.list = list;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public List<EmpLeaveTypeTO> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(List<EmpLeaveTypeTO> leaveList) {
		this.leaveList = leaveList;
	}
	public String getDupLeaveTypeId() {
		return dupLeaveTypeId;
	}
	public void setDupLeaveTypeId(String dupLeaveTypeId) {
		this.dupLeaveTypeId = dupLeaveTypeId;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getAllotedDate() {
		return allotedDate;
	}
	public void setAllotedDate(String allotedDate) {
		this.allotedDate = allotedDate;
	}
	public String getDupDate() {
		return dupDate;
	}
	public void setDupDate(String dupDate) {
		this.dupDate = dupDate;
	}
	public void resetFields(){
		this.empTypeId=null;
		this.dupEmpTypeId=null;
		this.allotedLeaves=null;
		this.isInitializeRequired=true;
		this.leaveTypeId=null;
		this.id=0;
		this.allotedDate=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
}
