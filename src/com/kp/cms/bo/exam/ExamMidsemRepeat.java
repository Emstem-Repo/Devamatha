package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ExamMidsemRepeat implements java.io.Serializable{
	
	private int id;
	private Student studentId;
	private Classes classId;
	private ExamDefinitionBO midsemExamId;
	private ExamDefinitionBO examId;
	private OnlinePaymentReciepts onlinePaymentReceipt;
	private BigDecimal totalAmount;
	private Boolean isDownload;
	private Boolean isFeePaid;
	private Boolean isPaymentFail;
	private Boolean isApplyOnline;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String reason;
	private String aggregatePercentage;
	private Boolean isFeeExempted;
	private String feeDescription;
	private Set<ExamMidsemRepeatDetails> examMidsemRepeatDetails=new HashSet<ExamMidsemRepeatDetails>(0);
	
	public ExamMidsemRepeat(){
	}
	
	public ExamMidsemRepeat(int id, Student studentId, Classes classId, ExamDefinitionBO midsemExamId, 
			ExamDefinitionBO examId, OnlinePaymentReciepts onlinePaymentReceipt,
			Boolean isPaymentFail, Boolean isApplyOnline, BigDecimal totalAmount,
			Boolean isDownload, Boolean isFeePaid, 
			String createdBy, Date createdDate, Boolean isActive,
			Set<ExamMidsemRepeatDetails> examMidsemRepeatDetails,
			String aggregatePercentage,String reason, String modifiedBy,
			Date lastModifiedDate, Boolean isFeeExempted, String feeDescription){
		super();
		this.id = id;
		this.studentId=studentId;
		this.classId=classId;
		this.midsemExamId=midsemExamId;
		this.examId=examId;
		this.totalAmount=totalAmount;
		this.isDownload=isDownload;
		this.isFeePaid=isFeePaid;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.isActive=isActive;
		this.examMidsemRepeatDetails=examMidsemRepeatDetails;
		this.onlinePaymentReceipt=onlinePaymentReceipt;
		this.isPaymentFail=isPaymentFail;
		this.isApplyOnline=isApplyOnline;
		this.reason=reason;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isFeeExempted=isFeeExempted;
		this.feeDescription=feeDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Classes getClassId() {
		return classId;
	}

	public void setClassId(Classes classId) {
		this.classId = classId;
	}

	public ExamDefinitionBO getMidsemExamId() {
		return midsemExamId;
	}

	public void setMidsemExamId(ExamDefinitionBO midsemExamId) {
		this.midsemExamId = midsemExamId;
	}

	public ExamDefinitionBO getExamId() {
		return examId;
	}

	public void setExamId(ExamDefinitionBO examId) {
		this.examId = examId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Boolean getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Boolean isDownload) {
		this.isDownload = isDownload;
	}

	public Boolean getIsFeePaid() {
		return isFeePaid;
	}

	public void setIsFeePaid(Boolean isFeePaid) {
		this.isFeePaid = isFeePaid;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<ExamMidsemRepeatDetails> getExamMidsemRepeatDetails() {
		return examMidsemRepeatDetails;
	}

	public void setExamMidsemRepeatDetails(
			Set<ExamMidsemRepeatDetails> examMidsemRepeatDetails) {
		this.examMidsemRepeatDetails = examMidsemRepeatDetails;
	}

	public OnlinePaymentReciepts getOnlinePaymentReceipt() {
		return onlinePaymentReceipt;
	}

	public void setOnlinePaymentReceipt(OnlinePaymentReciepts onlinePaymentReceipt) {
		this.onlinePaymentReceipt = onlinePaymentReceipt;
	}

	public Boolean getIsPaymentFail() {
		return isPaymentFail;
	}

	public void setIsPaymentFail(Boolean isPaymentFail) {
		this.isPaymentFail = isPaymentFail;
	}

	public Boolean getIsApplyOnline() {
		return isApplyOnline;
	}

	public void setIsApplyOnline(Boolean isApplyOnline) {
		this.isApplyOnline = isApplyOnline;
	}

	public String getAggregatePercentage() {
		return aggregatePercentage;
	}

	public void setAggregatePercentage(String aggregatePercentage) {
		this.aggregatePercentage = aggregatePercentage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsFeeExempted() {
		return isFeeExempted;
	}

	public void setIsFeeExempted(Boolean isFeeExempted) {
		this.isFeeExempted = isFeeExempted;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	

}
