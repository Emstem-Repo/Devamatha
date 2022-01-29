package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.GradeClassDefinitionTO;

public class GradeClassDefinitionEntryForm  extends BaseActionForm{

	private String[] selectedCourses;
	private String accYear;
	private String schemeNumber;
	private List<ExamCourseUtilTO> listExamCourseUtilTO;
	private Map<Integer, Integer> schemeMap;
	private List<GradeClassDefinitionTO> gradeDefinitionList;
	private List<GradeClassDefinitionTO> displayGradeDefinitionList;
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.selectedCourses = null;
		this.schemeNumber = null;
		this.accYear = null;
		this.listExamCourseUtilTO = null;
		this.schemeMap = null;
		this.gradeDefinitionList = null;
		this.displayGradeDefinitionList = null;
	}

	public String[] getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String[] selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public String getAccYear() {
		return accYear;
	}

	public void setAccYear(String accYear) {
		this.accYear = accYear;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public List<ExamCourseUtilTO> getListExamCourseUtilTO() {
		return listExamCourseUtilTO;
	}

	public void setListExamCourseUtilTO(List<ExamCourseUtilTO> listExamCourseUtilTO) {
		this.listExamCourseUtilTO = listExamCourseUtilTO;
	}

	public Map<Integer, Integer> getSchemeMap() {
		return schemeMap;
	}

	public void setSchemeMap(Map<Integer, Integer> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public List<GradeClassDefinitionTO> getGradeDefinitionList() {
		return gradeDefinitionList;
	}

	public void setGradeDefinitionList(
			List<GradeClassDefinitionTO> gradeDefinitionList) {
		this.gradeDefinitionList = gradeDefinitionList;
	}

	public List<GradeClassDefinitionTO> getDisplayGradeDefinitionList() {
		return displayGradeDefinitionList;
	}

	public void setDisplayGradeDefinitionList(
			List<GradeClassDefinitionTO> displayGradeDefinitionList) {
		this.displayGradeDefinitionList = displayGradeDefinitionList;
	}
	

}
