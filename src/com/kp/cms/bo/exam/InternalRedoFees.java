package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ReligionSection;

public class InternalRedoFees {

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
	private ReligionSection religionSection;
	private String academicYear;
	private BigDecimal paperFees;
	private BigDecimal registrationFees;
	public InternalRedoFees(int id, Classes classes, BigDecimal theoryFees,
							BigDecimal practicalFees, String createdBy, String modifiedBy,
							Date createdDate, Date lastModifiedDate, Boolean isActive,
							BigDecimal applicationFees, BigDecimal cvCampFees,
							BigDecimal marksListFees, ReligionSection religionSection,
							String academicYear, BigDecimal paperFees,
							BigDecimal registrationFees) {
		super();
		this.id = id;
		this.classes = classes;
		this.theoryFees = theoryFees;
		this.practicalFees = practicalFees;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.applicationFees = applicationFees;
		this.cvCampFees = cvCampFees;
		this.marksListFees = marksListFees;
		this.religionSection = religionSection;
		this.academicYear = academicYear;
		this.paperFees = paperFees;
		this.registrationFees = registrationFees;
	}
	public InternalRedoFees() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
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
	public ReligionSection getReligionSection() {
		return religionSection;
	}
	public void setReligionSection(ReligionSection religionSection) {
		this.religionSection = religionSection;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public BigDecimal getPaperFees() {
		return paperFees;
	}
	public void setPaperFees(BigDecimal paperFees) {
		this.paperFees = paperFees;
	}
	public BigDecimal getRegistrationFees() {
		return registrationFees;
	}
	public void setRegistrationFees(BigDecimal registrationFees) {
		this.registrationFees = registrationFees;
	}
}
