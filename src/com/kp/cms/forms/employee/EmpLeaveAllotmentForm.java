package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpLeaveAllotTO;

public class EmpLeaveAllotmentForm extends BaseActionForm{
private int id;
private String empType;
private String leaveType;
private String allottedLeave;
private String initializeRequired;
private String noOfAccumulatedLeave;
private Map<Integer,String> empLeaveMap;
private List<EmpLeaveAllotTO> leaveAllotTOList;
private Map<Integer,String> empTypeMap;
private String origEmpType;
private String origLeaveType;
private String origAllottedLeave;
private String origInitRequired;
private String origAccumulatedLeave;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getEmpType() {
	return empType;
}
public void setEmpType(String empType) {
	this.empType = empType;
}
public String getLeaveType() {
	return leaveType;
}
public void setLeaveType(String leaveType) {
	this.leaveType = leaveType;
}
public String getAllottedLeave() {
	return allottedLeave;
}
public void setAllottedLeave(String allottedLeave) {
	this.allottedLeave = allottedLeave;
}
public String getInitializeRequired() {
	return initializeRequired;
}
public void setInitializeRequired(String initializeRequired) {
	this.initializeRequired = initializeRequired;
}
public String getNoOfAccumulatedLeave() {
	return noOfAccumulatedLeave;
}
public void setNoOfAccumulatedLeave(String noOfAccumulatedLeave) {
	this.noOfAccumulatedLeave = noOfAccumulatedLeave;
}
public void reset(){
	this.empType=null;
	this.leaveType=null;
	this.allottedLeave=null;
	this.initializeRequired=null;
	this.noOfAccumulatedLeave=null;
	this.id=0;
}
public Map<Integer, String> getEmpLeaveMap() {
	return empLeaveMap;
}
public void setEmpLeaveMap(Map<Integer, String> empLeaveMap) {
	this.empLeaveMap = empLeaveMap;
}
public List<EmpLeaveAllotTO> getLeaveAllotTOList() {
	return leaveAllotTOList;
}
public void setLeaveAllotTOList(List<EmpLeaveAllotTO> leaveAllotTOList) {
	this.leaveAllotTOList = leaveAllotTOList;
}
public Map<Integer, String> getEmpTypeMap() {
	return empTypeMap;
}
public void setEmpTypeMap(Map<Integer, String> empTypeMap) {
	this.empTypeMap = empTypeMap;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public String getOrigEmpType() {
	return origEmpType;
}
public void setOrigEmpType(String origEmpType) {
	this.origEmpType = origEmpType;
}
public String getOrigLeaveType() {
	return origLeaveType;
}
public void setOrigLeaveType(String origLeaveType) {
	this.origLeaveType = origLeaveType;
}
public String getOrigAllottedLeave() {
	return origAllottedLeave;
}
public void setOrigAllottedLeave(String origAllottedLeave) {
	this.origAllottedLeave = origAllottedLeave;
}
public String getOrigInitRequired() {
	return origInitRequired;
}
public void setOrigInitRequired(String origInitRequired) {
	this.origInitRequired = origInitRequired;
}
public String getOrigAccumulatedLeave() {
	return origAccumulatedLeave;
}
public void setOrigAccumulatedLeave(String origAccumulatedLeave) {
	this.origAccumulatedLeave = origAccumulatedLeave;
}
}
