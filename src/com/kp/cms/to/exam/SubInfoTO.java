package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubInfoTO implements Serializable,Comparable<SubInfoTO>{

	private Integer subjectId;
	private String theoryEseEnteredMaxMark;
	private String theoryEseMaximumMark;
	private String practicalEseEnteredMaxMark;
	private String practicalEseMaximumMark;
	private String isTheoryPractical;
	private String typeOfEvaluation;
	private String theoryEseMinimumMark;
	private String practicalEseMinimumMark;

	// For Supplementary Data Creation
	private String theoryInternalMinimumMark;
	private String practicalInternalMinimumMark;
	private String theoryInternalEnteredMaxMark;
	private String theoryInternalMaximumMark;
	private String practicalInternalEnteredMaxMark;
	private String practicalInternalMaximumMark;

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getTheoryEseEnteredMaxMark() {
		return theoryEseEnteredMaxMark;
	}

	public void setTheoryEseEnteredMaxMark(String theoryEseEnteredMaxMark) {
		this.theoryEseEnteredMaxMark = theoryEseEnteredMaxMark;
	}

	public String getTheoryEseMaximumMark() {
		return theoryEseMaximumMark;
	}

	public void setTheoryEseMaximumMark(String theoryEseMaximumMark) {
		this.theoryEseMaximumMark = theoryEseMaximumMark;
	}

	public String getPracticalEseEnteredMaxMark() {
		return practicalEseEnteredMaxMark;
	}

	public void setPracticalEseEnteredMaxMark(String practicalEseEnteredMaxMark) {
		this.practicalEseEnteredMaxMark = practicalEseEnteredMaxMark;
	}

	public String getPracticalEseMaximumMark() {
		return practicalEseMaximumMark;
	}

	public void setPracticalEseMaximumMark(String practicalEseMaximumMark) {
		this.practicalEseMaximumMark = practicalEseMaximumMark;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getTypeOfEvaluation() {
		return typeOfEvaluation;
	}

	public void setTypeOfEvaluation(String typeOfEvaluation) {
		this.typeOfEvaluation = typeOfEvaluation;
	}

	public void setTheoryEseMinimumMark(String theoryEseMinimumMark) {
		this.theoryEseMinimumMark = theoryEseMinimumMark;
	}

	public String getTheoryEseMinimumMark() {
		return theoryEseMinimumMark;
	}

	public void setPracticalEseMinimumMark(String practicalEseMinimumMark) {
		this.practicalEseMinimumMark = practicalEseMinimumMark;
	}

	public String getPracticalEseMinimumMark() {
		return practicalEseMinimumMark;
	}

	public String getTheoryInternalMinimumMark() {
		return theoryInternalMinimumMark;
	}

	public void setTheoryInternalMinimumMark(String theoryInternalMinimumMark) {
		this.theoryInternalMinimumMark = theoryInternalMinimumMark;
	}

	public String getPracticalInternalMinimumMark() {
		return practicalInternalMinimumMark;
	}

	public void setPracticalInternalMinimumMark(
			String practicalInternalMinimumMark) {
		this.practicalInternalMinimumMark = practicalInternalMinimumMark;
	}

	public String getTheoryInternalEnteredMaxMark() {
		return theoryInternalEnteredMaxMark;
	}

	public void setTheoryInternalEnteredMaxMark(
			String theoryInternalEnteredMaxMark) {
		this.theoryInternalEnteredMaxMark = theoryInternalEnteredMaxMark;
	}

	public String getTheoryInternalMaximumMark() {
		return theoryInternalMaximumMark;
	}

	public void setTheoryInternalMaximumMark(String theoryInternalMaximumMark) {
		this.theoryInternalMaximumMark = theoryInternalMaximumMark;
	}

	public String getPracticalInternalEnteredMaxMark() {
		return practicalInternalEnteredMaxMark;
	}

	public void setPracticalInternalEnteredMaxMark(
			String practicalInternalEnteredMaxMark) {
		this.practicalInternalEnteredMaxMark = practicalInternalEnteredMaxMark;
	}

	public String getPracticalInternalMaximumMark() {
		return practicalInternalMaximumMark;
	}

	public void setPracticalInternalMaximumMark(
			String practicalInternalMaximumMark) {
		this.practicalInternalMaximumMark = practicalInternalMaximumMark;
	}

	@Override
	public int compareTo(SubInfoTO arg0) {
		if(arg0!=null && this!=null && arg0.getTypeOfEvaluation()!=null
				 && this.getTypeOfEvaluation()!=null){
			return this.getTypeOfEvaluation().compareTo(arg0.getTypeOfEvaluation());
		}else
		return 0;
	}

}
