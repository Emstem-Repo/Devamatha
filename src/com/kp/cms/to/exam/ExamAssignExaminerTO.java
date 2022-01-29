package com.kp.cms.to.exam;

public class ExamAssignExaminerTO {
	private int id;
	private String examinerName;
	private String invigilatorType;
	private String roomNo;
	private String remarks;
	private String departementName;
	
	public ExamAssignExaminerTO(int id, String examinerName,
			String invigilatorType,  String roomNo,String remarks) {
		super();
		this.id = id;
		this.examinerName = examinerName;
		this.invigilatorType = invigilatorType;
		this.remarks = remarks;
		this.roomNo = roomNo;
	}
	
	public ExamAssignExaminerTO(int id, String examinerName,
			String invigilatorType,  String roomNo,String remarks,String departementName) {
		super();
		this.id = id;
		this.examinerName = examinerName;
		this.invigilatorType = invigilatorType;
		this.remarks = remarks;
		this.roomNo = roomNo;
		this.departementName=departementName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExaminerName() {
		return examinerName;
	}
	public void setExaminerName(String examinerName) {
		this.examinerName = examinerName;
	}
	public String getInvigilatorType() {
		return invigilatorType;
	}
	public void setInvigilatorType(String invigilatorType) {
		this.invigilatorType = invigilatorType;
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
	public String getDepartementName() {
		return departementName;
	}
	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}
	
	

}
