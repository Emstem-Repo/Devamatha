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
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.StudentMarksTO;

public class InternalMarksEntryForm extends BaseActionForm {
	
	
	private String examType;
	private String marksEntryFor;
	private String schemeNo;
	private String subjectId;
	private String subjectType;
	private String evaluatorType;
	private String answerScriptType;
	private String orderBy;
	private String section;
	private String examId;
	private Map<Integer, String> examNameList;
	private Map<Integer, String> courseMap;
	private Map<String, String> schemeMap;
	private String validationAST;
	private String validationET;
	private String displayAST;
	private String displayET;
	private Map<Integer, String> evaluatorList;
	private Map<Integer, String> answerScriptTypeList;
	private Map<Integer, String> subjectTypeMap;
	private Map<Integer, String> subjectMap;
	private Map<Integer, String> sectionMap;
	private List<StudentMarksTO> studentList;
	private String examName;
	private String subjectName;
	private String subjectCode;
	private String courseName;
	private boolean regular;
	private boolean internal;
	private List<InternalMarksEntryTO> examDeatails;
	private String className;
	private String classId;
	private List<InternalMarksEntryTO> practicalDeatails;
	private String teacherId;
	private Map<Integer, String> teachersMap;
	private boolean forTeachers;
	private Map<Integer,Map<String, Map<Integer, InternalMarksEntryTO>>> examMap = new HashMap<Integer, Map<String,Map<Integer,InternalMarksEntryTO>>>();
	private String maxMarks;
	// for HOD - View
	private Map<Integer, String> employeeMap;
	private String empId;
	private boolean hodView;
	private String empType;
	private String batchId;
	private String batchName;
	/**
	 * reseting the fields in the form
	 */
	public void resetFields() {
		this.examType="Regular";
		this.marksEntryFor=null;
		this.schemeNo=null;
		this.subjectId=null;
		this.subjectType=null;
		this.evaluatorType=null;
		this.answerScriptType=null;
		this.orderBy="register";
		this.section=null;
		this.examId=null;
		super.setCourseId(null);
		this.examName=null;
		this.subjectCode=null;
		this.subjectName=null;
		this.courseName=null;
		this.regular=false;
		this.internal=false;
		this.hodView=false;
		this.empId=null;
		this.empType=null;
	}


	public String getExamType() {
		return examType;
	}


	public void setExamType(String examType) {
		this.examType = examType;
	}


	public String getMarksEntryFor() {
		return marksEntryFor;
	}


	public void setMarksEntryFor(String marksEntryFor) {
		this.marksEntryFor = marksEntryFor;
	}


	public String getSchemeNo() {
		return schemeNo;
	}


	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
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


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}


	public String getExamId() {
		return examId;
	}


	public void setExamId(String examId) {
		this.examId = examId;
	}


	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}


	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}


	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}


	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}


	public Map<String, String> getSchemeMap() {
		return schemeMap;
	}


	public void setSchemeMap(Map<String, String> schemeMap) {
		this.schemeMap = schemeMap;
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


	public Map<Integer, String> getEvaluatorList() {
		return evaluatorList;
	}


	public void setEvaluatorList(Map<Integer, String> evaluatorList) {
		this.evaluatorList = evaluatorList;
	}


	public Map<Integer, String> getAnswerScriptTypeList() {
		return answerScriptTypeList;
	}


	public void setAnswerScriptTypeList(Map<Integer, String> answerScriptTypeList) {
		this.answerScriptTypeList = answerScriptTypeList;
	}


	public Map<Integer, String> getSubjectTypeMap() {
		return subjectTypeMap;
	}


	public void setSubjectTypeMap(Map<Integer, String> subjectTypeMap) {
		this.subjectTypeMap = subjectTypeMap;
	}


	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}


	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}


	public Map<Integer, String> getSectionMap() {
		return sectionMap;
	}


	public void setSectionMap(Map<Integer, String> sectionMap) {
		this.sectionMap = sectionMap;
	}


	public List<StudentMarksTO> getStudentList() {
		return studentList;
	}


	public void setStudentList(List<StudentMarksTO> studentList) {
		this.studentList = studentList;
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


	public boolean isRegular() {
		return regular;
	}


	public void setRegular(boolean regular) {
		this.regular = regular;
	}


	public boolean isInternal() {
		return internal;
	}


	public void setInternal(boolean internal) {
		this.internal = internal;
	}


	public List<InternalMarksEntryTO> getExamDeatails() {
		return examDeatails;
	}


	public void setExamDeatails(List<InternalMarksEntryTO> examDeatails) {
		this.examDeatails = examDeatails;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public List<InternalMarksEntryTO> getPracticalDeatails() {
		return practicalDeatails;
	}


	public void setPracticalDeatails(List<InternalMarksEntryTO> practicalDeatails) {
		this.practicalDeatails = practicalDeatails;
	}


	public String getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}


	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}


	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}


	public boolean isForTeachers() {
		return forTeachers;
	}


	public void setForTeachers(boolean forTeachers) {
		this.forTeachers = forTeachers;
	}




	public Map<Integer, String> getEmployeeMap() {
		return employeeMap;
	}


	public void setEmployeeMap(Map<Integer, String> employeeMap) {
		this.employeeMap = employeeMap;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public boolean isHodView() {
		return hodView;
	}


	public void setHodView(boolean hodView) {
		this.hodView = hodView;
	}


	public String getEmpType() {
		return empType;
	}


	public void setEmpType(String empType) {
		this.empType = empType;
	}


	public Map<Integer, Map<String, Map<Integer, InternalMarksEntryTO>>> getExamMap() {
		return examMap;
	}


	public void setExamMap(
			Map<Integer, Map<String, Map<Integer, InternalMarksEntryTO>>> examMap) {
		this.examMap = examMap;
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


	public String getMaxMarks() {
		return maxMarks;
	}


	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}


}
