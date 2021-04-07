package com.kp.cms.bo.admin;
import java.io.Serializable;
@SuppressWarnings("serial")
public class TeacherDepartment implements Serializable{
private int id;
private Department departmentId;
private Users teacherId;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Department getDepartmentId() {
	return departmentId;
}
public void setDepartmentId(Department departmentId) {
	this.departmentId = departmentId;
}
public Users getTeacherId() {
	return teacherId;
}
public void setTeacherId(Users teacherId) {
	this.teacherId = teacherId;
}


}
