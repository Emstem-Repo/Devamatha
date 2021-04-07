package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamRevaluationEntryTO implements Serializable{

	private Integer id;
	private int studentId;
	private String studentName;
	private String classCode;
	private String marksCardNo;
	private String marksCardDate;

	private ArrayList<ExamRevaluationDetailsTO> listMarksDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getMarksCardNo() {
		return marksCardNo;
	}

	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
	}

	public String getMarksCardDate() {
		return marksCardDate;
	}

	public void setMarksCardDate(String marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public ArrayList<ExamRevaluationDetailsTO> getListMarksDetails() {
		return listMarksDetails;
	}

	public void setListMarksDetails(
			ArrayList<ExamRevaluationDetailsTO> listMarksDetails) {
		this.listMarksDetails = listMarksDetails;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

}
