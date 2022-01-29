package com.kp.cms.forms.serviceLearning;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;

public class ProgrammeEntryForm extends BaseActionForm{
	private static final long serialVersionUID = 1L;
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
	private String deptId;
	private String startMins;
	private String startHours;
	
	// by bala
	
	private List<ExtraCreditActivityTypeTo> activityTypeTos;
	private String extraCreditActivityType;
	
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	public String getStartMins() {
		return startMins;
	}
	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}
	public String getStartHours() {
		return startHours;
	}
	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.programmeCode = null;
		this.programmeName = null;
		this.inchargeName = null;
		this.startDate = null;
		this.endDate = null;
		this.startHours=null;
		this.startMins=null;
		this.deptId=null;	
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<ExtraCreditActivityTypeTo> getActivityTypeTos() {
		return activityTypeTos;
	}
	public void setActivityTypeTos(List<ExtraCreditActivityTypeTo> activityTypeTos) {
		this.activityTypeTos = activityTypeTos;
	}
	public String getExtraCreditActivityType() {
		return extraCreditActivityType;
	}
	public void setExtraCreditActivityType(String extraCreditActivityType) {
		this.extraCreditActivityType = extraCreditActivityType;
	}
	
	

}
