package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamSupplementaryImpAppForm extends BaseActionForm {
	private int id;
	private String supplementaryImprovement;
	private int supplementaryImprovementId;
	private String examNameId;
	private String examNameId_value;
	private String schemeName;
	private String studentId;
	private String studentName;
	private String section;
	private String chance;
	private List<KeyValueTO> listExamName;
	private String addOrEdit;
	
	private int semisterYearNo;

	private List<ExamSupplementaryImpApplicationTO> listStudentName;
	private List<ExamSupplementaryImpApplicationSubjectTO> listSubject;

	private Map<Integer, String> courseList;
	private HashMap<Integer, String> schemeNameList;

	public void clearPage() {
		this.supplementaryImprovement = "";
		this.examNameId = "";

	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getSupplementaryImprovement() {
		return supplementaryImprovement;
	}

	public void setSupplementaryImprovement(String supplementaryImprovement) {
		this.supplementaryImprovement = supplementaryImprovement;
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

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<ExamSupplementaryImpApplicationTO> getListStudentName() {
		return listStudentName;
	}

	public void setListStudentName(
			List<ExamSupplementaryImpApplicationTO> listStudentName) {
		this.listStudentName = listStudentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getChance() {
		return chance;
	}

	public void setChance(String chance) {
		this.chance = chance;
	}

	public List<ExamSupplementaryImpApplicationSubjectTO> getListSubject() {
		return listSubject;
	}

	public void setListSubject(
			List<ExamSupplementaryImpApplicationSubjectTO> listSubject) {
		this.listSubject = listSubject;
	}

	public int getSupplementaryImprovementId() {
		return supplementaryImprovementId;
	}

	public void setSupplementaryImprovementId(int supplementaryImprovementId) {
		this.supplementaryImprovementId = supplementaryImprovementId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCourseList(Map<Integer, String> map) {
		this.courseList = map;
	}

	public Map<Integer, String> getCourseList() {
		return courseList;
	}

	public void setSchemeNameList(HashMap<Integer, String> schemeNameList) {
		this.schemeNameList = schemeNameList;
	}

	public HashMap<Integer, String> getSchemeNameList() {
		return schemeNameList;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setSemisterYearNo(int semisterYearNo) {
		this.semisterYearNo = semisterYearNo;
	}

	public int getSemisterYearNo() {
		return semisterYearNo;
	}

	public String getAddOrEdit() {
		return addOrEdit;
	}

	public void setAddOrEdit(String addOrEdit) {
		this.addOrEdit = addOrEdit;
	}
	
}
