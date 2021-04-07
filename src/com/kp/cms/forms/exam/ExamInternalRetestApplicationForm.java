package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamInternalRetestApplicationTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;

@SuppressWarnings("serial")
public class ExamInternalRetestApplicationForm extends BaseActionForm {

	private int id;
	private String examNameId;
	private String regNo;
	private String rollNo;
	private String courseId;
	private String courseName;
	private HashMap<Integer, String> listExamName;
	private ArrayList<ExamSupplementaryImpApplicationTO> listClassDetails;
	private ExamInternalRetestApplicationTO internalRetestTo;

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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	public void setListClassDetails(
			ArrayList<ExamSupplementaryImpApplicationTO> listClassDetails) {
		this.listClassDetails = listClassDetails;
	}

	public ArrayList<ExamSupplementaryImpApplicationTO> getListClassDetails() {
		return listClassDetails;
	}

	public void setListExamName(HashMap<Integer, String> listExamName) {
		this.listExamName = listExamName;
	}

	public HashMap<Integer, String> getListExamName() {
		return listExamName;
	}

	public void setInternalRetestTo(ExamInternalRetestApplicationTO internalRetestTo) {
		this.internalRetestTo = internalRetestTo;
	}

	public ExamInternalRetestApplicationTO getInternalRetestTo() {
		return internalRetestTo;
	}

	public void clearPage() {
		regNo="";
		rollNo="";
		examNameId="";
		
	}
}
