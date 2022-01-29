package com.kp.cms.forms.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class EducationMasterForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String qualificationId;
	private String education;
	private int duplId;
	private String origEdu;
	private String origQualificationId;
	
	public String getQualificationId() {
		return qualificationId;
	}
	public String getEducation() {
		return education;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigEdu() {
		return origEdu;
	}
	public String getOrigQualificationId() {
		return origQualificationId;
	}
	public void setOrigEdu(String origEdu) {
		this.origEdu = origEdu;
	}
	public void setOrigQualificationId(String origQualificationId) {
		this.origQualificationId = origQualificationId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
		
}
