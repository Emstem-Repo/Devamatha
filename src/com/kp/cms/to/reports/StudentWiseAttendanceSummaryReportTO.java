package com.kp.cms.to.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentWiseAttendanceSummaryReportTO {

	private String studentName;

	private String registerNo;

	private String rollNo;

	private List<StudentWiseSubjectSummaryTO> subjectWiseSummaryToList = new ArrayList<StudentWiseSubjectSummaryTO>();

	private Map<Integer, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public List<StudentWiseSubjectSummaryTO> getSubjectWiseSummaryToList() {
		return subjectWiseSummaryToList;
	}

	public void setSubjectWiseSummaryToList(
			List<StudentWiseSubjectSummaryTO> subjectWiseSummaryToList) {
		this.subjectWiseSummaryToList = subjectWiseSummaryToList;
	}

	public Map<Integer, StudentWiseSubjectSummaryTO> getSummaryMap() {
		return summaryMap;
	}

	public void setSummaryMap(
			Map<Integer, StudentWiseSubjectSummaryTO> summaryMap) {
		this.summaryMap = summaryMap;
	}

}
