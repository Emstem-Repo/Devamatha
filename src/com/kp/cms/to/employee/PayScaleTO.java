package com.kp.cms.to.employee;

import java.io.Serializable;

public class PayScaleTO implements Serializable{
private int id;
private String payScale;
private String scale;
private String teachingStaff;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getPayScale() {
	return payScale;
}
public void setPayScale(String payScale) {
	this.payScale = payScale;
}
public String getScale() {
	return scale;
}
public void setScale(String scale) {
	this.scale = scale;
}
public String getTeachingStaff() {
	return teachingStaff;
}
public void setTeachingStaff(String teachingStaff) {
	this.teachingStaff = teachingStaff;
}

}
