package com.kp.cms.to.examallotment;

import java.io.Serializable;

public class RoomAllotmentStatusDetailsTo implements Serializable {

	private int id;
	private String registerNo;
	private String className;
	private Integer columnNO;
	private Integer rowNO;
	private Integer seatingPosition;
	private String origRegisterNo;
	private String blockNo;
	private String floorNo;
	private String roomNo;
	private String roomCapacity;
	private String totalRoomCapacity;
	private String allotedRoomCapacity;
	private String tempField;
	private int tempRowsCount;
	private int noOfSeatsInDesk;
	private boolean isTempCheck;
	private int examRoomAllotmentDetailsId;
	private int studentId;
	private int classId;
	private int subject;
	private String subjectName;
	private boolean isAlloted;
	public boolean isTempCheck() {
		return isTempCheck;
	}
	public void setTempCheck(boolean isTempCheck) {
		this.isTempCheck = isTempCheck;
	}
	public int getNoOfSeatsInDesk() {
		return noOfSeatsInDesk;
	}
	public void setNoOfSeatsInDesk(int noOfSeatsInDesk) {
		this.noOfSeatsInDesk = noOfSeatsInDesk;
	}
	public int getTempRowsCount() {
		return tempRowsCount;
	}
	public void setTempRowsCount(int tempRowsCount) {
		this.tempRowsCount = tempRowsCount;
	}
	public String getTempField() {
		return tempField;
	}
	public void setTempField(String tempField) {
		this.tempField = tempField;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getColumnNO() {
		return columnNO;
	}
	public void setColumnNO(Integer columnNO) {
		this.columnNO = columnNO;
	}
	public Integer getRowNO() {
		return rowNO;
	}
	public void setRowNO(Integer rowNO) {
		this.rowNO = rowNO;
	}
	public Integer getSeatingPosition() {
		return seatingPosition;
	}
	public void setSeatingPosition(Integer seatingPosition) {
		this.seatingPosition = seatingPosition;
	}
	public String getOrigRegisterNo() {
		return origRegisterNo;
	}
	public void setOrigRegisterNo(String origRegisterNo) {
		this.origRegisterNo = origRegisterNo;
	}
	public String getBlockNo() {
		return blockNo;
	}
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getRoomCapacity() {
		return roomCapacity;
	}
	public void setRoomCapacity(String roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
	public String getTotalRoomCapacity() {
		return totalRoomCapacity;
	}
	public void setTotalRoomCapacity(String totalRoomCapacity) {
		this.totalRoomCapacity = totalRoomCapacity;
	}
	public String getAllotedRoomCapacity() {
		return allotedRoomCapacity;
	}
	public void setAllotedRoomCapacity(String allotedRoomCapacity) {
		this.allotedRoomCapacity = allotedRoomCapacity;
	}
	public int getExamRoomAllotmentDetailsId() {
		return examRoomAllotmentDetailsId;
	}
	public void setExamRoomAllotmentDetailsId(int examRoomAllotmentDetailsId) {
		this.examRoomAllotmentDetailsId = examRoomAllotmentDetailsId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getSubject() {
		return subject;
	}
	public void setSubject(int subject) {
		this.subject = subject;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public boolean isAlloted() {
		return isAlloted;
	}
	public void setAlloted(boolean isAlloted) {
		this.isAlloted = isAlloted;
	}
}
