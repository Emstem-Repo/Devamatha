package com.kp.cms.bo.exam;

/**
 * Feb 1, 2010 Created By 9Elements Team
 */
import java.util.Date;

import com.kp.cms.bo.examallotment.ExaminationSessions;

@SuppressWarnings("serial")
public class ExamTimeTableBO extends ExamGenBO {

	private int examId;
	private int subjectId;
	private Date dateStarttime;
	private Date dateEndtime;

	// many-to-one
	// private ExamDefinitionBO examDefinitionBO;
	private SubjectUtilBO subjectUtilBO;

	private ExamExamCourseSchemeDetailsBO examExamCourseSchemeDetailsBO;
	private ExaminationSessions examinationSessions;

	public ExamTimeTableBO() {
		super();
	}

	public ExamTimeTableBO(int examId, int subjectId, Date dateStarttime,
			Date dateEndtime,boolean isActive,ExaminationSessions examinationSessions) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.dateStarttime = dateStarttime;
		this.dateEndtime = dateEndtime;
		this.isActive=isActive;
		this.examinationSessions=examinationSessions;
	}
	
	public ExamTimeTableBO(int examId, int subjectId,boolean isActive) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.isActive=isActive;
		
	}

	public ExamTimeTableBO(int subjectId, Date dateStarttime, Date dateEndtime,ExaminationSessions examinationSessions) {
		this.subjectId = subjectId;
		this.dateStarttime = dateStarttime;
		this.dateEndtime = dateEndtime;
		this.examinationSessions=examinationSessions;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Date getDateStarttime() {
		return dateStarttime;
	}

	public void setDateStarttime(Date dateStarttime) {
		this.dateStarttime = dateStarttime;
	}

	public Date getDateEndtime() {
		return dateEndtime;
	}

	public void setDateEndtime(Date dateEndtime) {
		this.dateEndtime = dateEndtime;
	}

	// public ExamDefinitionBO getExamDefinitionBO() {
	// return examDefinitionBO;
	// }
	//
	// public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
	// this.examDefinitionBO = examDefinitionBO;
	// }

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setExamExamCourseSchemeDetailsBO(
			ExamExamCourseSchemeDetailsBO examExamCourseSchemeDetailsBO) {
		this.examExamCourseSchemeDetailsBO = examExamCourseSchemeDetailsBO;
	}

	public ExamExamCourseSchemeDetailsBO getExamExamCourseSchemeDetailsBO() {
		return examExamCourseSchemeDetailsBO;
	}

	public ExaminationSessions getExaminationSessions() {
		return examinationSessions;
	}

	public void setExaminationSessions(ExaminationSessions examinationSessions) {
		this.examinationSessions = examinationSessions;
	}

}
