package com.kp.cms.bo.admin;

import java.math.BigDecimal;

public class FeePaymentDetailAmount implements java.io.Serializable {

	private int id;
	private FeeHeading feeHeading;
	private BigDecimal amount;
	private FeeAccount feeAccount;
	private Integer semesterNo;
	private Boolean isOptional;
	private Student student;
	

	public FeePaymentDetailAmount() {
	}

	public FeePaymentDetailAmount(int id) {
		this.id = id;
	}

	public FeePaymentDetailAmount(int id,BigDecimal amount,
								FeeAccount feeAccount, Integer semesterNo,
								Boolean isOptional, FeeHeading feeHeading, Student student) {
		this.id = id;
		this.feeHeading = feeHeading;
		this.feeAccount = feeAccount;
		this.amount = amount;
		this.isOptional = isOptional;
		this.semesterNo = semesterNo;
		this.student = student;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FeeHeading getFeeHeading() {
		return feeHeading;
	}

	public void setFeeHeading(FeeHeading feeHeading) {
		this.feeHeading = feeHeading;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getIsOptional() {
		return this.isOptional;
	}

	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	public FeeAccount getFeeAccount() {
		return feeAccount;
	}

	public void setFeeAccount(FeeAccount feeAccount) {
		this.feeAccount = feeAccount;
	}

	public Integer getSemesterNo() {
		return semesterNo;
	}

	public void setSemesterNo(Integer semesterNo) {
		this.semesterNo = semesterNo;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
