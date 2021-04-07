package com.kp.cms.to.hostel;

import java.util.List;

public class HlGroupTo {

	
	private int id;
	private String name;
	private HostelTO hostelTO;
	private String floorNo;
	private List<HostelGroupStudentTO> hlGroupStudentList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HostelTO getHostelTO() {
		return hostelTO;
	}
	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public List<HostelGroupStudentTO> getHlGroupStudentList() {
		return hlGroupStudentList;
	}
	public void setHlGroupStudentList(List<HostelGroupStudentTO> hlGroupStudentList) {
		this.hlGroupStudentList = hlGroupStudentList;
	}
}
