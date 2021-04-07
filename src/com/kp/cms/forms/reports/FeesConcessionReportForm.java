package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ClassFeeConcessionReportTO;

public class FeesConcessionReportForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divId;
	private String accId;
	private String startDate;
	private String endDate;
	private Boolean classOrDate;
	private List<ClassFeeConcessionReportTO> classFeeConcessionList;
	private String print;
	private String organizationName;
	private String divisionName;
	
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
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
	public Boolean getClassOrDate() {
		return classOrDate;
	}
	public void setClassOrDate(Boolean classOrDate) {
		this.classOrDate = classOrDate;
	}
	public List<ClassFeeConcessionReportTO> getClassFeeConcessionList() {
		return classFeeConcessionList;
	}
	public void setClassFeeConcessionList(
			List<ClassFeeConcessionReportTO> classFeeConcessionList) {
		this.classFeeConcessionList = classFeeConcessionList;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		super.setProgramTypeId(null);
		divId = null;
		accId = null;
//		classOrDate = true;
		startDate = null;
		endDate = null;
	}	

}
