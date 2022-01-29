package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSecuredMarksEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamSecuredMarksEntryForm extends BaseActionForm {
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

	private String checkBox;
	private String examId;
	private Integer subjectTypeId;
	private String evaluatorTypeId;
	private String answerScriptTypeId;
	private boolean boxcheck;

	private String registerNo;
	private String marks;
	private String mistake;
	private String retest;
	private String decryptRegisterNo;
	private Map<Integer, String> examNameList;
	private ArrayList<ExamSecuredMarksEntryTO> listSingleStudents;
	private ArrayList<ExamSecuredMarksEntryTO> listSingleStudentsView;
	private boolean regNoOrRollNumber;
	private String sCodeName;
	//private String examType;
	private String opView;
	private String validationAST;
	private String validationET;
	private Integer regCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
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

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public Integer getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setSubjectTypeId(Integer subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public String getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}

	public void setAnswerScriptTypeId(String answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}

	public boolean isBoxcheck() {
		return boxcheck;
	}

	public void setBoxcheck(boolean boxcheck) {
		this.boxcheck = boxcheck;
	}

	public String getRegisterNo() {
		return registerNo;
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

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMistake() {
		return mistake;
	}

	public void setMistake(String mistake) {
		this.mistake = mistake;
	}

	public String getRetest() {
		return retest;
	}

	public void setRetest(String retest) {
		this.retest = retest;
	}

	public String getDecryptRegisterNo() {
		return decryptRegisterNo;
	}

	public void setDecryptRegisterNo(String decryptRegisterNo) {
		this.decryptRegisterNo = decryptRegisterNo;
	}

	public ArrayList<ExamSecuredMarksEntryTO> getListSingleStudents() {
		return listSingleStudents;
	}

	public void setListSingleStudents(
			ArrayList<ExamSecuredMarksEntryTO> listSingleStudents) {
		this.listSingleStudents = listSingleStudents;
	}

	public boolean isRegNoOrRollNumber() {
		return regNoOrRollNumber;
	}

	public void setRegNoOrRollNumber(boolean regNoOrRollNumber) {
		this.regNoOrRollNumber = regNoOrRollNumber;
	}

	public String getOpView() {
		return opView;
	}

	public void setOpView(String opView) {
		this.opView = opView;
	}

/*	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
*/
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		//examType = "";
		subject = null;
		subjectType = null;
		evaluatorType = null;
		answerScriptType = null;
		super.setSchemeNo(null);
	}

	public void setListSingleStudentsView(
			ArrayList<ExamSecuredMarksEntryTO> listSingleStudentsView) {
		this.listSingleStudentsView = listSingleStudentsView;
	}

	public ArrayList<ExamSecuredMarksEntryTO> getListSingleStudentsView() {
		return listSingleStudentsView;
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
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

	public HashMap<String, String> getSubjectTypeList() {
		return subjectTypeList;
	}

	public void setSubjectTypeList(HashMap<String, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}

	public void setsCodeName(String sCodeName) {
		this.sCodeName = sCodeName;
	}

	public String getsCodeName() {
		return sCodeName;
	}

	public Integer getRegCount() {
		return regCount;
	}

	public void setRegCount(Integer regCount) {
		this.regCount = regCount;
	}
	
}
