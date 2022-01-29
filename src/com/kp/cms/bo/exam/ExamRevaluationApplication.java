package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ExamRevaluationApplication implements Serializable {
	private int id;
	private ExamDefinition exam;
	private Student student;
	private Classes classes;
	private String ddNumber;
	private Date ddDate;
	private Double amount;
	private String bank;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String modifiedBy;
	private Set<ExamRevaluationAppDetails> examRevaluationAppDetails;
	private String branchName;
	
	public ExamRevaluationApplication() {
		super();
	}
	
	public ExamRevaluationApplication(int id, ExamDefinition exam,
			Student student, Classes classes, String ddNumber, Date ddDate,
			Double amount, String bank, Boolean isActive, Date createdDate,
			Date lastModifiedDate, String createdBy, String modifiedBy,String branchName) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.classes = classes;
		this.ddNumber = ddNumber;
		this.ddDate = ddDate;
		this.amount = amount;
		this.bank = bank;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.branchName=branchName;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinition getExam() {
		return exam;
	}
	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public String getDdNumber() {
		return ddNumber;
	}
	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}
	public Date getDdDate() {
		return ddDate;
	}
	public void setDdDate(Date ddDate) {
		this.ddDate = ddDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Set<ExamRevaluationAppDetails> getExamRevaluationAppDetails() {
		return examRevaluationAppDetails;
	}

	public void setExamRevaluationAppDetails(
			Set<ExamRevaluationAppDetails> examRevaluationAppDetails) {
		this.examRevaluationAppDetails = examRevaluationAppDetails;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}