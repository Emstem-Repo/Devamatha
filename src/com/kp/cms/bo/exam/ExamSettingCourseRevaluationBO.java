package com.kp.cms.bo.exam;

import java.io.Serializable;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamSettingCourseRevaluationBO implements Serializable {
	private int id;
	private int examSettingsCourseId;
	private int revaluationId;
	private String revaluation;

	private ExamRevaluationTypeBO examRevaluationTypeBO;
	private ExamSettingCourseBO examSettingCourseBO;

	public ExamSettingCourseRevaluationBO() {
	}

	public ExamSettingCourseRevaluationBO(int examSettingsCourseId, String revaluation,
			int revaluationId) {
		super();
		this.examSettingsCourseId = examSettingsCourseId;
		this.revaluation = revaluation;
		this.revaluationId = revaluationId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getRevaluationId() {
		return revaluationId;
	}

	public void setRevaluationId(int revaluationId) {
		this.revaluationId = revaluationId;
	}

	public String getRevaluation() {
		return revaluation;
	}

	public void setRevaluation(String revaluation) {
		this.revaluation = revaluation;
	}

	public ExamRevaluationTypeBO getExamRevaluationTypeBO() {
		return examRevaluationTypeBO;
	}

	public void setExamRevaluationTypeBO(
			ExamRevaluationTypeBO examRevaluationTypeBO) {
		this.examRevaluationTypeBO = examRevaluationTypeBO;
	}

	public void setExamSettingsCourseId(int examSettingsCourseId) {
		this.examSettingsCourseId = examSettingsCourseId;
	}

	public int getExamSettingsCourseId() {
		return examSettingsCourseId;
	}

	public void setExamSettingCourseBO(ExamSettingCourseBO examSettingCourseBO) {
		this.examSettingCourseBO = examSettingCourseBO;
	}

	public ExamSettingCourseBO getExamSettingCourseBO() {
		return examSettingCourseBO;
	}

}
