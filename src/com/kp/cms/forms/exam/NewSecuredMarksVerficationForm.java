package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.StudentMarksTO;

public class NewSecuredMarksVerficationForm extends BaseActionForm {
	
	private String examType;
	private String examId;
	private String subjectId;
	private String subjectType;
	private String evaluatorType;
	private String answerScriptType;
	private String displaySubType;
	private String isPreviousExam;
	private Map<Integer, String> examNameList;
	private ArrayList<KeyValueTO> subjectList;
	private Map<String, String> subjectTypeList;
	private Map<Integer, String>  evaluatorTypeMap;
	private Map<Integer, String> answerScriptTypeMap;
	private String validationAST;
	private String validationET;
	private String displayAST;
	private String displayET;
	private List<StudentMarksTO> studentList;
	private List<StudentMarksTO> mainList;
	private boolean isTheory;
	private boolean isPractical;
	private String regNo;
	private String examMark;
	private String examName;
	private String subjectName;
	private String subjectCode;
	private String courseName;
	private List<String> regList=null;
	private Map<Integer,Map<Integer,String>> evaMap;
	private String studentId;
	private String course;
	private String checkBox;
	private boolean dummyCheckBox;
	private String schemeNo;
	private Map<Integer, String>  subjectMap;
	
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
	public Map<Integer, String> getEvaluatorTypeMap() {
		return evaluatorTypeMap;
	}
	public void setEvaluatorTypeMap(Map<Integer, String> evaluatorTypeMap) {
		this.evaluatorTypeMap = evaluatorTypeMap;
	}
	public Map<Integer, String> getAnswerScriptTypeMap() {
		return answerScriptTypeMap;
	}
	public void setAnswerScriptTypeMap(Map<Integer, String> answerScriptTypeMap) {
		this.answerScriptTypeMap = answerScriptTypeMap;
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
	public List<StudentMarksTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentMarksTO> studentList) {
		this.studentList = studentList;
	}
	public List<StudentMarksTO> getMainList() {
		return mainList;
	}
	public void setMainList(List<StudentMarksTO> mainList) {
		this.mainList = mainList;
	}
	public boolean isTheory() {
		return isTheory;
	}
	public void setTheory(boolean isTheory) {
		this.isTheory = isTheory;
	}
	public boolean isPractical() {
		return isPractical;
	}
	public void setPractical(boolean isPractical) {
		this.isPractical = isPractical;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getExamMark() {
		return examMark;
	}
	public void setExamMark(String examMark) {
		this.examMark = examMark;
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
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<String> getRegList() {
		return regList;
	}
	public void setRegList(List<String> regList) {
		this.regList = regList;
	}
	public Map<Integer, Map<Integer, String>> getEvaMap() {
		return evaMap;
	}
	public void setEvaMap(Map<Integer, Map<Integer, String>> evaMap) {
		this.evaMap = evaMap;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
	public boolean isDummyCheckBox() {
		return dummyCheckBox;
	}
	public void setDummyCheckBox(boolean dummyCheckBox) {
		this.dummyCheckBox = dummyCheckBox;
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
		this.evaluatorType=null;
		this.subjectId=null;
		this.subjectType=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.isTheory=false;
		this.isPractical=false;
		this.regList=new ArrayList<String>();
		this.evaMap=null;
		this.course=null;
		this.studentId=null;
		this.checkBox="yes";
		super.setYear(null);
		this.schemeNo=null;
		super.setAcademicYear(null);
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	
}