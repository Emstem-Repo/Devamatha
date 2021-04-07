package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.ClassWiseTimeTableViewTo;
@SuppressWarnings("serial")
public class ClassWiseTimeTableViewForm extends BaseActionForm
{
	private String academicClass = null;
	private Integer id;
	private Integer academicYr;
	private ArrayList<String> periodList;
	private ArrayList<ClassWiseTimeTableViewTo> subTimeList;
	private Map<Integer, String> listOfClasses;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public String getAcademicClass() {
		return academicClass;
	}

	public void setAcademicClass(String academicClass) {
		this.academicClass = academicClass;
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

	public ArrayList<String> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(ArrayList<String> periodList) {
		this.periodList = periodList;
	}

	public ArrayList<ClassWiseTimeTableViewTo> getSubTimeList() {
		return subTimeList;
	}

	public void setSubTimeList(ArrayList<ClassWiseTimeTableViewTo> subTimeList) {
		this.subTimeList = subTimeList;
	}

	public Map<Integer, String> getListOfClasses() {
		return listOfClasses;
	}

	public void setListOfClasses(Map<Integer, String> listOfClasses) {
		this.listOfClasses = listOfClasses;
	}
	
	
}
