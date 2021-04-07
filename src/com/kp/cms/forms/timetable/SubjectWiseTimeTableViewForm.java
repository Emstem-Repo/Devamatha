package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.timetable.SubjectWiseTimeTableViewTo;
@SuppressWarnings("serial")
public class SubjectWiseTimeTableViewForm extends BaseActionForm
{
	private String subject = null;
	private Integer id;
	private Integer academicYr;
	private String course;
	private ArrayList<String> periodList;
	private ArrayList<SubjectWiseTimeTableViewTo> subTimeList;
	private List<CourseTO> courseList;
	private String termNo;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
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

	public ArrayList<SubjectWiseTimeTableViewTo> getSubTimeList() {
		return subTimeList;
	}

	public void setSubTimeList(ArrayList<SubjectWiseTimeTableViewTo> subTimeList) {
		this.subTimeList = subTimeList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public List<CourseTO> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	
	
}
