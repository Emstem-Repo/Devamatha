package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class ConsolidateMarksCardForm  extends BaseActionForm{
	
	private String examNameId;
	private String classNameId;
//	private String date;
	private String examType;
	private String regNo;
	private String isDuplicate;
	private String totalCount;
	private Map<Integer, String> listClassName;
	private Map<Integer, String> listExamName;
	private String radioId;
	private  String regForConsolidate;
	
	private String year;
	//private String classSchemewiseId;
	private String courseId;
	private Map<Integer,String> classMap = new HashMap<Integer, String>();
	private List<ProgramTypeTO> programTypeList;
	
	
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset(){
		this.classNameId=null;
//		this.date=null;
		this.isDuplicate=null;
		this.regNo=null;
		this.isDuplicate="no";
		this.examType="";
		this.listClassName=null;
		this.listExamName=null;
	}

	public String getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
	}

	public String getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(String classNameId) {
		this.classNameId = classNameId;
	}

//	public String getDate() {
//		return date;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(String isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Map<Integer, String> getListClassName() {
		return listClassName;
	}

	public void setListClassName(Map<Integer, String> listClassName) {
		this.listClassName = listClassName;
	}

	public Map<Integer, String> getListExamName() {
		return listExamName;
	}

	public void setListExamName(Map<Integer, String> listExamName) {
		this.listExamName = listExamName;
	}

	public String getRadioId() {
		return radioId;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public String getRegForConsolidate() {
		return regForConsolidate;
	}

	public void setRegForConsolidate(String regForConsolidate) {
		this.regForConsolidate = regForConsolidate;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	
	
}