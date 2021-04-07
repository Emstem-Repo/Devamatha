package com.kp.cms.to.reports;

import java.util.List;

public class AbsenceInformationSummaryTO {
	
	private String registerNo;
	
	private String studentName;
	
	private List<AbsenceInfoMapTO> subjectMaplist;

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<AbsenceInfoMapTO> getSubjectMaplist() {
		return subjectMaplist;
	}

	public void setSubjectMaplist(List<AbsenceInfoMapTO> subjectMaplist) {
		this.subjectMaplist = subjectMaplist;
	}

	

	

}
