package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */
public class ExamUpdateExcludeWithheldBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int courseId;
	private int schemeNo;
	private boolean excludeFromResults;
	private boolean withheld;

	private ExamDefinitionUtilBO examDefinitionUtilBO;
	private StudentUtilBO studentUtilBO;
	private ExamCourseUtilBO examCourseUtilBO;
	private CurriculumSchemeUtilBO curriculumSchemeUtilBO;

	public ExamUpdateExcludeWithheldBO() {
		super();
	}

	public ExamUpdateExcludeWithheldBO(int courseId, int examId, int studentId,int schemeNo,
			String userId) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.excludeFromResults = false;
		this.withheld = false;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		this.schemeNo = schemeNo;
		this.modifiedBy = userId;
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

	public boolean getExcludeFromResults() {
		return excludeFromResults;
	}

	public void setExcludeFromResults(boolean excldeFromResults) {
		this.excludeFromResults = excldeFromResults;
	}

	public boolean getWithheld() {
		return withheld;
	}

	public void setWithheld(boolean withheld) {
		this.withheld = withheld;
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

	public void setCurriculumSchemeUtilBO(
			CurriculumSchemeUtilBO curriculumSchemeUtilBO) {
		this.curriculumSchemeUtilBO = curriculumSchemeUtilBO;
	}

	public CurriculumSchemeUtilBO getCurriculumSchemeUtilBO() {
		return curriculumSchemeUtilBO;
	}

	public void reset(String userId) {
		withheld = false;
		excludeFromResults = false;
		modifiedBy = userId;
		lastModifiedDate = new Date();

	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

}
