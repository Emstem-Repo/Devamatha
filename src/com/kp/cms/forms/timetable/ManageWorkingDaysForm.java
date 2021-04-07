package com.kp.cms.forms.timetable;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.timetable.ManageHolidayListTo;

@SuppressWarnings("serial")
public class ManageWorkingDaysForm extends BaseActionForm {

	private int academicYr;
	private int id;
	private String holidayName;
	private String startDate;
	private String endDate;
	private ArrayList<ManageHolidayListTo> bottomGrid;
	private ManageHolidayListTo metaTo;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setAcademicYr(int academicYr) {
		this.academicYr = academicYr;
	}

	public int getAcademicYr() {
		return academicYr;
	}

	public ArrayList<ManageHolidayListTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<ManageHolidayListTo> bottomGrid) {
		this.bottomGrid = bottomGrid;
	}

	public ManageHolidayListTo getMetaTo() {
		return metaTo;
	}

	public void setMetaTo(ManageHolidayListTo metaTo) {
		this.metaTo = metaTo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void clearPage() {
		this.holidayName = null;
		this.startDate = null;
		this.endDate = null;

	}

}
