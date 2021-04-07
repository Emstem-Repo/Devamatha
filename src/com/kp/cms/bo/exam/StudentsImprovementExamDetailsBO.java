package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;

public class StudentsImprovementExamDetailsBO{
	
	private int id;
	private ExamDefinition examDef;
	private Classes classes;
	private Student student;
	private boolean improvementFlag;
	private String subjectId;
	private String createdBy;;
	private Date createdDate;
	public ExamDefinition getExamDef() {
		return examDef;
	}
	public void setExamDef(ExamDefinition examDef) {
		this.examDef = examDef;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public boolean isImprovementFlag() {
		return improvementFlag;
	}
	public void setImprovementFlag(boolean improvementFlag) {
		this.improvementFlag = improvementFlag;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
