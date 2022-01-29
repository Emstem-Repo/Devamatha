	package com.kp.cms.bo.exam;

	import java.util.Date;

import com.kp.cms.bo.admin.Student;

	public class ExamMidSemRepeatExemption implements java.io.Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private Student studentId;
		private ExamDefinitionBO midsemExamId;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private Boolean isActive;
		
		public ExamMidSemRepeatExemption(){
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Student getStudentId() {
			return studentId;
		}

		public void setStudentId(Student studentId) {
			this.studentId = studentId;
		}

		public ExamDefinitionBO getMidsemExamId() {
			return midsemExamId;
		}

		public void setMidsemExamId(ExamDefinitionBO midsemExamId) {
			this.midsemExamId = midsemExamId;
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

		public ExamMidSemRepeatExemption(Student studentId,
				ExamDefinitionBO midsemExamId, String createdBy,
				Boolean isActive) {
			super();
			this.studentId = studentId;
			this.midsemExamId = midsemExamId;
			this.createdBy = createdBy;
			this.isActive = isActive;
		}


	}


