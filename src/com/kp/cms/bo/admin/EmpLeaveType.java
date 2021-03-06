package com.kp.cms.bo.admin;

// Generated Sep 22, 2009 4:21:49 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmployeeSettings;

/**
 * EmpLeaveType generated by hbm2java
 */
public class EmpLeaveType implements java.io.Serializable {

	private int id;
	private String name;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isLeave;
	private Boolean isExemption;
	private Set<EmpApplyLeave> empApplyLeaves = new HashSet<EmpApplyLeave>(0);
	private Set<EmpLeaveAllotment> empLeaveAllot=new HashSet<EmpLeaveAllotment>(0);
	private Set<EmployeeSettings> empSettings=new HashSet<EmployeeSettings>(0);
	private String code;
	private Boolean continuousdays;
    private Boolean canapplyonline;
	public EmpLeaveType() {
	}

	public EmpLeaveType(int id) {
		this.id = id;
	}

	public EmpLeaveType(int id, String name, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive,Boolean isLeave, Boolean isExemption, Set<EmpApplyLeave> empApplyLeaves,
            String code,Boolean continuousdays,Boolean canapplyonline) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.isLeave=isLeave;
		this.isExemption=isExemption;
		this.empApplyLeaves = empApplyLeaves;
		this.code=code;
		this.continuousdays=continuousdays;
		this.canapplyonline=canapplyonline;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<EmpApplyLeave> getEmpApplyLeaves() {
		return this.empApplyLeaves;
	}

	public Boolean getIsLeave() {
		return isLeave;
	}

	public Boolean getisExemption() {
		return isExemption;
	}

	public void setIsLeave(Boolean isLeave) {
		this.isLeave = isLeave;
	}

	public void setisExemption(Boolean isExemption) {
		this.isExemption = isExemption;
	}

	public void setEmpApplyLeaves(Set<EmpApplyLeave> empApplyLeaves) {
		this.empApplyLeaves = empApplyLeaves;
	}

	public Set<EmpLeaveAllotment> getEmpLeaveAllot() {
		return empLeaveAllot;
	}

	public void setEmpLeaveAllot(Set<EmpLeaveAllotment> empLeaveAllot) {
		this.empLeaveAllot = empLeaveAllot;
	}

	public Set<EmployeeSettings> getEmpSettings() {
		return empSettings;
	}

	public void setEmpSettings(Set<EmployeeSettings> empSettings) {
		this.empSettings = empSettings;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getContinuousdays() {
		return continuousdays;
	}

	public void setContinuousdays(Boolean continuousdays) {
		this.continuousdays = continuousdays;
	}

	public Boolean getCanapplyonline() {
		return canapplyonline;
	}

	public void setCanapplyonline(Boolean canapplyonline) {
		this.canapplyonline = canapplyonline;
	}

}
