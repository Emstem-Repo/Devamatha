package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ClasswithStudentTO;

public class ClasswiseStudentListForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean casteDisplay;
	private String language;
	private String subRelId;
	private String casteId;
	private String studentCatId;
	private String organizationName;
	private String[] selClassids;
	private String[] selStCategoryIds;
	private List<ClasswithStudentTO> classWithStudentToList;
	
	public boolean isCasteDisplay() {
		return casteDisplay;
	}

	public void setCasteDisplay(boolean casteDisplay) {
		this.casteDisplay = casteDisplay;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSubRelId() {
		return subRelId;
	}

	public void setSubRelId(String subRelId) {
		this.subRelId = subRelId;
	}

	public String getCasteId() {
		return casteId;
	}

	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}

	public String getStudentCatId() {
		return studentCatId;
	}

	public void setStudentCatId(String studentCatId) {
		this.studentCatId = studentCatId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String[] getSelClassids() {
		return selClassids;
	}

	public String[] getSelStCategoryIds() {
		return selStCategoryIds;
	}

	public void setSelClassids(String[] selClassids) {
		this.selClassids = selClassids;
	}

	public void setSelStCategoryIds(String[] selStCategoryIds) {
		this.selStCategoryIds = selStCategoryIds;
	}

	public List<ClasswithStudentTO> getClassWithStudentToList() {
		return classWithStudentToList;
	}

	public void setClassWithStudentToList(
			List<ClasswithStudentTO> classWithStudentToList) {
		this.classWithStudentToList = classWithStudentToList;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		language = null;
		subRelId = null;
		casteId = null;
		studentCatId = null;
		organizationName = null;
		selClassids = null;
		selStCategoryIds = null;
		super.setReligionId(null);
//		classWithStudentToList = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}		

}
