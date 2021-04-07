package com.kp.cms.to.admission;

import java.io.Serializable;

public class InterviewSubroundDisplayTO implements Serializable{

	private int subroundId;
	private int interviewTypeId;
	private String interviewTypeName;
	private String subroundName;
	private int noOfInterviewers;
	private int weightageId;
	private String weightagePercentage;	

	public InterviewSubroundDisplayTO() {
		super();
	}

	public InterviewSubroundDisplayTO(int subroundId, String subroundName,
			int interviewTypeId, String interviewTypeName, int noOfInterviewers, int weightageId, String weightagePercentage) {
		super();
		this.subroundId = subroundId;
		this.setInterviewTypeId(interviewTypeId);
		this.subroundName = subroundName;
		this.interviewTypeName = interviewTypeName;
		this.noOfInterviewers = noOfInterviewers;
		this.weightageId = weightageId;
		this.weightagePercentage = weightagePercentage;
	}

	public InterviewSubroundDisplayTO(int subroundId, String subroundName,
			int noOfInterviewers) {
		super();
		this.subroundId = subroundId;
		this.subroundName = subroundName;
		this.noOfInterviewers = noOfInterviewers;
	}

	public InterviewSubroundDisplayTO(
			InterviewSubroundDisplayTO interviewSubroundDisplayTO) {
		super();
		this.interviewTypeId = interviewSubroundDisplayTO.getInterviewTypeId();
		this.interviewTypeName = interviewSubroundDisplayTO
				.getInterviewTypeName();
		this.subroundId = interviewSubroundDisplayTO.getSubroundId();
		this.subroundName = interviewSubroundDisplayTO.getInterviewTypeName();
		this.noOfInterviewers = interviewSubroundDisplayTO
				.getNoOfInterviewers();
	}

	public void setInterviewTypeName_INTEL(String intName) {
		if (interviewTypeName == null || interviewTypeName.equalsIgnoreCase("")) {
			this.interviewTypeName = intName;
		} else if (!interviewTypeName.contains(intName)) {
			this.interviewTypeName = this.interviewTypeName + " / " + intName;
		}
		this.subroundName = interviewTypeName;
	}

	public void setSubroundName_INTEL(String interviewTypeName_input,
			String subroundName_input) {
		String s = interviewTypeName_input + "-" + subroundName_input;
		if (this.interviewTypeName == null
				|| this.interviewTypeName.equalsIgnoreCase("")) {
			this.interviewTypeName = s;
		} else if (!interviewTypeName.contains(s)) {
			this.interviewTypeName = this.interviewTypeName + " / " + s;
		}
		this.subroundName = interviewTypeName;
	}

	public void setSubroundName_INTEL_bothPresent() {
		this.subroundName = this.interviewTypeName + "-" + this.subroundName;

	}

	public int getSubroundId() {
		return subroundId;
	}

	public void setSubroundId(int subroundId) {
		this.subroundId = subroundId;
	}

	public int getNoOfInterviewers() {
		return noOfInterviewers;
	}

	public void setNoOfInterviewers(int noOfInterviewers) {
		this.noOfInterviewers = noOfInterviewers;
	}

	public void setInterviewTypeName(String interviewTypeName) {
		this.interviewTypeName = interviewTypeName;
	}

	public String getInterviewTypeName() {
		return interviewTypeName;
	}

	public void setSubroundName(String subroundName) {
		this.subroundName = subroundName;
	}

	public String getSubroundName() {
		return subroundName;
	}

	public void setInterviewTypeId(int interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}

	public int getInterviewTypeId() {
		return interviewTypeId;
	}

	public int getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(int weightageId) {
		this.weightageId = weightageId;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

}
