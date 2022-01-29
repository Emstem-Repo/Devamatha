package com.kp.cms.to.admission;

import java.io.Serializable;

import com.kp.cms.bo.admin.AdmAppln;

public class FinalMeritListSearchTo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maxIntakeCourseId;
	
	private String finalMeritSetId;

	private String applicationId;

	private String applicantName;

	private String applicantDOB;

	private String applicantNationality;

	private String applicantReligion;

	private String applicantSubReligion;

	private String applicantCasteCategory;

	private String applicantTotalWeightage;
	
	private boolean isSelected ;
	
	private int inWaitingList;
	
	private String preRequisiteType;
	
	private String prerequisiteMarks;
	
	private String applicantMail;
	
	private AdmAppln admAppln = null;
	
	private String applicantGender;
	
	private String selectedCrsName;
	
	//This property is used to display student category in final meritlist search page	
	private String applicantResidentCategory;
	

	public String getApplicantResidentCategory() {
		return applicantResidentCategory;
	}

	public void setApplicantResidentCategory(String applicantResidentCategory) {
		this.applicantResidentCategory = applicantResidentCategory;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantDOB() {
		return applicantDOB;
	}

	public void setApplicantDOB(String applicantDOB) {
		this.applicantDOB = applicantDOB;
	}

	public String getApplicantNationality() {
		return applicantNationality;
	}

	public void setApplicantNationality(String applicantNationality) {
		this.applicantNationality = applicantNationality;
	}

	public String getApplicantReligion() {
		return applicantReligion;
	}

	public void setApplicantReligion(String applicantReligion) {
		this.applicantReligion = applicantReligion;
	}

	public String getApplicantSubReligion() {
		return applicantSubReligion;
	}

	public void setApplicantSubReligion(String applicantSubReligion) {
		this.applicantSubReligion = applicantSubReligion;
	}

	public String getApplicantCasteCategory() {
		return applicantCasteCategory;
	}

	public void setApplicantCasteCategory(String applicantCasteCategory) {
		this.applicantCasteCategory = applicantCasteCategory;
	}

	public String getApplicantTotalWeightage() {
		return applicantTotalWeightage;
	}

	public void setApplicantTotalWeightage(String applicantTotalWeightage) {
		this.applicantTotalWeightage = applicantTotalWeightage;
	}
	

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	

	public String getFinalMeritSetId() {
		return finalMeritSetId;
	}

	public void setFinalMeritSetId(String finalMeritSetId) {
		this.finalMeritSetId = finalMeritSetId;
	}

	public String getPreRequisiteType() {
		return preRequisiteType;
	}

	public void setPreRequisiteType(String preRequisiteType) {
		this.preRequisiteType = preRequisiteType;
	}

	public String getPrerequisiteMarks() {
		return prerequisiteMarks;
	}

	public void setPrerequisiteMarks(String prerequisiteMarks) {
		this.prerequisiteMarks = prerequisiteMarks;
	}



	public int getInWaitingList() {
		return inWaitingList;
	}

	public void setInWaitingList(int inWaitingList) {
		this.inWaitingList = inWaitingList;
	}

	public String getApplicantMail() {
		return applicantMail;
	}

	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}

	public AdmAppln getAdmAppln() {
		return admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

	public String getApplicantGender() {
		return applicantGender;
	}

	public void setApplicantGender(String applicantGender) {
		this.applicantGender = applicantGender;
	}

	public String getMaxIntakeCourseId() {
		return maxIntakeCourseId;
	}

	public void setMaxIntakeCourseId(String maxIntakeCourseId) {
		this.maxIntakeCourseId = maxIntakeCourseId;
	}

	public String getSelectedCrsName() {
		return selectedCrsName;
	}

	public void setSelectedCrsName(String selectedCrsName) {
		this.selectedCrsName = selectedCrsName;
	}
}
