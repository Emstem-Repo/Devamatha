package com.kp.cms.to.exam;

import java.util.ArrayList;
import java.util.Date;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
public class ExamMarksEntryTO {

	private Integer id;
	private int studentId;
	private String studentName;
	private String classCode;
	private String marksCardNo;
	private Date marksCardDate;

	private ArrayList<ExamMarksEntryDetailsTO> listMarksDetails;

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

	public Date getMarksCardDate() {
		return marksCardDate;
	}

	public void setMarksCardDate(Date marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public ArrayList<ExamMarksEntryDetailsTO> getListMarksDetails() {
		return listMarksDetails;
	}

	public void setListMarksDetails(
			ArrayList<ExamMarksEntryDetailsTO> listMarksDetails) {
		this.listMarksDetails = listMarksDetails;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

}
