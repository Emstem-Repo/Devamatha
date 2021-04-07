package com.kp.cms.bo.exam;

import java.io.Serializable;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class StudentPreviousClassHistory implements Serializable,Comparable<StudentPreviousClassHistory> {
	
	private int id;
	private Integer academicYear;
	private Integer schemeNo; 
	private Student student;
	private Classes classes;
	
	public StudentPreviousClassHistory() {
		super();
	}
	
	public StudentPreviousClassHistory(int id, Integer academicYear,
			Integer schemeNo, Student student, Classes classes) {
		super();
		this.id = id;
		this.academicYear = academicYear;
		this.schemeNo = schemeNo;
		this.student = student;
		this.classes = classes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Override
	public int compareTo(StudentPreviousClassHistory o) {
		if(this != null&& o != null)
		{
			if(this.getSchemeNo() != null && o.getSchemeNo()!=null){
				if(this.getSchemeNo() > o.getSchemeNo())
					return 1;
				else if(this.getSchemeNo() < o.getSchemeNo())
					return -1;
				else
					return 0;
			}
		}
		
		return 0;
	}
	
	
	
}
