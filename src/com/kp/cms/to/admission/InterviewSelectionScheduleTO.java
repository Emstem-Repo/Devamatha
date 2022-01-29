package com.kp.cms.to.admission;

import java.util.Date;

import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;

public class InterviewSelectionScheduleTO implements java.io.Serializable,Comparable<InterviewSelectionScheduleTO>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int programId;
	private String selectionProcessDate;
	private String cutOffDate;
	private int maxNoSeatsOnline;
	private int maxNoSeatsOffline;
	private Boolean isActive;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private String programName;
	private String academicYear;
	private String allottedSeats;
	private String selectionProcessVenue;
	private String examCenterId;
	private String examCentreName;
	private String venueName;
	private int totalAppliedStudents;
	
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getSelectionProcessDate() {
		return selectionProcessDate;
	}
	public void setSelectionProcessDate(String selectionProcessDate) {
		this.selectionProcessDate = selectionProcessDate;
	}
	public String getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public int getMaxNoSeatsOnline() {
		return maxNoSeatsOnline;
	}
	public void setMaxNoSeatsOnline(int maxNoSeatsOnline) {
		this.maxNoSeatsOnline = maxNoSeatsOnline;
	}
	public int getMaxNoSeatsOffline() {
		return maxNoSeatsOffline;
	}
	public void setMaxNoSeatsOffline(int maxNoSeatsOffline) {
		this.maxNoSeatsOffline = maxNoSeatsOffline;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getAllottedSeats() {
		return allottedSeats;
	}
	public void setAllottedSeats(String allottedSeats) {
		this.allottedSeats = allottedSeats;
	}
	
	public int compareTo(InterviewSelectionScheduleTO arg0) {
		if(arg0 instanceof InterviewSelectionScheduleTO && arg0.getSelectionProcessDate()!=null ){
			return this.getSelectionProcessDate().compareTo(arg0.getSelectionProcessDate());
	}else
		return 0;
	}
	public String getSelectionProcessVenue() {
		return selectionProcessVenue;
	}
	public void setSelectionProcessVenue(String selectionProcessVenue) {
		this.selectionProcessVenue = selectionProcessVenue;
	}
	public String getExamCenterId() {
		return examCenterId;
	}
	public void setExamCenterId(String examCenterId) {
		this.examCenterId = examCenterId;
	}
	public String getExamCentreName() {
		return examCentreName;
	}
	public void setExamCentreName(String examCentreName) {
		this.examCentreName = examCentreName;
	}
	
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public int getTotalAppliedStudents() {
		return totalAppliedStudents;
	}
	public void setTotalAppliedStudents(int totalAppliedStudents) {
		this.totalAppliedStudents = totalAppliedStudents;
	}
	
}
