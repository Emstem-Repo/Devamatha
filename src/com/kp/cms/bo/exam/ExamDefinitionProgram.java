package com.kp.cms.bo.exam;

import java.io.Serializable;

import com.kp.cms.bo.admin.Program;

public class ExamDefinitionProgram implements Serializable {
	private int id;
	private ExamDefinition examDefinition;
	private Program program;
	private Boolean isActive;
	private Boolean delIsActive;
	
	public ExamDefinitionProgram() {
		super();
	}
	
	public ExamDefinitionProgram(int id, ExamDefinition examDefinition,
			Program program, Boolean isActive, Boolean delIsActive) {
		super();
		this.id = id;
		this.examDefinition = examDefinition;
		this.program = program;
		this.isActive = isActive;
		this.delIsActive = delIsActive;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinition getExamDefinition() {
		return examDefinition;
	}
	public void setExamDefinition(ExamDefinition examDefinition) {
		this.examDefinition = examDefinition;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getDelIsActive() {
		return delIsActive;
	}
	public void setDelIsActive(Boolean delIsActive) {
		this.delIsActive = delIsActive;
	}
}