package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.KeyValueTO;

public class AdminHallTicketForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String regNoFrom;
	private String regNoTo;
	private String examId;
	private String classId;
	private String examType;
	private String examTypePrint;
	private List<KeyValueTO> programTypeList;
	Map<Integer, String> examNameMap;
	Map<Integer, String> classMap;
	private String description;
	private boolean print;
	List<HallTicketTo> studentList;
	
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	
	public List<HallTicketTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<HallTicketTo> studentList) {
		this.studentList = studentList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.studentList=null;
		this.regNoFrom=null;
		this.regNoTo=null;
		this.examId=null;
		this.classId=null;
		super.setProgramTypeId(null);
		this.examType="Regular";
		this.print=false;
	}
	
	public String getExamTypePrint() {
		return examTypePrint;
	}
	public void setExamTypePrint(String examTypePrint) {
		this.examTypePrint = examTypePrint;
	}
	
}
