package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelStudentExtractTo;

public class HostelStudentExtractForm extends BaseActionForm{

	private String toDate;
	private String fromDate;
	List<HostelStudentExtractTo> hostelStudExtractToList;
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void clearMyFields() {
		this.toDate = null;
		this.fromDate = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public List<HostelStudentExtractTo> getHostelStudExtractToList() {
		return hostelStudExtractToList;
	}
	public void setHostelStudExtractToList(
			List<HostelStudentExtractTo> hostelStudExtractToList) {
		this.hostelStudExtractToList = hostelStudExtractToList;
	}
}
