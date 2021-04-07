package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamOptAssSubTypeTO;

public class ExamOptionalSubjectAssignmentToStudentForm extends BaseActionForm {
	private int listSize;
	private String academicYear;
	private String year;
	private String[] selectedClasses;
	private String checkedName;
//	private String[] classIds;
	private ArrayList<ExamOptAssSubTypeTO> list;
	private HashMap<Integer, String> classSelected;
	
	public String getCheckedName() {
		return checkedName;
	}

	public void setCheckedName(String checkedName) {
		this.checkedName = checkedName;
	}

	

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setList(ArrayList<ExamOptAssSubTypeTO> list) {
		this.list = list;
	}

	public ArrayList<ExamOptAssSubTypeTO> getList() {
		return list;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
//
//	public void setClassIds(String[] classIds) {
//		this.classIds = classIds;
//	}
//
//	public String[] getClassIds() {
//		return classIds;
//	}

	public void setSelectedClasses(String[] selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public String[] getSelectedClasses() {
		return selectedClasses;
	}

	public void setClassSelected(HashMap<Integer, String> classSelected) {
		this.classSelected = classSelected;
	}

	public HashMap<Integer, String> getClassSelected() {
		return classSelected;
	}

	

}