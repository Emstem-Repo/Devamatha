package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamExamEligibilitySetUpTO;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Jan 7, 2010 Created By 9Elements
 */
public class ExamExamEligibilitySetUpForm extends BaseActionForm {

	private String courseIdsFrom = "";
	private String courseIdsTo = "";
	private String examtypeId = "";
	private String checkEligibility = "";
	private String additionalEligibility = "";
	private String classValues;
	private String noEligibilityCheck = "";
	private String examFees = "";
	private String attendance = "";
	private String courseFees = "";
	private String eligibilitySetUpId;
	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private List<KeyValueTO> listExamtype;
	private List<ExamExamEligibilityTO> listcheckEligibility;
	private List<ExamExamEligibilityTO> listAdditionalEligibility;
	private List<ExamExamEligibilitySetUpTO> listExamEligibilitySetUp;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {

		this.noEligibilityCheck = "off";
		this.examFees = "off";
		this.attendance = "off";
		this.courseFees = "off";
		this.examtypeId = "";
	}

	public String getExamtypeId() {
		return examtypeId;
	}

	public void setExamtypeId(String examtypeId) {
		this.examtypeId = examtypeId;
	}

	public String getCheckEligibility() {
		return checkEligibility;
	}

	public void setCheckEligibility(String checkEligibility) {
		this.checkEligibility = checkEligibility;
	}

	public String getAdditionalEligibility() {
		return additionalEligibility;
	}

	public void setAdditionalEligibility(String additionalEligibility) {
		this.additionalEligibility = additionalEligibility;
	}

	public List<KeyValueTO> getListExamtype() {
		return listExamtype;
	}

	public void setListExamtype(List<KeyValueTO> listExamtype) {
		this.listExamtype = listExamtype;
	}

	public List<ExamExamEligibilityTO> getListcheckEligibility() {
		return listcheckEligibility;
	}

	public void setListcheckEligibility(
			List<ExamExamEligibilityTO> listcheckEligibility) {
		this.listcheckEligibility = listcheckEligibility;
	}

	public List<ExamExamEligibilitySetUpTO> getListExamEligibilitySetUp() {
		return listExamEligibilitySetUp;
	}

	public void setListExamEligibilitySetUp(
			List<ExamExamEligibilitySetUpTO> listExamEligibilitySetUp) {
		this.listExamEligibilitySetUp = listExamEligibilitySetUp;
	}

	public String getCourseIdsFrom() {
		return courseIdsFrom;
	}

	public void setCourseIdsFrom(String courseIdsFrom) {
		this.courseIdsFrom = courseIdsFrom;
	}

	public String getCourseIdsTo() {
		return courseIdsTo;
	}

	public void setCourseIdsTo(String courseIdsTo) {
		this.courseIdsTo = courseIdsTo;
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

	public List<ExamExamEligibilityTO> getListAdditionalEligibility() {
		return listAdditionalEligibility;
	}

	public void setListAdditionalEligibility(
			List<ExamExamEligibilityTO> listAdditionalEligibility) {
		this.listAdditionalEligibility = listAdditionalEligibility;
	}

	public String getNoEligibilityCheck() {
		return noEligibilityCheck;
	}

	public void setNoEligibilityCheck(String noEligibilityCheck) {
		this.noEligibilityCheck = noEligibilityCheck;
	}

	public String getExamFees() {
		return examFees;
	}

	public void setExamFees(String examFees) {
		this.examFees = examFees;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getCourseFees() {
		return courseFees;
	}

	public void setCourseFees(String courseFees) {
		this.courseFees = courseFees;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public String getEligibilitySetUpId() {
		return eligibilitySetUpId;
	}

	public void setEligibilitySetUpId(String eligibilitySetUpId) {
		this.eligibilitySetUpId = eligibilitySetUpId;
	}



}
