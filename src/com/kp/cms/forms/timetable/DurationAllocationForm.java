package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.timetable.DurationAllocationTo;

@SuppressWarnings("serial")
public class DurationAllocationForm extends BaseActionForm {

	private int academicYr;
	private String course;
	private Map<Integer, String> courseList;
	private ArrayList<DurationAllocationTo> bottomGrid;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public int getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(int academicYr) {
		this.academicYr = academicYr;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public ArrayList<DurationAllocationTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<DurationAllocationTo> bottomGrid) {
		this.bottomGrid = bottomGrid;
	}

	public Map<Integer, String> getCourseList() {
		return courseList;
	}

	public void setCourseList(Map<Integer, String> courseList) {
		this.courseList = courseList;
	}

	public void clearPage() {
		this.academicYr = 0;
		this.course = null;

	}

}
