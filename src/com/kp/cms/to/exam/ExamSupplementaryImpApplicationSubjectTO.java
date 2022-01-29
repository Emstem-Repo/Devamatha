package com.kp.cms.to.exam;

/**
 * Jan 27, 2010 Created By 9Elements
 */
public class ExamSupplementaryImpApplicationSubjectTO {

	private Integer id;
	private int examId;
	private int studentId;
	private int subjectId;
	private String subjectCode;
	private String subjectName;
	private String failedTheory;
	private String failedPractical;

	private String appearedTheory;
	private String appearedPractical;

	private boolean isFailedTheory;
	private boolean isFailedPractical;
	private boolean isAppearedTheory;
	private boolean isAppearedPractical;
	private boolean controlDisable;
	private boolean tempChecked;
	private boolean tempPracticalChecked;

	private String fees;
	private int chance;
	private int classId;
	private Boolean isOverallTheoryFailed;
	private Boolean isOverallPracticalFailed;
	private int schemeNo;
	private double previousFees;
	private boolean isOnline;
	private boolean theory;
	private boolean practical;
	
	//raghu added from mounts
	
	private boolean ciaExam;
	// 
	private Boolean isCIAFailedTheory;
	private Boolean isCIAFailedPractical;
	private Boolean isCIAAppearedTheory;
	private Boolean isCIAAppearedPractical;
	private Boolean tempCIAExamChecked;
	private Boolean isESE;

	private boolean isSupplementary;
	private boolean isImprovement;
	private String subjectType;
	private String sectionName;
	private boolean commonChecked;
	
	private String marks;
	private boolean isRevaluation;
	private boolean isChallengeRevaluation;
	private boolean isScrutiny;
	private boolean isApplied;
	private boolean disableCheckBox;
	
	private String ciaMark;
	
	public ExamSupplementaryImpApplicationSubjectTO() {
		super();
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getFailedTheory() {
		return failedTheory;
	}

	public void setFailedTheory(String failedTheory) {
		this.failedTheory = failedTheory;
	}

	public String getFailedPractical() {
		return failedPractical;
	}

	public void setFailedPractical(String failedPractical) {
		this.failedPractical = failedPractical;
	}

	public String getAppearedTheory() {
		return appearedTheory;
	}

	public void setAppearedTheory(String appearedTheory) {
		this.appearedTheory = appearedTheory;
	}

	public String getAppearedPractical() {
		return appearedPractical;
	}

	public void setAppearedPractical(String appearedPractical) {
		this.appearedPractical = appearedPractical;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public boolean getIsFailedTheory() {
		return isFailedTheory;
	}

	public void setIsFailedTheory(boolean isFailedTheory) {
		this.isFailedTheory = isFailedTheory;
	}

	public boolean getIsFailedPractical() {
		return isFailedPractical;
	}

	public void setIsFailedPractical(boolean isFailedPractical) {
		this.isFailedPractical = isFailedPractical;
	}
	public boolean getIsAppearedTheory() {
		return isAppearedTheory;
	}

	public void setIsAppearedTheory(boolean isAppearedTheory) {
		this.isAppearedTheory = isAppearedTheory;
	}

	public boolean getIsAppearedPractical() {
		return isAppearedPractical;
	}

	public void setIsAppearedPractical(boolean isAppearedPractical) {
		this.isAppearedPractical = isAppearedPractical;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

	public void setControlDisable(boolean controlDisable) {
		this.controlDisable = controlDisable;
	}

	public boolean isControlDisable() {
		return controlDisable;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public boolean isTempChecked() {
		return tempChecked;
	}

	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}

	public boolean isTempPracticalChecked() {
		return tempPracticalChecked;
	}

	public void setTempPracticalChecked(boolean tempPracticalChecked) {
		this.tempPracticalChecked = tempPracticalChecked;
	}

	public Boolean getIsOverallTheoryFailed() {
		return isOverallTheoryFailed;
	}

	public void setIsOverallTheoryFailed(Boolean isOverallTheoryFailed) {
		this.isOverallTheoryFailed = isOverallTheoryFailed;
	}

	public Boolean getIsOverallPracticalFailed() {
		return isOverallPracticalFailed;
	}

	public void setIsOverallPracticalFailed(Boolean isOverallPracticalFailed) {
		this.isOverallPracticalFailed = isOverallPracticalFailed;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public double getPreviousFees() {
		return previousFees;
	}

	public void setPreviousFees(double previousFees) {
		this.previousFees = previousFees;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isTheory() {
		return theory;
	}

	public void setTheory(boolean theory) {
		this.theory = theory;
	}

	public boolean isPractical() {
		return practical;
	}

	public void setPractical(boolean practical) {
		this.practical = practical;
	}
	
	public boolean isCiaExam() {
		return ciaExam;
	}

	public void setCiaExam(boolean ciaExam) {
		this.ciaExam = ciaExam;
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

	public Boolean getTempCIAExamChecked() {
		return tempCIAExamChecked;
	}

	public void setTempCIAExamChecked(Boolean tempCIAExamChecked) {
		this.tempCIAExamChecked = tempCIAExamChecked;
	}

	public Boolean getIsESE() {
		return isESE;
	}

	public void setIsESE(Boolean isESE) {
		this.isESE = isESE;
	}

	public boolean getIsSupplementary() {
		return isSupplementary;
	}

	public void setIsSupplementary(boolean isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public boolean getIsImprovement() {
		return isImprovement;
	}

	public void setIsImprovement(boolean isImprovement) {
		this.isImprovement = isImprovement;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public boolean isCommonChecked() {
		return commonChecked;
	}

	public void setCommonChecked(boolean commonChecked) {
		this.commonChecked = commonChecked;
	}


	public String getMarks() {
		return marks;
	}


	public void setMarks(String marks) {
		this.marks = marks;
	}


	public boolean isRevaluation() {
		return isRevaluation;
	}


	public void setRevaluation(boolean isRevaluation) {
		this.isRevaluation = isRevaluation;
	}

	public boolean isScrutiny() {
		return isScrutiny;
	}


	public void setScrutiny(boolean isScrutiny) {
		this.isScrutiny = isScrutiny;
	}


	public boolean getIsApplied() {
		return isApplied;
	}


	public void setIsApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}


	public boolean getIsChallengeRevaluation() {
		return isChallengeRevaluation;
	}


	public void setIsChallengeRevaluation(boolean isChallengeRevaluation) {
		this.isChallengeRevaluation = isChallengeRevaluation;
	}


	public boolean isDisableCheckBox() {
		return disableCheckBox;
	}


	public void setDisableCheckBox(boolean disableCheckBox) {
		this.disableCheckBox = disableCheckBox;
	}


	public String getCiaMark() {
		return ciaMark;
	}


	public void setCiaMark(String ciaMark) {
		this.ciaMark = ciaMark;
	}
	
	

}
