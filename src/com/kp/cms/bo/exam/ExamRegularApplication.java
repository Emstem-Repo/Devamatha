package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ExamRegularApplication implements Serializable {
	
	private int id;
	private ExamDefinitionBO exam;
	private Student student;
	private boolean isApplied;
	private Classes classes;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String challanNo;
	private boolean isChallanVerified;
	private BigDecimal amount;
	
	public ExamRegularApplication() {
		super();
	}
	
	public ExamRegularApplication(int id, ExamDefinitionBO exam,
			Student student, boolean isapplied, Classes classes,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.isApplied = isapplied;
		this.classes = classes;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinitionBO getExam() {
		return exam;
	}
	public void setExam(ExamDefinitionBO exam) {
		this.exam = exam;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public boolean getIsApplied() {
		return isApplied;
	}

	public void setIsApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public boolean getIsChallanVerified() {
		return isChallanVerified;
	}

	public void setIsChallanVerified(boolean isChallanVerified) {
		this.isChallanVerified = isChallanVerified;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
	

}
