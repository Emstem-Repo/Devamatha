package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamCceFactorTO;

public class ExamCceFactorForm extends BaseActionForm {
    
	private int id;
	private String[] selectedSubject;
	private String[] selectExam;
	private String cceFactor;
	private String code;
	private String year;
	private String examType;
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	private Map<Integer,String> subjectMap;
	private List<ExamCceFactorTO> examCceFactorList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() 
	     {
	    this.id = 0;
		this.cceFactor = null;
		this.selectedSubject=null;
		this.selectExam=null;
		this.examTypeList = null;
		this.examNameMap = null;
		this.subjectMap=null;
		this.year = null;
		this.examType=null;
		this.examCceFactorList=null;
		this.code=null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String[] getSelectedSubject() {
		return selectedSubject;
	}

	public void setSelectedSubject(String[] selectedSubject) {
		this.selectedSubject = selectedSubject;
	}

	public String[] getSelectExam() {
		return selectExam;
	}

	public void setSelectExam(String[] selectExam) {
		this.selectExam = selectExam;
	}
	public String getCceFactor() {
		return cceFactor;
	}

	public void setCceFactor(String cceFactor) {
		this.cceFactor = cceFactor;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
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

	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public List<ExamCceFactorTO> getExamCceFactorList() {
		return examCceFactorList;
	}

	public void setExamCceFactorList(List<ExamCceFactorTO> examCceFactorList) {
		this.examCceFactorList = examCceFactorList;
	}


	
}
