package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;

public class CertificateMarksCardForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private List<CourseTO> courseList;
	private String regFrom;
	private String regTo;
	List<ConsolidateMarksCardTO> studentList;
	private boolean print;
	
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public String getRegFrom() {
		return regFrom;
	}
	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}
	public String getRegTo() {
		return regTo;
	}
	public void setRegTo(String regTo) {
		this.regTo = regTo;
	}
	public List<ConsolidateMarksCardTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<ConsolidateMarksCardTO> studentList) {
		this.studentList = studentList;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		super.setCourseId(null);
		super.setYear(null);
		this.regFrom=null;
		this.regTo=null;
		this.print=false;
	}
}
