package com.kp.cms.bo.exam;

/**
 * Dec 29, 2009 Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.Date;

public class GradeDefinitionBatchWiseBO extends ExamGenBO {

	private int courseId;
	private BigDecimal startPercentage;
	private BigDecimal endPercentage;
	private String grade;
	private String interpretation;
	private String resultClass;
	private BigDecimal gradePoint;
	private Integer fromBatch;

	private ExamCourseUtilBO courseBO;

	public ExamCourseUtilBO getCourseBO() {
		return courseBO;
	}

	public void setCourseBO(ExamCourseUtilBO courseBO) {
		this.courseBO = courseBO;
	}

	public GradeDefinitionBatchWiseBO() {
		super();
	}

	public GradeDefinitionBatchWiseBO(int courseId,Integer fromBatch, BigDecimal startPercentage,
			BigDecimal endPercentage, String grade, String interpretation,
			String resultClass, BigDecimal gradePoint, String userId) {
		super();
		this.courseId = courseId;
		this.startPercentage = startPercentage;
		this.endPercentage = endPercentage;
		this.grade = grade;
		this.interpretation = interpretation;
		this.resultClass = resultClass;
		this.gradePoint = gradePoint;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.isActive = true;
		this.fromBatch=fromBatch;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public BigDecimal getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(BigDecimal startPercentage) {
		this.startPercentage = startPercentage;
	}

	public BigDecimal getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(BigDecimal endPercentage) {
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

	public BigDecimal getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(BigDecimal gradePoint) {
		this.gradePoint = gradePoint;
	}

	public Integer getFromBatch() {
		return fromBatch;
	}

	public void setFromBatch(Integer fromBatch) {
		this.fromBatch = fromBatch;
	}

}
