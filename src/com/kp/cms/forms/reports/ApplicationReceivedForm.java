package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;

public class ApplicationReceivedForm extends BaseActionForm {
	
	private String method;
	private List<CourseTO> courseList;
	private String applicationType;
	private String receivedApplication;
	
	public String getReceivedApplication() {
		return receivedApplication;
	}
	public void setReceivedApplication(String receivedApplication) {
		this.receivedApplication = receivedApplication;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getMethod() {
		return method;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear(){
		this.setCourseId(null);
		this.setYear(null);
		this.applicationType = null;
	}
}
