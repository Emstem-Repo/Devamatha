package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class ProgressSubjectTO extends BaseActionForm implements Comparable<ProgressSubjectTO>{
	
		private int id;
		private String name;
		private String code;
		private String optional;
		private String secondlanguage;
		private String passingmarks;
		private String totalmarks;
		private String nameCode;
		private String questionbyrequired;
		private String hourpersem;
		private String attPressent;
		private String attMax;
		private double attPercentage;
		
		//Added by Shwetha - 9Elements
		private String theoryPractical;
		private String subjectType;
		private boolean checked;
		
		
		// Added  by balaji
		
		private String eseMaxMarks;
		private String firstInternalMarksAwarded;
		private String secondInternalMarksAwarded;
		private String firstInternalMarksMax;
		private String secondInternalMarksMax;
		private String eseMinMarks;
		private String eseMarksAwarded;
		private String credits;
		private String grade;
		private boolean practical;
		private String practicalEseMaxMarks;
		private String practicalEseMinMarks;
		private String practicalEseMarksAwarded;
		private String practicalTotalMaxMarks;
		private String practicalTotalMarksAwarded;
		private String practicalCredits;
		private String practicalGrade;
		private int subOrder;
		private boolean theory;
		private int order;
		private String subjectID;
		private String schemeNo;
		private String status;
		private boolean revaluationReq;
		private String revType;
		private String theoryMarks;
		private String practicalMarks;
		private String dateofBirth;
		private String semesterNo;
		private String examName;
		private String courseName;
		private String secName;
		
		private String gradePoints;
		private String theoryGradePnt;
		private String practicalGradePnt;
		private String creditPoint;
		private String resultClass;
		private String totalGradePoints;
		private String avgmark;
		//raghu
		private String examType;
		private String theoryCreditPoint;
		private String practicalCreditPoint;
		private String grievanceRemark;
		private int term;
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getOptional() {
			return optional;
		}
		public void setOptional(String optional) {
			this.optional = optional;
		}
		public String getSecondlanguage() {
			return secondlanguage;
		}
		public void setSecondlanguage(String secondlanguage) {
			this.secondlanguage = secondlanguage;
		}
		public String getPassingmarks() {
			return passingmarks;
		}
		public void setPassingmarks(String passingmarks) {
			this.passingmarks = passingmarks;
		}
		public String getTotalmarks() {
			return totalmarks;
		}
		public void setTotalmarks(String totalmarks) {
			this.totalmarks = totalmarks;
		}
		public String getNameCode() {
			return nameCode;
		}
		public void setNameCode(String nameCode) {
			this.nameCode = nameCode;
		}
		public String getQuestionbyrequired() {
			return questionbyrequired;
		}
		public void setQuestionbyrequired(String questionbyrequired) {
			this.questionbyrequired = questionbyrequired;
		}
		public String getHourpersem() {
			return hourpersem;
		}
		public void setHourpersem(String hourpersem) {
			this.hourpersem = hourpersem;
		}
		public String getTheoryPractical() {
			return theoryPractical;
		}
		public void setTheoryPractical(String theoryPractical) {
			this.theoryPractical = theoryPractical;
		}
		public String getSubjectType() {
			return subjectType;
		}
		public void setSubjectType(String subjectType) {
			this.subjectType = subjectType;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public String getEseMaxMarks() {
			return eseMaxMarks;
		}
		public void setEseMaxMarks(String eseMaxMarks) {
			this.eseMaxMarks = eseMaxMarks;
		}
		public String getFirstInternalMarksAwarded() {
			return firstInternalMarksAwarded;
		}
		public void setFirstInternalMarksAwarded(String firstInternalMarksAwarded) {
			this.firstInternalMarksAwarded = firstInternalMarksAwarded;
		}
		public String getSecondInternalMarksAwarded() {
			return secondInternalMarksAwarded;
		}
		public void setSecondInternalMarksAwarded(String secondInternalMarksAwarded) {
			this.secondInternalMarksAwarded = secondInternalMarksAwarded;
		}
		public String getEseMinMarks() {
			return eseMinMarks;
		}
		public void setEseMinMarks(String eseMinMarks) {
			this.eseMinMarks = eseMinMarks;
		}
		public String getEseMarksAwarded() {
			return eseMarksAwarded;
		}
		public void setEseMarksAwarded(String eseMarksAwarded) {
			this.eseMarksAwarded = eseMarksAwarded;
		}
		public String getCredits() {
			return credits;
		}
		public void setCredits(String credits) {
			this.credits = credits;
		}
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public boolean isPractical() {
			return practical;
		}
		public void setPractical(boolean practical) {
			this.practical = practical;
		}
		public String getPracticalEseMaxMarks() {
			return practicalEseMaxMarks;
		}
		public void setPracticalEseMaxMarks(String practicalEseMaxMarks) {
			this.practicalEseMaxMarks = practicalEseMaxMarks;
		}
		public String getPracticalEseMinMarks() {
			return practicalEseMinMarks;
		}
		public void setPracticalEseMinMarks(String practicalEseMinMarks) {
			this.practicalEseMinMarks = practicalEseMinMarks;
		}
		public String getPracticalEseMarksAwarded() {
			return practicalEseMarksAwarded;
		}
		public void setPracticalEseMarksAwarded(String practicalEseMarksAwarded) {
			this.practicalEseMarksAwarded = practicalEseMarksAwarded;
		}
		public String getPracticalTotalMaxMarks() {
			return practicalTotalMaxMarks;
		}
		public void setPracticalTotalMaxMarks(String practicalTotalMaxMarks) {
			this.practicalTotalMaxMarks = practicalTotalMaxMarks;
		}
		public String getPracticalTotalMarksAwarded() {
			return practicalTotalMarksAwarded;
		}
		public void setPracticalTotalMarksAwarded(String practicalTotalMarksAwarded) {
			this.practicalTotalMarksAwarded = practicalTotalMarksAwarded;
		}
		public String getPracticalCredits() {
			return practicalCredits;
		}
		public void setPracticalCredits(String practicalCredits) {
			this.practicalCredits = practicalCredits;
		}
		public String getPracticalGrade() {
			return practicalGrade;
		}
		public void setPracticalGrade(String practicalGrade) {
			this.practicalGrade = practicalGrade;
		}
		public int getSubOrder() {
			return subOrder;
		}
		public void setSubOrder(int subOrder) {
			this.subOrder = subOrder;
		}
		public boolean isTheory() {
			return theory;
		}
		public void setTheory(boolean theory) {
			this.theory = theory;
		}
		public int getOrder() {
			return order;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public String getSubjectID() {
			return subjectID;
		}
		public void setSubjectID(String subjectID) {
			this.subjectID = subjectID;
		}
		public String getSchemeNo() {
			return schemeNo;
		}
		public void setSchemeNo(String schemeNo) {
			this.schemeNo = schemeNo;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public boolean isRevaluationReq() {
			return revaluationReq;
		}
		public void setRevaluationReq(boolean revaluationReq) {
			this.revaluationReq = revaluationReq;
		}
		public String getRevType() {
			return revType;
		}
		public void setRevType(String revType) {
			this.revType = revType;
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
		public String getDateofBirth() {
			return dateofBirth;
		}
		public void setDateofBirth(String dateofBirth) {
			this.dateofBirth = dateofBirth;
		}
		public String getSemesterNo() {
			return semesterNo;
		}
		public void setSemesterNo(String semesterNo) {
			this.semesterNo = semesterNo;
		}
		public String getExamName() {
			return examName;
		}
		public void setExamName(String examName) {
			this.examName = examName;
		}
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getSecName() {
			return secName;
		}
		public void setSecName(String secName) {
			this.secName = secName;
		}
		public String getGradePoints() {
			return gradePoints;
		}
		public void setGradePoints(String gradePoints) {
			this.gradePoints = gradePoints;
		}
		public String getTheoryGradePnt() {
			return theoryGradePnt;
		}
		public void setTheoryGradePnt(String theoryGradePnt) {
			this.theoryGradePnt = theoryGradePnt;
		}
		public String getPracticalGradePnt() {
			return practicalGradePnt;
		}
		public void setPracticalGradePnt(String practicalGradePnt) {
			this.practicalGradePnt = practicalGradePnt;
		}
		public String getCreditPoint() {
			return creditPoint;
		}
		public void setCreditPoint(String creditPoint) {
			this.creditPoint = creditPoint;
		}
		public String getResultClass() {
			return resultClass;
		}
		public void setResultClass(String resultClass) {
			this.resultClass = resultClass;
		}
		public String getTotalGradePoints() {
			return totalGradePoints;
		}
		public void setTotalGradePoints(String totalGradePoints) {
			this.totalGradePoints = totalGradePoints;
		}
		public String getAvgmark() {
			return avgmark;
		}
		public void setAvgmark(String avgmark) {
			this.avgmark = avgmark;
		}
		public String getExamType() {
			return examType;
		}
		public void setExamType(String examType) {
			this.examType = examType;
		}
		public String getTheoryCreditPoint() {
			return theoryCreditPoint;
		}
		public void setTheoryCreditPoint(String theoryCreditPoint) {
			this.theoryCreditPoint = theoryCreditPoint;
		}
		public String getPracticalCreditPoint() {
			return practicalCreditPoint;
		}
		public void setPracticalCreditPoint(String practicalCreditPoint) {
			this.practicalCreditPoint = practicalCreditPoint;
		}
		public String getGrievanceRemark() {
			return grievanceRemark;
		}
		public void setGrievanceRemark(String grievanceRemark) {
			this.grievanceRemark = grievanceRemark;
		}
		public String getAttPressent() {
			return attPressent;
		}
		public void setAttPressent(String attPressent) {
			this.attPressent = attPressent;
		}
		public String getAttMax() {
			return attMax;
		}
		public void setAttMax(String attMax) {
			this.attMax = attMax;
		}
		public int getTerm() {
			return term;
		}
		public void setTerm(int term) {
			this.term = term;
		}
		public String getFirstInternalMarksMax() {
			return firstInternalMarksMax;
		}
		public void setFirstInternalMarksMax(String firstInternalMarksMax) {
			this.firstInternalMarksMax = firstInternalMarksMax;
		}
		public String getSecondInternalMarksMax() {
			return secondInternalMarksMax;
		}
		public void setSecondInternalMarksMax(String secondInternalMarksMax) {
			this.secondInternalMarksMax = secondInternalMarksMax;
		}
		public double getAttPercentage() {
			return attPercentage;
		}
		public void setAttPercentage(double attPercentage) {
			this.attPercentage = attPercentage;
		}
		

		@Override
		public int compareTo(ProgressSubjectTO o) {
			Integer n1=new Integer(this.getOrder());
			Integer n2=new Integer(o.getOrder());
			return n1.compareTo(n2);
		}
		
		
		
}