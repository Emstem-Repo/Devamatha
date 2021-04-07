package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.studentfeedback.RoomMaster;

public class ExamRoomAllotment implements Serializable{
	private int id;
	private ExamDefinition examDefinition;
	private RoomMaster room;
	private String midOrEndSem;
	private ExamRoomAllotmentCycle cycle;
	private Date date;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamRoomAllotmentDetails> roomAllotmentDetails = new HashSet<ExamRoomAllotmentDetails>();
	private ExaminationSessions examinationSessions;
	private Boolean fillAllColumns;
	public ExamRoomAllotment(){
		
	}
	
	
	

	public ExamRoomAllotment(int id, ExamDefinition examDefinition,
			RoomMaster room, String midOrEndSem, ExamRoomAllotmentCycle cycle,
			Date date, String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,
			Set<ExamRoomAllotmentDetails> roomAllotmentDetails) {
		super();
		this.id = id;
		this.examDefinition = examDefinition;
		this.room = room;
		this.midOrEndSem = midOrEndSem;
		this.cycle = cycle;
		this.date = date;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.roomAllotmentDetails = roomAllotmentDetails;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinition getExamDefinition() {
		return examDefinition;
	}

	public void setExamDefinition(ExamDefinition examDefinition) {
		this.examDefinition = examDefinition;
	}

	public RoomMaster getRoom() {
		return room;
	}

	public void setRoom(RoomMaster room) {
		this.room = room;
	}

	public String getMidOrEndSem() {
		return midOrEndSem;
	}

	public void setMidOrEndSem(String midOrEndSem) {
		this.midOrEndSem = midOrEndSem;
	}

	public ExamRoomAllotmentCycle getCycle() {
		return cycle;
	}

	public void setCycle(ExamRoomAllotmentCycle cycle) {
		this.cycle = cycle;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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




	public Set<ExamRoomAllotmentDetails> getRoomAllotmentDetails() {
		return roomAllotmentDetails;
	}




	public void setRoomAllotmentDetails(
			Set<ExamRoomAllotmentDetails> roomAllotmentDetails) {
		this.roomAllotmentDetails = roomAllotmentDetails;
	}




	public ExaminationSessions getExaminationSessions() {
		return examinationSessions;
	}




	public void setExaminationSessions(ExaminationSessions examinationSessions) {
		this.examinationSessions = examinationSessions;
	}




	public Boolean getFillAllColumns() {
		return fillAllColumns;
	}




	public void setFillAllColumns(Boolean fillAllColumns) {
		this.fillAllColumns = fillAllColumns;
	}
	
	
}
