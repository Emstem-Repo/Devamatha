package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ReligionSection;

public class RegularExamFees implements Serializable {
	
	private int id;
	private Classes classes;
	private BigDecimal theoryFees;
	private BigDecimal practicalFees;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal applicationFees;
	private BigDecimal cvCampFees;
	private BigDecimal marksListFees;
	private BigDecimal onlineServiceChargeFees;
	private ReligionSection religionSection;
	private String academicYear;
	private BigDecimal examFees;
	
	public RegularExamFees() {
		super();
	}
	
	public RegularExamFees(int id, Program program, BigDecimal theoryFees,
			BigDecimal practicalFees, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, Boolean isActive,Classes classes) {
		this.id = id;
		this.classes = classes;
		this.theoryFees = theoryFees;
		this.practicalFees = practicalFees;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getTheoryFees() {
		return theoryFees;
	}
	public void setTheoryFees(BigDecimal theoryFees) {
		this.theoryFees = theoryFees;
	}
	public BigDecimal getPracticalFees() {
		return practicalFees;
	}
	public void setPracticalFees(BigDecimal practicalFees) {
		this.practicalFees = practicalFees;
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
	
	public BigDecimal getApplicationFees() {
		return applicationFees;
	}

	public void setApplicationFees(BigDecimal applicationFees) {
		this.applicationFees = applicationFees;
	}

	public BigDecimal getCvCampFees() {
		return cvCampFees;
	}

	public void setCvCampFees(BigDecimal cvCampFees) {
		this.cvCampFees = cvCampFees;
	}

	public BigDecimal getMarksListFees() {
		return marksListFees;
	}

	public void setMarksListFees(BigDecimal marksListFees) {
		this.marksListFees = marksListFees;
	}

	public BigDecimal getOnlineServiceChargeFees() {
		return onlineServiceChargeFees;
	}

	public void setOnlineServiceChargeFees(BigDecimal onlineServiceChargeFees) {
		this.onlineServiceChargeFees = onlineServiceChargeFees;
	}
	

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public ReligionSection getReligionSection() {
		return religionSection;
	}

	public void setReligionSection(ReligionSection religionSection) {
		this.religionSection = religionSection;
	}

	public BigDecimal getExamFees() {
		return examFees;
	}

	public void setExamFees(BigDecimal examFees) {
		this.examFees = examFees;
	}
	
	

	
}