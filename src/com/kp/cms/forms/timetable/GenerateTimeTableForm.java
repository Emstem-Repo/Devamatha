package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.timetable.GenerateTimeTableTo;

@SuppressWarnings("serial")
public class GenerateTimeTableForm extends BaseActionForm {

	private Integer academicYr;
	private String classes;
	private Map<Integer, String> classMap;
	private String schemeDuration;
	private ArrayList<GenerateTimeTableTo> listTo;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public Integer getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(Integer academicYr) {
		this.academicYr = academicYr;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public void setSchemeDuration(String schemeDuration) {
		this.schemeDuration = schemeDuration;
	}

	public String getSchemeDuration() {
		return schemeDuration;
	}

	public ArrayList<GenerateTimeTableTo> getListTo() {
		return listTo;
	}

	public void setListTo(ArrayList<GenerateTimeTableTo> listTo) {
		this.listTo = listTo;
	}

}
