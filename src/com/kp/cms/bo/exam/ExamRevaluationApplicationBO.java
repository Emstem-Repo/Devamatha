package com.kp.cms.bo.exam;

/**
 * Jan 12, 2010
 * Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class ExamRevaluationApplicationBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int courseId;
	private int revaluationTypeId;
	private BigDecimal amount;
	private Date applicationDate;
	private int schemeNo;

	private ExamDefinitionUtilBO examDefinitionUtilBO;
	private StudentUtilBO studentUtilBO;
	private ExamCourseUtilBO examCourseUtilBO;
	private ExamRevaluationTypeBO examRevaluationTypeBO;
	
	private Set<ExamRevaluationApplicationSubjectBO> examRevaluationApplicationSubjectBOset;

	public ExamRevaluationApplicationBO() {
		super();
	}

	public ExamRevaluationApplicationBO(int examId, int courseId, int schemeNo,
			int studentId, BigDecimal amount, Date applicationDate,
			int revaluationTypeId, String userId) {
		super();
		this.examId = examId;
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.studentId = studentId;
		this.amount = amount;
		this.applicationDate = applicationDate;
		this.revaluationTypeId = revaluationTypeId;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.isActive=true;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getRevaluationTypeId() {
		return revaluationTypeId;
	}

	public void setRevaluationTypeId(int revaluationTypeId) {
		this.revaluationTypeId = revaluationTypeId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public ExamDefinitionUtilBO getExamDefinitionUtilBO() {
		return examDefinitionUtilBO;
	}

	public void setExamDefinitionUtilBO(
			ExamDefinitionUtilBO examDefinitionUtilBO) {
		this.examDefinitionUtilBO = examDefinitionUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public ExamRevaluationTypeBO getExamRevaluationTypeBO() {
		return examRevaluationTypeBO;
	}

	public void setExamRevaluationTypeBO(
			ExamRevaluationTypeBO examRevaluationTypeBO) {
		this.examRevaluationTypeBO = examRevaluationTypeBO;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setExamRevaluationApplicationSubjectBOset(
			Set<ExamRevaluationApplicationSubjectBO> examRevaluationApplicationSubjectBOset) {
		this.examRevaluationApplicationSubjectBOset = examRevaluationApplicationSubjectBOset;
	}

	public Set<ExamRevaluationApplicationSubjectBO> getExamRevaluationApplicationSubjectBOset() {
		return examRevaluationApplicationSubjectBOset;
	}

}
