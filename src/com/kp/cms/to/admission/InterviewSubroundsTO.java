package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.to.admin.CourseTO;

public class InterviewSubroundsTO implements Serializable,Comparable<InterviewSubroundsTO>{

	private int id;
	private String interviewTypeId;
	private String interviewTypeName;
	private String name;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String programTypeId;
	private String programId;
	private String courseId;
	private InterviewProgramCourseTO interviewProgramCourseTO;
	private int appliedYear;
	private CourseTO courseTO;
	private String isActive;
	private String cDate;
	private String lDate;
	private List<Grade> gradeList;
	private String courseName;
	private String year;
	private int noOfInterviewersPerPanel;
	private String combinedYear;
	private boolean intDefinitionSel;
	private String sequence;
	
	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getInterviewTypeId() {
		return interviewTypeId;
	}

	public void setInterviewTypeId(String interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}
	
	public String getInterviewTypeName() {
		return interviewTypeName;
	}

	public void setInterviewTypeName(String interviewTypeName) {
		this.interviewTypeName = interviewTypeName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public InterviewProgramCourseTO getInterviewProgramCourseTO() {
		return interviewProgramCourseTO;
	}

	public void setInterviewProgramCourseTO(
			InterviewProgramCourseTO interviewProgramCourseTO) {
		this.interviewProgramCourseTO = interviewProgramCourseTO;
	}

	public CourseTO getCourseTO() {
		return courseTO;
	}

	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getNoOfInterviewersPerPanel() {
		return noOfInterviewersPerPanel;
	}

	public void setNoOfInterviewersPerPanel(int noOfInterviewersPerPanel) {
		this.noOfInterviewersPerPanel = noOfInterviewersPerPanel;
	}

	public String getCombinedYear() {
		return combinedYear;
	}

	public void setCombinedYear(String combinedYear) {
		this.combinedYear = combinedYear;
	}

	public boolean isIntDefinitionSel() {
		return intDefinitionSel;
	}

	public void setIntDefinitionSel(boolean intDefinitionSel) {
		this.intDefinitionSel = intDefinitionSel;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public int compareTo(InterviewSubroundsTO arg0) {
		if(arg0!=null && this!=null && arg0.getName()!=null
				 && this.getName()!=null){
				return this.getName().compareTo(arg0.getName());
		}else
		return 0;
	}

	
}
