package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;

public class StudentAttendanceForExamForm extends BaseActionForm {

	
	private String examType;
	private String examId;
	private String subjectId;
	private String subjectType;
	private String displaySubType;
	private String isPreviousExam;
	private Map<Integer, String> examNameList;
	private ArrayList<KeyValueTO> subjectList;
	private Map<String, String> subjectTypeList;
	private String registerNoEntry;
	private String isAbscent;
	private List<Integer> idList;
	private Map<String, Integer> oldRegNOMap;
	private Map<Integer, String> subjectMap;
	
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public String getDisplaySubType() {
		return displaySubType;
	}
	public void setDisplaySubType(String displaySubType) {
		this.displaySubType = displaySubType;
	}
	public String getIsPreviousExam() {
		return isPreviousExam;
	}
	public void setIsPreviousExam(String isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}
	public ArrayList<KeyValueTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(ArrayList<KeyValueTO> subjectList) {
		this.subjectList = subjectList;
	}
	public Map<String, String> getSubjectTypeList() {
		return subjectTypeList;
	}
	public void setSubjectTypeList(Map<String, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}
	public String getRegisterNoEntry() {
		return registerNoEntry;
	}
	public void setRegisterNoEntry(String registerNoEntry) {
		this.registerNoEntry = registerNoEntry;
	}
	public String getIsAbscent() {
		return isAbscent;
	}
	public void setIsAbscent(String isAbscent) {
		this.isAbscent = isAbscent;
	}
	public List<Integer> getIdList() {
		return idList;
	}
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
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
	
	/**
	 * 
	 */
	public void resetFields(){
		this.examId=null;
		this.examType="Regular";
		this.subjectId=null;
		this.subjectType=null;
		this.displaySubType="sCode";
		this.isPreviousExam="false";
		this.registerNoEntry=null;
		super.setSchemeNo(null);
		this.isAbscent="yes";
		this.idList=null;
		this.oldRegNOMap=null;
	}
	public Map<String, Integer> getOldRegNOMap() {
		return oldRegNOMap;
	}
	public void setOldRegNOMap(Map<String, Integer> oldRegNOMap) {
		this.oldRegNOMap = oldRegNOMap;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
}