package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;

public class HonorsEntryForm extends BaseActionForm 
{
	private int id;
	private String regNo;
	private String semister;
	private String programId;
	private String courseId;
	private List<ProgramTO>programList;
	private Map<Integer, String> courseMap;
	private Map<Integer, Integer> semisterMap;
	private String tempyear;
	private String academicYear;
	private String add;
	
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		// TODO Auto-generated method stub
		super.reset(mapping, request);
		this.courseId=null;
		this.programId=null;
		this.regNo=null;
		this.semister=null;
		this.add="Add";
	}
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public Map<Integer, Integer> getSemisterMap() {
		return semisterMap;
	}
	public void setSemisterMap(Map<Integer, Integer> semisterMap) {
		this.semisterMap = semisterMap;
	}
	public String getTempyear() {
		return tempyear;
	}
	public void setTempyear(String tempyear) {
		this.tempyear = tempyear;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
