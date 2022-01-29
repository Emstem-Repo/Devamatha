package com.kp.cms.forms.timetable;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.StaffWiseTimeTableViewTo;

@SuppressWarnings("serial")
public class StaffWiseTimeTableViewForm extends BaseActionForm {

	private String teachingStaff = null;
	private Integer id;
	private Integer academicYr;
	private ArrayList<KeyValueTO> teachingStaffList;
	private ArrayList<String> periodList;
	private ArrayList<StaffWiseTimeTableViewTo> subTimeList;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public ArrayList<String> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(ArrayList<String> periodList) {
		this.periodList = periodList;
	}

	public ArrayList<StaffWiseTimeTableViewTo> getSubTimeList() {
		return subTimeList;
	}

	public void setSubTimeList(ArrayList<StaffWiseTimeTableViewTo> subTimeList) {
		this.subTimeList = subTimeList;
	}
}
