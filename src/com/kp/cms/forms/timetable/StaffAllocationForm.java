package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.StaffAllocationTo;

@SuppressWarnings("serial")
public class StaffAllocationForm extends BaseActionForm {

	private int id;
	private String teachingStaff;
	private int academicYr;
	private String className;
	private String classValues;
	private String allSubjectPreference;
	private String selectedSubjectPreferences;
	private ArrayList<KeyValueTO> teachingStaffList;
	private ArrayList<StaffAllocationTo> bottomGrid;
	private Map<Integer, String> listOfClasses;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public String getTeachingStaff() {
		return teachingStaff;
	}

	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}

	public int getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(int academicYr) {
		this.academicYr = academicYr;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ArrayList<KeyValueTO> getTeachingStaffList() {
		return teachingStaffList;
	}

	public String getAllSubjectPreference() {
		return allSubjectPreference;
	}

	public void setAllSubjectPreference(String allSubjectPreference) {
		this.allSubjectPreference = allSubjectPreference;
	}

	public String getSelectedSubjectPreferences() {
		return selectedSubjectPreferences;
	}

	public void setSelectedSubjectPreferences(String selectedSubjectPreferences) {
		this.selectedSubjectPreferences = selectedSubjectPreferences;
	}

	public void setTeachingStaffList(ArrayList<KeyValueTO> teachingStaffList) {
		this.teachingStaffList = teachingStaffList;
	}

	public ArrayList<StaffAllocationTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<StaffAllocationTo> bottomGrid) {
		this.bottomGrid = bottomGrid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Integer, String> getListOfClasses() {
		return listOfClasses;
	}

	public void setListOfClasses(Map<Integer, String> listOfClasses) {
		this.listOfClasses = listOfClasses;
	}

	public void clearPage() {
		teachingStaff = null;
		className = null;
		classValues = null;
		allSubjectPreference = null;
		selectedSubjectPreferences = null;

	}

}
