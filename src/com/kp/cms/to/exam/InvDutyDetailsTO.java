package com.kp.cms.to.exam;

import java.util.ArrayList;

public class InvDutyDetailsTO {

	private int id;
	private int employerId;
	// private boolean isSelected;
	private boolean isInvigilatorsFlag;
	private boolean isReliversFlag;
	private boolean isTypeFlag;
	private String examinerName;
	private String roomNo;
	private String remarks;
	private String invigilatorType;
	private int invigilatorTypeId;
	private int examId;
	private int assignExaminerExamId;
	private ArrayList<ExaminerDutiesTO> listInvigilator;
	private String departementName;
	
	public InvDutyDetailsTO(int employerId, String examinerName,
			int assignExaminerExamId, String invigilatorType, String roomNo,
			String remarks, int invigilatorTypeId, int id,
			ArrayList<ExaminerDutiesTO> listInvigilator) {
		super();
		this.employerId = employerId;
		this.examinerName = examinerName;
		this.roomNo = roomNo;
		this.remarks = remarks;
		this.invigilatorType = invigilatorType;
		this.invigilatorTypeId = invigilatorTypeId;
		this.id = id;
		this.listInvigilator = listInvigilator;
		this.assignExaminerExamId = assignExaminerExamId;
	}

	public InvDutyDetailsTO(int id, int examId, int invigilatorTypeId,
			String roomNo, String remarks, int employerId) {
		super();
		this.id = id;
		this.examId = examId;
		this.invigilatorTypeId = invigilatorTypeId;
		this.roomNo = roomNo;
		this.remarks = remarks;
		this.employerId = employerId;
	}
	public InvDutyDetailsTO(int employerId, String examinerName,
			int assignExaminerExamId, String invigilatorType, String roomNo,
			String remarks, int invigilatorTypeId, int id,
			ArrayList<ExaminerDutiesTO> listInvigilator,String departementName) {
		super();
		this.employerId = employerId;
		this.examinerName = examinerName;
		this.roomNo = roomNo;
		this.remarks = remarks;
		this.invigilatorType = invigilatorType;
		this.invigilatorTypeId = invigilatorTypeId;
		this.id = id;
		this.listInvigilator = listInvigilator;
		this.assignExaminerExamId = assignExaminerExamId;
		this.departementName=departementName;
	}

	public String getExaminerName() {
		return examinerName;
	}

	public void setExaminerName(String examinerName) {
		this.examinerName = examinerName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}

	public int getEmployerId() {
		return employerId;
	}

	public String getInvigilatorType() {
		return invigilatorType;
	}

	public void setInvigilatorType(String invigilatorType) {
		this.invigilatorType = invigilatorType;
	}

	public InvDutyDetailsTO() {
		super();
	}

	public boolean getIsReliversFlag() {
		return isReliversFlag;
	}

	public void setReliversFlag(boolean isReliversFlag) {
		this.isReliversFlag = isReliversFlag;
	}

	public boolean getIsInvigilatorsFlag() {
		return isInvigilatorsFlag;
	}

	public void setInvigilatorsFlag(boolean isInvigilatorsFlag) {
		this.isInvigilatorsFlag = isInvigilatorsFlag;
	}

	public boolean getIsTypeFlag() {
		return isTypeFlag;
	}

	public void setTypeFlag(boolean isTypeFlag) {
		this.isTypeFlag = isTypeFlag;
	}

	public int getInvigilatorTypeId() {
		return invigilatorTypeId;
	}

	public void setInvigilatorTypeId(int invigilatorTypeId) {
		this.invigilatorTypeId = invigilatorTypeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public ArrayList<ExaminerDutiesTO> getListInvigilator() {
		return listInvigilator;
	}

	public void setListInvigilator(ArrayList<ExaminerDutiesTO> listInvigilator) {
		this.listInvigilator = listInvigilator;
	}

	public int getAssignExaminerExamId() {
		return assignExaminerExamId;
	}

	public void setAssignExaminerExamId(int assignExaminerExamId) {
		this.assignExaminerExamId = assignExaminerExamId;
	}

	public String getDepartementName() {
		return departementName;
	}

	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}

}
