package com.kp.cms.bo.exam;

public class ExamAssignExaminerToExamBO extends ExamGenBO {

	private int examId;
	private int employeeId;
//	private int isPresent;

	private ExamDefinitionBO examDefinitionBO;
	private EmployeeUtilBO employeeUtilBO;

	public ExamAssignExaminerToExamBO() {
		super();
	}

	public ExamAssignExaminerToExamBO(Integer id) {
		super();
		this.id = id;

	}

	public ExamAssignExaminerToExamBO(int employeeId, int examId) {
		super();
		this.employeeId = employeeId;
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setEmployeeUtilBO(EmployeeUtilBO employeeUtilBO) {
		this.employeeUtilBO = employeeUtilBO;
	}

	public EmployeeUtilBO getEmployeeUtilBO() {
		return employeeUtilBO;
	}

//	public void setIsPresent(int isPresent) {
//		this.isPresent = isPresent;
//	}
//
//	public int getIsPresent() {
//		return isPresent;
//	}

}
