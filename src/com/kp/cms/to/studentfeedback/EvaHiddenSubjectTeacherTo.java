package com.kp.cms.to.studentfeedback;

import java.io.Serializable;


public class EvaHiddenSubjectTeacherTo implements Serializable
{

    private Integer id;
    private String className;
    private String subjectName;
    private String teacherName;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


}
