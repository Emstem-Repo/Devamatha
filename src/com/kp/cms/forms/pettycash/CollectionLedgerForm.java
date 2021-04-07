package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.CollectionLedgerTO;
import com.kp.cms.to.pettycash.PcAccountHeadsTO;

public class CollectionLedgerForm extends BaseActionForm {
	private String account;
	private String accountName;
	private String accountCode;
	private String startDate;
	private String endDate;
	private List<PcAccountHeadsTO> accountList;
	private String accountId;
	private String userType;
	private String otherName;
	private String groupCode;
	private double totalAmount;
	private String msg;
	private String downloadExcel;
    private String mode;
    private List<CollectionLedgerTO> collectionList;
		
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
	public List<PcAccountHeadsTO> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<PcAccountHeadsTO> accountList) {
		this.accountList = accountList;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void resetFields() {
		this.account = null;
		this.accountName = null;
		this.accountCode = null;
		this.startDate = null;
		this.endDate = null;
		this.accountId=null;
		this.otherName=null;
		this.groupCode=null;
		this.userType=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
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
	public List<CollectionLedgerTO> getCollectionList() {
		return collectionList;
	}
	public void setCollectionList(List<CollectionLedgerTO> collectionList) {
		this.collectionList = collectionList;
	}

	
}
