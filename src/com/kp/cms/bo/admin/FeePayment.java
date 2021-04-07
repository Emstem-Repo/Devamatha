package com.kp.cms.bo.admin;

// Generated Feb 20, 2009 2:31:55 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeePayment generated by hbm2java
 */
public class FeePayment implements java.io.Serializable {

	private int id;
	private Student student;
	private Currency currency;
	private FeePaymentMode feePaymentMode;
	private String applicationNo;
	private String studentName;
	private String registrationNo;
	private String billNo;
	private String challenNo;
	private BigDecimal totalAmount;
	private BigDecimal totalFeePaid;
	private Boolean isChallenPrinted;
	private Date challenPrintedDate;
	private Boolean isFeePaid;
	private Date feePaidDate;
	private Boolean isCompletlyPaid;
    private BigDecimal totalConcessionAmount;
    private BigDecimal totalBalanceAmount;
    private Course course;
    private Integer academicYear;
    private Boolean isCancelChallan;
    private Date challanCreatedTime;
	private String concessionVoucherNo;
	private String rollNo;
	private String cancellationReason;
	private String manualClassName;
	private FeeFinancialYear amountFinancialYear;
	private Boolean paymentVerified;
	private String createdBy;
	private Date createdDate;
	private Classes classes;
	private String remarks;

	private Set<FeePaymentDetail> feePaymentDetails = new HashSet<FeePaymentDetail>(
			0);
	private Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses = new HashSet<FeePaymentApplicantDetails>(
			0);

	public FeePayment() {
	}

	public FeePayment(int id, Boolean isCompletlyPaid) {
		this.id = id;
		this.isCompletlyPaid = isCompletlyPaid;
	}

