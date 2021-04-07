package com.kp.cms.bo.exam;

/**
 * Feb 25, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamStudentBioDataBO extends ExamGenBO {

	private Integer studentId;
	private Integer specializationId;
	private Integer secondLanguageId;
	private String consolidatedMarksCardNo;
	private String courseNameForMarksCard;

	// Many-to-One
	private StudentUtilBO studentUtilBO;
	private ExamSpecializationBO examSpecializationBO;
	private ExamSecondLanguageMasterBO examSecondLanguageMasterBO;

	public ExamStudentBioDataBO() {
		super();
	}

	public ExamStudentBioDataBO(Integer studentId, Integer specializationId,
			Integer secondLanguageId, String consolidatedMarksCardNo,
			String courseNameForMarksCard) {
		super();
		this.studentId = studentId;
		this.specializationId = specializationId;
		this.secondLanguageId = secondLanguageId;
		this.consolidatedMarksCardNo = consolidatedMarksCardNo;
		this.courseNameForMarksCard = courseNameForMarksCard;
	}



	public String getConsolidatedMarksCardNo() {
		return consolidatedMarksCardNo;
	}

	public void setConsolidatedMarksCardNo(String consolidatedMarksCardNo) {
		this.consolidatedMarksCardNo = consolidatedMarksCardNo;
	}

	public String getCourseNameForMarksCard() {
		return courseNameForMarksCard;
	}

	public void setCourseNameForMarksCard(String courseNameForMarksCard) {
		this.courseNameForMarksCard = courseNameForMarksCard;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ExamSpecializationBO getExamSpecializationBO() {
		return examSpecializationBO;
	}

	public void setExamSpecializationBO(
			ExamSpecializationBO examSpecializationBO) {
		this.examSpecializationBO = examSpecializationBO;
	}

	public ExamSecondLanguageMasterBO getExamSecondLanguageMasterBO() {
		return examSecondLanguageMasterBO;
	}

	public void setExamSecondLanguageMasterBO(
			ExamSecondLanguageMasterBO examSecondLanguageMasterBO) {
		this.examSecondLanguageMasterBO = examSecondLanguageMasterBO;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Integer specializationId) {
		this.specializationId = specializationId;
	}

	public Integer getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguageId(Integer secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

}
