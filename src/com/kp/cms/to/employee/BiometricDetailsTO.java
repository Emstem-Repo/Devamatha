package com.kp.cms.to.employee;

import java.io.Serializable;

public class BiometricDetailsTO implements Serializable{
private int id;
private String machineId;
private String machineIp;
private String machinName;
private String workLocation;
private String hostelName;
private String blockName;
private String unitName;

public String getHostelName() {
	return hostelName;
}
public void setHostelName(String hostelName) {
	this.hostelName = hostelName;
}
public String getBlockName() {
	return blockName;
}
public void setBlockName(String blockName) {
	this.blockName = blockName;
}
public String getUnitName() {
	return unitName;
}
public void setUnitName(String unitName) {
	this.unitName = unitName;
}
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
public String getMachinName() {
	return machinName;
}
public void setMachinName(String machinName) {
	this.machinName = machinName;
}
public String getWorkLocation() {
	return workLocation;
}
public void setWorkLocation(String workLocation) {
	this.workLocation = workLocation;
}
 }
