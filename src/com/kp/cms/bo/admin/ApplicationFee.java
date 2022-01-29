package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class ApplicationFee implements Serializable
{
	private ReligionSection religionSection;
	private ProgramType programType;
	private int id;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String accademicYear;
	private boolean isActive;
	private String amount;
	
	
	
	public ApplicationFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ApplicationFee(ReligionSection religionSection,
			ProgramType programType, int id, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			String accademicYear, boolean isActive, String amount) {
		super();
		this.religionSection = religionSection;
		this.programType = programType;
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.accademicYear = accademicYear;
		this.isActive = isActive;
		this.amount = amount;
	}


	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public ProgramType getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	
	public ReligionSection getReligionSection() {
		return religionSection;
	}
	public void setReligionSection(ReligionSection religionSection) {
		this.religionSection = religionSection;
	}
	
	public String getAccademicYear() {
		return accademicYear;
	}
	public void setAccademicYear(String accademicYear) {
		this.accademicYear = accademicYear;
	}

	

}
