package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.ExamUpdateExcludeWithheldTO;

/**
 * Jan 6, 2010 Created By 9Elements
 */
public class ExamUpdateExcludeWithheldForm extends BaseActionForm {
	private String examNameId;
	private String examNameId_value;
	private String courseName;
	private String schemeName;
	private String courseId;
	private String schemeNo;
	private String exclude;
	private String withheld;
	private List<KeyValueTO> listExamName;
	private List<ExamUpdateExcludeWithheldTO> listUpdateExcludeWithheld;
	private String oldScheme;
	 //added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;        
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() {
		this.examNameId="";
		this.courseName = "";
		this.schemeNo = "";
		
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}



	public String getExamNameId() {
		return examNameId;
	}



	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
	}



	public String getExamNameId_value() {
		return examNameId_value;
	}



	public void setExamNameId_value(String examNameId_value) {
		this.examNameId_value = examNameId_value;
	}



	public List<ExamUpdateExcludeWithheldTO> getListUpdateExcludeWithheld() {
		return listUpdateExcludeWithheld;
	}



	public void setListUpdateExcludeWithheld(
			List<ExamUpdateExcludeWithheldTO> listUpdateExcludeWithheld) {
		this.listUpdateExcludeWithheld = listUpdateExcludeWithheld;
	}



	public String getExclude() {
		return exclude;
	}



	public void setExclude(String exclude) {
		this.exclude = exclude;
	}



	public String getWithheld() {
		return withheld;
	}



	public void setWithheld(String withheld) {
		this.withheld = withheld;
	}

	public String getOldScheme() {
		return oldScheme;
	}

	public void setOldScheme(String oldScheme) {
		this.oldScheme = oldScheme;
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
	
}
