package com.kp.cms.to.hostel;

import java.io.Serializable;

public class DisciplinaryTypeTO implements Serializable,Comparable<DisciplinaryTypeTO> {
	private int id;
	private String name;
	private String description;
	private String date;
	private String studentName;
	private String applNo;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	@Override
	public int compareTo(DisciplinaryTypeTO attendanceTo1) {
		if (attendanceTo1 != null) {
			if(attendanceTo1.getDate() == null) {
				attendanceTo1.setDate("");
			}
			if(this.getDate() == null) {
				this.setDate("");
			}
			return this.getDate().compareTo(attendanceTo1.getDate());
		}
		return 0;
	}
}
