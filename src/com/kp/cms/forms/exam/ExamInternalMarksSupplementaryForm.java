package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamInternalMarksSupplementaryForm extends BaseActionForm {

	private String examNameId;
	private String schemeId;
	private String regNo;
	private String rollNo;
	private String id;
	private String subjectCode;
	private String markId;
	private String pMarks;
	private String tMarks;
	private String studentId;
	private String subjectTypeId;
	private ArrayList<KeyValueTO> listExamName;
	private Map<Integer, String> courseList;
	private Map<String, String> schemeList;

	private List<ExamInternalMarksSupplementaryTO> subjects;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {

	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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

	public void setListExamName(ArrayList<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public ArrayList<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getPMarks() {
		return pMarks;
	}

	public void setPMarks(String marks) {
		pMarks = marks;
	}

	public String getTMarks() {
		return tMarks;
	}

	public void setTMarks(String marks) {
		tMarks = marks;
	}

	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getSubjectTypeId() {
		return subjectTypeId;
	}
	public void setCourseList(Map<Integer, String> courseList) {
		this.courseList = courseList;
	}

	public Map<Integer, String> getCourseList() {
		return courseList;
	}

	public void setSchemeList(Map<String, String> schemeList) {
		this.schemeList = schemeList;
	}

	public Map<String, String> getSchemeList() {
		return schemeList;
	}

	public List<ExamInternalMarksSupplementaryTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<ExamInternalMarksSupplementaryTO> subjects) {
		this.subjects = subjects;
	}

}
