package com.kp.cms.bo.examallotment;

	import java.io.Serializable;
	import java.util.Date;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
	import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.studentfeedback.RoomMaster;

	public class ExamInviligatorDuties implements Serializable{
		private int id;
		private ExamDefinition examId;
		private RoomMaster roomId;
		private String invOrReliver;
		private Users teacherId;
		private Date examDate;
		private String session;
		private EmployeeWorkLocationBO workLocationId; 
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private Boolean isActive;
		private Boolean isApproved;
		private ExaminationSessions examinationSessions;
		
		public ExamInviligatorDuties(){
			
		}
		
		public ExamInviligatorDuties(int id, ExamDefinition examId,
				RoomMaster roomId, String invOrReliver, Users teacherId,EmployeeWorkLocationBO workLocationId,
				Date examDate, String session, String createdBy, Date createdDate, String modifiedBy,
				Date lastModifiedDate, Boolean isActive, Boolean isApproved,ExaminationSessions examinationSessions) {
			super();
			this.id = id;
			this.examId = examId;
			this.roomId = roomId;
			this.invOrReliver = invOrReliver;
			this.teacherId = teacherId;
			this.workLocationId= workLocationId;
			this.examDate = examDate;
			this.session= session;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.lastModifiedDate = lastModifiedDate;
			this.isActive = isActive;
			this.isApproved= isApproved;
			this.examinationSessions=examinationSessions;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public ExamDefinition getExamId() {
			return examId;
		}

		public void setExamId(ExamDefinition examId) {
			this.examId = examId;
		}

		public RoomMaster getRoomId() {
			return roomId;
		}

		public void setRoomId(RoomMaster roomId) {
			this.roomId = roomId;
		}

		public Users getTeacherId() {
			return teacherId;
		}

		public void setTeacherId(Users teacherId) {
			this.teacherId = teacherId;
		}

		public Date getExamDate() {
			return examDate;
		}

		public void setExamDate(Date examDate) {
			this.examDate = examDate;
		}

		public String getSession() {
			return session;
		}

		public void setSession(String session) {
			this.session = session;
		}

		public EmployeeWorkLocationBO getWorkLocationId() {
			return workLocationId;
		}

		public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
			this.workLocationId = workLocationId;
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

		public String getInvOrReliver() {
			return invOrReliver;
		}

		public void setInvOrReliver(String invOrReliver) {
			this.invOrReliver = invOrReliver;
		}

		public Boolean getIsApproved() {
			return isApproved;
		}

		public void setIsApproved(Boolean isApproved) {
			this.isApproved = isApproved;
		}

		public ExaminationSessions getExaminationSessions() {
			return examinationSessions;
		}

		public void setExaminationSessions(ExaminationSessions examinationSessions) {
			this.examinationSessions = examinationSessions;
		}
		
		
}