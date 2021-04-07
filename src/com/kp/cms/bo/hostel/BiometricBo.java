package com.kp.cms.bo.hostel;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlUnits;

public class BiometricBo implements Serializable {
	private int id;
	private HlHostel hlHostel;
	private HlBlocks hlBlock;
	private HlUnits hlUnit;
	private String machineId;
	private String machineIp;
	private String machineName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String worklocation;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HlHostel getHlHostel() {
		return hlHostel;
	}
	public void setHlHostel(HlHostel hlHostel) {
		this.hlHostel = hlHostel;
	}
	public HlBlocks getHlBlock() {
		return hlBlock;
	}
	public void setHlBlock(HlBlocks hlBlock) {
		this.hlBlock = hlBlock;
	}
	public HlUnits getHlUnit() {
		return hlUnit;
	}
	public void setHlUnit(HlUnits hlUnit) {
		this.hlUnit = hlUnit;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getWorklocation() {
		return worklocation;
	}
	public void setWorklocation(String worklocation) {
		this.worklocation = worklocation;
	}
	
}
