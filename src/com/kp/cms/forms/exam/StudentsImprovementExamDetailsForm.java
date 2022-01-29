package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;

public class StudentsImprovementExamDetailsForm extends BaseActionForm {
	
	private String examId;
	private String examType;
	private Map<Integer,String> examNameList;
	private String classCodeIdsFrom;
	
	public String getClassCodeIdsFrom() {
		return classCodeIdsFrom;
	}
	public void setClassCodeIdsFrom(String classCodeIdsFrom) {
		this.classCodeIdsFrom = classCodeIdsFrom;
	}
	private Map<Integer, String> mapClass;
	
	
	
	public Map<Integer, String> getMapClass() {
		return mapClass;
	}
	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	
	public void resetFields(){
		this.examId=null;
		this.examType="Supplementary";
		this.mapClass=null;
		this.examNameList=null;
		
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}	
}
