package com.kp.cms.to.attendance;

import java.io.Serializable;

public class TeacherDepartmentTO implements Serializable{
private String departmentName;
private String teacherName;
private Integer id;
private String deptTeacherName;

public String getDeptTeacherName() {
	return deptTeacherName;
}
public void setDeptTeacherName(String deptTeacherName) {
	this.deptTeacherName = deptTeacherName;
}
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}
public String getTeacherName() {
	return teacherName;
}
public void setTeacherName(String teacherName) {
	this.teacherName = teacherName;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}

}
