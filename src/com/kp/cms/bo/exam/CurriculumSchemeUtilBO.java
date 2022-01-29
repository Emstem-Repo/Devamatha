package com.kp.cms.bo.exam;

import java.util.Set;


public class CurriculumSchemeUtilBO extends ExamGenBO{

	
	private int courseId;
	private int courseSchemeId;
	private int year;
	private int noScheme;
//	private int programId;
//
//	
//	private ExamProgramUtilBO examProgramUtilBO;
	private CourseSchemeUtilBO courseSchemeUtilBO;
	private ExamCourseUtilBO examCourseUtilBO ;
	private Set<CurriculumSchemeDurationUtilBO> curriculumSchemeDurationUtilBOSet; 
		
	
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCourseSchemeId() {
		return courseSchemeId;
	}
	public void setCourseSchemeId(int courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getNoScheme() {
		return noScheme;
	}
	public void setNoScheme(int noScheme) {
		this.noScheme = noScheme;
	}
	public CourseSchemeUtilBO getCourseSchemeUtilBO() {
		return courseSchemeUtilBO;
	}
	public void setCourseSchemeUtilBO(CourseSchemeUtilBO courseSchemeUtilBO) {
		this.courseSchemeUtilBO = courseSchemeUtilBO;
	}
	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}
	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}
	public void setCurriculumSchemeDurationUtilBOSet(
			Set<CurriculumSchemeDurationUtilBO> curriculumSchemeDurationUtilBOSet) {
		this.curriculumSchemeDurationUtilBOSet = curriculumSchemeDurationUtilBOSet;
	}
	public Set<CurriculumSchemeDurationUtilBO> getCurriculumSchemeDurationUtilBOSet() {
		return curriculumSchemeDurationUtilBOSet;
	}
//	public void setProgramId(int programId) {
//		this.programId = programId;
//	}
//	public int getProgramId() {
//		return programId;
//	}
//	public void setExamProgramUtilBO(ExamProgramUtilBO examProgramUtilBO) {
//		this.examProgramUtilBO = examProgramUtilBO;
//	}
//	public ExamProgramUtilBO getExamProgramUtilBO() {
//		return examProgramUtilBO;
//	}
	
	
}
