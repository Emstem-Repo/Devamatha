package com.kp.cms.to.fee;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Transfer object class for FeePayment Page.
 */
public class FeePaymentTO implements Serializable{

	private int id;
	private String applicationNo;
	private String registrationNo;
	private String billNo;
	private BigDecimal totalFeePaid;
	private String challenPrintedDate;
	private Boolean isFeePaid;
	private Boolean isChallanCanceled;
	private String conversionRate;
	private String studentName;
	private String cancelReason;
	private String className;
	private String currencyCode;
	private String studentId;
	private Boolean paymentVerified;

	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the applicationNo
	 */
	public String getApplicationNo() {
		return applicationNo;
	}
	/**
	 * @param applicationNo the applicationNo to set
	 */
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	/**
	 * @return the registrationNo
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}
	/**
	 * @param registrationNo the registrationNo to set
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the totalFeePaid
	 */
	public BigDecimal getTotalFeePaid() {
		return totalFeePaid;
	}
	/**
	 * @param totalFeePaid the totalFeePaid to set
	 */
	public void setTotalFeePaid(BigDecimal totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}
	
	/**
	 * @return the isFeePaid
	 */
	public Boolean getIsFeePaid() {
		return isFeePaid;
	}
	/**
	 * @param isFeePaid the isFeePaid to set
	 */
	public void setIsFeePaid(Boolean isFeePaid) {
		this.isFeePaid = isFeePaid;
	}
	/**
	 * @return the challenPrintedDate
	 */
	public String getChallenPrintedDate() {
		return challenPrintedDate;
	}
	/**
	 * @param challenPrintedDate the challenPrintedDate to set
	 */
	public void setChallenPrintedDate(String challenPrintedDate) {
		this.challenPrintedDate = challenPrintedDate;
	}
	/**
	 * @return the isChallanCanceled
	 */
	public Boolean getIsChallanCanceled() {
		return isChallanCanceled;
	}
	/**
	 * @param isChallanCanceled the isChallanCanceled to set
	 */
	public void setIsChallanCanceled(Boolean isChallanCanceled) {
		this.isChallanCanceled = isChallanCanceled;
	}
	public String getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(String conversionRate) {
		this.conversionRate = conversionRate;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Boolean getPaymentVerified() {
		return paymentVerified;
	}
	public void setPaymentVerified(Boolean paymentVerified) {
		this.paymentVerified = paymentVerified;
	}
	
	
}
