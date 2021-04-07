package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class MarksEntryOldDetained implements Serializable {
	
	private int id;
	private ExamDefinitionBO exam;
	private Student student;
	private Integer evaluatorType;
	private String marksCardNo;
	private Date marksCardDate;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Integer answerScript;
	private Integer sequenceEvaluator;
	private String markType;
	private Classes classes;
	private Set<MarksEntryDetailsOldDetained> marksDetails=new HashSet<MarksEntryDetailsOldDetained>();
	
	public MarksEntryOldDetained() {
		super();
	}
	
	public MarksEntryOldDetained(int id, ExamDefinitionBO exam, Student student,
			Integer evaluatorType, String marksCardNo, Date marksCardDate,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Integer answerScript,
			Integer sequenceEvaluator, String markType,Classes classes) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.evaluatorType = evaluatorType;
		this.marksCardNo = marksCardNo;
		this.marksCardDate = marksCardDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.answerScript = answerScript;
		this.sequenceEvaluator = sequenceEvaluator;
		this.markType = markType;
		this.classes=classes;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinitionBO getExam() {
		return exam;
	}
	public void setExam(ExamDefinitionBO exam) {
		this.exam = exam;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getEvaluatorType() {
		return evaluatorType;
	}
	public void setEvaluatorType(Integer evaluatorType) {
		this.evaluatorType = evaluatorType;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Integer getAnswerScript() {
		return answerScript;
	}
	public void setAnswerScript(Integer answerScript) {
		this.answerScript = answerScript;
	}
	public Integer getSequenceEvaluator() {
		return sequenceEvaluator;
	}
	public void setSequenceEvaluator(Integer sequenceEvaluator) {
		this.sequenceEvaluator = sequenceEvaluator;
	}
	public String getMarkType() {
		return markType;
	}
	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Set<MarksEntryDetailsOldDetained> getMarksDetails() {
		return marksDetails;
	}

	public void setMarksDetails(Set<MarksEntryDetailsOldDetained> marksDetails) {
		this.marksDetails = marksDetails;
	}

	
}
