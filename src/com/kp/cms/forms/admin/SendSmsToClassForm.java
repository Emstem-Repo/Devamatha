package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class SendSmsToClassForm extends BaseActionForm {
	private int listSize;
	private String year;
	private String classId;
	private String message;
	private Map<Integer, String> classMap;
	private String applicationNumber;
	private String registerNumber;
	private String applicationYear;
	private boolean showDetails;
	private StudentTO studentDetail;
	private boolean showStudentList;
	private List<StudentTO> studentList;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	/**
	 * to reset the form data
	 */
	public void resetFields() {
		this.year = null;
		this.classId = null;
		this.message = null;
		this.showDetails = false;
		this.showStudentList = false;
	}

	/*
	 * (non-Javadoc) to validate the form data for input createria
	 * 
	 * @see
	 * org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.
	 * action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public boolean isShowDetails() {
		return showDetails;
	}

	public void setShowDetails(boolean showDetails) {
		this.showDetails = showDetails;
	}

	public StudentTO getStudentDetail() {
		return studentDetail;
	}

	public void setStudentDetail(StudentTO studentDetail) {
		this.studentDetail = studentDetail;
	}

	public boolean isShowStudentList() {
		return showStudentList;
	}

	public void setShowStudentList(boolean showStudentList) {
		this.showStudentList = showStudentList;
	}

	public List<StudentTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
