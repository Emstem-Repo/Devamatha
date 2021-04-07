package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksCardSiNoGen;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

public class MarksCardForm  extends BaseActionForm{
	
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
	private String isRegular;
	private String isSupplementary;
	private String isImprovement;
	private String isRevaluation;
	private String semister;
	private List<ProgramTypeTO> programTypeList;
	private List<StudentTO> studentList;
	private Map<Integer, MarksCardSiNoGen> studentMap;
	private String examName;
	private String className;
	private int halfLength;
	
	// by Arun Sudhakaran
	private String schemes;	// all schemes related an exam with "," as separator. Initialized in handler
	private Map<Integer, String> semesters;
	
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
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
		this.isRegular=null;
		this.isSupplementary=null;
		this.isImprovement=null;
		this.isRevaluation=null;
		this.studentList=null;
		this.studentMap=null;
	}

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

	public String getIsRegular() {
		return isRegular;
	}

	public void setIsRegular(String isRegular) {
		this.isRegular = isRegular;
	}

	public String getIsSupplementary() {
		return isSupplementary;
	}

	public void setIsSupplementary(String isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public String getIsImprovement() {
		return isImprovement;
	}

	public void setIsImprovement(String isImprovement) {
		this.isImprovement = isImprovement;
	}

	public String getIsRevaluation() {
		return isRevaluation;
	}

	public void setIsRevaluation(String isRevaluation) {
		this.isRevaluation = isRevaluation;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<StudentTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}

	public Map<Integer, MarksCardSiNoGen> getStudentMap() {
		return studentMap;
	}

	public void setStudentMap(Map<Integer, MarksCardSiNoGen> studentMap) {
		this.studentMap = studentMap;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getHalfLength() {
		return halfLength;
	}

	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}

	public String getSchemes() {
		return schemes;
	}

	public void setSchemes(String schemes) {
		this.schemes = schemes;
	}

	public Map<Integer, String> getSemesters() {
		return semesters;
	}

	public void setSemesters(Map<Integer, String> semesters) {
		this.semesters = semesters;
	}
}