package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.StudentMarksTO;

public class NewInternalMarksEntryForm  extends BaseActionForm{
	private int id;
	private String batchId;
	private String batchName;
	private String subjectCode;
	private String maxMarks;
	private String examId;
	private String evaluatorType;
	private String answerScriptType;
	private String forTeachers;
	private String hodView;
	List<StudentMarksTO> studentList;
	private List<InternalMarksEntryTO> theoryExamMarksDetails;
	private List<InternalMarksEntryTO> practicalExamMarksDetails;
	private Map<Integer,Map<String, Map<Integer, Map<String,InternalMarksEntryTO>>>> examMap = new HashMap<Integer, Map<String,Map<Integer,Map<String,InternalMarksEntryTO>>>>();
	private String classNames;
	private String currentDate;
	private String printConfirmation;
	private String teacherMobileNo;
	
	public String getTeacherMobileNo() {
		return teacherMobileNo;
	}
	public void setTeacherMobileNo(String teacherMobileNo) {
		this.teacherMobileNo = teacherMobileNo;
	}
	public String getPrintConfirmation() {
		return printConfirmation;
	}
	public void setPrintConfirmation(String printConfirmation) {
		this.printConfirmation = printConfirmation;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<InternalMarksEntryTO> getPracticalExamMarksDetails() {
		return practicalExamMarksDetails;
	}
	public void setPracticalExamMarksDetails(
			List<InternalMarksEntryTO> practicalExamMarksDetails) {
		this.practicalExamMarksDetails = practicalExamMarksDetails;
	}
	public List<InternalMarksEntryTO> getTheoryExamMarksDetails() {
		return theoryExamMarksDetails;
	}
	public void setTheoryExamMarksDetails(
			List<InternalMarksEntryTO> theoryExamMarksDetails) {
		this.theoryExamMarksDetails = theoryExamMarksDetails;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public List<StudentMarksTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentMarksTO> studentList) {
		this.studentList = studentList;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public void resetFields() {
		// TODO Auto-generated method stub
		
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
	public String getForTeachers() {
		return forTeachers;
	}
	public void setForTeachers(String forTeachers) {
		this.forTeachers = forTeachers;
	}
	public String getHodView() {
		return hodView;
	}
	public void setHodView(String hodView) {
		this.hodView = hodView;
	}
	public Map<Integer, Map<String, Map<Integer, Map<String, InternalMarksEntryTO>>>> getExamMap() {
		return examMap;
	}
	public void setExamMap(
			Map<Integer, Map<String, Map<Integer, Map<String, InternalMarksEntryTO>>>> examMap) {
		this.examMap = examMap;
	}
	public String getClassNames() {
		return classNames;
	}
	public void setClassNames(String classNames) {
		this.classNames = classNames;
	}
	
}
