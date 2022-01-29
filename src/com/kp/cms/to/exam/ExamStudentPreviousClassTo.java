package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamStudentPreviousClassTo implements Serializable,Comparable<ExamStudentPreviousClassTo>{
private String className;
private int schemeNo;
private int classId;

public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}
public int getSchemeNo() {
	return schemeNo;
}
public void setSchemeNo(int schemeNo) {
	this.schemeNo = schemeNo;
}
public int getClassId() {
	return classId;
}
public void setClassId(int classId) {
	this.classId = classId;
}
@Override
public int compareTo(ExamStudentPreviousClassTo arg0) {
	
	if(arg0!=null && this!=null && arg0.getSchemeNo()!=0 && this.getSchemeNo()!=0){
		if(arg0.getSchemeNo() >  this.getSchemeNo())
			return 1;
		else if(arg0.getSchemeNo() <  this.getSchemeNo())
			return -1;
		else
			return 0;
	}
	return 0;
}

}
