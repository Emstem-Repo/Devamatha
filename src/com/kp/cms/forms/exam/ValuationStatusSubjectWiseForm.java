package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.ValuationStatusSubjectWiseTO;

public class ValuationStatusSubjectWiseForm extends BaseActionForm{
	private int id;
	private String examId;
	private String subjectId;
	private Map<Integer,String> examNameMap;
	private String displaySubType;
	private String isPreviousExam;
	private ArrayList<KeyValueTO> subjectList;
	private Map<String,String> subjectTypeList;
	private String subjectType;
	private String examType;
	private Map<String,List<ExamValidationDetailsTO>> valuationMap;
	private int remainingAnswerScripts ;
	private int totalAnswerScripts;
	private int totalInternalValuator;
	private int totalExternalValuator;
	private int totalReviewerValuator;
	private int totalMinorValuator;
	private int totalMajorValuator;
	private Map<Integer,String> subjectMap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
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
	
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public void resetFields(){
		this.examId=null;
		this.examType="Regular";
		this.subjectId=null;
		this.subjectType=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.subjectId = null;
		this.subjectList = null;
		this.examNameMap = null;
		this.subjectTypeList = null;
		this.valuationMap = null;
		this.totalAnswerScripts = 0;
		this.remainingAnswerScripts = 0;
		super.setSchemeNo(null);
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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
	
	public int getRemainingAnswerScripts() {
		return remainingAnswerScripts;
	}
	public void setRemainingAnswerScripts(int remainingAnswerScripts) {
		this.remainingAnswerScripts = remainingAnswerScripts;
	}
	public int getTotalAnswerScripts() {
		return totalAnswerScripts;
	}
	public void setTotalAnswerScripts(int totalAnswerScripts) {
		this.totalAnswerScripts = totalAnswerScripts;
	}
	public Map<String, List<ExamValidationDetailsTO>> getValuationMap() {
		return valuationMap;
	}
	public void setValuationMap(
			Map<String, List<ExamValidationDetailsTO>> valuationMap) {
		this.valuationMap = valuationMap;
	}
	public int getTotalInternalValuator() {
		return totalInternalValuator;
	}
	public void setTotalInternalValuator(int totalInternalValuator) {
		this.totalInternalValuator = totalInternalValuator;
	}
	public int getTotalExternalValuator() {
		return totalExternalValuator;
	}
	public void setTotalExternalValuator(int totalExternalValuator) {
		this.totalExternalValuator = totalExternalValuator;
	}
	public int getTotalReviewerValuator() {
		return totalReviewerValuator;
	}
	public void setTotalReviewerValuator(int totalReviewerValuator) {
		this.totalReviewerValuator = totalReviewerValuator;
	}
	public int getTotalMinorValuator() {
		return totalMinorValuator;
	}
	public void setTotalMinorValuator(int totalMinorValuator) {
		this.totalMinorValuator = totalMinorValuator;
	}
	public int getTotalMajorValuator() {
		return totalMajorValuator;
	}
	public void setTotalMajorValuator(int totalMajorValuator) {
		this.totalMajorValuator = totalMajorValuator;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
}
