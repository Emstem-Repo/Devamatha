package com.kp.cms.forms.pettycash;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.PettyCashCancelCashCollectionTO;
public class CashCollectionForm extends BaseActionForm {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String accId;
	

	private String number;
	private String paidDateTime;
	private String fineNumber;
	private String fineType;
	private String name;
	private String academicYear;
	private String studentId;
	private String accHeadId;
	private String finYearId;
	private String amount;
	private String isCancelled;
	private String cancelComments;
	private String isActive;
	
	private String accountId;
	private String accName;
	private String total;
	private String appRegRollno;
	private String paidDate;
	private String appNo;
	private String regNo;
	private String rollNo;
	public String isfixed;
	private String hour;
	private String minutes;
	private String userId;
	
	private Set<Integer> accIDSet;
	private List<CashCollectionTO> accountList;
	private String rupeesInWords;
	private String time;
	private List<CashCollectionTO> accountListforPrint;
	

	private String accountCode;
	
	//start for pettycash
	private String refNo;
	private String refType;
	private String paidTime;
	private BigDecimal totalAmount;
	private boolean refId1;
	private boolean refId2;
	private boolean refId3;
	private boolean refId4;
	private boolean refId5;
	private boolean refId6;
	//needed to print the receipt
	private String printReceipt;
	private String recNoToPrint;
	private String financialYearId;
	private List<FinancialYearTO> financilYearList;
	private String username;
	private String netAmount;
	private String currencyCode;
	private String flag;
	private byte[] logo;
	
	private PettyCashCancelCashCollectionTO pcCashCollectionTO;
	//added for pettyCash
	private List<PettyCashCancelCashCollectionTO> pcCashCollectionListTO=null;
	private String tempyear;
	private String oldRecNo;
	private String details;
	private String finYear;
	private String list;
	private String isPettyCash;
	
	public PettyCashCancelCashCollectionTO getPcCashCollectionTO() {
		return pcCashCollectionTO;
	}
	public void setPcCashCollectionTO(
			PettyCashCancelCashCollectionTO pcCashCollectionTO) {
		this.pcCashCollectionTO = pcCashCollectionTO;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getPaidTime() {
		return paidTime;
	}
	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public boolean isRefId1() {
		return refId1;
	}
	public void setRefId1(boolean refId1) {
		this.refId1 = refId1;
	}
	public boolean isRefId2() {
		return refId2;
	}
	public void setRefId2(boolean refId2) {
		this.refId2 = refId2;
	}
	public boolean isRefId3() {
		return refId3;
	}
	public void setRefId3(boolean refId3) {
		this.refId3 = refId3;
	}
	public boolean isRefId4() {
		return refId4;
	}
	public void setRefId4(boolean refId4) {
		this.refId4 = refId4;
	}
	public boolean isRefId5() {
		return refId5;
	}
	public void setRefId5(boolean refId5) {
		this.refId5 = refId5;
	}
	public boolean isRefId6() {
		return refId6;
	}
	public void setRefId6(boolean refId6) {
		this.refId6 = refId6;
	}
	public List<CashCollectionTO> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<CashCollectionTO> accountList) {
		this.accountList = accountList;
	}

	private Map<Integer, CashCollectionTO> accountMap;
	
	
	
