package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExamDefinition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String examCode;
	private Integer academicYear;
	private ExamTypeUtilBO examType;
	private String month;
	private Integer year;
	private Integer examForJoiningBatch;
	private Integer maxNoFailedSub;
	private Boolean isCurrent;
	private ExamInternalExamTypeBO internalExamType;
	private Boolean delIsActive;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String modifiedBy;
	
	// 1-to-many
	private Set<CourseSchemeDetails> courseSchemeDetails;
	// 1-to-many
	private Set<ExamDefinitionProgram> definitionPrograms;
	
	public ExamDefinition() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExamDefinition(int id, String name, String examCode,
			Integer academicYear, ExamTypeUtilBO examType, String month,
			Integer year, Integer examForJoiningBatch, Integer maxNoFailedSub,
			boolean isCurrent, ExamInternalExamTypeBO internalExamType,
			boolean delIsActive, Boolean isActive, Date createdDate,
			Date lastModifiedDate, String createdBy, String modifiedBy,
			Set<CourseSchemeDetails> courseSchemeDetails,
			Set<ExamDefinitionProgram> definitionPrograms) {
		super();
		this.id = id;
		this.name = name;
		this.examCode = examCode;
		this.academicYear = academicYear;
		this.examType = examType;
		this.month = month;
		this.year = year;
		this.examForJoiningBatch = examForJoiningBatch;
		this.maxNoFailedSub = maxNoFailedSub;
		this.isCurrent = isCurrent;
		this.internalExamType = internalExamType;
		this.delIsActive = delIsActive;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.courseSchemeDetails = courseSchemeDetails;
		this.definitionPrograms = definitionPrograms;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getExamForJoiningBatch() {
		return examForJoiningBatch;
	}
	public void setExamForJoiningBatch(Integer examForJoiningBatch) {
		this.examForJoiningBatch = examForJoiningBatch;
	}
	public Integer getMaxNoFailedSub() {
		return maxNoFailedSub;
	}
	public void setMaxNoFailedSub(Integer maxNoFailedSub) {
		this.maxNoFailedSub = maxNoFailedSub;
	}
	public ExamInternalExamTypeBO getInternalExamType() {
		return internalExamType;
	}
	public void setInternalExamType(ExamInternalExamTypeBO internalExamType) {
		this.internalExamType = internalExamType;
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
	public Set<CourseSchemeDetails> getCourseSchemeDetails() {
		return courseSchemeDetails;
	}
	public void setCourseSchemeDetails(Set<CourseSchemeDetails> courseSchemeDetails) {
		this.courseSchemeDetails = courseSchemeDetails;
	}
	public Set<ExamDefinitionProgram> getDefinitionPrograms() {
		return definitionPrograms;
	}
	public void setDefinitionPrograms(Set<ExamDefinitionProgram> definitionPrograms) {
		this.definitionPrograms = definitionPrograms;
	}

	public ExamTypeUtilBO getExamType() {
		return examType;
	}

	public void setExamType(ExamTypeUtilBO examType) {
		this.examType = examType;
	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Boolean getDelIsActive() {
		return delIsActive;
	}

	public void setDelIsActive(Boolean delIsActive) {
		this.delIsActive = delIsActive;
	}
}
