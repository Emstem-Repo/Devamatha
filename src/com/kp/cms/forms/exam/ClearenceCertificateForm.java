package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ClearanceCertificateTO;

public class ClearenceCertificateForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private String regNoFrom;
	private String regNoTo;
	private String examId;
	private String classId;
	private String examType;
	Map<Integer, String> examNameMap;
	Map<Integer, String> classMap;
	private String description;
	private boolean print;
	List<ClearanceCertificateTO> studentList;
	String hallTicketOrMarksCard;
	private String msg;
	
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
	
	public List<ClearanceCertificateTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<ClearanceCertificateTO> studentList) {
		this.studentList = studentList;
	}
	public String getHallTicketOrMarksCard() {
		return hallTicketOrMarksCard;
	}
	public void setHallTicketOrMarksCard(String hallTicketOrMarksCard) {
		this.hallTicketOrMarksCard = hallTicketOrMarksCard;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.regNoFrom=null;
		this.regNoTo=null;
		this.examId=null;
		this.classId=null;
		super.setProgramTypeId(null);
		this.examType="Regular";
		this.print=false;
		super.setYear(null);
		this.hallTicketOrMarksCard="H";
	}
	
}
