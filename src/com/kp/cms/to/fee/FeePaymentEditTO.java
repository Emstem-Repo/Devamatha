package com.kp.cms.to.fee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FeePaymentEditTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int feePaymentId;
	private int feePaymentModeId;
	private String applnNo;
	private String studentName;
	private String courseName;
	private String admittedThrough;
	private String className;
	private String feeDivisionName;
	private String dateTime;
	private List<FeePaymentDetailEditTO> feePaymentDetailEditList;
	private boolean feePaid;
	private String feeBillNo;
	private String totalConcessionAmount;
	private String totalInstallmentAmount;
	private String totalScholarshipAmount;
	private String totalAmount;
	private String currencyCode;
	private Map<Integer,String> allFeeAccountMap ;
	private String concessionVoucherNo;
	private String installmentVoucherNo;
	private String scholarshipVoucherNo;
	private String chalanDate;
	
	public int getFeePaymentId() {
		return feePaymentId;
	}
	public void setFeePaymentId(int feePaymentId) {
		this.feePaymentId = feePaymentId;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getAdmittedThrough() {
		return admittedThrough;
	}
	public void setAdmittedThrough(String admittedThrough) {
		this.admittedThrough = admittedThrough;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFeeDivisionName() {
		return feeDivisionName;
	}
	public void setFeeDivisionName(String feeDivisionName) {
		this.feeDivisionName = feeDivisionName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getFeePaymentModeId() {
		return feePaymentModeId;
	}
	public void setFeePaymentModeId(int feePaymentModeId) {
		this.feePaymentModeId = feePaymentModeId;
	}
	public List<FeePaymentDetailEditTO> getFeePaymentDetailEditList() {
		return feePaymentDetailEditList;
	}
	public void setFeePaymentDetailEditList(
			List<FeePaymentDetailEditTO> feePaymentDetailEditList) {
		this.feePaymentDetailEditList = feePaymentDetailEditList;
	}
	public boolean isFeePaid() {
		return feePaid;
	}
	public void setFeePaid(boolean feePaid) {
		this.feePaid = feePaid;
	}
	public String getFeeBillNo() {
		return feeBillNo;
	}
	public void setFeeBillNo(String feeBillNo) {
		this.feeBillNo = feeBillNo;
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
	public String getTotalScholarshipAmount() {
		return totalScholarshipAmount;
	}
	public void setTotalScholarshipAmount(String totalScholarshipAmount) {
		this.totalScholarshipAmount = totalScholarshipAmount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Map<Integer, String> getAllFeeAccountMap() {
		return allFeeAccountMap;
	}
	public void setAllFeeAccountMap(Map<Integer, String> allFeeAccountMap) {
		this.allFeeAccountMap = allFeeAccountMap;
	}
	public String getConcessionVoucherNo() {
		return concessionVoucherNo;
	}
	public void setConcessionVoucherNo(String concessionVoucherNo) {
		this.concessionVoucherNo = concessionVoucherNo;
	}
	public String getInstallmentVoucherNo() {
		return installmentVoucherNo;
	}
	public void setInstallmentVoucherNo(String installmentVoucherNo) {
		this.installmentVoucherNo = installmentVoucherNo;
	}
	public String getScholarshipVoucherNo() {
		return scholarshipVoucherNo;
	}
	public void setScholarshipVoucherNo(String scholarshipVoucherNo) {
		this.scholarshipVoucherNo = scholarshipVoucherNo;
	}
	public String getChalanDate() {
		return chalanDate;
	}
	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}
	
}