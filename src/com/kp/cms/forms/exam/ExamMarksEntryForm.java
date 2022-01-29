package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.exam.ExamMarksEntryStudentTO;
import com.kp.cms.to.exam.ExamMarksEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamMarksEntryForm extends BaseActionForm {

	/**
	 * Used in Exam Module
	 */
	private String id;
	private String examMasterId;
	private String examType;
	private String examName;
	private String marksEntryFor;
	private String course;
	private String scheme;
	private String subject;
	private String subjectType;
	private String evaluatorType;
	private String answerScriptType;
	private String displayOrder;
	
	private int studentId;
	private String marksCardNo;
	private String regNo;
	private String rollNo;
	private String studentName;
	private int examNameId;
	private int subjectTypeId;
	private int evaluatorTypeId;
	private Integer answerScriptTypeId;
	private int listStudentDetailsSize;
	private String classCode;
	private List<KeyValueTO> listExamName;

	private ExamMarksEntryTO objExamMarksEntryTO;
	private ArrayList<ExamMarksEntryStudentTO> examMarksEntryStudentTO;
	private ArrayList<ExamMarksEntryDetailsTO> examMarksEntryDetailsTO;
	private String rollOrReg;
	
	private Map<Integer, String> examNameList;
	private Map<Integer, String> courseNameList;
	private Map<String, String> schemeNameList;
	private Map<Integer, String> subjectNameList;
	private String validationAST;
	private String validationET;
	
	private String marksCardDate;
	
	private Map<Integer,String> evaluatorMap;
	private Map<Integer,String> answerScriptTypeMap;
	private String sectionId;
	private String showExamType;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
	evaluatorType="";
		answerScriptType="";
		subject="";
		subjectType="";
		examType="";
		regNo="";
		rollNo="";
		rollOrReg="";
		marksEntryFor="";
		marksCardDate="";
		sectionId = "";
		
	}
	public void clearPageOnError(ActionMapping mapping, HttpServletRequest request) {
		evaluatorType="";
		answerScriptType="";
		subject="";
		subjectType="";
		examType="";
		marksEntryFor="";
		marksCardDate="";
		sectionId = "";
	}
	public void clearOnSearch(ActionMapping mapping,HttpServletRequest request){
		this.examNameList=null;
		this.courseNameList=null;
		this.schemeNameList=null;
		this.subjectNameList=null;
		
		
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getMarksEntryFor() {
		return marksEntryFor;
	}

	public void setMarksEntryFor(String marksEntryFor) {
		this.marksEntryFor = marksEntryFor;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	
	public String getMarksCardNo() {
		return marksCardNo;
	}

	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public int getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(int examNameId) {
		this.examNameId = examNameId;
	}

	
	public int getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setSubjectTypeId(int subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public int getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(int evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public Integer getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}

	public void setAnswerScriptTypeId(Integer answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}

	public int getListStudentDetailsSize() {
		return listStudentDetailsSize;
	}

	public void setListStudentDetailsSize(int listStudentDetailsSize) {
		this.listStudentDetailsSize = listStudentDetailsSize;
	}

	public void setObjExamMarksEntryTO(ExamMarksEntryTO objExamMarksEntryTO) {
		this.objExamMarksEntryTO = objExamMarksEntryTO;
	}

	public ExamMarksEntryTO getObjExamMarksEntryTO() {
		return objExamMarksEntryTO;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setExamMarksEntryStudentTO(
			ArrayList<ExamMarksEntryStudentTO> examMarksEntryStudentTO) {
		this.examMarksEntryStudentTO = examMarksEntryStudentTO;
	}

	public ArrayList<ExamMarksEntryStudentTO> getExamMarksEntryStudentTO() {
		return examMarksEntryStudentTO;
	}

	public void setExamMarksEntryDetailsTO(
			ArrayList<ExamMarksEntryDetailsTO> examMarksEntryDetailsTO) {
		this.examMarksEntryDetailsTO = examMarksEntryDetailsTO;
	}

	public ArrayList<ExamMarksEntryDetailsTO> getExamMarksEntryDetailsTO() {
		return examMarksEntryDetailsTO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamMasterId() {
		return examMasterId;
	}

	public void setExamMasterId(String examMasterId) {
		this.examMasterId = examMasterId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setRollOrReg(String rollOrReg) {
		this.rollOrReg = rollOrReg;
	}

	public String getRollOrReg() {
		return rollOrReg;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public Map<Integer, String> getCourseNameList() {
		return courseNameList;
	}

	public void setCourseNameList(Map<Integer, String> courseNameList) {
		this.courseNameList = courseNameList;
	}

	public Map<String, String> getSchemeNameList() {
		return schemeNameList;
	}

	public void setSchemeNameList(Map<String, String> schemeNameList) {
		this.schemeNameList = schemeNameList;
	}



	

	public void setSubjectNameList(Map<Integer, String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}

	public Map<Integer, String> getSubjectNameList() {
		return subjectNameList;
	}

	public void setMarksCardDate(String marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public String getMarksCardDate() {
		return marksCardDate;
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

	public Map<Integer, String> getEvaluatorMap() {
		return evaluatorMap;
	}

	public void setEvaluatorMap(HashMap<Integer, String> evaluatorMap) {
		this.evaluatorMap = evaluatorMap;
	}

	public Map<Integer, String> getAnswerScriptTypeMap() {
		return answerScriptTypeMap;
	}

	public void setAnswerScriptTypeMap(Map<Integer, String> answerScriptTypeMap) {
		this.answerScriptTypeMap = answerScriptTypeMap;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getShowExamType() {
		return showExamType;
	}

	public void setShowExamType(String showExamType) {
		this.showExamType = showExamType;
	}

	
	

}
