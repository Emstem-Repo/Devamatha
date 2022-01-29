package com.kp.cms.to.attendance;

import java.util.List;

import com.kp.cms.to.admission.CurriculumSchemeDurationTO;

public class ClassSchemewiseTO {
	private int id;
	private int classId;
	private int curriculamSchemeDurationId;
	private ClassesTO classesTo;
	private CurriculumSchemeDurationTO curriculumSchemeDurationTO;
	private List<PeriodTO> periodTO;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getCurriculamSchemeDurationId() {
		return curriculamSchemeDurationId;
	}
	public void setCurriculamSchemeDurationId(int curriculamSchemeDurationId) {
		this.curriculamSchemeDurationId = curriculamSchemeDurationId;
	}
	
	public CurriculumSchemeDurationTO getCurriculumSchemeDurationTO() {
		return curriculumSchemeDurationTO;
	}
	public void setCurriculumSchemeDurationTO(
			CurriculumSchemeDurationTO curriculumSchemeDurationTO) {
		this.curriculumSchemeDurationTO = curriculumSchemeDurationTO;
	}
	public ClassesTO getClassesTo() {
		return classesTo;
	}
	public void setClassesTo(ClassesTO classesTo) {
		this.classesTo = classesTo;
	}
	public void setPeriodTO(List<PeriodTO> periodTO) {
		this.periodTO = periodTO;
	}
	public List<PeriodTO> getPeriodTO() {
		return periodTO;
	}

	
	
}
