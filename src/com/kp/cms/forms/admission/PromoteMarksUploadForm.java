package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class PromoteMarksUploadForm extends BaseActionForm{

	private String academicYear;
	private FormFile theFile;
	private String subject1;
	private String subject2;
	private String subject3;
	private String subject4;
	private String subject5;
	private String subject6;
	private String subject7;
	private String practicalSub1;
	private String practicalSub2;
	private String practicalSub3;
	private String practicalSub4;
	private String practicalSub5;
	private String practicalSub6;
	private String practicalSub7;
	private String courseName;
	private Map<String,String> courses;
	private List<String> dupRegNos;
	public FormFile getTheFile() {
		return theFile;
	}
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
 		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.theFile=null;
		this.subject1=null;
		this.subject2=null;
		this.subject3=null;
		this.subject4=null;
		this.subject5=null;
		this.subject6=null;
		this.subject7=null;
		this.practicalSub1=null;
		this.practicalSub2=null;
		this.practicalSub3=null;
		this.practicalSub4=null;
		this.practicalSub5=null;
		this.practicalSub6=null;
		this.practicalSub7=null;
		this.courseName=null;
	}
	public String getSubject1() {
		return subject1;
	}
	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}
	public String getSubject2() {
		return subject2;
	}
	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}
	public String getSubject3() {
		return subject3;
	}
	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}
	public String getSubject4() {
		return subject4;
	}
	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}
	public String getSubject5() {
		return subject5;
	}
	public void setSubject5(String subject5) {
		this.subject5 = subject5;
	}
	public String getSubject6() {
		return subject6;
	}
	public void setSubject6(String subject6) {
		this.subject6 = subject6;
	}
	public String getSubject7() {
		return subject7;
	}
	public void setSubject7(String subject7) {
		this.subject7 = subject7;
	}
	public String getPracticalSub1() {
		return practicalSub1;
	}
	public void setPracticalSub1(String practicalSub1) {
		this.practicalSub1 = practicalSub1;
	}
	public String getPracticalSub2() {
		return practicalSub2;
	}
	public void setPracticalSub2(String practicalSub2) {
		this.practicalSub2 = practicalSub2;
	}
	public String getPracticalSub3() {
		return practicalSub3;
	}
	public void setPracticalSub3(String practicalSub3) {
		this.practicalSub3 = practicalSub3;
	}
	public String getPracticalSub4() {
		return practicalSub4;
	}
	public void setPracticalSub4(String practicalSub4) {
		this.practicalSub4 = practicalSub4;
	}
	public String getPracticalSub5() {
		return practicalSub5;
	}
	public void setPracticalSub5(String practicalSub5) {
		this.practicalSub5 = practicalSub5;
	}
	public String getPracticalSub6() {
		return practicalSub6;
	}
	public void setPracticalSub6(String practicalSub6) {
		this.practicalSub6 = practicalSub6;
	}
	public String getPracticalSub7() {
		return practicalSub7;
	}
	public void setPracticalSub7(String practicalSub7) {
		this.practicalSub7 = practicalSub7;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Map<String,String> getCourses() {
		return courses;
	}

	public void setCourses(Map<String,String> courses) {
		this.courses = courses;
	}
	public List<String> getDupRegNos() {
		return dupRegNos;
	}
	public void setDupRegNos(List<String> dupRegNos) {
		this.dupRegNos = dupRegNos;
	}
	
	

}
