package com.kp.cms.bo.exam;

/**
 * Feb 1, 2010 Created By 9Elements Team
 */
import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamStudentSpecializationBO extends ExamGenBO {

	private int courseId;
	private int studentId;
	private int specializationId;
	private String schemeNo;
	private String sectionName;
	private ExamCourseUtilBO examCourseUtilBO;
	private StudentUtilBO studentUtilBO;
	private ExamSpecializationBO examSpecializationBO;
	
	private Set<ExamStudentOptionalSubjectGroupBO> examStudentOptionalSubjectGroupBOSet;
	

	public Set<ExamStudentOptionalSubjectGroupBO> getExamStudentOptionalSubjectGroupBOSet() {
		return examStudentOptionalSubjectGroupBOSet;
	}

	public void setExamStudentOptionalSubjectGroupBOSet(
			Set<ExamStudentOptionalSubjectGroupBO> examStudentOptionalSubjectGroupBOSet) {
		this.examStudentOptionalSubjectGroupBOSet = examStudentOptionalSubjectGroupBOSet;
	}

	public ExamStudentSpecializationBO() {
		super();
	}

	public ExamStudentSpecializationBO(int courseId, int studentId,
			int specializationId, String userId) {
		super();
		this.courseId = courseId;
		this.studentId = studentId;
		this.specializationId = specializationId;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public ExamStudentSpecializationBO(int courseId, int studentId,
			int specializationId, String schemeNo, String sectionName,
			String userId) {
		super();
		this.courseId = courseId;
		this.studentId = studentId;
		this.specializationId = specializationId;
		this.schemeNo = schemeNo;
		this.sectionName = sectionName;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(int specializationId) {
		this.specializationId = specializationId;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamSpecializationBO getExamSpecializationBO() {
		return examSpecializationBO;
	}

	public void setExamSpecializationBO(
			ExamSpecializationBO examSpecializationBO) {
		this.examSpecializationBO = examSpecializationBO;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionName() {
		return sectionName;
	}

}
