package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;

public class ModerationMarksEntryBo implements Serializable {
	
	private String theoryMarks;
	private String practicalMarks;
	private String previousTheoryMarks;
	private String previousPracticalMarks;
	private boolean revaluation;
	private boolean moderation;

	// Many-to-one
	private SubjectUtilBO subjectUtilBO;
	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBo;
	private Classes classes;
	private String comments;
	private Integer subjectId;
	private Integer examId;
	private Integer studentId;
	private Integer classId;
	
	private Integer id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean scrutiny;
	private boolean challengeValuation;
	

	
	public String getTheoryMarks() {
		return theoryMarks;
	}

	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public String getPracticalMarks() {
		return practicalMarks;
	}

	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getPreviousTheoryMarks() {
		return previousTheoryMarks;
	}

	public void setPreviousTheoryMarks(String previousTheoryMarks) {
		this.previousTheoryMarks = previousTheoryMarks;
	}

	public String getPreviousPracticalMarks() {
		return previousPracticalMarks;
	}

	public void setPreviousPracticalMarks(String previousPracticalMarks) {
		this.previousPracticalMarks = previousPracticalMarks;
	}

	

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public StudentUtilBO getStudentUtilBo() {
		return studentUtilBo;
	}

	public void setStudentUtilBo(StudentUtilBO studentUtilBo) {
		this.studentUtilBo = studentUtilBo;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean getRevaluation() {
		return revaluation;
	}

	public void setRevaluation(boolean revaluation) {
		this.revaluation = revaluation;
	}

	public boolean getModeration() {
		return moderation;
	}

	public void setModeration(boolean moderation) {
		this.moderation = moderation;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public boolean getScrutiny() {
		return scrutiny;
	}

	public void setScrutiny(boolean scrutiny) {
		this.scrutiny = scrutiny;
	}

	public boolean getChallengeValuation() {
		return challengeValuation;
	}

	public void setChallengeValuation(boolean challengeValuation) {
		this.challengeValuation = challengeValuation;
	}

	
	
	


}
