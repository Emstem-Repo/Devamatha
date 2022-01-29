package com.kp.cms.to.exam;

import java.io.Serializable;


public class ExamRoomMasterTO implements Serializable,Comparable<ExamRoomMasterTO>{

	private int id;
	private int roomTypeId;
	private String roomType;

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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
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
	@Override
	public int compareTo(ExamRoomMasterTO arg0) {
		if(arg0!=null && this!=null && arg0.getRoomType()!=null 
				 && this.getRoomType()!=null){
			
			return this.getRoomType().compareTo(arg0.getRoomType());
		}else
		return 0;
	}
	public String getBlockNo() {
		return blockNo;
	}
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}
	
	
	

}
