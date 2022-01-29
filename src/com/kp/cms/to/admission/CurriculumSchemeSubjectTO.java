package com.kp.cms.to.admission;

import com.kp.cms.to.admin.SubjectGroupTO;

public class CurriculumSchemeSubjectTO {
	private int id;
	private int curriculumSchemeDurationId;
	private SubjectGroupTO subjectGroupTO;
	private CurriculumSchemeDurationTO curriculumSchemeDurationTO;
	
	public int getCurriculumSchemeDurationId() {
		return curriculumSchemeDurationId;
	}

	public void setCurriculumSchemeDurationId(int curriculumSchemeDurationId) {
		this.curriculumSchemeDurationId = curriculumSchemeDurationId;
	}
	

	
	public CurriculumSchemeDurationTO getCurriculumSchemeDurationTO() {
		return curriculumSchemeDurationTO;
	}

	public void setCurriculumSchemeDurationTO(
			CurriculumSchemeDurationTO curriculumSchemeDurationTO) {
		this.curriculumSchemeDurationTO = curriculumSchemeDurationTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public SubjectGroupTO getSubjectGroupTO() {
		return subjectGroupTO;
	}

	public void setSubjectGroupTO(SubjectGroupTO subjectGroupTO) {
		this.subjectGroupTO = subjectGroupTO;
	}



}
