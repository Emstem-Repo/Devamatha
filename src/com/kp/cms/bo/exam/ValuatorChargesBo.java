package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.ProgramType;

public class ValuatorChargesBo implements java.io.Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private ProgramType programType;
	private BigDecimal valuatorcharge;
	private BigDecimal reviewercharge;
	private BigDecimal projectevaluationminor;
	private BigDecimal projectevaluationmajor;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal boardMeetingCharge;
	private Integer minimumScripts;
	private BigDecimal minimumvaluatorcharge;
	private BigDecimal minimumreviewercharge;
	private BigDecimal minimumprojectevaluationminor;
	private BigDecimal minimumprojectevaluationmajor;
	
    public ValuatorChargesBo() {
		
	}
	
	
	
	public ValuatorChargesBo(int id, ProgramType programType,
			BigDecimal valuatorcharge, BigDecimal reviewercharge,
			BigDecimal projectevaluationminor,
			BigDecimal projectevaluationmajor, String createdby,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, BigDecimal boardMeetingCharge,
			int minimumScripts, BigDecimal minimumvaluatorcharge,
			BigDecimal minimumreviewercharge,
			BigDecimal minimumprojectevaluationminor,
			BigDecimal minimumprojectevaluationmajor) {
		this.id = id;
		this.programType = programType;
		this.valuatorcharge = valuatorcharge;
		this.reviewercharge = reviewercharge;
		this.projectevaluationminor = projectevaluationminor;
		this.projectevaluationmajor = projectevaluationmajor;
		this.createdby = createdby;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.boardMeetingCharge = boardMeetingCharge;
		this.minimumScripts = minimumScripts;
		this.minimumvaluatorcharge = minimumvaluatorcharge;
		this.minimumreviewercharge = minimumreviewercharge;
		this.minimumprojectevaluationminor = minimumprojectevaluationminor;
		this.minimumprojectevaluationmajor = minimumprojectevaluationmajor;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

	public BigDecimal getValuatorcharge() {
		return valuatorcharge;
	}

	public void setValuatorcharge(BigDecimal valuatorcharge) {
		this.valuatorcharge = valuatorcharge;
	}

	public BigDecimal getReviewercharge() {
		return reviewercharge;
	}

	public void setReviewercharge(BigDecimal reviewercharge) {
		this.reviewercharge = reviewercharge;
	}

	public BigDecimal getProjectevaluationminor() {
		return projectevaluationminor;
	}

	public void setProjectevaluationminor(BigDecimal projectevaluationminor) {
		this.projectevaluationminor = projectevaluationminor;
	}

	public BigDecimal getProjectevaluationmajor() {
		return projectevaluationmajor;
	}

	public void setProjectevaluationmajor(BigDecimal projectevaluationmajor) {
		this.projectevaluationmajor = projectevaluationmajor;
	}

	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public BigDecimal getBoardMeetingCharge() {
		return boardMeetingCharge;
	}

	public void setBoardMeetingCharge(BigDecimal boardMeetingCharge) {
		this.boardMeetingCharge = boardMeetingCharge;
	}



	public Integer getMinimumScripts() {
		return minimumScripts;
	}



	public void setMinimumScripts(Integer minimumScripts) {
		this.minimumScripts = minimumScripts;
	}



	public BigDecimal getMinimumvaluatorcharge() {
		return minimumvaluatorcharge;
	}



	public void setMinimumvaluatorcharge(BigDecimal minimumvaluatorcharge) {
		this.minimumvaluatorcharge = minimumvaluatorcharge;
	}



	public BigDecimal getMinimumreviewercharge() {
		return minimumreviewercharge;
	}



	public void setMinimumreviewercharge(BigDecimal minimumreviewercharge) {
		this.minimumreviewercharge = minimumreviewercharge;
	}



	public BigDecimal getMinimumprojectevaluationminor() {
		return minimumprojectevaluationminor;
	}



	public void setMinimumprojectevaluationminor(
			BigDecimal minimumprojectevaluationminor) {
		this.minimumprojectevaluationminor = minimumprojectevaluationminor;
	}



	public BigDecimal getMinimumprojectevaluationmajor() {
		return minimumprojectevaluationmajor;
	}



	public void setMinimumprojectevaluationmajor(
			BigDecimal minimumprojectevaluationmajor) {
		this.minimumprojectevaluationmajor = minimumprojectevaluationmajor;
	}
	
}
