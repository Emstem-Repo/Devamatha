package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.OpenSyllabusEntryTo;

public class OpenSyllabusEntryForm extends BaseActionForm{
	private int id;
	private String startDate;
	private String endDate;
	private String batch;
	private String tempBatch;
	private List<OpenSyllabusEntryTo> list;
	private String tempStartDate;
	private String tempEndDate;
	
	
	
	public String getTempStartDate() {
		return tempStartDate;
	}
	public void setTempStartDate(String tempStartDate) {
		this.tempStartDate = tempStartDate;
	}
	public String getTempEndDate() {
		return tempEndDate;
	}
	public void setTempEndDate(String tempEndDate) {
		this.tempEndDate = tempEndDate;
	}
	public List<OpenSyllabusEntryTo> getList() {
		return list;
	}
	public void setList(List<OpenSyllabusEntryTo> list) {
		this.list = list;
	}
	public String getTempBatch() {
		return tempBatch;
	}
	public void setTempBatch(String tempBatch) {
		this.tempBatch = tempBatch;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
