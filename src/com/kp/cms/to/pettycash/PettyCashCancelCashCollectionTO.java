package com.kp.cms.to.pettycash;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.Student;


public class PettyCashCancelCashCollectionTO implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PcAccountHead pcAccountHead;
	private PcFinancialYear pcFinancialYear;
	private String finYearId;
	private Student student;
	private String number;
	private Date paidDate;
	private String refNo;
	private String refType;
	private String name;
	private Date academicYear;
	private Boolean isCancelled;
	private String cancelComments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Integer id;
	private String accCode;
	private String accName;
	private BigDecimal amount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public PcAccountHead getPcAccountHead() {
		return pcAccountHead;
	}
	public void setPcAccountHead(PcAccountHead pcAccountHead) {
		this.pcAccountHead = pcAccountHead;
	}
	public PcFinancialYear getPcFinancialYear() {
		return pcFinancialYear;
	}
	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Date academicYear) {
		this.academicYear = academicYear;
	}
	public Boolean getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public String getCancelComments() {
		return cancelComments;
	}
	public void setCancelComments(String cancelComments) {
		this.cancelComments = cancelComments;
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
	public String getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}

}
