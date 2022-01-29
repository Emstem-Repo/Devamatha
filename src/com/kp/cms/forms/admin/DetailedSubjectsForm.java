package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DetailedSubjectsTO;
/**
 * 
 * A formbean for DetailedSubject Entry
 * Used in admission module to choose subject from dropdown.
 *
 */

public class DetailedSubjectsForm extends BaseActionForm {

	
	private String programType;
	private String program;
	private String course;
	private String subjectName;
	private String id;
	private String selectedCourseId;
	private List<DetailedSubjectsTO> detailedSubjectLists;
	private String oldCourseId;
	private String oldSubjectName;
	private String activationId;

	/**
	 * @return the activationId
	 */
	public String getActivationId() {
		return activationId;
	}

	/**
	 * @param activationId the activationId to set
	 */
	public void setActivationId(String activationId) {
		this.activationId = activationId;
	}

	public String getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(String selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
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

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	
	/**
	 * @return the detailedSubjectLists
	 */
	public List<DetailedSubjectsTO> getDetailedSubjectLists() {
		return detailedSubjectLists;
	}

	/**
	 * @param detailedSubjectLists the detailedSubjectLists to set
	 */
	public void setDetailedSubjectLists(
			List<DetailedSubjectsTO> detailedSubjectLists) {
		this.detailedSubjectLists = detailedSubjectLists;
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
		this.subjectName = null;
		this.id = null;
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the oldCourseId
	 */
	public String getOldCourseId() {
		return oldCourseId;
	}

	/**
	 * @param oldCourseId the oldCourseId to set
	 */
	public void setOldCourseId(String oldCourseId) {
		this.oldCourseId = oldCourseId;
	}

	/**
	 * @return the oldSubjectName
	 */
	public String getOldSubjectName() {
		return oldSubjectName;
	}

	/**
	 * @param oldSubjectName the oldSubjectName to set
	 */
	public void setOldSubjectName(String oldSubjectName) {
		this.oldSubjectName = oldSubjectName;
	}

}