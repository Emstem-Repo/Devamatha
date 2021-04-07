package com.kp.cms.to.exam;

import java.util.Date;
import java.util.List;

import com.kp.cms.to.admin.SubjectTO;

public class ExamMidsemExemptionTO {
	
	private int id;
	List<SubjectTO> subjectList;
	private String subjectName;
	private String checked;
	private String tempChecked;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SubjectTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectTO> subjectList) {
		this.subjectList = subjectList;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getTempChecked() {
		return tempChecked;
	}

	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	
	

}
