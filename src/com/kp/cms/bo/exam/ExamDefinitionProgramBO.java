package com.kp.cms.bo.exam;

import java.util.Set;

public class ExamDefinitionProgramBO extends ExamGenBO {

	private int examDefnId;
	private int programId;
	private boolean delIsActive;

	private ExamDefinitionBO examDefinitionBO;
	private ExamProgramUtilBO examProgramUtilBO;

	public ExamDefinitionProgramBO() {
		super();
	}

	public ExamDefinitionProgramBO(int examDefnId, int programId, boolean isActive) {
		super();
		
		this.examDefnId = examDefnId;
		this.programId = programId;
		this.isActive=isActive;
		this.delIsActive=true;
		
	}

	public int getExamDefnId() {
		return examDefnId;
	}

	public void setExamDefnId(int examDefnId) {
		this.examDefnId = examDefnId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public ExamProgramUtilBO getExamProgramUtilBO() {
		return examProgramUtilBO;
	}

	public void setExamProgramUtilBO(ExamProgramUtilBO examProgramUtilBO) {
		this.examProgramUtilBO = examProgramUtilBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setDelIsActive(boolean delIsActive) {
		this.delIsActive = delIsActive;
	}

	public boolean getDelIsActive() {
		return delIsActive;
	}



}
