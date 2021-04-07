package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class DateOfBirthPrintForm extends BaseActionForm {
	
	private String regNoFrom;
	private String regNoTo;
	private List<String> messageList;	
	private String date;
	private String printPage;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	private String method;
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		regNoFrom = null;
		regNoTo = null;
		printPage = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	
}
