package com.kp.cms.to.admin;

import java.io.Serializable;

import com.kp.cms.to.admission.ApplicationNumberTO;

public class CourseApplicationNoTO implements Serializable{
	private int id;
	private CourseTO courseTO;
	private ApplicationNumberTO applicationNumberTO;
	private int applNoId;
	private int programId;
	private ProgramTO programTO;
	private int programTypeId;
	private ProgramTO selectedProgram;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public ApplicationNumberTO getApplicationNumberTO() {
		return applicationNumberTO;
	}
	public void setApplicationNumberTO(ApplicationNumberTO applicationNumberTO) {
		this.applicationNumberTO = applicationNumberTO;
	}
	public int getApplNoId() {
		return applNoId;
	}
	public void setApplNoId(int applNoId) {
		this.applNoId = applNoId;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public int getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}
	public ProgramTO getProgramTO() {
		return programTO;
	}
	public void setProgramTO(ProgramTO programTO) {
		this.programTO = programTO;
	}
	public ProgramTO getSelectedProgram() {
		return selectedProgram;
	}
	public void setSelectedProgram(ProgramTO selectedProgram) {
		this.selectedProgram = selectedProgram;
	}
	
	
}
