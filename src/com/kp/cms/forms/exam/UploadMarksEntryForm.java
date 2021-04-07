package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.UploadMarksEntryTO;

public class UploadMarksEntryForm extends BaseActionForm 
{
	private String examId;
	private String programId;
	private String subjectId;
	private String subjectType;
	private FormFile uploadedFile;
	private String examType;
	private Map<Integer, String> listExamName;
	private Map<Integer, String> programList;
	private Map<Integer, String> subjectList;
	private Map<String, String> subjectTypeList;
	private String courseId;
	private String schemeId;
	private String dummySchemeId;
	private Map<Integer, String>courseList;
	private Map<String, String>schemeList;
	private String isPrevious="No";
	private String validateMsg;
	private List<UploadMarksEntryTO> evaluator1List;
    private List<UploadMarksEntryTO> evaluator2List;
    private Map<String,Integer> classIdMap;
    private boolean regular;
	private boolean internal;
	private String validationAST;
	private String validationET;
	private String displayAST;
	private String displayET;
	private String evaluatorType;
	private String answerScriptType;
	private List<StudentMarksTO> studentList;
    
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
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
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public Map<Integer, String> getListExamName() {
		return listExamName;
	}
	public void setListExamName(Map<Integer, String> listExamName) {
		this.listExamName = listExamName;
	}
	
	public Map<Integer, String> getProgramList() {
		return programList;
	}
	public void setProgramList(Map<Integer, String> programList) {
		this.programList = programList;
	}
	public Map<Integer, String> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(Map<Integer, String> subjectList) {
		this.subjectList = subjectList;
	}
	public Map<String, String> getSubjectTypeList() {
		return subjectTypeList;
	}
	public void setSubjectTypeList(Map<String, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getDummySchemeId() {
		return dummySchemeId;
	}
	public void setDummySchemeId(String dummySchemeId) {
		this.dummySchemeId = dummySchemeId;
	}
	public Map<Integer, String> getCourseList() {
		return courseList;
	}
	public void setCourseList(Map<Integer, String> courseList) {
		this.courseList = courseList;
	}
	public Map<String, String> getSchemeList() {
		return schemeList;
	}
	public void setSchemeList(Map<String, String> schemeList) {
		this.schemeList = schemeList;
	}
	public String getIsPrevious() {
		return isPrevious;
	}
	public void setIsPrevious(String isPrevious) {
		this.isPrevious = isPrevious;
	}
	public String getValidateMsg() {
		return validateMsg;
	}
	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}
	public List<UploadMarksEntryTO> getEvaluator1List() {
		return evaluator1List;
	}
	public void setEvaluator1List(List<UploadMarksEntryTO> evaluator1List) {
		this.evaluator1List = evaluator1List;
	}
	public List<UploadMarksEntryTO> getEvaluator2List() {
		return evaluator2List;
	}
	public void setEvaluator2List(List<UploadMarksEntryTO> evaluator2List) {
		this.evaluator2List = evaluator2List;
	}
	public Map<String, Integer> getClassIdMap() {
		return classIdMap;
	}
	public void setClassIdMap(Map<String, Integer> classIdMap) {
		this.classIdMap = classIdMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields()
	{
		this.programId=null;
		this.examType="Regular";
		this.examId=null;
		this.subjectId=null;
		this.subjectType=null;
		this.uploadedFile=null;
		this.listExamName=null;
		this.programList=null;
		this.subjectList=null;
		this.subjectTypeList=null;
		this.isPrevious="No";
		this.courseId=null;
		this.schemeId=null;
		this.validateMsg=null;
		this.evaluator1List=null;
		this.evaluator2List=null;
		this.classIdMap=null;
		this.regular=false;
		this.internal=false;
	}
	
	public void resetFieldsAfterUpload()
	{
		
		this.subjectId=null;
		this.subjectType=null;
		this.uploadedFile=null;
		this.subjectList=null;
		this.subjectTypeList=null;
		this.isPrevious="No";
		this.validateMsg=null;
		
	}
	public boolean isRegular() {
		return regular;
	}
	public void setRegular(boolean regular) {
		this.regular = regular;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	public String getValidationAST() {
		return validationAST;
	}
	public void setValidationAST(String validationAST) {
		this.validationAST = validationAST;
	}
	public String getValidationET() {
		return validationET;
	}
	public void setValidationET(String validationET) {
		this.validationET = validationET;
	}
	public String getDisplayAST() {
		return displayAST;
	}
	public void setDisplayAST(String displayAST) {
		this.displayAST = displayAST;
	}
	public String getDisplayET() {
		return displayET;
	}
	public void setDisplayET(String displayET) {
		this.displayET = displayET;
	}
	public String getEvaluatorType() {
		return evaluatorType;
	}
	public void setEvaluatorType(String evaluatorType) {
		this.evaluatorType = evaluatorType;
	}
	public String getAnswerScriptType() {
		return answerScriptType;
	}
	public void setAnswerScriptType(String answerScriptType) {
		this.answerScriptType = answerScriptType;
	}
	public List<StudentMarksTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentMarksTO> studentList) {
		this.studentList = studentList;
	}
	
}
