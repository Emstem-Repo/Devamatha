package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ApplicantSubjectGroupUtilBO extends ExamGenBO {

	private int admnApplnId;
	private int subjectGroupId;

	private AdmApplnUtilBO admApplnUtilBO;
	private SubjectGroupUtilBO subjectGroupUtilBO;

	public ApplicantSubjectGroupUtilBO() {
		super();
	}

	public ApplicantSubjectGroupUtilBO(Integer admnApplnId, Integer subjectGroupId) {
		super();
		this.admnApplnId = admnApplnId;
		this.subjectGroupId = subjectGroupId;
	}

	public int getAdmnApplnId() {
		return admnApplnId;
	}

	public void setAdmnApplnId(int admnApplnId) {
		this.admnApplnId = admnApplnId;
	}

	public int getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public AdmApplnUtilBO getAdmApplnUtilBO() {
		return admApplnUtilBO;
	}

	public void setAdmApplnUtilBO(AdmApplnUtilBO admApplnUtilBO) {
		this.admApplnUtilBO = admApplnUtilBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

}
