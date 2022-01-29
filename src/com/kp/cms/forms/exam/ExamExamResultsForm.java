package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamExamResultsTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamExamResultsForm extends BaseActionForm{
	private int id;
	private String examNameId;
	private String publishDate;
	private String courseIdsFrom ;
	private String courseIdsTo;
	private String classValues;
	private String publishOverallInternalComponentsOnly;
	private List<KeyValueTO> listExamName;
	private List<ExamExamResultsTO> listExamResult;
	private int internalComponents;
	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	 //added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;
	 private String year;
	 private String exname;
	 private String extype;
	 private Map<String,String> deaneryMap;
	 private String deanaryName;
	
	public String getExtype() {
		return extype;
	}

	public void setExtype(String extype) {
		this.extype = extype;
	}

	public void clearPage() {
		this.publishDate="";
		this.publishOverallInternalComponentsOnly="off";
		this.courseIdsFrom="";
	}
	
	 public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
	
	public String getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
	}

	public String getCourseIdsFrom() {
		return courseIdsFrom;
	}
	public void setCourseIdsFrom(String courseIdsFrom) {
		this.courseIdsFrom = courseIdsFrom;
	}
	public String getCourseIdsTo() {
		return courseIdsTo;
	}
	public void setCourseIdsTo(String courseIdsTo) {
		this.courseIdsTo = courseIdsTo;
	}
	public String getClassValues() {
		return classValues;
	}
	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}
	
	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}
	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}
	public Map<Integer, String> getMapClass() {
		return mapClass;
	}
	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}
	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}
	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishOverallInternalComponentsOnly() {
		return publishOverallInternalComponentsOnly;
	}
	public void setPublishOverallInternalComponentsOnly(
			String publishOverallInternalComponentsOnly) {
		this.publishOverallInternalComponentsOnly = publishOverallInternalComponentsOnly;
	}
	public List<ExamExamResultsTO> getListExamResult() {
		return listExamResult;
	}
	public void setListExamResult(List<ExamExamResultsTO> listExamResult) {
		this.listExamResult = listExamResult;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInternalComponents(int internalComponents) {
		this.internalComponents = internalComponents;
	}

	public int getInternalComponents() {
		return internalComponents;
	}

	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}

	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getExname() {
		return exname;
	}

	public void setExname(String exname) {
		this.exname = exname;
	}

	public Map<String, String> getDeaneryMap() {
		return deaneryMap;
	}

	public void setDeaneryMap(Map<String, String> deaneryMap) {
		this.deaneryMap = deaneryMap;
	}

	public String getDeanaryName() {
		return deanaryName;
	}

	public void setDeanaryName(String deanaryName) {
		this.deanaryName = deanaryName;
	}

}
