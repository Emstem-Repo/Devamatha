package com.kp.cms.bo.exam;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;

import com.kp.cms.bo.admin.Classes;

public class PendingExamResults implements Serializable {
    
	private int id;
	private ExamDefinition examId;
	private Classes classId;
	private Boolean isActive;
	
	public PendingExamResults() {
	}

	public PendingExamResults(int id,ExamDefinition examId,Classes classId,	Boolean isActive) 
	{
		
		this.id = id;
		this.examId=examId;
		this.classId=classId;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinition getExamId() {
		return examId;
	}

	public void setExamId(ExamDefinition examId) {
		this.examId = examId;
	}

	public Classes getClassId() {
		return classId;
	}

	public void setClassId(Classes classId) {
		this.classId = classId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	


}
