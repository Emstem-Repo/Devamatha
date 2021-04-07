package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kp.cms.to.admin.CourseTO;
/**
 * 
 * @author kshirod.k
 * A TO Class for Curriculumscheme Entry
 */
@SuppressWarnings("serial")
public class CurriculumSchemeTO implements Serializable {
	private String subjectname;
	private int subjectid;
	private String courseName;
	private String programTypeName;
	private String programName;
	private CourseTO courseTO;
	private String startDate;
	private String endDate;
	private int year;
	private int semister; 
	private int id;
	private int courseId;
	private int programId;
	private int programTypeId;
	private int courseSchemeId;
	private int noOfScheme;
	private String courseSchemeName; 
	private String subjectGroup[];
	private List<CurriculumSchemeDurationTO> curriculumDurationList;

	public String getCourseSchemeName() {
		return courseSchemeName;
	}

	public void setCourseSchemeName(String courseSchemeName) {
		this.courseSchemeName = courseSchemeName;
	}

	public int getNoOfScheme() {
		return noOfScheme;
	}

	public void setNoOfScheme(int noOfScheme) {
		this.noOfScheme = noOfScheme;
	}

	private Set<CurriculumSchemeDurationTO>curriculumSchemeDurationTO = new HashSet<CurriculumSchemeDurationTO>();	
	
	

	
	public int getCourseSchemeId() {
		return courseSchemeId;
	}

	public void setCourseSchemeId(int courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	
	
	public Set<CurriculumSchemeDurationTO> getCurriculumSchemeDurationTO() {
		return curriculumSchemeDurationTO;
	}

	public void setCurriculumSchemeDurationTO(
			Set<CurriculumSchemeDurationTO> curriculumSchemeDurationTO) {
		this.curriculumSchemeDurationTO = curriculumSchemeDurationTO;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemister() {
		return semister;
	}

	public void setSemister(int semister) {
		this.semister = semister;
	}



	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public String[] getSubjectGroup() {
		return subjectGroup;
	}

	public void setSubjectGroup(String[] subjectGroup) {
		this.subjectGroup = subjectGroup;
	}

	public CourseTO getCourseTO() {
		return courseTO;
	}

	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
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

	public List<CurriculumSchemeDurationTO> getCurriculumDurationList() {
		return curriculumDurationList;
	}

	public void setCurriculumDurationList(
			List<CurriculumSchemeDurationTO> curriculumDurationList) {
		this.curriculumDurationList = curriculumDurationList;
	}

}
