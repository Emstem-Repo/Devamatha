package com.kp.cms.bo.exam;

/**
 * Created by 9Elelments Team
 */
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ExamFooterAgreementBO extends ExamGenBO implements Serializable{

	private int programTypeId;
	private int isAgreement;
	private int isFooter;
	private Date date;
	private String academicYear;
	private String description;
	private String hallTktOrMarksCard;
	
	private ExamProgramTypeUtilBO examProgramTypeUtilBO;

	public ExamFooterAgreementBO() {
		super();
	}

	public void setUserAdd(String userId) {
		this.createdBy = userId;
		this.modifiedBy = userId;
		this.createdDate = new Date();
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public void setUserUpdate(String userId) {

		this.modifiedBy = userId;

		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public ExamFooterAgreementBO(String academicYear, int programTypeId,
			Date date, String description, int isAgreement, int isFooter) {
		super();
		this.academicYear = academicYear;
		this.setProgramTypeId(programTypeId);
		this.date = date;
		this.description = description;
		this.isAgreement = isAgreement;
		this.isFooter = isFooter;
	}

	public int getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(int isAgreement) {
		this.isAgreement = isAgreement;
	}

	public int getIsFooter() {
		return isFooter;
	}

	public void setIsFooter(int isFooter) {
		this.isFooter = isFooter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}

	public int getProgramTypeId() {
		return programTypeId;
	}

	public void setExamProgramTypeUtilBO(ExamProgramTypeUtilBO examProgramTypeUtilBO) {
		this.examProgramTypeUtilBO = examProgramTypeUtilBO;
	}

	public ExamProgramTypeUtilBO getExamProgramTypeUtilBO() {
		return examProgramTypeUtilBO;
	}

	public String getHallTktOrMarksCard() {
		return hallTktOrMarksCard;
	}

	public void setHallTktOrMarksCard(String hallTktOrMarksCard) {
		this.hallTktOrMarksCard = hallTktOrMarksCard;
	}

	
}
