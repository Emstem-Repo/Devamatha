package com.kp.cms.to.fee;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.to.pettycash.AccountHeadTO;

public class InstallmentPaymentTO implements Serializable{
private String installMentAmount;
private int feePaymentId;
private List<FeePaidDateTO> feePaidDateTOList;
private List<AccountHeadTO> accountList;
private String referenceNo;
private String payMentTypeId;
private String financialYear;
private String totalAmount;
private FeePayment feePayment;
public FeePayment getFeePayment() {
	return feePayment;
}
public void setFeePayment(FeePayment feePayment) {
	this.feePayment = feePayment;
}
public String getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}
public String getFinancialYear() {
	return financialYear;
}
public void setFinancialYear(String financialYear) {
	this.financialYear = financialYear;
}
public String getPayMentTypeId() {
	return payMentTypeId;
}
public void setPayMentTypeId(String payMentTypeId) {
	this.payMentTypeId = payMentTypeId;
}
public String getReferenceNo() {
	return referenceNo;
}
public void setReferenceNo(String referenceNo) {
	this.referenceNo = referenceNo;
}
public List<AccountHeadTO> getAccountList() {
	return accountList;
}
public void setAccountList(List<AccountHeadTO> accountList) {
	this.accountList = accountList;
}
public List<FeePaidDateTO> getFeePaidDateTOList() {
	return feePaidDateTOList;
}
public void setFeePaidDateTOList(List<FeePaidDateTO> feePaidDateTOList) {
	this.feePaidDateTOList = feePaidDateTOList;
}
public String getInstallMentAmount() {
	return installMentAmount;
}
public int getFeePaymentId() {
	return feePaymentId;
}

public void setInstallMentAmount(String installMentAmount) {
	this.installMentAmount = installMentAmount;
}
public void setFeePaymentId(int feePaymentId) {
	this.feePaymentId = feePaymentId;
}

}
