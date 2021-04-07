package com.kp.cms.bo.exam;

import java.util.Date;

public class ExamRoomMasterBO extends ExamGenBO {

	private ExamRoomTypeBO examRoomTypeBO;

	private int roomTypeId;
	private String roomNo;
	private int roomCapacity;
	private String comments;
	private int examCapacity;
	private int alloted;
	private int internalExamCapacity;
	private String floorNo;
	// added by sudhir
	private String blockNo;
	//
	public ExamRoomMasterBO() {
		super();
	}

	// To Add
	public ExamRoomMasterBO(int id, int roomTypeId, String roomNo,
			int roomCapacity, String comments, int alloted, int examCapacity,
			int internalExamCapacity, String floorNo, String createdBy,
			Date createdDate, Date lastModifiedDate, String modifiedBy,
			Boolean isActive) {
		super();
		this.id = id;
		this.roomTypeId = roomTypeId;
		this.examRoomTypeBO = new ExamRoomTypeBO();
		this.examRoomTypeBO.setId(roomTypeId);
		this.roomNo = roomNo;
		this.roomCapacity = roomCapacity;
		this.comments = comments;
		this.alloted = alloted;
		this.examCapacity = examCapacity;
		this.internalExamCapacity = internalExamCapacity;
		this.floorNo = floorNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}

	// To Modify
	public ExamRoomMasterBO(int id, int roomTypeId, String roomNo,
			int roomCapacity, String comments, int alloted, int examCapacity,
			int internalExamCapacity, String floorNo, Date lastModifiedDate,
			String modifiedBy, Boolean isActive) {
		super();
		this.id = id;
		this.roomTypeId = roomTypeId;
		this.roomNo = roomNo;
		this.roomCapacity = roomCapacity;
		this.comments = comments;
		this.alloted = alloted;
		this.examCapacity = examCapacity;
		this.internalExamCapacity = internalExamCapacity;
		this.floorNo = floorNo;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}

	// To Delete
	public ExamRoomMasterBO(int id, Date lastModifiedDate, String modifiedBy,
			Boolean isActive) {
		super();
		this.id = id;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}

	public ExamRoomTypeBO getExamRoomTypeBO() {
		return examRoomTypeBO;
	}

	public void setExamRoomTypeBO(ExamRoomTypeBO examRoomTypeBO) {
		this.examRoomTypeBO = examRoomTypeBO;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getExamCapacity() {
		return examCapacity;
	}

	public void setExamCapacity(int examCapacity) {
		this.examCapacity = examCapacity;
	}

	public int getAlloted() {
		return alloted;
	}

	public void setAlloted(int alloted) {
		this.alloted = alloted;
	}

	public int getInternalExamCapacity() {
		return internalExamCapacity;
	}

	public void setInternalExamCapacity(int internalExamCapacity) {
		this.internalExamCapacity = internalExamCapacity;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

}
