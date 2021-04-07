package com.kp.cms.bo.exam;

/**
 * Jan 12, 2010
 * Created By 9Elements Team
 */
import java.util.Date;

import com.kp.cms.bo.admin.Classes;

public class ExamSupplementaryImprovementApplicationBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int subjectId;
	private int isSupplementary;
	private int isImprovement;
	private int isFailedTheory;
	private int isFailedPractical;
	private int isAppearedTheory;
	private int isAppearedPractical;
	private String fees;
	private int chance;
	private int schemeNo;
	private boolean isTheoryOverallFailed;
	private boolean isPracticalOverallFailed;

	// Many-to-one
	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private SubjectUtilBO subjectUtilBO;
	private Classes classes;

	public ExamSupplementaryImprovementApplicationBO() {
		super();
	}

	public ExamSupplementaryImprovementApplicationBO(int examId, int studentId,
			int subjectId, int isSupplementary, int isImprovement,
			int isFailedTheory, int isFailedPractical, int isAppearedTheory,
			int isAppearedPractical, String fees, String userId) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.isSupplementary = isSupplementary;
		this.isImprovement = isImprovement;
		this.isFailedTheory = isFailedTheory;
		this.isFailedPractical = isFailedPractical;
		this.isAppearedTheory = isAppearedTheory;
		this.isAppearedPractical = isAppearedPractical;
		this.fees = fees;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}
	

	public ExamSupplementaryImprovementApplicationBO(int examId, int studentId,
			int subjectId, int isFailedTheory, int isFailedPractical,
			int isAppearedTheory, int isAppearedPractical, String fees,String userId ) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.isFailedTheory = isFailedTheory;
		this.isFailedPractical = isFailedPractical;
		this.isAppearedTheory = isAppearedTheory;
		this.isAppearedPractical = isAppearedPractical;
		this.fees = fees;
		this.createdBy=userId;
		this.createdDate= new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}
	
	

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getIsSupplementary() {
		return isSupplementary;
	}

	public void setIsSupplementary(int isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public int getIsImprovement() {
		return isImprovement;
	}

	public void setIsImprovement(int isImprovement) {
		this.isImprovement = isImprovement;
	}

	public int getIsFailedTheory() {
		return isFailedTheory;
	}

	public void setIsFailedTheory(int isFailedTheory) {
		this.isFailedTheory = isFailedTheory;
	}

	public int getIsFailedPractical() {
		return isFailedPractical;
	}

	public void setIsFailedPractical(int isFailedPractical) {
		this.isFailedPractical = isFailedPractical;
	}

	public int getIsAppearedTheory() {
		return isAppearedTheory;
	}

	public void setIsAppearedTheory(int isAppearedTheory) {
		this.isAppearedTheory = isAppearedTheory;
	}

	public int getIsAppearedPractical() {
		return isAppearedPractical;
	}

	public void setIsAppearedPractical(int isAppearedPractical) {
		this.isAppearedPractical = isAppearedPractical;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSchemeNo() {
		return schemeNo;
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
	
}
