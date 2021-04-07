package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamAttendanceMarksTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;

public class ExamAttendanceMarksForm extends BaseActionForm {
	private int id=0;
	private String fromPercentage="";
	private String toPercentage="";
	private String marks="";
	private String theoryPractical="";
	private String selectedCourse="";
	
	
	private String orgFromPercentage="";
	private String orgToPercentage="";
	private String orgMarks="";
	private String orgTheoryPractical="";
	private String orgSelectedCourse="";
	
	
	
	
	
	
	private List<Integer> listCourses;

	// course id get from base action
	private List<ExamAttendanceMarksTO> examAttList;

	private List<ExamCourseUtilTO> listExamCourseUtilTO;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.selectedCourse="";
		this.fromPercentage = "";
		this.toPercentage = "";
		this.marks = "";
		this.theoryPractical = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public List<ExamAttendanceMarksTO> getExamAttList() {
		return examAttList;
	}

	public void setExamAttList(List<ExamAttendanceMarksTO> examAttList) {
		this.examAttList = examAttList;
	}

	public String getFromPercentage() {
		return fromPercentage;
	}

	public void setFromPercentage(String fromPercentage) {
		this.fromPercentage = fromPercentage;
	}

	public String getToPercentage() {
		return toPercentage;
	}

	public void setToPercentage(String toPercentage) {
		this.toPercentage = toPercentage;
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

	public void setListExamCourseUtilTO(
			List<ExamCourseUtilTO> listExamCourseUtilTO) {
		this.listExamCourseUtilTO = listExamCourseUtilTO;
	}

	public String getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(String selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public List<Integer> getListCourses() {
		return listCourses;
	}

	public void setListCourses(List<Integer> listCourses) {
		this.listCourses = listCourses;
	}

	public String getTheoryPractical() {
		return theoryPractical;
	}

	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}

	public String getOrgFromPercentage() {
		return orgFromPercentage;
	}

	public void setOrgFromPercentage(String orgFromPercentage) {
		this.orgFromPercentage = orgFromPercentage;
	}

	public String getOrgToPercentage() {
		return orgToPercentage;
	}

	public void setOrgToPercentage(String orgToPercentage) {
		this.orgToPercentage = orgToPercentage;
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
