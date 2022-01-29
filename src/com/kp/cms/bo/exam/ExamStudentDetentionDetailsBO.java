package com.kp.cms.bo.exam;

/**
 * Feb 25, 2010 Created By 9Elements Team
 */
import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentDetentionDetailsBO extends ExamGenBO {

	private int studentId;
	private Date detentionDate;
	private String reason;
	private Integer schemeNo;

	// Many-to-one
	private StudentUtilBO studentUtilBO;

	public ExamStudentDetentionDetailsBO() {
		super();
	}

	public ExamStudentDetentionDetailsBO(int studentId, Date detentionDate,
			String reason) {
		super();
		this.studentId = studentId;
		this.detentionDate = detentionDate;
		this.reason = reason;
	}

	public ExamStudentDetentionDetailsBO(int studentId, Date detentionDate,
			String reason, Integer schemeNo) {
		super();
		this.studentId = studentId;
		this.detentionDate = detentionDate;
		this.reason = reason;
		this.schemeNo = schemeNo;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Date getDetentionDate() {
		return detentionDate;
	}

	public void setDetentionDate(Date detentionDate) {
		this.detentionDate = detentionDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}

	public Integer getSchemeNo() {
		return schemeNo;
	}

}
