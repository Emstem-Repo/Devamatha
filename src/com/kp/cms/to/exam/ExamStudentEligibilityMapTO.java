package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ExamStudentEligibilityMapTO implements Serializable {

	private HashMap<Integer, Integer> courseFeeMap;
	private HashMap<Integer, Integer> examFeeMap;
	private HashMap<Integer, Integer> attendenceMap;
	private HashMap<Integer, Integer> examElgibilityMap;
	private HashMap<Integer, ExamStudentEligibilityCheckTO> studentDetailsMap;
	private HashMap<Integer, ArrayList<ExamExamEligibilityTO>> additionalElgibilityMap;

	public HashMap<Integer, ArrayList<ExamExamEligibilityTO>> getAdditionalElgibilityMap() {
		return additionalElgibilityMap;
	}

	public void setAdditionalElgibilityMap(
			HashMap<Integer, ArrayList<ExamExamEligibilityTO>> additionalElgibilityMap) {
		this.additionalElgibilityMap = additionalElgibilityMap;
	}

	public void setCourseFeeMap(HashMap<Integer, Integer> courseFeeMap) {
		this.courseFeeMap = courseFeeMap;
	}

	public void setExamFeeMap(HashMap<Integer, Integer> examFeeMap) {
		this.examFeeMap = examFeeMap;
	}

	public void setAttendenceMap(HashMap<Integer, Integer> attendenceMap) {
		this.attendenceMap = attendenceMap;
	}

	public void setExamElgibilityMap(HashMap<Integer, Integer> examElgibilityMap) {
		this.examElgibilityMap = examElgibilityMap;
	}

	public boolean getCourseFee(int id) {
		return (courseFeeMap.get(id) == 1) ? true : false;
	}

	public boolean getExamFee(int id) {
		return (examFeeMap.get(id) == 1) ? true : false;
	}

	public boolean getAttendence(int id) {
		return (attendenceMap.get(id) == 1) ? true : false;
	}

	public boolean getExamElgibility(int id) {
		return (examElgibilityMap.get(id) == 1) ? true : false;
	}

	public ExamStudentEligibilityCheckTO getstudentDetails(int id) {
		return studentDetailsMap.get(id);
	}

	public ArrayList<ExamExamEligibilityTO> getAdditionalFeeList(int id) {
		return additionalElgibilityMap.get(id);
	}

	public HashMap<Integer, Integer> getCourseFeeMap() {
		return courseFeeMap;
	}

	public HashMap<Integer, Integer> getExamFeeMap() {
		return examFeeMap;
	}

	public HashMap<Integer, Integer> getAttendenceMap() {
		return attendenceMap;
	}

	public HashMap<Integer, Integer> getExamElgibilityMap() {
		return examElgibilityMap;
	}

	public void setStudentDetailsMap(
			HashMap<Integer, ExamStudentEligibilityCheckTO> studentDetailsMap) {
		this.studentDetailsMap = studentDetailsMap;
	}

	public HashMap<Integer, ExamStudentEligibilityCheckTO> getStudentDetailsMap() {
		return studentDetailsMap;
	}

	
}
