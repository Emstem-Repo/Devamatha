package com.kp.cms.bo.exam;

/**
 * Feb 1, 2010 Created By 9Elements Team
 */
import java.util.Date;

@SuppressWarnings("serial")
public class ExamPublishExamResultsBO extends ExamGenBO {

	private int examId;
	private int classId;
	private Date publishDate;
	private int isPublishOverallInternalCompOnly;
	private ExamDefinitionBO examDefinitionBO;
	private ClassUtilBO classUtilBO;

	public ExamPublishExamResultsBO() {
		super();
	}

	public ExamPublishExamResultsBO(int examId, int classId, Date publishDate,
			int isPublishOverallInternalCompOnly, String userId) {
		super();
		this.examId = examId;
		this.classId = classId;
		this.publishDate = publishDate;
		this.isPublishOverallInternalCompOnly = isPublishOverallInternalCompOnly;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getIsPublishOverallInternalCompOnly() {
		return isPublishOverallInternalCompOnly;
	}

	public void setIsPublishOverallInternalCompOnly(
			int isPublishOverallInternalCompOnly) {
		this.isPublishOverallInternalCompOnly = isPublishOverallInternalCompOnly;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	

}
