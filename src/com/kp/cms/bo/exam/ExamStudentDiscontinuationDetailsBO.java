package com.kp.cms.bo.exam;

/**
 * Feb 25, 2010
 * Created By 9Elements Team
 */
import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentDiscontinuationDetailsBO extends ExamGenBO {

	private int studentId;
	private Date discontinueDate;
	private String reason;
	private Integer schemeNo;

	// Many-to-one
	private StudentUtilBO studentUtilBO;

	public ExamStudentDiscontinuationDetailsBO() {
		super();
	}

	public ExamStudentDiscontinuationDetailsBO(int studentId,
			Date discontinueDate, String reason) {
		super();
		this.studentId = studentId;
		this.discontinueDate = discontinueDate;
		this.reason = reason;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Date getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(Date discontinueDate) {
		this.discontinueDate = discontinueDate;
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

	public ExamStudentDiscontinuationDetailsBO(int studentId,
			Date discontinueDate, String reason, Integer schemeNo) {
		super();
		this.studentId = studentId;
		this.discontinueDate = discontinueDate;
		this.reason = reason;
		this.schemeNo = schemeNo;
	}

}
