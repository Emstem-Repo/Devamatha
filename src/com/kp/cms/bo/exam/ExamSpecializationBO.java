package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;
/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationBO extends ExamGenBO  {

	private int courseId;
	
	private ExamCourseUtilBO courseUtilBO;
	private String course;
	
	//one-to-many
	private Set<ExamSpecializationSubjectGroupBO> examSpecSubGrpBOset;
	
	public ExamSpecializationBO() {
		super();
	}
public ExamSpecializationBO(IExamGenBO obj) {
		super(obj);
	}
	public ExamSpecializationBO(int id, String name, int courseId,
			String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.courseId = courseId;
		this.createdBy = createdBy;
	}

	public ExamSpecializationBO(int id, String name, int courseId,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.courseUtilBO = new ExamCourseUtilBO();
		this.courseUtilBO.setCourseID(courseId);
		this.courseId = courseId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	
	

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public ExamCourseUtilBO getCourseUtilBO() {
		return courseUtilBO;
	}

	public void setCourseUtilBO(ExamCourseUtilBO courseUtilBO) {
		this.courseUtilBO = courseUtilBO;
	}

	public void setExamSpecSubGrpBOset(Set<ExamSpecializationSubjectGroupBO> examSpecSubGrpBOset) {
		this.examSpecSubGrpBOset = examSpecSubGrpBOset;
	}

	public Set<ExamSpecializationSubjectGroupBO> getExamSpecSubGrpBOset() {
		return examSpecSubGrpBOset;
	}

}
