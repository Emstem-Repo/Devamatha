package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ClassWithAdmStudentTO;

public class AdmittedStudentReportForm extends BaseActionForm {

	private String academicYear;
	private String organizationName;
	private String tempYear;
	private List<ClassWithAdmStudentTO> classWithStudents;

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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTempYear() {
		return tempYear;
	}

	public void setTempYear(String tempYear) {
		this.tempYear = tempYear;
	}

	public List<ClassWithAdmStudentTO> getClassWithStudents() {
		return classWithStudents;
	}

	public void setClassWithStudents(List<ClassWithAdmStudentTO> classWithStudents) {
		this.classWithStudents = classWithStudents;
	}
	
}
