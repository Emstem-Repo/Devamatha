package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExamPromotionCriteriaBO extends ExamGenBO {

	private int courseId;
	private int fromSchemeId;
	private int toSchemeId;
	private String scheme;
	private BigDecimal maxBacklogCountPrcntg = new BigDecimal("0.00");
	private int maxBacklogNumber;

	private ExamCourseUtilBO examCourseUtilBO;
	private CurriculumSchemeUtilBO curriculumSchemeUtilfromScheme;
	private CurriculumSchemeUtilBO curriculumSchemeUtiltoScheme;

	public ExamPromotionCriteriaBO() {
		super();
	}

	public ExamPromotionCriteriaBO(int courseId, int fromSchemeId,
			int toSchemeId, String scheme, BigDecimal maxBacklogCountPrcntg,
			int maxBacklogNumber) {
		super();
		this.courseId = courseId;
		this.fromSchemeId = fromSchemeId;
		this.toSchemeId = toSchemeId;
		this.scheme = scheme;
		this.maxBacklogCountPrcntg = maxBacklogCountPrcntg.setScale(0,
				RoundingMode.HALF_UP);
		this.maxBacklogNumber = maxBacklogNumber;
	}

	public ExamPromotionCriteriaBO(int id, int courseId2, int fromSchemeId2,
			int toSchemeId2, String scheme1, BigDecimal maxBCPrcntg) {
		this.id = id;
		this.courseId = courseId2;
		this.fromSchemeId = fromSchemeId2;
		this.toSchemeId = toSchemeId2;
		this.scheme = scheme1;
		this.maxBacklogCountPrcntg = maxBCPrcntg.setScale(0,
				RoundingMode.HALF_UP);
	}

	public ExamPromotionCriteriaBO(int id, int courseId2, int fromSchemeId2,
			int toSchemeId2, String scheme1, int maxBCPrcntg) {
		this.id = id;
		this.courseId = courseId2;
		this.fromSchemeId = fromSchemeId2;
		this.toSchemeId = toSchemeId2;
		this.scheme = scheme1;
		this.maxBacklogNumber = maxBCPrcntg;
	}

	public ExamPromotionCriteriaBO(int parseInt, int parseInt2, int parseInt3,
			String scheme1, BigDecimal maxBCPrcntg) {
		this.courseId = parseInt;
		this.fromSchemeId = parseInt2;
		this.toSchemeId = parseInt3;
		this.scheme = scheme1;
		this.maxBacklogCountPrcntg = maxBCPrcntg.setScale(0,
				RoundingMode.HALF_UP);
	}

	public ExamPromotionCriteriaBO(int parseInt, int parseInt2, int parseInt3,
			String scheme1, int maxBCPrcntg) {
		this.courseId = parseInt;
		this.fromSchemeId = parseInt2;
		this.toSchemeId = parseInt3;
		this.scheme = scheme1;
		this.maxBacklogNumber = maxBCPrcntg;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getFromSchemeId() {
		return fromSchemeId;
	}

	public void setFromSchemeId(int fromSchemeId) {
		this.fromSchemeId = fromSchemeId;
	}

	public int getToSchemeId() {
		return toSchemeId;
	}

	public void setToSchemeId(int toSchemeId) {
		this.toSchemeId = toSchemeId;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public BigDecimal getMaxBacklogCountPrcntg() {
		return maxBacklogCountPrcntg;
	}

	public void setMaxBacklogCountPrcntg(BigDecimal maxBacklogCountPrcntg) {
		this.maxBacklogCountPrcntg = maxBacklogCountPrcntg.setScale(0,
				RoundingMode.HALF_UP);

	}

	public int getMaxBacklogNumber() {
		return maxBacklogNumber;
	}

	public void setMaxBacklogNumber(int maxBacklogNumber) {
		this.maxBacklogNumber = maxBacklogNumber;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public void setCurriculumSchemeUtilfromScheme(
			CurriculumSchemeUtilBO curriculumSchemeUtilfromScheme) {
		this.curriculumSchemeUtilfromScheme = curriculumSchemeUtilfromScheme;
	}

	public CurriculumSchemeUtilBO getCurriculumSchemeUtilfromScheme() {
		return curriculumSchemeUtilfromScheme;
	}

	public void setCurriculumSchemeUtiltoScheme(
			CurriculumSchemeUtilBO curriculumSchemeUtiltoScheme) {
		this.curriculumSchemeUtiltoScheme = curriculumSchemeUtiltoScheme;
	}

	public CurriculumSchemeUtilBO getCurriculumSchemeUtiltoScheme() {
		return curriculumSchemeUtiltoScheme;
	}

}
