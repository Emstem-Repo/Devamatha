package com.kp.cms.bo.exam;

public class AdmApplnUtilBO {
	private int id;
	private int personalDataId;
	private int applnNo;
	private int isCancelled;
	private int selectedCourseId;

	PersonalDataUtilBO personalDataUtilBO;

	public PersonalDataUtilBO getPersonalDataUtilBO() {
		return personalDataUtilBO;
	}

	public void setPersonalDataUtilBO(PersonalDataUtilBO personalDataUtilBO) {
		this.personalDataUtilBO = personalDataUtilBO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonalDataId() {
		return personalDataId;
	}

	public void setPersonalDataId(int personalDataId) {
		this.personalDataId = personalDataId;
	}

	public void setApplnNo(int applnNo) {
		this.applnNo = applnNo;
	}

	public int getApplnNo() {
		return applnNo;
	}

	public void setIsCancelled(int isCancelled) {
		this.isCancelled = isCancelled;
	}

	public int getIsCancelled() {
		return isCancelled;
	}

	public int getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(int selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}
	
}
