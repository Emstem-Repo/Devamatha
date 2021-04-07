package com.kp.cms.bo.exam;

/**
 * Jan 15, 2010 Created By 9Elements Team
 */
import java.util.Set;

import com.kp.cms.bo.admin.SubjectType;

public class SubjectUtilBO extends ExamGenBO {

	private String code;
	private String universitySubjectCode;
	private int isSecondLanguage;
	private int isOptionalSubject;
	private String isTheoryPractical;
	private SubjectType subjectType;
	private Boolean isCertificateCourse;
	private Set<ExamTimeTableBO> examTimeTableBOset;
	private Set<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOset;
	private Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset;
	private Set<ExamSubjectRuleSettingsBO> examSubjectRuleSettingsBOset;
	private Set<ExamRevaluationDetailsBO> examRevaluationDetailsBOSet;

	public SubjectUtilBO() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}

	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}

	public void setExamTimeTableBOset(Set<ExamTimeTableBO> examTimeTableBOset) {
		this.examTimeTableBOset = examTimeTableBOset;
	}

	public Set<ExamTimeTableBO> getExamTimeTableBOset() {
		return examTimeTableBOset;
	}

	public void setIsSecondLanguage(int isSecondLanguage) {
		this.isSecondLanguage = isSecondLanguage;
	}

	public int getIsSecondLanguage() {
		return isSecondLanguage;
	}

	public void setIsOptionalSubject(int isOptionalSubject) {
		this.isOptionalSubject = isOptionalSubject;
	}

	public int getIsOptionalSubject() {
		return isOptionalSubject;
	}

	public void setExamSupplementaryImprovementApplicationBOset(
			Set<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOset) {
		this.examSupplementaryImprovementApplicationBOset = examSupplementaryImprovementApplicationBOset;
	}

	public Set<ExamSupplementaryImprovementApplicationBO> getExamSupplementaryImprovementApplicationBOset() {
		return examSupplementaryImprovementApplicationBOset;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setExamInternalRetestApplicationBOset(
			Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset) {
		this.examInternalRetestApplicationBOset = examInternalRetestApplicationBOset;
	}

	public Set<ExamInternalRetestApplicationBO> getExamInternalRetestApplicationBOset() {
		return examInternalRetestApplicationBOset;
	}

	public void setExamSubjectRuleSettingsBOset(
			Set<ExamSubjectRuleSettingsBO> examSubjectRuleSettingsBOset) {
		this.examSubjectRuleSettingsBOset = examSubjectRuleSettingsBOset;
	}

	public Set<ExamSubjectRuleSettingsBO> getExamSubjectRuleSettingsBOset() {
		return examSubjectRuleSettingsBOset;
	}

	public void setExamRevaluationDetailsBOSet(
			Set<ExamRevaluationDetailsBO> examRevaluationDetailsBOSet) {
		this.examRevaluationDetailsBOSet = examRevaluationDetailsBOSet;
	}

	public Set<ExamRevaluationDetailsBO> getExamRevaluationDetailsBOSet() {
		return examRevaluationDetailsBOSet;
	}

	public SubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public Boolean getIsCertificateCourse() {
		return isCertificateCourse;
	}

	public void setIsCertificateCourse(Boolean isCertificateCourse) {
		this.isCertificateCourse = isCertificateCourse;
	}

	

}