	public FeePayment(int id, String applicationNo, String registrationNo,
			String billNo, String challenNo, BigDecimal totalAmount,
			BigDecimal totalFeePaid, Boolean isChallenPrinted,
			Date challenPrintedDate, Boolean isFeePaid, Date feePaidDate,
			Boolean isCompletlyPaid, Set<FeePaymentDetail> feePaymentDetails,
			Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses,
			String paymentMode,String concessionVoucherNo,
			BigDecimal totalConcessionAmount,
			Course course,Integer academicYear,String studentName,
			Boolean isCancelChallan, Student student,String rollNo, String cancellationReason,
			Currency currency,String manualClassName, Boolean paymentVerified,String createdBy,Date createdDate,Classes classes,String remarks) {
		this.id = id;
		this.applicationNo = applicationNo;
		this.registrationNo = registrationNo;
		this.billNo = billNo;
		this.challenNo = challenNo;
		this.totalAmount = totalAmount;
		this.totalFeePaid = totalFeePaid;
		this.isChallenPrinted = isChallenPrinted;
		this.challenPrintedDate = challenPrintedDate;
		this.isFeePaid = isFeePaid;
		this.feePaidDate = feePaidDate;
		this.isCompletlyPaid = isCompletlyPaid;
		this.feePaymentDetails = feePaymentDetails;
		this.feePaymentApplicantDetailses = feePaymentApplicantDetailses;
		this.concessionVoucherNo = concessionVoucherNo;
		this.totalConcessionAmount = totalConcessionAmount;
		this.course = course;
		this.academicYear = academicYear;
		this.studentName = studentName;
		this.isCancelChallan = isCancelChallan;
		this.student = student;
		this.rollNo = rollNo;
		this.cancellationReason = cancellationReason;
		this.feePaymentMode = feePaymentMode;
		this.currency = currency;
		this.manualClassName = manualClassName;
		this.paymentVerified = paymentVerified;
		this.createdBy = createdBy;
		this .createdDate = createdDate;
		this.classes = classes;
		this.remarks = remarks;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegistrationNo() {
		return this.registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getChallenNo() {
		return this.challenNo;
	}

	public void setChallenNo(String challenNo) {
		this.challenNo = challenNo;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalFeePaid() {
		return this.totalFeePaid;
	}

	public void setTotalFeePaid(BigDecimal totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}

	public Boolean getIsChallenPrinted() {
		return this.isChallenPrinted;
	}

	public void setIsChallenPrinted(Boolean isChallenPrinted) {
		this.isChallenPrinted = isChallenPrinted;
	}

	public Date getChallenPrintedDate() {
		return this.challenPrintedDate;
	}

	public void setChallenPrintedDate(Date challenPrintedDate) {
		this.challenPrintedDate = challenPrintedDate;
	}

	public Boolean getIsFeePaid() {
		return this.isFeePaid;
	}

	public void setIsFeePaid(Boolean isFeePaid) {
		this.isFeePaid = isFeePaid;
	}

	public Date getFeePaidDate() {
		return this.feePaidDate;
	}

	public void setFeePaidDate(Date feePaidDate) {
		this.feePaidDate = feePaidDate;
	}

	public Boolean getIsCompletlyPaid() {
		return this.isCompletlyPaid;
	}

	public void setIsCompletlyPaid(Boolean isCompletlyPaid) {
		this.isCompletlyPaid = isCompletlyPaid;
	}

	public Set<FeePaymentDetail> getFeePaymentDetails() {
		return this.feePaymentDetails;
	}

	public void setFeePaymentDetails(Set<FeePaymentDetail> feePaymentDetails) {
		this.feePaymentDetails = feePaymentDetails;
	}

	public Set<FeePaymentApplicantDetails> getFeePaymentApplicantDetailses() {
		return this.feePaymentApplicantDetailses;
	}

	public void setFeePaymentApplicantDetailses(
			Set<FeePaymentApplicantDetails> feePaymentApplicantDetailses) {
		this.feePaymentApplicantDetailses = feePaymentApplicantDetailses;
	}

	/**
	 * @return the totalConcessionAmount
	 */
	public BigDecimal getTotalConcessionAmount() {
		return totalConcessionAmount;
	}

	/**
	 * @param totalConcessionAmount the totalConcessionAmount to set
	 */
	public void setTotalConcessionAmount(BigDecimal totalConcessionAmount) {
		this.totalConcessionAmount = totalConcessionAmount;
	}


	/**
	 * @return the totalBalanceAmount
	 */
	public BigDecimal getTotalBalanceAmount() {
		return totalBalanceAmount;
	}

	/**
	 * @param totalBalanceAmount the totalBalanceAmount to set
	 */
	public void setTotalBalanceAmount(BigDecimal totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}


	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the academicYear
	 */
	public Integer getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the isCancelChallan
	 */
	public Boolean getIsCancelChallan() {
		return isCancelChallan;
	}

	/**
	 * @param isCancelChallan the isCancelChallan to set
	 */
	public void setIsCancelChallan(Boolean isCancelChallan) {
		this.isCancelChallan = isCancelChallan;
	}

	public Date getChallanCreatedTime() {
		return challanCreatedTime;
	}

	public void setChallanCreatedTime(Date challanCreatedTime) {
		this.challanCreatedTime = challanCreatedTime;
	}

	public String getConcessionVoucherNo() {
		return concessionVoucherNo;
	}

	public void setConcessionVoucherNo(String concessionVoucherNo) {
		this.concessionVoucherNo = concessionVoucherNo;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
	public FeePaymentMode getFeePaymentMode() {
		return feePaymentMode;
	}

	public void setFeePaymentMode(FeePaymentMode feePaymentMode) {
		this.feePaymentMode = feePaymentMode;
	}

	public String getManualClassName() {
		return manualClassName;
	}

	public void setManualClassName(String manualClassName) {
		this.manualClassName = manualClassName;
	}

	public FeeFinancialYear getAmountFinancialYear() {
		return amountFinancialYear;
	}

	public void setAmountFinancialYear(FeeFinancialYear amountFinancialYear) {
		this.amountFinancialYear = amountFinancialYear;
	}

	public Boolean getPaymentVerified() {
		return paymentVerified;
	}

	public void setPaymentVerified(Boolean paymentVerified) {
		this.paymentVerified = paymentVerified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}