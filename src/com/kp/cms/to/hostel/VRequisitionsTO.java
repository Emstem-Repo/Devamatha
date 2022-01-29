package com.kp.cms.to.hostel;

import java.io.Serializable;

public class VRequisitionsTO implements Serializable,Comparable<VRequisitionsTO> {
	private int id;
	
	private String roomStatus;
	private String roomType;
	private String applno;
	private String studentName;
	private boolean approve;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getApplno() {
		return applno;
	}
	public void setApplno(String applno) {
		this.applno = applno;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public boolean isApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	@Override
	public int compareTo(VRequisitionsTO arg0) {
		if(arg0!=null && this!=null && arg0.getApplno()!=null 
				 && this.getApplno()!=null){
				return this.getApplno().compareTo(arg0.getApplno());
		}else
		return 0;
	}
	
}
