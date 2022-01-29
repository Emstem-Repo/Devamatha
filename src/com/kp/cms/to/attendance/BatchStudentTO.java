package com.kp.cms.to.attendance;

import java.io.Serializable;

import com.kp.cms.to.admin.StudentTO;

public class BatchStudentTO implements Serializable{
	
	private int id;
	private StudentTO studentTO;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StudentTO getStudentTO() {
		return studentTO;
	}
	public void setStudentTO(StudentTO studentTO) {
		this.studentTO = studentTO;
	}

}
