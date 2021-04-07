package com.kp.cms.to.fee;

import java.util.List;


/**
 * @author kalyan.c
 *
 */
public class FeeReportTO {

	private String applicationNo;
	private String registrationNo;

	private String billNo;
	private String status;
	private String challenNo;
	private String totalAmount;
	private String totalFeePaid;
	private String isChallenPrinted;
	private String challenPrintedDate;
	private String isFeePaid;
	private String feePaidDate;
	private String isCompletlyPaid;
    private String paymentMode;
    private String consessionReferenceNo;
    private String installmentReferenceNo;
    private String installmentDate;
    private String totalConcessionAmount;
    private String totalInstallmentAmount;
    private String totalBalanceAmount;
    private List concessionAmount;
    private List instalmentAmount;
    private List amountPaid;
    private List amountBalance;
    private List excessShortAmount;
    
    
    
	private String feeAccount[];    
    
	private List feeAccountList;    
	private List feeAccountTotalAmountList;
	
	
	
	public List getConcessionAmount() {
		return concessionAmount;
	}
	public void setConcessionAmount(List concessionAmount) {
		this.concessionAmount = concessionAmount;
	}
	public List getInstalmentAmount() {
		return instalmentAmount;
	}
	public void setInstalmentAmount(List instalmentAmount) {
		this.instalmentAmount = instalmentAmount;
	}
	public List getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(List amountPaid) {
		this.amountPaid = amountPaid;
	}
	public List getAmountBalance() {
		return amountBalance;
	}
	public void setAmountBalance(List amountBalance) {
		this.amountBalance = amountBalance;
	}
	public List getExcessShortAmount() {
		return excessShortAmount;
	}
	public void setExcessShortAmount(List excessShortAmount) {
		this.excessShortAmount = excessShortAmount;
	}
	public List getFeeAccountTotalAmountList() {
		return feeAccountTotalAmountList;
	}
	public void setFeeAccountTotalAmountList(List feeAccountTotalAmountList) {
		this.feeAccountTotalAmountList = feeAccountTotalAmountList;
	}
	public List getFeeAccountList() {
		return feeAccountList;
	}
	public void setFeeAccountList(List feeAccountList) {
		this.feeAccountList = feeAccountList;
	}
	public String[] getFeeAccount() {
		return feeAccount;
	}
	public void setFeeAccount(String[] feeAccount) {
		this.feeAccount = feeAccount;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getChallenNo() {
		return challenNo;
	}
	public void setChallenNo(String challenNo) {
		this.challenNo = challenNo;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalFeePaid() {
		return totalFeePaid;
	}
	public void setTotalFeePaid(String totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}
	public String getIsChallenPrinted() {
		return isChallenPrinted;
	}
	public void setIsChallenPrinted(String isChallenPrinted) {
		this.isChallenPrinted = isChallenPrinted;
	}
	public String getChallenPrintedDate() {
		return challenPrintedDate;
	}
	public void setChallenPrintedDate(String challenPrintedDate) {
		this.challenPrintedDate = challenPrintedDate;
	}
	public String getIsFeePaid() {
		return isFeePaid;
	}
	public void setIsFeePaid(String isFeePaid) {
		this.isFeePaid = isFeePaid;
	}
	public String getFeePaidDate() {
		return feePaidDate;
	}
	public void setFeePaidDate(String feePaidDate) {
		this.feePaidDate = feePaidDate;
	}
	public String getIsCompletlyPaid() {
		return isCompletlyPaid;
	}
	public void setIsCompletlyPaid(String isCompletlyPaid) {
		this.isCompletlyPaid = isCompletlyPaid;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getConsessionReferenceNo() {
		return consessionReferenceNo;
	}
	public void setConsessionReferenceNo(String consessionReferenceNo) {
		this.consessionReferenceNo = consessionReferenceNo;
	}
	public String getInstallmentReferenceNo() {
		return installmentReferenceNo;
	}
	public void setInstallmentReferenceNo(String installmentReferenceNo) {
		this.installmentReferenceNo = installmentReferenceNo;
	}
	public String getInstallmentDate() {
		return installmentDate;
	}
	public void setInstallmentDate(String installmentDate) {
		this.installmentDate = installmentDate;
	}
	public String getTotalConcessionAmount() {
		return totalConcessionAmount;
	}
	public void setTotalConcessionAmount(String totalConcessionAmount) {
		this.totalConcessionAmount = totalConcessionAmount;
	}
	public String getTotalInstallmentAmount() {
		return totalInstallmentAmount;
	}
	public void setTotalInstallmentAmount(String totalInstallmentAmount) {
		this.totalInstallmentAmount = totalInstallmentAmount;
	}
	public String getTotalBalanceAmount() {
		return totalBalanceAmount;
	}
	public void setTotalBalanceAmount(String totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    
	
}
