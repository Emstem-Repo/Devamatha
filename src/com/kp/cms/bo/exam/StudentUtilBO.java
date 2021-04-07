package com.kp.cms.bo.exam;

import java.util.Set;

/**
 * Jan 1, 2010 Created By 9Elements Team
 */
public class StudentUtilBO extends ExamGenBO {
	
	private int admApplnId;
	private String rollNo;
	private String registerNo;
	private Integer classSchemewiseId;
	private Boolean isHide;
	private ClassSchemewiseUtilBO classSchemewiseUtilBO;
	private AdmApplnUtilBO admApplnUtilBO;
	private Set<ExamStudentEligibilityCheckBO> examStudentEligibilityCheckBOSet;
	private Set<ExamOptionalSubjectAssignmentStudentBO> examOptSubjAssignStudentBOset;
	private Set<ExamAssignOverallMarksBO> examAssignOverallMarksBOset;
	private Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset;
	private Set<ExamStudentSpecializationBO> examStudentSpecializationBOSet;
	
	private Set<ExamStudentEligibilityEntryBO> examStudentEligibilityEntryBOSet;
	private Set<ExamStudentBioDataBO> examStudentBioDataBOSet;
	
	private Set<ExamStudentPreviousClassDetailsBO> examStudentPreviousClassDetailsBOSet;
	private Set<ExamStudentSubGrpHistoryBO> examStudentSubjectGroupBOSet;

	public StudentUtilBO() {
	}

	

	public Integer getClassSchemewiseId() {
		return classSchemewiseId;
	}



	public void setClassSchemewiseId(Integer classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}



	public int getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}

	

	public void setId(int id) {
		this.id = id;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public AdmApplnUtilBO getAdmApplnUtilBO() {
		return admApplnUtilBO;
	}

	public void setAdmApplnUtilBO(AdmApplnUtilBO admApplnUtilBO) {
		this.admApplnUtilBO = admApplnUtilBO;
	}

	public ClassSchemewiseUtilBO getClassSchemewiseUtilBO() {
		return classSchemewiseUtilBO;
	}

	public void setClassSchemewiseUtilBO(ClassSchemewiseUtilBO classSchemewiseUtilBO) {
		this.classSchemewiseUtilBO = classSchemewiseUtilBO;
	}

	public void setExamStudentEligibilityCheckBOSet(
			Set<ExamStudentEligibilityCheckBO> examStudentEligibilityCheckBOSet) {
		this.examStudentEligibilityCheckBOSet = examStudentEligibilityCheckBOSet;
	}

	public Set<ExamStudentEligibilityCheckBO> getExamStudentEligibilityCheckBOSet() {
		return examStudentEligibilityCheckBOSet;
	}

	public void setExamOptSubjAssignStudentBOset(
			Set<ExamOptionalSubjectAssignmentStudentBO> examOptSubjAssignStudentBOset) {
		this.examOptSubjAssignStudentBOset = examOptSubjAssignStudentBOset;
	}

	public Set<ExamOptionalSubjectAssignmentStudentBO> getExamOptSubjAssignStudentBOset() {
		return examOptSubjAssignStudentBOset;
	}

	public void setExamAssignOverallMarksBOset(
			Set<ExamAssignOverallMarksBO> examAssignOverallMarksBOset) {
		this.examAssignOverallMarksBOset = examAssignOverallMarksBOset;
	}

	public Set<ExamAssignOverallMarksBO> getExamAssignOverallMarksBOset() {
		return examAssignOverallMarksBOset;
	}

	public void setExamInternalRetestApplicationBOset(
			Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset) {
		this.examInternalRetestApplicationBOset = examInternalRetestApplicationBOset;
	}

	public Set<ExamInternalRetestApplicationBO> getExamInternalRetestApplicationBOset() {
		return examInternalRetestApplicationBOset;
	}

	public void setExamStudentEligibilityEntryBOSet(
			Set<ExamStudentEligibilityEntryBO> examStudentEligibilityEntryBOSet) {
		this.examStudentEligibilityEntryBOSet = examStudentEligibilityEntryBOSet;
	}

	public Set<ExamStudentEligibilityEntryBO> getExamStudentEligibilityEntryBOSet() {
		return examStudentEligibilityEntryBOSet;
	}

	public Set<ExamStudentBioDataBO> getExamStudentBioDataBOSet() {
		return examStudentBioDataBOSet;
	}

	public void setExamStudentBioDataBOSet(
			Set<ExamStudentBioDataBO> examStudentBioDataBOSet) {
		this.examStudentBioDataBOSet = examStudentBioDataBOSet;
	}
	public Set<ExamStudentSpecializationBO> getExamStudentSpecializationBOSet() {
		return examStudentSpecializationBOSet;
	}

	public void setExamStudentSpecializationBOSet(
			Set<ExamStudentSpecializationBO> examStudentSpecializationBOSet) {
		this.examStudentSpecializationBOSet = examStudentSpecializationBOSet;
	}



	public Set<ExamStudentPreviousClassDetailsBO> getExamStudentPreviousClassDetailsBOSet() {
		return examStudentPreviousClassDetailsBOSet;
	}



	public void setExamStudentPreviousClassDetailsBOSet(
			Set<ExamStudentPreviousClassDetailsBO> examStudentPreviousClassDetailsBOSet) {
		this.examStudentPreviousClassDetailsBOSet = examStudentPreviousClassDetailsBOSet;
	}



	public Set<ExamStudentSubGrpHistoryBO> getExamStudentSubjectGroupBOSet() {
		return examStudentSubjectGroupBOSet;
	}



	public void setExamStudentSubjectGroupBOSet(
			Set<ExamStudentSubGrpHistoryBO> examStudentSubjectGroupBOSet) {
		this.examStudentSubjectGroupBOSet = examStudentSubjectGroupBOSet;
	}



	public Boolean getIsHide() {
		return isHide;
	}



	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

}
