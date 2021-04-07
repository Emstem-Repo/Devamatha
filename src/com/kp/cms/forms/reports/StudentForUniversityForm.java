package com.kp.cms.forms.reports;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.CourseWithStudentUniversityTO;
import com.kp.cms.to.reports.StudentForUniversityTO;

public class StudentForUniversityForm extends BaseActionForm {
	private List<ProgramTypeTO> programTypeList;
	List<StudentForUniversityTO> totalList;
	List<CourseWithStudentUniversityTO> studentList;
	private String nextYear;
	private String programTypeName;
	private String method;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public String getNextYear() {
		return nextYear;
	}

	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}

	public List<CourseWithStudentUniversityTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<CourseWithStudentUniversityTO> studentList) {
		this.studentList = studentList;
	}

	public List<StudentForUniversityTO> getTotalList() {
		return totalList;
	}

	public void setTotalList(List<StudentForUniversityTO> totalList) {
		this.totalList = totalList;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setSemister(null);
	}
	
}
