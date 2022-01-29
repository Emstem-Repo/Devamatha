package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;

public class OpenInternalExamForm extends BaseActionForm {


	private String academicYear;
	private String examId;
	private String startDate;
	private String endDate;
	private Map<Integer, String> examMap;
	private List<ExamDefinitionTO> examList;
	private int id;
	private String theoryPractical;
	private Map<Integer, String> programTypeMap;
	private String newProgramTypeId;
	private String[] classes;
	private Map<Integer, String> classesMap;
	private String[] oldClasses;
	private Map<Integer, Integer> clasMap;
	private String[] selClasses;
	private Map<Integer,String> selClassesMap;
	private String[] stayClass;
	private Map<Integer,Integer> totalClassesMap;
	private String displayName;
	
	public Map<Integer, Integer> getTotalClassesMap() {
		return totalClassesMap;
	}

	public void setTotalClassesMap(Map<Integer, Integer> totalClassesMap) {
		this.totalClassesMap = totalClassesMap;
	}

	public String[] getStayClass() {
		return stayClass;
	}

	public void setStayClass(String[] stayClass) {
		this.stayClass = stayClass;
	}

	public Map<Integer, String> getSelClassesMap() {
		return selClassesMap;
	}

	public void setSelClassesMap(Map<Integer, String> selClassesMap) {
		this.selClassesMap = selClassesMap;
	}

	public String[] getSelClasses() {
		return selClasses;
	}

	public void setSelClasses(String[] selClasses) {
		this.selClasses = selClasses;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);

		this.academicYear = null;
		this.examId="";
		this.endDate=null;
		this.startDate=null;
		this.id=0;
		this.theoryPractical="";
		this.newProgramTypeId=null;
		this.classes=null;
		this.classesMap=null;
		this.oldClasses=null;
		this.clasMap=null;
		this.selClasses=null;
		this.selClassesMap=null;
		this.displayName = null;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<Integer, String> getExamMap() {
		return examMap;
	}

	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}

	public List<ExamDefinitionTO> getExamList() {
		return examList;
	}

	public void setExamList(List<ExamDefinitionTO> examList) {
		this.examList = examList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTheoryPractical() {
		return theoryPractical;
	}

	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}

	public Map<Integer, String> getProgramTypeMap() {
		return programTypeMap;
	}

	public void setProgramTypeMap(Map<Integer, String> programTypeMap) {
		this.programTypeMap = programTypeMap;
	}

	public String getNewProgramTypeId() {
		return newProgramTypeId;
	}

	public void setNewProgramTypeId(String newProgramTypeId) {
		this.newProgramTypeId = newProgramTypeId;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public Map<Integer, String> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<Integer, String> classesMap) {
		this.classesMap = classesMap;
	}

	public String[] getOldClasses() {
		return oldClasses;
	}

	public void setOldClasses(String[] oldClasses) {
		this.oldClasses = oldClasses;
	}

	public Map<Integer, Integer> getClasMap() {
		return clasMap;
	}

	public void setClasMap(Map<Integer, Integer> clasMap) {
		this.clasMap = clasMap;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
