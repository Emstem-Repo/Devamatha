package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.List;

public class StudentAbsenceDetailsTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String registerNumber;
	private String studentName;
	private List<StudentAbsenceDetailsReportTO> studentAbsenceDetailsList;
	
	public List<StudentAbsenceDetailsReportTO> getStudentAbsenceDetailsList() {
		return studentAbsenceDetailsList;
	}
	public void setStudentAbsenceDetailsList(
			List<StudentAbsenceDetailsReportTO> studentAbsenceDetailsList) {
		this.studentAbsenceDetailsList = studentAbsenceDetailsList;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}
