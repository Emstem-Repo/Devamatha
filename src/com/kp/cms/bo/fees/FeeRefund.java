package com.kp.cms.bo.fees;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.Student;

public class FeeRefund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	private int id;
	private Student student;
	private Integer academicYear;
	private String challanNo;
	private Date challanDate;
	private BigDecimal challanAmount;
	private BigDecimal refundAmount;
	private Date refundDate;
	private FeePaymentMode refundMode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public FeeRefund() {
	}

	public FeeRefund(int id, Student student, Integer academicYear,
			String challanNo, Date challanDate, BigDecimal challanAmount,
			BigDecimal refundAmount, Date refundDate, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, FeePaymentMode refundMode) {
		super();
		this.id = id;
		this.student = student;
		this.academicYear = academicYear;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.challanAmount = challanAmount;
		this.refundAmount = refundAmount;
		this.refundDate = refundDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.refundMode = refundMode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public BigDecimal getChallanAmount() {
		return challanAmount;
	}

	public void setChallanAmount(BigDecimal challanAmount) {
		this.challanAmount = challanAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public FeePaymentMode getRefundMode() {
		return refundMode;
	}

	public void setRefundMode(FeePaymentMode refundMode) {
		this.refundMode = refundMode;
	}
	
}
