package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;

public class FeeAssignmentCopyForm extends BaseActionForm
{
	private Integer id;
	private String courseId;
	private String academicYear;
	private String fromSemester;
	private String toSemester;
	private String method;
	private List<CourseTO>courseList;
	private String toCourseId;
	
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getFromSemester() {
		return fromSemester;
	}
	public void setFromSemester(String fromSemester) {
		this.fromSemester = fromSemester;
	}
	public String getToSemester() {
		return toSemester;
	}
	public void setToSemester(String toSemester) {
		this.toSemester = toSemester;
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
		fromSemester=null;
		toSemester=null;
		toCourseId = null;
		
	}
	public String getToCourseId() {
		return toCourseId;
	}
	public void setToCourseId(String toCourseId) {
		this.toCourseId = toCourseId;
	}
	
	
	
}
