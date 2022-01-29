package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamInternalCalculationMarksTO;

public class ExamInternalCalculationMarksForm extends BaseActionForm {
	private int id;
	private String startPercentage;
	private String endPercentage;
	private String marks;
	private String theoryPractical;
	//courseID from internal Class
	private String[] selectedCourse;
	
	private String orgStartPercentage;
	private String orgEndPercentage;
	private String orgMarks;
	private String orgTheoryPractical;
	private String orgSelectedCourse;
	
	private List<ExamInternalCalculationMarksTO> examICMList;
	private List<ExamCourseUtilTO> listExamCourseUtilTO;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void clearPage() {
		this.selectedCourse=null;
		this.startPercentage = "";
		this.endPercentage = "";
		this.marks = "";
		this.theoryPractical = "";
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public List<ExamInternalCalculationMarksTO> getExamICMList() {
		return examICMList;
	}

	public void setExamICMList(List<ExamInternalCalculationMarksTO> examICMList) {
		this.examICMList = examICMList;
	}

	public String getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}

	public String getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public List<ExamCourseUtilTO> getListExamCourseUtilTO() {
		return listExamCourseUtilTO;
	}

	public void setListExamCourseUtilTO(List<ExamCourseUtilTO> listExamCourseUtilTO) {
		this.listExamCourseUtilTO = listExamCourseUtilTO;
	}

	public String[] getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(String[] selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public String getTheoryPractical() {
		return theoryPractical;
	}

	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}

	public String getOrgStartPercentage() {
		return orgStartPercentage;
	}

	public void setOrgStartPercentage(String orgStartPercentage) {
		this.orgStartPercentage = orgStartPercentage;
	}

	public String getOrgEndPercentage() {
		return orgEndPercentage;
	}

	public void setOrgEndPercentage(String orgEndPercentage) {
		this.orgEndPercentage = orgEndPercentage;
	}

	public String getOrgMarks() {
		return orgMarks;
	}

	public void setOrgMarks(String orgMarks) {
		this.orgMarks = orgMarks;
	}

	public String getOrgTheoryPractical() {
		return orgTheoryPractical;
	}

	public void setOrgTheoryPractical(String orgTheoryPractical) {
		this.orgTheoryPractical = orgTheoryPractical;
	}

	public String getOrgSelectedCourse() {
		return orgSelectedCourse;
	}

	public void setOrgSelectedCourse(String orgSelectedCourse) {
		this.orgSelectedCourse = orgSelectedCourse;
	}


	
	
}
