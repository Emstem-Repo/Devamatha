package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class PrintShortageAttendanceForm extends BaseActionForm {
	private List<ProgramTypeTO> programTypeList;
	private String percentageFrom;
	private String percentageTo;
	private List<String> messageList;
	private boolean print;
	private String startDate;
	private String endDate;
	private String regNoFrom;
	private String regNoTo;
	private String titleFather;
	private String titleMother;
	private String commSentTo;

	
	public String getRegNoFrom() {
		return regNoFrom;
	}

	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}

	public String getRegNoTo() {
		return regNoTo;
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
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
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

	public String getPercentageTo() {
		return percentageTo;
	}

	public void setPercentageTo(String percentageTo) {
		this.percentageTo = percentageTo;
	}

	public String getPercentageFrom() {
		return percentageFrom;
	}

	public void setPercentageFrom(String percentageFrom) {
		this.percentageFrom = percentageFrom;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.percentageFrom=null;
		this.percentageTo=null;
		this.startDate=null;
		this.endDate=null;
		this.regNoFrom=null;
		this.regNoTo=null;
	}

	public String getTitleFather() {
		return titleFather;
	}

	public void setTitleFather(String titleFather) {
		this.titleFather = titleFather;
	}

	public String getTitleMother() {
		return titleMother;
	}

	public void setTitleMother(String titleMother) {
		this.titleMother = titleMother;
	}

	public String getCommSentTo() {
		return commSentTo;
	}

	public void setCommSentTo(String commSentTo) {
		this.commSentTo = commSentTo;
	}
}
