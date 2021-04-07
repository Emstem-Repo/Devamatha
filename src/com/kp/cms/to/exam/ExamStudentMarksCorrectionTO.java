package com.kp.cms.to.exam;

import java.util.ArrayList;
import java.util.Date;

public class ExamStudentMarksCorrectionTO {

	private Integer id;
	private int studentId;
	private int examId;
	private int answerScriptId;
	private String studentName;
	private String markType;
	private int evaluatorType;
	private String marksCardNo;
	private Date marksCardDate;

	private ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listMarksDetails;

	public ExamStudentMarksCorrectionTO() {
		super();
	}

	public ExamStudentMarksCorrectionTO(int examId, int studentId,
			int answerScriptId, String markType, int evaluatorType, String marksCardNo) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.answerScriptId = answerScriptId;
		this.markType = markType;
		this.evaluatorType = evaluatorType;
		this.marksCardNo = marksCardNo;
	}

	
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

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setListMarksDetails(
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listMarksDetails) {
		this.listMarksDetails = listMarksDetails;
	}

	public ArrayList<ExamStudentMarksCorrectionSingleStudentTO> getListMarksDetails() {
		return listMarksDetails;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(int answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public int getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(int evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

}
