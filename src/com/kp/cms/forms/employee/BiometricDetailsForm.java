package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.BiometricDetailsTO;

public class BiometricDetailsForm extends BaseActionForm{
private int id;
private String machineId;
private String machineIp;
private String machineName;
private String origMachineId;
private String origMachineIp;
private String origMachineName;
private String workLocation;
private String origWorkLocation;
private List<BiometricDetailsTO> biometricList;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMachineId() {
	return machineId;
}
public void setMachineId(String machineId) {
	this.machineId = machineId;
}
public String getMachineIp() {
	return machineIp;
}
public void setMachineIp(String machineIp) {
	this.machineIp = machineIp;
}
public String getMachineName() {
	return machineName;
}
public void setMachineName(String machineName) {
	this.machineName = machineName;
}
public void reset(){
	this.machineId=null;
	this.machineIp=null;
	this.machineName=null;
	this.id=0;
	this.workLocation=null;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public List<BiometricDetailsTO> getBiometricList() {
	return biometricList;
}
public void setBiometricList(List<BiometricDetailsTO> biometricList) {
	this.biometricList = biometricList;
}
public String getOrigMachineId() {
	return origMachineId;
}
public void setOrigMachineId(String origMachineId) {
	this.origMachineId = origMachineId;
}
public String getOrigMachineIp() {
	return origMachineIp;
}
public void setOrigMachineIp(String origMachineIp) {
	this.origMachineIp = origMachineIp;
}
public String getOrigMachineName() {
	return origMachineName;
}
public void setOrigMachineName(String origMachineName) {
	this.origMachineName = origMachineName;
}
public String getWorkLocation() {
	return workLocation;
}
public void setWorkLocation(String workLocation) {
	this.workLocation = workLocation;
}
public String getOrigWorkLocation() {
	return origWorkLocation;
}
public void setOrigWorkLocation(String origWorkLocation) {
	this.origWorkLocation = origWorkLocation;
}
}
