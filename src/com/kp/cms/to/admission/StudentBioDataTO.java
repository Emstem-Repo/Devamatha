package com.kp.cms.to.admission;

import java.util.List;

import com.kp.cms.to.admin.StudentTO;

	public class StudentBioDataTO {
		private int studentId;
		private int specializationId;
		private int secondLangaugeId;
		private String consolidatedMarksCardNo;
		private String courseNameForMarksCard;
		private int courseId;
		private String regNo;
		private String specializationNotExists;
		
		public int getStudentId() {
			return studentId;
		}
		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}
		public int getSpecializationId() {
			return specializationId;
		}
		public void setSpecializationId(int specializationId) {
			this.specializationId = specializationId;
		}
		public int getSecondLangaugeId() {
			return secondLangaugeId;
		}
		public void setSecondLangaugeId(int secondLangaugeId) {
			this.secondLangaugeId = secondLangaugeId;
		}
		public String getConsolidatedMarksCardNo() {
			return consolidatedMarksCardNo;
		}
		public void setConsolidatedMarksCardNo(String consolidatedMarksCardNo) {
			this.consolidatedMarksCardNo = consolidatedMarksCardNo;
		}
		public String getCourseNameForMarksCard() {
			return courseNameForMarksCard;
		}
		public void setCourseNameForMarksCard(String courseNameForMarksCard) {
			this.courseNameForMarksCard = courseNameForMarksCard;
		}
		public int getCourseId() {
			return courseId;
		}
		public void setCourseId(int courseId) {
			this.courseId = courseId;
		}
		public String getRegNo() {
			return regNo;
		}
		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}
		public String getSpecializationNotExists() {
			return specializationNotExists;
		}
		public void setSpecializationNotExists(String specializationNotExists) {
			this.specializationNotExists = specializationNotExists;
		}
		
}
