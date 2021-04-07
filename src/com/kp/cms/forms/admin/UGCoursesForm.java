package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.UGCoursesTO;

public class UGCoursesForm extends BaseActionForm {
	private int ugCoursesId;
	private String ugCoursesName;	
	private String origUGCourses;
	private int duplId;
	private List<UGCoursesTO> ugCoursesList;
	
	public String getOrigUGCourses() {
		return origUGCourses;
	}
	public void setOrigUGCourses(String origUGCourses) {
		this.origUGCourses = origUGCourses;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public int getUgCoursesId() {
		return ugCoursesId;
	}
	public void setUgCoursesId(int ugCoursesId) {
		this.ugCoursesId = ugCoursesId;
	}
	public String getUgCoursesName() {
		return ugCoursesName;
	}
	public void setUgCoursesName(String ugCoursesName) {
		this.ugCoursesName = ugCoursesName;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.UGCOURSES_ENTRY);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.ugCoursesName = null;
		this.ugCoursesList = null;
	}
	public List<UGCoursesTO> getUgCoursesList() {
		return ugCoursesList;
	}
	public void setUgCoursesList(List<UGCoursesTO> ugCoursesList) {
		this.ugCoursesList = ugCoursesList;
	}
	
	
	

}
