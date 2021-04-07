package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;

public class InterviewProgramCourseTO implements Serializable{

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private ProgramTO program;
	private CourseTO course;
	private String totalMarks;
	private String passingMarks;
	private String sequence;
	private Date createdDate;
	private Date lastModifiedDate;
	private String name;
	private String content;
	private Set<InterviewTO> interviews = new HashSet<InterviewTO>(0);
	private Set<InterviewSelectedTO> interviewSelecteds = new HashSet<InterviewSelectedTO>(
			0);
	private int year;
	private String programTypeName;
	private String programName;
	private String courseName;
	private int programTypeId;
	private int programId;
	private int courseId;
	private String isActive;
	private String academicYear;
	private String cDate;
	private String lDate;
	private String interviewsPerPanel;
	private String combinedYear;
	private boolean intDefinitionSel;
	
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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ProgramTO getProgram() {
		return program;
	}

	public void setProgram(ProgramTO program) {
		this.program = program;
	}

	public CourseTO getCourse() {
		return course;
	}

	public String getInterviewsPerPanel() {
		return interviewsPerPanel;
	}

	public void setInterviewsPerPanel(String interviewsPerPanel) {
		this.interviewsPerPanel = interviewsPerPanel;
	}

	public void setCourse(CourseTO course) {
		this.course = course;
	}

	public String getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}

	public String getPassingMarks() {
		return passingMarks;
	}

	public void setPassingMarks(String passingMarks) {
		this.passingMarks = passingMarks;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<InterviewTO> getInterviews() {
		return interviews;
	}

	public void setInterviews(Set<InterviewTO> interviews) {
		this.interviews = interviews;
	}

	public Set<InterviewSelectedTO> getInterviewSelecteds() {
		return interviewSelecteds;
	}

	public void setInterviewSelecteds(
			Set<InterviewSelectedTO> interviewSelecteds) {
		this.interviewSelecteds = interviewSelecteds;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

}
