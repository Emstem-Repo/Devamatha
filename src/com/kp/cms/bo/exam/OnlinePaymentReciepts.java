package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.Student;

/**
 * @author user
 *
 */
public class OnlinePaymentReciepts implements Serializable {
	
	private int id;
	private Date transactionDate;
	private String status;
	private String bankConfirmationId;
	private Boolean isPaymentFailed; 
	private BigDecimal totalAmount;
	private Student student;
	private Integer recieptNo;
	private String applicationFor;
	private PcFinancialYear pcFinancialYear;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public OnlinePaymentReciepts() {
	}
	/**
	 * @param id
	 * @param transactionDate
	 * @param status
	 * @param bankConfirmationId
	 * @param isPaymentFailed
	 * @param totalAmount
	 * @param student
	 * @param recieptNo
	 * @param applicationFor
	 */
	public OnlinePaymentReciepts(int id, Date transactionDate, String status,
			String bankConfirmationId, Boolean isPaymentFailed,
			BigDecimal totalAmount, Student student, Integer recieptNo,
			String applicationFor,PcFinancialYear pcFinancialYear) {
		this.id = id;
		this.transactionDate = transactionDate;
		this.status = status;
		this.bankConfirmationId = bankConfirmationId;
		this.isPaymentFailed = isPaymentFailed;
		this.totalAmount = totalAmount;
		this.student = student;
		this.recieptNo = recieptNo;
		this.applicationFor = applicationFor;
		this.pcFinancialYear=pcFinancialYear;
	}
	
	public int getId() {
		return id;
	}
	


	public void setId(int id) {
		this.id = id;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankConfirmationId() {
		return bankConfirmationId;
	}
	public void setBankConfirmationId(String bankConfirmationId) {
		this.bankConfirmationId = bankConfirmationId;
	}
	public Boolean getIsPaymentFailed() {
		return isPaymentFailed;
	}
	public void setIsPaymentFailed(Boolean isPaymentFailed) {
		this.isPaymentFailed = isPaymentFailed;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getRecieptNo() {
		return recieptNo;
	}
	public void setRecieptNo(Integer recieptNo) {
		this.recieptNo = recieptNo;
	}
	public String getApplicationFor() {
		return applicationFor;
	}
	public void setApplicationFor(String applicationFor) {
		this.applicationFor = applicationFor;
	}
	public PcFinancialYear getPcFinancialYear() {
		return pcFinancialYear;
	}
	public void setPcFinancialYear(PcFinancialYear pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}