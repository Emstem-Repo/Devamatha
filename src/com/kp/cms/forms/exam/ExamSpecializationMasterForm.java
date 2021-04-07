package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationMasterForm extends BaseActionForm {
	private int id;
	private String specializationName;
	private String courseId;

	private String orgName = "";
	private String orgCourseName = "";
	private String orgCourseId = "";

	private List<KeyValueTO> courseList;

	private List<ExamSpecializationTO> listOfSpecialization;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.specializationName = "";
		this.courseId = "";
	}

	public String getSpecializationName() {
		return specializationName;
	}

	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<KeyValueTO> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<KeyValueTO> courseList) {
		this.courseList = courseList;
	}

	public List<ExamSpecializationTO> getListOfSpecialization() {
		return listOfSpecialization;
	}

	public void setListOfSpecialization(
			List<ExamSpecializationTO> listOfSpecialization) {
		this.listOfSpecialization = listOfSpecialization;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCourseName() {
		return orgCourseName;
	}

	public void setOrgCourseName(String orgCourseId) {
		this.orgCourseName = orgCourseId;
	}

	public String getOrgCourseId() {
		return orgCourseId;
	}

	public void setOrgCourseId(String orgCourseId) {
		this.orgCourseId = orgCourseId;
	}

}
