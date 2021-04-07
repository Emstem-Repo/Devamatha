package com.kp.cms.to.employee;

import java.util.List;

public class EmpResumeSubmissionTo {
	
	private List<EmpPreviousOrgTo> experiences;
	private List<EmpPreviousOrgTo> teachingExperience;
	private List<EmpQualificationLevelTo> empQualificationLevelTos;
	private List<EmpQualificationLevelTo> empQualificationFixedTo;
	
	public List<EmpPreviousOrgTo> getExperiences() {
		return experiences;
	}
	public List<EmpPreviousOrgTo> getTeachingExperience() {
		return teachingExperience;
	}
	public List<EmpQualificationLevelTo> getEmpQualificationLevelTos() {
		return empQualificationLevelTos;
	}
	public List<EmpQualificationLevelTo> getEmpQualificationFixedTo() {
		return empQualificationFixedTo;
	}
	public void setExperiences(List<EmpPreviousOrgTo> experiences) {
		this.experiences = experiences;
	}
	public void setTeachingExperience(List<EmpPreviousOrgTo> teachingExperience) {
		this.teachingExperience = teachingExperience;
	}
	public void setEmpQualificationLevelTos(
			List<EmpQualificationLevelTo> empQualificationLevelTos) {
		this.empQualificationLevelTos = empQualificationLevelTos;
	}
	public void setEmpQualificationFixedTo(
			List<EmpQualificationLevelTo> empQualificationFixedTo) {
		this.empQualificationFixedTo = empQualificationFixedTo;
	}
	
	

}
