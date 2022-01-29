package com.kp.cms.bo.exam;

import com.kp.cms.bo.admin.Program;


public class ExamExamCourseSchemeDetailsBO extends ExamGenBO {

	private int examId;
	private int courseId;
	private int schemeNo;
	private int courseSchemeId;
	private int programId;

	// many-to-one
	private ExamCourseUtilBO examCourseUtilBO;
	private ExamDefinitionBO examDefinitionBO;
	private CourseSchemeUtilBO courseSchemeUtilBO;
	private ExamProgramUtilBO examProgramUtilBO;
	private Program program; 
	public ExamExamCourseSchemeDetailsBO() {
		super();
	}

	public ExamExamCourseSchemeDetailsBO(int examId, int courseId,
			int schemeNo, int courseSchemeId,int programId,boolean isActive) {
		super();
		super.id = id;
		this.examId = examId;
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.courseSchemeId = courseSchemeId;
		this.programId=programId;
		this.isActive=isActive;
	}

	

	public ExamExamCourseSchemeDetailsBO(int examId, int courseId,
			int schemeNo, int courseSchemeId, int programId,
			ExamCourseUtilBO examCourseUtilBO,
			ExamDefinitionBO examDefinitionBO,
			CourseSchemeUtilBO courseSchemeUtilBO,
			ExamProgramUtilBO examProgramUtilBO, Program program) {
		super();
		this.examId = examId;
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.courseSchemeId = courseSchemeId;
		this.programId = programId;
		this.examCourseUtilBO = examCourseUtilBO;
		this.examDefinitionBO = examDefinitionBO;
		this.courseSchemeUtilBO = courseSchemeUtilBO;
		this.examProgramUtilBO = examProgramUtilBO;
		this.program = program;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public int getCourseSchemeId() {
		return courseSchemeId;
	}

	public void setCourseSchemeId(int courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public CourseSchemeUtilBO getCourseSchemeUtilBO() {
		return courseSchemeUtilBO;
	}

	public void setCourseSchemeUtilBO(CourseSchemeUtilBO courseSchemeUtilBO) {
		this.courseSchemeUtilBO = courseSchemeUtilBO;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setExamProgramUtilBO(ExamProgramUtilBO examProgramUtilBO) {
		this.examProgramUtilBO = examProgramUtilBO;
	}

	public ExamProgramUtilBO getExamProgramUtilBO() {
		return examProgramUtilBO;
	}

}
