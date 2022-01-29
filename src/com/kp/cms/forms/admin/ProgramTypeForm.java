package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

/**
 * A Java Bean associated with ProgramTypeAction.
 * 
 * @see ActionForm
 * @author
 */
public class ProgramTypeForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String programTypeName;
	private List<ProgramTypeTO> programTypeList;
	private String editedField;
	private int duplId;
	private String ageFrom;
	private String ageTo;
	private String origAgeFrom;
	private String origAgeTo;
	private int id;
	private String isOpen;
	private String origisOpen;
	
	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	
	

	public String getEditedField() {
		return editedField;
	}

	public void setEditedField(String editedField) {
		this.editedField = editedField;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.programTypeName = null;
		this.ageFrom = null;
		this.ageTo = null;
		isOpen = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	public String getAgeFrom() {
		return ageFrom;
	}

	public String getAgeTo() {
		return ageTo;
	}

	public void setAgeFrom(String ageFrom) {
		this.ageFrom = ageFrom;
	}

	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrigAgeFrom() {
		return origAgeFrom;
	}

	public String getOrigAgeTo() {
		return origAgeTo;
	}

	public void setOrigAgeFrom(String origAgeFrom) {
		this.origAgeFrom = origAgeFrom;
	}

	public void setOrigAgeTo(String origAgeTo) {
		this.origAgeTo = origAgeTo;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getOrigisOpen() {
		return origisOpen;
	}

	public void setOrigisOpen(String origisOpen) {
		this.origisOpen = origisOpen;
	}
	
}