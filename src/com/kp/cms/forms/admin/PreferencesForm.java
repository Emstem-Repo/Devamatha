package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.PreferencesTO;

public class PreferencesForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prefId;
	private String prefCourseId;
	private String prefProgName;
	private String prefCourseName;
	private String courseName;
	private String programTypeName;
	private String programName;
	private String prefProgramId;
	private String origCourseId;
	private String origPrefCourseid;
	private String editProgramType;
	private String editProgram;
	private String editCourse;
	
	
	private List<PreferencesTO> prefList;

	public String getPrefCourseId() {
		return prefCourseId;
	}

	public void setPrefCourseId(String prefCourseId) {
		this.prefCourseId = prefCourseId;
	}

	public String getPrefId() {
		return prefId;
	}

	public void setPrefId(String prefId) {
		this.prefId = prefId;
	}

	public String getPrefProgramId() {
		return prefProgramId;
	}

	public void setPrefProgramId(String prefProgramId) {
		this.prefProgramId = prefProgramId;
	}

	public List<PreferencesTO> getPrefList() {
		return prefList;
	}

	public void setPrefList(List<PreferencesTO> prefList) {
		this.prefList = prefList;
	}

	public String getPrefProgName() {
		return prefProgName;
	}

	public void setPrefProgName(String prefProgName) {
		this.prefProgName = prefProgName;
	}

	public String getPrefCourseName() {
		return prefCourseName;
	}

	public void setPrefCourseName(String prefCourseName) {
		this.prefCourseName = prefCourseName;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getOrigCourseId() {
		return origCourseId;
	}

	public void setOrigCourseId(String origCourseId) {
		this.origCourseId = origCourseId;
	}

	public String getOrigPrefCourseid() {
		return origPrefCourseid;
	}

	public void setOrigPrefCourseid(String origPrefCourseid) {
		this.origPrefCourseid = origPrefCourseid;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		prefCourseId = null;
		prefProgName = null;
		prefCourseName = null;
		prefProgramId =  null;
//		courseName = null;
//		programTypeName = null;
//		programName = null;
//		courseId = null;
//		programId = null;
//		programTypeId = null;
		prefProgramId = null;
	}
	
	public String getEditProgramType() {
		return editProgramType;
	}

	public String getEditProgram() {
		return editProgram;
	}

	public String getEditCourse() {
		return editCourse;
	}

	public void setEditProgramType(String editProgramType) {
		this.editProgramType = editProgramType;
	}

	public void setEditProgram(String editProgram) {
		this.editProgram = editProgram;
	}

	public void setEditCourse(String editCourse) {
		this.editCourse = editCourse;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void firstFromreset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		super.setCourseName(null);
		super.setProgramTypeName(null);
		super.setProgramName(null);
		super.setCourseId(null);
		super.setProgramId(null);
		super.setProgramTypeId(null);
	}
	
	
}
