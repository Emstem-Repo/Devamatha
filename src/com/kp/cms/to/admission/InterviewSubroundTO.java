package com.kp.cms.to.admission;

import java.io.Serializable;

public class InterviewSubroundTO implements Comparable<InterviewSubroundTO> , Serializable{
	private int interviewResultId;
	private String subroundId_noOfInterviewer;
	private int gradeObtainedId;

	public int getSubroundId() {
		int return_subroundId = 0;
		if (subroundId_noOfInterviewer != null
				&& subroundId_noOfInterviewer.length() > 0) {
			return_subroundId = Integer.parseInt((subroundId_noOfInterviewer
					.split("_"))[0]);
		}
		return return_subroundId;
	}

	@Override
	public int compareTo(InterviewSubroundTO o) {
		return this.getSubroundId() - o.getSubroundId();
	}

	public String getSubroundId_noOfInterviewer() {
		return subroundId_noOfInterviewer;
	}

	public void setSubroundId_noOfInterviewer(String subroundId_noOfInterviewer) {
		this.subroundId_noOfInterviewer = subroundId_noOfInterviewer;
	}

	public int getGradeObtainedId() {
		return gradeObtainedId;
	}

	public void setGradeObtainedId(int gradeObtainedId) {
		this.gradeObtainedId = gradeObtainedId;
	}

	public void setInterviewResultId(int interviewResultId) {
		this.interviewResultId = interviewResultId;
	}

	public int getInterviewResultId() {
		return interviewResultId;
	}

}
