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

public class ExamValidationDetailsForm extends BaseActionForm {
	
	private String examType;
	private String examId;
	private String subjectId;
	private String subjectType;
	private String employeeName;
	private String answerScriptType;
	private String displaySubType;
	private String isPreviousExam;
	private Map<Integer, String> examNameList;
	private ArrayList<KeyValueTO> subjectList;
	private Map<String, String> subjectTypeList;
	private Map<Integer, String>  evaluatorsMap;
	private String examName;
	private String subjectName;
	private String subjectCode;
	private String date;
	private String otherEmployee;
	private String employeeId;
	private List<ExamValidationDetailsTO> validationDetails;
	private int id;
	private String answers;
	private String isReviewer;
	private ExamValidationDetailsTO answerScripts;
	private String valuatorName;
	private String totalAnswerScripts;
	private int deleteId;
	private String valuator;
	private String otherEmpId;
	private Map<Integer, String> subjectMap;
	private String valuatorDetails;
	private String reviewerDetails;
	private String updatedScripts;
	private Boolean displayTotalScripts;
	private String pendingAnswerScript;
	private String issuedAnswerScript;
	private Boolean displayTotalScriptsForEvl2;
	private String issuedAnswerScriptForEvl2;
	private String pendingAnswerScriptForEvl2;
	
	
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
	public String getAnswerScriptType() {
		return answerScriptType;
	}
	public void setAnswerScriptType(String answerScriptType) {
		this.answerScriptType = answerScriptType;
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
	
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Map<Integer, String> getEvaluatorsMap() {
		return evaluatorsMap;
	}
	public void setEvaluatorsMap(Map<Integer, String> evaluatorsMap) {
		this.evaluatorsMap = evaluatorsMap;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOtherEmployee() {
		return otherEmployee;
	}
	public void setOtherEmployee(String otherEmployee) {
		this.otherEmployee = otherEmployee;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public List<ExamValidationDetailsTO> getValidationDetails() {
		return validationDetails;
	}
	public void setValidationDetails(List<ExamValidationDetailsTO> validationDetails) {
		this.validationDetails = validationDetails;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		this.answerScriptType=null;
		this.subjectId=null;
		this.subjectType=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.employeeId=null;
		this.validationDetails=null;
		this.otherEmployee=null;
		this.subjectName = null;
		this.answers = "10";
		this.isReviewer="Valuator1";
		this.valuator=null;
		this.valuatorDetails=null;
		this.updatedScripts=null;
		this.reviewerDetails=null;
		this.displayTotalScripts=false;
		this.pendingAnswerScript=null;
		this.issuedAnswerScript=null;
		this.displayTotalScriptsForEvl2=false;
		this.issuedAnswerScriptForEvl2=null;
		this.pendingAnswerScriptForEvl2=null;
	}
	
	public void resetSomeFields(){
		this.answerScriptType=null;
		this.subjectId=null;
		this.subjectType=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.employeeId=null;
		this.otherEmployee=null;
		this.subjectName = null;
		this.answers = "10";
		this.isReviewer="Valuator1";
		this.valuator=null;
		this.valuatorDetails=null;
		this.updatedScripts=null;
		this.reviewerDetails=null;
		this.displayTotalScripts=false;
		this.pendingAnswerScript=null;
		this.issuedAnswerScript=null;
		this.displayTotalScriptsForEvl2=false;
		this.issuedAnswerScriptForEvl2=null;
		this.pendingAnswerScriptForEvl2=null;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getIsReviewer() {
		return isReviewer;
	}
	public void setIsReviewer(String isReviewer) {
		this.isReviewer = isReviewer;
	}
	
	public ExamValidationDetailsTO getAnswerScripts() {
		return answerScripts;
	}
	public void setAnswerScripts(ExamValidationDetailsTO answerScripts) {
		this.answerScripts = answerScripts;
	}
	public String getValuatorName() {
		return valuatorName;
	}
	public void setValuatorName(String valuatorName) {
		this.valuatorName = valuatorName;
	}
	public String getTotalAnswerScripts() {
		return totalAnswerScripts;
	}
	public void setTotalAnswerScripts(String totalAnswerScripts) {
		this.totalAnswerScripts = totalAnswerScripts;
	}
	public int getDeleteId() {
		return deleteId;
	}
	public void setDeleteId(int deleteId) {
		this.deleteId = deleteId;
	}
	public String getValuator() {
		return valuator;
	}
	public void setValuator(String valuator) {
		this.valuator = valuator;
	}
	public String getOtherEmpId() {
		return otherEmpId;
	}
	public void setOtherEmpId(String otherEmpId) {
		this.otherEmpId = otherEmpId;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public String getUpdatedScripts() {
		return updatedScripts;
	}
	public void setUpdatedScripts(String updatedScripts) {
		this.updatedScripts = updatedScripts;
	}
	public String getValuatorDetails() {
		return valuatorDetails;
	}
	public void setValuatorDetails(String valuatorDetails) {
		this.valuatorDetails = valuatorDetails;
	}
	public String getReviewerDetails() {
		return reviewerDetails;
	}
	public void setReviewerDetails(String reviewerDetails) {
		this.reviewerDetails = reviewerDetails;
	}
	public Boolean getDisplayTotalScripts() {
		return displayTotalScripts;
	}
	public void setDisplayTotalScripts(Boolean displayTotalScripts) {
		this.displayTotalScripts = displayTotalScripts;
	}
	public String getPendingAnswerScript() {
		return pendingAnswerScript;
	}
	public void setPendingAnswerScript(String pendingAnswerScript) {
		this.pendingAnswerScript = pendingAnswerScript;
	}
	public String getIssuedAnswerScript() {
		return issuedAnswerScript;
	}
	public void setIssuedAnswerScript(String issuedAnswerScript) {
		this.issuedAnswerScript = issuedAnswerScript;
	}
	public Boolean getDisplayTotalScriptsForEvl2() {
		return displayTotalScriptsForEvl2;
	}
	public void setDisplayTotalScriptsForEvl2(Boolean displayTotalScriptsForEvl2) {
		this.displayTotalScriptsForEvl2 = displayTotalScriptsForEvl2;
	}
	public String getIssuedAnswerScriptForEvl2() {
		return issuedAnswerScriptForEvl2;
	}
	public void setIssuedAnswerScriptForEvl2(String issuedAnswerScriptForEvl2) {
		this.issuedAnswerScriptForEvl2 = issuedAnswerScriptForEvl2;
	}
	public String getPendingAnswerScriptForEvl2() {
		return pendingAnswerScriptForEvl2;
	}
	public void setPendingAnswerScriptForEvl2(String pendingAnswerScriptForEvl2) {
		this.pendingAnswerScriptForEvl2 = pendingAnswerScriptForEvl2;
	}
    	
}
