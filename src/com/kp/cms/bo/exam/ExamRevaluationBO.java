package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamRevaluationBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int revaluationTypeId;
	private String oldMarksCardNo;
	private Date oldMarksCardDate;
	private String newMarksCardNo;
	private Date newMarksCardDate;

	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ExamRevaluationTypeBO examRevaluationTypeBO;
	
	private Set<ExamRevaluationDetailsBO> examRevaluationDetailsBOSet;

	public ExamRevaluationBO() {
		super();
	}

	public ExamRevaluationBO(int examId, int studentId, int revaluationTypeId,
			String oldMarksCardNo, Date oldMarksCardDate,
			String newMarksCardNo, Date newMarksCardDate) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.revaluationTypeId = revaluationTypeId;
		this.oldMarksCardNo = oldMarksCardNo;
		this.oldMarksCardDate = oldMarksCardDate;
		this.newMarksCardNo = newMarksCardNo;
		this.newMarksCardDate = newMarksCardDate;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getRevaluationTypeId() {
		return revaluationTypeId;
	}

	public void setRevaluationTypeId(int revaluationTypeId) {
		this.revaluationTypeId = revaluationTypeId;
	}

	public String getOldMarksCardNo() {
		return oldMarksCardNo;
	}

	public void setOldMarksCardNo(String oldMarksCardNo) {
		this.oldMarksCardNo = oldMarksCardNo;
	}

	public Date getOldMarksCardDate() {
		return oldMarksCardDate;
	}

	public void setOldMarksCardDate(Date oldMarksCardDate) {
		this.oldMarksCardDate = oldMarksCardDate;
	}

	public String getNewMarksCardNo() {
		return newMarksCardNo;
	}

	public void setNewMarksCardNo(String newMarksCardNo) {
		this.newMarksCardNo = newMarksCardNo;
	}

	public Date getNewMarksCardDate() {
		return newMarksCardDate;
	}

	public void setNewMarksCardDate(Date newMarksCardDate) {
		this.newMarksCardDate = newMarksCardDate;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setExamRevaluationTypeBO(ExamRevaluationTypeBO examRevaluationTypeBO) {
		this.examRevaluationTypeBO = examRevaluationTypeBO;
	}

	public ExamRevaluationTypeBO getExamRevaluationTypeBO() {
		return examRevaluationTypeBO;
	}

	public void setExamRevaluationDetailsBOSet(
			Set<ExamRevaluationDetailsBO> examRevaluationDetailsBOSet) {
		this.examRevaluationDetailsBOSet = examRevaluationDetailsBOSet;
	}

	public Set<ExamRevaluationDetailsBO> getExamRevaluationDetailsBOSet() {
		return examRevaluationDetailsBOSet;
	}

}
