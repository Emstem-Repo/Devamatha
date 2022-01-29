package com.kp.cms.forms.attendance;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.attendance.AttendanceCondonationTo;



public class AttendanceCondonationForm extends BaseActionForm {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String academicYear;
	private String academicYear_value;
	private String courseName;
	private String schemeName;
	private String course;
	private String scheme;
	private String className;
	private Map<String, String> schemeMapList;
	private Map<Integer, String> courseNameList;
	private Map<Integer, String> programList;
	private List<ProgramTypeTO> programTypeList;
	private List<AttendanceCondonationTo> studentList;
	private String mode;
	private String cutoff;
	private String schemeType;

	

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAcademicYear_value() {
		return academicYear_value;
	}

	public void setAcademicYear_value(String academicYear_value) {
		this.academicYear_value = academicYear_value;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}


	

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	

	public void setSchemeMapList(Map<String, String> schemeMapList) {
		this.schemeMapList = schemeMapList;
	}

	public Map<String, String> getSchemeMapList() {
		return schemeMapList;
	}

	public void setCourseNameList(Map<Integer, String> courseNameList) {
		this.courseNameList = courseNameList;
	}

	public Map<Integer, String> getCourseNameList() {
		return courseNameList;
	}
	
	

	public Map<Integer, String> getProgramList() {
		return programList;
	}

	public void setProgramList(Map<Integer, String> programList) {
		this.programList = programList;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public void setClassname(String classname) {
		this.className = classname;
	}

	public String getClassname() {
		return className;
	}

	public void setStudentList(List<AttendanceCondonationTo> studentList) {
		this.studentList = studentList;
	}

	public List<AttendanceCondonationTo> getStudentList() {
		return studentList;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setCutoff(String cutoff) {
		this.cutoff = cutoff;
	}

	public String getCutoff() {
		return cutoff;
	}
	
	

}
