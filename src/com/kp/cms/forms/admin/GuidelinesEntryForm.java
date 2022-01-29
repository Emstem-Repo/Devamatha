package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.GuidelinesEntryTO;


public class GuidelinesEntryForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private FormFile thefile;
	private String programType;
	private String program;
	private String course;
	private String year;
	private int id;
	private String selectedCourseId;
	private int selectedId;
	private int oldCourseId;
	private int oldYear;
	
	public int getOldCourseId() {
		return oldCourseId;
	}

	public void setOldCourseId(int oldCourseId) {
		this.oldCourseId = oldCourseId;
	}

	public int getOldYear() {
		return oldYear;
	}

	public void setOldYear(int oldYear) {
		this.oldYear = oldYear;
	}

	public int getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(int selectedId) {
		this.selectedId = selectedId;
	}

	public String getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(String selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private List<GuidelinesEntryTO>guideLinesDetails;
	
	
	public List<GuidelinesEntryTO> getGuideLinesDetails() {
		return guideLinesDetails;
	}

	public void setGuideLinesDetails(List<GuidelinesEntryTO> guideLinesDetails) {
		this.guideLinesDetails = guideLinesDetails;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	/**
	 * This method is used to reset the formbean properties
	 */
	public void clear()
	{
		this.programType=null;
		this.program=null;
		this.course=null;
		this.year=null;
	}

}