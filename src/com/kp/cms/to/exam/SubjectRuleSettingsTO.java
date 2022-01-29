package com.kp.cms.to.exam;

import java.io.Serializable;

public class SubjectRuleSettingsTO implements Serializable {
	
	private int subjectId;
	private String isTheoryPractical;
	private boolean isTheoryAttendanceCheck;
	private boolean isPracticalAttendanceCheck;
	private boolean isTheoryLeaveCheck;
	private boolean isPracticalLeaveCheck;
	private boolean isTheoryCoLeaveCheck;
	private boolean isPracticalCoLeaveCheck;
	private int theoryAttTypeId;
	private int practicalAttTypeId;
	private boolean isTheoryAssignmentCheck;
	private boolean isPracticalAssigntmentCheck;
	private boolean isTheoryInteralCheck;
	private boolean isPracticalInternalCheck;
	private int theoryBest;
	private int practicalBest;
	private int theoryMin;
	private int practicalMin;
	private boolean theoryIndCheck;
	private boolean practicalIndCheck;
	private boolean isTheoryRegularCheck;
	private boolean isPracticalRegularCheck;
	private boolean isTheoryEvalCheck;
	private boolean isPracticalEvalCheck;
	private boolean isTheoryAnsCheck;
	private boolean isPracticalAnsCheck;
	private double theoryEseEnteredMaxMarks;
	private double theoryEseMaxMarks;
	private double theoryEseMinMarks;
	private double practicalEseEnteredMaxMarks;
	private double practicalEseMaxMarks;
	private double practicalEseMinMarks;
	private String theoryTypeOfEval;
	private String practicalTypeOfEval;
	private int theoryNoOfEval;
	private int practicalNoOfEval;
	private String subjectName;
	private int theoryNoOfAns;
	private int practicalNoOfAns;
	private int reviewerId;
	private double theoryCiaMinMark;
	private double practicalCiaMinMark;

	
	public int getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}
	public int getTheoryAttTypeId() {
		return theoryAttTypeId;
	}
	public void setTheoryAttTypeId(int theoryAttTypeId) {
		this.theoryAttTypeId = theoryAttTypeId;
	}
	public int getPracticalAttTypeId() {
		return practicalAttTypeId;
	}
	public void setPracticalAttTypeId(int practicalAttTypeId) {
		this.practicalAttTypeId = practicalAttTypeId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}
	public boolean isTheoryAttendanceCheck() {
		return isTheoryAttendanceCheck;
	}
	public void setTheoryAttendanceCheck(boolean isTheoryAttendanceCheck) {
		this.isTheoryAttendanceCheck = isTheoryAttendanceCheck;
	}
	public boolean isPracticalAttendanceCheck() {
		return isPracticalAttendanceCheck;
	}
	public void setPracticalAttendanceCheck(boolean isPracticalAttendanceCheck) {
		this.isPracticalAttendanceCheck = isPracticalAttendanceCheck;
	}
	public boolean isTheoryLeaveCheck() {
		return isTheoryLeaveCheck;
	}
	public void setTheoryLeaveCheck(boolean isTheoryLeaveCheck) {
		this.isTheoryLeaveCheck = isTheoryLeaveCheck;
	}
	public boolean isPracticalLeaveCheck() {
		return isPracticalLeaveCheck;
	}
	public void setPracticalLeaveCheck(boolean isPracticalLeaveCheck) {
		this.isPracticalLeaveCheck = isPracticalLeaveCheck;
	}
	public boolean isTheoryCoLeaveCheck() {
		return isTheoryCoLeaveCheck;
	}
	public void setTheoryCoLeaveCheck(boolean isTheoryCoLeaveCheck) {
		this.isTheoryCoLeaveCheck = isTheoryCoLeaveCheck;
	}
	public boolean isPracticalCoLeaveCheck() {
		return isPracticalCoLeaveCheck;
	}
	public void setPracticalCoLeaveCheck(boolean isPracticalCoLeaveCheck) {
		this.isPracticalCoLeaveCheck = isPracticalCoLeaveCheck;
	}
	public boolean isTheoryAssignmentCheck() {
		return isTheoryAssignmentCheck;
	}
	public void setTheoryAssignmentCheck(boolean isTheoryAssignmentCheck) {
		this.isTheoryAssignmentCheck = isTheoryAssignmentCheck;
	}
	public boolean isPracticalAssigntmentCheck() {
		return isPracticalAssigntmentCheck;
	}
	public void setPracticalAssigntmentCheck(boolean isPracticalAssigntmentCheck) {
		this.isPracticalAssigntmentCheck = isPracticalAssigntmentCheck;
	}
	public boolean isTheoryInteralCheck() {
		return isTheoryInteralCheck;
	}
	public void setTheoryInteralCheck(boolean isTheoryInteralCheck) {
		this.isTheoryInteralCheck = isTheoryInteralCheck;
	}
	public boolean isPracticalInternalCheck() {
		return isPracticalInternalCheck;
	}
	public void setPracticalInternalCheck(boolean isPracticalInternalCheck) {
		this.isPracticalInternalCheck = isPracticalInternalCheck;
	}
	public int getTheoryBest() {
		return theoryBest;
	}
	public void setTheoryBest(int theoryBest) {
		this.theoryBest = theoryBest;
	}
	public int getPracticalBest() {
		return practicalBest;
	}
	public void setPracticalBest(int practicalBest) {
		this.practicalBest = practicalBest;
	}
	public int getTheoryMin() {
		return theoryMin;
	}
	public void setTheoryMin(int theoryMin) {
		this.theoryMin = theoryMin;
	}
	public int getPracticalMin() {
		return practicalMin;
	}
	public void setPracticalMin(int practicalMin) {
		this.practicalMin = practicalMin;
	}
	public boolean isTheoryIndCheck() {
		return theoryIndCheck;
	}
	public void setTheoryIndCheck(boolean theoryIndCheck) {
		this.theoryIndCheck = theoryIndCheck;
	}
	public boolean isPracticalIndCheck() {
		return practicalIndCheck;
	}
	public void setPracticalIndCheck(boolean practicalIndCheck) {
		this.practicalIndCheck = practicalIndCheck;
	}
	public boolean isTheoryRegularCheck() {
		return isTheoryRegularCheck;
	}
	public void setTheoryRegularCheck(boolean isTheoryRegularCheck) {
		this.isTheoryRegularCheck = isTheoryRegularCheck;
	}
	public boolean isPracticalRegularCheck() {
		return isPracticalRegularCheck;
	}
	public void setPracticalRegularCheck(boolean isPracticalRegularCheck) {
		this.isPracticalRegularCheck = isPracticalRegularCheck;
	}
	public boolean isTheoryEvalCheck() {
		return isTheoryEvalCheck;
	}
	public void setTheoryEvalCheck(boolean isTheoryEvalCheck) {
		this.isTheoryEvalCheck = isTheoryEvalCheck;
	}
	public boolean isPracticalEvalCheck() {
		return isPracticalEvalCheck;
	}
	public void setPracticalEvalCheck(boolean isPracticalEvalCheck) {
		this.isPracticalEvalCheck = isPracticalEvalCheck;
	}
	public boolean isTheoryAnsCheck() {
		return isTheoryAnsCheck;
	}
	public void setTheoryAnsCheck(boolean isTheoryAnsCheck) {
		this.isTheoryAnsCheck = isTheoryAnsCheck;
	}
	public boolean isPracticalAnsCheck() {
		return isPracticalAnsCheck;
	}
	public void setPracticalAnsCheck(boolean isPracticalAnsCheck) {
		this.isPracticalAnsCheck = isPracticalAnsCheck;
	}
	public double getTheoryEseEnteredMaxMarks() {
		return theoryEseEnteredMaxMarks;
	}
	public void setTheoryEseEnteredMaxMarks(double theoryEseEnteredMaxMarks) {
		this.theoryEseEnteredMaxMarks = theoryEseEnteredMaxMarks;
	}
	public double getTheoryEseMaxMarks() {
		return theoryEseMaxMarks;
	}
	public void setTheoryEseMaxMarks(double theoryEseMaxMarks) {
		this.theoryEseMaxMarks = theoryEseMaxMarks;
	}
	public double getTheoryEseMinMarks() {
		return theoryEseMinMarks;
	}
	public void setTheoryEseMinMarks(double theoryEseMinMarks) {
		this.theoryEseMinMarks = theoryEseMinMarks;
	}
	public double getPracticalEseEnteredMaxMarks() {
		return practicalEseEnteredMaxMarks;
	}
	public void setPracticalEseEnteredMaxMarks(double practicalEseEnteredMaxMarks) {
		this.practicalEseEnteredMaxMarks = practicalEseEnteredMaxMarks;
	}
	public double getPracticalEseMaxMarks() {
		return practicalEseMaxMarks;
	}
	public void setPracticalEseMaxMarks(double practicalEseMaxMarks) {
		this.practicalEseMaxMarks = practicalEseMaxMarks;
	}
	public double getPracticalEseMinMarks() {
		return practicalEseMinMarks;
	}
	public void setPracticalEseMinMarks(double practicalEseMinMarks) {
		this.practicalEseMinMarks = practicalEseMinMarks;
	}
	public String getTheoryTypeOfEval() {
		return theoryTypeOfEval;
	}
	public void setTheoryTypeOfEval(String theoryTypeOfEval) {
		this.theoryTypeOfEval = theoryTypeOfEval;
	}
	public String getPracticalTypeOfEval() {
		return practicalTypeOfEval;
	}
	public void setPracticalTypeOfEval(String practicalTypeOfEval) {
		this.practicalTypeOfEval = practicalTypeOfEval;
	}
	public int getTheoryNoOfEval() {
		return theoryNoOfEval;
	}
	public void setTheoryNoOfEval(int theoryNoOfEval) {
		this.theoryNoOfEval = theoryNoOfEval;
	}
	public int getPracticalNoOfEval() {
		return practicalNoOfEval;
	}
	public void setPracticalNoOfEval(int practicalNoOfEval) {
		this.practicalNoOfEval = practicalNoOfEval;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getTheoryNoOfAns() {
		return theoryNoOfAns;
	}
	public void setTheoryNoOfAns(int theoryNoOfAns) {
		this.theoryNoOfAns = theoryNoOfAns;
	}
	public int getPracticalNoOfAns() {
		return practicalNoOfAns;
	}
	public void setPracticalNoOfAns(int practicalNoOfAns) {
		this.practicalNoOfAns = practicalNoOfAns;
	}
	public double getTheoryCiaMinMark() {
		return theoryCiaMinMark;
	}
	public void setTheoryCiaMinMark(double theoryCiaMinMark) {
		this.theoryCiaMinMark = theoryCiaMinMark;
	}
	public double getPracticalCiaMinMark() {
		return practicalCiaMinMark;
	}
	public void setPracticalCiaMinMark(double practicalCiaMinMark) {
		this.practicalCiaMinMark = practicalCiaMinMark;
	}
}