package com.kp.cms.forms.pettycash;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.ConsolidatedCollectionReportTO;
import com.kp.cms.to.pettycash.PcAccountHeadsTO;
import com.kp.cms.to.pettycash.PcAccountTo;

public class ConsolidatedCollectionReportForm extends BaseActionForm{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private List<ProgramTypeTO> programTypeList;
	Map<String,Integer> accPositionMap;
	private List<PcAccountTo> accNolist;
	private String[] accountNumber;
	List<AccountHeadTO> totalAccountList;
	private List<ConsolidatedCollectionReportTO> selectedData;
	private List<Double> amountList;
	private String groupCodeValue;
	
	public List<ConsolidatedCollectionReportTO> getSelectedData() {
		return selectedData;
	}
	public void setSelectedData(List<ConsolidatedCollectionReportTO> selectedData) {
		this.selectedData = selectedData;
	}
	public List<AccountHeadTO> getTotalAccountList() {
		return totalAccountList;
	}
	public void setTotalAccountList(List<AccountHeadTO> totalAccountList) {
		this.totalAccountList = totalAccountList;
	}
	public Map<String, Integer> getAccPositionMap() {
		return accPositionMap;
	}
	public void setAccPositionMap(Map<String, Integer> accPositionMap) {
		this.accPositionMap = accPositionMap;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
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
	public List<PcAccountHeadsTO> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<PcAccountHeadsTO> accountList) {
		this.accountList = accountList;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String[] getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String[] accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<PcAccountTo> getAccNolist() {
		return accNolist;
	}
	public void setAccNolist(List<PcAccountTo> accNolist) {
		this.accNolist = accNolist;
	}
	public List<Double> getAmountList() {
		return amountList;
	}
	public void setAmountList(List<Double> amountList) {
		this.amountList = amountList;
	}
	public String getGroupCodeValue() {
		return groupCodeValue;
	}
	public void setGroupCodeValue(String groupCodeValue) {
		this.groupCodeValue = groupCodeValue;
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
		this.accountList=null;
		this.accountNumber=null;
		super.setProgramTypeId(null);
		this.groupCodeValue=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
