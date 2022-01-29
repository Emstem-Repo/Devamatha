package com.kp.cms.to.serviceLearning;

import java.util.List;

import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;

public class ProgrammeEntryTo {
	
	private String id;
	private String dupId;
	private DepartmentNameEntryTo departmentNameEntryTo;
	private String programmeName;
	private String inchargeName;
	private int orgId;
	private String programmeCode;
	private List<ProgrammeEntryTo> programmeEntryToList;
	private List<DepartmentNameEntryTo> departmentNameEntryToList;
	private String startDate;
	private String endDate;
	private String maxHrs;
	private ExtraCreditActivityTypeTo extraCreditActivityTypeTo;
	private String activityName;
	private String deptName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDupId() {
		return dupId;
	}
	public void setDupId(String dupId) {
		this.dupId = dupId;
	}
	public DepartmentNameEntryTo getDepartmentNameEntryTo() {
		return departmentNameEntryTo;
	}
	public void setDepartmentNameEntryTo(DepartmentNameEntryTo departmentNameEntryTo) {
		this.departmentNameEntryTo = departmentNameEntryTo;
	}
	public String getProgrammeName() {
		return programmeName;
	}
	public void setProgrammeName(String programmeName) {
		this.programmeName = programmeName;
	}
	public String getInchargeName() {
		return inchargeName;
	}
	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public String getProgrammeCode() {
		return programmeCode;
	}
	public void setProgrammeCode(String programmeCode) {
		this.programmeCode = programmeCode;
	}
	public List<ProgrammeEntryTo> getProgrammeEntryToList() {
		return programmeEntryToList;
	}
	public void setProgrammeEntryToList(List<ProgrammeEntryTo> programmeEntryToList) {
		this.programmeEntryToList = programmeEntryToList;
	}
	public List<DepartmentNameEntryTo> getDepartmentNameEntryToList() {
		return departmentNameEntryToList;
	}
	public void setDepartmentNameEntryToList(
			List<DepartmentNameEntryTo> departmentNameEntryToList) {
		this.departmentNameEntryToList = departmentNameEntryToList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMaxHrs() {
		return maxHrs;
	}
	public void setMaxHrs(String maxHrs) {
		this.maxHrs = maxHrs;
	}
	public ExtraCreditActivityTypeTo getExtraCreditActivityTypeTo() {
		return extraCreditActivityTypeTo;
	}
	public void setExtraCreditActivityTypeTo(
			ExtraCreditActivityTypeTo extraCreditActivityTypeTo) {
		this.extraCreditActivityTypeTo = extraCreditActivityTypeTo;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
	
}
