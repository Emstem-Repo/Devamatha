package com.kp.cms.bo.admin;

import java.math.BigDecimal;
import java.util.Date;

public class StudentCancellationDetails implements java.io.Serializable {
	
	private int id;
	private AdmAppln admAppln;
	private String chequeNo;
	private Date chequeDate;
	private Date chequeIssuedDate;
	private BigDecimal repaidAmount;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public StudentCancellationDetails(int id, AdmAppln admAppln,
			String chequeNo, Date chequeDate, Date chequeIssuedDate,
			BigDecimal repaidAmount, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate) {
		super();
		this.id = id;
		this.admAppln = admAppln;
		this.chequeNo = chequeNo;
		this.chequeDate = chequeDate;
		this.chequeIssuedDate = chequeIssuedDate;
		this.repaidAmount = repaidAmount;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}
	public StudentCancellationDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	public Date getChequeIssuedDate() {
		return chequeIssuedDate;
	}
	public void setChequeIssuedDate(Date chequeIssuedDate) {
		this.chequeIssuedDate = chequeIssuedDate;
	}
	public BigDecimal getRepaidAmount() {
		return repaidAmount;
	}
	public void setRepaidAmount(BigDecimal repaidAmount) {
		this.repaidAmount = repaidAmount;
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
}
