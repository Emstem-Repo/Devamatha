package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class StudentSupplementaryImprovementApplication implements Serializable {
	
	private int id;
	private Boolean isSupplementary;
	private Boolean isImprovement;
	private Boolean isFailedTheory;
	private Boolean isFailedPractical;
	private Boolean isAppearedTheory;
	private Boolean isAppearedPractical;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String fees;
	private Integer chance;
	private Integer schemeNo;
	private Student student;
	private ExamDefinition examDefinition;
	private Subject subject;
	private Classes classes;
	private boolean isTheoryOverallFailed;
	private boolean isPracticalOverallFailed;
	private Boolean isOnline;
	
	//raghu added from mounts
	// added newly on 16-01-2015
	private Boolean isCIAFailedTheory;
	private Boolean isCIAFailedPractical;
	private Boolean isCIAAppearedTheory;
	private Boolean isCIAAppearedPractical;
	private Boolean ciaExam;

	
	public StudentSupplementaryImprovementApplication() {
		super();
	}
	
	
	public StudentSupplementaryImprovementApplication(int id,
			Boolean isSupplementary, Boolean isImprovement,
			Boolean isFailedTheory, Boolean isFailedPractical,
			Boolean isAppearedTheory, Boolean isAppearedPractical,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, String fees, Integer chance,
			Integer schemeNo, Student student, ExamDefinition examDefinition,
			Subject subject, Classes classes,Boolean isOnline,Boolean isCIAFailedTheory,
			Boolean isCIAFailedPractical,Boolean isCIAAppearedTheory,Boolean isCIAAppearedPractical,
			Boolean ciaExam) {
		super();
		this.id = id;
		this.isSupplementary = isSupplementary;
		this.isImprovement = isImprovement;
		this.isFailedTheory = isFailedTheory;
		this.isFailedPractical = isFailedPractical;
		this.isAppearedTheory = isAppearedTheory;
		this.isAppearedPractical = isAppearedPractical;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.fees = fees;
		this.chance = chance;
		this.schemeNo = schemeNo;
		this.student = student;
		this.examDefinition = examDefinition;
		this.subject = subject;
		this.classes = classes;
		this.isOnline=isOnline;
		this.isCIAAppearedPractical = isCIAAppearedPractical;
		this.isCIAAppearedTheory = isCIAAppearedTheory;
		this.isCIAFailedPractical = isCIAFailedPractical;
		this.isCIAFailedTheory = isCIAFailedTheory;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsSupplementary() {
		return isSupplementary;
	}
	public void setIsSupplementary(Boolean isSupplementary) {
		this.isSupplementary = isSupplementary;
	}
	public Boolean getIsImprovement() {
		return isImprovement;
	}
	public void setIsImprovement(Boolean isImprovement) {
		this.isImprovement = isImprovement;
	}
	public Boolean getIsFailedTheory() {
		return isFailedTheory;
	}
	public void setIsFailedTheory(Boolean isFailedTheory) {
		this.isFailedTheory = isFailedTheory;
	}
	public Boolean getIsFailedPractical() {
		return isFailedPractical;
	}
	public void setIsFailedPractical(Boolean isFailedPractical) {
		this.isFailedPractical = isFailedPractical;
	}
	public Boolean getIsAppearedTheory() {
		return isAppearedTheory;
	}
	public void setIsAppearedTheory(Boolean isAppearedTheory) {
		this.isAppearedTheory = isAppearedTheory;
	}
	public Boolean getIsAppearedPractical() {
		return isAppearedPractical;
	}
	public void setIsAppearedPractical(Boolean isAppearedPractical) {
		this.isAppearedPractical = isAppearedPractical;
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
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public Integer getChance() {
		return chance;
	}
	public void setChance(Integer chance) {
		this.chance = chance;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public ExamDefinition getExamDefinition() {
		return examDefinition;
	}

	public void setExamDefinition(ExamDefinition examDefinition) {
		this.examDefinition = examDefinition;
	}

	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public boolean getIsTheoryOverallFailed() {
		return isTheoryOverallFailed;
	}

	public void setIsTheoryOverallFailed(boolean isTheoryOverallFailed) {
		this.isTheoryOverallFailed = isTheoryOverallFailed;
	}

	public boolean getIsPracticalOverallFailed() {
		return isPracticalOverallFailed;
	}

	public void setIsPracticalOverallFailed(boolean isPracticalOverallFailed) {
		this.isPracticalOverallFailed = isPracticalOverallFailed;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
	public Boolean getIsCIAFailedTheory() {
		return isCIAFailedTheory;
	}

	public void setIsCIAFailedTheory(Boolean isCIAFailedTheory) {
		this.isCIAFailedTheory = isCIAFailedTheory;
	}

	public Boolean getIsCIAFailedPractical() {
		return isCIAFailedPractical;
	}

	public void setIsCIAFailedPractical(Boolean isCIAFailedPractical) {
		this.isCIAFailedPractical = isCIAFailedPractical;
	}

	public Boolean getIsCIAAppearedTheory() {
		return isCIAAppearedTheory;
	}

	public void setIsCIAAppearedTheory(Boolean isCIAAppearedTheory) {
		this.isCIAAppearedTheory = isCIAAppearedTheory;
	}

	public Boolean getIsCIAAppearedPractical() {
		return isCIAAppearedPractical;
	}

	public void setIsCIAAppearedPractical(Boolean isCIAAppearedPractical) {
		this.isCIAAppearedPractical = isCIAAppearedPractical;
	}

	public Boolean getCiaExam() {
		return ciaExam;
	}

	public void setCiaExam(Boolean ciaExam) {
		this.ciaExam = ciaExam;
	}

	public void setTheoryOverallFailed(boolean isTheoryOverallFailed) {
		this.isTheoryOverallFailed = isTheoryOverallFailed;
	}

	public void setPracticalOverallFailed(boolean isPracticalOverallFailed) {
		this.isPracticalOverallFailed = isPracticalOverallFailed;
	}
	
	
}