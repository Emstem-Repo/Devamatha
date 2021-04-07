package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSecuredMarksVerificationTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamSecuredMarksVerificationForm extends BaseActionForm {

	/**
	 * Used in Exam Module
	 */
	private String id;
	private String studentMarksVerification;
	private String secured;
	private String subject;
	private String subjectCode;
	private String subjectType;
	private String evaluatorType;
	private String answerScriptType;
	private HashMap<Integer, String> listEvaluatorType;
	private HashMap<Integer, String> listAnswerScriptType;
	private ArrayList<KeyValueTO> subjectList;
	private HashMap<String, String> subjectTypeList;
	private Map<Integer, String> examNameList;
	private String checkBox;
	private boolean dummyCheckBox;
	private String examId;
	private String subjectTypeId;
	private String evaluatorTypeId;
	private String answerScriptTypeId;
	private String boxCheck;

	private String registerNo;
	private String marks;
	private String mistake;
	private String retest;
	private String decryptRegisterNo;
	private ArrayList<ExamSecuredMarksVerificationTO> listSingleStudents;
	private String dRNO;
	private boolean regNoOrRollNumber;
	private String examType;
	private String sCodeName;
	private String validationAST;
	private String validationET;
	private String packetNo;
	
	private String studentId;
	private Map<Integer,Map<Integer,String>> evaMap;
	private String isPreviousExam;
	private Map<Integer, String> subjectmap;
	
	private String className;
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getStudentMarksVerification() {
		return studentMarksVerification;
	}

	public void setStudentMarksVerification(String studentMarksVerification) {
		this.studentMarksVerification = studentMarksVerification;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
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

	public ArrayList<KeyValueTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList<KeyValueTO> subjectList) {
		this.subjectList = subjectList;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamId() {
		return examId;
	}

	public void setSubjectTypeList(HashMap<String, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}

	public HashMap<String, String> getSubjectTypeList() {
		return subjectTypeList;
	}

	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getSubjectTypeId() {
		return subjectTypeId;
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

	public String getsCodeName() {
		return sCodeName;
	}

	public void setsCodeName(String sCodeName) {
		this.sCodeName = sCodeName;
	}

	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMarks() {
		return marks;
	}

	public void setMistake(String mistake) {
		this.mistake = mistake;
	}

	public String getMistake() {
		return mistake;
	}

	public void setRetest(String retest) {
		this.retest = retest;
	}

	public String getRetest() {
		return retest;
	}

	public void setDecryptRegisterNo(String decryptRegisterNo) {
		this.decryptRegisterNo = decryptRegisterNo;
	}

	public String getDecryptRegisterNo() {
		return decryptRegisterNo;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setBoxCheck(String boxCheck) {
		this.boxCheck = boxCheck;
	}

	public String getBoxCheck() {
		return boxCheck;
	}

	public void setListSingleStudents(
			ArrayList<ExamSecuredMarksVerificationTO> listSingleStudents) {
		this.listSingleStudents = listSingleStudents;
	}

	public ArrayList<ExamSecuredMarksVerificationTO> getListSingleStudents() {
		return listSingleStudents;
	}

	public void clearValues() {

		this.subject = "";
		this.evaluatorType = "";
		this.answerScriptType = "";
	}

	public void setDRNO(String dRNO) {
		this.dRNO = dRNO;
	}

	public String getDRNO() {
		return dRNO;
	}

	public void setRegNoOrRollNumber(boolean regNoOrRollNumber) {
		this.regNoOrRollNumber = regNoOrRollNumber;
	}

	public boolean isRegNoOrRollNumber() {
		return regNoOrRollNumber;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		//examType = "";
		subject = "";
		subjectType = "";
		evaluatorType = "";
		answerScriptType = "";
		secured = null;
		answerScriptTypeId = "";
		this.isPreviousExam="true";
		evaluatorTypeId = "";
		packetNo="";
		super.setSchemeNo(null);
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}

	public void setAnswerScriptTypeId(String answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}

	public String getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}

	public void setDummyCheckBox(boolean dummyCheckBox) {
		this.dummyCheckBox = dummyCheckBox;
	}

	public boolean isDummyCheckBox() {
		return dummyCheckBox;
	}

	public String getPacketNo() {
		return packetNo;
	}

	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}

	public Map<Integer, Map<Integer, String>> getEvaMap() {
		return evaMap;
	}

	public void setEvaMap(Map<Integer, Map<Integer, String>> evaMap) {
		this.evaMap = evaMap;
	}

	public String getIsPreviousExam() {
		return isPreviousExam;
	}

	public void setIsPreviousExam(String isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}

	public Map<Integer, String> getSubjectmap() {
		return subjectmap;
	}

	public void setSubjectmap(Map<Integer, String> subjectmap) {
		this.subjectmap = subjectmap;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	

}
