package com.kp.cms.to.admission;

import java.io.Serializable;


public class ExamCenterTO implements Serializable{
	
	private int id;
	private String programName;
	private String center;
	private Integer seatNoFrom;
	private Integer seatNoTo;
	private Integer currentSeatNo;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String seatNoPrefix;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public Integer getSeatNoFrom() {
		return seatNoFrom;
	}
	public void setSeatNoFrom(Integer seatNoFrom) {
		this.seatNoFrom = seatNoFrom;
	}
	public Integer getSeatNoTo() {
		return seatNoTo;
	}
	public void setSeatNoTo(Integer seatNoTo) {
		this.seatNoTo = seatNoTo;
	}
	public Integer getCurrentSeatNo() {
		return currentSeatNo;
	}
	public void setCurrentSeatNo(Integer currentSeatNo) {
		this.currentSeatNo = currentSeatNo;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getSeatNoPrefix() {
		return seatNoPrefix;
	}
	public void setSeatNoPrefix(String seatNoPrefix) {
		this.seatNoPrefix = seatNoPrefix;
	}

}
