package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AssignCertificateCourseDetailsTO;
import com.kp.cms.to.admission.AssignCertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTO;

public class AssignCertificateCourseForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String semNo;
	private String semType;
	private List<CourseTO> courseList;
	private List<ProgramTypeTO> programTypeList;
	private List<AssignCertificateCourseDetailsTO> assignCertificateCourseDetailsTOs;;
	private List<AssignCertificateCourseTO> assignCertificateCourseTOs;
	private String programTypeId;
	
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public List<AssignCertificateCourseTO> getAssignCertificateCourseTOs() {
		return assignCertificateCourseTOs;
	}
	public void setAssignCertificateCourseTOs(
			List<AssignCertificateCourseTO> assignCertificateCourseTOs) {
		this.assignCertificateCourseTOs = assignCertificateCourseTOs;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<AssignCertificateCourseDetailsTO> getAssignCertificateCourseDetailsTOs() {
		return assignCertificateCourseDetailsTOs;
	}
	public void setAssignCertificateCourseDetailsTOs(
			List<AssignCertificateCourseDetailsTO> assignCertificateCourseDetailsTOs) {
		this.assignCertificateCourseDetailsTOs = assignCertificateCourseDetailsTOs;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * reseting the fields 
	 */
	public void resetFields() {
		this.semNo=null;
		this.semType="ODD";
		super.setCourseId(null);
		super.setAcademicYear(null);
		this.id=0;
		this.programTypeId=null;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
}
