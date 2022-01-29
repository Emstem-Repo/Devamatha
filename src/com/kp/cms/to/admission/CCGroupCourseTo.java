package com.kp.cms.to.admission;

import java.io.Serializable;

import com.kp.cms.to.exam.StudentMarksTO;

public class CCGroupCourseTo implements Serializable,Comparable<CCGroupCourseTo> {
	
	private int id;
	private int courseId;
	private String courseName;
	private int groupId;
	private String groupName;
	private String programName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	@Override
	public int compareTo(CCGroupCourseTo o) {
		if(o!=null && o.getProgramName()!=null && this.programName!=null){
			return this.programName.compareTo(o.programName);
		}else
			return 0;
	}
}