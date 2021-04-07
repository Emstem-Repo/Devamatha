package com.kp.cms.to.admin;

import java.util.List;

public class SyllabusEntryHodApprovalTo implements Comparable<SyllabusEntryHodApprovalTo>{
	private SyllabusEntryProgramDetailsTo programTo;
	private String courseName;
	private String programDetails;
	private String courseId;
	private List<SyllabusEntryTo> syllabusEntryTos;
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public SyllabusEntryProgramDetailsTo getProgramTo() {
		return programTo;
	}
	public void setProgramTo(SyllabusEntryProgramDetailsTo programTo) {
		this.programTo = programTo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProgramDetails() {
		return programDetails;
	}
	public void setProgramDetails(String programDetails) {
		this.programDetails = programDetails;
	}
	public List<SyllabusEntryTo> getSyllabusEntryTos() {
		return syllabusEntryTos;
	}
	public void setSyllabusEntryTos(List<SyllabusEntryTo> syllabusEntryTos) {
		this.syllabusEntryTos = syllabusEntryTos;
	}
	@Override
	public int compareTo(SyllabusEntryHodApprovalTo arg0) {
		if(arg0 instanceof SyllabusEntryHodApprovalTo && arg0.getCourseName()!=null ){
			return this.getCourseName().compareTo(arg0.getCourseName());
		}else{
			return 0;
		}
	}
}
