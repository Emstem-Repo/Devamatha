package com.kp.cms.bo.exam;

import java.util.Date;


public class ExamMarksEntryCorrectionDetailsBO extends ExamGenBO {
		private Integer marksEntryId;
		private int subjectId;
		private String theoryMarks;
		private String practicalMarks;

		// For Secured Marks Entry Screen
		private String previousEvaluatorTheoryMarks;
		private String previousEvaluatorPracticalMarks;
		private Integer isMistake;
		private Integer isRetest;

		// Many-to-one
		private SubjectUtilBO subjectUtilBO;
		private ExamMarksEntryBO examMarksEntryBO;
		private String comments;
		private Integer overAllId;
		private Integer finalMarkId;
		private Date lastModifiedDate;

		public ExamMarksEntryCorrectionDetailsBO() {
			super();
		}

		public ExamMarksEntryCorrectionDetailsBO(Integer marksEntryId,
				int subjectId, String theoryMarks, String practicalMarks,
				String previousEvaluatorTheoryMarks,
				String previousEvaluatorPracticalMarks, Integer isMistake,
				Integer isRetest, SubjectUtilBO subjectUtilBO,
				ExamMarksEntryBO examMarksEntryBO, String comments,
				Integer overAllId, Integer finalMarkId) {
			super();
			this.marksEntryId = marksEntryId;
			this.subjectId = subjectId;
			this.theoryMarks = theoryMarks;
			this.practicalMarks = practicalMarks;
			this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
			this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
			this.isMistake = isMistake;
			this.isRetest = isRetest;
			this.subjectUtilBO = subjectUtilBO;
			this.examMarksEntryBO = examMarksEntryBO;
			this.comments = comments;
			this.overAllId = overAllId;
			this.finalMarkId = finalMarkId;
			this.lastModifiedDate = new Date();
		}


		public Integer getMarksEntryId() {
			return marksEntryId;
		}

		public void setMarksEntryId(Integer marksEntryId) {
			this.marksEntryId = marksEntryId;
		}

		public int getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
		}

		public String getTheoryMarks() {
			return theoryMarks;
		}

		public void setTheoryMarks(String theoryMarks) {
			this.theoryMarks = theoryMarks;
		}

		public String getPracticalMarks() {
			return practicalMarks;
		}

		public void setPracticalMarks(String practicalMarks) {
			this.practicalMarks = practicalMarks;
		}

		public SubjectUtilBO getSubjectUtilBO() {
			return subjectUtilBO;
		}

		public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
			this.subjectUtilBO = subjectUtilBO;
		}

		public void setExamMarksEntryBO(ExamMarksEntryBO examMarksEntryBO) {
			this.examMarksEntryBO = examMarksEntryBO;
		}

		public ExamMarksEntryBO getExamMarksEntryBO() {
			return examMarksEntryBO;
		}

		public String getPreviousEvaluatorTheoryMarks() {
			return previousEvaluatorTheoryMarks;
		}

		public void setPreviousEvaluatorTheoryMarks(
				String previousEvaluatorTheoryMarks) {
			this.previousEvaluatorTheoryMarks = previousEvaluatorTheoryMarks;
		}

		public String getPreviousEvaluatorPracticalMarks() {
			return previousEvaluatorPracticalMarks;
		}

		public void setPreviousEvaluatorPracticalMarks(
				String previousEvaluatorPracticalMarks) {
			this.previousEvaluatorPracticalMarks = previousEvaluatorPracticalMarks;
		}

		public Integer getIsMistake() {
			return isMistake;
		}

		public void setIsMistake(Integer isMistake) {
			this.isMistake = isMistake;
		}

		public Integer getIsRetest() {
			return isRetest;
		}

		public void setIsRetest(Integer isRetest) {
			this.isRetest = isRetest;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getComments() {
			return comments;
		}

		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}

		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}

		public Integer getOverAllId() {
			return overAllId;
		}

		public void setOverAllId(Integer overAllId) {
			this.overAllId = overAllId;
		}

		public Integer getFinalMarkId() {
			return finalMarkId;
		}

		public void setFinalMarkId(Integer finalMarkId) {
			this.finalMarkId = finalMarkId;
		}
		
		
		
}


