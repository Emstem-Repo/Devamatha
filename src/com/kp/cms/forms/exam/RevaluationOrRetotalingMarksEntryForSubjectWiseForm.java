package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTo;
import com.kp.cms.to.exam.StudentMarksTO;

public class RevaluationOrRetotalingMarksEntryForSubjectWiseForm extends BaseActionForm{
	private String examType;
	private String examId;
	private String subjectId;
	private String subjectType;
	private String evaluatorType;
	private String displaySubType;
	private Map<Integer, String> examNameList;
	private ArrayList<KeyValueTO> subjectList;
	private Map<Integer, String>  evaluatorTypeMap;
	private String displayET;
	private String validationET;
	private List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentList;
	private List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> mainList;
	private String examName;
	private String subjectName;
	private String subjectCode;
	private int regCount;
	private String examMark;
	private String studentId;
	private String course;
	private Map<Integer, Double> maxMarksMap;
	private String thirdparyEval;
	private String revaluation;
	
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
	public String getDisplaySubType() {
		return displaySubType;
	}
	public void setDisplaySubType(String displaySubType) {
		this.displaySubType = displaySubType;
	}
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public ArrayList<KeyValueTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(ArrayList<KeyValueTO> subjectList) {
		this.subjectList = subjectList;
	}
	public Map<Integer, String> getEvaluatorTypeMap() {
		return evaluatorTypeMap;
	}
	public void setEvaluatorTypeMap(Map<Integer, String> evaluatorTypeMap) {
		this.evaluatorTypeMap = evaluatorTypeMap;
	}
	public String getDisplayET() {
		return displayET;
	}
	public void setDisplayET(String displayET) {
		this.displayET = displayET;
	}
	public void resetFields(){
		this.examId=null;
		this.examType="Regular";
		this.evaluatorType=null;
		this.subjectId=null;
		this.subjectType="T";
		this.displaySubType="sCode";
		super.setYear(null);
		super.setAcademicYear(null);
		super.setSchemeNo(null);
		this.revaluation="Revaluation";
	}
	public String getValidationET() {
		return validationET;
	}
	public void setValidationET(String validationET) {
		this.validationET = validationET;
	}
	public List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(
			List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentList) {
		this.studentList = studentList;
	}
	public List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> getMainList() {
		return mainList;
	}
	public void setMainList(
			List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> mainList) {
		this.mainList = mainList;
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
	public int getRegCount() {
		return regCount;
	}
	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}
	public String getExamMark() {
		return examMark;
	}
	public void setExamMark(String examMark) {
		this.examMark = examMark;
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
	public Map<Integer, Double> getMaxMarksMap() {
		return maxMarksMap;
	}
	public void setMaxMarksMap(Map<Integer, Double> maxMarksMap) {
		this.maxMarksMap = maxMarksMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getThirdparyEval() {
		return thirdparyEval;
	}
	public void setThirdparyEval(String thirdparyEval) {
		this.thirdparyEval = thirdparyEval;
	}
	public String getRevaluation() {
		return revaluation;
	}
	public void setRevaluation(String revaluation) {
		this.revaluation = revaluation;
	}
}
