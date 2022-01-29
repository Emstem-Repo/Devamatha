package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CategoryWiseStudentTO;
import com.kp.cms.to.admin.CategoryWithStudentsTO;

public class CategoryWiseStudentForm extends BaseActionForm{
	private String organizationName;
	private String academicYear;
	
	private List<CategoryWithStudentsTO> studentList;
	private List<CategoryWiseStudentTO> totalStudentList;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public String getOrganizationName() {
		return organizationName;
	}


	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getAcademicYear() {
		return academicYear;
	}


	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}


	public List<CategoryWithStudentsTO> getStudentList() {
		return studentList;
	}


	public void setStudentList(List<CategoryWithStudentsTO> studentList) {
		this.studentList = studentList;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public List<CategoryWiseStudentTO> getTotalStudentList() {
		return totalStudentList;
	}


	public void setTotalStudentList(List<CategoryWiseStudentTO> totalStudentList) {
		this.totalStudentList = totalStudentList;
	}


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
