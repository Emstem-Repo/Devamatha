package com.kp.cms.forms.admission;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.CurriculumSchemeTO;

public class CourseChangeForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationYear;
	private String applicationNumber;
	private String fromCourse;
	private String toCourse;
	private String appliedCourse;
	//Code added by Mary
	
	private List<ProgramTypeTO> programTypeList;
	private List<SubjectGroupTO> subjectGroupList;
	private List<CourseSchemeTO> courseSchemeList;
	private CurriculumSchemeTO curriculumSchemeTO;
	private List<CurriculumSchemeTO> curriculumSchemeDetails = null;
	private String programType;
	private String program;
	private String course;
	private Date lastModifiedDate;
	private String modifiedBy;
		
	public String getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getFromCourse() {
		return fromCourse;
	}

	public void setFromCourse(String fromCourse) {
		this.fromCourse = fromCourse;
	}

	public String getToCourse() {
		return toCourse;
	}

	public void setToCourse(String toCourse) {
		this.toCourse = toCourse;
	}
	public String getAppliedCourse() {
		return appliedCourse;
	}

	public void setAppliedCourse(String appliedCourse) {
		this.appliedCourse = appliedCourse;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<SubjectGroupTO> getSubjectGroupList() {
		return subjectGroupList;
	}

	public void setSubjectGroupList(List<SubjectGroupTO> subjectGroupList) {
		this.subjectGroupList = subjectGroupList;
	}

	public List<CourseSchemeTO> getCourseSchemeList() {
		return courseSchemeList;
	}

	public void setCourseSchemeList(List<CourseSchemeTO> courseSchemeList) {
		this.courseSchemeList = courseSchemeList;
	}

	public CurriculumSchemeTO getCurriculumSchemeTO() {
		return curriculumSchemeTO;
	}

	public void setCurriculumSchemeTO(CurriculumSchemeTO curriculumSchemeTO) {
		this.curriculumSchemeTO = curriculumSchemeTO;
	}

	public List<CurriculumSchemeTO> getCurriculumSchemeDetails() {
		return curriculumSchemeDetails;
	}

	public void setCurriculumSchemeDetails(
			List<CurriculumSchemeTO> curriculumSchemeDetails) {
		this.curriculumSchemeDetails = curriculumSchemeDetails;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}
	

	

	public void setCourse(String course) {
		this.course = course;
	}
	

	public String getCourse() {
		return course;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
}
