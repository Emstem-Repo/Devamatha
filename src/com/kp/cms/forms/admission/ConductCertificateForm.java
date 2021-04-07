package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;

public class ConductCertificateForm extends BaseActionForm{
	private String year;
	private String method;
	private String classes;
	private String fromUsn;
	private String toUsn;
	Map<Integer,String> classMap;
	List<PrintTcDetailsTo>studentList;
	private List<TCDetailsTO> tcDetails; 		
	private String studentName;
	private String studentId;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getFromUsn() {
		return fromUsn;
	}
	public void setFromUsn(String fromUsn) {
		this.fromUsn = fromUsn;
	}
	public String getToUsn() {
		return toUsn;
	}
	public void setToUsn(String toUsn) {
		this.toUsn = toUsn;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public List<PrintTcDetailsTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<PrintTcDetailsTo> studentList) {
		this.studentList = studentList;
	}
	public List<TCDetailsTO> getTcDetails() {
		return tcDetails;
	}
	public void setTcDetails(List<TCDetailsTO> tcDetails) {
		this.tcDetails = tcDetails;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);		
		return actionErrors;
	}
	
	public void resetFields()
	{
		this.fromUsn=null;
		this.toUsn=null;
		this.classes=null;
		this.year="2015";
		this.tcDetails=null;
	}
}
