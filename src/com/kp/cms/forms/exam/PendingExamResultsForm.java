package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamCceFactorTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.PendingExamResultsTO;

public class PendingExamResultsForm extends BaseActionForm {
    
	private int id;
	private String[] selectExam;
	private String examTypeName;
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	private List<PendingExamResultsTO> examPendingResultList;
	private String mode;
	private List<StudentTO>  students;
	private String examName;
	private String examId;
	private String classId;
	//added by chandra
	private Boolean finalYears;
	private String examType;
	private Map<Integer,Map<String,List<StudentTO>>> misMatchStudentList;
	private List<ExamValuationStatusTO> valuationStatus;
	private String termNumber;
	private String evaluatorTypeId;
	private String subjectId;
	private String subjectName;
	private String courseId;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() 
	     {
		super.clear();
	    this.id = 0;
		this.examTypeList = null;
		this.examNameMap = null;
		this.selectExam=null;
		this.examTypeName=null;
		this.examPendingResultList=null;
		this.examId=null;
		this.classId=null;
	    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<PendingExamResultsTO> getExamPendingResultList() {
		return examPendingResultList;
	}

	public void setExamPendingResultList(List<PendingExamResultsTO> examPendingResultList) {
		this.examPendingResultList = examPendingResultList;
	}

	public String[] getSelectExam() {
		return selectExam;
	}

	public void setSelectExam(String[] selectExam) {
		this.selectExam = selectExam;
	}

	public String getExamTypeName() {
		return examTypeName;
	}

	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<StudentTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentTO> students) {
		this.students = students;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Boolean getFinalYears() {
		return finalYears;
	}

	public void setFinalYears(Boolean finalYears) {
		this.finalYears = finalYears;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Map<Integer, Map<String, List<StudentTO>>> getMisMatchStudentList() {
		return misMatchStudentList;
	}

	public void setMisMatchStudentList(
			Map<Integer, Map<String, List<StudentTO>>> misMatchStudentList) {
		this.misMatchStudentList = misMatchStudentList;
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

	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
		
}
