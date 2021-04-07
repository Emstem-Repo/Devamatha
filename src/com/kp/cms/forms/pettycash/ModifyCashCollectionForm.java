package com.kp.cms.forms.pettycash;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;

public class ModifyCashCollectionForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String recNumber;
	private String recNoResult;
	private int recId;
	private String paidDate;
	private String hours;
	private String minutes;
	private String appRegRollno;
	private String appNo;
	private String academicYear;
	private String nameOfStudent;
	private String fineType;
	private String accountId;
	private List<CashCollectionTO> accNameWithCode;
	private String isfixed;
	private String amount;
	private List<CashCollectionTO> accountList;
	private String total;
	private List<CashCollectionTO> accountHeadList;
	//added for change
	private List<AccountHeadTO> accountHeadListToDisplay;
	private Map<Integer,AccountHeadTO> accountHeadMap;
	private int pcReceiptId;
	// for printing the receipt
	private String refNo;
	private String reftype;
	private String printReceipt;
	private String accCode;
	private String accName;
	private String userName;
	private String time;
	private String rupeesInWords;
	private String flag;
	private byte[] logo;
	private Set<Integer> accIDSet;
	private Set<Integer> idSet;
	private List<AccountHeadTO>originalAccountHeadList;
	private String details;
	private int financialYearId;
	private String finYearId;
	
	public int getPcReceiptId() {
		return pcReceiptId;
	}

	public void setPcReceiptId(int pcReceiptId) {
		this.pcReceiptId = pcReceiptId;
	}


	private Map<Integer, CashCollectionTO> accountMap;
	

	public List<AccountHeadTO> getOriginalAccountHeadList() {
		return originalAccountHeadList;
	}

	public void setOriginalAccountHeadList(
			List<AccountHeadTO> originalAccountHeadList) {
		this.originalAccountHeadList = originalAccountHeadList;
	}

	public List<AccountHeadTO> getAccountHeadListToDisplay() {
		return accountHeadListToDisplay;
	}

	public void setAccountHeadListToDisplay(
			List<AccountHeadTO> accountHeadListToDisplay) {
		this.accountHeadListToDisplay = accountHeadListToDisplay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}



	public String getRecNoResult() {
		return recNoResult;
	}

	public void setRecNoResult(String recNoResult) {
		this.recNoResult = recNoResult;
	}

	

	public String getRecNumber() {
		return recNumber;
	}

	public void setRecNumber(String recNumber) {
		this.recNumber = recNumber;
	}
	
	

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	

	public String getAppRegRollno() {
		return appRegRollno;
	}

	public void setAppRegRollno(String appRegRollno) {
		this.appRegRollno = appRegRollno;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getNameOfStudent() {
		return nameOfStudent;
	}

	public void setNameOfStudent(String nameOfStudent) {
		this.nameOfStudent = nameOfStudent;
	}

	public String getFineType() {
		return fineType;
	}

	public void setFineType(String fineType) {
		this.fineType = fineType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<CashCollectionTO> getAccNameWithCode() {
		return accNameWithCode;
	}

	public void setAccNameWithCode(List<CashCollectionTO> accNameWithCode) {
		this.accNameWithCode = accNameWithCode;
	}

	public String getIsfixed() {
		return isfixed;
	}

	public void setIsfixed(String isfixed) {
		this.isfixed = isfixed;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<CashCollectionTO> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<CashCollectionTO> accountList) {
		this.accountList = accountList;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	

	public List<CashCollectionTO> getAccountHeadList() {
		return accountHeadList;
	}

	public void setAccountHeadList(List<CashCollectionTO> accountHeadList) {
		this.accountHeadList = accountHeadList;
	}

	public Map<Integer, CashCollectionTO> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<Integer, CashCollectionTO> accountMap) {
		this.accountMap = accountMap;
	}
	
	
	public Set<Integer> getAccIDSet() {
		return accIDSet;
	}

	public void setAccIDSet(Set<Integer> accIDSet) {
		this.accIDSet = accIDSet;
	}
	

	public Set<Integer> getIdSet() {
		return idSet;
	}

	public void setIdSet(Set<Integer> idSet) {
		this.idSet = idSet;
	}
	

	public Map<Integer, AccountHeadTO> getAccountHeadMap() {
		return accountHeadMap;
	}

	public void setAccountHeadMap(Map<Integer, AccountHeadTO> accountHeadMap) {
		this.accountHeadMap = accountHeadMap;
	}

	public int getRecId() {
		return recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
	}
	

	public String getPrintReceipt() {
		return printReceipt;
	}

	public void setPrintReceipt(String printReceipt) {
		this.printReceipt = printReceipt;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getReftype() {
		return reftype;
	}

	public void setReftype(String reftype) {
		this.reftype = reftype;
	}

	public String getAccCode() {
		return accCode;
	}

	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRupeesInWords() {
		return rupeesInWords;
	}

	public void setRupeesInWords(String rupeesInWords) {
		this.rupeesInWords = rupeesInWords;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public int getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(int financialYearId) {
		this.financialYearId = financialYearId;
	}

	public String getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.recNumber=null;
		this.recNoResult=null;
		this.paidDate =null;
		this.hours=null;
		this.minutes = null;
		this.appRegRollno=null;
		this.appNo=null;
		this.academicYear=null;
		this.nameOfStudent=null;
		this.accountId=null;
		this.accNameWithCode=null;
		this.fineType=null;
		this.isfixed=null;;
		this.amount=null;
		this.accountList=null;
		this.accountHeadList=null;
		this.accountMap=null;
		this.accIDSet=null;
		this.total=null;
		this.logo=null;
	}
	public void reset() {
		this.recNumber=null;
		this.amount=null;
		this.accountId = null;
		//this.total=null;
		this.originalAccountHeadList=null;	
		this.logo=null;
		this.details=null;
		this.finYearId=null;
	}
	

}
