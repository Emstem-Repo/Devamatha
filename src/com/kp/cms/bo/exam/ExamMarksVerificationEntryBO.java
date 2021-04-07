package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

public class ExamMarksVerificationEntryBO extends ExamGenBO {


	private Integer examId;
	private Integer studentId;
	private Integer subjectId;
	private String fsvm;
	private String ssvm;
	private Integer evaluatorTypeId;
	private String vmarks;
	private Integer answerScriptTypeId;
	private Integer userId;
	private Date createdDate;
	private String packetNo;
	
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getFsvm() {
		return fsvm;
	}
	public void setFsvm(String fsvm) {
		this.fsvm = fsvm;
	}
	public String getSsvm() {
		return ssvm;
	}
	public void setSsvm(String ssvm) {
		this.ssvm = ssvm;
	}
	public Integer getEvaluatorTypeId() {
		return evaluatorTypeId;
	}
	public void setEvaluatorTypeId(Integer evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}
	public String getVmarks() {
		return vmarks;
	}
	public void setVmarks(String vmarks) {
		this.vmarks = vmarks;
	}
	public Integer getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}
	public void setAnswerScriptTypeId(Integer answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getPacketNo() {
		return packetNo;
	}
	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}
	
	
	
	
	
}
