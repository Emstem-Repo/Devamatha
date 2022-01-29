package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionAllstudentsTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionSingleStudentTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamStudentMarksCorrectionForm extends BaseActionForm {

	private String id;
	private String examMasterId;
	private String examType;
	private String examName;
	private String marksEntryFor;
	private String course;
	private String scheme;
	private String subject;
	private String markType;
	private String subjectType;
	private String evaluatorType;
	private String answerScriptType;
	private String theoryMarks;
	private String practicalMarks;
//	private BigDecimal theoryMarks;
//	private BigDecimal practicalMarks;
	private int studentId;
	private String marksCardNo;
	private String regNo;
	private String rollNo;
	private String studentName;
	private int examNameId;
	private int evaluatorTypeId;
	private int answerScriptTypeId;
	private int listStudentDetailsSize;
	private String classCode;
	private List<KeyValueTO> listExamName;
	private HashMap<Integer, String> listEvaluatorType;
	private HashMap<Integer, String> listAnswerScriptType;
	private ExamStudentMarksCorrectionTO objExamMarksEntryTO;
	private ArrayList<ExamStudentMarksCorrectionAllstudentsTO> originalSingleSubjFor_AllStudents;
	private ArrayList<ExamStudentMarksCorrectionAllstudentsTO> singleSubjFor_AllStudents;
	private ArrayList<ExamStudentMarksCorrectionSingleStudentTO> singleStuFor_AllSubjects;
	private ArrayList<ExamStudentMarksCorrectionSingleStudentTO> singleStuFor_AllSubjects1;
	private ArrayList<ExamStudentMarksCorrectionSingleStudentTO> originalSingleStuFor_AllSubjects;
	private Map<Integer, String> examNameList;
	private Map<Integer, String> courseNameList;
	private Map<String, String> schemeNameList;
	private HashMap<String, String> markTypeList;
	private Map<Integer, String> subjectNameList;
	private String rollOrReg;
	private String validationAST;
	private String validationET;
	private Map<Integer, String> evaluatorMap;
	private Map<Integer, String> answerScriptTypeMap;
	private String secured = null;
	private boolean isPrevExam;
	private boolean searchDone;
	private String dupsecured;

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

	public HashMap<String, String> getMarkTypeList() {
		return markTypeList;
	}

	public void setMarkTypeList(HashMap<String, String> markTypeList) {
		this.markTypeList = markTypeList;
	}

	public Map<Integer, String> getSubjectNameList() {
		return subjectNameList;
	}

	public void setSubjectNameList(Map<Integer, String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		examType = "";
		examName = "";
		marksEntryFor = "";
		course = "";
		scheme = "";
		super.setSchemeId("");
		subject = "";
		markType = "";
		regNo = "";
		rollNo = "";
		studentName = "";
		evaluatorType = "";
		answerScriptType = "";
		secured = null;
		isPrevExam = false;
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

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public HashMap<Integer, String> getListEvaluatorType() {
		return listEvaluatorType;
	}

	public void setListEvaluatorType(HashMap<Integer, String> listEvaluatorType) {
		this.listEvaluatorType = listEvaluatorType;
	}

	public HashMap<Integer, String> getListAnswerScriptType() {
		return listAnswerScriptType;
	}

	public void setListAnswerScriptType(
			HashMap<Integer, String> listAnswerScriptType) {
		this.listAnswerScriptType = listAnswerScriptType;
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

	public int getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(int evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public int getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}

	public void setAnswerScriptTypeId(int answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}

	public int getListStudentDetailsSize() {
		return listStudentDetailsSize;
	}

	public void setListStudentDetailsSize(int listStudentDetailsSize) {
		this.listStudentDetailsSize = listStudentDetailsSize;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
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

	public String getTheoryMarks() {
		return theoryMarks;
	}

	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public String getPracticalMarks() {
		return practicalMarks;
	}

	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getMarkType() {
		return markType;
	}

	public void setObjExamMarksEntryTO(
			ExamStudentMarksCorrectionTO objExamMarksEntryTO) {
		this.objExamMarksEntryTO = objExamMarksEntryTO;
	}

	public ExamStudentMarksCorrectionTO getObjExamMarksEntryTO() {
		return objExamMarksEntryTO;
	}

	public void setSingleSubjFor_AllStudents(
			ArrayList<ExamStudentMarksCorrectionAllstudentsTO> singleSubjFor_AllStudents) {
		this.singleSubjFor_AllStudents = singleSubjFor_AllStudents;
	}

	public ArrayList<ExamStudentMarksCorrectionAllstudentsTO> getSingleSubjFor_AllStudents() {
		return singleSubjFor_AllStudents;
	}

	public void setSingleStuFor_AllSubjects(
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> singleStuFor_AllSubjects) {
		this.singleStuFor_AllSubjects = singleStuFor_AllSubjects;
	}

	public ArrayList<ExamStudentMarksCorrectionSingleStudentTO> getSingleStuFor_AllSubjects() {
		return singleStuFor_AllSubjects;
	}

	public void setOriginalSingleSubjFor_AllStudents(
			ArrayList<ExamStudentMarksCorrectionAllstudentsTO> originalSingleSubjFor_AllStudents) {
		this.originalSingleSubjFor_AllStudents = originalSingleSubjFor_AllStudents;
	}

	public ArrayList<ExamStudentMarksCorrectionAllstudentsTO> getOriginalSingleSubjFor_AllStudents() {
		return originalSingleSubjFor_AllStudents;
	}

	public void setOriginalSingleStuFor_AllSubjects(
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> originalSingleStuFor_AllSubjects) {
		this.originalSingleStuFor_AllSubjects = originalSingleStuFor_AllSubjects;
	}

	public ArrayList<ExamStudentMarksCorrectionSingleStudentTO> getOriginalSingleStuFor_AllSubjects() {
		return originalSingleStuFor_AllSubjects;
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

	public void setEvaluatorMap(Map<Integer, String> evaluatorMap) {
		this.evaluatorMap = evaluatorMap;
	}

	public Map<Integer, String> getAnswerScriptTypeMap() {
		return answerScriptTypeMap;
	}

	public void setSingleStuFor_AllSubjects1(
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> singleStuFor_AllSubjects1) {
		this.singleStuFor_AllSubjects1 = singleStuFor_AllSubjects1;
	}

	public ArrayList<ExamStudentMarksCorrectionSingleStudentTO> getSingleStuFor_AllSubjects1() {
		return singleStuFor_AllSubjects1;
	}

	public void setAnswerScriptTypeMap(Map<Integer, String> answerScriptTypeMap) {
		this.answerScriptTypeMap = answerScriptTypeMap;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public String getSecured() {
		return secured;
	}

	public boolean getIsPrevExam() {
		return isPrevExam;
	}

	public void setIsPrevExam(boolean isPrevExam) {
		this.isPrevExam = isPrevExam;
	}

	public boolean isSearchDone() {
		return searchDone;
	}

	public void setSearchDone(boolean searchDone) {
		this.searchDone = searchDone;
	}

	public String getDupsecured() {
		return this.dupsecured;
	}

	public void setDupsecured(String dupsecured) {
		this.dupsecured = dupsecured;
	}
	
}
