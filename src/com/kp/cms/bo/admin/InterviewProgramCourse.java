package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.kp.cms.bo.admin.ApplicantRecommendor;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Weightage;

/**
 * InterviewProgramCourse generated by hbm2java
 */
public class InterviewProgramCourse implements java.io.Serializable {

	private Integer id = 0;
	private Program program;
	private Weightage weightage;
	private Course course;
	private String sequence;
	private Integer year;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String name;
	private Boolean isActive;
	private BigDecimal weightagePercentage;
	private String content;
	private Integer noOfInterviewsPerPanel;
	private Set<ApplicantRecommendor> applicantRecommendors = new HashSet<ApplicantRecommendor>(
			0);
	private Set<InterviewSelected> interviewSelecteds = new HashSet<InterviewSelected>(
			0);
	private Set<Interview> interviews = new HashSet<Interview>(0);
	private Set<InterviewResult> interviewResults = new HashSet<InterviewResult>(
			0);
	private Set<InterviewSubRounds> interviewSubRoundses = new HashSet<InterviewSubRounds>(
			0);

	public InterviewProgramCourse() {
	}

	public InterviewProgramCourse(Integer id) {
		this.id = id;
	}

	public InterviewProgramCourse(Integer id, Program program,
			Weightage weightage, Course course, String sequence, Integer year,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, String name, Boolean isActive,
			BigDecimal weightagePercentage, String content,
			Set<ApplicantRecommendor> applicantRecommendors,
			Set<InterviewSelected> interviewSelecteds,
			Integer noOfInterviewsPerPanel, Set<Interview> interviews,
			Set<InterviewResult> interviewResults,
			Set<InterviewSubRounds> interviewSubRoundses) {
		this.id = id;
		this.program = program;
		this.weightage = weightage;
		this.course = course;
		this.sequence = sequence;
		this.year = year;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.name = name;
		this.isActive = isActive;
		this.weightagePercentage = weightagePercentage;
		this.content = content;
		this.noOfInterviewsPerPanel = noOfInterviewsPerPanel;
		this.applicantRecommendors = applicantRecommendors;
		this.interviewSelecteds = interviewSelecteds;
		this.interviews = interviews;
		this.interviewResults = interviewResults;
		this.interviewSubRoundses = interviewSubRoundses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Weightage getWeightage() {
		return this.weightage;
	}

	public void setWeightage(Weightage weightage) {
		this.weightage = weightage;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getWeightagePercentage() {
		return this.weightagePercentage;
	}

	public void setWeightagePercentage(BigDecimal weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<ApplicantRecommendor> getApplicantRecommendors() {
		return this.applicantRecommendors;
	}

	public void setApplicantRecommendors(
			Set<ApplicantRecommendor> applicantRecommendors) {
		this.applicantRecommendors = applicantRecommendors;
	}

	public Set<InterviewSelected> getInterviewSelecteds() {
		return this.interviewSelecteds;
	}

	public void setInterviewSelecteds(Set<InterviewSelected> interviewSelecteds) {
		this.interviewSelecteds = interviewSelecteds;
	}

	public Set<Interview> getInterviews() {
		return this.interviews;
	}

	public void setInterviews(Set<Interview> interviews) {
		this.interviews = interviews;
	}

	public Set<InterviewResult> getInterviewResults() {
		return this.interviewResults;
	}

	public void setInterviewResults(Set<InterviewResult> interviewResults) {
		this.interviewResults = interviewResults;
	}

	public Set<InterviewSubRounds> getInterviewSubRoundses() {
		return this.interviewSubRoundses;
	}

	public void setInterviewSubRoundses(
			Set<InterviewSubRounds> interviewSubRoundses) {
		this.interviewSubRoundses = interviewSubRoundses;
	}

	public Integer getNoOfInterviewsPerPanel() {
		return noOfInterviewsPerPanel;
	}

	public void setNoOfInterviewsPerPanel(Integer noOfInterviewsPerPanel) {
		this.noOfInterviewsPerPanel = noOfInterviewsPerPanel;
	}

}