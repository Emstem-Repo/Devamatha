package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.ManageTimeTableTo;

@SuppressWarnings("serial")
public class ManageTimeTableForm extends BaseActionForm {

	private String teachingStaff = null;
	private Integer id;
	private Integer academicYr;
	private String classes;
	private Map<Integer, String> classMap;
	private ArrayList<KeyValueTO> teachingStaffList;
	private ArrayList<ManageTimeTableTo> viewTo;
	private ArrayList<ManageTimeTableTo> bottomGrid;
	private String fromDate;
	private String toDate;

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

	public Integer getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(Integer academicYr) {
		this.academicYr = academicYr;
	}

	public ArrayList<KeyValueTO> getTeachingStaffList() {
		return teachingStaffList;
	}

	public void setTeachingStaffList(ArrayList<KeyValueTO> teachingStaffList) {
		this.teachingStaffList = teachingStaffList;
	}

	public ArrayList<ManageTimeTableTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<ManageTimeTableTo> bottomGrid) {
		this.bottomGrid = bottomGrid;
	}

	public ArrayList<ManageTimeTableTo> getViewTo() {
		return viewTo;
	}

	public void setViewTo(ArrayList<ManageTimeTableTo> viewTo) {
		this.viewTo = viewTo;
	}

	public void clearPage() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
