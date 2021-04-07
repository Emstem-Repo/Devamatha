package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamAttendanceMarksTO implements Serializable,Comparable<ExamAttendanceMarksTO>{
	
	private int id;
	private String programTypeProgramCourse;
	private String fromPercentage;
	private String toPercentage;
	private String marks;
	private String isTheoryPractical;
	
	
	
	public String getProgramTypeProgramCourse() {
		return programTypeProgramCourse;
	}

	public void setProgramTypeProgramCourse(String programTypeProgramCourse) {
		this.programTypeProgramCourse = programTypeProgramCourse;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFromPercentage() {
		return fromPercentage;
	}
	public void setFromPercentage(String fromPercentage) {
		this.fromPercentage = fromPercentage;
	}
	public String getToPercentage() {
		return toPercentage;
	}
	public void setToPercentage(String toPercentage) {
		this.toPercentage = toPercentage;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	@Override
	public int compareTo(ExamAttendanceMarksTO arg0) {
		if(arg0!=null && this!=null && arg0.getProgramTypeProgramCourse()!=null
				 && this.getProgramTypeProgramCourse()!=null){
			return this.getProgramTypeProgramCourse().compareTo(arg0.getProgramTypeProgramCourse());
		}else
		return 0;
	}
	

}
