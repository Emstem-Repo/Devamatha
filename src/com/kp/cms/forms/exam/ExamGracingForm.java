package com.kp.cms.forms.exam;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ExamGracingForm extends BaseActionForm {
	private String registerNumber;
	private String remark;
	private String percentage;
	private boolean initialPage;
	private String appliedYear;
	private String studentId;
	private Double totalGraceMark;
	private Double totalSubMark;
	private Set<Integer> gracingIds = new HashSet<Integer>();
	private boolean onlyPass;
	
	public Double getTotalGraceMark() {
		return totalGraceMark;
	}

	public void setTotalGraceMark(Double totalGraceMark) {
		this.totalGraceMark = totalGraceMark;
	}

	public Double getTotalSubMark() {
		return totalSubMark;
	}

	public void setTotalSubMark(Double totalSubMark) {
		this.totalSubMark = totalSubMark;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetAll(){
		this.registerNumber=null;
		this.remark=null;
		this.appliedYear=null;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public boolean isInitialPage() {
		return initialPage;
	}
	public void setInitialPage(boolean initialPage) {
		this.initialPage = initialPage;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getAppliedYear() {
		return appliedYear;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setGracingIds(Set<Integer> gracingIds) {
		this.gracingIds = gracingIds;
	}

	public Set<Integer> getGracingIds() {
		return gracingIds;
	}

	public void setOnlyPass(boolean onlyPass) {
		this.onlyPass = onlyPass;
	}

	public boolean getOnlyPass() {
		return onlyPass;
	}
	

}