	public Map<Integer, CashCollectionTO> getAccountMap() {
		return accountMap;
	}
	public void setAccountMap(Map<Integer, CashCollectionTO> accountMap) {
		this.accountMap = accountMap;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	private List<CashCollectionTO> accNameWithCode;
	
	private Set<String> accountIdSet;
	public Set<String> getAccountIdSet() {
		return accountIdSet;
	}
	public void setAccountIdSet(Set<String> accountIdSet) {
		this.accountIdSet = accountIdSet;
	}

	private List<AccountHeadTO> accountName;
	
	private List<FinancialYearTO> financialYearList;
	
	

	private List<CashCollectionTO> fineDetails;
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CashCollectionTO> getAccNameWithCode() {
		return accNameWithCode;
	}
	public void setAccNameWithCode(List<CashCollectionTO> accNameWithCode) {
		this.accNameWithCode = accNameWithCode;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getAppRegRollno() {
		return appRegRollno;
	}
	public void setAppRegRollno(String appRegRollno) {
		this.appRegRollno = appRegRollno;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}

	
	
	
	public List<CashCollectionTO> getFineDetails() {
		return fineDetails;
	}
	public void setFineDetails(List<CashCollectionTO> fineDetails) {
		this.fineDetails = fineDetails;
	}
	
	public List<AccountHeadTO> getAccountName() {
		return accountName;
	}
	public void setAccountName(List<AccountHeadTO> accountName) {
		this.accountName = accountName;
	}

	
	
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPaidDateTime() {
		return paidDateTime;
	}
	public void setPaidDateTime(String paidDateTime) {
		this.paidDateTime = paidDateTime;
	}
	
	public String getFineNumber() {
		return fineNumber;
	}
	public void setFineNumber(String fineNumber) {
		this.fineNumber = fineNumber;
	}
	public String getFineType() {
		return fineType;
	}
	public void setFineType(String fineType) {
		this.fineType = fineType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getAccHeadId() {
		return accHeadId;
	}
	public void setAccHeadId(String accHeadId) {
		this.accHeadId = accHeadId;
	}
	public String getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}
	public String getCancelComments() {
		return cancelComments;
	}
	public void setCancelComments(String cancelComments) {
		this.cancelComments = cancelComments;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<FinancialYearTO> getFinancialYearList() {
		return financialYearList;
	}
	public void setFinancialYearList(List<FinancialYearTO> financialYearList) {
		this.financialYearList = financialYearList;
	}
	
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getIsfixed() {
		return isfixed;
	}
	public void setIsfixed(String isfixed) {
		this.isfixed = isfixed;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Set<Integer> getAccIDSet() {
		return accIDSet;
	}
	public void setAccIDSet(Set<Integer> accIDSet) {
		this.accIDSet = accIDSet;
	}
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public List<PettyCashCancelCashCollectionTO> getPcCashCollectionListTO() {
		return pcCashCollectionListTO;
	}
	public void setPcCashCollectionListTO(
			List<PettyCashCancelCashCollectionTO> pcCashCollectionListTO) {
		this.pcCashCollectionListTO = pcCashCollectionListTO;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//getters and setters for print receipt
	
	public String getPrintReceipt() {
		return printReceipt;
	}
	public void setPrintReceipt(String printReceipt) {
		this.printReceipt = printReceipt;
	}
	public String getRecNoToPrint() {
		return recNoToPrint;
	}
	public void setRecNoToPrint(String recNoToPrint) {
		this.recNoToPrint = recNoToPrint;
	}
	public String getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(String financialYearId) {
		this.financialYearId = financialYearId;
	}
	public List<FinancialYearTO> getFinancilYearList() {
		return financilYearList;
	}
	public void setFinancilYearList(List<FinancialYearTO> financilYearList) {
		this.financilYearList = financilYearList;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getRupeesInWords() {
		return rupeesInWords;
	}
	public void setRupeesInWords(String rupeesInWords) {
		this.rupeesInWords = rupeesInWords;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<CashCollectionTO> getAccountListforPrint() {
		return accountListforPrint;
	}
	public void setAccountListforPrint(List<CashCollectionTO> accountListforPrint) {
		this.accountListforPrint = accountListforPrint;
	}
	public String getTempyear() {
		return tempyear;
	}
	public void setTempyear(String tempyear) {
		this.tempyear = tempyear;
	}
	public String getOldRecNo() {
		return oldRecNo;
	}
	public void setOldRecNo(String oldRecNo) {
		this.oldRecNo = oldRecNo;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getIsPettyCash() {
		return isPettyCash;
	}
	public void setIsPettyCash(String isPettyCash) {
		this.isPettyCash = isPettyCash;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void resetFields() {
		this.number = null;
		this.paidDateTime =null;
		this.academicYear=null;
		this.studentId=null;
		this.accHeadId=null;
		this.finYearId=null;
		this.amount=null;
		this.isCancelled=null;
		this.isActive=null;
		this.appNo="3";
		this.regNo=null;
		this.rollNo=null;
		this.accountId=null;
		this.isfixed=null;
		this.hour=null;
		this.minutes=null;
		this.logo=null;
		this.finYear=null;
		this.list=null;
		this.isPettyCash="PettyCash";
	}
	
	public void clear() {
		this.id=null;
		this.name=null;
		this.appRegRollno=null;
		this.accountIdSet=null;
		this.accountList=null;
		this.accountMap=null;
		this.total=null;
		this.accNameWithCode=null;
		this.accountId = null;
		this.tempyear=null;
		this.finYearId=null;
		this.oldRecNo=null;
		this.logo=null;
		this.details=null;
		this.list=null;
		this.isPettyCash="PettyCash";
		
	}

	public void clearAll() {
		this.id=null;
		this.name=null;
		this.appRegRollno=null;
		this.accountIdSet=null;
		this.accountList=null;
		this.accountMap=null;
		this.total=null;
		this.number=null;
		this.accNameWithCode=null;
		this.accountId = null;
		this.academicYear=null;
		this.logo=null;
		this.finYear=null;
		this.list=null;
		this.isPettyCash="PettyCash";
	
	}
	
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
}