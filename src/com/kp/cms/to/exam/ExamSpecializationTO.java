package com.kp.cms.to.exam;

import java.io.Serializable;
/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationTO implements Serializable,Comparable<ExamSpecializationTO> {
	
	private int id;
	private String name;
	private String course;
	private int courseId;
	
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
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	@Override
	public int compareTo(ExamSpecializationTO arg0) {
		if(arg0!=null && this!=null && arg0.getCourse()!=null
				 && this.getCourse()!=null){
			return this.getCourse().compareTo(arg0.getCourse());
		}else
		return 0;
	}

}
