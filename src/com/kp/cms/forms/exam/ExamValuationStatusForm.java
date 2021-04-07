package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;

public class ExamValuationStatusForm extends BaseActionForm {
	
	private String examType;
	private String examId;
	private String answerScriptType;
	private String displaySubType;
	private String isPreviousExam;
	private Map<Integer, String> examNameList;
	private String examName;
	private List<ExamValidationDetailsTO> validationDetails;
	private List<ExamValuationStatusTO> valuationStatus;
	private String termNumber;
	private String subjectId;
	private String printPage;
	private List<StudentTO>  students;
	private String evaluatorTypeId;
	private List<CourseTO> courseList;
	private String courseId;
	private String subjectName;
	private Boolean finalYears;
	private Map<Integer, String> courceList;
	private Map<Integer,Map<String,List<StudentTO>>> misMatchStudentList;
	private Map<Integer, String> programmCourseMap;
	public Map<Integer, String> getProgrammCourseMap() {
		return programmCourseMap;
	}
	public void setProgrammCourseMap(Map<Integer, String> programmCourseMap) {
		this.programmCourseMap = programmCourseMap;
	}
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
	
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public List<ExamValidationDetailsTO> getValidationDetails() {
		return validationDetails;
	}
	public void setValidationDetails(List<ExamValidationDetailsTO> validationDetails) {
		this.validationDetails = validationDetails;
	}
	public List<ExamValuationStatusTO> getValuationStatus() {
		return valuationStatus;
	}
	public void setValuationStatus(List<ExamValuationStatusTO> valuationStatus) {
		this.valuationStatus = valuationStatus;
	}
	public String getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(String termNumber) {
		this.termNumber = termNumber;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public List<StudentTO> getStudents() {
		return students;
	}
	public void setStudents(List<StudentTO> students) {
		this.students = students;
	}
	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}
	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Boolean getFinalYears() {
		return finalYears;
	}
	public void setFinalYears(Boolean finalYears) {
		this.finalYears = finalYears;
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
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.validationDetails=null;
		this.termNumber=null;
		this.printPage=null;
		this.courseId = null;
		this.finalYears=false;
	}
	
	public void resetFieldsForSaveDetails(){
		//this.examId=null;
		//this.examType="Regular";
		this.answerScriptType=null;
		this.subjectId=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.validationDetails=null;
		//this.termNumber=null;
		this.printPage=null;
		this.courseId = null;
		this.finalYears=false;
	}
	public Map<Integer, String> getCourceList() {
		return courceList;
	}
	public void setCourceList(Map<Integer, String> courceList) {
		this.courceList = courceList;
	}
	public Map<Integer, Map<String, List<StudentTO>>> getMisMatchStudentList() {
		return misMatchStudentList;
	}
	public void setMisMatchStudentList(
			Map<Integer, Map<String, List<StudentTO>>> misMatchStudentList) {
		this.misMatchStudentList = misMatchStudentList;
	}
	
}
