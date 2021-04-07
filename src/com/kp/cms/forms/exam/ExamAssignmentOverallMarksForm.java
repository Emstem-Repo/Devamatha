package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamAssignOverallMarksTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamAssignmentOverallMarksForm extends BaseActionForm {
	private int id;
	private String assignmentOverall;
	private String examNameId;
	private String examNameId_value;
	private String courseId;
	private String schemeNo;
	private String courseName;
	private String schemeName;
	private String schemeId;

	private String subject;
	private String subjectType;
	private String examName;
	private int subjectTypeId;
	private String type;
	private String studentId;
	private String displayAssignOverall;
	private List<KeyValueTO> listExamName;
	private List<ExamAssignOverallMarksTO> listOfStudent;
	private Map<Integer, String> typeMap;

	private Map<Integer, String> listCourseMap;
	private Map<String, String> mapScheme;
	private Map<Integer, String> mapSubject;
	private int subInternalTheory;
	private int subInternalPractical;
	private int attendancePractical;
	private int assignmentPractical;
	private int attendanceTheory;
	private int assignmentTheory;
	private String linkType;
	private String selectedExam;
	 //added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssignmentOverall() {
		return assignmentOverall;
	}

	public void setAssignmentOverall(String assignmentOverall) {
		this.assignmentOverall = assignmentOverall;
	}

	public String getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
	}

	public String getExamNameId_value() {
		return examNameId_value;
	}

	public void setExamNameId_value(String examNameId_value) {
		this.examNameId_value = examNameId_value;
	}

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public List<ExamAssignOverallMarksTO> getListOfStudent() {
		return listOfStudent;
	}

	public void setListOfStudent(List<ExamAssignOverallMarksTO> listOfStudent) {
		this.listOfStudent = listOfStudent;
	}

	public void setTypeMap(Map<Integer, String> typeMap) {
		this.typeMap = typeMap;
	}

	public Map<Integer, String> getTypeMap() {
		return typeMap;
	}

	public void setSubjectTypeId(int subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public int getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setMapScheme(Map<String, String> mapScheme) {
		this.mapScheme = mapScheme;
	}

	public Map<String, String> getMapScheme() {
		return mapScheme;
	}

	public void setMapSubject(Map<Integer, String> mapSubject) {
		this.mapSubject = mapSubject;
	}

	public Map<Integer, String> getMapSubject() {
		return mapSubject;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public int getSubInternalTheory() {
		return subInternalTheory;
	}

	public void setSubInternalTheory(int subInternalTheory) {
		this.subInternalTheory = subInternalTheory;
	}

	public int getSubInternalPractical() {
		return subInternalPractical;
	}

	public void setSubInternalPractical(int subInternalPractical) {
		this.subInternalPractical = subInternalPractical;
	}

	public int getAttendancePractical() {
		return attendancePractical;
	}

	public void setAttendancePractical(int attendancePractical) {
		this.attendancePractical = attendancePractical;
	}

	public int getAssignmentPractical() {
		return assignmentPractical;
	}

	public void setAssignmentPractical(int assignmentPractical) {
		this.assignmentPractical = assignmentPractical;
	}

	public int getAttendanceTheory() {
		return attendanceTheory;
	}

	public void setAttendanceTheory(int attendanceTheory) {
		this.attendanceTheory = attendanceTheory;
	}

	public int getAssignmentTheory() {
		return assignmentTheory;
	}

	public void setAssignmentTheory(int assignmentTheory) {
		this.assignmentTheory = assignmentTheory;
	}

	public void setListCourseMap(Map<Integer, String> listCourseMap) {
		this.listCourseMap = listCourseMap;
	}

	public Map<Integer, String> getListCourseMap() {
		return listCourseMap;
	}

	public void setDisplayAssignOverall(String displayAssignOverall) {
		this.displayAssignOverall = displayAssignOverall;
	}

	public String getDisplayAssignOverall() {
		return displayAssignOverall;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getSelectedExam() {
		return selectedExam;
	}

	public void setSelectedExam(String selectedExam) {
		this.selectedExam = selectedExam;
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
