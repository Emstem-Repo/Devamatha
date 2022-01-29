package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamStudentEligibilityEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamStudentEligibilityEntryForm extends BaseActionForm {
	private String classIdsFrom;
	private String classIdsTo;

	private String eligibilityCriteria;
	private String eligibilityId;
	private String classValues;
	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private ArrayList<KeyValueTO> listEligibilityCriteria;
	private int studentSize;
	private ArrayList<ExamStudentEligibilityEntryTO> listStudent;
	private String studentId;
	
	

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		
		this.eligibilityCriteria = "";
		this.classIdsTo="";

	}

	public String getClassIdsFrom() {
		return classIdsFrom;
	}

	public void setClassIdsFrom(String classIdsFrom) {
		this.classIdsFrom = classIdsFrom;
	}

	public String getClassIdsTo() {
		return classIdsTo;
	}

	public void setClassIdsTo(String classIdsTo) {
		this.classIdsTo = classIdsTo;
	}

	public String getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	public void setEligibilityCriteria(String eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}

	public String getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(String eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public ArrayList<KeyValueTO> getListEligibilityCriteria() {
		return listEligibilityCriteria;
	}

	public void setListEligibilityCriteria(
			ArrayList<KeyValueTO> listEligibilityCriteria) {
		this.listEligibilityCriteria = listEligibilityCriteria;
	}

	public ArrayList<ExamStudentEligibilityEntryTO> getListStudent() {
		return listStudent;
	}

	public void setListStudent(
			ArrayList<ExamStudentEligibilityEntryTO> listStudent) {
		this.listStudent = listStudent;
	}

	public Map<Integer, String> getMapClass() {
		return mapClass;
	}

	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}

	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}

	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getStudentSize() {
		return studentSize;
	}

	public void setStudentSize(int studentSize) {
		this.studentSize = studentSize;
	}

}
