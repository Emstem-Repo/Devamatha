package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Jan 13, 2010 Created By 9Elements
 */
public class ExamRevaluationApplicationForm extends BaseActionForm {
	private int id;
	private String examNameId;
	private String examNameId_value;
	private String regNo;
	private String rollNo;
	private String courseId;
	private String schemeNo;
	private String courseName;
	private String schemeName;
	private String schemeId;

	private String revaluationType;
	private String revaluationDate;
	private String revaluationAmount;
	private String studentId;
	private String studentName;
	private List<KeyValueTO> listExamName;
	private Map<Integer, String> courseMap;
	private Map<Integer, String> schemeMap;

	private List<ExamRevaluationApplicationTO> listStudentName;
	private List<ExamSubjectTO> listSubject;
	private List<KeyValueTO> listRevaluationType;
	 //added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() {
		this.courseId = "";
		this.regNo = null;
		this.rollNo = null;
		this.examNameId = "";
		revaluationAmount = "";

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
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

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
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

	public String getExamNameId_value() {
		return examNameId_value;
	}

	public void setExamNameId_value(String examNameId_value) {
		this.examNameId_value = examNameId_value;
	}

	public List<ExamRevaluationApplicationTO> getListStudentName() {
		return listStudentName;
	}

	public void setListStudentName(
			List<ExamRevaluationApplicationTO> listStudentName) {
		this.listStudentName = listStudentName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getRevaluationType() {
		return revaluationType;
	}

	public void setRevaluationType(String revaluationType) {
		this.revaluationType = revaluationType;
	}

	public String getRevaluationDate() {
		return revaluationDate;
	}

	public void setRevaluationDate(String revaluationDate) {
		this.revaluationDate = revaluationDate;
	}

	public String getRevaluationAmount() {
		return revaluationAmount;
	}

	public void setRevaluationAmount(String revaluationAmount) {
		this.revaluationAmount = revaluationAmount;
	}

	public List<KeyValueTO> getListRevaluationType() {
		return listRevaluationType;
	}

	public void setListRevaluationType(List<KeyValueTO> listRevaluationType) {
		this.listRevaluationType = listRevaluationType;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setListSubject(List<ExamSubjectTO> listSubject) {
		this.listSubject = listSubject;
	}

	public List<ExamSubjectTO> getListSubject() {
		return listSubject;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setSchemeMap(Map<Integer, String> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public Map<Integer, String> getSchemeMap() {
		return schemeMap;
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
