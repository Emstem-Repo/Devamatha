package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamPromotionCriteriaTO implements Serializable,Comparable<ExamPromotionCriteriaTO> {

private int id;
private String courseName;
private int fromScheme;
private int toScheme;
private String scheme;
private String maxBackLogCountPrcntg;
private int maxBackLogNumber;
public ExamPromotionCriteriaTO(int id, String courseName, int fromScheme,
		int toScheme, String scheme, String maxBackLogCountPrcntg,
		int maxBackLogNumber) {
	super();
	this.id = id;
	this.courseName = courseName;
	this.fromScheme = fromScheme;
	this.toScheme = toScheme;
	this.scheme = scheme;
	this.maxBackLogCountPrcntg = maxBackLogCountPrcntg;
	this.maxBackLogNumber = maxBackLogNumber;
}
public int getId() {
	return id;
}
public String getCourseName() {
	return courseName;
}
public int getFromScheme() {
	return fromScheme;
}
public int getToScheme() {
	return toScheme;
}
public String getScheme() {
	return scheme;
}
public String getMaxBackLogCountPrcntg() {
	return maxBackLogCountPrcntg;
}
public int getMaxBackLogNumber() {
	return maxBackLogNumber;
}
@Override
public int compareTo(ExamPromotionCriteriaTO arg0) {
	if(arg0!=null && this!=null && arg0.getCourseName()!=null
			 && this.getCourseName()!=null){
		return this.getCourseName().compareTo(arg0.getCourseName());
	}else
	return 0;
}

}