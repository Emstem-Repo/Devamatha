package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ApplicationFeeTO;

public class ApplicationFeeForm extends BaseActionForm 
{
	private int appfeeId;
	
	private int origAppId;
	private String origAppName;
	private String appName;
	private int reactivateid;
	private String year;
	private String amount;
	private String subReligionId;
	private String orgSubReligionId;
	private int reactivateId;
	private String orgYear;
	private String orgAmount;
	private String programTypeId;
	private String orgProgramTypeId;
	
	
	
	
	public String getOrgProgramTypeId() {
		return orgProgramTypeId;
	}
	public void setOrgProgramTypeId(String orgProgramTypeId) {
		this.orgProgramTypeId = orgProgramTypeId;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getOrgSubReligionId() {
		return orgSubReligionId;
	}
	public void setOrgSubReligionId(String orgSubReligionId) {
		this.orgSubReligionId = orgSubReligionId;
	}
	public String getOrgYear() {
		return orgYear;
	}
	public void setOrgYear(String orgYear) {
		this.orgYear = orgYear;
	}
	public int getReactivateId() {
		return reactivateId;
	}
	public void setReactivateId(int reactivateId) {
		this.reactivateId = reactivateId;
	}
	public String getSubReligionId() {
		return subReligionId;
	}
	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}


	private List<ApplicationFeeTO> appfeeList;
	
	
	public List<ApplicationFeeTO> getAppfeeList() {
		return appfeeList;
	}
	public void setAppfeeList(List<ApplicationFeeTO> appfeeList) {
		this.appfeeList = appfeeList;
	}
	
	
	public int getAppfeeId() {
		return appfeeId;
	}
	public void setAppfeeId(int appfeeId) {
		this.appfeeId = appfeeId;
	}
	public int getOrigAppId() {
		return origAppId;
	}
	public void setOrigAppId(int origAppId) {
		this.origAppId = origAppId;
	}
	public String getOrigAppName() {
		return origAppName;
	}
	public void setOrigAppName(String origAppName) {
		this.origAppName = origAppName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	public String getOrgAmount() {
		return orgAmount;
	}
	public void setOrgAmount(String orgAmount) {
		this.orgAmount = orgAmount;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
