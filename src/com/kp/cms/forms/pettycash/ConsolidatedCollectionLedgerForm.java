package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.ConsolidatedCollectionLedgerTO;

public class ConsolidatedCollectionLedgerForm extends BaseActionForm {
	private String startDate;
	private String endDate;
	private String userType;
	private String otherName;
	private Double totalAmount;
	private String msg;
    private List<ConsolidatedCollectionLedgerTO> consolidatedList;
    private String downloadExcel;
    private String mode;
	
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void resetFields() {
		this.startDate = null;
		this.endDate = null;
		this.otherName=null;
		this.userType=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<ConsolidatedCollectionLedgerTO> getConsolidatedList() {
		return consolidatedList;
	}
	public void setConsolidatedList(
			List<ConsolidatedCollectionLedgerTO> consolidatedList) {
		this.consolidatedList = consolidatedList;
	}
	public String getDownloadExcel() {
		return downloadExcel;
	}
	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	
}
