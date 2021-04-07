package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamInternalCalculationMarksTO implements Serializable,Comparable<ExamInternalCalculationMarksTO> {
	private int id;
	private String programTypeProgramCourse;
	private String startPercentage;
	private String endPercentage;
	private String marks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProgramTypeProgramCourse() {
		return programTypeProgramCourse;
	}

	public void setProgramTypeProgramCourse(String programTypeProgramCourse) {
		this.programTypeProgramCourse = programTypeProgramCourse;
	}

	public String getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}

	public String getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}
	@Override
	public int compareTo(ExamInternalCalculationMarksTO arg0) {
		if(arg0!=null && this!=null && arg0.getProgramTypeProgramCourse()!=null
				 && this.getProgramTypeProgramCourse()!=null){
			return this.getProgramTypeProgramCourse().compareTo(arg0.getProgramTypeProgramCourse());
		}else
		return 0;
	}
}
