package com.kp.cms.forms.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRevaluationDetailsTO;
import com.kp.cms.to.exam.ExamRevaluationStudentTO;
import com.kp.cms.to.exam.ExamRevaluationEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamRevaluationForm extends BaseActionForm {

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
	private BigDecimal theoryMarks;
	private BigDecimal practicalMarks;
	private int studentId;
	private String marksCardNo;
	private String marksCardDate;
	private String regNo;
	private String rollNo;
	private String studentName;
	private int examNameId;
	private int subjectTypeId;
	private int evaluatorTypeId;
	private int answerScriptTypeId;
	private int listStudentDetailsSize;
	private String revaluationType;
	private String classCode;
	private List<KeyValueTO> listExamName;
	private List<KeyValueTO> listevaluatorType;
	private List<KeyValueTO> listanswerScriptType;
	private List<KeyValueTO> listRevaluationType;
	private Map<Integer, String> courseNameList;
	private Map<String, String> schemeNameList;
	private Map<Integer, String> subjectNameList;
	private ExamRevaluationEntryTO objExamRevaluationEntryTO;
	private ArrayList<ExamRevaluationStudentTO> examRevaluationStudentTO;
	private ArrayList<ExamRevaluationDetailsTO> examRevaluationDetailsTO;
	private String rollOrReg;
	 //added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;

	public List<KeyValueTO> getListanswerScriptType() {
		return listanswerScriptType;
	}

	public void setListanswerScriptType(List<KeyValueTO> listanswerScriptType) {
		this.listanswerScriptType = listanswerScriptType;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		evaluatorType=null;
		answerScriptType=null;
		subject=null;
		subjectType=null;
		examType=null;
		regNo=null;
		rollNo=null;
		rollOrReg=null;
		marksEntryFor=null;
		revaluationType=null;
		examName=null;
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

	public void setListevaluatorType(List<KeyValueTO> listevaluatorType) {
		this.listevaluatorType = listevaluatorType;
	}

	public List<KeyValueTO> getListevaluatorType() {
		return listevaluatorType;
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

	public void setObjExamRevaluationEntryTO(ExamRevaluationEntryTO objExamRevaluationEntryTO) {
		this.objExamRevaluationEntryTO = objExamRevaluationEntryTO;
	}

	public ExamRevaluationEntryTO getObjExamRevaluationEntryTO() {
		return objExamRevaluationEntryTO;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setExamRevaluationStudentTO(
			ArrayList<ExamRevaluationStudentTO> ExamRevaluationStudentTO) {
		this.examRevaluationStudentTO = ExamRevaluationStudentTO;
	}

	public ArrayList<ExamRevaluationStudentTO> getExamRevaluationStudentTO() {
		return examRevaluationStudentTO;
	}

	public void setExamRevaluationDetailsTO(
			ArrayList<ExamRevaluationDetailsTO> ExamRevaluationDetailsTO) {
		this.examRevaluationDetailsTO = ExamRevaluationDetailsTO;
	}

	public ArrayList<ExamRevaluationDetailsTO> getExamRevaluationDetailsTO() {
		return examRevaluationDetailsTO;
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

	public BigDecimal getTheoryMarks() {
		return theoryMarks;
	}

	public void setTheoryMarks(BigDecimal theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public BigDecimal getPracticalMarks() {
		return practicalMarks;
	}

	public void setPracticalMarks(BigDecimal practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public void setMarksCardDate(String marksCardDate) {
		this.marksCardDate = marksCardDate;
	}

	public String getMarksCardDate() {
		return marksCardDate;
	}

	public void setRevaluationType(String revaluationType) {
		this.revaluationType = revaluationType;
	}

	public String getRevaluationType() {
		return revaluationType;
	}

	public void setCourseNameList(Map<Integer, String> courseNameList) {
		this.courseNameList = courseNameList;
	}

	public Map<Integer, String> getCourseNameList() {
		return courseNameList;
	}

	public void setSchemeNameList(Map<String, String> schemeNameList) {
		this.schemeNameList = schemeNameList;
	}

	public Map<String, String> getSchemeNameList() {
		return schemeNameList;
	}


	public void setSubjectNameList(Map<Integer, String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}

	public Map<Integer, String> getSubjectNameList() {
		return subjectNameList;
	}

	public void setListRevaluationType(List<KeyValueTO> listRevaluationType) {
		this.listRevaluationType = listRevaluationType;
	}

	public List<KeyValueTO> getListRevaluationType() {
		return listRevaluationType;
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

}
