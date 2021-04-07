package com.kp.cms.bo.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NewDocChecklist {
	
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private DocType docType;
	private Program program;
	private NewCourse course;
	private Date createdDate;
	private Date lastModifiedDate;
	private int year;
	private Boolean isMarksCard;
	private Boolean isConsolidatedMarks;
	private Boolean needToProduce;
	private Boolean isActive;
	private Boolean isDocSelected;
	private Boolean isSemesterWise;
	private Boolean isPreviousExam;
	private Boolean isIncludeLanguage;
	private Boolean isExamRequired;
	private Weightage weightage;
	private BigDecimal weightagePercentage;
	private Set<NewEdnQualification> ednQualifications = new HashSet<NewEdnQualification>(
			0);

	public NewDocChecklist() {
	}

	public NewDocChecklist(int id) {
		this.id = id;
	}

	public NewDocChecklist(int id, String createdBy,
			String modifiedBy, DocType docType, Program program, Weightage weightage,BigDecimal weightagePercentage,
			NewCourse course, Date createdDate, Date lastModifiedDate, int year,
			Boolean isMarksCard, Boolean isConsolidatedMarks,
			Boolean needToProduce, Boolean isActive, Boolean isDocSelected,
			Boolean isSemesterWise, Boolean isPreviousExam,Boolean isExamRequired, 
			Boolean isIncludeLanguage,
			Set<NewEdnQualification> ednQualifications) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.docType = docType;
		this.program = program;
		this.course = course;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.year = year;
		this.isMarksCard = isMarksCard;
		this.isConsolidatedMarks = isConsolidatedMarks;
		this.needToProduce = needToProduce;
		this.isActive = isActive;
		this.isDocSelected = isDocSelected;
		this.isSemesterWise = isSemesterWise;
		this.isPreviousExam = isPreviousExam;
		this.ednQualifications = ednQualifications;
		this.isIncludeLanguage = isIncludeLanguage;
		this.isExamRequired=isExamRequired;
	}

	public Boolean getIsSemesterWise() {
		return isSemesterWise;
	}

	public void setIsSemesterWise(Boolean isSemesterWise) {
		this.isSemesterWise = isSemesterWise;
	}

	public Boolean getIsPreviousExam() {
		return isPreviousExam;
	}

	public void setIsPreviousExam(Boolean isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}

	public Boolean getIsDocSelected() {
		return isDocSelected;
	}

	public void setIsDocSelected(Boolean isDocSelected) {
		this.isDocSelected = isDocSelected;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public DocType getDocType() {
		return this.docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public NewCourse getCourse() {
		return course;
	}

	public void setCourse(NewCourse course) {
		this.course = course;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Boolean getIsMarksCard() {
		return this.isMarksCard;
	}

	public void setIsMarksCard(Boolean isMarksCard) {
		this.isMarksCard = isMarksCard;
	}

	public Boolean getIsConsolidatedMarks() {
		return this.isConsolidatedMarks;
	}

	public void setIsConsolidatedMarks(Boolean isConsolidatedMarks) {
		this.isConsolidatedMarks = isConsolidatedMarks;
	}

	public Boolean getNeedToProduce() {
		return this.needToProduce;
	}

	public void setNeedToProduce(Boolean needToProduce) {
		this.needToProduce = needToProduce;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public Set<NewEdnQualification> getEdnQualifications() {
		return ednQualifications;
	}

	public void setEdnQualifications(Set<NewEdnQualification> ednQualifications) {
		this.ednQualifications = ednQualifications;
	}

	public Weightage getWeightage() {
		return weightage;
	}

	public void setWeightage(Weightage weightage) {
		this.weightage = weightage;
	}

	public BigDecimal getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(BigDecimal weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	/**
	 * @return the isIncludeLanguage
	 */
	public Boolean getIsIncludeLanguage() {
		return isIncludeLanguage;
	}

	/**
	 * @param isIncludeLanguage the isIncludeLanguage to set
	 */
	public void setIsIncludeLanguage(Boolean isIncludeLanguage) {
		this.isIncludeLanguage = isIncludeLanguage;
	}

	public Boolean getIsExamRequired() {
		return isExamRequired;
	}

	public void setIsExamRequired(Boolean isExamRequired) {
		this.isExamRequired = isExamRequired;
	}

}
