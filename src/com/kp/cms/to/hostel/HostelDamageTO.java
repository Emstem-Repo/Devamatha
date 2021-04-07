package com.kp.cms.to.hostel;
import java.io.Serializable;
import java.util.List;

public class HostelDamageTO implements Serializable
{
	private int id;
	private String hostelName;
	private String studentName;
	private String staffName;
	private String staffId;
	private String studentOrStaffDisplay;
	private HostelTO hostelTO;
	private List<HlDamageTO> hlDamageTOList;
	private int hostelId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public HostelTO getHostelTO() {
		return hostelTO;
	}
	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	public List<HlDamageTO> getHlDamageTOList() {
		return hlDamageTOList;
	}
	public void setHlDamageTOList(List<HlDamageTO> hlDamageTOList) {
		this.hlDamageTOList = hlDamageTOList;
	}
	public int getHostelId() {
		return hostelId;
	}
	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}
	public String getStudentOrStaffDisplay() {
		return studentOrStaffDisplay;
	}
	public void setStudentOrStaffDisplay(String studentOrStaffDisplay) {
		this.studentOrStaffDisplay = studentOrStaffDisplay;
	}
}
