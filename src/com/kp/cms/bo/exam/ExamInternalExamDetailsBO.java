package com.kp.cms.bo.exam;

import java.io.Serializable;

public class ExamInternalExamDetailsBO implements Serializable {
	private int id;
	private int examId;
	private int internalExamNameId;

	// many to one
	private ExamDefinitionBO examDefinitionBO;

	public ExamInternalExamDetailsBO() {
		super();
	}

	public ExamInternalExamDetailsBO(int examId, int internalExamNameId) {
		super();
		this.examId = examId;
		this.internalExamNameId = internalExamNameId;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getInternalExamNameId() {
		return internalExamNameId;
	}

	public void setInternalExamNameId(int internalExamNameId) {
		this.internalExamNameId = internalExamNameId;
	}

}
