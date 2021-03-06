package com.kp.cms.to.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.io.Serializable;

public class GradeDefinitionBatchWiseTO implements Serializable {

	private int id = 0;
	private String course = "";
	private String startPercentage = "";
	private String endPercentage = "";
	private String grade = "";
	private String interpretation = "";
	private String resultClass = "";
	private String gradePoint = "";
	private String fromBatch="";

	public GradeDefinitionBatchWiseTO() {
		super();
	}

	public GradeDefinitionBatchWiseTO(int id, String course,String fromBatch, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint) {
		super();
		this.id = id;
		this.course = course;
		this.startPercentage = startPercentage;
		this.endPercentage = endPercentage;
		this.grade = grade;
		this.interpretation = interpretation;
		this.resultClass = resultClass;
		this.gradePoint = gradePoint;
		this.fromBatch=fromBatch;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}

	public String getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public String getResultClass() {
		return resultClass;
	}

	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	public String getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(String gradePoint) {
		this.gradePoint = gradePoint;
	}

	public String getFromBatch() {
		return fromBatch;
	}

	public void setFromBatch(String fromBatch) {
		this.fromBatch = fromBatch;
	}

}
