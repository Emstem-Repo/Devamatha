package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CancelPromotionTo;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;

public class CancelPromotionForm extends BaseActionForm{
	private int id;
	private String registerNo;
	private List<CancelPromotionTo> cancelPromotionTo;
	private String studentName;
	 private String classes;
	 private List<SubjectGroupDetailsTo> subjectGroupTo;
	private String method;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public List<SubjectGroupDetailsTo> getSubjectGroupTo() {
		return subjectGroupTo;
	}
	public void setSubjectGroupTo(List<SubjectGroupDetailsTo> subjectGroupTo) {
		this.subjectGroupTo = subjectGroupTo;
	}
	public void setCancelPromotionTo(List<CancelPromotionTo> cancelPromotionTo) {
		this.cancelPromotionTo = cancelPromotionTo;
	}
	public List<CancelPromotionTo> getCancelPromotionTo() {
		return cancelPromotionTo;
	}
	
}
