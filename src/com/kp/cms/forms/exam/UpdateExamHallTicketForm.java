package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;

public class UpdateExamHallTicketForm extends BaseActionForm {
	
	private String endDate;
	private String toEndDate;
	private String toRevEndDate;
	private List<ExamPublishHallTicketTO> listTo;
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getToEndDate() {
		return toEndDate;
	}
	public void setToEndDate(String toEndDate) {
		this.toEndDate = toEndDate;
	}
	public List<ExamPublishHallTicketTO> getListTo() {
		return listTo;
	}
	public void setListTo(List<ExamPublishHallTicketTO> listTo) {
		this.listTo = listTo;
	}
	
	public void resetFields(){
		this.endDate=null;
		this.toEndDate=null;
		this.listTo=null;
		this.toRevEndDate=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getToRevEndDate() {
		return toRevEndDate;
	}
	public void setToRevEndDate(String toRevEndDate) {
		this.toRevEndDate = toRevEndDate;
	}
}
