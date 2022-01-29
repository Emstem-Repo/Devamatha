package com.kp.cms.bo.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Program;

public class InterviewSelectionSchedule implements java.io.Serializable{
	private int id;
	private Date selectionProcessDate;
	private Date cutOffDate;
	private int maxNumOfSeatsOnline;
	private int maxNumOfSeatsOffline;
	private Program programId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String academicYear;
	private Set<InterviewTimeSelection> interviewTimeSelections=new HashSet<InterviewTimeSelection>(0);
	//private Set<InterviewVenueSelection> interviewVenueSelections=new HashSet<InterviewVenueSelection>(0);
	private ExamCenter examCenter;
	
	
	public Set<InterviewTimeSelection> getInterviewTimeSelections() {
		return interviewTimeSelections;
	}
	public void setInterviewTimeSelections(
			Set<InterviewTimeSelection> interviewTimeSelections) {
		this.interviewTimeSelections = interviewTimeSelections;
	}
	/*public Set<InterviewVenueSelection> getInterviewVenueSelections() {
		return interviewVenueSelections;
	}
	public void setInterviewVenueSelections(
			Set<InterviewVenueSelection> interviewVenueSelections) {
		this.interviewVenueSelections = interviewVenueSelections;
	}*/
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSelectionProcessDate() {
		return selectionProcessDate;
	}
	public void setSelectionProcessDate(Date selectionProcessDate) {
		this.selectionProcessDate = selectionProcessDate;
	}
	public Date getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public int getMaxNumOfSeatsOnline() {
		return maxNumOfSeatsOnline;
	}
	public void setMaxNumOfSeatsOnline(int maxNumOfSeatsOnline) {
		this.maxNumOfSeatsOnline = maxNumOfSeatsOnline;
	}
	public int getMaxNumOfSeatsOffline() {
		return maxNumOfSeatsOffline;
	}
	public void setMaxNumOfSeatsOffline(int maxNumOfSeatsOffline) {
		this.maxNumOfSeatsOffline = maxNumOfSeatsOffline;
	}
	public Program getProgramId() {
		return programId;
	}
	public void setProgramId(Program programId) {
		this.programId = programId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	//default constructor
	public InterviewSelectionSchedule(){
		
	}
	//fields constructor
	public InterviewSelectionSchedule(int id, Date selectionProcessDate,
			Date cutOffDate, int maxNumOfSeatsOnline, int maxNumOfSeatsOffline,
			Program programId, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			String academicYear,
			Set<InterviewTimeSelection> interviewTimeSelections,
			/*Set<InterviewVenueSelection> interviewVenueSelections,*/ExamCenter examCenter) {
		super();
		this.id = id;
		this.selectionProcessDate = selectionProcessDate;
		this.cutOffDate = cutOffDate;
		this.maxNumOfSeatsOnline = maxNumOfSeatsOnline;
		this.maxNumOfSeatsOffline = maxNumOfSeatsOffline;
		this.programId = programId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.academicYear = academicYear;
		this.interviewTimeSelections = interviewTimeSelections;
		//this.interviewVenueSelections = interviewVenueSelections;
		this.examCenter=examCenter;
	}
	public ExamCenter getExamCenter() {
		return examCenter;
	}
	public void setExamCenter(ExamCenter examCenter) {
		this.examCenter = examCenter;
	}
	
}
