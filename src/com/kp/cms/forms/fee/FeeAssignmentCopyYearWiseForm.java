package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;

public class FeeAssignmentCopyYearWiseForm extends BaseActionForm
{
	private Integer id;
	private String courseId;
	private String fromYear;
	private String toYear;
	private String method;
	private List<CourseTO>courseList;
	private String schemeNo; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void clear()
	{
		courseId=null;
		fromYear=null;
		toYear=null;
		schemeNo=null;
	}
	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	
	
	
}
