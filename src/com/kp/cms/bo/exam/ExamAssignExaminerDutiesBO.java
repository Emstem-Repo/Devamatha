package com.kp.cms.bo.exam;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.util.Date;

public class ExamAssignExaminerDutiesBO extends ExamGenBO {

	private Date dateTime;
	private int invigilatorDutyTypeId;
	private String roomNos;
	private String remarks;
	private int assignExaminerExamId;
	private int isPresent;

	private ExamInvigilationDutyBO examInvigilationDutyBO;
	private ExamAssignExaminerToExamBO examAssignExaminerToExamBO;

	public ExamAssignExaminerDutiesBO() {
		super();
	}

	public ExamAssignExaminerDutiesBO(int id, int isPresent) {
		super();
		this.id = id;
		this.isPresent = isPresent;
	}

	public ExamAssignExaminerDutiesBO(Date dateTime, int invigilatorDutyTypeId,
			String roomNos, String remarks, int assignExaminerExamId) {
		super();
		this.dateTime = dateTime;
		this.invigilatorDutyTypeId = invigilatorDutyTypeId;
		this.roomNos = roomNos;
		this.remarks = remarks;
		this.assignExaminerExamId = assignExaminerExamId;
	}

	public ExamAssignExaminerDutiesBO(int id, int invigilatorDutyTypeId,
			String roomNos, String remarks) {
		super();
		this.id = id;
		this.invigilatorDutyTypeId = invigilatorDutyTypeId;
		this.remarks = remarks;
		this.roomNos = roomNos;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getInvigilatorDutyTypeId() {
		return invigilatorDutyTypeId;
	}

	public void setInvigilatorDutyTypeId(int invigilatorDutyTypeId) {
		this.invigilatorDutyTypeId = invigilatorDutyTypeId;
	}

	public String getRoomNos() {
		return roomNos;
	}

	public void setRoomNos(String roomNos) {
		this.roomNos = roomNos;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getAssignExaminerExamId() {
		return assignExaminerExamId;
	}

	public void setAssignExaminerExamId(int assignExaminerExamId) {
		this.assignExaminerExamId = assignExaminerExamId;
	}

	public ExamInvigilationDutyBO getExamInvigilationDutyBO() {
		return examInvigilationDutyBO;
	}

	public void setExamInvigilationDutyBO(
			ExamInvigilationDutyBO examInvigilationDutyBO) {
		this.examInvigilationDutyBO = examInvigilationDutyBO;
	}

	public ExamAssignExaminerToExamBO getExamAssignExaminerToExamBO() {
		return examAssignExaminerToExamBO;
	}

	public void setExamAssignExaminerToExamBO(
			ExamAssignExaminerToExamBO examAssignExaminerToExamBO) {
		this.examAssignExaminerToExamBO = examAssignExaminerToExamBO;
	}

	public void setIsPresent(int isPresent) {
		this.isPresent = isPresent;
	}

	public int getIsPresent() {
		return isPresent;
	}

}
