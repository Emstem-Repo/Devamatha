package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Users;

public class ExamValuationRemuneration implements Serializable{
	
	private int id;
	private ExamDefinition exam;
	private Users users;
	private ExamValuators examValuators;
	private Date startDate;
	private Date endDate;
	private int boardMeetings;
	private int conveyanceCharges;
	private BigDecimal ta;
	private BigDecimal da;
	private int billNumber;
	private String anyOther;
	private BigDecimal anyOtherCost;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamRemunerationDetails> examRemunerationDetails;
	private Boolean isPaid;
	private String modeOfPayment;
	
	public ExamValuationRemuneration(){
		
	}
	
	public ExamValuationRemuneration(int id, ExamDefinition exam, Users users,
			ExamValuators examValuators, Date startDate, Date endDate,
			int boardMeetings, int conveyanceCharges, BigDecimal ta,
			BigDecimal da, int billNumber, String anyOther,
			BigDecimal anyOtherCost, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, Boolean isActive,Set<ExamRemunerationDetails> examRemunerationDetails,Boolean isPaid,String modeOfPayment) {
		this.id = id;
		this.exam = exam;
		this.users = users;
		this.examValuators = examValuators;
		this.startDate = startDate;
		this.endDate = endDate;
		this.boardMeetings = boardMeetings;
		this.conveyanceCharges = conveyanceCharges;
		this.ta = ta;
		this.da = da;
		this.billNumber = billNumber;
		this.anyOther = anyOther;
		this.anyOtherCost = anyOtherCost;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.examRemunerationDetails=examRemunerationDetails;
		this.isPaid=isPaid;
		this.modeOfPayment=modeOfPayment;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public ExamValuators getExamValuators() {
		return examValuators;
	}

	public void setExamValuators(ExamValuators examValuators) {
		this.examValuators = examValuators;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getBoardMeetings() {
		return boardMeetings;
	}

	public void setBoardMeetings(int boardMeetings) {
		this.boardMeetings = boardMeetings;
	}

	public int getConveyanceCharges() {
		return conveyanceCharges;
	}

	public void setConveyanceCharges(int conveyanceCharges) {
		this.conveyanceCharges = conveyanceCharges;
	}

	public BigDecimal getTa() {
		return ta;
	}

	public void setTa(BigDecimal ta) {
		this.ta = ta;
	}

	public BigDecimal getDa() {
		return da;
	}

	public void setDa(BigDecimal da) {
		this.da = da;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public String getAnyOther() {
		return anyOther;
	}

	public void setAnyOther(String anyOther) {
		this.anyOther = anyOther;
	}

	public BigDecimal getAnyOtherCost() {
		return anyOtherCost;
	}

	public void setAnyOtherCost(BigDecimal anyOtherCost) {
		this.anyOtherCost = anyOtherCost;
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

	public Set<ExamRemunerationDetails> getExamRemunerationDetails() {
		return examRemunerationDetails;
	}

	public void setExamRemunerationDetails(
			Set<ExamRemunerationDetails> examRemunerationDetails) {
		this.examRemunerationDetails = examRemunerationDetails;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	
	
}
