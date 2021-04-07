package com.kp.cms.to.exam;

import java.util.Date;

	public class UploadSupplementaryAppTO {
		private int examId;
		private int studentId;
		private int subjectId;
		private int isSupplementary;
		private int isImprovement;
		private int isFailedTheory;
		private int isFailedPractical;
		private int isAppearedTheory;
		private int isAppearedPractical;
		private String fees;
		private int chance;
		private int schemeNo;
		private String examName;
		private String subjectCode;
		private String regNo;
		
		public UploadSupplementaryAppTO(int examId, int studentId,
				int subjectId, int isSupplementary, int isImprovement,
				int isFailedTheory, int isFailedPractical, int isAppearedTheory,
				int isAppearedPractical, String fees, String userId) {
			super();
			this.examId = examId;
			this.studentId = studentId;
			this.subjectId = subjectId;
			this.isSupplementary = isSupplementary;
			this.isImprovement = isImprovement;
			this.isFailedTheory = isFailedTheory;
			this.isFailedPractical = isFailedPractical;
			this.isAppearedTheory = isAppearedTheory;
			this.isAppearedPractical = isAppearedPractical;
			this.fees = fees;
		}

		public UploadSupplementaryAppTO() {
		}

		public int getExamId() {
			return examId;
		}

		public void setExamId(int examId) {
			this.examId = examId;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public int getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
		}

		public int getIsSupplementary() {
			return isSupplementary;
		}

		public void setIsSupplementary(int isSupplementary) {
			this.isSupplementary = isSupplementary;
		}

		public int getIsImprovement() {
			return isImprovement;
		}

		public void setIsImprovement(int isImprovement) {
			this.isImprovement = isImprovement;
		}

		public int getIsFailedTheory() {
			return isFailedTheory;
		}

		public void setIsFailedTheory(int isFailedTheory) {
			this.isFailedTheory = isFailedTheory;
		}

		public int getIsFailedPractical() {
			return isFailedPractical;
		}

		public void setIsFailedPractical(int isFailedPractical) {
			this.isFailedPractical = isFailedPractical;
		}

		public int getIsAppearedTheory() {
			return isAppearedTheory;
		}

		public void setIsAppearedTheory(int isAppearedTheory) {
			this.isAppearedTheory = isAppearedTheory;
		}

		public int getIsAppearedPractical() {
			return isAppearedPractical;
		}

		public void setIsAppearedPractical(int isAppearedPractical) {
			this.isAppearedPractical = isAppearedPractical;
		}

		public String getFees() {
			return fees;
		}

		public void setFees(String fees) {
			this.fees = fees;
		}

		public int getChance() {
			return chance;
		}

		public void setChance(int chance) {
			this.chance = chance;
		}

		public int getSchemeNo() {
			return schemeNo;
		}

		public void setSchemeNo(int schemeNo) {
			this.schemeNo = schemeNo;
		}

		public String getExamName() {
			return examName;
		}

		public void setExamName(String examName) {
			this.examName = examName;
		}

		public String getSubjectCode() {
			return subjectCode;
		}

		public void setSubjectCode(String subjectCode) {
			this.subjectCode = subjectCode;
		}

		public String getRegNo() {
			return regNo;
		}

		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}

}
